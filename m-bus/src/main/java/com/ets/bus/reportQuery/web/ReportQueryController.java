package com.ets.bus.reportQuery.web;

import javax.servlet.http.HttpServletRequest;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 吴浩
 * @create 2019-01-08 14:04
 */
@Controller
@RequestMapping("reportquery")
public class ReportQueryController {

    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private OperationLogService operationLogService;


	String baseUrl = "bus/report-query/";
	
	//报表查询-补水记录
    @RequestMapping("waterAddDtls")
    public String waterAddDtls(HttpServletRequest request)
    {
        List<RoomTypeVo> roomTypeList=waterPurchaseMgtService.getRoomTypeList();
        request.setAttribute("roomTypeList",roomTypeList);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("报表查询-补水记录");
        operationLog.setOperaContent("查看补水记录列表");
        operationLogService.addLog(operationLog);
        return baseUrl + "waterAdd-dtls";
    }
    
    //报表查询-用水明细记录
    @RequestMapping("waterDtlRecord")
    public String waterDtlRecord(HttpServletRequest request)
    {
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("报表查询-用水明细记录");
        operationLog.setOperaContent("查看用水明细记录列表");
        operationLogService.addLog(operationLog);
        return baseUrl + "waterDtl-record";
    }
    
    //报表查询-换表历史记录
    @RequestMapping("watermeterChange")
    public String watermeterChange(HttpServletRequest request)
    {
        return baseUrl + "watermeter-change";
    }
    
    
    //报表查询-购水记录
    @RequestMapping("waterPayRecord")
    public String waterPayRecord(HttpServletRequest request)
    {
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("报表查询-购水记录");
        operationLog.setOperaContent("查看购水记录列表");
        operationLogService.addLog(operationLog);
        return baseUrl + "waterPay-record";
    }
    
    
    //报表查询-退水记录
    @RequestMapping("waterQuitDtls")
    public String waterQuitDtls(HttpServletRequest request)
    {
        List<RoomTypeVo> roomTypeList=waterPurchaseMgtService.getRoomTypeList();
        request.setAttribute("roomTypeList",roomTypeList);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("报表查询-购水记录");
        operationLog.setOperaContent("查看购水记录列表");
        operationLogService.addLog(operationLog);
        return baseUrl + "waterQuit-dtls";
    }
    
    //报表查询-用水换房明细
    @RequestMapping("wateruseChangeRoomDtls")
    public String wateruseChangeRoomDtls(HttpServletRequest request)
    {

        return baseUrl + "wateruse-change-room-dtls";
    }
    
    
    //报表查询-用水房间日报
    @RequestMapping("wateruseRoomDaily")
    public String wateruseRoomDaily(HttpServletRequest request)
    {


        return baseUrl + "wateruse-room-daily";
    }
    
    
    //报表查询-用水房间月报
    @RequestMapping("wateruseRoomMonthly")
    public String wateruseRoomMonthly(HttpServletRequest request)
    {


        return baseUrl + "wateruse-room-monthly";
    }



}
