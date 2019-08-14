package com.ets.bus.socket.server;

import com.ets.bus.socket.coder.ConcentratorDecoder;
import com.ets.bus.socket.coder.ConcentratorEncoder;
import com.ets.bus.socket.coder.DataDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;

/**
 * @author 姚轶文
 * @create 2019- 04-06 16:34
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // TODO Auto-generated method stub
        ByteBuf delimiter = Unpooled.copiedBuffer(new byte[]{ConstantValue.PROTOCOL_REC_END}); //数据结束标识位
        ChannelPipeline pipline = ch.pipeline();
        pipline.addLast(new IdleStateHandler(360, 0, 0, TimeUnit.SECONDS));//设置客户端心跳超时时间，单位秒
        //pipline.addLast(new DelimiterBasedFrameDecoder(ConstantValue.MAX_DATA_LENGTH, delimiter)); //分隔符解码器
        //使用新的解码器
        pipline.addLast(new DataDecoder());
        pipline.addLast(new ConcentratorEncoder());
        pipline.addLast(new ServerHandler());

    }

}
