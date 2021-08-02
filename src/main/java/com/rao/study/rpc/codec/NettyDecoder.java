package com.rao.study.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author raoshihong
 * @date 2021-08-01 09:19
 */
public class NettyDecoder extends ReplayingDecoder<NettyDecoder.State> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()){
            case BODY_LENGTH:
                checkpoint(State.BODY);
                break;
            case BODY:
//                out.add();
                checkpoint(State.BODY_LENGTH);
                break;
            default:
                System.out.println("unknown decoder state"+state());
        }
    }

    enum State{
        BODY_LENGTH,
        BODY
    }
}
