package com.ets.bus.waterMeterMgt.waterPurchaseMgt.dao;

import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/3/27
 * 购水管理Dao
 */
public interface WaterPurchaseMgtDao {

    /**
     * 查询水表信息表集合
     * @return
     */
    List<WaterMeterInfoVo> selectWaterMeterInfoList(Map<String, Object> map);

    /**
     * 根据ID查询房间水表信息
     * @param id
     * @return
     */
    WaterMeterInfoVo selectWaterMeterInfoById(String id);

    /**
     * 向操作指令表插入一条记录
     * @param operationInstruction
     */
    void insertOperationInstruction(OperationInstructionVo operationInstruction);

    /**
     * 向指令明细表插入一条记录
     * @param instructionDetail
     */
    void insertInstructionDetail(InstructionDetailVo instructionDetail);

    /**
     * 向购水记录表插入一条现金购水记录
     * @param buyRecord
     */
    void insertBuyRecord(BuyRecordVo buyRecord);

    /**
     * 获取系统运行参数
     * @return
     */
    SysoperatParamVo getSysoperatParam();

    /**
     * 根据房间水表ID查询是否存在未完成的指令
     * @param waterMeterInfoId
     * @return
     */
    List<OperationInstructionVo> getNotFinishInstruct(@Param("waterMeterInfoId") String waterMeterInfoId);

    /**
     * 根据房间水表ID查询是否存在未完成的指令
     * @param waterMeterInfoId
     * @return
     */
    List<OperationInstructionVo> getNotFinishByMeterIdAndType(@Param("waterMeterInfoId") String waterMeterInfoId, @Param("type") Integer type);

    /**
     * 根据房间水表ID查询是否存在未完成的指令
     * @param concentratorId
     * @return
     */
    List<OperationInstructionVo> getNotFinishByConIdAndType(@Param("concentratorId") String concentratorId, @Param("type") Integer type);

    /**
     * 查询房间类型
     * @return
     */
    List<RoomTypeVo> getRoomTypeList();

    /**
     * 向采集水表信息记录插入一条记录
     * @param collectRecord
     */
    void insertCollectRecord(CollectRecordVo collectRecord);

    /**
     * 更加code获取名称
     * @param typeCode
     * @return
     */
    String getRoomTypeName(String typeCode);

    /**
     * 向房间参数下发记录插入一条记录
     * @param roomParamRecordVo
     */
    void insertRoomParamRecord(RoomParamRecordVo roomParamRecordVo);

    /**
     * 获取所有集中器编号
     * @return
     */
    HashSet<String> getAllConcentratorVo();

    /**
     * 获取房间信息
     * @param apartmentName
     * @param buildName
     * @param floorName
     * @param roomNum
     * @return
     */
    WaterMeterInfoVo getWaterMeterInfoByRoom(@Param("apartmentName") String apartmentName, @Param("buildName") String buildName, @Param("floorName") String floorName, @Param("roomNum") String roomNum);

    void testSaveWaterMeter(@Param("id") String id, @Param("waterMeterId") String waterMeterId);
    void testSaveWatermeterRoomInfoMgt(@Param("id") String id, @Param("floorId") String floorId, @Param("roomId") String roomId, @Param("roomNum") String roomNum, @Param("concentratorId") String concentratorId, @Param("waterMeterId") String waterMeterId);
    void testaddConcentrator(@Param("id") String id, @Param("concentratorNum") String concentratorNum);
    void testInsertLayer(@Param("id") String id, @Param("code") String code, @Param("name") String name);
    void testInsertRoom(@Param("id") String id, @Param("code") String code, @Param("name") String name, @Param("parentid") String parentid);
}
