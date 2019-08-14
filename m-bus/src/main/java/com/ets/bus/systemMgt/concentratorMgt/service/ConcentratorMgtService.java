package com.ets.bus.systemMgt.concentratorMgt.service;

import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.entity.WaterBean;
import com.ets.bus.socket.server.ConvertCode;
import com.ets.bus.socket.server.ServerHandler;
import com.ets.bus.systemMgt.concentrator.entity.Concentrator;
import com.ets.bus.systemMgt.concentrator.service.ConcentratorService;
import com.ets.bus.systemMgt.concentratorMgt.dao.ConcentratorMgtDao;
import com.ets.bus.systemMgt.concentratorMgt.entity.CollectVo;
import com.ets.bus.systemMgt.concentratorMgt.entity.ConcentratorVo;
import com.ets.bus.systemMgt.concentratorMgt.entity.ReportCycleVo;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.instructionOperation.service.InstructionsService;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.*;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.common.MyConstant;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.Message;
import com.ets.utils.PageListData;
import com.ets.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/4/10
 * 集中器服务
 */
@Service
@Transactional
@SuppressWarnings("all")
public class ConcentratorMgtService {
    @Resource
    private ConcentratorMgtDao concentratorMgtDao;
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private InstructionsService instructionsService;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    ConcentratorService concentratorService;

    /**
     * 查询集中器集合
     * @param param
     * @return
     */
    public PageListData<ConcentratorVo> getConcentratorList(int page, int limit, Map<String, Object> param){
        PageHelper.startPage(page, limit);
        List<ConcentratorVo> list = concentratorMgtDao.selectConcentratorList(param);
        List<ConcentratorVo> cList=new ArrayList<ConcentratorVo>();
        //设置在线情况
        setOnline(list);
        if(param.get("status")!=null && param.get("status")!="")
        {
            String flag=null;
            if(param.get("status").equals("0")){
                flag="在线";
            }else{
                flag="离线";
            }
            for(ConcentratorVo li:list){

                if(flag.equals(li.getOnline())){
                    cList.add(li);
                }
            }
            list.clear();
            list.addAll(cList);
        }
        PageInfo<ConcentratorVo> pageInfo = new PageInfo<ConcentratorVo>(list);
        PageListData<ConcentratorVo> pageData = new PageListData<ConcentratorVo>();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(list);

        return pageData;
    }

    /**
     * 校时-将服务器时间更新到集中器上
     * 通知采集，将服务器时间更新到选中的集中器以及集中器下所有的水表，
     * 需要向操作指令表插入一条指令，标记为校时，且状态未下发。
     * @param request
     * @return
     */
    public Message calibrationTime(HttpServletRequest request){
        Message message = new Message(MyConstant.MSG_SUCCESS,"校时指令下发成功！");
        String idArray = request.getParameter("idArray");
        String[] arr=new String[0];
        boolean flag = true;
        //校验数据是否合法
        if(StringUtils.isBlank(idArray)){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg("集中器ID不能为空！");
            flag=false;
        }else{
            arr = idArray.split(",");
            //若当前存在未执行完毕的指令，则不允许对相同的集中器重复操作同样的指令
            String msg = checkNotFinishInsByConId(arr,MyConstant.OPERATION_TYPE_9);
            if(StringUtils.isNotBlank(msg)){
                message.setStatus(MyConstant.MSG_FAIL);
                message.setMsg(msg);
                flag =false;

            }
        }
        if(flag){
            String str="";
            for (String concentratorId : arr) {
                //根据集中器ID获取集中器信息
                ConcentratorVo concentrator =concentratorMgtDao.findConcentratormgtById(concentratorId);
                ConcentratorProtocolBean conProtocolBean = instructionsService.compareTimeConcent(concentrator.getConcentratorNum());

                //先向操作指令表插入校时指令
                OperationInstructionVo operationInstruction = new OperationInstructionVo();
                operationInstruction.setType(MyConstant.OPERATION_TYPE_9);
                operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                operationInstruction.setUserId(waterPurchaseMgtService.getLoginUserId());
                operationInstruction.setId(UUIDUtils.getUUID());
                operationInstruction.setApartmentId(concentrator.getApartmentId());
                operationInstruction.setConcentratorId(concentrator.getId());
                operationInstruction.setInstructionDetail(new Gson().toJson(conProtocolBean));
                waterPurchaseMgtService.insertOperationInstruction(operationInstruction);

                //通知采集下发命令
                instructionsService.compareTime(concentrator.getConcentratorNum(),conProtocolBean);

            }

        }
        return message;
       // ServerHandler.channelMap.get("172.215.2.41").writeAndFlush(new ConcentratorProtocolBean());
       // return null;
    }

