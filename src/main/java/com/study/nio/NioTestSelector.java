package com.study.nio;

import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
 * @author ：fei
 * @date ：Created in 2019/11/25 0025 09:21
 *
 * 服务器监听五个端口号，用一个线程来处理所有的连接
 *
 */
public class NioTestSelector {
    public static void main(String[] args) throws Exception {
        //每个端口号对应一个服务器端通道
        int[] ports = new int[]{8855,8866,8877,8888,8899};
        Selector selector = Selector.open();
        for (int i = 0; i < ports.length; i++) {
            //服务端通道对象
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(ports[i]));
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        }
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey -> {
                try {
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        System.out.println("连接成功");
                        socketChannel.register(selector,SelectionKey.OP_READ);

                    }
                    if (selectionKey.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        socketChannel.configureBlocking(false);
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        while (socketChannel.read(byteBuffer) > 0){
                            byteBuffer.flip();
                            System.out.println(new String(byteBuffer.array()).trim());
                        }
                        socketChannel.register(selector, SelectionKey.OP_WRITE);
                    }
                    if (selectionKey.isWritable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        socketChannel.configureBlocking(false);
                        ByteBuffer byteBuffer = ByteBuffer.wrap(
                                LocalDateTime.now().format(
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                        .concat(System.getProperty("line.separator")).getBytes());
                        socketChannel.write(byteBuffer);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            selectionKeys.clear();
        }
    }
}
