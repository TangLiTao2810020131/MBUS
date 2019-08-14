package com.ets.bus.waterMeterMgt.waterAddMgt.dao;

import com.ets.bus.waterMeterMgt.waterAddMgt.entity.AddRecordVo;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 宋晨
 * @create 2019/3/27
 * 购水管理Dao
 */
public interface WaterAddMgtDao {

    /**
     * 查询补水周期
     * @param id
     * @return
     */
    Integer getAddCycleById(String id);

    /**
     * 周期内是否有补水记录
     * @param id
     * @param cycle
     * @return
     */
    List<AddRecordVo> getAddRecordByCycle(@Param("id") String id, @Param("cycle") Integer cycle);

    /**
     * 向补水记录表插入一条补水记录
     * @param addRecordVo
     */
    void insertAddRecord(AddRecordVo addRecordVo);

    /**
     * 根据楼层ID查询所有房间类型
     * @param floorId
     * @return
     */
    List<RoomTypeVo> getRoomTypeByFloorId(String floorId);

    /**
     * 根据楼层id查询房间信息
     * @param floorId
     * @return
     */
    List<WaterMeterInfoVo> getRoomsByFloorId(String floorId);
}
