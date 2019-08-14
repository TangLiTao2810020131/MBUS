package com.ets.bus.systemMgt.floorMgt.service;

import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;
import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.entity.WaterBean;
import com.ets.bus.socket.server.ConstantValue;
import com.ets.bus.systemMgt.concentratorMgt.service.ConcentratorMgtService;
import com.ets.bus.systemMgt.floorMgt.dao.FloorMgtDao;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.systemMgt.waterMeterMgt.dao.WaterMeterMgtDao;
import com.ets.bus.systemMgt.waterMeterMgt.entity.WaterMeterMgt;
import com.ets.bus.systemMgt.watermeterRoomInfoMgt.service.WaterMeterInfoMgtService;
import com.ets.bus.waterMeterMgt.instructionOperation.service.InstructionsService;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.OperationInstructionVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.SysoperatParamVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.bus.waterMeterMgt.waterReset.service.WaterResetService;
import com.ets.common.MyConstant;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.Message;
import com.ets.utils.PageListData;
import com.ets.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/4/11
 * 房间管理
 */
@Service
@Transactional
@SuppressWarnings("all")
public class FloorMgtService {
    @Resource
    private FloorMgtDao floorMgtDao;
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private ConcentratorMgtService concentratorMgtService;
    @Autowired
    private InstructionsService instructionsService;
    @Autowired
    private WaterResetService waterResetService;
    @Autowired
    private WaterMeterInfoMgtService waterMeterInfoMgtService;
    @Autowired
    private WaterMeterMgtDao waterMeterMgtDao;
    @Autowired
    private OperationLogService operationLogService;
    String moduleName="设备管理-房间管理";


    /**
     * 获取房间指令列表
     * @param page
     * @param limit
     * @param param
     * @return
     */
    public PageListData<OperationInstructionVo> getRecordListData(int page, int limit, Map<String, Object> param) {
        PageHelper.startPage(page, limit);
        List<OperationInstructionVo> list = floorMgtDao.selectRecordList(param);
        for (OperationInstructionVo item : list) {
            item.setTypeName(MyConstant.OPERATION_TYPE_STATUS_MAP.get(item.getType()));
        }

        PageInfo<OperationInstructionVo> pageInfo = new PageInfo<OperationInstructionVo>(list);
        PageListData<OperationInstructionVo> pageData = new PageListData<OperationInstructionVo>();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(list);

        return pageData;
    }

    /**
     * 更新房间参数
     * @param ids
     * @return
     */
    public Message updateRoomParam(String[] ids){
        Message message = checkDondition(ids,MyConstant.OPERATION_TYPE_12,null);
        StringBuffer sb=new StringBuffer();
        if(MyConstant.MSG_SUCCESS.equals(message.getStatus())){
            List<WaterMeterInfoVo> list = getWaterMeterInfoByIds(ids);
            if(list.size() != ids.length){
                message.setStatus(MyConstant.MSG_FAIL);
                message.setMsg("房间未绑定水表或集中器！");
            }else {
                for (WaterMeterInfoVo waterMeterInfo: list) {
                    //更新房间参数
                    concentratorMgtService.sendRoomParamRecord(waterMeterInfo);
                    sb.append(waterMeterInfo.getAreaName()+"-"+waterMeterInfo.getFloorName()+"-"+waterMeterInfo.getRoomNum()+",");
                }

                mb_operation_log operationLog=new mb_operation_log();
                operationLog.setModuleName(moduleName);
                operationLog.setOperaContent("更新"+sb.substring(0,sb.length()-1)+"房间参数");
                operationLogService.addLog(operationLog);
            }
        }

        return message;
    }

    /**
     * 获取房间信息
     * @param ids
     * @return
     */
    public Message getRoomInfo(String[] ids){
        Message message = checkDondition(ids,MyConstant.OPERATION_TYPE_8,null);
        StringBuffer sb=new StringBuffer();
        if(MyConstant.MSG_SUCCESS.equals(message.getStatus())){
            List<WaterMeterInfoVo> list = getWaterMeterInfoByIds(ids);
            for (WaterMeterInfoVo waterMeterInfo: list) {
                //更新房间参数
                waterPurchaseMgtService.sendWaterMeterStatus(waterMeterInfo);
                sb.append(waterMeterInfo.getAreaName()+"-"+waterMeterInfo.getFloorName()+"-"+waterMeterInfo.getRoomNum()+",");
             }
            mb_operation_log operationLog=new mb_operation_log();
            operationLog.setModuleName(moduleName);
            operationLog.setOperaContent("获取"+sb.substring(0,sb.length()-1)+"房间信息");
            operationLogService.addLog(operationLog);
        }

        return message;
    }

