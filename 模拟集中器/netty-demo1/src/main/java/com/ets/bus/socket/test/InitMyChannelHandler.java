package com.ets.bus.socket.test;

import com.ets.bus.socket.coder.ConcentratorDecoder;
import com.ets.bus.socket.coder.ConcentratorEncoder;
import com.ets.bus.socket.server.ConstantValue;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * @author 宋晨
 * @create 2019/5/7
 * 批量任务
 */
public class InitMyChannelHandler  extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel ch) throws Exception {
        // 添加自定义协议的编解码工具
        ByteBuf delimiter = Unpooled.copiedBuffer(new byte[]{ConstantValue.PROTOCOL_END}); //数据结束标识位
        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(ConstantValue.MAX_DATA_LENGTH, delimiter)); //分隔符解码器
        ch.pipeline().addLast(new ConcentratorDecoder());
        ch.pipeline().addLast(new ConcentratorEncoder());
        // 处理网络IO
        ch.pipeline().addLast(new NettyClientHandler());
    }
}
