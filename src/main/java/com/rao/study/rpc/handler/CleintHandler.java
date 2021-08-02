package com.rao.study.rpc.handler;

import com.rao.study.rpc.Command;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author raoshihong
 * @date 2021-08-02 23:12
 */
public class CleintHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Command command = (Command) msg;
        System.out.println("ss"+command.getName());
    }
}
