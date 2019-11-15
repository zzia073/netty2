package com.study.base.origin.firstexemple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author ：fei
 * @date ：Created in 2019/11/15 0015 10:16
 * 后续示例写bind步骤
 */
public class NettyServer1 {
    public static void main(String[] args) throws Exception {
        //处理连接信息
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理业务信息
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //netty提供的服务端启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //绑定两个group，初始化channelFactory为后续创建channel做准备，给childHandler赋值
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new HttpServerCodec())
                                .addLast(new NettyServerHandler1());
                        }
                    });
            //服务端绑定一个端口
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            //
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
