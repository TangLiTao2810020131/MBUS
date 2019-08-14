package com.ets.bus.systemMgt.watermeterRoomInfoMgt.service;

import com.ets.bus.systemMgt.waterMeterMgt.entity.RoomWaterMeterVo;
import com.ets.bus.systemMgt.watermeterRoomInfoMgt.dao.WaterMeterInfoMgtDao;
import com.ets.bus.systemMgt.watermeterRoomInfoMgt.entity.WaterMeterInfoMgt;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WaterMeterInfoMgtService {
    @Resource
    WaterMeterInfoMgtDao waterMeterInfoMgtDao;
    public void saveWatermeterRoomInfoMgt(WaterMeterInfoMgt waterMeterInfoMgt){
        waterMeterInfoMgtDao.saveWatermeterRoomInfoMgt(waterMeterInfoMgt);
    }
    /**
     * 水表绑定房间
     * @param map
     * @return
     */
    public void updateWatermeterRoomInfoMgtByRoomId(Map<String,Object> map){
        waterMeterInfoMgtDao.updateWatermeterRoomInfoMgtByRoomId(map);
    }
    /**
     * 绑定房间时检测删除的水表是否绑定有房间（水表列表中判断）
     * @param id
     * @return
     */
    public int isCheckWaterMeter(String id){
        return waterMeterInfoMgtDao.isCheckWaterMeter(id);
    }
    public void delWatermeterRoomInfoMgtByRoomId(String[] id){
        waterMeterInfoMgtDao.delWatermeterRoomInfoMgtByRoomId(id);
    }
    /**
     * 检测区域下所有房间是否绑定有水表
     * @param regionId
     * @return
     */
    public long isCheckRegionWaterMeter(String[] regionId){
        return waterMeterInfoMgtDao.isCheckRegionWaterMeter(regionId);
    }
    /**
     * 检测公寓下所有房间是否绑定有水表
     * @param apartmentId
     * @return
     */
    public long isCheckApartmentWaterMeter(String[] apartmentId){
        return waterMeterInfoMgtDao.isCheckApartmentWaterMeter(apartmentId);
    }
    /**
     * 检测楼栋下所有房间是否绑定有水表
     * @param floorId
     * @return
     */
    public long isCheckFloorWaterMeter(String[] floorId){
        return waterMeterInfoMgtDao.isCheckFloorWaterMeter(floorId);
    }
    /**
     * 检测楼层下所有房间是否绑定有水表
     * @param layerId
     * @return
     */
    public long isCheckLayerWaterMeter(String[] layerId){
        return waterMeterInfoMgtDao.isCheckLayerWaterMeter(layerId);
    }
    /**
     * 检测删除的房间是否绑定有水表（房间列表中判断）
     * @param roomId
     * @return
     */
    public long isCheckRoomWaterMeter(String[] roomId){
        return waterMeterInfoMgtDao.isCheckRoomWaterMeter(roomId);
    }
    /**
     * 根据id查询出水表信息表中的对应的数据信息
     * @param id
     * @return
     */
    public RoomWaterMeterVo getWaterMeterRoomInfoById(String id){
        return waterMeterInfoMgtDao.getWaterMeterRoomInfoById(id);
    };
    public void updateWaterMeterInfoByWaterMeterId(Map map){
        waterMeterInfoMgtDao.updateWaterMeterInfoByWaterMeterId(map);
    }
    public WaterMeterInfoMgt getInfoByRoomId(String id){
        return waterMeterInfoMgtDao.getInfoByRoomId(id);
    }
    public void updateInfoById(WaterMeterInfoMgt waterMeterInfoMgt){
       waterMeterInfoMgtDao.updateInfoById(waterMeterInfoMgt);
    }
    /**
     * 删除时检测删除的水表是否绑定有房间（水表列表中判断）
     * @param ids
     * @return
     */
    public long isCheckWaterMeterBind(String[] ids){
        return waterMeterInfoMgtDao.isCheckWaterMeterBind(ids);

    }
    public List<WaterMeterInfoMgt> findWatermeterInfoByRoomTypeId(String[] typeNums)
    {
        return waterMeterInfoMgtDao.findWatermeterInfoByRoomTypeId(typeNums);
    }

    /**
     * 解除绑定时候检测水表是否存在数据（水表列表中判断）
     * @param ids
     * @return
     */
    public long checkWaterMeterExitData(String[] ids){
        return waterMeterInfoMgtDao.checkWaterMeterExitData(ids);

    }

}
