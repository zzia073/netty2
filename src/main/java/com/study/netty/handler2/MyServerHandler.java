package com.study.netty.handler2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.UUID;

/**
 * @author ：fei
 * @date ：Created in 2019/12/4 0004 17:14
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("服务端接收到的消息内容：" + message);
        System.out.println("服务端接收到的消息数量：" + (++ this.count));
        ByteBuf responseByteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), CharsetUtil.UTF_8);
        ctx.writeAndFlush(responseByteBuf);
    }
}
