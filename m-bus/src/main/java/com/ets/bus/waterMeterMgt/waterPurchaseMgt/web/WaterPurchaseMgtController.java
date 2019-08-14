package com.ets.bus.waterMeterMgt.waterPurchaseMgt.web;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.OnlineSituationVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/3/26
 * 购水管理控制器
 */
@Controller
@RequestMapping("waterpurchasemgt")
@SuppressWarnings("all")
public class WaterPurchaseMgtController {
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(WaterPurchaseMgtController.class);

    @RequestMapping("waterPurchaseMgt")
    public String waterPurchaseMgt(Model model) {
        //查询房间类型
        List<RoomTypeVo> roomTypeList = waterPurchaseMgtService.getRoomTypeList();
        model.addAttribute("roomTypeList", roomTypeList);
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("水表管理-购水管理");
        mol.setOperaContent("查看购水管理列表");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return "bus/watermeter-mgt/waterPurchase-mgt/waterPurchase-mgt";
    }

    /**
     * 获取购水房间列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "listData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(HttpServletRequest request) {

        PageListData<WaterMeterInfoVo> waterMeterInfoList = null;
        try {
            Map<String, Object> map = waterPurchaseMgtService.getReqSearchParam(request);
            int page = request.getParameter(MyConstant.PAGE_KEY) != null ? Integer.parseInt(request.getParameter(MyConstant.PAGE_KEY)) : MyConstant.PAGE_DEFULT;
            int limit = request.getParameter(MyConstant.LIMIT_KEY) != null ? Integer.parseInt(request.getParameter(MyConstant.LIMIT_KEY)) : MyConstant.LIMIT_DEFULT;
            //查询数据
            waterMeterInfoList = waterPurchaseMgtService.getWaterMeterInfoList(page, limit, map);

        } catch (Exception e) {
            logger.error("获取购水房间列表报错！", e);
        }
        return new Gson().toJson(waterMeterInfoList);
    }

    /**
     * 购水弹框
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("buyIndex")
    public String buyIndex(HttpServletRequest request, Model model) {
        WaterMeterInfoVo waterMeterInfo = null;
        try {
            String id = request.getParameter("id");
            //若当前房间存在未下发成功的水量（包含补水或购水等），则不允许继续购水。
            String allow = waterPurchaseMgtService.checkAllowBuyWater(id);
            if (StringUtils.isNotBlank(allow)) {
                model.addAttribute("errMsg", allow);
                return "bus/watermeter-mgt/waterPurchase-mgt/waterPurchase-notAllow";
            } else {
                waterMeterInfo = waterPurchaseMgtService.collectAndGetWaterMeterInfo(id);

                waterPurchaseMgtService.allowWater(waterMeterInfo);
            }
        } catch (Exception e) {
            logger.error("获取购水弹框数据报错！", e);
        }
        model.addAttribute("waterMeterInfo", waterMeterInfo);
        return "bus/watermeter-mgt/waterPurchase-mgt/waterPurchase-cash";
    }

    /**
     * 确认购水
     *
     * @param request
     * @return
     */
    @RequestMapping("buyConfirm")
    @ResponseBody
    public String buyConfirm(HttpServletRequest request) {
        Message infoMessage;
        try {
            infoMessage = waterPurchaseMgtService.buyConfirm(request);
        } catch (Exception e) {
            logger.error("确认购水报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL, "购水失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 集中器在线情况
     */
    @RequestMapping("onlineSituation")
    @ResponseBody
    public String onlineSituation(HttpServletRequest request) {
        List<OnlineSituationVo> onlineSit = null;
        try {
            onlineSit = waterPurchaseMgtService.onlineSituation();
        } catch (Exception e) {
            logger.error("获取集中器在线情况报错！", e);
        }
        return new Gson().toJson(onlineSit);
    }

}
