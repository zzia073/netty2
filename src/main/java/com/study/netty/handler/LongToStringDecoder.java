package com.study.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author ：fei
 * @date ：Created in 2019/12/4 0004 15:51
 */
public class LongToStringDecoder extends MessageToMessageDecoder<Long> {
    @Override
    protected void decode(ChannelHandlerContext ctx, Long msg, List<Object> out) throws Exception {
        out.add(String.valueOf(msg));
    }
}
