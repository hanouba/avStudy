package com.ivosm.vvv.audiorecord;

/**
 * 创建者 by ${HanSir} on 2018/12/17.
 * 版权所有  WELLTRANS.
 * 说明   * 获取录音的音频流,用于拓展的处理
 */

public interface RecordStreamListener {
    void recordOfByte(byte[] data,int begin,int end);
}