    /**
     * 确认发参数
     * @param request
     * @return
     */
    public Message setParamConfirm(HttpServletRequest request){
        Message message = new Message(MyConstant.MSG_SUCCESS,"发参数指令下发成功！");
        String concentratorId = request.getParameter("concentratorId");
        String type = request.getParameter("type");
        boolean flag = true;
        //校验数据是否合法
        if(StringUtils.isBlank(concentratorId)){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg("集中器ID不能为空！");
            flag=false;
        }else{
            //若当前存在未执行完毕的指令，则不允许对相同的集中器重复操作同样的指令
            String msg = checkNotFinishInsByConId(new String[]{concentratorId},MyConstant.OPERATION_TYPE_10);
            if(StringUtils.isNotBlank(msg)){
                message.setStatus(MyConstant.MSG_FAIL);
                message.setMsg(msg);
                flag=false;
            }
            String msg2 = checkNotFinishInsByConId(new String[]{concentratorId},MyConstant.OPERATION_TYPE_11);
            if(StringUtils.isNotBlank(msg2)){
                message.setStatus(MyConstant.MSG_FAIL);
                message.setMsg(msg2);
                flag=false;
            }
        }

        if(flag){
            //仅设置集中器
            if(type.equals("1")){
                setConcentratorParam(concentratorId,MyConstant.OPERATION_TYPE_10);
            //设置集中器及水表
            }else{
                setConcentratorParam(concentratorId,MyConstant.OPERATION_TYPE_11);
                //获取集中器下所有水表房间
                List<WaterMeterInfoVo> waterMeterInfoList = getWaterMeterInfoAndParam(concentratorId);
                for (WaterMeterInfoVo item : waterMeterInfoList) {
                    //房间发参数
                    sendRoomParamRecord(item);
                }
            }
        }
        return message;
    }

    /**
     * 获取集中器下所有的房间的当前信息
     * 通知采集，获取指定集中器下面所有水表的各类数值，需要向操作指令表插入一条指令，标记为获取集中器下房间信息
     * @param request
     * @return
     */
    public Message getRoomInfo(HttpServletRequest request){
        Message message = new Message(MyConstant.MSG_SUCCESS,"获取房间信息指令下发成功！");
        String idArray = request.getParameter("idArray");
        String[] arr = new String[0];
        boolean flag = true;
        //校验数据是否合法
        if(StringUtils.isBlank(idArray)){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg("集中器ID不能为空！");
            flag = false;
        }else{
            arr = idArray.split(",");
            //若当前存在未执行完毕的指令，则不允许对相同的集中器重复操作同样的指令
            String msg = checkNotFinishInsByConId(arr,MyConstant.OPERATION_TYPE_8);
            if(StringUtils.isNotBlank(msg)){
                message.setStatus(MyConstant.MSG_FAIL);
                message.setMsg(msg);
                flag = false;
            }
        }
        if(flag){
            String str="";
            String str1="";
            for (String concentratorId : arr) {
                //获取集中器下所有水表房间
                List<WaterMeterInfoVo> waterMeterInfoList = concentratorMgtDao.getWaterMeterInfoAndParam(concentratorId);
                //发送更新房间信息指令
                sendWaterMeterList(waterMeterInfoList);
                str+=concentratorId+",";
            }
            //通过集中器ID查询集中器信息
            List<Concentrator> cList=concentratorService.findConcentratorById(str.split(","));
            for(Concentrator c:cList){
                str1+=c.getConcentrator_num()+",";
            }
            /**************添加操作日志start***************/
            mb_operation_log mol=new mb_operation_log();
            mol.setModuleName("设备管理-集中器管理");
            mol.setOperaContent("获取编号为:["+str1.substring(0,str1.length()-1)+"]的集中器下所有房间信息");
            operationLogService.addLog(mol);
            /**************添加操作日志end****************/
        }
        return message;
    }

