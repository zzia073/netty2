package com.study.netty.handler2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author ：fei
 * @date ：Created in 2019/12/4 0004 17:24
 */
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i ++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("send from client ", CharsetUtil.UTF_8);
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("客户端收到的消息内容：" + message);
        System.out.println("客户端收到的消息数量：" + (++ this.count));

    }
}
