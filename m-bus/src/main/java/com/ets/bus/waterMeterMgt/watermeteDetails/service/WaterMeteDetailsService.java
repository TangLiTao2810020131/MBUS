package com.ets.bus.waterMeterMgt.watermeteDetails.service;

import com.ets.bus.reportQuery.entity.report.RedRushVo;
import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.entity.WaterBean;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.instructionOperation.service.InstructionsService;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.InstructionDetailVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.OperationInstructionVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.bus.waterMeterMgt.watermeteDetails.dao.WaterMeteDetailsDao;
import com.ets.bus.waterMeterMgt.watermeteDetails.entity.TransactionDetailsVo;
import com.ets.common.MyConstant;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.Message;
import com.ets.utils.PageListData;
import com.ets.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 宋晨
 * @create 2019/3/28
 * 水表交易明细服务
 */
@Service
@Transactional
public class WaterMeteDetailsService {
    @Resource
    private WaterMeteDetailsDao waterMeteDetailsDao;
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private InstructionsService instructionsService;
    @Autowired
    private OperationLogService operationLogService;

    /**
     * 查询水表交易记录集合
     * @param transDetailParam
     * @return
     */
    public PageListData<TransactionDetailsVo> getTransactionDetailsList(TransactionDetailsVo transDetailParam){
        PageListData<TransactionDetailsVo> pageData = new PageListData<TransactionDetailsVo>();
        List<TransactionDetailsVo> list = null;
        //是否选择交易类型决定是否使用单表查询
        if(StringUtils.isNotBlank(transDetailParam.getTransacType())){
            PageInfo<TransactionDetailsVo> pageInfo = selectTransDetailsByType(transDetailParam);
            list = pageInfo.getList();
            pageData.setCount(pageInfo.getTotal());
        }else{
            list = waterMeteDetailsDao.selectTransactionDetailsList(transDetailParam);
            Integer count = waterMeteDetailsDao.selectTransactionDetailsCount(transDetailParam);
            pageData.setCount(count);
        }
        pageData.setCode("0");
        pageData.setMessage("");
        pageData.setData(list);
        return pageData;
    }

    /**
     * 组装查询条件
     * @param request
     * @return
     */
    public TransactionDetailsVo getReqSearchParam(HttpServletRequest request){
        TransactionDetailsVo param = new TransactionDetailsVo();
        param.setOperatId(request.getParameter("operatId"));
        param.setTransacType(request.getParameter("transacType"));
        param.setRoomNum(request.getParameter("roomNum"));
        if(StringUtils.isNotBlank(request.getParameter("currentStatus"))){
            param.setCurrentStatus(Integer.parseInt(request.getParameter("currentStatus")));
        }
        if(StringUtils.isNotBlank(request.getParameter("startTime"))){
            Date sdate = DateTimeUtils.getDateByString(request.getParameter("startTime"));
            param.setStartTime(sdate);
        }
        if(StringUtils.isNotBlank(request.getParameter("endTime"))){
            Date edate = DateTimeUtils.getDateByString(request.getParameter("endTime"));
            param.setEndTime(edate);
        }
        if(StringUtils.isNotBlank(request.getParameter("level"))){
            if(MyConstant.TREE_LEVEL_2.equals(request.getParameter("level"))){
                param.setAreaId(request.getParameter("parentId"));
            }else if(MyConstant.TREE_LEVEL_3.equals(request.getParameter("level"))){
                param.setApartmentId(request.getParameter("parentId"));
            }else if(MyConstant.TREE_LEVEL_4.equals(request.getParameter("level"))){
                param.setBuildId(request.getParameter("parentId"));
            }else if(MyConstant.TREE_LEVEL_5.equals(request.getParameter("level"))){
                param.setFloorId(request.getParameter("parentId"));
            }
        }

        int page = request.getParameter(MyConstant.PAGE_KEY) != null?Integer.parseInt(request.getParameter(MyConstant.PAGE_KEY)):MyConstant.PAGE_DEFULT;
        int limit = request.getParameter(MyConstant.LIMIT_KEY) != null? Integer.parseInt(request.getParameter(MyConstant.LIMIT_KEY)):MyConstant.LIMIT_DEFULT;
        param.setRealPage(page);
        param.setRealLimit(limit);
        param.setPage(page * limit);
        param.setLimit((page - 1) * limit);
        return param;
    }

