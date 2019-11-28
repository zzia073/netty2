package com.study.netty.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ：fei
 * @date ：Created in 2019/11/28 0028 14:20
 * Reactor 相当于 EvenLoop
 * connect() decode() read() write() encode() reply()
 * 连上 读数据 解码 计算 编码 返回
 * 1. 先构造一个Reactor对象初始化好ServerSocketChannel，Selector；
 * 2. 注册对应的accept事件到Selector上；
 * 3. 注册之后产生的SelectionKey上携带一个Acceptor接收器。接收器的作用是用来把接收到的SocketChannel传给Handler去处理
 * 4. 使用时调用Reactor的启动方法分发事件（其实是调用attach上的Acceptor的方法接收事件源即客户端socket）
 * 5.
 *
 */
public class Reactor implements Runnable {
    final Selector selector;
    final ServerSocketChannel serverSocket;
    Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);
        SelectionKey sk = serverSocket.register(selector,
                SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor());
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while (it.hasNext()) {
                    dispatch((SelectionKey) it.next());
                }
                selected.clear();

            }
        }catch (IOException e){

        }

    }
    void dispatch(SelectionKey k) {
        Runnable r = (Runnable) k.attachment();
        if (r != null){
            r.run();
        }
    }
    class Acceptor implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel c = serverSocket.accept();
                if (c != null) {
                    new Handler(selector, c);
                }
            } catch (IOException e) {

            }
        }
    }
    class Handler implements Runnable {
        final SelectionKey sk;
        final SocketChannel socket;
        static final int MAXIN = 8192, MAXOUT = 11240*1024;
        ByteBuffer input = ByteBuffer.allocate(MAXIN);
        ByteBuffer output = ByteBuffer.allocate(MAXOUT);
        static final int READING = 0, SENDING = 1;
        int state = READING;
        Handler(Selector sel, SocketChannel c) throws IOException{
            socket = c;
            c.configureBlocking(false);
            sk = socket.register(sel, 0);
            sk.attach(this);
            sk.interestOps(SelectionKey.OP_READ);
            sel.wakeup();
        }
        boolean inputIsComplete() {return true;}
        boolean outputIsComplete() {return true;}
        void process() {}

        @Override
        public void run() {
            try {
                if (state == READING) {
                    read();
                }
                if (state == SENDING) {
                    send();
                }
            }catch (IOException ex){

            }
        }
        void read() throws IOException{
            socket.read(input);
            if (inputIsComplete()){
                process();
                state = SENDING;
                sk.interestOps(SelectionKey.OP_WRITE);
            }
        }
        void send() throws IOException{
            socket.write(output);
            if (outputIsComplete()) {
                sk.cancel();
            }
        }
    }
}
