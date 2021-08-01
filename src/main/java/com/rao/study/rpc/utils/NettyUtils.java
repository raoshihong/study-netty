package com.rao.study.rpc.utils;

import com.rao.study.rpc.RpcConstants;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author raoshihong
 * @date 2021-07-31 12:05
 */
public class NettyUtils {

    /**
     * 判断是否使用epoll
     * @return
     */
    public static boolean useEpoll(){
        if (!OSUtils.isLinux() || !Epoll.isAvailable()) {
            return false;
        }
        return Boolean.parseBoolean(RpcConstants.NETTY_EPOLL_ENABLE);
    }

    /**
     * 获取ServerSocketChannel类实例
     * @return
     */
    public static Class<? extends ServerSocketChannel> getServerSocketChannel(){
        if (useEpoll()) {
            return EpollServerSocketChannel.class;
        }
        return NioServerSocketChannel.class;
    }

}
