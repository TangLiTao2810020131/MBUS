package com.ets.bus.systemMgt.watermeterRoomInfoMgt.entity;


public class RoomWaterMeterVo {
    /**
     * 32位数UUID
     */
    private String id;

    /**
     * 区域ID
     */
    private String regionId;

    /**
     * 区域名称
     */
    private String regionName;

    /**
     * 公寓ID
     */
    private String apartmentId;

    /**
     * 公寓名称
     */
    private String apartmentName;

    /**
     * 楼ID
     */
    private String floorId;

    /**
     * 楼名称
     */
    private String floorName;
    /**
     * 楼层ID
     */
    private String layerId;

    /**
     * 楼层名称
     */
    private String layerName;

    /**
     * 房间ID
     */
    private String roomId;

    /**
     * 房间号
     */
    private String roomNum;
    /**
     * 水表Id
     */
    private String waterMeterid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getLayerId() {
        return layerId;
    }

    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
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

    public String getWaterMeterid() {
        return waterMeterid;
    }

    public void setWaterMeterid(String waterMeterid) {
        this.waterMeterid = waterMeterid;
    }
}
