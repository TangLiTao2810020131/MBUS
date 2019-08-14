package com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity;


import java.util.Date;

/**
 * @author 宋晨
 * @create 2019/3/27
 * 房间水表信息VO
 */
public class WaterMeterInfoVo {

    /**
     * 32位数UUID
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
     * 集中器ID
     */
    private String concentratorId;

    /**
     * 集中器IP
     */
    private String concentratorIp;

    /**
     * 集中器编号
     */
    private String concentratorNum;

    /**
     * 楼层ID
     */
    private String floorId;

    /**
     * 楼层编号
     */
    private String floorNum;

    /**
     * 楼层名称
     */
    private String floorName;

    /**
     * 楼栋ID
     */
    private String buildId;

    /**
     * 楼栋名称
     */
    private String buildName;

    /**
     * 房间ID
     */
    private String roomId;

    /**
     * 房间号
     */
    private String roomNum;

    /**
     * 水表ID
     */
    private String waterMeterId;

    /**
     * 水表编号
     */
    private String waterMeterNum;

    /**
     * 初始化状态（0：未初始化，1：已初始化）
     */
    private Integer initStatus;

    /**
     * 初始化状态名称（0：未初始化，1：已初始化）
     */
    private String initStatusName;

    /**
     * 阀门状态（0：关阀，1：一般开阀，2：水用完，3：窃水关阀）
     */
    private Integer valveStatus;

    /**
     * 阀门状态名称（0：关阀，1：一般开阀，2：水用完，3：窃水关阀）
     */
    private String valveStatusName;

    /**
     * 模块状态（0：未知，1：正常）
     */
    private Integer moduleStatus;

    /**
     * 模块状态名称（0：未知，1：正常）
     */
    private String moduleStatusName;

    /**
     * 采集时间
     */
    private Date collectTime;

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
     * 剩余水量（吨）剩余购水+剩余补水
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
     * 最大囤积水量（吨）
     */
    private Double maxWater;

    /**
     * 关阀水量（吨）
     */
    private Double closeValveWater;

    /**
     * 房间类型ID （关联最大囤积量，购水单价，退水单价。若没有配置类型，取默认参数）
     */
    private String roomTypeId;

    /**
     * 房间类型Name
     */
    private String roomTypeName;

    /**
     * 累积用水量（吨）
     */
    private Double cumulateWater;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 水量单价
     */
    private Double price;

    /**
     * 水量单价
     */
    private Double returnMoney;

    /**
     * 允许购买的最大水量
     */
    private Double allowWater;

    /**
     * 退水操作-剩余金额
     */
    private Double allowReturnMoney;

    /**
     * 采集表ID
     */
    private String collectId;

    /**
     * 采集表IP
     */
    private String collectIp;

    /**
     * 下发计数
     */
    private Integer addNum;

    /**
     * 报警水量（吨）
     */
    private Double warnWater;

    /**
     * 最大透支水量（吨）
     */
    private Double maxOverWater;

    /**
     * 软件补水周期（0：每日补一次，1：每月补一次，2：不限补水周期）
     */
    private Integer addCycle;

    public Double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Integer getAddCycle() {
        return addCycle;
    }

    public void setAddCycle(Integer addCycle) {
        this.addCycle = addCycle;
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

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getConcentratorId() {
        return concentratorId;
    }

    public void setConcentratorId(String concentratorId) {
        this.concentratorId = concentratorId;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
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

    public String getWaterMeterId() {
        return waterMeterId;
    }

    public void setWaterMeterId(String waterMeterId) {
        this.waterMeterId = waterMeterId;
    }

    public Integer getInitStatus() {
        return initStatus;
    }

    public void setInitStatus(Integer initStatus) {
        this.initStatus = initStatus;
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

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
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

    public Double getCloseValveWater() {
        return closeValveWater;
    }

    public void setCloseValveWater(Double closeValveWater) {
        this.closeValveWater = closeValveWater;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Double getCumulateWater() {
        return cumulateWater;
    }

    public void setCumulateWater(Double cumulateWater) {
        this.cumulateWater = cumulateWater;
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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getInitStatusName() {
        return initStatusName;
    }

    public void setInitStatusName(String initStatusName) {
        this.initStatusName = initStatusName;
    }

    public String getValveStatusName() {
        return valveStatusName;
    }

    public void setValveStatusName(String valveStatusName) {
        this.valveStatusName = valveStatusName;
    }

    public String getModuleStatusName() {
        return moduleStatusName;
    }

    public void setModuleStatusName(String moduleStatusName) {
        this.moduleStatusName = moduleStatusName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(String floorNum) {
        this.floorNum = floorNum;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public Double getOverWater() {
        return overWater;
    }

    public void setOverWater(Double overWater) {
        this.overWater = overWater;
    }

    public Double getMaxWater() {
        return maxWater;
    }

    public void setMaxWater(Double maxWater) {
        this.maxWater = maxWater;
    }

    public Double getAllowWater() {
        return allowWater;
    }

    public void setAllowWater(Double allowWater) {
        this.allowWater = allowWater;
    }

    public Double getAllowReturnMoney() {
        return allowReturnMoney;
    }

    public void setAllowReturnMoney(Double allowReturnMoney) {
        this.allowReturnMoney = allowReturnMoney;
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

    public String getWaterMeterNum() {
        return waterMeterNum;
    }

    public void setWaterMeterNum(String waterMeterNum) {
        this.waterMeterNum = waterMeterNum;
    }

    public String getConcentratorNum() {
        return concentratorNum;
    }

    public void setConcentratorNum(String concentratorNum) {
        this.concentratorNum = concentratorNum;
    }

    public Integer getAddNum() {
        return addNum;
    }

    public void setAddNum(Integer addNum) {
        this.addNum = addNum;
    }

    public Double getWarnWater() {
        return warnWater;
    }

    public void setWarnWater(Double warnWater) {
        this.warnWater = warnWater;
    }

    public Double getMaxOverWater() {
        return maxOverWater;
    }

    public void setMaxOverWater(Double maxOverWater) {
        this.maxOverWater = maxOverWater;
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
}
