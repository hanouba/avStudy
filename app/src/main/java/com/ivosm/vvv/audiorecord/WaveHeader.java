package com.ivosm.vvv.audiorecord;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 创建者 by ${HanSir} on 2018/12/17.
 * 版权所有  WELLTRANS.
 * 说明   wav头文件
 */

public class WaveHeader {
    public final char fileID[] = {'R','I','F','F'};
    public int fileLength;
    public char wavTag[] = {'W','A','V','E'};
    public char fmtHdrId[] = {'f','m','t',' '};
    public int fmtHdrLeth;
    public short formatTag;
    public short channels;
    public int samplesPerse;
    public int avgBytesPersec;
    public short blockAlign;
    public short bitsPerSamples;
    public char DateHdrID[] = {'d','a','t','e'};
    public int dataHdrleth;

    public byte[] getHeader() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WriteChar(bos, fileID);
        WriteInt(bos, fileLength);
        WriteChar(bos, wavTag);
        WriteChar(bos, fmtHdrId);
        WriteInt(bos,fmtHdrLeth);
        WriteShort(bos,formatTag);
        WriteShort(bos,channels);
        WriteInt(bos,samplesPerse);
        WriteInt(bos,avgBytesPersec);
        WriteShort(bos,blockAlign);
        WriteShort(bos,bitsPerSamples);
        WriteChar(bos,DateHdrID);
        WriteInt(bos,dataHdrleth);
        bos.flush();
        byte[] r = bos.toByteArray();
        bos.close();
        return r;
    }
    private void WriteShort(ByteArrayOutputStream bos, int s) throws IOException {
        byte[] mybyte = new byte[2];
        mybyte[1] =(byte)( (s << 16) >> 24 );
        mybyte[0] =(byte)( (s << 24) >> 24 );
        bos.write(mybyte);
    }

    private void WriteInt(ByteArrayOutputStream bos, int n) throws IOException {
        byte[] buf = new byte[4];
        buf[3] =(byte)( n >> 24 );
        buf[2] =(byte)( (n << 8) >> 24 );
        buf[1] =(byte)( (n << 16) >> 24 );
        buf[0] =(byte)( (n << 24) >> 24 );
        bos.write(buf);
    }

    private void WriteChar(ByteArrayOutputStream bos, char[] id) {
        for (int i=0; i<id.length; i++) {
            char c = id[i];
            bos.write(c);
        }
    }
}
