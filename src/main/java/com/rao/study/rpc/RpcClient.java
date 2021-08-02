package com.rao.study.rpc;

import com.rao.study.rpc.utils.NettyUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * rpc客户端工具，用来建立客户端,并通过连接到服务器，持有连接的channel，并发送消息
 * @author raoshihong
 * @date 2021-07-31 12:04
 */
public class RpcClient {

    private final Bootstrap bootstrap = new Bootstrap();

    /**
     * 客户端只需要一个workergroup
     */
    private EventLoopGroup workerGroup;

    public RpcClient(){
        if (NettyUtils.useEpoll()) {
            workerGroup = new EpollEventLoopGroup(10, new ThreadFactory() {
                private final AtomicInteger threadIndex = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, String.format("NettyClientWorkerThread_%d", this.threadIndex.incrementAndGet()));
                }
            });
        }else{
            workerGroup = new NioEventLoopGroup(10, new ThreadFactory() {
                private final AtomicInteger threadIndex = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, String.format("NettyClientWorkerThread_%d", this.threadIndex.incrementAndGet()));
                }
            });
        }
    }

    /**
     * 启动客户端
     */
    public void start(){
//        this.bootstrap.group(workerGroup)
//            .channel(NettyUtils.getSocketChannelClass())
//            .option();
    }

    // 通过channel发送消息,如果有channel则直接发送,如果没有则尝试通过客户端连接到指定host服务并返回channel，再通过channel发送消息
}
