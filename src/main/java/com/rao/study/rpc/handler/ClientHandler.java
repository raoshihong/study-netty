package com.rao.study.rpc.handler;

import com.rao.study.rpc.Command;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author raoshihong
 * @date 2021-08-02 23:12
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("clientHandler");
        Command command = (Command) msg;
        System.out.println("接收到服务端到信息"+command.getName());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().close();
    }
}
