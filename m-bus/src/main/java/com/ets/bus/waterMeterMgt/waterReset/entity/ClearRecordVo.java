package com.ets.bus.waterMeterMgt.waterReset.entity;

import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.RecordBaseVo;



/**
 * @author 宋晨
 * @create 2019/4/9
 * 清零记录表
 */
public class ClearRecordVo extends RecordBaseVo {


    /**
     * 记录类型（0:初始化，1:清零，2:按导入清零）
     */
    private Integer type;

    /**
     * 清零前补水量（吨）
     */
    private Double supplementWater;

    /**
     * 清零前购水量 (如果购水量=现金购水+一卡通购水此字段可弃用)
     */
    private Double buyWaterTotal;

    /**
     * 清零前现金购水量（吨）
     */
    private Double cashBuyWater;

    /**
     * 清零前持卡购水量（吨）
     */
    private Double cardBuyWater;

    /**
     * 清零前退水量（吨）
     */
    private Double returnWater;

    /**
     * 清零前剩余水量（吨）
     */
    private Double surplusWater;

    /**
     * 清零前已用水量（吨）
     */
    private Double userWater;

    /**
     * 清零前累积用水量（吨）
     */
    private Double cumulateWater;

    /**
     * 清零前关阀水量（吨）
     */
    private Double closeValveWater;

    /**
     * 操作金额
     */
    private Double operatMoney;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getSupplementWater() {
        return supplementWater;
    }

    public void setSupplementWater(Double supplementWater) {
        this.supplementWater = supplementWater;
    }

    public Double getBuyWaterTotal() {
        return buyWaterTotal;
    }

    public void setBuyWaterTotal(Double buyWaterTotal) {
        this.buyWaterTotal = buyWaterTotal;
    }

    public Double getCashBuyWater() {
        return cashBuyWater;
    }

    public void setCashBuyWater(Double cashBuyWater) {
        this.cashBuyWater = cashBuyWater;
    }

    public Double getCardBuyWater() {
        return cardBuyWater;
    }

    public void setCardBuyWater(Double cardBuyWater) {
        this.cardBuyWater = cardBuyWater;
    }

    public Double getReturnWater() {
        return returnWater;
    }

    public void setReturnWater(Double returnWater) {
        this.returnWater = returnWater;
    }

    public Double getSurplusWater() {
        return surplusWater;
    }

    public void setSurplusWater(Double surplusWater) {
        this.surplusWater = surplusWater;
    }

    public Double getUserWater() {
        return userWater;
    }

    public void setUserWater(Double userWater) {
        this.userWater = userWater;
    }

    public Double getCumulateWater() {
        return cumulateWater;
    }

    public void setCumulateWater(Double cumulateWater) {
        this.cumulateWater = cumulateWater;
    }

    public Double getCloseValveWater() {
        return closeValveWater;
    }

    public void setCloseValveWater(Double closeValveWater) {
        this.closeValveWater = closeValveWater;
    }

    public Double getOperatMoney() {
        return operatMoney;
    }

    public void setOperatMoney(Double operatMoney) {
        this.operatMoney = operatMoney;
    }
}
