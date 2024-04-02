package org.tcp.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer {
    public static void run() throws Exception {
        ChannelFuture future = null;
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss,work).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 12)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new HttpHelloWorldServerInitializer());
             future = bootstrap.bind(8080).sync();
             future.channel().closeFuture().sync();
        }finally {

            boss.shutdownGracefully();
            work.shutdownGracefully();
        }


    }

    public static void main(String[] args) throws Exception {
        run();
    }
}
