package org.example.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

public class ClientChatHandler2 extends ChannelOutboundHandlerAdapter {

    // 会写消息
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        System.out.println("我发的内容是" + msg );
        ctx.writeAndFlush(msg, promise);
//        super.write(ctx, msg, promise);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        System.out.println("远程地址" + remoteAddress);
        System.out.println("本地地址" + localAddress);
        System.out.println("连接进来了");
        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println( ctx.channel().remoteAddress()+"给你发消息了");
        super.read(ctx);
    }




}
