package com.ets.bus.socket.coder;

import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author xuqiang
 * @date 2019/7/20 9:33
 */
public class DataEncoder extends MessageToByteEncoder<ConcentratorProtocolBean> {
    protected void encode(ChannelHandlerContext channelHandlerContext, ConcentratorProtocolBean msg, ByteBuf out) throws Exception {
       // out.writeShort(msg.getRecHead());
        //out.writeByte(msg.getVersion());
        out.writeBytes(msg.getDeviceId());
       // out.writeByte(msg.getType());
        out.writeByte(msg.getFunctionCode());
        out.writeShort(msg.getContentLength());
        out.writeBytes(msg.getContent());
        out.writeShort(msg.getCheck());

    }
}
