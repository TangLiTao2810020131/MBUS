package com.ets.bus.reportQuery.web;

import com.ets.bus.reportQuery.entity.report.ReplaceRoomRecordVo;
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
 * 控水换房记录
 */
@Controller
@RequestMapping("replaceRoomRecord")
public class ReplaceRoomRecordController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(ReplaceRoomRecordController.class);

    //报表查询-用水换房明细
    @RequestMapping("index")
    public String index(){
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("报表查询-用水换房明细");
        operationLog.setOperaContent("查看用水换房明细列表");
        operationLogService.addLog(operationLog);return "bus/report-query/wateruse-change-room-dtls";
    }

    /**
     * 获取控水换房列表
     * @param request
     * @return
     */
    @RequestMapping(value = "listData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(HttpServletRequest request) {

        PageListData<ReplaceRoomRecordVo> replaceRoomRecordList = null;
        try {
            Map<String, Object> map = reportService.getReqSearchParam(request);
            int page = request.getParameter(MyConstant.PAGE_KEY) != null?Integer.parseInt(request.getParameter(MyConstant.PAGE_KEY)):MyConstant.PAGE_DEFULT;
            int limit = request.getParameter(MyConstant.LIMIT_KEY) != null? Integer.parseInt(request.getParameter(MyConstant.LIMIT_KEY)):MyConstant.LIMIT_DEFULT;
            //查询数据
            replaceRoomRecordList = reportService.getReplaceRoomRecordList(page, limit, map);

        }catch (Exception e){
            logger.error("获取控水换房列表报错！", e);
        }
        return new Gson().toJson(replaceRoomRecordList);
    }

    //根据id查询控水换房明细
    @RequestMapping("/findReplaceRoomRecord.action")
    public String findReplaceRoomRecord(HttpServletRequest request,String id)
    {
        //查询旧房间信息
        ReplaceRoomRecordVo br=reportService.findReplaceRoomRecord(id);
        //查询新房间信息
        ReplaceRoomRecordVo newRoomInfo=reportService.findReplaceNewRoomRecord(id);
        request.setAttribute("br",br);
        request.setAttribute("newRoomInfo",newRoomInfo);
        return "bus/report-query/wateruse-change-room-find";
    }

    //表格数据导出
    @RequestMapping("/export.action")
    public void export(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        reportService.exportReplaceRoomRecord(request,response,str,name);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("报表查询-控水换房明细");
        operationLog.setOperaContent("控水换房明细导出");
        operationLogService.addLog(operationLog);
    }
}
