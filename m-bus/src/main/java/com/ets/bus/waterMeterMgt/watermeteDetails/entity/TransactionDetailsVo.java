package com.ets.bus.waterMeterMgt.watermeteDetails.entity;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * @author 宋晨
 * @create 2019/3/28
 * 交易明细表
 */
public class TransactionDetailsVo {

    /**
     * 32位数UUID
     */
    private String id;

    /**
     * 房间水表信息表ID
     */
    private String waterMeterInfoId;

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
     * 集中器ID
     */
    private String concentratorId;

    /**
     * 集中器IP
     */
    private String concentratorIp;

    /**
     * 栋ID
     */
    private String buildId;

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
     * 模块状态名称（0：未知，1：正常）
     */
    private String moduleStatusName;

    /**
     * 交易类型（现金购水,一卡通购水,房间补水,楼层补水,按导入补水，退水，水量清零,按导入水量清零,换房补水,换房退水,交易冲红）
     */
    private String transacType;

    /**
     * 操作金额
     */
    private Double operatMoney;

    /**
     * 操作时间
     */
    private Date operatTime;

    /**
     * 操作水量
     */
    private Double operatWater;

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
     * 操作状态（0:未下发，1:下发成功，2:下发失败）
     */
    private Integer currentStatus;

    /**
     * 操作结果名称
     */
    private String currentStatusName;

    /**
     * 是否红冲（0：否，1：是）
     */
    private Integer hasRedrush;

    /**
     * 是否红冲（0：否，1：是）
     */
    private String hasRedrushName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 开始时间
     */
    @Expose(serialize = false, deserialize = false)
    private Date startTime;

    /**
     * 结束时间
     */
    @Expose(serialize = false, deserialize = false)
    private Date endTime;

    /**
     * 页数
     */
    @Expose(serialize = false, deserialize = false)
    private Integer page;

    /**
     * 条数
     */
    @Expose(serialize = false, deserialize = false)
    private Integer limit;

    /**
     * 页数
     */
    @Expose(serialize = false, deserialize = false)
    private Integer realPage;

    /**
     * 条数
     */
    @Expose(serialize = false, deserialize = false)
    private Integer realLimit;

    /**
     * 错误消息
     */
    @Expose(serialize = false, deserialize = false)
    private String errMsg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getTransacType() {
        return transacType;
    }

    public void setTransacType(String transacType) {
        this.transacType = transacType;
    }

    public Double getOperatMoney() {
        return operatMoney;
    }

    public void setOperatMoney(Double operatMoney) {
        this.operatMoney = operatMoney;
    }

    public Date getOperatTime() {
        return operatTime;
    }

    public void setOperatTime(Date operatTime) {
        this.operatTime = operatTime;
    }

    public Double getOperatWater() {
        return operatWater;
    }

    public void setOperatWater(Double operatWater) {
        this.operatWater = operatWater;
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

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Integer getHasRedrush() {
        return hasRedrush;
    }

    public void setHasRedrush(Integer hasRedrush) {
        this.hasRedrush = hasRedrush;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCurrentStatusName() {
        return currentStatusName;
    }

    public void setCurrentStatusName(String currentStatusName) {
        this.currentStatusName = currentStatusName;
    }

    public String getHasRedrushName() {
        return hasRedrushName;
    }

    public void setHasRedrushName(String hasRedrushName) {
        this.hasRedrushName = hasRedrushName;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getRealPage() {
        return realPage;
    }

    public void setRealPage(Integer realPage) {
        this.realPage = realPage;
    }

    public Integer getRealLimit() {
        return realLimit;
    }

    public void setRealLimit(Integer realLimit) {
        this.realLimit = realLimit;
    }

    public String getModuleStatusName() {
        return moduleStatusName;
    }

    public void setModuleStatusName(String moduleStatusName) {
        this.moduleStatusName = moduleStatusName;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getWaterMeterInfoId() {
        return waterMeterInfoId;
    }

    public void setWaterMeterInfoId(String waterMeterInfoId) {
        this.waterMeterInfoId = waterMeterInfoId;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }
}
