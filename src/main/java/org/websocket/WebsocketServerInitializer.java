package org.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;

public class WebsocketServerInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解析http请求
        pipeline.addLast(new HttpServerCodec());
        //聚合请求
        pipeline.addLast(new HttpObjectAggregator(65536));
        //webSocket数据压缩
        pipeline.addLast(new WebSocketServerCompressionHandler());
        // WebSocket 握手、控制帧处理  第一个是请求路径  第二个是表示不使用子协议  第三个参数表示websocket复杂关闭空闲请求
        pipeline.addLast(new WebSocketServerProtocolHandler("/wuhu", null, true));
        //自己的处理器
        pipeline.addLast("myhandler",new MyWebSocketHandler());
        pipeline.addLast("myhandler2", new MyWebSocketHandlerRead());
    }
}
