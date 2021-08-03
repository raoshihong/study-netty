package com.rao.study.rpc.handler;

import com.rao.study.rpc.Command;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author raoshihong
 * @date 2021-08-02 23:13
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("serverhandler");
        // 在这快解析
        Command command = (Command)msg;
        System.out.println("获取来自客户端到信息:"+command.getName());
        command.setName("server-bbb");
        ctx.writeAndFlush(command);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
