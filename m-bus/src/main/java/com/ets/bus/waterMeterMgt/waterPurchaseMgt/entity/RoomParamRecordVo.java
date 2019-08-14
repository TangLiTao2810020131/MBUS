package com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity;


/**
 * @author 宋晨
 * @create 2019/4/22
 * 下发房间类型参数记录表
 */
public class RoomParamRecordVo extends RecordBaseVo {

    /**
     * 报警水量
     */
    private Double warnWater;

    /**
     * 关阀水量（吨）
     */
    private Double valveWater;

    /**
     * 最大透支水量
     */
    private Double overWater;

    /**
     * 购水单价
     */
    private Double buyMoney;

    /**
     * 退水单价
     */
    private Double returnMoney;

    /**
     * 软件补水周期（0：每日补一次，1：每月补一次，2：不限补水周期）
     */
    private Integer addCycle;

    /**
     * 最大囤积量
     */
    private Double hoardWater;

    public Double getWarnWater() {
        return warnWater;
    }

    public void setWarnWater(Double warnWater) {
        this.warnWater = warnWater;
    }

    public Double getValveWater() {
        return valveWater;
    }

    public void setValveWater(Double valveWater) {
        this.valveWater = valveWater;
    }

    public Double getOverWater() {
        return overWater;
    }

    public void setOverWater(Double overWater) {
        this.overWater = overWater;
    }

    public Double getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(Double buyMoney) {
        this.buyMoney = buyMoney;
    }

    public Double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Integer getAddCycle() {
        return addCycle;
    }

    public void setAddCycle(Integer addCycle) {
        this.addCycle = addCycle;
    }

    public Double getHoardWater() {
        return hoardWater;
    }

    public void setHoardWater(Double hoardWater) {
        this.hoardWater = hoardWater;
    }
}
