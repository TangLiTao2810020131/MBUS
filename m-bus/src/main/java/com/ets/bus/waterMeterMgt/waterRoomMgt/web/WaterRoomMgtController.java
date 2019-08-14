package com.ets.bus.waterMeterMgt.waterRoomMgt.web;

import javax.servlet.http.HttpServletRequest;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.bus.waterMeterMgt.waterRoomMgt.service.WaterRoomMgtService;
import com.ets.common.MyConstant;
import com.ets.utils.Message;
import com.ets.utils.PageListData;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/3/28
 * 控水换房管理控制器
 */
@Controller
@RequestMapping("waterroommgt")
public class WaterRoomMgtController {
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private WaterRoomMgtService waterRoomMgtService;
    @Autowired
    private OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(WaterRoomMgtController.class);
	
    @RequestMapping("waterRoomMgt")
    public String waterQuitMgt(Model model){
        //查询房间类型
        List<RoomTypeVo> roomTypeList = waterPurchaseMgtService.getRoomTypeList();
        model.addAttribute("roomTypeList", roomTypeList);
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("水表管理-控水换房管理");
        mol.setOperaContent("查看控水换房管理列表");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return "bus/watermeter-mgt/waterRoom-mgt/waterRoom-mgt";
    }

    /**
     * 获取控水换房房间列表
     * @param request
     * @return
     */
    @RequestMapping(value = "listData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(HttpServletRequest request) {

        PageListData<WaterMeterInfoVo> waterMeterInfoList = null;
        try {
            Map<String, Object> map = waterPurchaseMgtService.getReqSearchParam(request);
            int page = request.getParameter(MyConstant.PAGE_KEY) != null?Integer.parseInt(request.getParameter(MyConstant.PAGE_KEY)):MyConstant.PAGE_DEFULT;
            int limit = request.getParameter(MyConstant.LIMIT_KEY) != null? Integer.parseInt(request.getParameter(MyConstant.LIMIT_KEY)):MyConstant.LIMIT_DEFULT;
            //查询数据
            waterMeterInfoList = waterPurchaseMgtService.getWaterMeterInfoList(page, limit, map);

        }catch (Exception e){
            logger.error("获取控水换房房间列表报错！", e);
        }
        return new Gson().toJson(waterMeterInfoList);
    }

    /**
     * 获取控水换房房间信息
     * @param request
     * @return
     */
    @RequestMapping(value = "getRoomInfo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getRoomInfo(HttpServletRequest request) {
        Message infoMessage = new Message(MyConstant.MSG_FAIL,"操作失败！");
        try {
            String id = request.getParameter("id");
            if(StringUtils.isNotBlank(id)){
                infoMessage = waterRoomMgtService.getRoomInfoById(id);
            }
        }catch (Exception e){
            logger.error("获取控水换房房间信息数据报错！", e);
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 确认控水换房
     * @param request
     * @return
     */
    @RequestMapping("roomConfirm")
    @ResponseBody
    public String roomConfirm(HttpServletRequest request){
        Message infoMessage;
        try {
            infoMessage = waterRoomMgtService.roomConfirm(request);
        }catch (Exception e){
            logger.error("确认控水换房报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"确认控水换房失败！");
        }
        return new Gson().toJson(infoMessage);
    }

}
