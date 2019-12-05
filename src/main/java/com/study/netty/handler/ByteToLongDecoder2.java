package com.study.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author ：fei
 * @date ：Created in 2019/12/4 0004 15:25
 */
public class ByteToLongDecoder2 extends ReplayingDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("ByteToLongDecoder2 decode invoked !");
        out.add(in.readLong());
    }
}
