package com.ets.bus.parmSet.roomTypeParm.dao;

import com.ets.bus.parmSet.roomTypeParm.entity.RoomType;

import java.util.List;
import java.util.Map;

public interface RoomTypeDao
{
    /**
     * 获取所有的房间类型信息
     * @param map
     * @return
     */
    List<RoomType> findRoomTypes(Map<String, Object> map);

    /**
     * 添加房间类型
     * @param rt
     */
    void addRoomType(RoomType rt);

    /**
     * 通过ID查询房间类型信息
     * @param id
     * @return
     */
    RoomType findRoomType(String id);

    /**
     * 删除房间类型
     * @param ids
     */
    void delRoomType(String[] ids);

    /**
     * 编辑房间类型
     * @param rt
     */
    void editRoomType(RoomType rt);

    void roomTypeService(String roomTypeNum);

    /**
     * 根据房间类型编号查询房间类型信息
     * @param roomTypeNum
     * @return
     */
    List<RoomType>  checkRoomTypeNum(String roomTypeNum);

    /**
     * 根据房间类型名查询房间类型信息
     * @param roomTypeName
     * @return
     */
    List<RoomType>  checkRoomTypeName(String roomTypeName);

    /**
     * 获取所有的房间类型信息（无参数）
     * @return
     */
    List<RoomType> getAllRoomType();

    /**
     * 更新房间类型删除状态
     * @param id
     */
    void updateRoomTypeDelStatus(String id);

    /**
     * 更新房间类型状态
     * @param ids
     */
    void updateRoomTypeStatusPl(String[] ids);

    /**
     * 通过数组获取房间类型信息
     * @param ids
     * @return
     */
    List<RoomType>  findRoomTypeByArray(String[] ids);

    /**
     * 根据房间类型ID查询房间类型信息
     * @param id
     * @return
     */
    RoomType infoRoomType(String id);
}