package com.study.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author ：fei
 * @date ：Created in 2019/12/4 0004 14:05
 */
public class ByteToLongDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode invoked!");
        System.out.println(in.readableBytes());
        if (in.readableBytes() >= Long.BYTES){
            out.add(in.readLong());
        }
    }
}
