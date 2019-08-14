package com.ets.bus.socket.entity;

import java.util.Date;

/**
 * @author 宋晨
 * @create 2019/4/15
 * 补水，购水,退水bean
 */
public class WaterBean {

    /**
     * 集中器编号
     */
    private String deviceId;

    /**
     * 水表编号
     */
    private String waterMeterNum;

    /**
     * 购水量 or 退购水量 0~65535 *0.1吨
     */
    private Integer buyWater;

    /**
     * 补水量 or 退补水量 0~65535 *0.1吨
     */
    private Integer addWater;

    /**
     * 下发计数
     */
    private Integer addNum;

    /**
     * 报警水量 0~65535 *0.1吨
     */
    private Integer warnWater;

    /**
     * 关阀水量 0~65535 *0.1吨
     */
    private Integer valveWater;

    /**
     * 透支水量 0~65535 *0.1吨
     */
    private Integer overWater;

    /**
     * 囤积水量 0~65535 *0.1吨
     */
    private Integer hoardWater;

    /**
     * 水阀操作
     * 1字节16进制数
     * 00表示一般开阀
     * 01表示一般关阀
     * 02表示强制开阀
     * 03表是强制关阀
     */
    private byte openType;

    private byte rnt;

    /**
     * 总用水量
     */
    private Integer totalWater;

    /**
     * 电池
     */
    private byte power;

    /**
     * 磁攻击
     */
    private byte attack;

    public byte getPower() {
        return power;
    }

    public void setPower(byte power) {
        this.power = power;
    }

    public byte getAttack() {
        return attack;
    }

    public void setAttack(byte attack) {
        this.attack = attack;
    }

    public Integer getTotalWater() {
        return totalWater;
    }

    public void setTotalWater(Integer totalWater) {
        this.totalWater = totalWater;
    }

    public byte getRnt() {
        return rnt;
    }

    public void setRnt(byte rnt) {
        this.rnt = rnt;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getWaterMeterNum() {
        return waterMeterNum;
    }

    public void setWaterMeterNum(String waterMeterNum) {
        this.waterMeterNum = waterMeterNum;
    }

    public Integer getBuyWater() {
        return buyWater;
    }

    public void setBuyWater(Integer buyWater) {
        this.buyWater = buyWater;
    }

    public Integer getAddWater() {
        return addWater;
    }

    public void setAddWater(Integer addWater) {
        this.addWater = addWater;
    }

    public Integer getAddNum() {
        return addNum;
    }

    public void setAddNum(Integer addNum) {
        this.addNum = addNum;
    }

    public Integer getWarnWater() {
        return warnWater;
    }

    public void setWarnWater(Integer warnWater) {
        this.warnWater = warnWater;
    }

    public Integer getValveWater() {
        return valveWater;
    }

    public void setValveWater(Integer valveWater) {
        this.valveWater = valveWater;
    }

    public Integer getOverWater() {
        return overWater;
    }

    public void setOverWater(Integer overWater) {
        this.overWater = overWater;
    }

    public Integer getHoardWater() {
        return hoardWater;
    }

    public void setHoardWater(Integer hoardWater) {
        this.hoardWater = hoardWater;
    }

    public byte getOpenType() {
        return openType;
    }

    public void setOpenType(byte openType) {
        this.openType = openType;
    }
}
