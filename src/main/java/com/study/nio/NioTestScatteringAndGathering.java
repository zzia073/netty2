package com.study.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author ：fei
 * @date ：Created in 2019/11/22 0022 16:27
 * 关于Buffer的Scattering和Gathering
 * Scattering会传入一个buffer数组，buffer读的时候会一个一个写满
 * Gathering会传入一个buffer数组，buffer写的时候会一个一个写满
 */
public class NioTestScatteringAndGathering {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(8899));
        int messageLength = 2 + 3 + 4;
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);
        SocketChannel socketChannel = channel.accept();
        while (true) {
            int bytesRead = 0;
            while (bytesRead < messageLength){
                long r = socketChannel.read(buffers);
                bytesRead += r;
                System.out.println("bytesRead: " + bytesRead);
            }
            //读完之后flip所有buffer
            Arrays.asList(buffers).forEach(ByteBuffer::flip);
            int bytesWrite = 0;
            while (bytesWrite < messageLength){
                long write = socketChannel.write(buffers);
                bytesWrite += write;
                System.out.println("bytesWrite: " + bytesWrite);
            }
            Arrays.asList(buffers).forEach(ByteBuffer::clear);
        }
     }
}