    /**
     * 重启集中器
     * @param request
     * @return
     */
    public Message restart(HttpServletRequest request){

        Channel ch=ServerHandler.channelMap.get("172.215.2.41");
        ConcentratorProtocolBean cpb=new ConcentratorProtocolBean();
        //7E 12 66 66 66 66 26 15 00 00 01 E5 0D 0A

        return null;

    }

    /**
     * 换集中器
     * 将集中器所管理的房间信息更新到新的集中器上
     * @param request
     * @return
     */
    public Message replace(HttpServletRequest request){
        Message message = new Message(MyConstant.MSG_SUCCESS,"换集中器指令下发成功！");
        String idArray = request.getParameter("idArray");
        //校验数据是否合法
        if(StringUtils.isBlank(idArray)){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg("集中器ID不能为空！");
        }else{
            //若当前存在未执行完毕的指令，则不允许对相同的集中器重复操作同样的指令

            //向操作指令表插入一条指令，标记为更换集中器

            //通知采集下发命令
        }
        return message;
    }

    /**
     * 获取集中器操作指令列表
     * @param page
     * @param limit
     * @param param
     * @return
     */
    public PageListData<OperationInstructionVo> getRecordListData(int page, int limit, Map<String, Object> param) {
        PageHelper.startPage(page, limit);
        List<OperationInstructionVo> list = concentratorMgtDao.selectRecordList(param);
        for (OperationInstructionVo item : list) {
            item.setTypeName(MyConstant.OPERATION_TYPE_STATUS_MAP.get(item.getType()));
        }
        PageInfo<OperationInstructionVo> pageInfo = new PageInfo<OperationInstructionVo>(list);
        PageListData<OperationInstructionVo> pageData = new PageListData<OperationInstructionVo>();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(list);

        //根据集中器ID查询集中器信息
        Concentrator c=concentratorService.findConcentrator((String)param.get("id"));
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-集中器管理");
        mol.setOperaContent("查看编号为:["+c.getConcentrator_num()+"]集中器的指令记录");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/

        return pageData;
    }

    /***
     * 获取请求域中的查询条件
     * @param request
     * @return
     */
    public Map<String, Object> getReqSearchParam(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(request.getParameter("date"))){
            String date = DateTimeUtils.getMonthDayByString(request.getParameter("date"));
            map.put("date", date);
        }
        map.put("currentStatus", request.getParameter("currentStatus"));
        map.put("id", request.getParameter("id"));

