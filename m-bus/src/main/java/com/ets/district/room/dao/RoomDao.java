package com.ets.district.room.dao;

import java.util.List;
import java.util.Map;

import com.ets.bus.reportQuery.entity.buywaterrecord.BuyWaterRecord;
//import com.ets.bus.systemMgt.watermeterRoomInfoMgt.entity.WaterMeterInfoMgt;
import com.ets.bus.systemMgt.watermeterRoomInfoMgt.entity.WaterMeterInfoMgt;
import com.ets.district.room.entity.tb_room;
import org.apache.ibatis.annotations.Param;

public interface RoomDao {
	/**
	 * 查询房间信息集合
	 * @param map
	 * @return
	 */
	List<tb_room> select(Map<String, Object> map);
	/**
	 * 根据查询条件查询房间信息数量
	 * @param map
	 * @return
	 */
	long selectCount(Map<String, Object> map);
	/**
	 * 根据id查询出对应的房间信息
	 * @param id
	 * @return
	 */
	tb_room info(String id);
	/**
	 * 新增房间信息
	 * @param room
	 * @return
	 */
	void insert(tb_room room);
	/**
	 * 编辑房间信息
	 * @param room
	 * @return
	 */
	void update(tb_room room);
	/**
	 * 检验房间的编号唯一性
	 * @param room
	 * @return
	 */
	long isCheckRoomCode(tb_room room);
	/**
	 * 批量删除房间信息
	 * @param id
	 * @return
	 */
	 void deleteRoomById(String[] id);

    List<tb_room> findRoomById(@Param("bList") List<BuyWaterRecord> bList);

   WaterMeterInfoMgt selectRoomInfoByRoomId(String id);

    List<tb_room> selectRoomByParentId(String father);
	/**
	 * 根据id获取详细信息
	 * @param id
	 * @return
	 */
    tb_room getDetails(String id);
	/**
	 * 检验房间号唯一性
	 * @param room
	 * @return
	 */
    long isCheckRoomNum(tb_room room);

}
