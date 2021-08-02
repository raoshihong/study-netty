package com.rao.study.rpc;

import com.rao.study.rpc.handler.ServerHandler;
import com.rao.study.rpc.utils.NettyUtils;
import com.rao.study.rpc.utils.RpcException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import com.rao.study.rpc.codec.*;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 启动rpc服务端
 * @author raoshihong
 * @date 2021-07-31 12:04
 */
public class RpcServer {
    private final ServerBootstrap serverBootstrap = new ServerBootstrap();

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    private final AtomicBoolean isStarted = new AtomicBoolean(false);

    private int port;

    public RpcServer(int port){
        this.port = port;
        if (NettyUtils.useEpoll()) {
            this.bossGroup = new EpollEventLoopGroup(1, new ThreadFactory() {
                private final AtomicInteger threadIndex = new AtomicInteger(0);
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r,String.format("NettyServer_Boss_Thread_%d",this.threadIndex.incrementAndGet()));
                }
            });

            this.workGroup = new EpollEventLoopGroup(RpcConstants.CPUS * 2, new ThreadFactory() {
                private final AtomicInteger threadIndex = new AtomicInteger(0);
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r,String.format("NettyServer_Worker_Thread_%d",this.threadIndex.incrementAndGet()));
                }
            });
        }else{
            this.bossGroup = new NioEventLoopGroup(1, new ThreadFactory() {
                private final AtomicInteger threadIndex = new AtomicInteger(0);
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r,String.format("NettyServer_Boss_Thread_%d",this.threadIndex.incrementAndGet()));
                }
            });

            this.workGroup = new NioEventLoopGroup(RpcConstants.CPUS * 2, new ThreadFactory() {
                private final AtomicInteger threadIndex = new AtomicInteger(0);
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r,String.format("NettyServer_Worker_Thread_%d",this.threadIndex.incrementAndGet()));
                }
            });
        }
    }

    public void start(){
        if(isStarted.compareAndSet(false,true)){
            // 通过服务器启动器启动boss端主reactor线程和worker端reactor
            this.serverBootstrap.group(this.bossGroup,this.workGroup)
                // 设置服务端channel
                .channel(NettyUtils.getServerSocketChannel())
                // 设置服务端ServerSocketChannel的参数
                // 设置运行端口能重复绑定 【socket非常有用】
                .option(ChannelOption.SO_REUSEADDR,true)
                // 设置服务初始化可连接的大小
                .option(ChannelOption.SO_BACKLOG,1024)
                // 设置连接客户端后,来源客户端channel参数设置
                // 设置是否保存存活
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                // true 禁用了Nagle算法，允许小包的发送
                .childOption(ChannelOption.TCP_NODELAY,true)
                // 设置发送缓存区大小
                .childOption(ChannelOption.SO_SNDBUF,65535)
                // 设置接受缓存区大小
                .childOption(ChannelOption.SO_RCVBUF,65535)
                // 通过管道,绑定服务端的handler处理器
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 通过channel.pipeline管道添加handler处理器,注意顺序
                        ch.pipeline()
                            .addLast("encoder",new NettyEncoder())
                            .addLast("decoder",new NettyDecoder())
                            .addLast(new ServerHandler());
                    }
                });

            // 绑定端口号,并阻塞等待
            ChannelFuture future;
            try {
                future = this.serverBootstrap.bind(port).sync();
            }catch (Exception e){
                throw new RpcException("绑定端口号失败");
            }

            if (future.isSuccess()) {
                System.out.println("bind success");
            } else if (future.cause()!=null) {
                System.out.println("bind fail");
            }else {
                System.out.println("bind fail");
            }

        }
    }

}
