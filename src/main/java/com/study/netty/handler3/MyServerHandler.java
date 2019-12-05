package com.study.netty.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author ：fei
 * @date ：Created in 2019/12/5 0005 10:16
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MyProtocol> {
    private int count = 0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyProtocol msg) throws Exception {
        String content = new String(msg.getContent(),CharsetUtil.UTF_8);
        System.out.println("第" + (++ count) + "次 , " + content);


        //======================服务端的返回========================================
        String serverMsg = "来自服务器的第" + count + "次返回";
        MyProtocol myProtocol = new MyProtocol();
        byte[] serverContent = serverMsg.getBytes("UTF-8");
        myProtocol.setLength(serverContent.length);
        myProtocol.setContent(serverContent);
        ctx.writeAndFlush(myProtocol);
    }
}
