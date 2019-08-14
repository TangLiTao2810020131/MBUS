package com.ets.bus.systemMgt.concentrator.entity;

/**
 * 房间水表关联实体类
 */
public class RoomWaterInfo
{
    private String id;//房间id
    private String area_id;//区域id
    private String area_name;//区域名
    private String apartment_id;//公寓id
    private String apartment_name;//公寓名称
    private String floor_id;//楼层id
    private String floor_name;//楼层名
    private String room_num;//房间号
    private String water_meter_id;//水表id
    private Boolean LAY_CHECKED;//设置表格数据默认选中状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getApartment_id() {
        return apartment_id;
    }

    public void setApartment_id(String apartment_id) {
        this.apartment_id = apartment_id;
    }

    public String getApartment_name() {
        return apartment_name;
    }

    public void setApartment_name(String apartment_name) {
        this.apartment_name = apartment_name;
    }

    public String getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(String floor_id) {
        this.floor_id = floor_id;
    }

    public String getFloor_name() {
        return floor_name;
    }

    public void setFloor_name(String floor_name) {
        this.floor_name = floor_name;
    }

    public String getRoom_num() {
        return room_num;
    }

    public void setRoom_num(String room_num) {
        this.room_num = room_num;
    }

    public String getWater_meter_id() {
        return water_meter_id;
    }

    public void setWater_meter_id(String water_meter_id) {
        this.water_meter_id = water_meter_id;
    }

    public Boolean getLAY_CHECKED() {
        return LAY_CHECKED;
    }

    public void setLAY_CHECKED(Boolean LAY_CHECKED) {
        this.LAY_CHECKED = LAY_CHECKED;
    }
}