package com.ets.district.layer.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ets.bus.reportQuery.entity.buywaterrecord.BuyWaterRecord;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.district.layer.dao.LayerDao;
import com.ets.district.layer.entity.tb_layer;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.UUIDUtils;

@Service
@Transactional
public class LayerService {
	
	@Resource
	LayerDao layerDao;
	/**
	 * 获取列表集合
	 * @param map
	 * @return
	 */
	public List<tb_layer> getlayer(Map<String, Object> map) {
		return layerDao.select(map);
	}
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public long getCount(Map<String, Object> map) {
		return layerDao.selectCount(map);
	}
	/**
	 * 根据id查询查询对应的楼层
	 * 信息
	 * @param id
	 * @return
	 */
	public tb_layer info(String id) {
		return layerDao.info(id);
	}
	/**
	 * 新增楼层信息
	 * @param layer
	 * @return
	 */
	public void save(tb_layer layer) {
		layer.setId(UUIDUtils.getUUID());
		layer.setCtime(DateTimeUtils.getnowdate());
		layerDao.insert(layer);		
	}
	/**
	 * 编辑楼层信息
	 * @param layer
	 * @return
	 */
	public void edit(tb_layer layer) {
		layerDao.update(layer);	
	}



	public List<tb_layer> getLayers(String parentId){
		return layerDao.getLayers(parentId);
	}
	/**
	 * 检验楼层编码的唯一性
	 * @param layer
	 * @return
	 */
	public long isCheckLayerCode(tb_layer layer){
		return  layerDao.isCheckLayerCode(layer);
	}
	/**
	 * 批量删除楼层信息
	 * @param id
	 * @return
	 */
	public void deleteLayerById(String[] id){
		layerDao.deleteLayerById(id);
	}

    public List<tb_layer> findLayerById(List<BuyWaterRecord> bList)
	{
		return layerDao.findLayerById(bList);
    }

    public List<tb_layer> selectLayerByParentId(String father){
		return layerDao.selectLayerByParentId(father);
	}

}
