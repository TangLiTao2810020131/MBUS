package com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity;


/**
 * @author 宋晨
 * @create 2019/4/1
 * 购水信息Vo
 */
public class BuyRecordVo extends RecordBaseVo {

    /**
     * 流水号
     */
    private String serialNumber;

    /**
     * 付款人ID
     */
    private String payerId;

    /**
     * 付款人姓名
     */
    private String payerName;

    /**
     * 购水量（吨）
     */
    private Double buyWater;

    /**
     * 购水金额
     */
    private Double buyMoney;

    /**
     * 实际收款
     */
    private Double actualMoney;

    /**
     * 找零
     */
    private Double returnMoney;

    /**
     * 类型（0:现金，1:一卡通）
     */
    private Integer type;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public Double getBuyWater() {
        return buyWater;
    }

    public void setBuyWater(Double buyWater) {
        this.buyWater = buyWater;
    }

    public Double getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(Double buyMoney) {
        this.buyMoney = buyMoney;
    }

    public Double getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(Double actualMoney) {
        this.actualMoney = actualMoney;
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
}
