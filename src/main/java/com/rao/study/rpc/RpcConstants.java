package com.rao.study.rpc;

/**
 * @author raoshihong
 * @date 2021-07-31 12:04
 */
public class RpcConstants {

    /**
     * cpus
     */
    public static final int CPUS = Runtime.getRuntime().availableProcessors();

    public static final String NETTY_EPOLL_ENABLE = System.getProperty("netty.epoll.enable", "true");
}
