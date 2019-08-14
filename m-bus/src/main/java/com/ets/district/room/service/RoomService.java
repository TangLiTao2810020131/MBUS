package com.ets.district.room.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import com.ets.bus.parmSet.roomTypeParm.dao.RoomTypeDao;
import com.ets.bus.parmSet.roomTypeParm.entity.RoomType;
import com.ets.bus.parmSet.roomTypeParm.service.RoomTypeService;
import com.ets.bus.parmSet.sysRunningParm.dao.SysRunningParmDao;
import com.ets.bus.parmSet.sysRunningParm.entity.SysRunningParm;
import com.ets.bus.parmSet.sysRunningParm.service.SysRunningParmService;
import com.ets.bus.reportQuery.entity.buywaterrecord.BuyWaterRecord;
import com.ets.bus.systemMgt.waterMeterMgt.service.WaterMeterMgtService;
import com.ets.bus.systemMgt.watermeterRoomInfoMgt.entity.WaterMeterInfoMgt;
import com.ets.bus.systemMgt.watermeterRoomInfoMgt.service.WaterMeterInfoMgtService;
import com.ets.bus.waterMeterMgt.waterAddMgt.dao.WaterAddMgtDao;
import com.ets.district.apartment.service.ApartmentService;
import com.ets.district.floor.service.FloorService;
import com.ets.district.layer.entity.tb_layer;
import com.ets.district.layer.service.LayerService;
import com.ets.district.region.service.RegionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.district.room.entity.tb_room;
import com.ets.district.room.dao.RoomDao;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.UUIDUtils;

@Service
@Transactional
public class RoomService {
	
	@Resource
	RoomDao roomDao;
	@Resource
    WaterMeterInfoMgtService waterMeterInfoMgtService;
	@Resource
	SysRunningParmDao sysRunningParmDao;
	@Resource
	RoomTypeDao roomTypeDao;

