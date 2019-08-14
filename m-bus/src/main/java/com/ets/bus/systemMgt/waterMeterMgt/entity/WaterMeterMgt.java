package com.ets.bus.systemMgt.waterMeterMgt.entity;

import com.ets.common.ExcelResources;

public class WaterMeterMgt {
    private String id;
    private String water_meter_id;
    private Integer type;//'水表类型(0:冷水水表,1:生活热水水表)'
    private String create_time;//'创建时间'
    private String update_time;//更新时间
    private String name;//水表名称
    private String roomNum;//房間號
    private Integer delStatus;//删除状态
    private String roomTypeName;//房间类型
    private String failResult;

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ExcelResources(title = "水表编号",order=1)
    public String getWater_meter_id() {
        return water_meter_id;
    }

    public void setWater_meter_id(String water_meter_id) {
        this.water_meter_id = water_meter_id;
    }

    @ExcelResources(title = "水表类型",order=2)
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

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }
    @ExcelResources(title = "失败明细", order = 2)
    public String getFailResult() {
        return failResult;
    }

    public void setFailResult(String failResult) {
        this.failResult = failResult;
    }
}