    public PageInfo<TransactionDetailsVo> selectTransDetailsByType(TransactionDetailsVo transDetailParam){
        List<TransactionDetailsVo> list;
        PageHelper.startPage(transDetailParam.getRealPage(), transDetailParam.getRealLimit());
        if(MyConstant.TRANSAC_TYPE_1.equals(transDetailParam.getTransacType())){
            //现金购水
            list = waterMeteDetailsDao.selectBuyRecordList(transDetailParam);
        }else if(MyConstant.TRANSAC_TYPE_2.equals(transDetailParam.getTransacType())){
            //一卡通购水
            list = waterMeteDetailsDao.selectBuyRecordList(transDetailParam);

        }else if(MyConstant.TRANSAC_TYPE_3.equals(transDetailParam.getTransacType())){
            //房间补水
            list = waterMeteDetailsDao.selectSupplementRecordList(transDetailParam);

        }else if(MyConstant.TRANSAC_TYPE_4.equals(transDetailParam.getTransacType())){
            //楼层补水
            list = waterMeteDetailsDao.selectSupplementRecordList(transDetailParam);

        }else if(MyConstant.TRANSAC_TYPE_5.equals(transDetailParam.getTransacType())){
            //按导入补水
            list = waterMeteDetailsDao.selectSupplementRecordList(transDetailParam);

        }else if(MyConstant.TRANSAC_TYPE_6.equals(transDetailParam.getTransacType())){
            //退水
            list = waterMeteDetailsDao.selectReturnRecordList(transDetailParam);

        }else if(MyConstant.TRANSAC_TYPE_7.equals(transDetailParam.getTransacType())){
            //水量清零
            list = waterMeteDetailsDao.selectClearRecordList(transDetailParam);

        }else if(MyConstant.TRANSAC_TYPE_8.equals(transDetailParam.getTransacType())){
            //按导入水量清零
            list = waterMeteDetailsDao.selectClearRecordList(transDetailParam);

        }else if(MyConstant.TRANSAC_TYPE_9.equals(transDetailParam.getTransacType())){
            //换房补水
            list = waterMeteDetailsDao.selectSupplementRecordList(transDetailParam);

        }else if(MyConstant.TRANSAC_TYPE_10.equals(transDetailParam.getTransacType())){
            //换房退水
            list = waterMeteDetailsDao.selectReturnRecordList(transDetailParam);

        }else if(MyConstant.TRANSAC_TYPE_11.equals(transDetailParam.getTransacType())){
            //交易冲红
            list = waterMeteDetailsDao.selectRedrushRecordList(transDetailParam);
        }else{
            list = new ArrayList<TransactionDetailsVo>();
        }
        PageInfo<TransactionDetailsVo> pageInfo = new PageInfo<TransactionDetailsVo>(list);
        return pageInfo;
    }

