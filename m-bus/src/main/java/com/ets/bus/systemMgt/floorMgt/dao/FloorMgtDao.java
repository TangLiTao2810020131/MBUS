package com.ets.bus.systemMgt.floorMgt.dao;

import com.ets.bus.systemMgt.floorMgt.entity.ReplaceRecord;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.OperationInstructionVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/4/11
 * 房间管理
 */
public interface FloorMgtDao {


    /**
     * 获取房间管理指令列表
     * @param param
     * @return
     */
    List<OperationInstructionVo> selectRecordList(Map<String, Object> param);

    /**
     * 若当前存在未执行完毕的指令，则不允许对相同的房间重复操作同样的指令
     * @param arr
     * @param type
     * @return
     */
    List<OperationInstructionVo> checkNotFinishInsByIds(@Param("arr") String[] arr, @Param("type")Integer type, @Param("types")Integer[] types);

    /**
     * 根据ID数组获取绑定集中器和水表的水表房间
     * @param ids
     * @return
     */
    List<WaterMeterInfoVo> getWaterMeterInfoByIds(@Param("ids")String[] ids);

    WaterMeterInfoVo getWaterMeterInfoById(String id);


   void insertReplaceRecord(ReplaceRecord replaceRecord);
   ReplaceRecord getNumByWaterMeterId(String id);
    void updateReplaceRecord(ReplaceRecord replaceRecord);

    OperationInstructionVo findInstructionsRecord(String id);
}
