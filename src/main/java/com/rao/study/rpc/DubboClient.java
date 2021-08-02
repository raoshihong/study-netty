package com.rao.study.rpc;

/**
 * @author raoshihong
 * @date 2021-08-03 00:07
 */
public class DubboClient {
    public static void main(String[] args) {
        RpcClient client = new RpcClient();
        client.send("127.0.0.1",2181);
    }
}
