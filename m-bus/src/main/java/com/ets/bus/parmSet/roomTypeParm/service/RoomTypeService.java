package com.ets.bus.parmSet.roomTypeParm.service;

import com.ets.bus.parmSet.roomTypeParm.dao.RoomTypeDao;
import com.ets.bus.parmSet.roomTypeParm.entity.RoomType;
import com.ets.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoomTypeService
{
    @Autowired
    RoomTypeDao roomTypeDao;

    /**
     * 获取所有的房间类型信息
     * @param map
     * @return
     */
    public List<RoomType> findRoomTypes(Map<String, Object> map)
    {
        List<RoomType> list=roomTypeDao.findRoomTypes(map);
        for(RoomType rt:list){
            if(rt.getAdd_cycle()!=null){
                if(rt.getAdd_cycle()==0){
                    rt.setAdd_cycle_name("每日补一次");
                }else if(rt.getAdd_cycle()==1){
                    rt.setAdd_cycle_name("每月补一次");
                }else if(rt.getAdd_cycle()==2){
                    rt.setAdd_cycle_name("不限制周期");
                }else{
                    rt.setAdd_cycle_name("其它");
                }
            }
        }
        return list;
    }

    /**
     * 添加房间类型
     * @param rt
     */
    public void addRoomType(RoomType rt)
    {
        rt.setId(UUIDUtils.getUUID());
        rt.setDel_status(0);//默认未删除
        rt.setEffect_status(0);
        roomTypeDao.addRoomType(rt);
    }

    /**
     * 通过ID查询房间类型信息
     * @param id
     * @return
     */
    public RoomType findRoomType(String id)
    {
        return roomTypeDao.findRoomType(id);
    }

    /**
     * 可以批量删除房间类型
     * @param ids
     */
    public void delRoomType(String[] ids)
    {
        roomTypeDao.delRoomType(ids);
    }

    /**
     * 编辑房间类型
     * @param rt
     */
    public void editRoomType(RoomType rt)
    {
        roomTypeDao.editRoomType(rt);
    }

    /**
     * 通过房间类型编号查询房间类型信息
     * @param roomTypeNum
     * @return
     */
    public  List<RoomType>  checkRoomTypeNum(String roomTypeNum)
    {
        return roomTypeDao.checkRoomTypeNum(roomTypeNum);
    }

    /**
     * 通过房间类型名查询房间类型信息
     * @param roomTypeName
     * @return
     */
    public List<RoomType>  checkRoomTypeName(String roomTypeName)
    {
        return roomTypeDao.checkRoomTypeName(roomTypeName);
    }

    /**
     * 获取所有的房间类型（无参数）
     * @return
     */
    public List<RoomType> getAllRoomType(){
        return roomTypeDao.getAllRoomType();
    }

    /**
     * 更新房间类型删除状态 0：未删除  1：已删除
     * @param id
     */
    public void updateRoomTypeDelStatus(String id)
    {
        roomTypeDao.updateRoomTypeDelStatus(id);
    }

    /**
     * 更新房间类型状态
     * @param ids
     */
    public void updateRoomTypeStatusPl(String[] ids)
    {
        roomTypeDao.updateRoomTypeStatusPl(ids);
    }

    /**
     * 通过数组的形式获取房间类型信息
     * @param ids
     * @return
     */
    public List<RoomType> findRoomTypeByArray(String[] ids)
    {
        return roomTypeDao.findRoomTypeByArray(ids);
    }

    /**
     * 通过ID获取房间类型信息
     * @param id
     * @return
     */
    public RoomType infoRoomType(String id){
        return  roomTypeDao.infoRoomType(id);
    }
}