        return map;
    }

    public ConcentratorVo findConcentratormgtById(String id)
    {
        ConcentratorVo cv=concentratorMgtDao.findConcentratormgtById(id);
        cv.setCreateTime(new Timestamp(cv.getCreateTime().getTime()));
        cv.setUpdateTime(new Timestamp(cv.getUpdateTime().getTime()));
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-集中器管理");
        mol.setOperaContent("查看编号为:["+cv.getConcentratorNum()+"]集中器详细信息");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return cv;
    }

    /**
     * 若当前存在未执行完毕的指令，则不允许对相同的集中器重复操作同样的指令
     * @param arr
     * @param type
     * @return
     */
    private String checkNotFinishInsByConId(String[] arr, Integer type){
        List<OperationInstructionVo> list= concentratorMgtDao.checkNotFinishInsByConId(arr, type);
        String result = null;
        if(list != null && list.size()>0){
            result = "存在未完成的指令，不允许重复操作！";
        }

        return result;
    }

    private void setConcentratorParam(String  concentratorId, Integer type){
        //集中器信息
        ConcentratorVo concentrator =concentratorMgtDao.findConcentratormgtById(concentratorId);
        //获取参数
        ReportCycleVo reportCycle = getConcentratorParam(concentratorId);
        //获取指令对象
        ConcentratorProtocolBean concentPro = instructionsService.setConcentratorParamConcent(concentrator.getConcentratorNum(),reportCycle.getHour(),reportCycle.getMinute());
        String instructionJSON = new Gson().toJson(concentPro);

        //插入操作指令表
        OperationInstructionVo operationInstruction = new OperationInstructionVo();
        operationInstruction.setType(type);
        operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
        operationInstruction.setUserId(waterPurchaseMgtService.getLoginUserId());
        operationInstruction.setId(UUIDUtils.getUUID());
        operationInstruction.setApartmentId(concentrator.getApartmentId());
        operationInstruction.setConcentratorId(concentrator.getId());
        operationInstruction.setInstructionDetail(instructionJSON);
        waterPurchaseMgtService.insertOperationInstruction(operationInstruction);

        //插入指令明细表
        InstructionDetailVo instructionDetail = new InstructionDetailVo();
        instructionDetail.setId(UUIDUtils.getUUID());
        instructionDetail.setType(type);
        instructionDetail.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
        instructionDetail.setUserId(waterPurchaseMgtService.getLoginUserId());
        instructionDetail.setApartmentId(concentrator.getApartmentId());
        instructionDetail.setOperationId(operationInstruction.getId());
        instructionDetail.setInstructionDetail(instructionJSON);
        waterPurchaseMgtService.insertInstructionDetail(instructionDetail);

        //通知采集下发命令
        instructionsService.setConcentratorParam(concentrator.getConcentratorNum(),concentPro);
    }

    /**
     * 获取集中器下所有水表房间包括绑定的参数
     * 优先发送该房间对应的房间类型中的参数，若没有配置房间类型，则发送默认参数；
     * @param concentratorId
     * @return
     */
    private List<WaterMeterInfoVo> getWaterMeterInfoAndParam(String concentratorId){
        SysoperatParamVo sysoperatParam = null;
        List<WaterMeterInfoVo> waterMeterInfoList = concentratorMgtDao.getWaterMeterInfoAndParam(concentratorId);
        for (WaterMeterInfoVo item : waterMeterInfoList) {
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
        return waterMeterInfoList;
    }

    /**
     * 获取下发水表参数指令对象
     * @param waterMeterInfo
     * @return
     */
    private WaterBean getWaterBean(WaterMeterInfoVo waterMeterInfo){
        WaterBean waterBean = new WaterBean();
        waterBean.setWaterMeterNum(Integer.parseInt(waterMeterInfo.getWaterMeterNum()));
       // waterBean.setWarnWater((new Double(waterMeterInfo.getWarnWater()*10)).intValue());
        waterBean.setWarnWater(1000);
        waterBean.setValveWater((new Double(waterMeterInfo.getCloseValveWater()*10)).intValue());
        waterBean.setOverWater((new Double(waterMeterInfo.getMaxOverWater()*10)).intValue());
        waterBean.setHoardWater((new Double(waterMeterInfo.getMaxWater()*10)).intValue());
        return waterBean;
    }

    //获取下发集中器指令参数
    private ReportCycleVo getConcentratorParam(String concentratorId){
        //获取绑定采集的下发周期
        CollectVo collectVo = concentratorMgtDao.getCollectParam(concentratorId);

        ReportCycleVo reportCycle = new ReportCycleVo();
        reportCycle.setHour((int) Math.floor(collectVo.getAutoCollectFrequency() / 60));
        reportCycle.setMinute(collectVo.getAutoCollectFrequency() % 60);
        return reportCycle;
    }

    /**
     * 获取水表信息
     * @param waterMeterInfoList
     */
    private void sendWaterMeterList(List<WaterMeterInfoVo> waterMeterInfoList){
        //批量发送命令
        if(waterMeterInfoList!=null && waterMeterInfoList.size()>0){
            //从采集获取数据刷新水表信息
            waterPurchaseMgtService.sendGroupWaterMeterStatus(waterMeterInfoList);
        }

    }

    /**
     * 根据ID查询指令信息
     * @param id
     * @return
     */
    public OperationInstructionVo findInstructionsById(String id){
        OperationInstructionVo ov=concentratorMgtDao.findInstructionsById(id);
        ov.setCreateTime(new Timestamp(ov.getCreateTime().getTime()));
        if(ov.getUpdateTime()!=null){
            ov.setUpdateTime(new Timestamp(ov.getUpdateTime().getTime()));
        }
        if(ov.getType()!=null){
            ov.setTypeName(MyConstant.OPERATION_TYPE_STATUS_MAP.get(ov.getType()));
        }
        return ov;
    }

    /**
     * 设置房间水表参数
     */
    public void sendRoomParamRecord(WaterMeterInfoVo waterMeterInfo){
        RoomParamRecordVo record = new RoomParamRecordVo();
        record.setId(UUIDUtils.getUUID());
        record.setAddCycle(waterMeterInfo.getAddCycle());
        record.setBuyMoney(waterMeterInfo.getPrice());
        record.setReturnMoney(waterMeterInfo.getReturnMoney());
        record.setHoardWater(waterMeterInfo.getMaxWater());
        record.setOverWater(waterMeterInfo.getMaxOverWater());
        record.setValveWater(waterMeterInfo.getCloseValveWater());
        record.setWarnWater(waterMeterInfo.getWarnWater());
        record.setConcentratorId(waterMeterInfo.getConcentratorId());
        record.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
        record.setUserId(waterPurchaseMgtService.getLoginUserId());
        record.setWaterMeterInfoId(waterMeterInfo.getId());

        //获取指令对象
        List<WaterBean> waterBeans = new ArrayList<WaterBean>();
        WaterBean water = getWaterBean(waterMeterInfo);
        waterBeans.add(water);
        ConcentratorProtocolBean concentrator = instructionsService.updateWaterConcent(waterMeterInfo.getConcentratorNum(),waterBeans);
        String instructionJSON = new Gson().toJson(concentrator);

        //插入操作指令表
        OperationInstructionVo operationInstruction = new OperationInstructionVo();
        operationInstruction.setId(UUIDUtils.getUUID());
        operationInstruction.setWaterMeterInfoId(waterMeterInfo.getId());
        operationInstruction.setType(MyConstant.OPERATION_TYPE_12);
        operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
        operationInstruction.setUserId(record.getUserId());
        operationInstruction.setCollectId(waterMeterInfo.getCollectId());
        operationInstruction.setCollectIp(waterMeterInfo.getCollectIp());
        operationInstruction.setApartmentId(waterMeterInfo.getApartmentId());
        operationInstruction.setConcentratorId(waterMeterInfo.getConcentratorId());
        operationInstruction.setWaterMeterNum(waterMeterInfo.getWaterMeterNum());
        operationInstruction.setInstructionDetail(instructionJSON);
        waterPurchaseMgtService.insertOperationInstruction(operationInstruction);

        //插入指令明细表
        InstructionDetailVo instructionDetail = new InstructionDetailVo();
        instructionDetail.setId(UUIDUtils.getUUID());
        instructionDetail.setWaterMeterInfoId(waterMeterInfo.getId());
        instructionDetail.setType(MyConstant.OPERATION_TYPE_12);
        instructionDetail.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
        instructionDetail.setUserId(waterPurchaseMgtService.getLoginUserId());
        instructionDetail.setCollectId(waterMeterInfo.getCollectId());
        instructionDetail.setCollectIp(waterMeterInfo.getCollectIp());
        instructionDetail.setApartmentId(waterMeterInfo.getApartmentId());
        instructionDetail.setOperationId(operationInstruction.getId());
        instructionDetail.setWaterMeterNum(operationInstruction.getWaterMeterNum());
        instructionDetail.setInstructionDetail(instructionJSON);
        waterPurchaseMgtService.insertInstructionDetail(instructionDetail);

        //保存记录
        record.setOperationId(operationInstruction.getId());
        waterPurchaseMgtService.insertRoomParamRecord(record);

        //通知采集下发命令
        instructionsService.updateWater(waterMeterInfo.getConcentratorNum(),concentrator);
    }

    public void setOnline(List<ConcentratorVo> list){
        for (ConcentratorVo item: list) {
            if(ServerHandler.channelMap.containsKey(item.getConcentratorNum())){
                item.setOnline(MyConstant.MSG_EXIST);
            }else{
                item.setOnline(MyConstant.MSG_NOT_EXIST);
            }
        }
    }

}
