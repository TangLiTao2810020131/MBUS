package com.ets.district.floor.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ets.bus.reportQuery.entity.buywaterrecord.BuyWaterRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.district.floor.dao.FloorDao;
import com.ets.district.floor.entity.tb_floor;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.UUIDUtils;

@Service
@Transactional
public class FloorService {
	
	@Resource
	FloorDao floorDao;
	/**
	 * 获取列表集合
	 * @param map
	 * @return
	 */
	public List<tb_floor> getfloor(Map<String, Object> map) {
		return floorDao.select(map);
	}
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public long getCount(Map<String, Object> map) {
		return floorDao.selectCount(map);
	}
	/**
	 * 根据id查询查询对应的楼栋信息
	 * 信息
	 * @param id
	 * @return
	 */
	public tb_floor info(String id) {
		return floorDao.info(id);
	}
	/**
	 * 新增楼栋信息
	 * @param floor
	 * @return
	 */
	public void save(tb_floor floor) {
		floor.setId(UUIDUtils.getUUID());
		floor.setCtime(DateTimeUtils.getnowdate());
		floorDao.insert(floor);		
	}
	/**
	 * 编辑楼栋信息
	 * @param floor
	 * @return
	 */
	public void edit(tb_floor floor) {
		floorDao.update(floor);	
	}

	public List<tb_floor> getFloors(String parentId){
		return floorDao.getFloors(parentId);
	}
	/**
	 * 检验楼栋编码的唯一性
	 * @param floor
	 * @return
	 */
	public long isCheckFloorCode(tb_floor floor){
		return floorDao.isCheckFloorCode(floor);
	}
	/**
	 * 批量删除楼栋信息
	 * @param id
	 * @return
	 */
	public void deleteFloorById(String[] id){
		floorDao.deleteFloorById(id);
	}

    public List<tb_floor> findFloorById(List<BuyWaterRecord> bList)
	{
		return floorDao.findFloorById(bList);
    }
    public List<tb_floor> selectFloorByParentId(String father){
		return floorDao.selectFloorByParentId(father);
	}

}
