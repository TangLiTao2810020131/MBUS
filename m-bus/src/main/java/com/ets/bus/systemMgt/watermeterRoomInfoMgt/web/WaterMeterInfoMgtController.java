package com.ets.bus.systemMgt.watermeterRoomInfoMgt.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.systemMgt.waterMeterMgt.entity.RoomWaterMeterVo;
import com.ets.bus.systemMgt.waterMeterMgt.entity.WaterMeterMgt;
import com.ets.bus.systemMgt.waterMeterMgt.service.WaterMeterMgtService;
import com.ets.bus.systemMgt.watermeterRoomInfoMgt.service.WaterMeterInfoMgtService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 14:04
 */
@Controller
@RequestMapping("watermeterroominfomgt")
public class WaterMeterInfoMgtController {
    @Resource
    WaterMeterMgtService waterMeterMgtService;
    @Resource
    WaterMeterInfoMgtService waterMeterInfoMgtService;
    @Autowired
    OperationLogService operationLogService;
	
    @RequestMapping("watermeterRoomInfoMgt")
    public String watermeterRoomInfoMgt(HttpServletRequest request)
    {

        return "bus/system-mgt/watermeter-room-info-mgt/watermeter-room-info-mgt";
    }
    /**
     * 水表绑定房间
     * @param id
     * @param waterMeterId
     * @return
     */
    @RequestMapping(value = "bindRoom", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String bindRoom(String id,String waterMeterId){
        //得到即将绑定房间的水表的水表信息
        WaterMeterMgt waterMeter =waterMeterMgtService.getWaterMeterById(waterMeterId);
        //水表信息更新到房间水表信息表中
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("waterMeterId",waterMeter.getId());
        map.put("id",id);
        waterMeterInfoMgtService.updateWatermeterRoomInfoMgtByRoomId(map);
        RoomWaterMeterVo info = waterMeterInfoMgtService.getWaterMeterRoomInfoById(id);
        //添加水表绑定房间的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setOperaContent("水表编号["+waterMeter.getWater_meter_id()+"]绑定["+info.getRegionName()+"-"+info.getApartmentName()+"-"+info.getFloorName()+"-"+info.getLayerName()+"-"+info.getRoomNum()+"]房间");
        operationLog.setModuleName("系统管理-水表管理");
        operationLogService.addLog(operationLog);
        return new Gson().toJson("绑定成功");
    }

    /**
     * 绑定房间时检测删除的水表是否绑定有房间（水表列表中判断）
     * @param id
     * @return
     */
    @RequestMapping(value = "isCheckWaterMeter", produces = "application/json; charset=utf-8")
    @ResponseBody
    public int isCheckWaterMeter(String id){
        return waterMeterInfoMgtService.isCheckWaterMeter(id);
    }
    /**
     * 检测区域下所有房间是否绑定有水表
     * @param ids
     * @return
     */
    @RequestMapping(value = "isCheckRegionWaterMeter", produces = "application/json; charset=utf-8")
    @ResponseBody
    public long isCheckRegionWaterMeter(String[] ids){
     return waterMeterInfoMgtService.isCheckRegionWaterMeter(ids);
    }
    /**
     * 检测公寓下所有房间是否绑定有水表
     * @param ids
     * @return
     */
    @RequestMapping(value = "isCheckApartmentWaterMeter", produces = "application/json; charset=utf-8")
    @ResponseBody
    public long isCheckApartmentWaterMeter(String[] ids){
        return waterMeterInfoMgtService.isCheckApartmentWaterMeter(ids);
    }
    /**
     * 检测楼栋下所有房间是否绑定有水表
     * @param ids
     * @return
     */
    @RequestMapping(value = "isCheckFloorWaterMeter", produces = "application/json; charset=utf-8")
    @ResponseBody
    public long isCheckFloorWaterMeter(String[] ids){
        return waterMeterInfoMgtService.isCheckFloorWaterMeter(ids);
    }
    /**
     * 检测楼层下所有房间是否绑定有水表
     * @param ids
     * @return
     */
    @RequestMapping(value = "isCheckLayerWaterMeter", produces = "application/json; charset=utf-8")
    @ResponseBody
    public long isCheckLayerWaterMeter(String[] ids){
        return waterMeterInfoMgtService.isCheckLayerWaterMeter(ids);
    }
    /**
     * 检测删除的房间是否绑定有水表（房间列表中判断）
     * @param ids
     * @return
     */
    @RequestMapping(value = "isCheckRoomWaterMeter", produces = "application/json; charset=utf-8")
    @ResponseBody
    public long isCheckRoomWaterMeter(String[] ids){
        return waterMeterInfoMgtService.isCheckRoomWaterMeter(ids);
    }
    /**
     * 删除时检测删除的水表是否绑定有房间（水表列表中判断）
     * @param ids
     * @return
     */
    @RequestMapping(value = "isCheckWaterMeterBind", produces = "application/json; charset=utf-8")
    @ResponseBody
    public long isCheckRoomWaterMeterBind(String[] ids){
        return waterMeterInfoMgtService.isCheckWaterMeterBind(ids);
    }


    @RequestMapping(value = "checkWaterMeterExitData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public long checkWaterMeterExitData(String[] ids){
        return waterMeterInfoMgtService.checkWaterMeterExitData(ids);
    }


}
