package com.ets.bus.socket.server;

/**
 * @author xuqiang
 * @date 2019/7/19 10:22
 */
public class TcpConstantValue {
    public static final int  HEAD_DATA = 0x0E;
    public static final byte PROTOCOL_SEND_START = 0x7E; //发送起始位 7EH
    public static final Short PROTOCOL_SEND_END = 0x0D0A; //发送结束位 0D0AH
    public static final Short PROTOCOL_REC_START = 0x9E; //接收起始位 9EH
    public static final byte PROTOCOL_REC_END = 0x5E; //接收结束位 5EH

    /**
     * 起始位	1个字节
     * 功能码1个字节
     * 设备编号8个字节
     * 包长2个字节
     * 校验位1个字节
     * 结束位1个字节
     */
    public static final int BASE_LENGTH = 14; //数据最小长度
    public static final int MIN_LENGTH = 13; //数据最小长度
    //    public static final int MAX_DATA_LENGTH = 2062; //最大长度=最小长度+数据域长度2048字节
    public static final int MAX_DATA_LENGTH = 999999999; //最大长度=最小长度+数据域长度2048字节
    public static final int DEVICEID_DATA_LENGTH = 4; //最大长度=最小长度+数据域长度2048字节

    public static final byte FUNCODE_LIVE = 0x01; //功能码 心跳包
    public static final byte FUNCODE_CON_DATE = 0x10; //功能码 获取集中器时间
    public static final byte FUNCODE_COM_TIME = 0x11; //功能码 校时
    public static final byte FUNCODE_CHA_ADDRESS = 0x12; //功能码 修改集中器服务地址
    public static final byte FUNCODE_CON_PARAM = 0x13; //功能码 获取集中器参数
    public static final byte FUNCODE_SET_PARAM = 0x14; //功能码 设置集中器参数
    public static final byte FUNCODE_WATER_METER = 0x15; //功能码 获取水表状态（包括剩余水量）
    public static final byte FUNCODE_WATER_RESET = 0x16; //功能码 水表清零
    public static final byte FUNCODE_WATER_RESTART = 0x17; //功能码 重启集中器
    public static final byte FUNCODE_ADD_WATER = 0x20; //功能码 购水、补水
    public static final byte FUNCODE_RETURN_WATER = 0x21; //功能码 退水
    public static final byte FUNCODE_UPDATE_WATER = 0x22; //功能码 更新水表信息
    public static final byte FUNCODE_GET_WATER_PARAM = 0x23; //功能码 获取水表参数
    public static final byte FUNCODE_OPEN = 0x24; //功能码 开关阀
    public static final byte FUNCODE_WARN = 0x32; //功能码 终端报警

    public static final byte RETURN_RNT_SUCCESS = 0x00; //购水补水返回状态码 正常
    public static final byte RETURN_RNT_NUMERROR = 0x11; //购水补水返回状态码 计数错误
    public static final byte RETURN_RNT_OVER = 0x12; //购水补水返回状态码 购水量超出
    public static final byte RETURN_RNT_OVERTIME = 0x31; //购水补水返回状态码 下发超时

    public static final byte OPEN_TYPE_0 = 0x00; //一般开阀
    public static final byte OPEN_TYPE_1 = 0x01; //一般关阀
    public static final byte OPEN_TYPE_2 = 0x02; //强制开阀
    public static final byte OPEN_TYPE_3 = 0x03; //强制关阀

    public static final String  REDIS_KEY_CHANNEL = "CHANNEL_"; //redis channel key

}
