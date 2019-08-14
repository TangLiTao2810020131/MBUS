package com.ets.bus.waterMeterMgt.waterQuitMgt.service;

import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.entity.WaterBean;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.instructionOperation.service.InstructionsService;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.InstructionDetailVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.OperationInstructionVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.bus.waterMeterMgt.waterQuitMgt.dao.WaterQuitMgtDao;
import com.ets.bus.waterMeterMgt.waterQuitMgt.entity.ReturnRecordVo;
import com.ets.common.MyConstant;
import com.ets.utils.Message;
import com.ets.utils.UUIDUtils;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 宋晨
 * @create 2019/3/28
 * 退水管理服务
 */
@Service
@Transactional
public class WaterQuitMgtService {
    @Resource
    private WaterQuitMgtDao waterQuitMgtDao;
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private InstructionsService instructionsService;
    @Autowired
    private OperationLogService operationLogService;


    /**
     * 计算剩余退水金额 购水剩余量* 价格
     * @param waterMeterInfo
     */
    public void getAllowReturnWater(WaterMeterInfoVo waterMeterInfo){
        BigDecimal buyWater = new BigDecimal(waterMeterInfo.getBuyWaterTotal());
        BigDecimal price = new BigDecimal(waterMeterInfo.getPrice());
        waterMeterInfo.setAllowReturnMoney(buyWater.multiply(price).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * 确认退水
     * @param request
     * @return
     */
    public Message returnConfirm(HttpServletRequest request){
        ReturnRecordVo returnRecordVo = getReturnRecordVo(request);
        Message message = new Message(MyConstant.MSG_SUCCESS,"退水成功！");
        //是否存在未下发成功的退水指令
        String errmsg = checkAllowReturnWater(returnRecordVo.getWaterMeterInfoId());
        if(StringUtils.isNotBlank(errmsg)){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg(errmsg);
        }else{
            //查询最新的水表数据
            WaterMeterInfoVo waterMeterInfo = waterPurchaseMgtService.getWaterMeterInfoById(returnRecordVo.getWaterMeterInfoId());
            getAllowReturnWater(waterMeterInfo);
            BigDecimal returnMoney = new BigDecimal(returnRecordVo.getReturnMoney());
            BigDecimal price = new BigDecimal(waterMeterInfo.getPrice());
            returnRecordVo.setReturnWater(returnMoney.divide(price,2,BigDecimal.ROUND_HALF_UP).doubleValue());
            //校验购水是否合法
            errmsg = checkReturnData(returnRecordVo, waterMeterInfo);
            if(StringUtils.isBlank(errmsg)){
                returnRecordVo.setId(UUIDUtils.getUUID());
                returnRecordVo.setSerialNumber(waterPurchaseMgtService.getSerialNumber());
                returnRecordVo.setConcentratorId(waterMeterInfo.getConcentratorId());
                returnRecordVo.setUserId(waterPurchaseMgtService.getLoginUserId());

                //通知采集下发命令
                List<WaterBean> returnWaterBeans = new ArrayList<WaterBean>();
                WaterBean water = getReturnWaterBean(waterMeterInfo, returnRecordVo);
                returnWaterBeans.add(water);
                ConcentratorProtocolBean concentrator = instructionsService.returnWaterConcent(waterMeterInfo.getConcentratorNum(),returnWaterBeans);
                String instructionJSON = new Gson().toJson(concentrator);

                //向操作指令表插入一条记录，标记为退水且状态未下发
                OperationInstructionVo operationInstruction = new OperationInstructionVo();
                operationInstruction.setId(UUIDUtils.getUUID());
                operationInstruction.setWaterMeterInfoId(returnRecordVo.getWaterMeterInfoId());
                operationInstruction.setType(MyConstant.OPERATION_TYPE_1);
                operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                operationInstruction.setUserId(returnRecordVo.getUserId());
                operationInstruction.setCollectId(waterMeterInfo.getCollectId());
                operationInstruction.setCollectIp(waterMeterInfo.getCollectIp());
                operationInstruction.setApartmentId(waterMeterInfo.getApartmentId());
                operationInstruction.setConcentratorId(waterMeterInfo.getConcentratorId());
                operationInstruction.setWaterMeterNum(waterMeterInfo.getWaterMeterNum());
                operationInstruction.setInstructionDetail(instructionJSON);
                waterPurchaseMgtService.insertOperationInstruction(operationInstruction);

                //向指令明细表插入一条退水记录
                InstructionDetailVo instructionDetail = new InstructionDetailVo();
                instructionDetail.setId(UUIDUtils.getUUID());
                instructionDetail.setWaterMeterInfoId(returnRecordVo.getWaterMeterInfoId());
                instructionDetail.setRecordId(returnRecordVo.getId());
                instructionDetail.setType(MyConstant.OPERATION_TYPE_1);
                instructionDetail.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                instructionDetail.setUserId(returnRecordVo.getUserId());
                instructionDetail.setCollectId(waterMeterInfo.getCollectId());
                instructionDetail.setCollectIp(waterMeterInfo.getCollectIp());
                instructionDetail.setApartmentId(waterMeterInfo.getApartmentId());
                instructionDetail.setOperationId(operationInstruction.getId());
                instructionDetail.setWaterMeterNum(operationInstruction.getWaterMeterNum());
                instructionDetail.setInstructionDetail(instructionJSON);
                waterPurchaseMgtService.insertInstructionDetail(instructionDetail);

                //向退水记录表插入一条退水记录
                returnRecordVo.setOperationId(operationInstruction.getId());
                insertReturnRecord(returnRecordVo);

                instructionsService.returnWater(waterMeterInfo.getConcentratorNum(),concentrator);

                /**************添加操作日志start***************/
                mb_operation_log mol=new mb_operation_log();
                mol.setModuleName("水表管理-退水管理");
                mol.setOperaContent(waterMeterInfo.getAreaName()+"-"+waterMeterInfo.getRoomNum()+"[退水]");
                operationLogService.addLog(mol);
                /**************添加操作日志end****************/

            }else{
                message.setStatus(MyConstant.MSG_FAIL);
                message.setMsg(errmsg);
            }
        }

        return message;
    }

    /**
     * 若该房间存在未下发成功的退水指令、购水指令时，则不允退水，必须等待指令完成才能执行退水操作。
     * @param id
     * @return
     */
    public String checkAllowReturnWater(String id){
        String result = "";
        //若该房间存在未下发成功的退水指令、购水指令时，则不允退水，必须等待指令完成才能执行退水操作。
        if(waterPurchaseMgtService.getNotFinishByMeterId(id)){
            result = "当前房间存在未下发成功的退水指令、购水指令，不允许此操作。";
        }

        return result;
    }

    /**
     * 获取前台传入参数
     * @param request
     * @return
     */
    private ReturnRecordVo getReturnRecordVo(HttpServletRequest request){
        ReturnRecordVo recordVo = new ReturnRecordVo();
        recordVo.setWaterMeterInfoId(request.getParameter("waterMeterInfoId"));
        BigDecimal returnMoney = new BigDecimal(request.getParameter("returnMoney"));
        recordVo.setReturnMoney(returnMoney.doubleValue());
        recordVo.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
        recordVo.setType(MyConstant.RETURN_TYPE_STATUS_0);
        recordVo.setRedrush(MyConstant.REDRUSH_STATUS_0);

        return recordVo;
    }

    /**
     * 校验购水是否合法
     * 用户输入的退水金额不能大于最大可退的金额
     * @param returnRecordVo
     * @return
     */
    private String checkReturnData(ReturnRecordVo returnRecordVo, WaterMeterInfoVo waterMeterInfo){
        String result = "";
        //允许购水量 最大囤积量与剩余水量之差（剩余购水+剩余补水）+ 透支量
        if(returnRecordVo.getReturnMoney() > waterMeterInfo.getAllowReturnMoney()){
            result = "用户输入的退水金额不能大于最大可退的金额！";
        }
        return result;
    }

    /**
     * 向退水记录表插入一条退水记录
     * @param returnRecordVo
     */
    public void insertReturnRecord(ReturnRecordVo returnRecordVo){
        waterQuitMgtDao.insertReturnRecord(returnRecordVo);
    }

    /**
     * 获取退水bean
     * @param waterMeterInfo
     * @param returnRecordVo
     * @return
     */
    private WaterBean getReturnWaterBean(WaterMeterInfoVo waterMeterInfo, ReturnRecordVo returnRecordVo){
        WaterBean waterBean = new WaterBean();
        waterBean.setWaterMeterNum(Integer.parseInt(waterMeterInfo.getWaterMeterNum()));
        waterBean.setBuyWater((new Double(Math.round(returnRecordVo.getReturnWater()*10))).intValue());
        waterBean.setAddWater(0);
        waterBean.setAddNum(waterMeterInfo.getAddNum()+1);
        return waterBean;
    }

}
