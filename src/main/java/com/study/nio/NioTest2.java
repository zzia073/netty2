package com.study.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ：fei
 * @date ：Created in 2019/11/21 0021 16:58
 */
public class NioTest2 {
    public static void main(String[] args) throws Exception{
        //inputStream 底层通道也是只可读不可写默认channel实现writeable为false
        FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        //获取文件通道，默认实现不可写入
        FileChannel channel = fileInputStream.getChannel();
        channel.read(byteBuffer);
        byteBuffer.flip();
        String str = "";
        while (byteBuffer.hasRemaining()){
            str = str.concat(String.valueOf((char) byteBuffer.get()));
        }
        System.out.println(str);
        fileInputStream.close();
    }
}
