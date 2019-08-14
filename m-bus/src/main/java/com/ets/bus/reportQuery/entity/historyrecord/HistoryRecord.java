package com.ets.bus.reportQuery.entity.historyrecord;

/**
 * 历史抄表实体类
 */
public class HistoryRecord
{
    private String id;//抄表记录表id
    private Double userWater;//用水量
    private Double surplusWater;//剩余水量
    private Integer valveStatus;//阀门状态
    private String valveStatusName;//阀门状态名称 '阀门状态（0：关阀，1：一般开阀，2：水用完，3：窃水关阀）
    private Integer moduleStatus;//模块状态
    private String  moduleStatusName;//'模块状态名称（0：未知，1：正常）'
    private Double supplementWater;//补水量
    private Double buyWaterTotal;//购水量
    private String cashBuyWater;//现金购水量
    private Double cardBuyWater;//持卡购水量
    private Double returnWater;//退水量
    private Integer powerStatus;//电池状态
    private Integer attackNum;//礠攻击次数
    private Integer currentStatus;//操作状态
    private Double overWater;//透支水量
    private String waterMeterInfoId;//房间信息id
    private String concentratorId;//集中器id
    private String result;//返回结果
    private String remark;//备注
    private String createTime;//创建时间
    private String updateTime;//更新时间
    private String addNum;//补水购水下发计数
    private String waterMeterNum;//水表编号
    private String currentStatusName;//操作状态名称
    private String areaId;//区域id
    private String areaName;//区域名称
    private String apartmentId;//公寓id
    private String apartmentName;//公寓名称
    private String buildId;
    private String buildName;
    private String floorId;
    private String floorName;
    private String roomNum;

    public String getModuleStatusName() {
        return moduleStatusName;
    }

    public void setModuleStatusName(String moduleStatusName) {
        this.moduleStatusName = moduleStatusName;
    }

    public String getValveStatusName() {
        return valveStatusName;
    }

    public void setValveStatusName(String valveStatusName) {
        this.valveStatusName = valveStatusName;
    }

    public String getCurrentStatusName() {
        return currentStatusName;
    }

    public void setCurrentStatusName(String currentStatusName) {
        this.currentStatusName = currentStatusName;
    }

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

    public Double getUserWater() {
        return userWater;
    }

    public void setUserWater(Double userWater) {
        this.userWater = userWater;
    }

    public Double getSurplusWater() {
        return surplusWater;
    }

    public void setSurplusWater(Double surplusWater) {
        this.surplusWater = surplusWater;
    }

    public Integer getValveStatus() {
        return valveStatus;
    }

    public void setValveStatus(Integer valveStatus) {
        this.valveStatus = valveStatus;
    }

    public Integer getModuleStatus() {
        return moduleStatus;
    }

    public void setModuleStatus(Integer moduleStatus) {
        this.moduleStatus = moduleStatus;
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

    public String getCashBuyWater() {
        return cashBuyWater;
    }

    public void setCashBuyWater(String cashBuyWater) {
        this.cashBuyWater = cashBuyWater;
    }

    public Double getCardBuyWater() {
        return cardBuyWater;
    }

    public void setCardBuyWater(Double cardBuyWater) {
        this.cardBuyWater = cardBuyWater;
    }

    public Double getReturnWater() {
        return returnWater;
    }

    public void setReturnWater(Double returnWater) {
        this.returnWater = returnWater;
    }

    public Integer getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(Integer powerStatus) {
        this.powerStatus = powerStatus;
    }

    public Integer getAttackNum() {
        return attackNum;
    }

    public void setAttackNum(Integer attackNum) {
        this.attackNum = attackNum;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getWaterMeterInfoId() {
        return waterMeterInfoId;
    }

    public void setWaterMeterInfoId(String waterMeterInfoId) {
        this.waterMeterInfoId = waterMeterInfoId;
    }

    public String getConcentratorId() {
        return concentratorId;
    }

    public void setConcentratorId(String concentratorId) {
        this.concentratorId = concentratorId;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Double getOverWater() {
        return overWater;
    }

    public void setOverWater(Double overWater) {
        this.overWater = overWater;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getAddNum() {
        return addNum;
    }

    public void setAddNum(String addNum) {
        this.addNum = addNum;
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
}