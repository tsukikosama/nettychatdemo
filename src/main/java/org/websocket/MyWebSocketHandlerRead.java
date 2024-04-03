package org.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class MyWebSocketHandlerRead extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println(msg.toString() + "66666");
        ctx.channel().writeAndFlush(msg.toString());
        super.write(ctx, msg, promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("rede 666");

        super.read(ctx);
    }
}
