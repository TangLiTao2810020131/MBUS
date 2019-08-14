package com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity;

import java.util.Date;

/**
 * @author 宋晨
 * @create 2019/4/8
 * 操作指令表信息
 */
public class OperationInstructionVo {

    /**
     * id
     */
    private String id;

    /**
     * 房间水表信息表ID
     */
    private String waterMeterInfoId;

    /**
     * 类型（0:购水，1:退水，2:补水，3:换房，4：更换水表，5：校时,6：仅设置集中器参数，7：设置集中器及水表参数，8：获取集中器下房间信息）
     */
    private Integer type;

    /**
     * 类型（0:购水，1:退水，2:补水，3:换房，4：更换水表，5：校时,6：仅设置集中器参数，7：设置集中器及水表参数，8：获取集中器下房间信息）
     */
    private String typeName;

    /**
     * 操作状态（0:未下发，1:下发成功，2:下发失败）
     */
    private Integer currentStatus;

    /**
     * 操作状态（0:未下发，1:下发成功，2:下发失败）
     */
    private String currentStatusName;

    /**
     * 指令json
     */
    private String instructionDetail;

    /**
     * 返回结果
     */
    private String result;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作人ID
     */
    private String userId;

    /**
     * 操作人名称
     */
    private String userName;

    /**
     * 指令编号
     */
    private Integer instructionNum;

    /**
     * 采集表ID
     */
    private String collectId;

    /**
     * 采集表IP
     */
    private String collectIp;

    /**
     * 公寓ID
     */
    private String apartmentId;

    /**
     * 公寓名称
     */
    private String apartmentName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 集中器ID
     */
    private String concentratorId;

    /**
     * 集中器编号
     */
    private String concentratorNum;

    /**
     * 水表编号
     */
    private String waterMeterNum;

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
     * 已透支水量（吨）/最大透支水量
     */
    private Double overWater;

    public String getTypeName() {
        return typeName;
    }

    public String getCurrentStatusName() {
        return currentStatusName;
    }

    public void setCurrentStatusName(String currentStatusName) {
        this.currentStatusName = currentStatusName;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getWaterMeterNum() {
        return waterMeterNum;
    }

    public void setWaterMeterNum(String waterMeterNum) {
        this.waterMeterNum = waterMeterNum;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWaterMeterInfoId() {
        return waterMeterInfoId;
    }

    public void setWaterMeterInfoId(String waterMeterInfoId) {
        this.waterMeterInfoId = waterMeterInfoId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getInstructionDetail() {
        return instructionDetail;
    }

    public void setInstructionDetail(String instructionDetail) {
        this.instructionDetail = instructionDetail;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getInstructionNum() {
        return instructionNum;
    }

    public void setInstructionNum(Integer instructionNum) {
        this.instructionNum = instructionNum;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getCollectIp() {
        return collectIp;
    }

    public void setCollectIp(String collectIp) {
        this.collectIp = collectIp;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getConcentratorId() {
        return concentratorId;
    }

    public void setConcentratorId(String concentratorId) {
        this.concentratorId = concentratorId;
    }

    public String getConcentratorNum() {
        return concentratorNum;
    }

    public void setConcentratorNum(String concentratorNum) {
        this.concentratorNum = concentratorNum;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Double getOverWater() {
        return overWater;
    }

    public void setOverWater(Double overWater) {
        this.overWater = overWater;
    }
}
