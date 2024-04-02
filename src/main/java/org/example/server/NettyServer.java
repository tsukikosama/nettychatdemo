package org.example.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class NettyServer {

    public static void start() throws Exception {
        ChannelFuture future = null;
        //构建两个事件组
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
       try {
           //构建一个引导类
           ServerBootstrap bootstrap = new ServerBootstrap();
           //配置使用的通道  设置和子类的设置
           bootstrap.group(boss,work).channel(NioServerSocketChannel.class)
                   .option(ChannelOption.SO_BACKLOG, 12)
                   .childOption(ChannelOption.SO_KEEPALIVE, true)
                   //添加一个自己的拦截器
                   .childHandler(new ServerChatHanlder());
           //绑定端口
           future = bootstrap.bind(8080).sync();
           //用户输入
           BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

           while (true){
               System.out.println("输入你的消息");
               //给全部的客户端发送消息

               ServerHandler.sendUDP(bf.readLine() + "\r\n");
//               future.channel().writeAndFlush(bf.readLine() + "\r\n");
           }

       }finally {
//           future.channel().closeFuture().sync();
           boss.shutdownGracefully();
           work.shutdownGracefully();
       }


    }
    public static void main(String[] args) throws Exception {
        start();
    }
}
