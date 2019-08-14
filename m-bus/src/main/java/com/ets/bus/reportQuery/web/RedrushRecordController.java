package com.ets.bus.reportQuery.web;

import com.ets.bus.reportQuery.entity.report.RedRushVo;
import com.ets.bus.reportQuery.entity.report.WaterDailyVo;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/4/4
 * 冲红记录查询
 */
@Controller
@RequestMapping("redrushRecord")
public class RedrushRecordController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(RedrushRecordController.class);


    //报表查询-冲红历史记录
    @RequestMapping("index")
    public String index(Model model){
        //查询房间类型
        List<RoomTypeVo> roomTypeList = waterPurchaseMgtService.getRoomTypeList();
        model.addAttribute("roomTypeList", roomTypeList);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("报表查询-冲红记录");
        operationLog.setOperaContent("查看冲红记录列表");
        operationLogService.addLog(operationLog);
        return "bus/report-query/waterDtl-redrush";
    }
    /**
     * 获取冲红列表
     * @param request
     * @return
     */
    @RequestMapping(value = "listData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(HttpServletRequest request) {
        PageListData<RedRushVo> redRushVoList = null;
        try {
            Map<String, Object> map = reportService.getReqSearchParam(request);
            int page = request.getParameter(MyConstant.PAGE_KEY) != null?Integer.parseInt(request.getParameter(MyConstant.PAGE_KEY)):MyConstant.PAGE_DEFULT;
            int limit = request.getParameter(MyConstant.LIMIT_KEY) != null? Integer.parseInt(request.getParameter(MyConstant.LIMIT_KEY)):MyConstant.LIMIT_DEFULT;
            //查询数据
            redRushVoList = reportService.getRedrushList(page, limit, map);

        }catch (Exception e){
            logger.error("获取冲红列表报错！", e);
        }
        return new Gson().toJson(redRushVoList);
    }

    /**
     * 通过冲红记录表ID查询冲红记录信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/findRedRecord.action")
    public String findRedRecord(HttpServletRequest request,String id)
    {
        RedRushVo rr=reportService.findRedRecord(id);
        request.setAttribute("br",rr);
        return "bus/report-query/waterDtl-redrush-find";
    }

    /**
     * 冲红记录数据导出
     * @param request
     * @param response
     * @param str
     * @param name
     */
    @RequestMapping("/export.action")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response,String str,String name)
    {
        reportService.exportRedrush(request,response,str,name);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("报表查询-冲红记录");
        operationLog.setOperaContent("冲红记录导出");
        operationLogService.addLog(operationLog);

    }
}
