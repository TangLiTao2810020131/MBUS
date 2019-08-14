package com.ets.bus.waterMeterMgt.waterRoomMgt.service;

import com.ets.bus.reportQuery.entity.report.ReplaceRoomRecordVo;
import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.entity.WaterBean;
import com.ets.bus.waterMeterMgt.instructionOperation.service.InstructionsService;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.InstructionDetailVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.OperationInstructionVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.bus.waterMeterMgt.waterRoomMgt.dao.WaterRoomMgtDao;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author 宋晨
 * @create 2019/3/28
 * 控水换房管理服务
 */
@Service
@Transactional
public class WaterRoomMgtService {
    @Resource
    private WaterRoomMgtDao waterRoomMgtDao;
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private InstructionsService instructionsService;

    public Message getRoomInfoById(String id){
        Message message = new Message(MyConstant.MSG_SUCCESS,MyConstant.MSG_SUCCESS);
        //采集更新房间信息
        //查询是否有未完成的指令 有则返回false
        if(waterPurchaseMgtService.getNotFinishByMeterIdAndType(id,null)){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg("房间存在未完成的指令，不允许进行换房！");
        }else{
            WaterMeterInfoVo waterMeterInfo = waterPurchaseMgtService.getWaterMeterInfoById(id);
            message.setBody(waterMeterInfo);
        }
        return  message;
    }

    /**
     * 确认控水换房
     * 清零新房间 > 将原房间水量信息覆盖给新房间 > 清零原房间
     * 换房不对数据库中房间信息进行更换，需要采集更新获取房间信息。
     * @param request
     * @return
     */
    public Message roomConfirm(HttpServletRequest request){
        Message message = new Message(MyConstant.MSG_SUCCESS,"退水成功！");
        String newId = request.getParameter("newRoomId");
        String oldId = request.getParameter("oldRoomId");
        //校验数据是否合法
        if(StringUtils.isBlank(newId) || StringUtils.isBlank(oldId)){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg("原房间和新房间不能为空！");
        }else{
            //查询最新的水表数据
            WaterMeterInfoVo newMeterInfo = waterPurchaseMgtService.collectAndGetWaterMeterInfo(newId);
            WaterMeterInfoVo oldMeterInfo = waterPurchaseMgtService.collectAndGetWaterMeterInfo(oldId);
            //是否符合控水换房条件
            String errmsg = checkAllow(oldMeterInfo, newMeterInfo);
            if(StringUtils.isNotBlank(errmsg)){
                message.setStatus(MyConstant.MSG_FAIL);
                message.setMsg(errmsg);
            }else{
                ReplaceRoomRecordVo replaceRoomRecord = new ReplaceRoomRecordVo();
                replaceRoomRecord.setId(UUIDUtils.getUUID());
                replaceRoomRecord.setConcentratorId(newMeterInfo.getConcentratorId());
                replaceRoomRecord.setUserId(waterPurchaseMgtService.getLoginUserId());
                replaceRoomRecord.setOldWaterMeterInfoId(oldMeterInfo.getId());
                replaceRoomRecord.setNewWaterMeterInfoId(newMeterInfo.getId());
                replaceRoomRecord.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                replaceRoomRecord.setSupplementWater(oldMeterInfo.getSupplementWater());
                replaceRoomRecord.setBuyWaterTotal(oldMeterInfo.getBuyWaterTotal());
                replaceRoomRecord.setCashBuyWater(oldMeterInfo.getCashBuyWater());
                replaceRoomRecord.setCardBuyWater(oldMeterInfo.getCardBuyWater());
                replaceRoomRecord.setReturnWater(oldMeterInfo.getReturnWater());
                replaceRoomRecord.setSurplusWater(oldMeterInfo.getSurplusWater());
                replaceRoomRecord.setUserWater(oldMeterInfo.getUserWater());
                replaceRoomRecord.setOverWater(oldMeterInfo.getOverWater());

                //获取指令对象
                List<WaterBean> addWaterBeans = new ArrayList<WaterBean>();
                WaterBean water = getRepalceWaterBean(newMeterInfo, replaceRoomRecord);
                addWaterBeans.add(water);
                ConcentratorProtocolBean concentrator = instructionsService.addWaterConcent(newMeterInfo.getWaterMeterNum(),addWaterBeans);
                String instructionJSON = new Gson().toJson(concentrator);

                //先向操作指令表插入一条控水换房指令
                OperationInstructionVo operationInstruction = new OperationInstructionVo();
                operationInstruction.setId(UUIDUtils.getUUID());
                operationInstruction.setWaterMeterInfoId(newMeterInfo.getId());
                operationInstruction.setType(MyConstant.OPERATION_TYPE_3);
                operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                operationInstruction.setUserId(replaceRoomRecord.getUserId());
                operationInstruction.setCollectId(newMeterInfo.getCollectId());
                operationInstruction.setCollectIp(newMeterInfo.getCollectIp());
                operationInstruction.setApartmentId(newMeterInfo.getApartmentId());
                operationInstruction.setConcentratorId(newMeterInfo.getConcentratorId());
                operationInstruction.setWaterMeterNum(newMeterInfo.getWaterMeterNum());
                operationInstruction.setInstructionDetail(instructionJSON);
                waterPurchaseMgtService.insertOperationInstruction(operationInstruction);

                //向指令明细表插入记录为原房间，类型为普通指令，
                InstructionDetailVo instructionDetail = new InstructionDetailVo();
                instructionDetail.setId(UUIDUtils.getUUID());
                instructionDetail.setWaterMeterInfoId(oldMeterInfo.getId());
                instructionDetail.setRecordId(replaceRoomRecord.getId());
                instructionDetail.setType(MyConstant.OPERATION_TYPE_5);
                instructionDetail.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                instructionDetail.setUserId(replaceRoomRecord.getUserId());
                instructionDetail.setCollectId(oldMeterInfo.getCollectId());
                instructionDetail.setCollectIp(oldMeterInfo.getCollectIp());
                instructionDetail.setApartmentId(oldMeterInfo.getApartmentId());
                instructionDetail.setOperationId(operationInstruction.getId());
                instructionDetail.setWaterMeterNum(operationInstruction.getWaterMeterNum());
                instructionDetail.setInstructionDetail(instructionJSON);
                waterPurchaseMgtService.insertInstructionDetail(instructionDetail);

                //向指令明细表插入记录为新房间
                InstructionDetailVo newInstructionDetail = new InstructionDetailVo();
                newInstructionDetail.setId(UUIDUtils.getUUID());
                newInstructionDetail.setWaterMeterInfoId(newMeterInfo.getId());
                newInstructionDetail.setRecordId(replaceRoomRecord.getId());
                newInstructionDetail.setType(MyConstant.OPERATION_TYPE_3);
                newInstructionDetail.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                newInstructionDetail.setUserId(replaceRoomRecord.getUserId());
                newInstructionDetail.setCollectId(newMeterInfo.getCollectId());
                newInstructionDetail.setCollectIp(newMeterInfo.getCollectIp());
                newInstructionDetail.setApartmentId(newMeterInfo.getApartmentId());
                newInstructionDetail.setOperationId(operationInstruction.getId());
                instructionDetail.setWaterMeterNum(operationInstruction.getWaterMeterNum());
                instructionDetail.setInstructionDetail(instructionJSON);
                waterPurchaseMgtService.insertInstructionDetail(instructionDetail);

                //向控水换房记录表插入一条换房记录
                replaceRoomRecord.setOperationId(operationInstruction.getId());
                insertReplaceRoomRecord(replaceRoomRecord);

                //通知采集下发命令
                instructionsService.addWater(newMeterInfo.getConcentratorNum(),concentrator);
            }
        }
        return message;
    }

