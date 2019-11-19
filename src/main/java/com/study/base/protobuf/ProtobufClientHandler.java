package com.study.base.protobuf;

import com.study.base.protobuf.protofile.AddressBookProtos;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author ：fei
 * @date ：Created in 2019/11/19 0019 14:45
 */
public class ProtobufClientHandler extends SimpleChannelInboundHandler<AddressBookProtos.AddressBook> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AddressBookProtos.AddressBook msg) throws Exception {


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        AddressBookProtos.Student student1 = AddressBookProtos.Student.newBuilder().setId(1)
                .setName("飞鸟").setEmail("11111@qq.com").build();
        AddressBookProtos.Student student2 = AddressBookProtos.Student.newBuilder().setId(2)
                .setName("飞飞").setEmail("22222@qq.com").build();
        AddressBookProtos.AddressBook msg = AddressBookProtos.AddressBook.newBuilder()
                .addPeople(student1).addPeople(student2).build();
        ctx.writeAndFlush(msg);
    }
}
