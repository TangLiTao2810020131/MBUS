package com.ets.bus.waterMeterMgt.waterRoomMgt.dao;

import com.ets.bus.reportQuery.entity.report.ReplaceRoomRecordVo;

/**
 * @author 宋晨
 * @create 2019/3/27
 * 控水换房管理Dao
 */
public interface WaterRoomMgtDao {

    /**
     * 向控水换房记录表插入一条记录
     * @param replaceRoomRecord
     */
    void insertReplaceRoomRecord(ReplaceRoomRecordVo replaceRoomRecord);
}
