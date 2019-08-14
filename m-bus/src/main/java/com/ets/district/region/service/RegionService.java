package com.ets.district.region.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ets.bus.reportQuery.entity.buywaterrecord.BuyWaterRecord;
import com.ets.common.dtree.Data;
import com.ets.common.dtree.DtreeEntity;
import com.ets.common.dtree.Status;
import com.ets.district.apartment.entity.tb_apartment;
import com.ets.district.apartment.service.ApartmentService;
import com.ets.district.floor.entity.tb_floor;
import com.ets.district.floor.service.FloorService;
import com.ets.district.layer.entity.tb_layer;
import com.ets.district.layer.service.LayerService;
import com.ets.utils.EleTree;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.district.region.dao.RegionDao;
import com.ets.district.region.entity.tb_region;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.UUIDUtils;

@Service
@Transactional
public class RegionService {
	
	@Resource
	RegionDao regionDao;
	@Resource
	ApartmentService apartmentService;
	@Resource
	FloorService floorService;
	@Resource
	LayerService layerService;
	/**
	 * 根据查询条件得到区域的信息集合
	 * @param map
	 * @return
	 */
	public List<tb_region> getRegion(Map<String, Object> map) {
		return regionDao.select(map);
	}
	/**
	 * 根据查询条件得到区域的信息数量进行分页
	 * @param
	 * @return
	 */
	public long getCount() {
		return regionDao.selectCount();
	}
	/**
	 * 根据id查询查询对应的区域信息
	 * @param id
	 * @return
	 */
	public tb_region info(String id) {
		return regionDao.info(id);
	}
	/**
	 * 新增区域信息
	 * @param region
	 * @return
	 */
	public void save(tb_region region) {
		region.setId(UUIDUtils.getUUID());
		region.setCtime(DateTimeUtils.getnowdate());
		regionDao.insert(region);		
	}
	/**
	 * 编辑区域信息
	 * @param region
	 * @return
	 */
	public void edit(tb_region region) {
		regionDao.update(region);	
	}
	//树的表头信息
	public DtreeEntity getTreeHead(){
		DtreeEntity dtreeEntity = new DtreeEntity();
		Status status = new Status();
		List<Data> dataList = new ArrayList<Data>();
		Data data = new Data();
		data.setId(UUIDUtils.getUUID());
		data.setLevel("1");
		data.setParentId(UUIDUtils.getUUID());
		data.setTitle("区域-公寓-楼层");
		data.setIsLast(false);
		dataList.add(data);

		dtreeEntity.setData(dataList);
		status.setCode(200);
		status.setMessage("加载完成");
		dtreeEntity.setStatus(status);
		return dtreeEntity;
	}

	//得到全部的区域信息
	public DtreeEntity getRegions()
	{
		/*List<tb_province> provinceList = provinceService.getTreeProvince();*/
		List<tb_region> regionList = regionDao.getRegions();

		DtreeEntity dtreeEntity = new DtreeEntity();
		Status status = new Status();
		List<Data> dataList = new ArrayList<Data>();
		for (tb_region region : regionList)
		{
			Data data = new Data();
			data.setId(region.getId());
			data.setLevel("2");
			data.setParentId(region.getId());
			data.setTitle(region.getName());
			data.setIsLast(false);
			dataList.add(data);
		}
		dtreeEntity.setData(dataList);
		status.setCode(200);
		status.setMessage("加载完成");
		dtreeEntity.setStatus(status);
		return dtreeEntity;
	}
	//得到全部的公寓信息
	public DtreeEntity getApartments(String parentId)
	{
		/*List<tb_province> provinceList = provinceService.getTreeProvince();*/
		/*List<tb_region> regionList = regionDao.getRegions();*/
		List<tb_apartment> apartmentList = apartmentService.getApartments(parentId);

		DtreeEntity dtreeEntity = new DtreeEntity();
		Status status = new Status();
		List<Data> dataList = new ArrayList<Data>();
		for (tb_apartment apartment : apartmentList)
		{
			Data data = new Data();
			data.setId(apartment.getId());
			data.setLevel("3");
			data.setParentId(apartment.getId());
			data.setTitle(apartment.getName());
			data.setIsLast(false);
			dataList.add(data);
		}
		dtreeEntity.setData(dataList);
		status.setCode(200);
		status.setMessage("加载完成");
		dtreeEntity.setStatus(status);
		return dtreeEntity;
	}
	//得到全部的楼信息
	public DtreeEntity getFloors(String parentId)
	{
		/*List<tb_province> provinceList = provinceService.getTreeProvince();*/
		/*List<tb_region> regionList = regionDao.getRegions();*/
		List<tb_floor> floorList = floorService.getFloors(parentId);

		DtreeEntity dtreeEntity = new DtreeEntity();
		Status status = new Status();
		List<Data> dataList = new ArrayList<Data>();
		for (tb_floor floor : floorList)
		{
			Data data = new Data();
			data.setId(floor.getId());
			data.setLevel("4");
			data.setParentId(floor.getId());
			data.setTitle(floor.getName());
			data.setIsLast(false);
			dataList.add(data);
		}
		dtreeEntity.setData(dataList);
		status.setCode(200);
		status.setMessage("加载完成");
		dtreeEntity.setStatus(status);
		return dtreeEntity;
	}
	//得到全部的楼层信息
	public DtreeEntity getLayers(String parentId)
	{
		/*List<tb_province> provinceList = provinceService.getTreeProvince();*/
		/*List<tb_region> regionList = regionDao.getRegions();*/
		List<tb_layer> layerList = layerService.getLayers(parentId);

		DtreeEntity dtreeEntity = new DtreeEntity();
		Status status = new Status();
		List<Data> dataList = new ArrayList<Data>();
		for (tb_layer layer : layerList)
		{
			Data data = new Data();
			data.setId(layer.getId());
			data.setLevel("5");
			data.setParentId(layer.getId());
			data.setTitle(layer.getName());
			data.setIsLast(true);
			dataList.add(data);
		}
		dtreeEntity.setData(dataList);
		status.setCode(200);
		status.setMessage("加载完成");
		dtreeEntity.setStatus(status);
		return dtreeEntity;
	}
	/**
	 * 根据区域编号查询该区域的个数 检验区域的唯一性
	 * @param region
	 * @return
	 */
	public long isCheckRegionCode(tb_region region){
		return regionDao.isCheckRegionCode(region.getCode());
	}
	/**
	 * 批量删除区域信息
	 * @param id
	 * @return
	 */
	public void deleteRegionById(String[] id){
      regionDao.deleteRegionById(id);
	}


    public List<tb_region> findRegionsByRegionId(List<BuyWaterRecord> bList)
	{
		return regionDao.findRegionsByRegionId(bList);
    }

	public List<EleTree> findRegions()
	{
		List<EleTree> eList=regionDao.findRegions();
		for(EleTree et:eList){
			for(EleTree et1:et.getChildren())
			{
				for(EleTree et2:et1.getChildren())
				{
					for(EleTree et3:et2.getChildren())
					{
						for(EleTree et4:et3.getChildren())
						{
							System.out.println("**********"+et4.getName());
							/*et4.setChecked(true);*/
						}
					}
				}
			}
		}
		return eList;
	}
}
