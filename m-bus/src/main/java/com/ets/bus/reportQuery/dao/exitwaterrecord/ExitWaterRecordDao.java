package com.ets.bus.reportQuery.dao.exitwaterrecord;


import com.ets.bus.reportQuery.entity.exitwaterrecord.ExitWaterRecord;
import java.util.List;
import java.util.Map;

public interface ExitWaterRecordDao
{
    /**
     * 获取所有的退水记录信息
     * @param map
     * @return
     */
    List<ExitWaterRecord> getExitWaterRecords(Map<String, Object> map);

    /**
     * 根据退水表ID查询退水信息
     * @param id
     * @return
     */
    ExitWaterRecord findExitRecord(String id);
}