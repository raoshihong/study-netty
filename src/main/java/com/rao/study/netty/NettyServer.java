package com.rao.study.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author raoshihong
 * @date 2020-08-31 23:01
 */
public class NettyServer {

    /**
     * Netty采用的是主从Reactor模式,在主从Reactor模式上升级了主Reactor多线程池模式
     * Reactor可以理解为响应堆模式,也可以理解为分发者模式,还可以理解为事件驱动模式,所以在Netty中采用的是EventLoop事件
     *
     * @param args
     */

    public static void main(String[] args) {

        // 创建主Reactor,可以设置多个线程
        EventLoopGroup boss = new NioEventLoopGroup(1);

        // 创建从Reactor,从Reactor一般多个线程
        EventLoopGroup work = new NioEventLoopGroup();

        try {
            // 创建服务启动器
            ServerBootstrap bootstrap = new ServerBootstrap().group(boss, work)
                // 设置服务器端Channel
                .channel(NioServerSocketChannel.class)
                // 设置服务器端Channel的配置参数
                .option(ChannelOption.SO_BACKLOG, 10).childOption(ChannelOption.SO_KEEPALIVE, true)
                // 设置从Reactor对应的handler处理器
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                        // 将对应的handler处理器添加到pipeline队列中
                        ch.pipeline().addLast(new NettyServerInboundHandler());
                    }
                });

            // 绑定端口,并同步等待
            ChannelFuture future = bootstrap.bind(6060).sync();

            // 等待服务关闭
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅的关闭线程
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }
}
