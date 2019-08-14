package com.ets.bus.systemMgt.clctApplMgt.web;

import javax.servlet.http.HttpServletRequest;

import com.ets.bus.parmSet.roomTypeParm.entity.RoomType;
import com.ets.bus.parmSet.smartCard.entity.CardTerminalVo;
import com.ets.bus.reportQuery.entity.buywaterrecord.BuyWaterRecord;
import com.ets.bus.systemMgt.clctApplMgt.entity.ClctApplMgt;
import com.ets.bus.systemMgt.clctApplMgt.service.ClctApplMgtService;

import com.ets.bus.systemMgt.concentrator.entity.Concentrator;
import com.ets.bus.systemMgt.concentrator.service.ConcentratorService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.utils.PageListData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 14:04
 */
@Controller
@RequestMapping("clctapplmgt")
public class ClctApplMgtController {

    @Autowired
    ClctApplMgtService clctApplMgtService;
    @Autowired
    ConcentratorService concentratorService;
    @Autowired
    OperationLogService operationLogService;
    String moduleName="系统管理-采集管理";
	
    @RequestMapping("clctApplMgt")
    public String clctApplMgt(HttpServletRequest request)
    {
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看采集应用列表");
        operationLogService.addLog(operationLog);
        return "bus/system-mgt/clct-appl-mgt/clct-appl-mgt";
    }

    @RequestMapping("/listData.action")
    @ResponseBody
    public String listData(HttpServletRequest request, ClctApplMgt clctApplMgt, int page, int limit)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("page", (page)*limit);
        map.put("limit", (page-1)*limit);
        map.put("collect_address",request.getParameter("collect_address"));
        map.put("application_number",request.getParameter("application_number"));
        map.put("collect_name",request.getParameter("collect_name"));
        PageHelper.startPage(page,limit);
        List<ClctApplMgt> bList=clctApplMgtService.getClctApplMgts(map);
        PageInfo<ClctApplMgt> pageInfo=new PageInfo<ClctApplMgt>(bList);
        List<ClctApplMgt> list=new ArrayList<ClctApplMgt>();
        //根据采集id查询命令编号
        for(ClctApplMgt c:bList)
        {
            String number=clctApplMgtService.findInstruction(c.getId());
            c.setInstruction_number(number);
            list.add(c);
        }
        PageListData<ClctApplMgt> pageData=new PageListData();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(list);
        Gson gson = new Gson();
        String listJson = gson.toJson(pageData);
        return listJson;
    }

    //跳转到添加页面
    @RequestMapping("/addClctApplMgtPage.action")
    public String addClctApplMgtPage(HttpServletRequest request)
    {
        return "bus/system-mgt/clct-appl-mgt/add";
    }

    //添加实现部分
    @RequestMapping("/addClctApplMgt.action")
    @ResponseBody
    public String addClctApplMgt(HttpServletRequest request,ClctApplMgt clctApplMgt)
    {
        clctApplMgtService.addClctApplMgt(clctApplMgt);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("添加采集应用编号为["+clctApplMgt.getApplication_number()+"]采集应用");
        operationLogService.addLog(operationLog);
        Gson gson = new Gson();
        String json = gson.toJson("新增成功!");
        return json;
    }

    //跳转到编辑页面
    @RequestMapping("/editClctApplMgtPage.action")
    public String editClctApplMgtPage(HttpServletRequest request,String id)
    {
        //根据id查询采集信息
        ClctApplMgt cm=clctApplMgtService.findClctApplMgtById(id);
        request.setAttribute("cm",cm);
        return "bus/system-mgt/clct-appl-mgt/edit";
    }

    //编辑实现
    @RequestMapping("/editClctApplMgt.action")
    @ResponseBody
    public String editClctApplMgt(HttpServletRequest request,ClctApplMgt clctApplMgt)
    {
        clctApplMgtService.editClctApplMgt(clctApplMgt);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("编辑采集应用编号为["+clctApplMgt.getApplication_number()+"]采集应用");
        operationLogService.addLog(operationLog);
        Gson gson = new Gson();
        String json = gson.toJson("编辑成功!");
        return json;
    }

    //删除(逻辑删除)
    @RequestMapping("/delClctApplMgt.action")
    @ResponseBody
    public String delClctApplMgt(HttpServletRequest request,String str)
    {
        Gson gson = new Gson();
        String[] ids=str.split(",");
        //判断采集应用是否与集中器关联，如果关联不允许删除
        List<Concentrator> cList=concentratorService.findConcentratorByCollectId(ids);
        if(cList.size()!=0){
            //已经被使用
            return gson.toJson("yes");
        }
        //添加删除日志
        StringBuilder sb=new StringBuilder();
        for(String st:ids){
            ClctApplMgt clctApplMgt = clctApplMgtService.findClctApplMgtById(st);
            sb.append(clctApplMgt.getApplication_number()+',');
        }
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("删除采集编号为["+sb.substring(0,sb.length()-1)+"]一卡通终端");
        operationLogService.addLog(operationLog);
        //逻辑删除
        clctApplMgtService.updateClctApplMgtDelStatus(ids);
        return gson.toJson("no");
    }

    //检测应用编号是否存在
    @RequestMapping("/checkApplicationNumber.action")
    @ResponseBody
    public String  checkApplicationNumber(HttpServletRequest request,String  applicationNumber)
    {
        ClctApplMgt rt=clctApplMgtService.checkApplicationNumber(applicationNumber);
        Gson gson=new Gson();
        if(rt==null)
        {
            return gson.toJson("0");//应用编号不存在
        }else{
            return gson.toJson("1");//应用编号存在
        }
    }

    //检测应用名称是否存在
    @RequestMapping("/checkApplicationName.action")
    @ResponseBody
    public String  checkApplicationName(HttpServletRequest request,String collectName)
    {
        ClctApplMgt rt=clctApplMgtService.checkApplicationName(collectName);
        Gson gson=new Gson();
        if(rt==null)
        {
            return gson.toJson("0");//应用编号不存在
        }else{
            return gson.toJson("1");//应用编号存在
        }
    }
}
