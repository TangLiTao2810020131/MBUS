package com.ets.bus.waterMeterMgt.watermeteDetails.web;

import javax.servlet.http.HttpServletRequest;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.watermeteDetails.entity.TransactionDetailsVo;
import com.ets.bus.waterMeterMgt.watermeteDetails.service.WaterMeteDetailsService;
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

/**
 * @author 宋晨
 * @create 2019/3/28
 * 水表交易明细控制器
 */
@Controller
@RequestMapping("watermetedetails")
public class WaterMeteDetailsController {
    @Autowired
    private WaterMeteDetailsService waterMeteDetailsService;
    @Autowired
    private OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(WaterMeteDetailsController.class);
	
    @RequestMapping("waterMeteDetails")
    public String waterMeteDetails(HttpServletRequest request)
    {
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("水表管理-水表交易明细");
        mol.setOperaContent("查看水表交易明细列表");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return "bus/watermeter-mgt/watermete-details/watermete-details";
    }

    /**
     * 获取交易明细列表
     * @param request
     * @return
     */
    @RequestMapping(value = "listData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(HttpServletRequest request) {

        PageListData<TransactionDetailsVo> transactionDetailsList = null;
        try {
            TransactionDetailsVo param = waterMeteDetailsService.getReqSearchParam(request);
            //查询数据
            transactionDetailsList = waterMeteDetailsService.getTransactionDetailsList(param);

        }catch (Exception e){
            logger.error("获取交易明细列表报错！", e);
        }
        return new Gson().toJson(transactionDetailsList);
    }

    /**
     * 冲红弹框
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("redrushIndex")
    public String redrushIndex(HttpServletRequest request, Model model){
        TransactionDetailsVo transactionDetails = null;
        try {
            transactionDetails = waterMeteDetailsService.getRecordById(
                                                            request.getParameter("id"),
                                                            request.getParameter("transacType"));
            if(StringUtils.isNotBlank(transactionDetails.getErrMsg())){
                model.addAttribute("errMsg",transactionDetails.getErrMsg());
                return "bus/watermeter-mgt/waterPurchase-mgt/waterPurchase-notAllow";
            }
        }catch (Exception e){
            logger.error("获取冲红弹框数据报错！", e);
        }
        model.addAttribute("transactionDetails", transactionDetails);
        return "bus/watermeter-mgt/watermete-details/watermete-details-ch";
    }

    /**
     * 确认冲红
     * @param request
     * @return
     */
    @RequestMapping("redrushConfirm")
    @ResponseBody
    public String redrushConfirm(HttpServletRequest request){
        Message infoMessage;
        try {
            infoMessage = waterMeteDetailsService.redrushConfirm(request);
        }catch (Exception e){
            logger.error("确认冲红报错！", e);
            infoMessage = new Message(MyConstant.MSG_FAIL,"冲红失败！");
        }
        return new Gson().toJson(infoMessage);
    }

    //根据id查询水表交易明细
    @RequestMapping("/findWaterMeterDetails.action")
    public String findWaterMeterDetails(HttpServletRequest request,String id)
    {
       TransactionDetailsVo td=waterMeteDetailsService.findWaterMeterDetails(id);
       request.setAttribute("br",td);
       return "bus/watermeter-mgt/watermete-details/watermete-details-info";
    }

}
