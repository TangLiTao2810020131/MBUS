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

    /**
     * 16进制起始位置
     */
    private byte head = ConstantValue.PROTOCOL_START;

    /**
     * 功能码
     */
    private byte functionCode;

    /**
     * 设备编码：设备唯一编码，HEX码表示，长度8个字节由8个16进制整数组成。
     */
    private byte[] deviceId;

    /**
     * 包长：整个数据包的长度（即从起始位到结束位的长度），两个字节,HEX码表示。
     */
    private Short contentLength;

    /**
     * 数据域：数据信息，最长2048字节。，其结构随控制码不同而改变。
     */
    private byte[] content;

    /**
     * 校验位：1个字节从起始位开始到校验位之前的所有字节进行算术累加，不计超出FFH的溢出值结束
     */
    private byte check;

    /**
     * 结束位
     */
    private byte end = ConstantValue.PROTOCOL_END;

    public byte getHead() {
        return head;
    }

    public void setHead(byte head) {
        this.head = head;
    }

    public byte getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(byte functionCode) {
        this.functionCode = functionCode;
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

    public byte getCheck() {
        return check;
    }

    public void setCheck(byte check) {
        this.check = check;
    }

    public byte getEnd() {
        return end;
    }

    public void setEnd(byte end) {
        this.end = end;
    }
}
