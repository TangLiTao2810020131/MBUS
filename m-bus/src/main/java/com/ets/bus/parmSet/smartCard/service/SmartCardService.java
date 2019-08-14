package com.ets.bus.parmSet.smartCard.service;

import com.ets.bus.parmSet.smartCard.dao.SmartCardDao;
import com.ets.bus.parmSet.smartCard.entity.CardTerminalVo;
import com.ets.bus.systemMgt.operationLog.dao.OperationLogDao;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.instructionOperation.service.OperationService;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.dao.WaterPurchaseMgtDao;
import com.ets.utils.PageListData;
import com.ets.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/4/15
 * 一卡通终端参数设置服务
 */
@Service
@Transactional
public class SmartCardService {
    @Resource
    private SmartCardDao smartCardDao;
    @Autowired
    private OperationLogService operationLogService;


    /**
     * 组装查询条件
     * @param request
     * @return
     */
    public Map<String, Object>  getReqSearchParam(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("terminalNum",request.getParameter("terminalNum"));
        return map;
    }

    /**
     * 查询一卡通终端集合
     * @param page
     * @param limit
     * @param param
     * @return
     */
    public PageListData<CardTerminalVo> getCardTerminalList(Integer page, Integer  limit, Map<String, Object> param){
        PageHelper.startPage(page, limit);
        List<CardTerminalVo> list = smartCardDao.getCardTerminalList(param);

        PageInfo<CardTerminalVo> pageInfo = new PageInfo<CardTerminalVo>(list);
        PageListData<CardTerminalVo> pageData = new PageListData<CardTerminalVo>();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(list);
        return pageData;
    }

    /**
     * 添加一卡通终端
     * @param ctv
     */
    public void addSmartCard(CardTerminalVo ctv)
    {
        ctv.setDelStatus(0);
        ctv.setId(UUIDUtils.getUUID());
        smartCardDao.addSmartCard(ctv);
    }

    /**
     * 更新一卡通终端删除状态
     * @param ids
     */
    public void updatedelStatus(String[] ids)
    {
        smartCardDao.updatedelStatus(ids);
    }

    /**
     * 编辑一卡通终端
     * @param cv
     */
    public void editSmartCard(CardTerminalVo cv)
    {
        smartCardDao.editSmartCard(cv);
    }

    /**
     * 更新一卡通可用状态
     * @param flag
     * @param str
     * @return
     */
    public String updateUseStatus(int flag, String str)
    {
        String[] ids=str.split(",");
        StringBuilder sb=new StringBuilder();
        for(String st:ids){
            CardTerminalVo cardTerminalVo = smartCardDao.findCardTerminalVoById(st);
            sb.append(cardTerminalVo .getTerminalNum()+',');
        }
        if(flag==0){
            mb_operation_log operationLog=new mb_operation_log();
            operationLog.setModuleName("参数设置-一卡通终端");
            operationLog.setOperaContent("启用编号为["+sb.substring(0,sb.length()-1)+"]一卡通终端");
            operationLogService.addLog(operationLog);
            smartCardDao.updateStartStatus(ids);
            return "启用成功！";
        }else if(flag==1){
            mb_operation_log operationLog=new mb_operation_log();
            operationLog.setModuleName("参数设置-一卡通终端");
            operationLog.setOperaContent("禁用编号为["+sb.substring(0,sb.length()-1)+"]一卡通终端");
            operationLogService.addLog(operationLog);
            smartCardDao.updateEndStatus(ids);
            return "禁用成功！";
        }
        return "";
    }

    /**
     * 通过终端编号查询终端信息
     * @param terminalNum
     * @return
     */
    public CardTerminalVo checkTerminalNum(String terminalNum)
    {
        return smartCardDao.checkTerminalNum(terminalNum);
    }

    /**
     * 通过终端IP地址查询一卡通终端信息
     * @param terminalAddress
     * @return
     */
    public CardTerminalVo checkTerminalAddress(String terminalAddress)
    {
        return smartCardDao.checkTerminalAddress(terminalAddress);
    }

    /**
     * 通过一卡通终端表ID查询一卡通终端信息
     * @param id
     * @return
     */
    public CardTerminalVo findCardTerminalVoById(String id)
    {
        return smartCardDao.findCardTerminalVoById(id);
    }

}