    /**
     * 获取房间信息
     * @param id
     * @return
     */
    public Message initRoom(String id){
        StringBuffer sb=new StringBuffer();
        String[] ids = new String[]{id};
        Message message = checkDondition(ids,null,null);
        if(MyConstant.MSG_SUCCESS.equals(message.getStatus())){
            List<WaterMeterInfoVo> list = getWaterMeterInfoByIds(ids);
            for (WaterMeterInfoVo waterMeterInfo: list) {
                sb.append(waterMeterInfo.getAreaName()+"-"+waterMeterInfo.getFloorName()+"-"+waterMeterInfo.getRoomNum()+",");
            }
            //清零
            waterResetService.sendClear(id,"初始化房间", MyConstant.CLEAR_TYPE_1);
            //发参数
            concentratorMgtService.sendRoomParamRecord(list.get(0));
            mb_operation_log operationLog=new mb_operation_log();
            operationLog.setModuleName(moduleName);
            operationLog.setOperaContent("为"+sb.substring(0,sb.length()-1)+"房间初始化");
            operationLogService.addLog(operationLog);


        }

        return message;
    }

    /**
     * 一般开阀
     * @param ids
     * @return
     */
    public Message open(String[] ids){
        Message message = checkDondition(ids,null,new Integer[]{MyConstant.OPERATION_TYPE_14,MyConstant.OPERATION_TYPE_15,MyConstant.OPERATION_TYPE_16,MyConstant.OPERATION_TYPE_17});
        StringBuffer sb=new StringBuffer();
        if(MyConstant.MSG_SUCCESS.equals(message.getStatus())){
            List<WaterMeterInfoVo> list = getWaterMeterInfoByIds(ids);
            for (WaterMeterInfoVo waterMeterInfo: list) {
                //发送命令
                sendWaterMeterStatus(waterMeterInfo, MyConstant.OPERATION_TYPE_14);
                sb.append(waterMeterInfo.getAreaName()+"-"+waterMeterInfo.getFloorName()+"-"+waterMeterInfo.getRoomNum()+",");
            }
            mb_operation_log operationLog=new mb_operation_log();
            operationLog.setModuleName(moduleName);
            operationLog.setOperaContent("为"+sb.substring(0,sb.length()-1)+"房间一般开阀");
            operationLogService.addLog(operationLog);
        }

        return message;
    }

    /**
     * 一般关阀
     * @param ids
     * @return
     */
    public Message close(String[] ids){
        StringBuffer sb=new StringBuffer();
        Message message = checkDondition(ids,null,new Integer[]{MyConstant.OPERATION_TYPE_14,MyConstant.OPERATION_TYPE_15,MyConstant.OPERATION_TYPE_16,MyConstant.OPERATION_TYPE_17});
        if(MyConstant.MSG_SUCCESS.equals(message.getStatus())){
            List<WaterMeterInfoVo> list = getWaterMeterInfoByIds(ids);
            for (WaterMeterInfoVo waterMeterInfo: list) {
                //发送命令
                sendWaterMeterStatus(waterMeterInfo, MyConstant.OPERATION_TYPE_15);
                sb.append(waterMeterInfo.getAreaName()+"-"+waterMeterInfo.getFloorName()+"-"+waterMeterInfo.getRoomNum()+",");
            }
            mb_operation_log operationLog=new mb_operation_log();
            operationLog.setModuleName(moduleName);
            operationLog.setOperaContent("为"+sb.substring(0,sb.length()-1)+"房间一般关阀");
            operationLogService.addLog(operationLog);
        }

        return message;
    }

    /**
     * 强制开阀
     * @param ids
     * @return
     */
    public Message forceOpen(String[] ids){
        StringBuffer sb=new StringBuffer();
        Message message = checkDondition(ids,null,new Integer[]{MyConstant.OPERATION_TYPE_14,MyConstant.OPERATION_TYPE_15,MyConstant.OPERATION_TYPE_16,MyConstant.OPERATION_TYPE_17});
        if(MyConstant.MSG_SUCCESS.equals(message.getStatus())){
            List<WaterMeterInfoVo> list = getWaterMeterInfoByIds(ids);
            for (WaterMeterInfoVo waterMeterInfo: list) {
                //发送命令
                sendWaterMeterStatus(waterMeterInfo, MyConstant.OPERATION_TYPE_16);
                sb.append(waterMeterInfo.getAreaName()+"-"+waterMeterInfo.getFloorName()+"-"+waterMeterInfo.getRoomNum()+",");
            }
            mb_operation_log operationLog=new mb_operation_log();
            operationLog.setModuleName(moduleName);
            operationLog.setOperaContent("为"+sb.substring(0,sb.length()-1)+"房间强制开阀");
            operationLogService.addLog(operationLog);
        }

        return message;
    }

