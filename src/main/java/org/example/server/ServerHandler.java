package org.example.server;



import io.netty.channel.*;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Slf4j
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {
    //用来存储连接进来的用户
    public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    public static final Map<Integer,Channel> map = new HashMap<Integer,Channel>();

    public static int idNum = 1;



    //一个新的连接进来的时候触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        group.add(ctx.channel());
        group.forEach(clientChannel -> {
            if(clientChannel != ctx.channel()) {
                clientChannel.writeAndFlush("用户[" + ctx.channel().remoteAddress() + "]上线\n");
            }
            else {
                clientChannel.writeAndFlush("欢迎您上线\n");
            }
        });

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("userEventTriggered 服务器收到事件");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
//        System.out.println(channel.remoteAddress()  +"消息" + msg);
        group.forEach(clientChannel -> {
            if(clientChannel != channel) {
                clientChannel.writeAndFlush(msg +"\r\n");
            }
        });

    }

    //客户端发送过来的消息
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//
//        System.out.println(msg);
//
//    }

    //客户端连接进来会触发的时间
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端连接成功 客户端id是" + ctx.channel().id());
        idNum++;
        map.put(idNum,ctx.channel());


    }

    //用来群发消息的方法
    public static void sendUDP(String msg){
        group.writeAndFlush(msg);
    }

    public static void sendUser(String uidId,String msg){
        group.forEach(item ->{
            System.out.println(item.id());
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("出现了异常");
        log.error(cause.toString());
    }

    /**
     * 客户端取消了绑定会触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        group.forEach(item ->{
            if (item == ctx){
                group.remove(item);
            }
        });
        log.info("用户" + ctx.channel().remoteAddress() +  "取消了绑定");
    }

    /**
     * 断开连接触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        group.forEach(item ->{
            if (item == ctx){
                group.remove(item);
            }
        });
        log.info("用户" + ctx.channel().remoteAddress() +  "断开了连接");
    }
}