    /**
     * 是否符合控水换房条件
     * 只有同一类型的房间才能换房。
     * 若选中的房间含有任何未完成的指令时，不允许进行换房，必须等指令都结束才能进行换房。
     * 原房间的清零需要用户手动在清零功能中清零，若没有清零不允许操作。
     * 判断新房间有没有清零，若没有清零提醒用户手动去清零。
     * @param oldMeterInfo
     * @param newMeterInfo
     * @return
     */
    private String checkAllow(WaterMeterInfoVo oldMeterInfo, WaterMeterInfoVo newMeterInfo){
        String errmsg = "";
        //是否同一类型的房间
        if((oldMeterInfo.getRoomTypeId() == null && newMeterInfo.getRoomTypeId()!= null) ||
                (oldMeterInfo.getRoomTypeId() != null && newMeterInfo.getRoomTypeId()== null)){
            errmsg = "只有同一类型的房间才能换房！";
        }else if(!oldMeterInfo.getRoomTypeId().equals(newMeterInfo.getRoomTypeId())){
            errmsg = "只有同一类型的房间才能换房！";
        }else if(waterPurchaseMgtService.getNotFinishByMeterIdAndType(oldMeterInfo.getId(),null)){
            errmsg = "原房间存在未完成指令，不允许换房！";
        }else if(waterPurchaseMgtService.getNotFinishByMeterIdAndType(newMeterInfo.getId(),null)){
            errmsg = "新房间存在未完成指令，不允许换房！";
        }else if(!MyConstant.INIT_STATUS_2.equals(oldMeterInfo.getInitStatus())){
            //判断新房间有没有清零，若没有清零提醒用户手动去清零。
            errmsg = "原房间没有清零不能换房，请手动清零！";
        }else if(!MyConstant.INIT_STATUS_2.equals(newMeterInfo.getInitStatus())){
            //判断新房间有没有清零，若没有清零提醒用户手动去清零。
            errmsg = "新房间没有清零不能换房，请手动清零！";
        }
        return  errmsg;
    }

    /**
     * 向控水换房记录表插入一条记录
     * @param replaceRoomRecord
     */
    public void insertReplaceRoomRecord(ReplaceRoomRecordVo replaceRoomRecord){
        waterRoomMgtDao.insertReplaceRoomRecord(replaceRoomRecord);
    }

    /**
     * 获取换房bean
     * @param waterMeterInfo
     * @param replaceRoomRecord
     * @return
     */
    private WaterBean getRepalceWaterBean(WaterMeterInfoVo waterMeterInfo, ReplaceRoomRecordVo replaceRoomRecord){
        WaterBean waterBean = new WaterBean();
        waterBean.setWaterMeterNum(Integer.parseInt(waterMeterInfo.getWaterMeterNum()));
        waterBean.setBuyWater((new Double(replaceRoomRecord.getBuyWaterTotal()*10)).intValue());
        waterBean.setAddWater(0);
        waterBean.setAddNum(waterMeterInfo.getAddNum()+1);
        return waterBean;
    }

}
