package com.ets.bus.reportQuery.entity.report;

import java.util.Date;

public class HistoryCommandVo {
    /**
     * 32位数UUID
     */
    private String id;

    /**
     * 房间水表信息表ID
     */
    private String waterMeterInfoId;

    /**
     * 区域ID
     */
    private String areaId;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 公寓ID
     */
    private String apartmentId;

    /**
     * 公寓名称
     */
    private String apartmentName;

    /**
     * 集中器ID
     */
    private String concentratorId;

    /**
     * 集中器编号
     */
    private String concentratorNum;

    /**
     * 楼层ID
     */
    private String floorId;

    /**
     * 楼层名称
     */
    private String floorName;

    /**
     * 房间ID
     */
    private String roomId;

    /**
     * 房间号
     */
    private String roomNum;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 操作人ID
     */
    private String operatId;

    /**
     * 操作人姓名
     */
    private String operatName;


    /**
     * '类型（0:购水，1:退水，2:补水，3:换房，4：更换水表,5:普通指令,6:清零,7:冲红,8获取水表状态等）'
     */
    private Integer type;
    /**
     *类型名称
     *
     */
    private String typeName;
    /**
     * 操作状态（0:未下发，1:下发成功，2:下发失败）
     */
    private Integer currentStatus;

    /**
     * 操作结果名称
     */
    private String currentStatusName;

    /**
     * 指令json
     *
     */
    private String  instructionDetatil;
    /**
     *返回结果
     *
     */
    private String result;

    /**
     * 指令编号
     *
     */
    private String  instructionNum;

    /**
     * 采集表ID
     *
     */
    private String collectId;
    /**
     * 采集表IP
     *
     */
    private String collectIp;
    /**
     *水表编号
     *
     */
    private String wateMeterNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

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

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
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

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperatId() {
        return operatId;
    }

    public void setOperatId(String operatId) {
        this.operatId = operatId;
    }

    public String getOperatName() {
        return operatName;
    }

    public void setOperatName(String operatName) {
        this.operatName = operatName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getCurrentStatusName() {
        return currentStatusName;
    }

    public void setCurrentStatusName(String currentStatusName) {
        this.currentStatusName = currentStatusName;
    }

    public String getInstructionDetatil() {
        return instructionDetatil;
    }

    public void setInstructionDetatil(String instructionDetatil) {
        this.instructionDetatil = instructionDetatil;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getInstructionNum() {
        return instructionNum;
    }

    public void setInstructionNum(String instructionNum) {
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

    public String getWateMeterNum() {
        return wateMeterNum;
    }

    public void setWateMeterNum(String wateMeterNum) {
        this.wateMeterNum = wateMeterNum;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
