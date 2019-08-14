package com.ets.bus.systemMgt.concentratorMgt.entity;

/**
 * @author 宋晨
 * @create 2019/4/22
 * 采集信息Vo
 */
public class CollectVo {
    private String id;

    /**
     * 采集应用编号
     */
    private String applicationNumber;

    /**
     * 采集名称
     */
    private String collectName;

    /**
     * 集中器id
     */
    private String concentratorId;

    /**
     * 自动采集频率(分钟)
     */
    private Integer autoCollectFrequency;

    /**
     * 采集地址(ip及端口不能重复）
     */
    private String collectAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getCollectName() {
        return collectName;
    }

    public void setCollectName(String collectName) {
        this.collectName = collectName;
    }

    public String getConcentratorId() {
        return concentratorId;
    }

    public void setConcentratorId(String concentratorId) {
        this.concentratorId = concentratorId;
    }

    public Integer getAutoCollectFrequency() {
        return autoCollectFrequency;
    }

    public void setAutoCollectFrequency(Integer autoCollectFrequency) {
        this.autoCollectFrequency = autoCollectFrequency;
    }

    public String getCollectAddress() {
        return collectAddress;
    }

    public void setCollectAddress(String collectAddress) {
        this.collectAddress = collectAddress;
    }
}