    /**
     * 强制关阀
     * @param ids
     * @return
     */
    public Message forceClose(String[] ids){
        StringBuffer sb=new StringBuffer();
        Message message = checkDondition(ids,null,new Integer[]{MyConstant.OPERATION_TYPE_14,MyConstant.OPERATION_TYPE_15,MyConstant.OPERATION_TYPE_16,MyConstant.OPERATION_TYPE_17});
        if(MyConstant.MSG_SUCCESS.equals(message.getStatus())){
            List<WaterMeterInfoVo> list = getWaterMeterInfoByIds(ids);
            for (WaterMeterInfoVo waterMeterInfo: list) {
                //发送命令
                sendWaterMeterStatus(waterMeterInfo, MyConstant.OPERATION_TYPE_17);
                sb.append(waterMeterInfo.getAreaName()+"-"+waterMeterInfo.getFloorName()+"-"+waterMeterInfo.getRoomNum()+",");
            }
            mb_operation_log operationLog=new mb_operation_log();
            operationLog.setModuleName(moduleName);
            operationLog.setOperaContent("为"+sb.substring(0,sb.length()-1)+"房间强制关阀");
            operationLogService.addLog(operationLog);
        }

        return message;
    }
    /**
     * 更换水表
     * @param id,replace_money,type,newWaterMeterId,openTime,remark
     * @return
     */
    public Message change(String id, Double replace_money, Integer type, String newWaterMeterId, String openTime,String remark){
        Message message = checkDondition(new String[]{id},null,null);
        //判断水表编号是否已存在，存在则判断是否已被使用
        WaterMeterMgt waterMeterMgt = waterMeterMgtDao.getWaterMeterByNum(newWaterMeterId);
        if(waterMeterMgt != null && waterMeterMgt.getRoomNum() != null){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg("该水表已被房间绑定,请重新输入水表编号。");
        }else if(waterMeterMgt == null){
            //新增水表信息
            waterMeterMgt = new WaterMeterMgt();
            waterMeterMgt.setId(UUIDUtils.getUUID());
            waterMeterMgt.setDelStatus(0);
            waterMeterMgt.setCreate_time(DateTimeUtils.getnewsdate());
            waterMeterMgt.setWater_meter_id(newWaterMeterId);
            waterMeterMgt.setType(type);
            waterMeterMgtDao.saveWaterMeter(waterMeterMgt);
        }
        if(MyConstant.MSG_SUCCESS.equals(message.getStatus())){
            //发送更换水表的指令
            sendWaterMeterChangeStatus(id,replace_money,type,waterMeterMgt,openTime,remark);
        }
        return message;
    }

