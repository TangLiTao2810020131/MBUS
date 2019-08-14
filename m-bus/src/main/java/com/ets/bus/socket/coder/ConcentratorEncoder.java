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
@SuppressWarnings("all")
public class ConcentratorEncoder extends MessageToByteEncoder<ConcentratorProtocolBean> {

    protected void encode(ChannelHandlerContext channelHandlerContext, ConcentratorProtocolBean msg, ByteBuf out) throws Exception {

        out.writeByte((byte)msg.getHead());
        out.writeByte(msg.getVersion());
        out.writeBytes(msg.getDeviceId());
        out.writeByte(msg.getType());
        out.writeByte(msg.getFunctionCode());
        out.writeShort(msg.getContentLength());
        out.writeBytes(msg.getContent());
        out.writeShort(msg.getCheck());
        out.writeShort(msg.getEnd());

    }
}
