package com.rao.study.rpc.utils;

/**
 * @author raoshihong
 * @date 2021-07-31 21:22
 */
public class RpcException extends RuntimeException {
    public RpcException() {
    }

    public RpcException(String message) {
        super(message);
    }
}
