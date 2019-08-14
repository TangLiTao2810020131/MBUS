package com.ets.bus.reportQuery.dao.buywaterrecord;

import com.ets.bus.reportQuery.entity.buywaterrecord.BuyWaterRecord;
import java.util.List;
import java.util.Map;

public interface BuyWaterRecordDao
{
        /**
         * 获取所有的购水记录
         * @param map
         * @return
         */
        List<BuyWaterRecord> getBuyWaterRecords(Map<String, Object> map);

        /**
         * 通过购水表ID获取购水信息
         * @param id
         * @return
         */
        BuyWaterRecord findBuyWaterRecordById(String id);

}