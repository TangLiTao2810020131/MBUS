package com.ets.bus.reportQuery.web;

import com.ets.bus.reportQuery.entity.report.ReplaceRecordVo;
import com.ets.bus.reportQuery.service.ReportService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.common.MyConstant;
import com.ets.utils.PageListData;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/4/4
 * 换表历史记录
 */
@Controller
@RequestMapping("replaceRecord")
public class ReplaceRecordController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(ReplaceRecordController.class);


    //报表查询-换表历史记录
    @RequestMapping("index")
    public String index(Model model){
        //查询房间类型
        List<RoomTypeVo> roomTypeList = waterPurchaseMgtService.getRoomTypeList();
        model.addAttribute("roomTypeList", roomTypeList);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("报表查询-换表记录");
        operationLog.setOperaContent("查看换表记录列表");
        operationLogService.addLog(operationLog);
        return "bus/report-query/watermeter-change";
    }

    /**
     * 获取换表列表
     * @param request
     * @return
     */
    @RequestMapping(value = "listData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(HttpServletRequest request) {

        PageListData<ReplaceRecordVo> replaceRecordList = null;
        try {
            Map<String, Object> map = reportService.getReqSearchParam(request);
            int page = request.getParameter(MyConstant.PAGE_KEY) != null?Integer.parseInt(request.getParameter(MyConstant.PAGE_KEY)):MyConstant.PAGE_DEFULT;
            int limit = request.getParameter(MyConstant.LIMIT_KEY) != null? Integer.parseInt(request.getParameter(MyConstant.LIMIT_KEY)):MyConstant.LIMIT_DEFULT;
            //查询数据
            replaceRecordList = reportService.getReplaceRecordList(page, limit, map);

        }catch (Exception e){
            logger.error("获取换表列表报错！", e);
        }
        return new Gson().toJson(replaceRecordList);
    }

    /**
     * 根据换表记录表ID查看换表历史记录
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/findReplaceWaterRecord.action")
    public String findReplaceWaterRecord(HttpServletRequest request,String id)
    {
        ReplaceRecordVo br=reportService.findReplaceWaterRecord(id);
        request.setAttribute("br",br);
        return "bus/report-query/watermeter-change-find";
    }

    /**
     * 换表记录数据导出
     * @param request
     * @param response
     * @param str
     * @param name
     */
    @RequestMapping("/export.action")
    public void export(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        reportService.exportReplaceRecord(request,response,str,name);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("报表查询-换表记录");
        operationLog.setOperaContent("换表记录记录导出");
        operationLogService.addLog(operationLog);
    }
}
