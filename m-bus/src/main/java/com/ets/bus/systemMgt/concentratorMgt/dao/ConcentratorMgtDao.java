package com.ets.bus.systemMgt.concentratorMgt.dao;

import com.ets.bus.systemMgt.concentratorMgt.entity.CollectVo;
import com.ets.bus.systemMgt.concentratorMgt.entity.ConcentratorVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.OperationInstructionVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/4/10
 * 集中器Dao
 */
public interface ConcentratorMgtDao {

    /**
     * 查询集中器集合
     * @param param
     * @return
     */
    List<ConcentratorVo> selectConcentratorList(Map<String, Object> param);

    /**
     * 获取集中器操作指令列表
     * @param param
     * @return
     */
    List<OperationInstructionVo> selectRecordList(Map<String, Object> param);

    /**
     * 根据ID获取集中器信息
     * @param id
     * @return
     */
    ConcentratorVo findConcentratormgtById(String id);

    /**
     * 若当前存在未执行完毕的指令，则不允许对相同的集中器重复操作同样的指令
     * @param arr
     * @param type
     * @return
     */
    List<OperationInstructionVo> checkNotFinishInsByConId(@Param("arr") String[] arr, @Param("type") Integer type);

    /**
     * 查询水表房间信息和绑定的最新房间类型信息
     * @param concentratorId
     * @return
     */
    List<WaterMeterInfoVo> getWaterMeterInfoAndParam(String concentratorId);

    /**
     * 获取集中器绑定采集的下发周期
     */
    CollectVo getCollectParam(String concentratorId);

    OperationInstructionVo findInstructionsById(String id);
}
