package com.ets.bus.reportQuery.entity.report;

import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.RecordBaseVo;

import java.util.Date;

/**
 * @author 宋晨
 * @create 2019/4/4
 * 红冲记录
 */
public class RedRushVo extends RecordBaseVo {

    /**
     * 流水号
     */
    private String serialNumber;

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
     * 集中器IP
     */
    private String concentratorIp;

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
     * 操作结果名称
     */
    private String currentStatusName;

    /**
     * 冲红水量
     */
    private Double redrushWater;

    /**
     * 冲红金额
     */
    private Double redrushMoney;

    /**
     * 记录表ID
     */
    private String recordId;

    /**
     * 类型（0:补水，1:退水）
     */
    private Integer type;

    /**
     * 冲红类型名称
     *
     */
    private String typeName;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public String getCurrentStatusName() {
        return currentStatusName;
    }

    public void setCurrentStatusName(String currentStatusName) {
        this.currentStatusName = currentStatusName;
    }

    public Double getRedrushWater() {
        return redrushWater;
    }

    public void setRedrushWater(Double redrushWater) {
        this.redrushWater = redrushWater;
    }

    public Double getRedrushMoney() {
        return redrushMoney;
    }

    public void setRedrushMoney(Double redrushMoney) {
        this.redrushMoney = redrushMoney;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
