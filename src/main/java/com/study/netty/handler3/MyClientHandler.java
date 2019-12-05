package com.study.netty.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author ：fei
 * @date ：Created in 2019/12/5 0005 10:22
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MyProtocol> {
    private int count = 0;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i ++) {
            byte[] content = "test myProtocol".getBytes("UTF-8");
            MyProtocol myProtocol = new MyProtocol();
            myProtocol.setLength(content.length);
            myProtocol.setContent(content);
            ctx.writeAndFlush(myProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyProtocol msg) throws Exception {
        String content = new String(msg.getContent(),CharsetUtil.UTF_8);
        System.out.println("第" + (++ count) + "次 , " + content);
    }
}
