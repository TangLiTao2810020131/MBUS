package com.ets.bus.parmSet.smartCard.entity;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * @author 宋晨
 * @create 2019/4/15
 * 一卡通终端Vo
 */
public class CardTerminalVo {

    /**
     * id
     */
    private String id;

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
     * 参数表编号
     */
    private String paramNum;

    /**
     * 终端编号（不可重复）
     */
    private String terminalNum;

    /**
     * ip地址（不可重复）
     */
    private String terminalAddress;

    /**
     * 终端类型（0：购水终端，1：退水终端
     */
    private Integer terminalType;

    /**
     * 终端类型名
     */
    private String terminalTypeName;

    /**
     * 是否删除（0：否，1：是）
     */
    private Integer delStatus;

    /**
     * 终端状态
     */
    private Integer terminalStatus;

    /**
     * 终端状态名称
     */
    private String terminalStatusName;

    /**
     * 终端功能
     */
    private String terminalFun;

    /**
     * 水量下限（吨）
     */
    private Double minWater;

    /**
     * 卡底金（元）
     */
    private Double minMoney;

    /**
     * 日限（元）
     */
    private Double dayRestrict;

    /**
     * 是否启用（0：启用，1：禁用）
     */
    private Double useStatus;

    /**
     * 心跳时间
     */
    private String heartbeatTime;
    /**
     * 采集时间
     */
    private String collectTime;
    /**
     * 创建时间
     */
    private String createTime;


    public String getTerminalTypeName() {
        return terminalTypeName;
    }

    public void setTerminalTypeName(String terminalTypeName) {
        this.terminalTypeName = terminalTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParamNum() {
        return paramNum;
    }

    public void setParamNum(String paramNum) {
        this.paramNum = paramNum;
    }

    public String getTerminalNum() {
        return terminalNum;
    }

    public void setTerminalNum(String terminalNum) {
        this.terminalNum = terminalNum;
    }

    public String getTerminalAddress() {
        return terminalAddress;
    }

    public void setTerminalAddress(String terminalAddress) {
        this.terminalAddress = terminalAddress;
    }

    public Integer getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getTerminalStatus() {
        return terminalStatus;
    }

    public void setTerminalStatus(Integer terminalStatus) {
        this.terminalStatus = terminalStatus;
    }

    public String getTerminalFun() {
        return terminalFun;
    }

    public void setTerminalFun(String terminalFun) {
        this.terminalFun = terminalFun;
    }

    public Double getMinWater() {
        return minWater;
    }

    public void setMinWater(Double minWater) {
        this.minWater = minWater;
    }

    public Double getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(Double minMoney) {
        this.minMoney = minMoney;
    }

    public Double getDayRestrict() {
        return dayRestrict;
    }

    public void setDayRestrict(Double dayRestrict) {
        this.dayRestrict = dayRestrict;
    }

    public Double getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Double useStatus) {
        this.useStatus = useStatus;
    }

    public String getHeartbeatTime() {
        return heartbeatTime;
    }

        public void setHeartbeatTime(String heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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


    public String getTerminalStatusName() {
        return terminalStatusName;
    }

    public void setTerminalStatusName(String terminalStatusName) {
        this.terminalStatusName = terminalStatusName;
    }
}
