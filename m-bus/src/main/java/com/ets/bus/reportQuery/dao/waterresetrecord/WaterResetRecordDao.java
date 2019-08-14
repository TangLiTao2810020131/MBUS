package com.ets.bus.reportQuery.dao.waterresetrecord;

import com.ets.bus.reportQuery.entity.waterresetrecord.WaterResetRecord;

import java.util.List;
import java.util.Map;

public interface WaterResetRecordDao
{
    /**
     * 获取所有的清零记录信息
     * @param map
     * @return
     */
    List<WaterResetRecord> selectWaterResetRecord(Map<String, Object> map);

    /**
     *  通过清零记录表ID查询清零记录信息
     * @param id
     * @return
     */
    WaterResetRecord findWaterReset(String id);
}