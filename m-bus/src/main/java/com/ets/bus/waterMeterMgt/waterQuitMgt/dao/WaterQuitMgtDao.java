package com.ets.bus.waterMeterMgt.waterQuitMgt.dao;

import com.ets.bus.waterMeterMgt.waterQuitMgt.entity.ReturnRecordVo;

/**
 * @author 宋晨
 * @create 2019/3/27
 * 退水管理Dao
 */
public interface WaterQuitMgtDao {

    /**
     * 向退水记录表插入一条退水记录
     * @param returnRecordVo
     */
    void insertReturnRecord(ReturnRecordVo returnRecordVo);

}
