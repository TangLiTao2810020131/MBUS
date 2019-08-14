package com.ets.bus.socket.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author 姚轶文
 * @create 2019- 04-07 17:41
 * 信令下发编码器
 */
public class MyEncoder  extends MessageToByteEncoder<RequestMessageBean> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RequestMessageBean msg, ByteBuf out) throws Exception {
        // 写入消息SmartCar的具体内容
        // 1.写入消息的开头的信息标志(int类型)
        out.writeInt(msg.getHead_data());
        // 2.写入消息的长度(int 类型)
        out.writeInt(msg.getContentLength());
        // 3.写入消息的内容(byte[]类型)
        out.writeBytes(msg.getContent());
    }
}
