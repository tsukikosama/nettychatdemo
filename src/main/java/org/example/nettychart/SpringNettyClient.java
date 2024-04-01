package org.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import lombok.extern.slf4j.Slf4j;
import org.example.server.NettyServer;
import org.example.server.ServerHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.sql.SQLOutput;

@Slf4j
public class NettyClient {

    //这是一个netty客户端
    public static void start() throws Exception{
        EventLoopGroup boss = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            //设置引导
            bootstrap.group(boss).channel(NioSocketChannel.class)
                    .handler(new ClientHandler());
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();
            Channel channel = future.channel();
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

            while (true){
                System.out.println(channel.id() + "要发送的消息");
                //给服务端发送消息
                channel.writeAndFlush(bf.readLine() + "\r\n");
//                ServerHandler.sendUser(2,bf.readLine() + "\r\n");
                System.out.println(channel.id() + "666");
                //TODO 客户端给客户端发送消息为实现
//                ServerHandler.sendUser("89e72177",bf.readLine() + "\r\n");
            }


        }finally {
            boss.shutdownGracefully();
        }

    }

    //send
    public static void main(String[] args) {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
