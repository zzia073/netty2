package com.study.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ：fei
 * @date ：Created in 2019/11/25 0025 17:42
 */
public class NioTestSimpleClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(8899));
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        while (true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isConnectable()) {
                    SocketChannel sc = (SocketChannel) selectionKey.channel();
                    sc.finishConnect();
                    sc.register(selector, SelectionKey.OP_WRITE);
                }
                if (selectionKey.isWritable()) {
                    SocketChannel sc = (SocketChannel) selectionKey.channel();
                    ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                    executorService.submit(() -> {
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            String s = reader.readLine();
                            sc.write(ByteBuffer.wrap(s.getBytes()));
                            sc.register(selector,SelectionKey.OP_WRITE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    sc.register(selector, SelectionKey.OP_READ);
                }
                if (selectionKey.isReadable()) {
                    SocketChannel sc = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    sc.read(byteBuffer);
                    byteBuffer.flip();
                    System.out.println(new String(byteBuffer.array()));
                    sc.register(selector, SelectionKey.OP_READ);
                }
            }
            selectionKeys.clear();
        }
    }
}
