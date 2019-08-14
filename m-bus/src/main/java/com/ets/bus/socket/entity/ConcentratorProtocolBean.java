package com.ets.bus.socket.entity;


import com.ets.bus.socket.server.ConstantValue;

/**
 * @author 宋晨
 * @create 2019/4/12
 * 水表集中器通讯协议封装类
 */
public class ConcentratorProtocolBean {

    public ConcentratorProtocolBean(){

    }

    public ConcentratorProtocolBean(byte functionCode){
        this.functionCode = functionCode;
    }

    public ConcentratorProtocolBean(byte type, byte functionCode) {
        this.type = type;
        this.functionCode = functionCode;
    }

    /**
     * 16进制起始位置
     */
    private byte head = ConstantValue.PROTOCOL_SEND_START;

    /**
     * 版本号
     */
    private byte version = ConstantValue.VERSION;

    /**
     * 设备编码：设备唯一编码，HEX码表示，长度8个字节由8个16进制整数组成。
     */
    private byte[] deviceId;

    /**
     * 设备类型。
     */
    private byte type = ConstantValue.BASE_TYPE;

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
    private Short end = ConstantValue.PROTOCOL_SEND_END;

    public byte getHead() {
        return head;
    }

    public void setHead(byte head) {
        this.head = head;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte[] getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(byte[] deviceId) {
        this.deviceId = deviceId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(byte functionCode) {
        this.functionCode = functionCode;
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

    public Short getEnd() {
        return end;
    }

    public void setEnd(Short end) {
        this.end = end;
    }
}
