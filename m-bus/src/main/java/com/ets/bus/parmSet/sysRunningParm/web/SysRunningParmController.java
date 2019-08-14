package com.ets.bus.parmSet.sysRunningParm.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ets.bus.parmSet.sysRunningParm.entity.SysRunningParm;
import com.ets.bus.parmSet.sysRunningParm.service.SysRunningParmService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.Message;
import com.ets.utils.UUIDUtils;
import com.google.gson.Gson;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.UUID;

/**
 * @author 吴浩
 * @create 2019-01-08 14:04
 */
@Controller
@RequestMapping("sysrunningparm")
public class SysRunningParmController {
    @Resource
    SysRunningParmService sysRunningParmService;
    @Autowired
    OperationLogService operationLogService;
    /**
     * 系统运行参数 默认页面展示 系统运行参数只有一条数据
     * @param request
     * @return
     */
    @RequestMapping("sysRunningParm")
    public String sysRunningParm(HttpServletRequest request)
    {
        SysRunningParm sysRunningParm = sysRunningParmService.info();
        request.setAttribute("sysRunningParm",sysRunningParm);
        return "bus/parm-set/sysRunning-parm/sysRunning-parm";
    }
    /**
     * 编辑系统运行参数
     * 页面参数未发生改变 则提示参数未进行修改
     * 页面参数发生改变 则进行编辑
     * @param sysRunningParm
     * @return
     */
    @RequestMapping(value="saveSysrunningparm", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String saveSysrunningparm(SysRunningParm sysRunningParm)
    {
        Gson gson=new Gson();
        SysRunningParm dbSysRunningParm = sysRunningParmService.getSysRunningParmById(sysRunningParm.getId());
        //验证是否修改了参数

        if (sysRunningParm.getWarn_water().equals(dbSysRunningParm.getWarn_water()) && sysRunningParm.getEffect_time().equals(dbSysRunningParm.getEffect_time()) && sysRunningParm.getAdd_cycle() .equals(dbSysRunningParm.getAdd_cycle())  && sysRunningParm.getReturn_money().equals(dbSysRunningParm.getReturn_money())  && sysRunningParm.getHoard_water() .equals(dbSysRunningParm.getHoard_water())  && sysRunningParm.getOver_water() .equals(dbSysRunningParm.getOver_water())  && sysRunningParm.getValve_water() .equals(dbSysRunningParm.getValve_water()) && sysRunningParm.getBuy_money().equals(dbSysRunningParm.getBuy_money()) ){
            return gson.toJson(new Message("2","参数未进行修改!"));
        }
        if (sysRunningParm != null && !sysRunningParm.equals("")){
        //新增参数前更新默认的系统运行参数的del_status为1并且effect_status为0
        sysRunningParmService.updateSysRunningParmById(sysRunningParm.getId());
        }
        //新增新添系统运行参数
        sysRunningParm.setId(UUIDUtils.getUUID());
        sysRunningParm.setCreate_time(DateTimeUtils.getnowdate());
        sysRunningParm.setDel_status(0);
        sysRunningParm.setEffect_status(1);
        sysRunningParmService.addSysRunningParm(sysRunningParm);
        //添加编辑系统运行参数的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("参数设置-系统运行参数");
        operationLog.setOperaContent("编辑系统运行参数");
        operationLogService.addLog(operationLog);
        return gson.toJson(new Message("1","操作成功!"));
    }


}
