package com.ets.bus.reportQuery.web;

import com.ets.bus.reportQuery.entity.report.HistoryCommandVo;
import com.ets.bus.reportQuery.entity.report.RedRushVo;
import com.ets.bus.reportQuery.service.ReportService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
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
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("historyCommand")
public class HistoryCommandController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(RedrushRecordController.class);

    //报表查询-历史命令
    @RequestMapping("historyCommand")
    public String index(Model model){
        //查询房间类型
        List<RoomTypeVo> roomTypeList = waterPurchaseMgtService.getRoomTypeList();
        model.addAttribute("roomTypeList", roomTypeList);
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-历史命令");
        mol.setOperaContent("查看历史命令列表");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return "bus/report-query/historyCommand/historyCommand-dtls";
    }
    /**
     * 获取历史命令的列表
     * @param request
     * @return
     */
    @RequestMapping(value = "listData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(HttpServletRequest request) {
        PageListData<HistoryCommandVo> historyCommandList = null;
        try {
            Map<String, Object> map = reportService.getReqSearchParam(request);
            int page = request.getParameter(MyConstant.PAGE_KEY) != null?Integer.parseInt(request.getParameter(MyConstant.PAGE_KEY)):MyConstant.PAGE_DEFULT;
            int limit = request.getParameter(MyConstant.LIMIT_KEY) != null? Integer.parseInt(request.getParameter(MyConstant.LIMIT_KEY)):MyConstant.LIMIT_DEFULT;
            //查询数据
            // redRushVoList = reportService.getRedrushList(page, limit, map);
             historyCommandList = reportService.getHistoryCommandList(page, limit, map);

        }catch (Exception e){
            logger.error("获取历史命令报错！", e);
        }
        return new Gson().toJson(historyCommandList);
    }

    //查看历史命令
    @RequestMapping("/findHistoryCommand.action")
    public String findRedRecord(HttpServletRequest request, String id)
    {
        HistoryCommandVo hc = reportService.findHistoryCommand(id);
        request.setAttribute("hc",hc);
        return "bus/report-query/historyCommand/historyCommand-dtls-find";
    }

    //数据表格导出
    @RequestMapping("/export.action")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        reportService.exportHistoryCommand(request,response,str,name);
    }

    /**
     * 重新下发命令
     * @param request
     * @return
     */
    @RequestMapping(value = "again", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String again(HttpServletRequest request) {
        Message infoMessage = new Message(MyConstant.MSG_FAIL,"操作失败！");
        try {
            String id = request.getParameter("id");
            if(StringUtils.isNotBlank(id)){
                infoMessage = waterPurchaseMgtService.again(id);
            }
        }catch (Exception e){
            logger.error("重新下发命令报错！", e);
        }
        return new Gson().toJson(infoMessage);
    }


}

