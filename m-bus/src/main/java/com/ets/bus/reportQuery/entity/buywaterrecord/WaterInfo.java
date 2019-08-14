package com.ets.bus.reportQuery.entity.buywaterrecord;

/**
 * 房间水表信息表实体类
 */
public class WaterInfo
{
    private String id;//ID
    private String area_id;//区域id
    private String apartment_id;//公寓id
    private String floor_id;//楼层id
    private String room_num;//房间号
    private String room_type_id;//房间类型id
    private String concentrator_id;//集中器id

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

    public String getConcentrator_id() {
        return concentrator_id;
    }

    public void setConcentrator_id(String concentrator_id) {
        this.concentrator_id = concentrator_id;
    }
}