package com.ets.bus.socket.coder;

import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author 宋晨
 * @create 2019/4/12
 * 集中器协议数据编码器
 */
public class ConcentratorEncoder extends MessageToByteEncoder<ConcentratorProtocolBean> {

    protected void encode(ChannelHandlerContext channelHandlerContext, ConcentratorProtocolBean msg, ByteBuf out) throws Exception {
        out.writeByte(msg.getHead());
        out.writeByte(msg.getFunctionCode());
        out.writeBytes(msg.getDeviceId());
        out.writeShort(msg.getContentLength());
        out.writeBytes(msg.getContent());
        out.writeByte(msg.getCheck());
        out.writeByte(msg.getEnd());
    }
}
