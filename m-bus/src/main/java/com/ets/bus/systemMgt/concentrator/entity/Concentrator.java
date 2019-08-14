package com.ets.bus.systemMgt.concentrator.entity;

/**
 * 集中器实体类
 */
public class Concentrator
{
    private String id;//id
    private String ip_address;//集中器IP地址
    private String concentrator_pwd;//集中器密码
    private String concentrator_version;//集中器版本
    private String communication_mode;//通讯模式
    private String communication_mode_name;//通讯模式名称
    private String collect_id;//采集表ID
    private String collect_name;//采集名称
    private String remark;//备注
    private String concentrator_num;//集中器编号
    private String apartment_id;//关联公寓id
    private String apartment_name;//关联公寓名称
    private String create_time;//创建时间
    private String update_time;//更新时间

    public String getCommunication_mode_name() {
        return communication_mode_name;
    }

    public void setCommunication_mode_name(String communication_mode_name) {
        this.communication_mode_name = communication_mode_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getConcentrator_pwd() {
        return concentrator_pwd;
    }

    public void setConcentrator_pwd(String concentrator_pwd) {
        this.concentrator_pwd = concentrator_pwd;
    }

    public String getConcentrator_version() {
        return concentrator_version;
    }

    public void setConcentrator_version(String concentrator_version) {
        this.concentrator_version = concentrator_version;
    }

    public String getCommunication_mode() {
        return communication_mode;
    }

    public void setCommunication_mode(String communication_mode) {
        this.communication_mode = communication_mode;
    }

    public String getCollect_name() {
        return collect_name;
    }

    public void setCollect_name(String collect_name) {
        this.collect_name = collect_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getConcentrator_num() {
        return concentrator_num;
    }

    public void setConcentrator_num(String concentrator_num) {
        this.concentrator_num = concentrator_num;
    }

    public String getApartment_id() {
        return apartment_id;
    }

    public void setApartment_id(String apartment_id) {
        this.apartment_id = apartment_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(String collect_id) {
        this.collect_id = collect_id;
    }

    public String getApartment_name() {
        return apartment_name;
    }

    public void setApartment_name(String apartment_name) {
        this.apartment_name = apartment_name;
    }
}