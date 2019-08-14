package com.ets.district.apartment.service;

import com.ets.bus.reportQuery.entity.buywaterrecord.BuyWaterRecord;
import com.ets.district.apartment.dao.ApartmentDao;
import com.ets.district.apartment.entity.tb_apartment;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ApartmentService {
	
	@Resource
	ApartmentDao apartmentDao;
	/**
	 * 获取列表集合
	 * @param map
	 * @return
	 */
	public List<tb_apartment> getApartment(Map<String, Object> map) {
		return apartmentDao.select(map);
	}
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public long getCount(Map<String, Object> map) {
		return apartmentDao.selectCount( map);
	}
	/**
	 * 根据id查询查询对应的公寓信息
	 * @param id
	 * @return
	 */
	public tb_apartment info(String id) {
		return apartmentDao.info(id);
	}
	/**
	 * 新增公寓信息
	 * @param apartment
	 * @return
	 */
	public void save(tb_apartment apartment) {
		apartment.setId(UUIDUtils.getUUID());
		apartment.setCtime(DateTimeUtils.getnowdate());
		apartmentDao.insert(apartment);		
	}
	/**
	 * 编辑公寓信息
	 * @param apartment
	 * @return
	 */
	public void edit(tb_apartment apartment) {
		apartmentDao.update(apartment);	
	}

	public List<tb_apartment> getApartments(String parenId){
		return apartmentDao.getApartments(parenId);
	}
	/**
	 *检验公寓编码的唯一性
	 * @param apartment
	 * @return
	 */
	public long isCheckApartmentCode(tb_apartment apartment){
		return apartmentDao.isCheckApartmentCode(apartment);
	}
	/**
	 *批量删除公寓信息
	 * @param id
	 * @return
	 */
	public void deleteApartmentById(String[] id){
		apartmentDao.deleteApartmentById(id);
	}
    public List<tb_apartment> findApartmentById(List<BuyWaterRecord> bList)
	{
		return apartmentDao.findApartmentById(bList);
    }

    public List<tb_apartment> findApartments()
	{
		return apartmentDao.findApartments();
    }
    public List<tb_apartment> selectApartmentByParentId(String father){
	    return apartmentDao.selectApartmentByParentId(father);
    }
}
