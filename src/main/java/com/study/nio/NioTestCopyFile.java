package com.study.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ：fei
 * @date ：Created in 2019/11/22 0022 11:03
 */
public class NioTestCopyFile {
    public static void main(String[] args) throws Exception {
        FileInputStream input = new FileInputStream("input.txt");
        FileOutputStream output = new FileOutputStream("output.txt");
        FileChannel in = input.getChannel();
        FileChannel out = output.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);

        while (in.read(byteBuffer) > 0){
            byteBuffer.flip();
            out.write(byteBuffer);
            byteBuffer.flip();
        }

        input.close();
        output.close();
    }
}
