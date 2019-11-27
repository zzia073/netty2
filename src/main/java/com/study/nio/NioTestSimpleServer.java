package com.study.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * @author ：fei
 * @date ：Created in 2019/11/25 0025 17:41
 * 服务端需要保持每次来的连接信息
 */
public class NioTestSimpleServer {
    public static void main(String[] args) throws Exception {
        //用来保存每次连接来的客户端信息
        Map<String, SocketChannel> socketChannelMap = new HashMap<>();
        //服务端的通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8899));
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            //监听选择器上的事件发生
            selector.select();
            //获取所有已发生的事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //判断事件类型
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                //如果是服务端通道accept事件发生，则获取来连接的客户端通道并保存下来
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel1.accept();
                    socketChannel.configureBlocking(false);
                    String key = UUID.randomUUID().toString();
                    socketChannelMap.put(key, socketChannel);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println(key + "连接成功");
                }
                if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    socketChannel.configureBlocking(false);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    socketChannel.read(byteBuffer);
                    String content = new String(byteBuffer.array(), 0, byteBuffer.position()).trim();
                    System.out.println(content);
                    for (String key: socketChannelMap.keySet()) {
                        SocketChannel sc = socketChannelMap.get(key);

                        if (sc != socketChannel){
                            sc.write(ByteBuffer.wrap(key.concat(" : " + content).trim().getBytes()));
                            byteBuffer.flip();
                        }
                    }
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                iterator.remove();
            }
        }
    }
}
