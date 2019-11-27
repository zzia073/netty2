package com.study.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author ：fei
 * @date ：Created in 2019/11/27 0027 14:43
 */
public class ZeroCopyNioServer {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(8899));
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while (true) {
            long s = System.nanoTime();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);
            int readCount = 0;
            while ((readCount = socketChannel.read(byteBuffer)) >= 0){
                byteBuffer.rewind();
            }
            System.out.println("完成");
        }
    }
}
