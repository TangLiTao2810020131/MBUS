package com.ets.bus.reportQuery.entity.report;

import java.util.Date;

/**
 * @author 宋晨
 * @create 2019/4/8
 * 用水日报记录
 */
public class WaterDailyVo {

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
     * 楼栋名称
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
     * 用水日期（精确到日，一天一条）
     */
    private Date useTime;

    /**
     * 用水月份
     */
    private String useMonth;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 当日补水量（吨）
     */
    private Double supplementWater;

    /**
     * 当日购水量 (如果购水量=现金购水+一卡通购水此字段可弃用)
     */
    private Double buyWaterTotal;

    /**
     * 当日现金购水量（吨）
     */
    private Double cashBuyWater;

    /**
     * 当日持卡购水量（吨）
     */
    private Double cardBuyWater;

    /**
     * 当日退水量（吨）
     */
    private Double returnWater;

    /**
     * 当日剩余水量（吨）
     */
    private Double surplusWater;

    /**
     * 当前剩余水量（吨）
     */
    private Double currSurplusWater;

    /**
     * 已用水量（吨）
     */
    private Double userWater;

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

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Double getCashBuyWater() {
        return cashBuyWater;
    }

    public void setCashBuyWater(Double cashBuyWater) {
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

    public Double getSurplusWater() {
        return surplusWater;
    }

    public void setSurplusWater(Double surplusWater) {
        this.surplusWater = surplusWater;
    }

    public Double getUserWater() {
        return userWater;
    }

    public void setUserWater(Double userWater) {
        this.userWater = userWater;
    }

    public Double getCurrSurplusWater() {
        return currSurplusWater;
    }

    public void setCurrSurplusWater(Double currSurplusWater) {
        this.currSurplusWater = currSurplusWater;
    }

    public String getUseMonth() {
        return useMonth;
    }

    public void setUseMonth(String useMonth) {
        this.useMonth = useMonth;
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

    @Override
    public String toString() {
        return "WaterDailyVo{" +
                "id='" + id + '\'' +
                ", waterMeterInfoId='" + waterMeterInfoId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", apartmentId='" + apartmentId + '\'' +
                ", apartmentName='" + apartmentName + '\'' +
                ", concentratorId='" + concentratorId + '\'' +
                ", concentratorIp='" + concentratorIp + '\'' +
                ", buildId='" + buildId + '\'' +
                ", buildName='" + buildName + '\'' +
                ", floorId='" + floorId + '\'' +
                ", floorName='" + floorName + '\'' +
                ", roomId='" + roomId + '\'' +
                ", roomNum='" + roomNum + '\'' +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", useTime=" + useTime +
                ", useMonth='" + useMonth + '\'' +
                ", createTime=" + createTime +
                ", supplementWater=" + supplementWater +
                ", buyWaterTotal=" + buyWaterTotal +
                ", cashBuyWater=" + cashBuyWater +
                ", cardBuyWater=" + cardBuyWater +
                ", returnWater=" + returnWater +
                ", surplusWater=" + surplusWater +
                ", currSurplusWater=" + currSurplusWater +
                ", userWater=" + userWater +
                '}';
    }
}
