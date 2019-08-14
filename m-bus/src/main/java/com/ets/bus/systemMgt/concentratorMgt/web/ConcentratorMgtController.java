package com.ets.bus.systemMgt.concentratorMgt.web;

import javax.servlet.http.HttpServletRequest;
import com.ets.bus.systemMgt.concentratorMgt.entity.ConcentratorVo;
import com.ets.bus.systemMgt.concentratorMgt.service.ConcentratorMgtService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.OperationInstructionVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.common.MyConstant;
import com.ets.utils.Message;
import com.ets.utils.PageListData;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/3/28
 * 集中器管理
 */
@Controller
@RequestMapping("concentratormgt")
public class ConcentratorMgtController {
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private ConcentratorMgtService concentratorMgtService;
    @Autowired
    private OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(ConcentratorMgtController.class);
	
    @RequestMapping("concentratorMgt")
    public String concentratorMgt(Model model)
    {
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-集中器管理");
        mol.setOperaContent("查看集中器管理列表");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return "bus/system-mgt/concentrator-mgt/concentrator-mgt";
    }

    /**
     * 获取集中器列表
     * @param request
     * @return
     */
    @RequestMapping(value = "listData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(HttpServletRequest request) {

        PageListData<ConcentratorVo> concentratorList = null;
        try {
            Map<String, Object> map = waterPurchaseMgtService.getReqSearchParam(request);
            int page = request.getParameter(MyConstant.PAGE_KEY) != null?Integer.parseInt(request.getParameter(MyConstant.PAGE_KEY)):MyConstant.PAGE_DEFULT;
            int limit = request.getParameter(MyConstant.LIMIT_KEY) != null? Integer.parseInt(request.getParameter(MyConstant.LIMIT_KEY)):MyConstant.LIMIT_DEFULT;
            //查询数据
            concentratorList = concentratorMgtService.getConcentratorList(page, limit, map);

        }catch (Exception e){
            logger.error("获取集中器列表报错！", e);
        }
        return new Gson().toJson(concentratorList);
    }

    /**
     * 校时-将服务器时间更新到集中器上
     * @param request
     * @return
     */
    @RequestMapping("calibrationTime")
    @ResponseBody
    public String calibrationTime(HttpServletRequest request){
        Message infoMessage;
        try {
            infoMessage = concentratorMgtService.calibrationTime(request);
        }catch (Exception e){
            logger.error("将服务器时间更新到集中器上报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"校时失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 发参数弹框
     * @param request
     * @return
     */
    @RequestMapping("setParam")
    public String setParam(HttpServletRequest request, Model model){

        try {
            model.addAttribute("concentratorId", request.getParameter("id"));
        }catch (Exception e){
            logger.error("发参数弹框报错！", e);
        }
        return "bus/system-mgt/concentrator-mgt/parm";
    }

    /**
     * 确认发参数
     * @param request
     * @return
     */
    @RequestMapping("setParamConfirm")
    @ResponseBody
    public String setParamConfirm(HttpServletRequest request){
        Message infoMessage;
        try {
            infoMessage = concentratorMgtService.setParamConfirm(request);
        }catch (Exception e){
            logger.error("确认发参数报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"发参数失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 获取集中器下所有的房间的当前信息
     * @param request
     * @return
     */
    @RequestMapping("getRoomInfo")
    @ResponseBody
    public String getRoomInfo(HttpServletRequest request){
        Message infoMessage;
        try {
            infoMessage = concentratorMgtService.getRoomInfo(request);
        }catch (Exception e){
            logger.error("获取房间信息报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"获取房间信息失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 重启集中器
     * @param request
     * @return
     */
    @RequestMapping("restart")
    @ResponseBody
    public String restart(HttpServletRequest request){
        Message infoMessage;
        try {
            infoMessage = concentratorMgtService.restart(request);
        }catch (Exception e){
            logger.error("重启集中器报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"重启集中器失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    /**
     * 换集中器
     * 将集中器所管理的房间信息更新到新的集中器上
     * @param request
     * @return
     */
    @RequestMapping("replace")
    @ResponseBody
    public String replace(HttpServletRequest request){
        Message infoMessage;
        try {
            infoMessage = concentratorMgtService.replace(request);
        }catch (Exception e){
            logger.error("换集中器报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"换集中器失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    @RequestMapping("record")
    public String record(HttpServletRequest request, Model model){
        model.addAttribute("tid", request.getParameter("id"));
        return "bus/system-mgt/concentrator-mgt/concentrator-record";
    }

    /**
     * 获取集中器操作指令列表
     * @param request
     * @return
     */
    @RequestMapping(value = "recordListData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String recordListData(HttpServletRequest request) {

        PageListData<OperationInstructionVo> operationInstructionList = null;
        try {
            Map<String, Object> map = concentratorMgtService.getReqSearchParam(request);
            int page = request.getParameter(MyConstant.PAGE_KEY) != null?Integer.parseInt(request.getParameter(MyConstant.PAGE_KEY)):MyConstant.PAGE_DEFULT;
            int limit = request.getParameter(MyConstant.LIMIT_KEY) != null? Integer.parseInt(request.getParameter(MyConstant.LIMIT_KEY)):MyConstant.LIMIT_DEFULT;
            //查询数据
            operationInstructionList = concentratorMgtService.getRecordListData(page, limit, map);

        }catch (Exception e){
            logger.error("获取集中器操作指令列表报错！", e);
        }
        return new Gson().toJson(operationInstructionList);
    }

    @RequestMapping("/findConcentratormgtById.action")
    public String findConcentratormgtById(HttpServletRequest request,String id)
    {
        ConcentratorVo cv=concentratorMgtService.findConcentratormgtById(id);
        request.setAttribute("br",cv);
        return "bus/system-mgt/concentrator-mgt/concentrator-find";
    }

    //根据id查询集中器的指令
    @RequestMapping("/findInstructionsById.action")
    public String findInstructionsById(HttpServletRequest request,String id)
    {
        OperationInstructionVo oiv=null;
        try{
            oiv = concentratorMgtService.findInstructionsById(id);
        }catch(Exception ex){

        }
        request.setAttribute("br",oiv);
        return "bus/system-mgt/concentrator-mgt/instructions-record";
    }

}
