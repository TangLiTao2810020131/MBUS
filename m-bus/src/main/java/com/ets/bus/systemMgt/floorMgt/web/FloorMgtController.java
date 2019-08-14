package com.ets.bus.systemMgt.floorMgt.web;

import javax.servlet.http.HttpServletRequest;

import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;
import com.ets.bus.reportQuery.entity.report.ReplaceRecordVo;
import com.ets.bus.systemMgt.concentratorMgt.entity.ConcentratorVo;
import com.ets.bus.systemMgt.concentratorMgt.service.ConcentratorMgtService;
import com.ets.bus.systemMgt.concentratorMgt.web.ConcentratorMgtController;
import com.ets.bus.systemMgt.floorMgt.entity.ReplaceRecord;
import com.ets.bus.systemMgt.floorMgt.service.FloorMgtService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.systemMgt.waterMeterMgt.entity.RoomWaterMeterVo;
import com.ets.bus.systemMgt.waterMeterMgt.entity.WaterMeterMgt;
import com.ets.bus.systemMgt.waterMeterMgt.service.WaterMeterMgtService;
import com.ets.bus.systemMgt.watermeterRoomInfoMgt.service.WaterMeterInfoMgtService;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.OperationInstructionVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.common.MyConstant;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.Message;
import com.ets.utils.PageListData;
import com.ets.utils.UUIDUtils;
import com.google.gson.Gson;
import javafx.collections.ObservableMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/4/11
 * 房间管理控制器
 */
@Controller
@RequestMapping("floormgt")
public class FloorMgtController {

    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private ConcentratorMgtService concentratorMgtService;
    @Autowired
    private FloorMgtService floorMgtService;
    @Autowired
    private WaterMeterInfoMgtService waterMeterInfoMgtService;
    @Autowired
    private WaterMeterMgtService waterMeterMgtService;
    @Autowired
    private OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(ConcentratorMgtController.class);

    @RequestMapping("floorMgt")
    public String floorMgt(Model model){
        //查询房间类型
        List<RoomTypeVo> roomTypeList = waterPurchaseMgtService.getRoomTypeList();
        model.addAttribute("roomTypeList", roomTypeList);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setOperaContent("查看房间管理列表");
        operationLog.setModuleName("设备管理-房间管理");
        operationLogService.addLog(operationLog);
        return "bus/system-mgt/floor-mgt/floor-mgt";
    }

    @RequestMapping("record")
    public String record(HttpServletRequest request, Model model){
        model.addAttribute("tid", request.getParameter("id"));
        return "bus/system-mgt/floor-mgt/floor-record";
    }

    /**
     * 获取房间指令列表
     * @param request
     * @return
     */
    @RequestMapping(value = "recordListData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String recordListData(HttpServletRequest request) {

        PageListData<OperationInstructionVo> operationInstructionList = null;
        try {
            Map<String, Object> map = concentratorMgtService.getReqSearchParam(request);
            int page = request.getParameter(MyConstant.PAGE_KEY) != null?Integer.parseInt(request.getParameter(MyConstant.PAGE_KEY)):MyConstant.PAGE_DEFULT;
            int limit = request.getParameter(MyConstant.LIMIT_KEY) != null? Integer.parseInt(request.getParameter(MyConstant.LIMIT_KEY)):MyConstant.LIMIT_DEFULT;
            //查询数据
            operationInstructionList = floorMgtService.getRecordListData(page, limit, map);

        }catch (Exception e){
            logger.error("获取房间指令列表报错！", e);
        }
        return new Gson().toJson(operationInstructionList);
    }

