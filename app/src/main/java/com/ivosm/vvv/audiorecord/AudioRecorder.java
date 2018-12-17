package com.ivosm.vvv.audiorecord;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者 by ${HanSir} on 2018/12/17.
 * 版权所有  WELLTRANS.
 * 说明
 */

public class AudioRecorder {
    //声明audiorecorder
    private static AudioRecorder audioRecorder;
    //音频输入 -- 麦克风  输入类型
    private final static int AUDIO_INPUT = MediaRecorder.AudioSource.MIC;
    //采用频率
    //44100是目前的标准，但是某些设备仍然支持22050，16000，11025
    //采样频率一般共分为22.05KHz、44.1KHz、48KHz三个等级
    private final static int AUDIO_SAMPLE_RATE = 16000;
    //声道  单声道
    private final static int AUDIO_CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    //编码
    private final static int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    //缓冲区字节大小
    private int bufferSizeBytes = 0;
    //录音对象
    private AudioRecord audioRecord;
    //录音状态
    private Status status = Status.STATUS_NO_READY;
    //文件名
    private String fileName;
    //录音文件
    private List<String> filesNmae = new ArrayList<>();

    private AudioRecorder() {

    }

    //单例模式
    public static AudioRecorder getInstance() {
        if (audioRecorder == null) {
            audioRecorder = new AudioRecorder();
        }
        return audioRecorder;
    }

    //创建录音对象
    public void createAudio(String fileName, int audioSource, int sampleRateInHz, int channelConfig, int audioFormat) {
        //获取缓冲区字节大小
        bufferSizeBytes = audioRecord.getMinBufferSize(sampleRateInHz, channelConfig, channelConfig);
        audioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSizeBytes);
        this.fileName = fileName;
    }

    //创建默认的录音对象
    public void createDefaultAudio(String fileName) {
        //获取缓冲区字节大小
        bufferSizeBytes = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING);
        audioRecord = new AudioRecord(AUDIO_INPUT, AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING, bufferSizeBytes);
        this.fileName = fileName;
        status = Status.STATUS_READY;
    }

    //开始录音
    public void startRecord(final RecordStreamListener listener) {
        if (status == Status.STATUS_NO_READY || TextUtils.isEmpty(fileName)) {
            throw new IllegalStateException("录音尚未初始化,请检查是否禁止录音权限");
        }
        if (status == Status.STATUS_START) {
            throw new IllegalStateException("正在录音");
        }
        audioRecord.startRecording();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //写入文件夹
                writeDataToFile(listener);
            }
        }).start();
    }

    private void writeDataToFile(RecordStreamListener listener) {
        //new 一个byte 数组来存着一些字节数据 大小为缓冲区大小
        byte[] audiodata = new byte[bufferSizeBytes];
        FileOutputStream fos = null;
        int readSize = 0;
        try {
            String currentFileName = fileName;
            if (status == Status.STATUS_PAUSE) {
                //暂停状态 将文件名后面加上个数组,防止重名文件内容被覆盖
                currentFileName += filesNmae.size();
            }
            filesNmae.add(currentFileName);
            File file = new File(FileUtils.getAudioPcmAbsolutepath(currentFileName));
            if (file.exists()) {
                //为啥删除呢  说明 重复了 重新录制
                file.delete();
            }

            fos = new FileOutputStream(file);
        } catch (IllegalStateException e) {
            e.getMessage();
            throw new IllegalStateException(e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //将录音状态设置为 正在录音
        status = Status.STATUS_START;
        while (status == Status.STATUS_START) {
            readSize = audioRecord.read(audiodata, 0, bufferSizeBytes);
            if (AudioRecord.ERROR_INVALID_OPERATION != readSize && fos != null) {
                try {
                    fos.write(audiodata);
                    if (listener != null) {
                        //拓展业务
                        listener.recordOfByte(audiodata, 0, audiodata.length);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (fos != null) {
                    fos.close();//关闭流
                }
            } catch (IOException e) {
            }


        }
    }


    /**
     * 将pcm 文件合并成 wav
     * @param filesPaths  pcm 文件路径
     */
    private void mergerPCMFilesToMAVFiles(List<String> filesPaths) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if ()
            }
        }).start();
    }

    /**
     * 自定义录音对象的状态
     */
    public enum Status {
        //未开始
        STATUS_NO_READY,
        //预备
        STATUS_READY,
        //录音
        STATUS_START,
        //暂停
        STATUS_PAUSE,
        //停止
        STATUS_STOP
    }
}
