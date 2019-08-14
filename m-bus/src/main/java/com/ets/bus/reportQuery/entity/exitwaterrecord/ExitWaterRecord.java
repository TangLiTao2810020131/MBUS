package com.ets.bus.reportQuery.entity.exitwaterrecord;

/**
 * 退水记录实体类
 */
public class ExitWaterRecord
{
    private String id;//id
    private String water_meter_info_id;//房间水表信息id
    private String serial_number;//流水号
    private String user_id;//操作人id
    private String user_name;//操作人姓名
    private Integer current_status;//操作状态
    private String current_status_name;//操作状态名称
    private Double return_water;//退水量
    private Double return_money;//退水金额
    private Double buy_water_total;//总购水量
    private String remark;//备注
    private String create_time;//创建时间
    private String update_time;//更新时间
    private Integer type;//类型
    private String type_name;//类型名称
    private String concentrator_id;//集中器id
    private String ip_address;//集中器ip地址
    private Integer redrush;//是否红冲
    private String area_id;//区域id
    private String area_name;//区域名
    private String apartment_id;//公寓id
    private String apartment_name;//公寓id
    private String floor_id;//楼层id
    private String floor_name;//楼层名
    private String room_num;//房间号
    private String room_type_id;//房间类型id
    private String room_type_name;//房间类型名
    private String build_id;//楼栋id
    private String build_name;//楼栋名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWater_meter_info_id() {
        return water_meter_info_id;
    }

    public void setWater_meter_info_id(String water_meter_info_id) {
        this.water_meter_info_id = water_meter_info_id;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Double getReturn_water() {
        return return_water;
    }

    public void setReturn_water(Double return_water) {
        this.return_water = return_water;
    }

    public Double getReturn_money() {
        return return_money;
    }

    public void setReturn_money(Double return_money) {
        this.return_money = return_money;
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

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public java.lang.Integer getType() {
        return type;
    }

    public void setType(java.lang.Integer type) {
        this.type = type;
    }

    public java.lang.Integer getRedrush() {
        return redrush;
    }

    public void setRedrush(java.lang.Integer redrush) {
        this.redrush = redrush;
    }

    public String getConcentrator_id() {
        return concentrator_id;
    }

    public void setConcentrator_id(String concentrator_id) {
        this.concentrator_id = concentrator_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getApartment_id() {
        return apartment_id;
    }

    public void setApartment_id(String apartment_id) {
        this.apartment_id = apartment_id;
    }

    public String getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(String floor_id) {
        this.floor_id = floor_id;
    }

    public String getRoom_num() {
        return room_num;
    }

    public void setRoom_num(String room_num) {
        this.room_num = room_num;
    }

    public String getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(String room_type_id) {
        this.room_type_id = room_type_id;
    }

    public String getBuild_id() {
        return build_id;
    }

    public void setBuild_id(String build_id) {
        this.build_id = build_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(Integer current_status) {
        this.current_status = current_status;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getApartment_name() {
        return apartment_name;
    }

    public void setApartment_name(String apartment_name) {
        this.apartment_name = apartment_name;
    }

    public String getFloor_name() {
        return floor_name;
    }

    public void setFloor_name(String floor_name) {
        this.floor_name = floor_name;
    }

    public String getRoom_type_name() {
        return room_type_name;
    }

    public void setRoom_type_name(String room_type_name) {
        this.room_type_name = room_type_name;
    }

    public String getBuild_name() {
        return build_name;
    }

    public void setBuild_name(String build_name) {
        this.build_name = build_name;
    }

    public Double getBuy_water_total() {
        return buy_water_total;
    }

    public void setBuy_water_total(Double buy_water_total) {
        this.buy_water_total = buy_water_total;
    }

    public String getCurrent_status_name() {
        return current_status_name;
    }

    public void setCurrent_status_name(String current_status_name) {
        this.current_status_name = current_status_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}