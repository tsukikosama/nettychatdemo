package org.example.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.example.client.ClientChatHandler;

public class ServerChatHanlder extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

            socketChannel.pipeline()
                    //设置分隔符
                    .addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                    //解码和编码
                    .addLast("decoder", new StringDecoder())
                    .addLast("encoder", new StringEncoder())
                    //自己的拦截器
                    .addLast("handler", new ServerHandler())
                    .addLast(new ServerChatHandler2());



    }
}
