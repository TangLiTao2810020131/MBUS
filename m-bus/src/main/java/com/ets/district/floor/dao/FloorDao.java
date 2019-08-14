package com.ets.district.floor.dao;

import java.util.List;
import java.util.Map;

import com.ets.bus.reportQuery.entity.buywaterrecord.BuyWaterRecord;
import com.ets.district.floor.entity.tb_floor;
import org.apache.ibatis.annotations.Param;

public interface FloorDao {
	/**
	 * 获取列表集合
	 * @param map
	 * @return
	 */
	List<tb_floor> select(Map<String, Object> map);
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	long selectCount(Map<String, Object> map);
	/**
	 * 根据id查询查询对应的楼栋信息
	 * 信息
	 * @param id
	 * @return
	 */
	tb_floor info(String id);
	/**
	 * 新增楼栋信息
	 * @param floor
	 * @return
	 */
	void insert(tb_floor floor);
	/**
	 * 编辑楼栋信息
	 * @param floor
	 * @return
	 */
	void update(tb_floor floor);
	List<tb_floor> getFloors(String parentId);
	/**
	 * 检验楼栋编码的唯一性
	 * @param floor
	 * @return
	 */
	long isCheckFloorCode(tb_floor floor);
	/**
	 * 批量删除楼栋信息
	 * @param id
	 * @return
	 */
	void deleteFloorById(String[] id);

	List<tb_floor> findFloorById(@Param("bList") List<BuyWaterRecord> bList);
	List<tb_floor> selectFloorByParentId(String father);
}
