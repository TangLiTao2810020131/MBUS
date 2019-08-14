package com.ets.bus.waterMeterMgt.waterReset.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.bus.waterMeterMgt.waterQuitMgt.web.WaterQuitMgtController;
import com.ets.bus.waterMeterMgt.waterReset.service.WaterResetService;
import com.ets.common.FileUploadUrl;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/3/28
 * 水量清零管理控制器
 */
@Controller
@RequestMapping("waterreset")
public class WaterResetController {
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private WaterResetService waterResetService;
    @Autowired
    private OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(WaterResetController.class);

    @RequestMapping("waterResetIndex")
    public String waterResetIndex(Model model) {
        //查询房间类型
        List<RoomTypeVo> roomTypeList = waterPurchaseMgtService.getRoomTypeList();
        model.addAttribute("roomTypeList", roomTypeList);
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("水表管理-房间水量清零");
        mol.setOperaContent("查看房间水量清零列表");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return "bus/watermeter-mgt/water-reset/water-reset-index";
    }

    /**
     * 获取水量清零房间列表
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
            logger.error("获取水量清零房间列表报错！", e);
        }
        return new Gson().toJson(waterMeterInfoList);
    }

    /**
     * 清零弹框
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("waterReset")
    public String waterReset(HttpServletRequest request, Model model) {
        WaterMeterInfoVo waterMeterInfo = null;
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                waterMeterInfo = waterPurchaseMgtService.collectAndGetWaterMeterInfo(id);
                //设置房间类型名称
                waterPurchaseMgtService.setRoomTypeName(waterMeterInfo);
            }
        } catch (Exception e) {
            logger.error("获取清零弹框数据报错！", e);
        }
        model.addAttribute("waterMeterInfo", waterMeterInfo);
        return "bus/watermeter-mgt/water-reset/water-reset";
    }

    /**
     * 确认清零
     *
     * @param request
     * @return
     */
    @RequestMapping("resetConfirm")
    @ResponseBody
    public String resetConfirm(HttpServletRequest request) {
        Message infoMessage;
        try {
            infoMessage = waterResetService.resetConfirm(request);
        } catch (Exception e) {
            logger.error("确认清零报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL, "确认清零失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 导入清零弹框
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("waterResetImport")
    public String waterResetImport(HttpServletRequest request, Model model) {
        WaterMeterInfoVo waterMeterInfo = null;
        try {
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                waterMeterInfo = waterPurchaseMgtService.getWaterMeterInfoById(id);
            }
        } catch (Exception e) {
            logger.error("获取导入清零框数据报错！", e);
        }
        model.addAttribute("waterMeterInfo", waterMeterInfo);
        return "bus/watermeter-mgt/water-reset/water-reset-import";
    }

    /**
     * 下载模板
     *
     * @param request
     * @return
     */
    @RequestMapping("download")
    public String download(HttpServletRequest request, HttpServletResponse response, String fileName) {
        try{
            waterPurchaseMgtService.download(MyConstant.EXCEL_TEM_RESETWATER,request,response);
        }catch (Exception e){
            logger.error("下载清零导入模版报错！", e);
        }
        return null;
    }

    /**
     * 导入清零
     * @param excelFile
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile excelFile) {
        Message infoMessage;
        try{
            infoMessage = waterResetService.upload(excelFile);;
        }catch(Exception e){
            logger.error("导入清零报错!", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"导入清零失败！");
        }
        return new Gson().toJson(infoMessage);
    }
}
