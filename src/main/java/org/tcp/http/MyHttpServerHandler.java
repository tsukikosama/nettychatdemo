package org.tcp.http;
 
 
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import io.netty.util.CharsetUtil;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现一个简单的http 请求 get
 *  每个资源都会进行一次请求
 *  #
 */
public class MyHttpServerHandler extends SimpleChannelInboundHandler {

    //获取文件的访问路径
    private String uri ;

    public HttpMethod method;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        //读取资源
        File file = new File("C:\\Users\\Administrator\\Desktop\\新建文件夹\\tour-project");
        //如果保护httprequest请求就进去获取亲求路径
        if (msg instanceof HttpRequest) {
            //
            HttpRequest httpRequest = (HttpRequest) msg;
//            String s = httpRequest.headers().get(HttpHeaderNames.CONTENT_TYPE);
            uri = httpRequest.uri();
            method = httpRequest.method();
        }
        if (method == HttpMethod.GET){

            //获取文件通道
            FileChannel files = new FileInputStream(file + uri).getChannel();
            // 构造一个http响应体，即HttpResponse
            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            // 设置响应头信息
            setType(defaultFullHttpResponse,uri);
            //设置响应状态
            defaultFullHttpResponse.setStatus(HttpResponseStatus.OK);

            //读取文件大小
            int len =(int)files.size();
            //把文件写入请求体
            defaultFullHttpResponse.content().writeBytes(files,0,len);
            //设置请求长度
            defaultFullHttpResponse.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,len);


//        System.out.println(((HttpRequest) msg).uri());
//        System.out.println(response);
//           //写入给浏览器
            ctx.writeAndFlush(defaultFullHttpResponse);
        }

        //  post请求获取param 参数和 body参数
        if (method == HttpMethod.POST){

            FullHttpRequest httpRequest = (FullHttpRequest) msg;
            Map<String, String> parmMap = new HashMap<>();
            Map<String, String> parmMap2 = new HashMap<>();
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder((HttpRequest) msg);

            decoder.offer((HttpContent) msg);
    //        获取data里面的数据
            List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();
            //获取？后面的数据
            QueryStringDecoder decoder1 = new QueryStringDecoder(httpRequest.uri());
            decoder1.parameters().entrySet().forEach( entry -> {
                // entry.getValue()是一个List, 只取第一个元素
                parmMap2.put(entry.getKey(), entry.getValue().get(0));
                System.out.println(entry.getKey() +":" +  entry.getValue().get(0));
            });
        //        System.out.println(decoder1.parameters().entrySet().size());
            System.out.println(parmList.size());
            for (InterfaceHttpData parm : parmList) {
                Attribute data = (Attribute) parm;
                parmMap.put(data.getName(), data.getValue());
                System.out.println(data.getName() +":");
                System.out.println(data.getValue());
            }
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            ctx.writeAndFlush(response);

        }
    }

    public void setType(HttpResponse  response ,String file){
        //默认响应类型为text/html
        String contentType = "text/html";
        //通过请求资源的后缀名来确认该返回什么类型的响应
        if (file.endsWith(".jpg") || file.endsWith(".png") || file.endsWith(".jpeg") || file.endsWith(".ico")) {
            contentType =  "image/jpeg";
        } else if (uri.endsWith(".css")) {
            contentType = "text/css";
        } else if (uri.endsWith(".js")) {
            contentType = "text/javascript";
        }

        //设置响应类型
        response.headers().set("Content-Type", contentType);
    }
}