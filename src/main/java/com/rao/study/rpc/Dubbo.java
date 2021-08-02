package com.rao.study.rpc;

/**
 * @author raoshihong
 * @date 2021-08-02 23:46
 */
public class Dubbo {
    public static void main(String[] args) {
        RpcServer server = new RpcServer(2181);
        server.start();
    }
}
