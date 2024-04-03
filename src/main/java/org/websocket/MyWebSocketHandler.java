package org.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

//只实现了接口客户端发送的请求
public class MyWebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof TextWebSocketFrame){
            String content = ((TextWebSocketFrame) msg).text();
            System.out.println("收到来自客户端的消息：" + content);
            ctx.writeAndFlush(content + "给你");
            FullHttpResponse response = (FullHttpResponse) msg;

        }

        if (msg instanceof BinaryWebSocketFrame){
            byte[] content = ((BinaryWebSocketFrame) msg).content().array();
            System.out.println("收到来自客户端的二进制消息：" + content.length);
        }


    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("websocket连接成功");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("可以开始发送消息了");
    }
}
