package com.ets.bus.waterMeterMgt.waterAddMgt.web;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterAddMgt.service.WaterAddMgtService;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.common.MyConstant;
import com.ets.utils.Message;
import com.ets.utils.PageListData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/3/27
 * 补水管理控制器
 */
@Controller
@RequestMapping("wateraddmgt")
@SuppressWarnings("all")
public class WaterAddMgtController {

    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private WaterAddMgtService waterAddMgtService;
    @Autowired
    OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(WaterAddMgtController.class);

    @RequestMapping("waterAddMgt")
    public String waterAddMgt(Model model){
        //查询房间类型
        List<RoomTypeVo> roomTypeList = waterPurchaseMgtService.getRoomTypeList();
        model.addAttribute("roomTypeList", roomTypeList);
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("水表管理-补水管理");
        mol.setOperaContent("查看补水管理列表");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return "bus/watermeter-mgt/waterAdd-mgt/waterAdd-mgt";
    }

    /**
     * 获取补水房间列表
     * @param request
     * @return
     */
    @RequestMapping(value = "listData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(HttpServletRequest request) {

        PageListData<WaterMeterInfoVo> waterMeterInfoList = null;
        try {
            Map<String, Object> map = waterPurchaseMgtService.getReqSearchParam(request);
            int page = request.getParameter(MyConstant.PAGE_KEY) != null?Integer.parseInt(request.getParameter(MyConstant.PAGE_KEY)): MyConstant.PAGE_DEFULT;
            int limit = request.getParameter(MyConstant.LIMIT_KEY) != null? Integer.parseInt(request.getParameter(MyConstant.LIMIT_KEY)): MyConstant.LIMIT_DEFULT;
            //查询数据
            waterMeterInfoList = waterPurchaseMgtService.getWaterMeterInfoList(page, limit, map);

        }catch (Exception e){
            logger.error("获取补水房间列表报错！", e);
        }
        return new Gson().toJson(waterMeterInfoList);
    }

    /**
     * 按房间补水弹框
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("waterAddRoom")
    public String waterAddRoom(HttpServletRequest request, Model model){
        WaterMeterInfoVo waterMeterInfo = null;
        try {
            String id = request.getParameter("id");
            //若当前房间存在未下发成功的水量（包含补水或购水等）或者补水周期内已补过水
            String allow = waterAddMgtService.checkAllowAddWater(id);
            if(StringUtils.isNotBlank(allow)){
                model.addAttribute("errMsg",allow);
                return "bus/watermeter-mgt/waterPurchase-mgt/waterPurchase-notAllow";
            }else{
                waterMeterInfo = waterPurchaseMgtService.collectAndGetWaterMeterInfo(id);
                waterPurchaseMgtService.allowWater(waterMeterInfo);
            }
        }catch (Exception e){
            logger.error("获取按房间补水弹框数据报错！", e);
        }
        model.addAttribute("waterMeterInfo", waterMeterInfo);
        return "bus/watermeter-mgt/waterAdd-mgt/waterAdd-room";
    }

    /**
     * 确认补水
     * @param request
     * @return
     */
    @RequestMapping("addConfirm")
    @ResponseBody
    public String addConfirm(HttpServletRequest request){
        Message infoMessage;
        try {
            infoMessage = waterAddMgtService.addConfirm(request);
        }catch (Exception e){
            logger.error("确认补水报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"补水失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 按楼层补水弹框
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("waterAddBuild")
    public String waterAddBuild(HttpServletRequest request, Model model){
        WaterMeterInfoVo waterMeterInfo = null;
        List<RoomTypeVo> roomTypeList = null;
        try {
            String id = request.getParameter("id");
            waterMeterInfo = waterPurchaseMgtService.getWaterMeterInfoById(id);
            waterPurchaseMgtService.allowWater(waterMeterInfo);
            roomTypeList = waterAddMgtService.getRoomTypeByFloorId(waterMeterInfo.getFloorId());
        }catch (Exception e){
            logger.error("获取按楼层补水弹框数据报错！", e);
        }
        model.addAttribute("waterMeterInfo", waterMeterInfo);
        model.addAttribute("roomTypeList", roomTypeList);
        return "bus/watermeter-mgt/waterAdd-mgt/waterAdd-build";
    }

    /**
     * 按楼层确认补水
     * @param request
     * @return
     */
    @RequestMapping("addFloorConfirm")
    @ResponseBody
    public String addFloorConfirm(HttpServletRequest request){
        Message infoMessage;
        try {
            infoMessage = waterAddMgtService.addFloorConfirm(request);
        }catch (Exception e){
            logger.error("按楼层确认补水报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"补水失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 按导入补水弹框
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("waterAddImport")
    public String waterAddImport(HttpServletRequest request, Model model){
        WaterMeterInfoVo waterMeterInfo = null;
        try {
            String id = request.getParameter("id");
            if(StringUtils.isNotBlank(id)){
                waterMeterInfo = waterPurchaseMgtService.getWaterMeterInfoById(id);
            }
        }catch (Exception e){
            logger.error("获取按导入补水弹框据报错！", e);
        }
        model.addAttribute("waterMeterInfo", waterMeterInfo);
        return "bus/watermeter-mgt/waterAdd-mgt/waterAdd-import";
}

    /**
     * 下载模版
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/download.action")
    public String download(HttpServletRequest request, HttpServletResponse response) {
        try{
            waterPurchaseMgtService.download(MyConstant.EXCEL_TEM_ADDWATER,request,response);
        }catch (Exception e){
            logger.error("下载补水导入模版报错!", e);
        }
        return null;
    }

    /**
     * 补水导入文件EXCEL
     * @return
     */
    @RequestMapping("/excelUpload.action")
    @ResponseBody
    public String excelUpload(@RequestParam("file") MultipartFile excelFile){
        Message infoMessage;
        try{
            infoMessage = waterAddMgtService.excelUpload(excelFile);
        }catch(Exception e){
            logger.error("补水导入报错!", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"补水导入失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 根据楼层id查询楼层中所有的房间
     */
    @RequestMapping("/getRoomsByFloorId.action")
    @ResponseBody
    public PageListData<WaterMeterInfoVo> getRoomsByFloorId(HttpServletRequest request){
        PageHelper.startPage(Integer.parseInt(request.getParameter("page")),
                Integer.parseInt(request.getParameter("limit")));
        String str=(String)request.getParameter("floorId");
        List<WaterMeterInfoVo> list=waterAddMgtService.getRoomsByFloorId(request.getParameter("floorId"));
        PageInfo<WaterMeterInfoVo> pageInfo = new PageInfo<WaterMeterInfoVo>(list);
        PageListData<WaterMeterInfoVo> pageData = new PageListData<WaterMeterInfoVo>();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(list);
        return pageData;
    }
}
