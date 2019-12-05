package com.study.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author ：fei
 * @date ：Created in 2019/12/4 0004 14:08
 */
public class LongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("encode invoked !");
        System.out.println(msg);
        out.writeLong(msg);
    }
}
