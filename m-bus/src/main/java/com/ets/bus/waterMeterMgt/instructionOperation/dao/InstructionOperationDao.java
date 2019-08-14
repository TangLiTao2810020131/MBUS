package com.ets.bus.waterMeterMgt.instructionOperation.dao;

import com.ets.bus.reportQuery.entity.report.RedRushVo;
import com.ets.bus.waterMeterMgt.instructionOperation.entity.InstructionData;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.OperationInstructionVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.RoomParamRecordVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author 宋晨
 * @create 2019/4/19
 * 指令操作Dao
 */
public interface InstructionOperationDao {

    /**
     * 获取操作指令表记录
     * @param deviceId
     * @param waterMeterNum
     * @param operationType
     * @return
     */
    //使用水表编号查询
    //OperationInstructionVo getOperationInstructionRecord(@Param("deviceId") String deviceId, @Param("waterMeterNum")String waterMeterNum, @Param("operationType")Integer operationType);
    OperationInstructionVo getOperationInstructionRecord(@Param("waterMeterNum")String waterMeterNum, @Param("operationType")Integer operationType);
    /**
     * 获取操作指令表记录
     * @param deviceId
     * @param operationType
     * @return
     */
    OperationInstructionVo getOperaInstRecordByDevice(@Param("deviceId") String deviceId, @Param("operationType")Integer operationType);

    /**
     * 更新水表房间信息
     * @param instructionData
     */
    void updateWaterMeteInfo(InstructionData instructionData);

    /**
     * 更新房间参数信息
     * @param instructionData
     */
    void updateWaterMeteParam(InstructionData instructionData);

    /**
     * 更新补水记录信息
     * @param instructionData
     */
    void updateAddWaterRecord(InstructionData instructionData);

    /**
     * 更新购水记录信息
     * @param instructionData
     */
    void updateBuyWaterRecord(InstructionData instructionData);

    /**
     * 更新采集记录表
     * @param instructionData
     */
    void updateCollectRecord(InstructionData instructionData);

    /**
     * 更新操作指令表
     * @param operaId
     * @param result
     * @param successStatus
     */
    void updateOperationInstruction(@Param("operaId") String operaId, @Param("result")String result, @Param("successStatus")Integer successStatus);

    /**
     * 更新指令明细表
     * @param operaId
     * @param result
     * @param successStatus
     */
    void updateInstructionDetail(@Param("operaId") String operaId, @Param("waterMeterNum")String waterMeterNum,@Param("result")String result, @Param("successStatus")Integer successStatus);

    /**
     * 更新水表房间阀门状态
     * @param instructionData
     */
    void updateWaterMeteValveStatus(InstructionData instructionData);

    /**
     * 更新清零记录表
     * @param instructionData
     */
    void updateClearRecord(InstructionData instructionData);

    /**
     * 清零房间
     * @param instructionData
     */
    void waterMeteInfoClear(InstructionData instructionData);

    /**
     * 更新退水记录
     * @param instructionData
     */
    void updateReturnMeteInfo(InstructionData instructionData);

    /**
     * 更新冲红记录信息
     * @param instructionData
     */
    void updateRedrushRecord(InstructionData instructionData);

    /**
     * 更新补水记录冲红状态
     * @param recordId
     */
    void updateRedAddRecord(String recordId);

    /**
     * 更新退水记录冲红状态
     * @param recordId
     */
    void updateRedReturnRecord(String recordId);

    /**
     * 获取冲红记录
     * @param operationId
     * @return
     */
    RedRushVo  getRedrushByOperationId(String operationId);

    /**
     * 更新房间发参数记录
     * @return
     */
    void updateRoomParamRecord(InstructionData instructionData);

    /**
     * 房间发参数记录
     * @return
     */
    RoomParamRecordVo getRoomParamRecordVo(InstructionData instructionData);

    /**
     * 根据水表编号获取房间ID
     * @param waterMeterNum
     * @return
     */
    String getWaterMeterInfoId(@Param("waterMeterNum") String waterMeterNum);

}
