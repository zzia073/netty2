package com.study.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author ：fei
 * @date ：Created in 2019/11/29 0029 16:54
 */
public class ByteBufTest01 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            byteBuf.writeByte(i);
        }
        for (int i = 0; i < byteBuf.capacity()/2; i++) {
            System.out.println(byteBuf.readByte());
        }
        System.out.println(byteBuf.readerIndex() + "  " + byteBuf.writerIndex());
    }
}
