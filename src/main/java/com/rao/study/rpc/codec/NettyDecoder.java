package com.rao.study.rpc.codec;

import com.rao.study.rpc.Command;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author raoshihong
 * @date 2021-08-01 09:19
 */
public class NettyDecoder extends ReplayingDecoder<NettyDecoder.State> {

    private int length;

    public NettyDecoder() {
        // 必须提前初始化一个状态
        super(State.BODY_LENGTH);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode");
        switch (state()){
            case BODY_LENGTH:
                // 必须要在每个状态下调用buf.read，这样下标才会移动,数据才会读取到下一个状态,否则会一直调用handler和decode
                length = in.readInt();
                checkpoint(State.BODY);
                break;
            case BODY:
                byte[] bytes = new byte[length];
                in.readBytes(bytes);
                Command command = new Command();
                command.setName(new String(bytes));
                out.add(command);
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
