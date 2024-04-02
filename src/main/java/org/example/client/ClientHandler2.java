package org.example.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ClientHandler2 extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

                ch.pipeline()
                    .addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                    .addLast("decoder", new StringDecoder())
                    .addLast("encoder", new StringEncoder())
                    .addLast("handler", new ClientChatHandler2());
        }

}
