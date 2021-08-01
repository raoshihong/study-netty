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
 *
 *
 * 我们使用 ServerBootStrap.option 设置的参数适用于新创建的 ServerChannel 的 ChannelConfig，即侦听并接受客户端连接的服务器套接字。当调用 bind() 或 connect() 方法时，这些选项将在服务器通道上设置。此通道是每个服务器一个。ServerBootStrap.childOption 适用于通道的 channelConfig，一旦 serverChannel 接受客户端连接，就会创建该通道。此通道是每个客户端（或每个客户端套接字）。因此 ServerBootStrap.option 参数适用于侦听连接的服务器套接字（服务器通道），而 ServerBootStrap.childOption 参数适用于连接被接受后创建的套接字服务器套接字。同样可以扩展到 ServerBootstrap 类中的 attr vs childAttr 和 handler vs childHandler 方法。我怎么知道哪个选项应该是一个选项，哪个应该是 childOption ？支持哪些 ChannelOptions 取决于我们的通道类型使用。您可以参考您正在使用的 ChannelConfig 的 API 文档。
 *
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
