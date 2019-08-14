package com.ets.bus.reportQuery.entity.buywaterrecord;

/**
 * 购水记录实体类
 */
public class BuyWaterRecord
{
    private String id;//id
    private String water_meter_info_id;//水表房间信息id
    private String payer_name;//付款人姓名
    private Double buy_water;//购水量
    private Double buy_money;//购水金额
    private Integer type;//购水类型
    private String type_name;//购水类型名称
    private String create_time;//创建时间
    private String area_id;//区域id
    private String area_name;//区域名称
    private String apartment_id;//公寓id
    private String apartment_name;//公寓名称
    private String floor_id;//楼层id
    private String floor_name;//楼层名称
    private String room_id;//房间id
    private String room_name;//房间名称
    private String room_num;//房间号
    private String room_type_id;//房间类型id
    private String room_type_name;//房间类型名称
    private String build_id;//楼栋id
    private String build_name;//楼栋名称
    private String serial_number;//流水号
    private String user_id;//操作人id
    private String user_name;//操作人姓名
    private String update_time;//更新时间
    private String actual_money;//实际收款
    private String return_money;//找零
    private Integer current_status;//操作状态
    private String current_status_name;//操作状态名称

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getPayer_name() {
        return payer_name;
    }

    public void setPayer_name(String payer_name) {
        this.payer_name = payer_name;
    }

    public Double getBuy_water() {
        return buy_water;
    }

    public void setBuy_water(Double buy_water) {
        this.buy_water = buy_water;
    }

    public Double getBuy_money() {
        return buy_money;
    }

    public void setBuy_money(Double buy_money) {
        this.buy_money = buy_money;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

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

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
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

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getActual_money() {
        return actual_money;
    }

    public void setActual_money(String actual_money) {
        this.actual_money = actual_money;
    }

    public String getReturn_money() {
        return return_money;
    }

    public void setReturn_money(String return_money) {
        this.return_money = return_money;
    }

    public Integer getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(Integer current_status) {
        this.current_status = current_status;
    }

    public String getCurrent_status_name() {
        return current_status_name;
    }

    public void setCurrent_status_name(String current_status_name) {
        this.current_status_name = current_status_name;
    }
}