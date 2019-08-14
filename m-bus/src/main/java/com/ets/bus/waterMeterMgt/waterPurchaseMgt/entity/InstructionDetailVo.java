package com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity;

import java.util.Date;

/**
 * @author 宋晨
 * @create 2019/4/8
 * 指令明细表信息
 */
public class InstructionDetailVo {

    /**
     * id
     */
    private String id;

    /**
     * 房间水表信息表ID
     */
    private String waterMeterInfoId;

    /**
     * 记录表ID
     */
    private String recordId;

    /**
     * 类型（0:购水，1:退水，2:补水，3:换房）
     */
    private Integer type;

    /**
     * 操作状态（0:未下发，1:下发成功，2:下发失败）
     */
    private Integer currentStatus;

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
     * 操作指令表ID
     */
    private String operationId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 水表编号
     */
    private String waterMeterNum;

    /**
     * 集中器编号
     */
    private String concentratorNum;

    public String getWaterMeterNum() {
        return waterMeterNum;
    }

    public void setWaterMeterNum(String waterMeterNum) {
        this.waterMeterNum = waterMeterNum;
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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
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

    public String getConcentratorNum() {
        return concentratorNum;
    }

    public void setConcentratorNum(String concentratorNum) {
        this.concentratorNum = concentratorNum;
    }
}
