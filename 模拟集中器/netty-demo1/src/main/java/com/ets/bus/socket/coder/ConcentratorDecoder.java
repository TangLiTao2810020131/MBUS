package com.ets.bus.socket.coder;

import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.server.ConstantValue;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author 宋晨
 * @create 2019/4/12
 * 集中器协议数据解码器
 */
public class ConcentratorDecoder extends ByteToMessageDecoder {

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buffer, List<Object> out) throws Exception {
        // 可读长度必须大于基本长度
        if (buffer.readableBytes() >= ConstantValue.BASE_LENGTH) {
            // 防止socket字节流攻击，客户端传来的数据过大
            if (buffer.readableBytes() > ConstantValue.MAX_DATA_LENGTH) {
                buffer.skipBytes(buffer.readableBytes());
            }
            //记录包头开始的index
            int beginReader;
            while (true) {
                // 获取包头开始的index
                beginReader = buffer.readerIndex();
                // 标记包头开始的index
                buffer.markReaderIndex();
                // 读到了协议的开始标志，结束while循环
                if (buffer.readByte() == ConstantValue.PROTOCOL_START) {
                    break;
                }
                // 未读到包头，略过一个字节，每次略过，一个字节，去读取，包头信息的开始标记
                buffer.resetReaderIndex();
                buffer.readByte();
                // 当略过，一个字节之后，数据包的长度，又变得不满足，此时，应该结束。等待后面的数据到达
                if (buffer.readableBytes() < ConstantValue.BASE_LENGTH) {
                    return;
                }
            }

            //功能码
            byte functionCode = buffer.readByte();
            //设备编码
            byte[] deviceidData = new byte[ConstantValue.DEVICEID_DATA_LENGTH];
            buffer.readBytes(deviceidData);
            //包长
            Short contentLength = buffer.readShort();
            // 判断请求数据包数据是否到齐
            if (buffer.readableBytes() < contentLength- ConstantValue.BASE_LENGTH) {
                //还原读指针
                buffer.readerIndex(beginReader);
                return;
            }
            //数据域  长度=包长-非数据域内容
            byte[] content = new byte[contentLength- ConstantValue.BASE_LENGTH];
            buffer.readBytes(content);
            //校验位
            byte check = buffer.readByte();
            //校验和
            int sum = ConstantValue.PROTOCOL_START + functionCode + contentLength;
            for (byte item:deviceidData) {
                sum += item;
            }
            for (byte item:content) {
                sum += item;
            }
            byte[] sumArr = intToByteArray(sum);
            //校验
            if(sumArr[3] != check){
                return;
            }

            ConcentratorProtocolBean data = new ConcentratorProtocolBean();
            data.setFunctionCode(functionCode);
            data.setDeviceId(deviceidData);
            data.setContentLength(contentLength);
            data.setContent(content);
            data.setCheck(check);
            out.add(data);
        }
    }

    public byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }
}
