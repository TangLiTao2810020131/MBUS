package com.ets.district.region.dao;

import java.util.List;
import java.util.Map;

import com.ets.bus.reportQuery.entity.buywaterrecord.BuyWaterRecord;
import com.ets.common.dtree.DtreeEntity;
import com.ets.district.region.entity.tb_region;
import com.ets.utils.EleTree;
import org.apache.ibatis.annotations.Param;

public interface RegionDao {
	/**
	 * 根据查询条件得到区域的信息集合
	 * @param map
	 * @return
	 */
	List<tb_region> select(Map<String, Object> map);
	/**
	 * 根据查询条件得到区域的信息数量进行分页
	 * @param
	 * @return
	 */
	long selectCount();
	/**
	 * 根据id查询查询对应的区域信息
	 * @param id
	 * @return
	 */
	tb_region info(String id);
	/**
	 * 新增区域信息
	 * @param region
	 * @return
	 */
	void insert(tb_region region);
	/**
	 * 编辑区域信息
	 * @param region
	 * @return
	 */
	void update(tb_region region);
	List<tb_region> getRegions();
	/**
	 * 根据区域编号查询该区域的个数 检验区域的唯一性
	 * @param code
	 * @return
	 */
	long isCheckRegionCode(String code);
	/**
	 * 批量删除区域信息
	 * @param id
	 * @return
	 */
	void deleteRegionById(String[] id);

    List<tb_region> findRegionsByRegionId(@Param("bList") List<BuyWaterRecord> bList);

	List<EleTree> findRegions();

}
