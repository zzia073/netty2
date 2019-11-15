package com.study.base.origin.thirdexemple;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author ：fei
 * @date ：Created in 2019/11/15 0015 15:48
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 所有与服务端建立连接的客户端
     * 每次有客户端连接都会调用 childHandler 中添加的 ChannelInitializer 对象的 initChannel 方法
     * 每次添加一个新的 ChatServerHandler 因此此处的 channelGroup 必须是类级别 static 的，否则每次创建一个就是去了容器的意义
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        //广播channelGroup中的所有用户，如果是自己则返回带着自己
        channelGroup.forEach(ch ->{
            if (ch == channel){
                ch.writeAndFlush("【自己】 - " + msg + "\n");
            }else {
                ch.writeAndFlush(ch.remoteAddress() + " - " + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
//        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    /**
     * 当客户端与服务端建立连接时会调用该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //用的编解码器为 delimiter 编解码器，并且用回车来做分隔符，因此每个消息后必须加入 \n 用来识别
        //此处先写后 add 是在某个客户端连接上之后不给他自己发送连接信息，只是给其他所有用户发送信息
        channelGroup.writeAndFlush("【服务器】 - " + channel.remoteAddress() + "加入\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】 - " + channel.remoteAddress() + "离开\n");
        System.out.println("当前聊天室人数：" + channelGroup.size());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 下线");
    }

}
