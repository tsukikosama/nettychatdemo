package org.example.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

public class ServerChatHandler2 extends ChannelOutboundHandlerAdapter {

    // 写入的时候触发
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("用户[" + ctx.channel().remoteAddress() +"]发送了消息" + msg );

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
        System.out.println( "用户["+ctx.channel().remoteAddress()+"]给你发消息了");
        super.read(ctx);
    }




}
