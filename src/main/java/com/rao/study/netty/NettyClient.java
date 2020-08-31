package com.rao.study.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author raoshihong
 * @date 2020-08-31 23:25
 */
public class NettyClient {

    public static void main(String[] args) {

        // 创建一个EventLoop
        EventLoopGroup work = new NioEventLoopGroup(1);

        try {
            // 创建客户端
            Bootstrap bootstrap = new Bootstrap().group(work)
                // 设置为NioSocketChannel
                .channel(NioSocketChannel.class).handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast();
                    }
                });

            ChannelFuture future = bootstrap.connect(new InetSocketAddress(6060)).sync();

            future.channel().closeFuture();

        }catch (Exception e){
            work.shutdownGracefully();
        }
    }

}
