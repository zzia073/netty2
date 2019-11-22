package com.study.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ：fei
 * @date ：Created in 2019/11/21 0021 17:19
 */
public class NioTest3 {
    public static void main(String[] args) throws Exception {
        FileOutputStream outputStream = new FileOutputStream("NioTest3.txt");
        FileChannel channel = outputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byteBuffer.put("test fileOutputStream channel just write".getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);
        outputStream.close();
    }
}
