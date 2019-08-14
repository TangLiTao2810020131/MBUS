package com.ets.bus.waterMeterMgt.waterReset.dao;

import com.ets.bus.waterMeterMgt.waterReset.entity.ClearRecordVo;

/**
 * @author 宋晨
 * @create 2019/3/27
 * 购水管理Dao
 */
public interface WaterResetDao {

    /**
     * 向补水记录表插入一条补水记录
     * @param clearRecordVo
     */
    void insertClearRecord(ClearRecordVo clearRecordVo);

}