    /**
     * 获取冲红记录信息
     * 若该笔记录有冲红记录，则不允许再次冲红
     * 只能对各类补水或退水记录进行冲红操作
     * @param id
     * @param transacType
     * @return
     */
    public TransactionDetailsVo getRecordById(String id, String transacType){
        TransactionDetailsVo transactionDetails;
        if(transacType.contains("补水")){
            transactionDetails = waterMeteDetailsDao.selectSupplementRecordById(id);
        }else if(transacType.contains("退水")){
            transactionDetails = waterMeteDetailsDao.selectReturnRecordById(id);
        }else{
            transactionDetails = new TransactionDetailsVo();
            transactionDetails.setErrMsg("只能对各类补水或退水记录进行冲红操作！");
        }
        if(transactionDetails.getHasRedrush() != null
                && MyConstant.REDRUSH_STATUS_1.equals(transactionDetails.getHasRedrush())){
            transactionDetails.setErrMsg("该笔记录有冲红记录，不允许再次冲红！");
        }
        if(transactionDetails.getCurrentStatus() != null
                && !MyConstant.CURRENT_STATUS_1.equals(transactionDetails.getCurrentStatus())){
            transactionDetails.setErrMsg("该笔记录未操作成功，不允许冲红！");
        }

        return transactionDetails;
    }

    /**
     * 确认冲红
     * @param request
     * @return
     */
    public Message redrushConfirm(HttpServletRequest request){
        Message message = new Message(MyConstant.MSG_SUCCESS,"冲红成功！");
        String transactionDetailsId = request.getParameter("transactionDetailsId");
        String remark = request.getParameter("remark");
        String transacType = request.getParameter("transacType");
        Double redrushWater = new BigDecimal(request.getParameter("redrushWater")).doubleValue();

        TransactionDetailsVo transactionDetails = getRecordById(transactionDetailsId, transacType);
        if(StringUtils.isNotBlank(transactionDetails.getErrMsg())){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg(transactionDetails.getErrMsg());

        }else if(redrushWater <= 0 || redrushWater > transactionDetails.getOperatWater()){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg("冲红水量不能大于操作水量！");
        }else{
            WaterMeterInfoVo waterMeterInfo = waterPurchaseMgtService.getWaterMeterInfoById(transactionDetails.getWaterMeterInfoId());
            RedRushVo redRushVo = new RedRushVo();
            redRushVo.setId(UUIDUtils.getUUID());
            redRushVo.setWaterMeterInfoId(waterMeterInfo.getId());
            redRushVo.setSerialNumber(waterPurchaseMgtService.getSerialNumber());
            redRushVo.setUserId(waterPurchaseMgtService.getLoginUserId());
            redRushVo.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
            redRushVo.setRemark(remark);
            redRushVo.setRedrushWater(redrushWater);
            redRushVo.setRecordId(transactionDetailsId);
            redRushVo.setConcentratorId(waterMeterInfo.getConcentratorId());

            //获取指令对象
            boolean isadd = true;
            ConcentratorProtocolBean concentrator;
            List<WaterBean> waterBeans = new ArrayList<WaterBean>();
            if(transacType.contains("补水")){
                redRushVo.setType(MyConstant.REDRUSH_TYPE_0);
                isadd = false;
            }else if(transacType.contains("退水")){
                redRushVo.setType(MyConstant.REDRUSH_TYPE_1);
            }
            WaterBean water = getRedWaterBean(waterMeterInfo, redRushVo, isadd);
            waterBeans.add(water);
            if(isadd){
                concentrator = instructionsService.addWaterConcent(waterMeterInfo.getWaterMeterNum(),waterBeans);
            }else {
                concentrator = instructionsService.returnWaterConcent(waterMeterInfo.getWaterMeterNum(),waterBeans);
            }
            String instructionJSON = new Gson().toJson(concentrator);

            //向操作指令表插入一条红冲指令
            OperationInstructionVo operationInstruction = new OperationInstructionVo();
            operationInstruction.setId(UUIDUtils.getUUID());
            operationInstruction.setWaterMeterInfoId(redRushVo.getWaterMeterInfoId());
            operationInstruction.setType(MyConstant.OPERATION_TYPE_7);
            operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
            operationInstruction.setUserId(redRushVo.getUserId());
            operationInstruction.setCollectId(waterMeterInfo.getCollectId());
            operationInstruction.setCollectIp(waterMeterInfo.getCollectIp());
            operationInstruction.setApartmentId(waterMeterInfo.getApartmentId());
            operationInstruction.setConcentratorId(waterMeterInfo.getConcentratorId());
            operationInstruction.setWaterMeterNum(waterMeterInfo.getWaterMeterNum());
            operationInstruction.setInstructionDetail(instructionJSON);
            waterPurchaseMgtService.insertOperationInstruction(operationInstruction);

            //指令明细表插入一条冲红记录
            InstructionDetailVo instructionDetail = new InstructionDetailVo();
            instructionDetail.setId(UUIDUtils.getUUID());
            instructionDetail.setWaterMeterInfoId(redRushVo.getWaterMeterInfoId());
            instructionDetail.setRecordId(redRushVo.getId());
            instructionDetail.setType(MyConstant.OPERATION_TYPE_7);
            instructionDetail.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
            instructionDetail.setUserId(redRushVo.getUserId());
            instructionDetail.setCollectId(waterMeterInfo.getCollectId());
            instructionDetail.setCollectIp(waterMeterInfo.getCollectIp());
            instructionDetail.setApartmentId(waterMeterInfo.getApartmentId());
            instructionDetail.setOperationId(operationInstruction.getId());
            instructionDetail.setWaterMeterNum(operationInstruction.getWaterMeterNum());
            instructionDetail.setInstructionDetail(instructionJSON);
            waterPurchaseMgtService.insertInstructionDetail(instructionDetail);

            //冲红记录表插入一条冲红记录
            redRushVo.setOperationId(operationInstruction.getId());
            insertRedRushVo(redRushVo);

            //通知采集下发命令
            if(MyConstant.REDRUSH_TYPE_0.equals(redRushVo.getType())){
                //对补水冲红即下发退水操作
                instructionsService.returnWater(waterMeterInfo.getConcentratorNum(),concentrator);
            }else{
                //对退水冲红即下发补水操作
                instructionsService.addWater(waterMeterInfo.getConcentratorNum(),concentrator);
            }

            /**************添加操作日志start***************/
            mb_operation_log mol=new mb_operation_log();
            mol.setModuleName("水表管理-水表交易明细");
            mol.setOperaContent("为:["+waterMeterInfo.getAreaName()+"-"+waterMeterInfo.getApartmentName()+"-"+waterMeterInfo.getRoomNum()+"]冲红");
            operationLogService.addLog(mol);
            /**************添加操作日志end****************/

        }
        return message;
    }

