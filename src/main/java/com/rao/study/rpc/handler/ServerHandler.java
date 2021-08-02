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
        // 在这快解析
        Command command = (Command)msg;
        // 将这个channel保存起来,到时通过这个channel发送数据
        Channel channel = ctx.channel();
        System.out.println("command"+command.getName());
        channel.writeAndFlush("aa");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
