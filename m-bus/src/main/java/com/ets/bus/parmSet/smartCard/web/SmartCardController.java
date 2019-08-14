package com.ets.bus.parmSet.smartCard.web;

import javax.servlet.http.HttpServletRequest;

import com.ets.bus.parmSet.roomTypeParm.entity.RoomType;
import com.ets.bus.parmSet.smartCard.entity.CardTerminalVo;
import com.ets.bus.parmSet.smartCard.service.SmartCardService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.common.MyConstant;
import com.ets.utils.PageListData;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/4/15
 * 一卡通终端参数设置
 */
@Controller
@RequestMapping("smartcard")
public class SmartCardController {

    @Autowired
    private SmartCardService smartCardService;
    @Autowired
    private OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(SmartCardController.class);
    String moduleName="参数设置-一卡通终端";

    /**
     * 一卡通终端列表
     * @param request
     * @return
     */
    @RequestMapping("smartCard")
    public String smartCard(HttpServletRequest request){
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setOperaContent("查看一卡通终端列表");
        operationLog.setModuleName(moduleName);
        operationLogService.addLog(operationLog);
        return "bus/parm-set/smartCard/smartCard";
    }

    /**
     * layui 表格数据
     * @param request
     * @return
     */
    @RequestMapping(value = "listData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(HttpServletRequest request) {

        PageListData<CardTerminalVo> cardTerminalList = null;
        try {
            Map<String, Object> map = smartCardService.getReqSearchParam(request);
            int page = request.getParameter(MyConstant.PAGE_KEY) != null?Integer.parseInt(request.getParameter(MyConstant.PAGE_KEY)):MyConstant.PAGE_DEFULT;
            int limit = request.getParameter(MyConstant.LIMIT_KEY) != null? Integer.parseInt(request.getParameter(MyConstant.LIMIT_KEY)):MyConstant.LIMIT_DEFULT;
            //查询数据
            cardTerminalList = smartCardService.getCardTerminalList(page, limit, map);

        }catch (Exception e){
            logger.error("获取一卡通终端列表报错！", e);
        }
        return new Gson().toJson(cardTerminalList);
    }

    /**
     * 跳转到一卡通终端添加页面
     * @param request
     * @return
     */
    @RequestMapping("add")
    public String add(HttpServletRequest request){
        return "bus/parm-set/smartCard/smartCard-add";
    }

    /**
     * 添加一卡通终端逻辑实现
     * @param request
     * @param ctv
     * @return
     */
    @RequestMapping("/addSmartCard.action")
    @ResponseBody
    public String addSmartCard(HttpServletRequest request,CardTerminalVo ctv)
    {
        smartCardService.addSmartCard(ctv);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("新增编号为["+ctv.getTerminalNum()+"]终端");
        operationLogService.addLog(operationLog);
        return new Gson().toJson("添加成功");
    }

    /**
     * 编辑一卡通终端页面
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("edit")
    public String edit(HttpServletRequest request,String id)
    {
        CardTerminalVo cv=smartCardService.findCardTerminalVoById(id);
        request.setAttribute("cv",cv);
        return "bus/parm-set/smartCard/smartCard-edit";
    }

    /**
     * 编辑一卡通终端逻辑实现
     * @param request
     * @param cv
     * @return
     */
    @RequestMapping("/editSmartCard.action")
    @ResponseBody
    public String editSmartCard(HttpServletRequest request,CardTerminalVo cv)
    {
        smartCardService.editSmartCard(cv);
        request.setAttribute("br",cv);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("编辑编号为["+cv.getTerminalNum()+"]终端");
        operationLogService.addLog(operationLog);
        return new  Gson().toJson("编辑成功");
    }

    //删除(逻辑删除)
    @RequestMapping("/delSmartCard.action")
    @ResponseBody
    public String delSmartCard(HttpServletRequest request,String str)
    {
        String[] ids=str.split(",");
        //添加删除日志
        StringBuilder sb=new StringBuilder();
        for(String st:ids){
            CardTerminalVo cardTerminalVo = smartCardService.findCardTerminalVoById(st);
            sb.append(cardTerminalVo .getTerminalNum()+',');
        }
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("删除编号为["+sb.substring(0,sb.length()-1)+"]一卡通终端");
        operationLogService.addLog(operationLog);
        smartCardService.updatedelStatus(str.split(","));
        return new Gson().toJson("删除成功");
    }

    //启用 禁用
    @RequestMapping("/startAndEnd.action")
    @ResponseBody
    public String startAndEnd(HttpServletRequest request,int flag,String str)
    {
        String result=smartCardService.updateUseStatus(flag,str);
        return new Gson().toJson(result);
    }

    //验证终端编号是否重复
    @RequestMapping("/checkTerminalNum.action")
    @ResponseBody
    public String checkTerminalNum(HttpServletRequest request,String terminalNum)
    {
        CardTerminalVo cv=smartCardService.checkTerminalNum(terminalNum);
        if(cv==null){
            return new Gson().toJson("0");//不存在
        }else{
            return new Gson().toJson("1");//存在
        }
    }

    //验证终端编号是否重复
    @RequestMapping("/checkTerminalAddress.action")
    @ResponseBody
    public String checkTerminalAddress(HttpServletRequest request,String terminalAddress)
    {
        CardTerminalVo cv=smartCardService.checkTerminalAddress(terminalAddress);
        if(cv==null){
            return new Gson().toJson("0");//不存在
        }else{
            return new Gson().toJson("1");//存在
        }
    }
}
