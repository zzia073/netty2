package com.study.base.protobuf;

import com.study.base.protobuf.protofile.AddressBookProtos;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @author ：fei
 * @date ：Created in 2019/11/18 0018 17:42
 *
 * 服务端要和客户端共用.proto文件或生成的文件，解决方案
 * 1. 用Git作为版本控制系统：
 *  git submodule: git仓库里的一个仓库 让本地项目引用外部项目，还是两个项目，两个仓库
 *
 *  ServerProject: 主工程服务端
 *
 *  data.proto文件是又一个新的项目只用来存放proto源文件
 *  Protobuf-Java:proto生成的文件的项目，存放生成的文件
 *
 *  ClientProject: 主工程客户端
 *
 *  branch:
 *      develop:
 *      test:
 *      master:
 *  2. git subtree 将Protobuf-Java项目merge到主工程仓库，合并为一个项目（推荐）
 *  3. nexus 把生成的java文件做成jar包然后两个项目去依赖
 */
public class ProtobufServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4))
                                        .addLast(new ProtobufDecoder(AddressBookProtos.AddressBook.newBuilder()
                                                .getDefaultInstanceForType()))
                                        .addLast(new ProtobufServerHandler());
                        }
                    });
            ChannelFuture cf = serverBootstrap.bind(8899).sync();
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
