package com.ets.bus.socket.entity;


import com.ets.bus.socket.server.ConstantValue;
import com.ets.bus.socket.server.TcpConstantValue;

/**
 * @author xuqiang
 * @date 2019/7/19 10:19
 */
public class TcpProtocolBean {

    public TcpProtocolBean(){}

    public TcpProtocolBean(byte functionCode) {
        this.functionCode = functionCode;
    }

    /**
     * 发送起始位置
     */
    private byte sendHead = TcpConstantValue.PROTOCOL_SEND_START;

    /**
     * 接收起始位置
     */
   // private short recHead ;

    /**
     * 版本号
     */
    private byte version;

    /**
     * 设备编码：设备唯一编码，HEX码表示，长度8个字节由8个16进制整数组成。
     */
    private byte[] deviceId;

    /**
     * 设备类型。
     */
    private byte type;

    /**
     * 功能码
     */
    private byte functionCode;


    /**
     * 数据的长度，两个字节,HEX码表示。
     */
    private Short contentLength;

    /**
     * 数据域：数据信息，最长2048字节。，其结构随控制码不同而改变。
     */
    private byte[] content = new byte[]{};

    /**
     * 校验位：2个字节从起始位开始到校验位之前的所有字节进行算术累加，不计超出FFH的溢出值结束
     */
    private Short check;

    /**
     * 发送结束位
     */
    private Short sendEnd = TcpConstantValue.PROTOCOL_SEND_END;

    /**
     * 接收结束位
     */
    //private byte recEnd = TcpConstantValue.PROTOCOL_REC_END;

    public byte getSendHead() {
        return sendHead;
    }

    public void setSendHead(byte sendHead) {
        this.sendHead = sendHead;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(byte functionCode) {
        this.functionCode = functionCode;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte[] getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(byte[] deviceId) {
        this.deviceId = deviceId;
    }

    public Short getContentLength() {
        return contentLength;
    }

    public void setContentLength(Short contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Short getCheck() {
        return check;
    }

    public void setCheck(Short check) {
        this.check = check;
    }

    public Short getSendEnd() {
        return sendEnd;
    }

    public void setSendEnd(Short sendEnd) {
        this.sendEnd = sendEnd;
    }

}
