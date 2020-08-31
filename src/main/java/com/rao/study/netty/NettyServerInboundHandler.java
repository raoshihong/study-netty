package com.rao.study.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.channels.SelectionKey;

/**
 * 自定义入栈Handler
 *
 * @author raoshihong
 * @date 2020-08-31 23:16
 */
public class NettyServerInboundHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当有客户端连接上服务器时,就会触发并调用该方法
     *
     * @param ctx channel上下文,在这个上下文中存放了渠道和管道
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 从上下文中获取当前连接上的channel
        Channel channel = ctx.channel();
        System.out.println("客户端:"+channel.remoteAddress().toString()+"连接上来");

    }

    /**
     * 当渠道中有可读取数据时触发该方法,即SelectionKey.isReadable()
     * @param ctx 上下文
     * @param msg 可读消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf message = (ByteBuf)msg;

        System.out.println("读取到的消息:"+message.toString(CharsetUtil.UTF_8));
    }

    /**
     * 消息读取完毕时调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        String msg = "hello 你好!";
        ctx.writeAndFlush(Unpooled.copiedBuffer(msg.getBytes(CharsetUtil.UTF_8)));
    }

    /**
     * 异常时调用,或者异常关闭时调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
