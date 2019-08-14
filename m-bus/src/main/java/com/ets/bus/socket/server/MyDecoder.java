package com.ets.bus.socket.server;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author 姚轶文
 * @create 2019- 04-07 17:46
 * 接受数据，解码器
 */
public class MyDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {

        // 可读长度必须大于基本长度
        if (buffer.readableBytes() >= ConstantValue.BASE_LENGTH) {
            // 防止socket字节流攻击，客户端传来的数据过大
            if (buffer.readableBytes() > ConstantValue.MAX_DATA_LENGTH) {
                buffer.skipBytes(buffer.readableBytes());
            }
            // 记录包头开始的index
            int beginReader;
            while (true) {
                // 获取包头开始的index
                beginReader = buffer.readerIndex();
                // 标记包头开始的index
                buffer.markReaderIndex();
                // 读到了协议的开始标志，结束while循环
                if (buffer.readInt() == ConstantValue.PROTOCOL_START) {
                    break;
                }

                // 未读到包头，略过一个字节
                // 每次略过，一个字节，去读取，包头信息的开始标记
                buffer.resetReaderIndex();
                buffer.readByte();

                // 当略过，一个字节之后，
                // 数据包的长度，又变得不满足
                // 此时，应该结束。等待后面的数据到达
                if (buffer.readableBytes() < ConstantValue.BASE_LENGTH) {
                    return;
                }
            }

            // 消息的长度
            int length = buffer.readInt();
            // 判断请求数据包数据是否到齐
            if (buffer.readableBytes() < length) {
                // 还原读指针
                buffer.readerIndex(beginReader);
                return;
            }

            // 读取data数据
            byte[] data = new byte[length];
            buffer.readBytes(data);

            RequestMessageBean requestMessageBean = new RequestMessageBean();
            requestMessageBean.setContentLength(data.length);
            requestMessageBean.setContent(data);

            out.add(requestMessageBean);

            System.out.println("解码消息："+ new Gson().toJson(requestMessageBean));
        }
    }
}
