package com.ets.bus.systemMgt.watermeterRoomInfoMgt.dao;

import com.ets.bus.systemMgt.waterMeterMgt.entity.RoomWaterMeterVo;
import com.ets.bus.systemMgt.watermeterRoomInfoMgt.entity.WaterMeterInfoMgt;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;

import java.util.List;
import java.util.Map;

public interface WaterMeterInfoMgtDao {

    void saveWatermeterRoomInfoMgt(WaterMeterInfoMgt waterMeterInfoMgt);
    /**
     * 水表绑定房间
     * @param map
     * @return
     */
    void updateWatermeterRoomInfoMgtByRoomId(Map<String,Object> map);
    /**
     * 绑定房间时检测删除的水表是否绑定有房间（水表列表中判断）
     * @param id
     * @return
     */
    int isCheckWaterMeter(String id);
    void delWatermeterRoomInfoMgtByRoomId(String[] id);
    /**
     * 检测区域下所有房间是否绑定有水表
     * @param regionId
     * @return
     */
    long isCheckRegionWaterMeter(String[] regionId);
    /**
     * 检测公寓下所有房间是否绑定有水表
     * @param apartmentId
     * @return
     */
    long isCheckApartmentWaterMeter(String[] apartmentId);
    /**
     * 检测楼栋下所有房间是否绑定有水表
     * @param floorId
     * @return
     */
    long isCheckFloorWaterMeter(String[] floorId);
    /**
     * 检测楼层下所有房间是否绑定有水表
     * @param layerId
     * @return
     */
    long isCheckLayerWaterMeter(String[] layerId);
    /**
     * 检测删除的房间是否绑定有水表（房间列表中判断）
     * @param roomId
     * @return
     */
    long isCheckRoomWaterMeter(String[] roomId);
    /**
     * 根据id查询出水表信息表中的对应的数据信息
     * @param id
     * @return
     */
    RoomWaterMeterVo getWaterMeterRoomInfoById(String id);
    void updateWaterMeterInfoByWaterMeterId(Map map);
    WaterMeterInfoMgt getInfoByRoomId(String id);
    void updateInfoById(WaterMeterInfoMgt waterMeterInfoMgt);
    /**
     * 删除时检测删除的水表是否绑定有房间（水表列表中判断）
     * @param id
     * @return
     */
    long isCheckWaterMeterBind(String[] id);

    /**
     * 解除绑定时候检测水表是否存在数据（水表列表中判断）
     * @param ids
     * @return
     */
    long checkWaterMeterExitData(String[] ids);


    List<WaterMeterInfoMgt> findWatermeterInfoByRoomTypeId(String[] typeNums);
}
