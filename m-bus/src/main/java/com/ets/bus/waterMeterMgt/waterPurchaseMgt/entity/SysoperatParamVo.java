package com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity;

import java.util.Date;

/**
 * @author 宋晨
 * @create 2019/4/8
 * 系统运行参数
 */
public class SysoperatParamVo {

    /**
     * 32位数UUID
     */
    private String id;

    /**
     * 是否删除（0：否，1：是）
     */
    private Integer delStatus;

    /**
     * 是否生效（0：否，1：是  有在其他相应功能中手动下发或更新，才能被运用和生效）
     */
    private Integer effectStatus;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 参数生效时间
     */
    private Date effectTime;

    /**
     * 最大囤积量
     */
    private Double hoardWater;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getEffectStatus() {
        return effectStatus;
    }

    public void setEffectStatus(Integer effectStatus) {
        this.effectStatus = effectStatus;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public Double getHoardWater() {
        return hoardWater;
    }

    public void setHoardWater(Double hoardWater) {
        this.hoardWater = hoardWater;
    }
}
