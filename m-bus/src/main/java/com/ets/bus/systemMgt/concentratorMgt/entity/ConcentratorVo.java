package com.ets.bus.systemMgt.concentratorMgt.entity;

import java.util.Date;

/**
 * @author 宋晨
 * @create 2019/4/10
 * 集中器Vo
 */
public class ConcentratorVo {

    /**
     * id
     */
    private String id;

    /**
     * 集中器ip地址
     */
    private String ipAddress;

    /**
     * 集中器密码
     */
    private String concentratorPwd;

    /**
     * 集中器版本
     */
    private String concentratorVersion;

    /**
     * 通讯模式
     */
    private String communicationMode;

    /**
     * 采集表ID
     */
    private String collectId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 集中器编号
     */
    private String concentratorNum;

    /**
     * 关联公寓ID
     */
    private String apartmentId;

    /**
     *公寓名称
     */
    private String apartmentName;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 在线情况
     */
    private String online;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getConcentratorPwd() {
        return concentratorPwd;
    }

    public void setConcentratorPwd(String concentratorPwd) {
        this.concentratorPwd = concentratorPwd;
    }

    public String getConcentratorVersion() {
        return concentratorVersion;
    }

    public void setConcentratorVersion(String concentratorVersion) {
        this.concentratorVersion = concentratorVersion;
    }

    public String getCommunicationMode() {
        return communicationMode;
    }

    public void setCommunicationMode(String communicationMode) {
        this.communicationMode = communicationMode;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getConcentratorNum() {
        return concentratorNum;
    }

    public void setConcentratorNum(String concentratorNum) {
        this.concentratorNum = concentratorNum;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
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

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
