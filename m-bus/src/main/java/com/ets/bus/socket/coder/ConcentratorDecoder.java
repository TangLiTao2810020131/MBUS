package com.ets.bus.socket.coder;

import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.server.ConstantValue;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author 宋晨
 * @create 2019/4/12
 * 集中器协议数据解码器
 */

@SuppressWarnings("all")
public class ConcentratorDecoder extends ByteToMessageDecoder {

    private static final Logger logger = Logger.getLogger(ConcentratorDecoder.class);

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buffer, List<Object> out) throws Exception {
        System.out.println("=================数据解码开始============");
        try {
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
                    //byte不能表示9e需测试能不能读到头信息,所以用读取无符号数 readUnsignedByte
                    short start = buffer.readUnsignedByte();
                    if (start == ConstantValue.PROTOCOL_START) {
                        break;
                    }
                    // 未读到包头，略过一个字节，每次略过，一个字节，去读取，包头信息的开始标记
                    buffer.resetReaderIndex();
                    //同上 readUnsignedByte()
                    buffer.readUnsignedByte();
                    // 当略过，一个字节之后，数据包的长度，又变得不满足，此时，应该结束。等待后面的数据到达
                    if (buffer.readableBytes() < ConstantValue.BASE_LENGTH) {
                        return;
                    }
                }
                //版本号
                byte version = buffer.readByte();

                //设备编码
                byte[] deviceidData = new byte[ConstantValue.DEVICEID_DATA_LENGTH];
                buffer.readBytes(deviceidData);

                //设备类型
                byte type = buffer.readByte();
                //功能码
                byte functionCode = buffer.readByte();

                //数据长度
                Short contentLength = buffer.readShort();
                // 判断请求数据包数据是否到齐
                if (buffer.readableBytes() < contentLength) {
                    //还原读指针
                    buffer.readerIndex(beginReader);
                    return;
                }
                //数据域  长度=数据的长度
                byte[] content = new byte[contentLength];
                buffer.readBytes(content);
                //校验位
                Short check = buffer.readShort();
                byte end = buffer.readByte();
                //判断数据是否到齐
                if(end == ConstantValue.PROTOCOL_END){
                    //校验和
                    int sum = version + functionCode + contentLength + type;
                    for (byte item : deviceidData) {
                        sum += item;
                    }
                    for (byte item : content) {
                        sum += item;
                    }

                    //校验
                    if((short)sum != check){
                        //丢弃
                        out.add(null);
                    }else{
                        ConcentratorProtocolBean data = new ConcentratorProtocolBean();
                        data.setHead(ConstantValue.PROTOCOL_START);
                        data.setFunctionCode(functionCode);
                        data.setDeviceId(deviceidData);
                        data.setContentLength(contentLength);
                        data.setContent(content);
                        data.setCheck(check);
                        data.setType(type);
                        data.setVersion(version);
                        data.setEnd(ConstantValue.PROTOCOL_SEND_END);
                        out.add(data);
                    }
                }
            }else if(buffer.readableBytes()==2){

                //测试数据长度不够时候的心跳包
                short data = buffer.readShort();
                out.add(data);
            }
        } catch (Exception e) {
            logger.error("解码器报错！", e);
        }
    }
}
