package com.ets.bus.systemMgt.clctApplMgt.entity;

/**
 * 采集管理实体类
 */
public class ClctApplMgt
{
    private String id;//id
    private String application_number;//采集应用编号
    private String collect_name;//采集名称
    private String concentrator_id;//集中器id
    private String failure;//密钥失效期
    private String auto_collect_frequency;//自动采集频率
    private String instruction_number;//执行命令编号
    private String del_status;//是否自动删除
    private String collect_key;//采集密钥
    private String collect_address;//采集地址（ip和端口)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplication_number() {
        return application_number;
    }

    public void setApplication_number(String application_number) {
        this.application_number = application_number;
    }

    public String getCollect_name() {
        return collect_name;
    }

    public void setCollect_name(String collect_name) {
        this.collect_name = collect_name;
    }

    public String getConcentrator_id() {
        return concentrator_id;
    }

    public void setConcentrator_id(String concentrator_id) {
        this.concentrator_id = concentrator_id;
    }

    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }

    public String getInstruction_number() {
        return instruction_number;
    }

    public void setInstruction_number(String instruction_number) {
        this.instruction_number = instruction_number;
    }

    public String getDel_status() {
        return del_status;
    }

    public void setDel_status(String del_status) {
        this.del_status = del_status;
    }

    public String getCollect_key() {
        return collect_key;
    }

    public void setCollect_key(String collect_key) {
        this.collect_key = collect_key;
    }

    public String getCollect_address() {
        return collect_address;
    }

    public void setCollect_address(String collect_address) {
        this.collect_address = collect_address;
    }

    public String getAuto_collect_frequency() {
        return auto_collect_frequency;
    }

    public void setAuto_collect_frequency(String auto_collect_frequency) {
        this.auto_collect_frequency = auto_collect_frequency;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}