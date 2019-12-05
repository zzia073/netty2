package com.study.netty.handler3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author ：fei
 * @date ：Created in 2019/12/5 0005 10:51
 */
public class MyEncoder extends MessageToByteEncoder<MyProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MyProtocol msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
