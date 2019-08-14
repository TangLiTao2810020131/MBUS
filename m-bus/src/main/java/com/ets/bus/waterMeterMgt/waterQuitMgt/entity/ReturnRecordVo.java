package com.ets.bus.waterMeterMgt.waterQuitMgt.entity;

import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.RecordBaseVo;


/**
 * @author 宋晨
 * @create 2019/4/2
 * 退水信息Vo
 */
public class ReturnRecordVo  extends RecordBaseVo {

    /**
     * 流水号
     */
    private String serialNumber;

    /**
     * 退购水量（吨）
     */
    private Double returnWater;

    /**
     * 退补水量（吨）
     */
    private Double returnBuWater;

    /**
     * 退水金额（元）
     */
    private Double returnMoney;

    /**
     * 类型（0:退水，1:换房退水
     */
    private Integer type;

    /**
     * 是否红冲（0：否，1：是）
     */
    private Integer redrush;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Double getReturnWater() {
        return returnWater;
    }

    public void setReturnWater(Double returnWater) {
        this.returnWater = returnWater;
    }

    public Double getReturnBuWater() {
        return returnBuWater;
    }

    public void setReturnBuWater(Double returnBuWater) {
        this.returnBuWater = returnBuWater;
    }

    public Double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public Integer getRedrush() {
        return redrush;
    }

    @Override
    public void setRedrush(Integer redrush) {
        this.redrush = redrush;
    }
}
