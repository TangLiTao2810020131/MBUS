package com.ets.bus.systemMgt.waterMeterMgt.dao;


import com.ets.bus.systemMgt.waterMeterMgt.entity.RoomWaterMeterVo;
import com.ets.bus.systemMgt.waterMeterMgt.entity.WaterMeterMgt;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WaterMeterMgtDao {
    /**
     * 水表信息列表集合
     * @param map
     * @return
     */
    List<WaterMeterMgt> getWaterMeterMgts(Map<String, Object> map);
    /**
     * 水表信息列表的总记录数
     * @param map
     * @return
     */
    long getCount(Map<String, Object> map);
    /**
     * 保存水表的信息
     * @param waterMeterMgt
     * @return
     */
    void saveWaterMeter(WaterMeterMgt waterMeterMgt);
    /**
     * 根据id得到水表的信息
     * @param id
     * @return
     */
    WaterMeterMgt getWaterMeterById(String id);
    /**
     * 更新水表的信息
     * @param waterMeterMgt
     * @return
     */
    void updateWaterMeter(WaterMeterMgt waterMeterMgt);
    /**
     * 根据id批量删除水表信息
     * @param id
     * @return
     */
    void delWaterMeterMgts(String[] id);

    /**
     * 根据id批量解除绑定水表信息
     * @param id
     * @return
     */
    void cancelBind(String[] id);

    /**
     * 查询未绑定水表的房间集合
     * @param map
     * @return
     */
    List<RoomWaterMeterVo> getAllRoom(Map<String, Object> map);
    /**
     * 查询未绑定水表的房间记录数
     * @param map
     * @return
     */
    long getAllRoomCount(Map<String, Object> map);
    /**
     * 根据水表的编号查询表中的水表信息的数量（检验水表标号的唯一性）
     * @param waterMeterId
     * @return
     */
    long isCheckWaterMeterId(String waterMeterId);
    List<RoomWaterMeterVo> getAllWaterMeter(Map<String, Object> map);

    long getAllWaterMeterCount(Map<String, Object> map);
    /**
     * 根据水表的id查询出 RoomWaterMeterVo 的属性值
     * @param id
     * @return
     */
    RoomWaterMeterVo waterMeterRoomInfo(String id);

    WaterMeterMgt getWaterMeterByNum(String waterMeterNum);

    void batchInsert(@Param("list") List<WaterMeterMgt> list);

}
