package com.ets.bus.systemMgt.concentrator.dao;

import com.ets.bus.systemMgt.concentrator.entity.Concentrator;
import com.ets.bus.systemMgt.concentrator.entity.RoomWaterInfo;

import java.util.List;
import java.util.Map;

public interface ConcentratorDao
{
    List<Concentrator> getConcentrators(Map<String, Object> map);

    void delConcentrators(String[] ids);

    void addConcentrator(Concentrator concentrator);

    void editConcentrator(Concentrator concentrator);

    Concentrator findConcentrator(String id);

    List<Concentrator> findConcentrators(Map<String, Object> map);

    List<RoomWaterInfo> findRoomInfo(Map<String, Object> map);

    void addRooomConcentrator(Map<String, Object> map);

    Concentrator checkConcentratorIp(String ipAddress);

    List<RoomWaterInfo> findRoomByCobcentratorId(String concentratorId);

    void clearConcentrator(String concentratorId);

    List<RoomWaterInfo> findRoomInfoNotConcentrator(Map<String, Object> map);

    void clearRoomConcentrator(String[] ids);

    List<Concentrator> findConcentratorByCollectId(String[] ids);

    List<Concentrator> getConcentratorByConcentratorId(String[] ids);

    List<RoomWaterInfo> findWaterRoomById(String[] ids);

}