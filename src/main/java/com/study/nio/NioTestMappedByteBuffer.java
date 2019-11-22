package com.study.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ：fei
 * @date ：Created in 2019/11/22 0022 16:14
 * 用于内存映射是堆外内存
 * idea中看不出来变化，打开实际文件去看是否有变化
 */
public class NioTestMappedByteBuffer {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTestByteBuffer.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0,(byte) 'a');
        mappedByteBuffer.put(3,(byte) 'b');
        randomAccessFile.close();
    }
}
