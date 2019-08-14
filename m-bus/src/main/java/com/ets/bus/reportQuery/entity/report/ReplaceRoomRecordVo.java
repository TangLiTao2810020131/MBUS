package com.ets.bus.reportQuery.entity.report;

import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.RecordBaseVo;

import java.util.Date;

/**
 * @author 宋晨
 * @create 2019/4/4
 * 控水换房记录
 */
public class ReplaceRoomRecordVo extends RecordBaseVo {

    /**
     * 原水表信息表ID
     */
    private String oldWaterMeterInfoId;

    /**
     * 新水表信息表ID
     */
    private String newWaterMeterInfoId;

    /**
     * 操作状态名称
     */
    private String currentStatusName;

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
     * 房间类型名称
     */
    private String roomTypeName;

    /**
     * 补水量（吨）
     */
    private Double supplementWater;

    /**
     * 购水量 (如果购水量=现金购水+一卡通购水此字段可弃用)
     */
    private Double buyWaterTotal;

    /**
     * 现金购水量（吨）
     */
    private Double cashBuyWater;

    /**
     * 持卡购水量（吨）
     */
    private Double cardBuyWater;

    /**
     * 退水量（吨）
     */
    private Double returnWater;

    /**
     * 剩余水量（吨）
     */
    private Double surplusWater;

    /**
     * 已用水量（吨）
     */
    private Double userWater;

    /**
     * 已透支水量（吨）
     */
    private Double overWater;

    /**
     * 操作人姓名
     */
    private String userName;

    public String getOldWaterMeterInfoId() {
        return oldWaterMeterInfoId;
    }

    public void setOldWaterMeterInfoId(String oldWaterMeterInfoId) {
        this.oldWaterMeterInfoId = oldWaterMeterInfoId;
    }

    public String getNewWaterMeterInfoId() {
        return newWaterMeterInfoId;
    }

    public void setNewWaterMeterInfoId(String newWaterMeterInfoId) {
        this.newWaterMeterInfoId = newWaterMeterInfoId;
    }

    public String getCurrentStatusName() {
        return currentStatusName;
    }

    public void setCurrentStatusName(String currentStatusName) {
        this.currentStatusName = currentStatusName;
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

    public Double getOverWater() {
        return overWater;
    }

    public void setOverWater(Double overWater) {
        this.overWater = overWater;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
