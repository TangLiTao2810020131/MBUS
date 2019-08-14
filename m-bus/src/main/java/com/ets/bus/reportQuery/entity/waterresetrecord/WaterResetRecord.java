package com.ets.bus.reportQuery.entity.waterresetrecord;

/**
 * 清零记录实体类
 */
public class WaterResetRecord
{
    private String id;//id
    private String areaId;//区域ID
    private String areaName;//区域名
    private String apartmentId;//公寓ID
    private String apartmentName;//公寓名
    private String buildId;//楼栋名
    private String buildName;//楼栋名
    private String floorId;//楼层ID
    private String floorName;//楼层名
    private String roomNum;//房间号
    private Integer type;//记录类型
    private String typeName;//记录类型名
    private Integer currentStatus;//状态类型
    private String currentStatusName;//状态类型名
    private Double userWater;//清零前用水量
    private Double supplementWater;//清零前补水量
    private Double buyWaterTotal;//清零前购水量
    private Double returnWater;//清零前退水量
    private Double surplusWater;//清零前剩余水量
    private Double closeValveWater;//清零前关阀水量
    private Double cumulateWater;//清零前累计水量
    private String userId;//操作人ID
    private String userName;//操作人姓名
    private String createTime;//创建时间
    private String updateTime;//更新时间
    private Integer redrush;//是否冲红
    private String redrushName;//冲红名
    private String concentratorId;//集中器id
    private String ipAddress;//集中器IP
    private String operatMoney;//操作金额
    private Integer roomType;//房间类型
    private String roomTypeName;//房间类型名

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
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

    public String getRedrushName() {
        return redrushName;
    }

    public void setRedrushName(String redrushName) {
        this.redrushName = redrushName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
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

    public Double getUserWater() {
        return userWater;
    }

    public void setUserWater(Double userWater) {
        this.userWater = userWater;
    }

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

    public Double getCloseValveWater() {
        return closeValveWater;
    }

    public void setCloseValveWater(Double closeValveWater) {
        this.closeValveWater = closeValveWater;
    }

    public Double getCumulateWater() {
        return cumulateWater;
    }

    public void setCumulateWater(Double cumulateWater) {
        this.cumulateWater = cumulateWater;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRedrush() {
        return redrush;
    }

    public void setRedrush(Integer redrush) {
        this.redrush = redrush;
    }

    public String getConcentratorId() {
        return concentratorId;
    }

    public void setConcentratorId(String concentratorId) {
        this.concentratorId = concentratorId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getOperatMoney() {
        return operatMoney;
    }

    public void setOperatMoney(String operatMoney) {
        this.operatMoney = operatMoney;
    }
}