package com.ets.district.apartment.dao;

import java.util.List;
import java.util.Map;

import com.ets.bus.reportQuery.entity.buywaterrecord.BuyWaterRecord;
import com.ets.district.apartment.entity.tb_apartment;
import org.apache.ibatis.annotations.Param;

public interface ApartmentDao {
	/**
	 * 获取列表集合
	 * @param map
	 * @return
	 */
	List<tb_apartment> select(Map<String, Object> map);
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	long selectCount(Map<String, Object> map);
	/**
	 * 根据id查询查询对应的公寓信息
	 * @param id
	 * @return
	 */
	tb_apartment info(String id);
	/**
	 * 新增公寓信息
	 * @param apartment
	 * @return
	 */
	void insert(tb_apartment apartment);
	/**
	 * 编辑公寓信息
	 * @param apartment
	 * @return
	 */
	void update(tb_apartment apartment);

	List<tb_apartment> getApartments(String parenId);
	/**
	 *检验公寓编码的唯一性
	 * @param apartment
	 * @return
	 */
	long isCheckApartmentCode(tb_apartment apartment);
	void deleteApartmentById(String[] id);

    List<tb_apartment> findApartmentById(@Param("bList") List<BuyWaterRecord> bList);

    List<tb_apartment> findApartments();

    List<tb_apartment> selectApartmentByParentId(String father);


}
