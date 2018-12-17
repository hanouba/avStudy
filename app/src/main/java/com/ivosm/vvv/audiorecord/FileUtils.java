package com.ivosm.vvv.audiorecord;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import org.w3c.dom.ls.LSException;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者 by ${HanSir} on 2018/12/17.
 * 版权所有  WELLTRANS.
 * 说明
 */

public class FileUtils {
    private static String rootPath = "pauseRecorDemo";
    //原始文件 不能播放的
    private final static  String AUDIO_PCM_BASEPATH = "/"+rootPath + "/pcm/";
    //可以播放的高质量文件
    private final static  String AUDIO_WAV_BASEPATH = "/" + rootPath + "/wav/";


    private static void setRoooPath(String roooPath) {
        FileUtils.rootPath = roooPath;
    }
    //获取pcm格式文件路径
    public static String getAudioPcmAbsolutepath(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            Log.d("AudioReocrdTag",fileName);
            throw  new NullPointerException("filename is Empty");
        }
        if (!isSdcardExit()) {
            Log.d("AudioReocrdTag","isSdcardExit==false");
            throw  new IllegalStateException("sd card not found");
        }
        String mAudioRawPath = "";
        if (isSdcardExit()) {
            Log.d("AudioReocrdTag","isSdcardExit="+fileName);
            if (!fileName.endsWith(".pcm")) {
                fileName = fileName + ".pcm";
            }
            String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() +  AUDIO_PCM_BASEPATH;
            Log.d("AudioReocrdTag","fileBasePath="+fileBasePath);
            File file = new File(fileBasePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            mAudioRawPath = fileBasePath + fileName;
        }
        return mAudioRawPath;
    }

    public static  String getWavFileAbsolutePath(String fileName) {
        if (fileName.isEmpty()) {
            throw new NullPointerException("fileName isEmpty");

        }
        if (!isSdcardExit()) {
            throw new IllegalStateException("sd card nof found");
        }
        String mAudioWavPath = "";
        if (isSdcardExit()) {


        if (!fileName.endsWith(".wav")) {
            fileName = fileName + ".wav";
        }
        String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + AUDIO_WAV_BASEPATH;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        mAudioWavPath = path + fileName;
        }
        return mAudioWavPath;
    }

    private static boolean isSdcardExit() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d("AudioReocrdTag","isSdcardExit==true");
        return true;
        }
        else
            Log.d("AudioReocrdTag","isSdcardExit==elsefalse");
            return false;
        }

    /**
     * 获取全部的PCM文件
     *
     */
    public static List<File> getpcmFiles () {
        List<File> list = new ArrayList<>();
        //文件夹路径
        String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_PCM_BASEPATH;
        File file = new File(fileBasePath);
        if (!file.exists()) {

        }else {
            File[] files = file.listFiles();
            for (File f :
                    files) {
               list.add(f);
            }
        }
        return list;
    }

    public static List<File> getWavFiles() {
        List<File> files = new ArrayList<>();
        String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() +  AUDIO_WAV_BASEPATH;
        File file = new File(fileBasePath);
        if (!file.exists()) {

        }else {
            File[] wavFiles = file.listFiles();
            for (File wavfile :
                 wavFiles) {
                files.add(wavfile);
            }

        }
        return files;
    }

}
