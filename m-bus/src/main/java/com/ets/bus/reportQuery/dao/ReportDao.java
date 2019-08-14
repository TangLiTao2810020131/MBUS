package com.ets.bus.reportQuery.dao;


import com.ets.bus.reportQuery.entity.historyrecord.HistoryRecord;
import com.ets.bus.reportQuery.entity.report.*;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/3/28
 * 报表查询Dao
 */
public interface ReportDao {

    /**
     * 查询冲红记录
     * @param map
     * @return
     */
    List<RedRushVo> selectRedrushList(Map<String, Object> map);

    /**
     * 查询换表记录
     * @param map
     * @return
     */
    List<ReplaceRecordVo> selectReplaceRecordList(Map<String, Object> map);

    /**
     * 查询控水换房记录
     * @param map
     * @return
     */
    List<ReplaceRoomRecordVo> selectReplaceRoomRecordList(Map<String, Object> map);

    /**
     * 查询用水房间日报
     * @param map
     * @return
     */
    List<WaterDailyVo> selectRoomDailyList(Map<String, Object> map);

    /**
     * 查询用水房间月报
     * @param map
     * @return
     */
    List<WaterDailyVo> selectRoomMonthList(Map<String, Object> map);

    /**
     * 查询历史命令
     * @param map
     * @return
     */
    List<HistoryCommandVo> selectHistoryCommandList(Map<String, Object> map);
    //最新抄表
    List<HistoryRecord> selectNewReadMeterList(Map<String, Object> map);

    //根据id查询冲红记录
    RedRushVo findRedRecord(String id);

    ReplaceRecordVo findReplaceWaterRecord(String id);

    ReplaceRoomRecordVo findReplaceRoomRecord(String id);

    WaterDailyVo findRoomDaily(String id);

    ReplaceRoomRecordVo findReplaceNewRoomRecord(String id);

    HistoryCommandVo findHistoryCommand(String id);

    HistoryRecord findNewReadMeter(String id);

    List<WaterDailyVo> getRoomWaterDaily();

    void batchInsertDaily (List<WaterDailyVo> waterDailyVos);

    void insertMonth(List<WaterDailyVo> waterDailyVos);

    List<WaterMeterInfoVo> getMeterInfoList();
}
