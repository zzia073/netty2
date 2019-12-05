package com.study.netty.handler3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author ：fei
 * @date ：Created in 2019/12/5 0005 10:07
 */
public class MyDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        MyProtocol myProtocol = new MyProtocol();
        myProtocol.setLength(length);
        myProtocol.setContent(content);
        out.add(myProtocol);
    }
}
