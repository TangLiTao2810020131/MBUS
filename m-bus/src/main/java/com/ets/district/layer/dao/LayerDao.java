package com.ets.district.layer.dao;

import java.util.List;
import java.util.Map;

import com.ets.bus.reportQuery.entity.buywaterrecord.BuyWaterRecord;
import com.ets.district.layer.entity.tb_layer;
import org.apache.ibatis.annotations.Param;

public interface LayerDao {
	/**
	 * 获取列表集合
	 * @param map
	 * @return
	 */

	List<tb_layer> select(Map<String, Object> map);
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	long selectCount(Map<String, Object> map);
	
	tb_layer info(String id);
	/**
	 * 新增楼层信息
	 * @param layer
	 * @return
	 */
	void insert(tb_layer layer);
	/**
	 * 编辑楼层信息
	 * @param layer
	 * @return
	 */
	void update(tb_layer layer);

	List<tb_layer> getLayers(String parentId);
	/**
	 * 根据的楼层编码查询楼层信息的数量 判断楼层信息的唯一性
	 * @param layer
	 * @return
	 */
	long isCheckLayerCode(tb_layer layer);
	/**
	 * 批量删除楼层信息
	 * @param id
	 * @return
	 */
	void deleteLayerById(String[] id);

	List<tb_layer>  findLayerById(@Param("bList") List<BuyWaterRecord> bList);

	List<tb_layer> selectLayerByParentId(String father);

}
