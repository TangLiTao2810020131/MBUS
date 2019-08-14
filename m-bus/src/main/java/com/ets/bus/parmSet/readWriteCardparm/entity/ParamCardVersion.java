package com.ets.bus.parmSet.readWriteCardparm.entity;

public class ParamCardVersion {
    private String id;
    private String version_num;//版本号
    private String version_name;//版本名称
    private String lawful_status;//'是否合法（0：合法，1：不合法）'
    private String remark;//备注
    private String create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion_num() {
        return version_num;
    }

    public void setVersion_num(String version_num) {
        this.version_num = version_num;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getLawful_status() {
        return lawful_status;
    }

    public void setLawful_status(String lawful_status) {
        this.lawful_status = lawful_status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
