package com.ets.bus.reportQuery.dao.historyrecord;

import com.ets.bus.reportQuery.entity.historyrecord.HistoryRecord;
import java.util.List;
import java.util.Map;

public interface HistoryRecordDao
{
    /**
     * 查询所有的历史抄表记录
     * @param map
     * @return
     */
    List<HistoryRecord> selectHistoryRecords(Map<String, Object> map);

    /**
     * 通过历史抄表表ID查询历史抄表信息
     * @param id
     * @return
     */
    HistoryRecord findHistoryRecordById(String id);
}