package com.ets.bus.reportQuery.entity.report;

import java.util.Date;

/**
 * @author 宋晨
 * @create 2019/4/4
 * 换表记录
 */
public class ReplaceRecordVo {

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
     * 集中器IP
     */
    private String concentratorIp;

    /**
     * 楼栋id
     */
    private String buildId;

    /**
     * 楼栋名
     */
    private String buildName;

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
     * 房间类型Name
     */
    private String roomTypeName;

    /**
     * 新水表ID
     */
    private String newWaterMeterId;

    /**
     * 新水表编号
     */
    private String newWaterMeterNum;

    /**
     * 旧水表ID
     */
    private String oldWaterMeterId;

    /**
     * 旧水表编号
     */
    private String oldWaterMeterNum;

    /**
     * 换表费用
     */
    private Double replaceMoney;

    /**
     * 操作状态（0:未下发，1:下发成功，2:下发失败）
     */
    private Integer currentStatus;

    /**
     * 操作状态名称
     */
    private String currentStatusName;

    /**
     * 换表次数
     */
    private Integer replaceWaterNum;

    /**
     * 水表类型
     */
    private String waterType;

    /**
     * 开户日期
     */
    private Date openTime;

    /**
     * 换表日期
     */
    private Date replaceTime;

    /**
     * 操作员
     *
     */
    private String operationUser;

    /**
     *
     * 创建时间
     */
    private Date createTime;

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

    public String getConcentratorIp() {
        return concentratorIp;
    }

    public void setConcentratorIp(String concentratorIp) {
        this.concentratorIp = concentratorIp;
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

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
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

    public String getWaterType() {
        return waterType;
    }

    public void setWaterType(String waterType) {
        this.waterType = waterType;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getReplaceTime() {
        return replaceTime;
    }

    public void setReplaceTime(Date replaceTime) {
        this.replaceTime = replaceTime;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }


    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getCurrentStatusName() {
        return currentStatusName;
    }

    public void setCurrentStatusName(String currentStatusName) {
        this.currentStatusName = currentStatusName;
    }

    public String getNewWaterMeterNum() {
        return newWaterMeterNum;
    }

    public void setNewWaterMeterNum(String newWaterMeterNum) {
        this.newWaterMeterNum = newWaterMeterNum;
    }

    public String getOldWaterMeterNum() {
        return oldWaterMeterNum;
    }

    public void setOldWaterMeterNum(String oldWaterMeterNum) {
        this.oldWaterMeterNum = oldWaterMeterNum;
    }
}
