package org.example.nettychart;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
@Component
public class SpringNettyClient {
    public static Channel channel ;
    //这是一个netty客户端
    public static void start(String msg) throws Exception{
        EventLoopGroup boss = new NioEventLoopGroup();


            Bootstrap bootstrap = new Bootstrap();
            //设置引导
            bootstrap.group(boss).channel(NioSocketChannel.class)
                    .handler(new SpringClientHandler())
                    ;
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();
            channel = future.channel();

        channel.writeAndFlush(msg + "\r\n");
    }

    //send

}