    /**
     * 更新房间参数
     * @param ids
     * @return
     */
    @RequestMapping(value = "updateRoomParam", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String updateRoomParam(String[] ids){
        Message infoMessage;
        try {
            infoMessage = floorMgtService.updateRoomParam(ids);
        }catch (Exception e){
            logger.error("更新房间参数报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"更新房间参数失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 初始化房间
     * @param id
     * @return
     */
    @RequestMapping(value = "initRoom", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String initRoom(String id){
        Message infoMessage;
        try {
            infoMessage = floorMgtService.initRoom(id);
        }catch (Exception e){
            logger.error("初始化房间报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"初始化房间失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 获取房间信息
     * @param ids
     * @return
     */
    @RequestMapping(value = "getRoomInfo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getRoomInfo(String[] ids){
        Message infoMessage;
        try {
            infoMessage = floorMgtService.getRoomInfo(ids);
        }catch (Exception e){
            logger.error("获取房间信息报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"获取房间信息失败！");
        }
        return new Gson().toJson(infoMessage);
    }


    /**
     * 一般开阀
     * @param ids
     * @return
     */
    @RequestMapping(value = "open", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String open(String[] ids){
        Message infoMessage;
        try {
            infoMessage = floorMgtService.open(ids);
        }catch (Exception e){
            logger.error("一般开阀报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"一般开阀失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 一般关阀
     * @param ids
     * @return
     */
    @RequestMapping(value = "close", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String close(String[] ids){
        Message infoMessage;
        try {
            infoMessage = floorMgtService.close(ids);
        }catch (Exception e){
            logger.error("一般关阀报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"一般关阀失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 强制开阀
     * @param ids
     * @return
     */
    @RequestMapping(value = "forceOpen", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String forceOpen(String[] ids){
        Message infoMessage;
        try {
            infoMessage = floorMgtService.forceOpen(ids);
        }catch (Exception e){
            logger.error("强制开阀报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"强制开阀失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 强制关阀
     * @param ids
     * @return
     */
    @RequestMapping(value = "forceClose", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String forceClose(String[] ids){
        Message infoMessage;
        try {
            infoMessage = floorMgtService.forceClose(ids);
        }catch (Exception e){
            logger.error("强制关阀报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"强制关阀失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 更换水表页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("changeWatermeter")
    public String changeWatermeter(HttpServletRequest request, Model model){
        RoomWaterMeterVo waterMeterRoomInfo = null;
        try {
            String id = request.getParameter("id");
            //若当前房间存在未下发成功的水量（包含补水或购水等），则不允许继续购水。
            if(waterPurchaseMgtService.getNotFinishByMeterIdAndType(id, null)){
                model.addAttribute("errMsg","该房间存在未完成指令，不允许换表。");
                return "bus/watermeter-mgt/waterPurchase-mgt/waterPurchase-notAllow";
            }
            waterMeterRoomInfo = waterMeterInfoMgtService.getWaterMeterRoomInfoById(id);
        }catch (Exception e){
            logger.error("获取更换水表弹框数据报错！", e);
        }
        model.addAttribute("waterMeterRoomInfo",waterMeterRoomInfo);
        return "bus/system-mgt/floor-mgt/change-watermeter";
    }

    //更换水表
    @RequestMapping(value = "change", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String change(String id, Double replace_money, Integer type, String newWaterMeterId, String openTime,String remark){
        Message infoMessage;
        try {
            infoMessage = floorMgtService.change(id,replace_money,type,newWaterMeterId,openTime,remark);
        }catch (Exception e){
            logger.error("更换水表报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"更换水表失败！");
        }
        return new Gson().toJson(infoMessage);


    }

    @RequestMapping("/findInstructionsRecord.action")
    public String findInstructionsRecord(HttpServletRequest request,String id)
    {
        OperationInstructionVo oiv=null;
        try{
            oiv=floorMgtService.findInstructionsRecord(id);
            RoomWaterMeterVo info = waterMeterInfoMgtService.getWaterMeterRoomInfoById(id);
            mb_operation_log operationLog=new mb_operation_log();
            operationLog.setModuleName("设备管理-房间管理");
            operationLog.setOperaContent("查看["+info.getRegionName()+"-"+info.getLayerName()+"-"+info.getRoomNum()+"]房间指令记录");
            operationLogService.addLog(operationLog);

        }catch(Exception ex){

        }
        request.setAttribute("br",oiv);
        return "bus/system-mgt/floor-mgt/instructions-record";
    }
}