    /**
     * 冲红记录表插入一条冲红记录
     * @param redRushVo
     */
    public void insertRedRushVo(RedRushVo redRushVo){
        waterMeteDetailsDao.insertRedRushVo(redRushVo);
    }

    public TransactionDetailsVo findWaterMeterDetails(String id)
    {
        TransactionDetailsVo td=waterMeteDetailsDao.findWaterMeterDetails(id);
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("水表管理-水表交易明细");
        mol.setOperaContent("查看:["+td.getAreaName()+"-"+td.getApartmentName()+"-"+td.getRoomNum()+"]水表交易明细");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return td;
    }

    /**
     * 获取bean
     * @param waterMeterInfo
     * @param redRushVo
     * @return
     */
    private WaterBean getRedWaterBean(WaterMeterInfoVo waterMeterInfo, RedRushVo redRushVo, boolean isadd){
        WaterBean waterBean = new WaterBean();
        waterBean.setWaterMeterNum(Integer.parseInt(waterMeterInfo.getWaterMeterNum()));
        if(isadd){
            waterBean.setBuyWater(0);
            waterBean.setAddWater((new Double(redRushVo.getRedrushWater()*10)).intValue());
        }else{
            waterBean.setBuyWater((new Double(redRushVo.getRedrushWater()*10)).intValue());
            waterBean.setAddWater(0);
        }
        waterBean.setAddNum(waterMeterInfo.getAddNum()+1);
        return waterBean;
    }

}
