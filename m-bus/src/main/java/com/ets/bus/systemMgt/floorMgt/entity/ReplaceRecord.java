package com.ets.bus.systemMgt.floorMgt.entity;



import java.util.Date;

//换表记录
public class ReplaceRecord {
    private String id;
    private String water_meter_info_id;//信息表的id
    private String newWaterMeterId;//新水表id
    private String oldWaterMeterId;//旧水表id
    private Double replaceMoney;
    private Integer currentStatus;
    private Integer replaceWaterNum;
    private Integer waterType;
    private String openTime;
    private Date replaceTime;
    private String operationUser;
    private String userId;
    private Date createTime;
    private String remark;//换表原因
    private String operationId;//操作表的Id

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWater_meter_info_id() {
        return water_meter_info_id;
    }

    public void setWater_meter_info_id(String water_meter_info_id) {
        this.water_meter_info_id = water_meter_info_id;
    }

    public String getNewWaterMeterId() {
        return newWaterMeterId;
    }

    public void setNewWaterMeterId(String newWaterMeterId) {
        this.newWaterMeterId = newWaterMeterId;
    }

    public String getOldWaterMeterId() {
        return oldWaterMeterId;
    }

    public void setOldWaterMeterId(String oldWaterMeterId) {
        this.oldWaterMeterId = oldWaterMeterId;
    }

    public Double getReplaceMoney() {
        return replaceMoney;
    }

    public void setReplaceMoney(Double replaceMoney) {
        this.replaceMoney = replaceMoney;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Integer getReplaceWaterNum() {
        return replaceWaterNum;
    }

    public void setReplaceWaterNum(Integer replaceWaterNum) {
        this.replaceWaterNum = replaceWaterNum;
    }

    public Integer getWaterType() {
        return waterType;
    }

    public void setWaterType(Integer waterType) {
        this.waterType = waterType;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public Date getReplaceTime() {
        return replaceTime;
    }

    public void setReplaceTime(Date replaceTime) {
        this.replaceTime = replaceTime;
    }

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
