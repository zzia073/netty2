package com.study.base.protobuf;

import com.study.base.protobuf.protofile.AddressBookProtos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ：fei
 * @date ：Created in 2019/11/19 0019 14:37
 */
public class ProtobufServerHandler extends SimpleChannelInboundHandler<AddressBookProtos.AddressBook> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AddressBookProtos.AddressBook msg) throws Exception {
        for (AddressBookProtos.Student student : msg.getPeopleList()) {
            System.out.print(student.getId() + " : ");
            System.out.print(student.getName());
            System.out.println("  " + student.getEmail());

        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdd 连接建立");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生");
        ctx.close();
    }
}
