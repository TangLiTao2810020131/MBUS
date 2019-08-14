package com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity;

/**
 * @author 宋晨
 * @create 2019/4/19
 * 获取水表信息记录表
 */
public class CollectRecordVo  extends RecordBaseVo {

    /**
     * 补水量（吨）
     */
    private Double supplementWater;

    /**
     * 购水量 (如果购水量=现金购水+一卡通购水此字段可弃用)
     */
    private Double buyWaterTotal;

    /**
     * 现金购水量（吨）
     */
    private Double cashBuyWater;

    /**
     * 持卡购水量（吨）
     */
    private Double cardBuyWater;

    /**
     * 退水量（吨）
     */
    private Double returnWater;

    /**
     * 剩余水量（吨）剩余购水+剩余补水
     */
    private Double surplusWater;

    /**
     * 已用水量（吨）
     */
    private Double userWater;

    /**
     * 下发计数
     */
    private Integer addNum;

    /**
     * 电池状态（0：正常，1：亏电）
     */
    private Integer powerStatus;

    /**
     * 磁攻击次数，该值不大于3
     */
    private Integer attackNum;

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

    public Integer getAddNum() {
        return addNum;
    }

    public void setAddNum(Integer addNum) {
        this.addNum = addNum;
    }

    public Integer getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(Integer powerStatus) {
        this.powerStatus = powerStatus;
    }

    public Integer getAttackNum() {
        return attackNum;
    }

    public void setAttackNum(Integer attackNum) {
        this.attackNum = attackNum;
    }
}