	/**
	 * 根据查询条件查询房间信息集合
	 * @param map
	 * @return
	 */
	public List<tb_room> getroom(Map<String, Object> map) {
		return roomDao.select(map);
	}
	/**
	 * 根据查询条件查询房间信息数量
	 * @param map
	 * @return
	 */
	public long getCount(Map<String, Object> map) {
		return roomDao.selectCount(map);
	}
	/**
	 * 根据id查询出对应的房间信息
	 * @param id
	 * @return
	 */
	public tb_room info(String id) {
		return roomDao.info(id);
	}
	/**
	 * 新增房间信息 并且同时存入房间信息表中
	 * 新增房间信息时 若没有选择房间类型 则分配系统运行参数
	 * 若是选择房间类型则为该房间分配对应的房间类型参数
	 * @param room
	 * @param roomTypeId
	 * @return
	 */
	public void save(tb_room room,String roomTypeId) {
		room.setId(UUIDUtils.getUUID());
		room.setCtime(DateTimeUtils.getnowdate());
		roomDao.insert(room);
		//同时存入'房间水表信息表'
		WaterMeterInfoMgt waterMeterInfoMgt = roomDao.selectRoomInfoByRoomId(room.getId());
		waterMeterInfoMgt.setId(UUIDUtils.getUUID());
		//如果没有选择房间类型，则为此系统分配系统参数

		if (roomTypeId==null || roomTypeId.equals("")){
			SysRunningParm sysRunningParm = sysRunningParmDao.info();
            //关阀水量
			waterMeterInfoMgt.setCloseValveWater(sysRunningParm.getValve_water());
			//报警水量
			waterMeterInfoMgt.setWarn_water(sysRunningParm.getWarn_water());
			//最大透支量
			waterMeterInfoMgt.setMax_over_water(sysRunningParm.getOver_water());
			//最大囤积水量
			waterMeterInfoMgt.setMaxWater(sysRunningParm.getHoard_water());
			//购水单价
			waterMeterInfoMgt.setBuy_money(sysRunningParm.getBuy_money());
			//退水单价
            waterMeterInfoMgt.setReturn_money(sysRunningParm.getReturn_money());
            //补水周期
			waterMeterInfoMgt.setAdd_cycle(sysRunningParm.getAdd_cycle());

		}else{
			//如果选择房间类型，则采用房间的运行参数
			RoomType roomType = roomTypeDao.findRoomType(roomTypeId);
			waterMeterInfoMgt.setCloseValveWater(roomType.getValve_water());
			//报警水量
			waterMeterInfoMgt.setWarn_water(roomType.getWarn_water());
			//最大透支量
			waterMeterInfoMgt.setMax_over_water(roomType.getOver_water());
			//最大囤积水量
			waterMeterInfoMgt.setMaxWater(roomType.getHoard_water());
			//购水单价
			waterMeterInfoMgt.setBuy_money(roomType.getBuy_money());
			//退水单价
			waterMeterInfoMgt.setReturn_money(roomType.getReturn_money());
			//补水周期
			waterMeterInfoMgt.setAdd_cycle(roomType.getAdd_cycle());
			//房间类型的编号
			waterMeterInfoMgt.setRoomTypeId(roomType.getType_num());
			//房间类型的名称
			waterMeterInfoMgt.setRoomTypeName(roomType.getType_name());
		}

        waterMeterInfoMgtService.saveWatermeterRoomInfoMgt(waterMeterInfoMgt);
    }
	/**
	 * 编辑房间信息 并且同时编辑房间信息表中
	 * 编辑房间信息时 若没有选择房间类型 则分配系统运行参数
	 * 若是选择房间类型则为该房间分配对应的房间类型参数
	 * @param room
	 * @param roomTypeId
	 * @return
	 */
	public void edit(tb_room room,String roomTypeId) {
		roomDao.update(room);

		WaterMeterInfoMgt waterMeterInfoMgt=new WaterMeterInfoMgt();
		WaterMeterInfoMgt infoByRoomId = waterMeterInfoMgtService.getInfoByRoomId(room.getId());
		waterMeterInfoMgt.setId(infoByRoomId.getId());
		//如果没有选择房间类型，则为此系统分配系统参数
		if (roomTypeId==null || roomTypeId.equals("")){
			SysRunningParm sysRunningParm = sysRunningParmDao.info();
			//关阀水量
			waterMeterInfoMgt.setCloseValveWater(sysRunningParm.getValve_water());
			//报警水量
			waterMeterInfoMgt.setWarn_water(sysRunningParm.getWarn_water());
			//最大透支量
			waterMeterInfoMgt.setMax_over_water(sysRunningParm.getOver_water());
			//最大囤积水量
			waterMeterInfoMgt.setMaxWater(sysRunningParm.getHoard_water());
			//购水单价
			waterMeterInfoMgt.setBuy_money(sysRunningParm.getBuy_money());
			//退水单价
			waterMeterInfoMgt.setReturn_money(sysRunningParm.getReturn_money());
			//补水周期
			waterMeterInfoMgt.setAdd_cycle(sysRunningParm.getAdd_cycle());

		}else{
			//如果选择房间类型，则采用房间的运行参数
			RoomType roomType = roomTypeDao.findRoomType(roomTypeId);
			waterMeterInfoMgt.setCloseValveWater(roomType.getValve_water());
			//报警水量
			waterMeterInfoMgt.setWarn_water(roomType.getWarn_water());
			//最大透支量
			waterMeterInfoMgt.setMax_over_water(roomType.getOver_water());
			//最大囤积水量
			waterMeterInfoMgt.setMaxWater(roomType.getHoard_water());
			//购水单价
			waterMeterInfoMgt.setBuy_money(roomType.getBuy_money());
			//退水单价
			waterMeterInfoMgt.setReturn_money(roomType.getReturn_money());
			//补水周期
			waterMeterInfoMgt.setAdd_cycle(roomType.getAdd_cycle());
			//房间类型的编号
			waterMeterInfoMgt.setRoomTypeId(roomType.getType_num());
			//房间类型的名称
			waterMeterInfoMgt.setRoomTypeName(roomType.getType_name());
		}
        waterMeterInfoMgtService.updateInfoById(waterMeterInfoMgt);
	}
	/**
	 * 检查房间编号的唯一性
	 * @param room
	 * @return
	 */
	public long isCheckRoomCode(tb_room room){
		return roomDao.isCheckRoomCode(room);
	}
	/**
	 * 检查房间号的唯一性
	 * @param room
	 * @return
	 */
	public long isCheckRoomNum(tb_room room){return roomDao.isCheckRoomNum(room);}
	/**
	 * 批量删除房间水表信息
	 * @param id
	 * @return
	 */
	public void deleteRoomById(String id[]){

		roomDao.deleteRoomById(id);
		//同时删除 '房间水表信息表'的房间信息
		waterMeterInfoMgtService.delWatermeterRoomInfoMgtByRoomId(id);

	}

    public List<tb_room> findRoomById(List<BuyWaterRecord> bList)
	{
		return roomDao.findRoomById(bList);
    }

    public List<tb_room> selectRoomByParentId(String father){
		return roomDao.selectRoomByParentId(father);
	}
	/**
	 * 根据id获取房间的详细信息
	 * @param id
	 * @return
	 */
	public tb_room getDetails(String id){
		return roomDao.getDetails(id);
	}
}
