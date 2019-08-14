package com.ets.bus.reportQuery.dao.addwaterrecord;

import com.ets.bus.reportQuery.entity.addwaterrecord.AddWaterRecord;
import java.util.List;
import java.util.Map;


public interface AddWaterRecordDao
{
    /**
     * 获取所有的补水记录
     * @param map
     * @return
     */
    List<AddWaterRecord> getAddWaterRecords(Map<String, Object> map);

    /**
     * 通过ID获取补水记录
     * @param id
     * @return
     */
    AddWaterRecord findAddRecord(String id);
}