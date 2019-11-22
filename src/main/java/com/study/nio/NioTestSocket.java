package com.study.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author ：fei
 * @date ：Created in 2019/11/22 0022 17:19
 */
public class NioTestSocket {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8899));
        ByteBuffer wrap = ByteBuffer.wrap("123456789".getBytes());
        socketChannel.write(wrap);
        wrap.flip();
        socketChannel.read(wrap);
        wrap.flip();
        while (wrap.hasRemaining()){
            System.out.println(wrap.get());
        }
        while (true){

        }
    }
}
