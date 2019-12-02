package com.study.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author ：fei
 * @date ：Created in 2019/11/29 0029 18:00
 */
public class ByteBufTest03 {
    public static void main(String[] args) {
        CompositeByteBuf byteBufs = Unpooled.compositeBuffer();
        ByteBuf byteBuf = Unpooled.buffer(6);
        ByteBuf directBuf = Unpooled.directBuffer(4);
        byteBufs.addComponents(byteBuf,directBuf);
        byteBufs.forEach(System.out::println);
        byteBufs.writeBytes("1234567890".getBytes());
        while (byteBufs.isReadable()) {
            System.out.println((char) byteBufs.readByte());
        }
        byteBufs.forEach(System.out::println);

    }
}
