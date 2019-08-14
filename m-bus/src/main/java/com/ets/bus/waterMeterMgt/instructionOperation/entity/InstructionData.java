package com.ets.bus.waterMeterMgt.instructionOperation.entity;

import java.util.Date;

/**
 * @author 宋晨
 * @create 2019/4/19
 * 指令返回数据域
 */
public class InstructionData {

    /**
     * 房间水表信息表ID
     */
    private String waterMeterInfoId;

    /**
     * 集中器编号
     */
    private String deviceId;

    /**
     * 指令记录ID
     */
    private String operationId;

    /**
     * 水表编号
     */
    private String waterMeterNum;

    /**
     * 是否成功 00表示成功，其他表示失败具体见错误代码
     */
    private Integer successStatus;

    /**
     * 00表示获取正常
     * 31表示获取超时
     */
    private byte rtn;

    /**
     * 时间
     */
    private Date time;

    /**
     * 0表示阀门关闭，
     * 1表示阀门开放状态
     * 2表示阀门强制开放状态
     * 3表示阀门强制关闭状态示）
     */
    private byte valveStatus;

    /**
     * 补水量（吨）
     */
    private Double supplementWater;

    /**
     * 购水量 (如果购水量=现金购水+一卡通购水此字段可弃用)
     */
    private Double buyWaterTotal;

    /**
     * 剩余水量=补水量+ 购水量
     */
    private Double surplusWater;

    /**
     * 已透支水量（吨）/最大透支水量
     */
    private Double overWater;

    /**
     * 累积用水量（吨）
     */
    private Double cumulateWater;

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
    private byte powerStatus;

    /**
     * 磁攻击次数，该值不大于3
     */
    private byte attackNum;

    /**
     * 操作状态（0:未下发，1:下发成功，2:下发失败，3：计数错误，4:购水量超出,5:下发超时）
     */
    private Integer currentStatus;

    /**
     * 报警水量
     */
    private Double warnWater;

    /**
     * 关阀水量（吨）
     */
    private Double valveWater;

    /**
     * 最大囤积量
     */
    private Double hoardWater;

    /**
     * 购水单价
     */
    private Double buyMoney;

    /**
     * 退水单价
     */
    private Double returnMoney;

    /**
     * 退购水量（吨）
     */
    private Double returnWater;

    /**
     * 补水周期
     */
    private Integer addCycle;

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

    public Double getOverWater() {
        return overWater;
    }

    public void setOverWater(Double overWater) {
        this.overWater = overWater;
    }

    public Double getCumulateWater() {
        return cumulateWater;
    }

    public void setCumulateWater(Double cumulateWater) {
        this.cumulateWater = cumulateWater;
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

    public String getWaterMeterNum() {
        return waterMeterNum;
    }

    public void setWaterMeterNum(String waterMeterNum) {
        this.waterMeterNum = waterMeterNum;
    }

    public Integer getSuccessStatus() {
        return successStatus;
    }

    public void setSuccessStatus(Integer successStatus) {
        this.successStatus = successStatus;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getWaterMeterInfoId() {
        return waterMeterInfoId;
    }

    public void setWaterMeterInfoId(String waterMeterInfoId) {
        this.waterMeterInfoId = waterMeterInfoId;
    }

    public byte getRtn() {
        return rtn;
    }

    public void setRtn(byte rtn) {
        this.rtn = rtn;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public byte getValveStatus() {
        return valveStatus;
    }

    public void setValveStatus(byte valveStatus) {
        this.valveStatus = valveStatus;
    }

    public byte getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(byte powerStatus) {
        this.powerStatus = powerStatus;
    }

    public byte getAttackNum() {
        return attackNum;
    }

    public void setAttackNum(byte attackNum) {
        this.attackNum = attackNum;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public Double getSurplusWater() {
        return surplusWater;
    }

    public void setSurplusWater(Double surplusWater) {
        this.surplusWater = surplusWater;
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

    public Double getHoardWater() {
        return hoardWater;
    }

    public void setHoardWater(Double hoardWater) {
        this.hoardWater = hoardWater;
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

    public Double getReturnWater() {
        return returnWater;
    }

    public void setReturnWater(Double returnWater) {
        this.returnWater = returnWater;
    }
}
