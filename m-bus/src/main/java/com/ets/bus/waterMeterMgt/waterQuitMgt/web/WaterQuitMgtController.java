package com.ets.bus.waterMeterMgt.waterQuitMgt.web;

import javax.servlet.http.HttpServletRequest;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterAddMgt.web.WaterAddMgtController;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.bus.waterMeterMgt.waterQuitMgt.service.WaterQuitMgtService;
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
 * 退水管理控制器
 */
@Controller
@RequestMapping("waterquitmgt")
public class WaterQuitMgtController {
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private WaterQuitMgtService waterQuitMgtService;
    @Autowired
    private OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(WaterQuitMgtController.class);
	
    @RequestMapping("waterQuitMgt")
    public String waterQuitMgt(Model model){
        //查询房间类型
        List<RoomTypeVo> roomTypeList = waterPurchaseMgtService.getRoomTypeList();
        model.addAttribute("roomTypeList", roomTypeList);
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("水表管理-退水管理");
        mol.setOperaContent("查看退水管理列表");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return "bus/watermeter-mgt/waterQuit-mgt/waterQuit-mgt";
    }

    /**
     * 获取退水房间列表
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
            logger.error("获取退水房间列表报错！", e);
        }
        return new Gson().toJson(waterMeterInfoList);
    }

    /**
     * 退水弹框
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("waterQuit")
    public String waterQuit(HttpServletRequest request, Model model){
        WaterMeterInfoVo waterMeterInfo = null;
        try {
            String id = request.getParameter("id");
            //若该房间存在未下发成功的退水指令、购水指令时，则不允退水，必须等待指令完成才能执行退水操作。
            String allow = waterQuitMgtService.checkAllowReturnWater(id);
            if(StringUtils.isNotBlank(allow)){
                model.addAttribute("errMsg",allow);
                return "bus/watermeter-mgt/waterPurchase-mgt/waterPurchase-notAllow";
            }else{
                waterMeterInfo = waterPurchaseMgtService.collectAndGetWaterMeterInfo(id);
                //计算剩余金额
                waterQuitMgtService.getAllowReturnWater(waterMeterInfo);
            }
        }catch (Exception e){
            logger.error("获取退水弹框数据报错！", e);
        }
        model.addAttribute("waterMeterInfo", waterMeterInfo);
        return "bus/watermeter-mgt/waterQuit-mgt/waterQuit";
    }

    /**
     * 确认退水
     * @param request
     * @return
     */
    @RequestMapping("returnConfirm")
    @ResponseBody
    public String returnConfirm(HttpServletRequest request){
        Message infoMessage;
        try {
            infoMessage = waterQuitMgtService.returnConfirm(request);
        }catch (Exception e){
            logger.error("确认退水报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"退水失败！");
        }
        return new Gson().toJson(infoMessage);
    }
}
