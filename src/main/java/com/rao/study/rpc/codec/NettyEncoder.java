package com.rao.study.rpc.codec;

import com.rao.study.rpc.Command;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author raoshihong
 * @date 2021-08-01 09:05
 */
public class NettyEncoder extends MessageToByteEncoder<Command> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Command msg, ByteBuf out) throws Exception {
        System.out.println("encode");

        // 这块到编码输出要与解码到顺序一致
        out.writeInt(msg.getName().length());
        out.writeBytes(msg.getName().getBytes());

    }
}
