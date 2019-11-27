package com.study.nio;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author ：fei
 * @date ：Created in 2019/11/27 0027 14:58
 */
public class ZeroCopyNioClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);
        FileChannel fileChannel = new FileInputStream("E:/Photoshop_CS6.zip").getChannel();
        long n = System.nanoTime();
        fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        long m = System.nanoTime();
        System.out.println(m - n);
        socketChannel.close();
    }
}
