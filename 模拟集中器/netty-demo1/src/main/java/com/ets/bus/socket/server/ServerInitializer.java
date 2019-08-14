package com.ets.bus.socket.server;

import com.ets.bus.socket.coder.ConcentratorDecoder;
import com.ets.bus.socket.coder.ConcentratorEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author 姚轶文
 * @create 2019- 04-06 16:34
 */
public class ServerInitializer  extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // TODO Auto-generated method stub
        ByteBuf delimiter = Unpooled.copiedBuffer(new byte[]{ConstantValue.PROTOCOL_END}); //数据结束标识位

        ChannelPipeline pipline = ch.pipeline();
//        pipline.addLast(new IdleStateHandler(360, 0, 0, TimeUnit.SECONDS));//设置客户端心跳超时时间，单位秒
        pipline.addLast(new DelimiterBasedFrameDecoder(ConstantValue.MAX_DATA_LENGTH, delimiter)); //分隔符解码器
//        pipline.addLast(new StringDecoder(CharsetUtil.UTF_8));
//        pipline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipline.addLast(new ConcentratorDecoder());
        pipline.addLast(new ConcentratorEncoder());
        pipline.addLast(new ServerHandler());

    }

    public static void main(String[] args){
        ByteBuf delimiter = Unpooled.copiedBuffer("0FH".getBytes());
        System.out.println("结果："+delimiter.array());
    }

}