    /**
     * 发送更换水表的指令
     * @param id
     * @param replace_money
     * @param type
     * @param waterMeterMgt
     * @param openTime
     * @param remark
     */
    public void sendWaterMeterChangeStatus(String id, Double replace_money, Integer type, WaterMeterMgt waterMeterMgt, String openTime,String remark) {
        mb_worker workerSession = (mb_worker) SecurityUtils.getSubject().getSession().getAttribute("workerSession");
        //根据Id获取绑定水表的房间信息
        WaterMeterInfoVo waterMeterInfo = floorMgtDao.getWaterMeterInfoById(id);
        //获取指令对象
        List<WaterBean> waterBeans = new ArrayList<WaterBean>();
        //更换水表的指令（待实现）
        //WaterBean water = getWaterBean(waterMeterInfo, MyConstant.OPERATION_TYPE_18);
        //waterBeans.add(water);
        /*ConcentratorProtocolBean concentrator = instructionsService.openWaterMeterConcent(waterMeterInfo.getConcentratorNum(),waterBeans);*/
        /*String instructionJSON = new Gson().toJson(concentrator);*/

        //插入操作指令表
        OperationInstructionVo operationInstruction = new OperationInstructionVo();
        operationInstruction.setId(UUIDUtils.getUUID());
        operationInstruction.setWaterMeterInfoId(waterMeterInfo.getId());
        operationInstruction.setType(MyConstant.OPERATION_TYPE_18);
        operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
        operationInstruction.setUserId(workerSession.getId());
        operationInstruction.setCollectId(waterMeterInfo.getCollectId());
        operationInstruction.setCollectIp(waterMeterInfo.getCollectIp());
        operationInstruction.setApartmentId(waterMeterInfo.getApartmentId());
        operationInstruction.setConcentratorId(waterMeterInfo.getConcentratorId());
        operationInstruction.setWaterMeterNum(waterMeterInfo.getWaterMeterNum());
        //operationInstruction.setInstructionDetail(instructionJSON);
        waterPurchaseMgtService.insertOperationInstruction(operationInstruction);

       //通知采集下发命令（待实现）
        /*instructionsService.openWaterMeter(waterMeterInfo.getConcentratorNum(),concentrator);*/

//        //更新水表信息表中水表的uuid
//        RoomWaterMeterVo waterMeterRoomInfo = waterMeterInfoMgtService.getWaterMeterRoomInfoById(id);
//        Map map = new HashMap<String, Object>();
//        map.put("id", id);
//        map.put("waterMeterId", waterMeterMgt.getId());
//        waterMeterInfoMgtService.updateWaterMeterInfoByWaterMeterId(map);
//        //换表记录表中插入数据
//        ReplaceRecord record = floorMgtDao.getNumByWaterMeterId(id);
//        //判断换表次数
//        ReplaceRecord replaceRecord = new ReplaceRecord();
//        if (record == null || record.equals("")) {
//            replaceRecord.setReplaceWaterNum(1);
//            replaceRecord.setId(UUIDUtils.getUUID());
//            replaceRecord.setCurrentStatus(0);
//            replaceRecord.setNewWaterMeterId(waterMeterMgt.getId());
//            replaceRecord.setOldWaterMeterId(waterMeterRoomInfo.getWater_meter_id());
//            replaceRecord.setOpenTime(openTime);
//            replaceRecord.setOperationUser(workerSession.getWorkerName());
//            replaceRecord.setUserId(workerSession.getId());
//            replaceRecord.setReplaceMoney(replace_money);
//            replaceRecord.setWater_meter_info_id(id);
//            replaceRecord.setWaterType(type);
//            replaceRecord.setRemark(remark);
//            replaceRecord.setOperationId(operationInstruction.getId());
//            floorMgtDao.insertReplaceRecord(replaceRecord);
//        } else {
//            replaceRecord.setId(UUIDUtils.getUUID());
//            replaceRecord.setReplaceWaterNum(record.getReplaceWaterNum() + 1);
//            replaceRecord.setCurrentStatus(0);
//            replaceRecord.setNewWaterMeterId(waterMeterMgt.getId());
//            replaceRecord.setOldWaterMeterId(waterMeterRoomInfo.getWater_meter_id());
//            replaceRecord.setOpenTime(openTime);
//            replaceRecord.setOperationUser(workerSession.getWorkerName());
//            replaceRecord.setUserId(workerSession.getId());
//            replaceRecord.setReplaceMoney(replace_money);
//            replaceRecord.setWater_meter_info_id(id);
//            replaceRecord.setWaterType(type);
//            replaceRecord.setRemark(remark);
//            replaceRecord.setOperationId(operationInstruction.getId());
//            floorMgtDao.insertReplaceRecord(replaceRecord);
//        }
    }


    /**
     * 若当前存在未执行完毕的指令，则不允许对相同的房间重复操作同样的指令
     * @param arr
     * @param type
     * @return
     */
    public String checkNotFinishInsByIds(String[] arr, Integer type, Integer[] types){
        List<OperationInstructionVo> list= floorMgtDao.checkNotFinishInsByIds(arr, type, types);
        String result = null;
        if(list != null && list.size()>0){
            result = "存在未完成的指令，不允许重复操作！";
        }

        return result;
    }

    /**
     * 根据ID数组获取绑定集中器和水表的水表房间
     * @param ids
     * @return
     */
    public List<WaterMeterInfoVo> getWaterMeterInfoByIds(String[] ids){
        SysoperatParamVo sysoperatParam = null;
        List<WaterMeterInfoVo> list= floorMgtDao.getWaterMeterInfoByIds(ids);
        for (WaterMeterInfoVo item : list) {
            if(item.getRoomTypeId() == null){
                if(sysoperatParam==null){
                    sysoperatParam = waterPurchaseMgtService.getSysoperatParam();
                }
                item.setWarnWater(sysoperatParam.getWarnWater());
                item.setCloseValveWater(sysoperatParam.getValveWater());
                item.setMaxOverWater(sysoperatParam.getOverWater());
                item.setMaxWater(sysoperatParam.getHoardWater());
                item.setAddCycle(sysoperatParam.getAddCycle());
                item.setPrice(sysoperatParam.getBuyMoney());
                item.setReturnMoney(sysoperatParam.getReturnMoney());
            }
        }
        return list;
    }

