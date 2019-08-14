package com.ets.bus.reportQuery.web;

import com.ets.bus.reportQuery.entity.report.WaterDailyVo;
import com.ets.bus.reportQuery.service.ReportService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.common.MyConstant;
import com.ets.utils.PageListData;
import com.ets.utils.UUIDUtils;
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
import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/4/4
 * 用水房间日报
 */
@Controller
@RequestMapping("roomDaily")
public class RoomDailyController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(RoomDailyController.class);

    //报表查询-用水房间日报
    @RequestMapping("index")
    public String index(Model model){
        //查询房间类型
        List<RoomTypeVo> roomTypeList = waterPurchaseMgtService.getRoomTypeList();
        model.addAttribute("roomTypeList", roomTypeList);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("报表查询-用水房间日报");
        operationLog.setOperaContent("查看用水房间日报列表");
        operationLogService.addLog(operationLog);

        return "bus/report-query/wateruse-room-daily";
    }

    /**
     * 获取日报列表
     * @param request
     * @return
     */
    @RequestMapping(value = "listData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(HttpServletRequest request) {

        PageListData<WaterDailyVo> waterDailyList = null;
        try {
            Map<String, Object> map = reportService.getReqSearchParam(request);
            int page = request.getParameter(MyConstant.PAGE_KEY) != null?Integer.parseInt(request.getParameter(MyConstant.PAGE_KEY)):MyConstant.PAGE_DEFULT;
            int limit = request.getParameter(MyConstant.LIMIT_KEY) != null? Integer.parseInt(request.getParameter(MyConstant.LIMIT_KEY)):MyConstant.LIMIT_DEFULT;
            //查询数据
            waterDailyList = reportService.getRoomDailyList(page, limit, map);

        }catch (Exception e){
            logger.error("获取日报列表报错！", e);
        }
        return new Gson().toJson(waterDailyList);
    }

    //根据id查询用水房间日报
    @RequestMapping("/findRoomDaily.action")
    public String findRoomDaily(HttpServletRequest request,String id)
    {
        WaterDailyVo br=reportService.findRoomDaily(id);
        request.setAttribute("br",br);
        return "bus/report-query/wateruse-room-dailt-find";
    }

    //表格数据导出
    @RequestMapping("/export.action")
    public void export(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        reportService.exportRoomDaily(request,response,str,name);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("报表查询-用水房间日报");
        operationLog.setOperaContent("用水房间日报导出");
        operationLogService.addLog(operationLog);
    }

    @RequestMapping("/test.action")
    public void test(HttpServletRequest request)
    {
        reportService.insertDaily();

    }
}
