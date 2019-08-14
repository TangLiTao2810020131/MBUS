package com.ets.bus.socket.test.entity;

/**
 * @author 宋晨
 * @create 2019/4/12
 * 心跳测试bean
 */
public class LiveTestBean {

    /**
     * 设备编码：设备唯一编码，HEX码表示，长度8个字节由8个16进制整数组成。
     */
    private byte[] deviceId={0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08};

    //集中器信号强度
    private byte signalIntensity = 0x06;

    //软件版本
    private byte version = 0x02;

    //发包次数
    private byte pingNum;

    //收包次数
    private byte pongNum;

    //备用字段
    private byte[] spare = {0x00,0x00,0x00,0x00};

    public byte[] getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(byte[] deviceId) {
        this.deviceId = deviceId;
    }

    public byte getSignalIntensity() {
        return signalIntensity;
    }

    public void setSignalIntensity(byte signalIntensity) {
        this.signalIntensity = signalIntensity;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getPingNum() {
        return pingNum;
    }

    public void setPingNum(byte pingNum) {
        this.pingNum = pingNum;
    }

    public byte getPongNum() {
        return pongNum;
    }

    public void setPongNum(byte pongNum) {
        this.pongNum = pongNum;
    }

    public byte[] getSpare() {
        return spare;
    }

    public void setSpare(byte[] spare) {
        this.spare = spare;
    }
}