    /**
     * 发送阀门命令
     * @param waterMeterInfo
     */
    public void sendWaterMeterStatus(WaterMeterInfoVo waterMeterInfo, Integer type){
        //获取指令对象
        List<WaterBean> waterBeans = new ArrayList<WaterBean>();
        WaterBean water = getWaterBean(waterMeterInfo, type);
        waterBeans.add(water);
        //拼装协议，增加了水表的编号
        ConcentratorProtocolBean concentrator = instructionsService.openWaterMeterConcent(waterMeterInfo.getConcentratorNum(),waterBeans);
        String instructionJSON = new Gson().toJson(concentrator);

        //插入操作指令表
        OperationInstructionVo operationInstruction = new OperationInstructionVo();
        operationInstruction.setId(UUIDUtils.getUUID());
        operationInstruction.setWaterMeterInfoId(waterMeterInfo.getId());
        operationInstruction.setType(type);
        operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
        operationInstruction.setUserId(waterPurchaseMgtService.getLoginUserId());
        operationInstruction.setCollectId(waterMeterInfo.getCollectId());
        operationInstruction.setCollectIp(waterMeterInfo.getCollectIp());
        operationInstruction.setApartmentId(waterMeterInfo.getApartmentId());
        operationInstruction.setConcentratorId(waterMeterInfo.getConcentratorId());
        operationInstruction.setWaterMeterNum(waterMeterInfo.getWaterMeterNum());
        operationInstruction.setInstructionDetail(instructionJSON);
        waterPurchaseMgtService.insertOperationInstruction(operationInstruction);

        //通知采集下发命令
        instructionsService.openWaterMeter(waterMeterInfo.getConcentratorNum(),concentrator);
    }

    public Message checkDondition(String[] ids, Integer type, Integer[] types){
        Message message = new Message(MyConstant.MSG_SUCCESS,"操作成功！");
        //校验数据是否合法
        if(ids==null || ids.length<1){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg("房间ID不能为空！");
        }else{
            //若当前存在未执行完毕的指令，则不允许重复操作同样的指令
            String msg = checkNotFinishInsByIds(ids,type, types);
            if(StringUtils.isNotBlank(msg)){
                message.setStatus(MyConstant.MSG_FAIL);
                message.setMsg(msg);
            }
        }
        return message;
    }

    /**
     * 获取下发水表参数指令对象
     * 水阀操作
     * 1字节16进制数
     * 00表示一般开阀
     * 01表示一般关阀
     * 02表示强制开阀
     * 03表是强制关阀
     * @param waterMeterInfo
     * @return
     */
    private WaterBean getWaterBean(WaterMeterInfoVo waterMeterInfo, Integer type){
        WaterBean waterBean = new WaterBean();
        waterBean.setWaterMeterNum(Integer.parseInt(waterMeterInfo.getWaterMeterNum()));
        switch (type){
            case MyConstant.OPERATION_TYPE_14 :
                waterBean.setOpenType(ConstantValue.OPEN_TYPE_0);
                break;
            case MyConstant.OPERATION_TYPE_15 :
                waterBean.setOpenType(ConstantValue.OPEN_TYPE_1);
                break;
            case MyConstant.OPERATION_TYPE_16 :
                waterBean.setOpenType(ConstantValue.OPEN_TYPE_2);
                break;
            case MyConstant.OPERATION_TYPE_17 :
                waterBean.setOpenType(ConstantValue.OPEN_TYPE_3);
                break;
            /*case MyConstant.OPERATION_TYPE_18 :
                waterBean.setOpenType(ConstantValue.OPEN_TYPE_4);
                break;*/

            default:
                break;
        }

        return waterBean;
    }

    public OperationInstructionVo findInstructionsRecord(String id)
    {
        OperationInstructionVo oiv=floorMgtDao.findInstructionsRecord(id);
        oiv.setCreateTime(new Timestamp(oiv.getCreateTime().getTime()));
        if(oiv.getUpdateTime()!=null){
            oiv.setUpdateTime(new Timestamp(oiv.getUpdateTime().getTime()));
        }
        if(oiv.getType()!=null){
            oiv.setTypeName(MyConstant.OPERATION_TYPE_STATUS_MAP.get(oiv.getType()));
        }
        return oiv;
    }
}
