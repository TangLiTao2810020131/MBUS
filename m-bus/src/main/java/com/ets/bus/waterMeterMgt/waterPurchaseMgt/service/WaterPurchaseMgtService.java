package com.ets.bus.waterMeterMgt.waterPurchaseMgt.service;

import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;
import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.entity.WaterBean;
import com.ets.bus.socket.server.ServerHandler;
import com.ets.bus.systemMgt.concentratorMgt.service.ConcentratorMgtService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.instructionOperation.service.InstructionsService;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.dao.WaterPurchaseMgtDao;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.*;
import com.ets.common.FileUploadUrl;
import com.ets.common.MyConstant;
import com.ets.utils.Message;
import com.ets.utils.PageListData;
import com.ets.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author 宋晨
 * @create 2019/3/27
 * 购水管理服务
 */
@Service
@Transactional
@SuppressWarnings("all")
public class WaterPurchaseMgtService{
    @Resource
    private WaterPurchaseMgtDao waterPurchaseMgtDao;
    @Autowired
    private InstructionsService instructionsService;
    @Autowired
    FileUploadUrl fileUploadUrl;
    @Autowired
    OperationLogService operationLogService;
    @Autowired
    private ConcentratorMgtService concentratorMgtService;

    private static final Logger logger = Logger.getLogger(WaterPurchaseMgtService.class);

    /**
     * 查询水表信息表集合
     * @param param
     * @return
     */
    public PageListData<WaterMeterInfoVo> getWaterMeterInfoList(int page, int limit, Map<String, Object> param){
        PageHelper.startPage(page, limit);
        List<WaterMeterInfoVo> list = waterPurchaseMgtDao.selectWaterMeterInfoList(param);
        setStatusNameList(list);

        PageInfo<WaterMeterInfoVo> pageInfo = new PageInfo<WaterMeterInfoVo>(list);
        PageListData<WaterMeterInfoVo> pageData = new PageListData<WaterMeterInfoVo>();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(list);

        return pageData;
    }

    /**
     * 根据ID查询房间水表信息
     * @param id
     * @return
     */
    public WaterMeterInfoVo getWaterMeterInfoById(String id){
        WaterMeterInfoVo waterMeterInfo = waterPurchaseMgtDao.selectWaterMeterInfoById(id);
        setStatusName(waterMeterInfo);
        return waterMeterInfo;
    }

    /**
     * 根据ID查询房间水表信息
     * @param id
     * @return
     */
    public WaterMeterInfoVo collectAndGetWaterMeterInfo(String id){
        WaterMeterInfoVo waterMeterInfo = getWaterMeterInfoById(id);
        //查看集中器状态
        if (ServerHandler.channelMap.containsKey(waterMeterInfo.getConcentratorNum())) {
            waterMeterInfo.setModuleStatusName("在线");
        }else{
            waterMeterInfo.setModuleStatusName("离线");
        }
        //从采集获取数据刷新水表信息
        sendWaterMeterStatus(waterMeterInfo);
        return waterMeterInfo;
    }

    /***
     * 获取请求域中的查询条件
     * @param request
     * @return
     */
    public Map<String, Object> getReqSearchParam(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("areaName", request.getParameter("areaId"));
        map.put("apartmentName", request.getParameter("apartmentId"));
        map.put("concentratorIp", request.getParameter("concentratorId"));
        map.put("concentratorNum", request.getParameter("concentratorNum"));
        map.put("floorName", request.getParameter("floorId"));
        map.put("roomNum", StringUtils.isNotBlank(request.getParameter("roomNum"))?request.getParameter("roomNum"):request.getParameter("roomNumOut"));
        map.put("roomTypeId", request.getParameter("roomTypeId"));
        map.put("status", request.getParameter("status"));
        if(StringUtils.isNotBlank(request.getParameter("level"))){
            if(MyConstant.TREE_LEVEL_2.equals(request.getParameter("level"))){
                map.put("areaId", request.getParameter("parentId"));
            }else if(MyConstant.TREE_LEVEL_3.equals(request.getParameter("level"))){
                map.put("apartmentId", request.getParameter("parentId"));
            }else if(MyConstant.TREE_LEVEL_4.equals(request.getParameter("level"))){
                map.put("buildId", request.getParameter("parentId"));
            }else if(MyConstant.TREE_LEVEL_5.equals(request.getParameter("level"))){
                map.put("floorId", request.getParameter("parentId"));
            }
        }

        return map;
    }

    /**
     * 若当前房间存在未下发成功的水量（包含补水或购水等），则不允许继续购水。
     * @param id
     * @return
     */
    public String checkAllowBuyWater(String id){
        String result = "";
        //检查当前房间水表是否存在未下发成功的水量（包含补水或购水等）存在返回true;
        if(getNotFinishByMeterId(id)){
            result = "房间存在未下发成功的水量，不允许此操作。";
        }

        return result;
    }

    /**
     * 确认购水
     * @param request
     * @return
     */
    public Message buyConfirm(HttpServletRequest request){
        BuyRecordVo buyRecordVo = getBuyRecordVo(request);
        Message message = new Message(MyConstant.MSG_SUCCESS,"购水成功！");
        String errmsg = checkAllowBuyWater(buyRecordVo.getWaterMeterInfoId());
        String concentratorNum=request.getParameter("concentratorNum");
        //检查当前房间水表是否存在未下发成功的水量（包含补水或购水等）;
        if(StringUtils.isNotBlank(errmsg)){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg(errmsg);
        }else if(!ServerHandler.channelMap.containsKey(concentratorNum)){  //检查集中器是否在线
            message.setMsg("集中器不在线！");
        }else{
            //查询最新的水表数据
            WaterMeterInfoVo waterMeterInfo = getWaterMeterInfoById(buyRecordVo.getWaterMeterInfoId());
            allowWater(waterMeterInfo);
            //校验购水是否合法
            errmsg = checkBuyData(buyRecordVo, waterMeterInfo);
            if(StringUtils.isBlank(errmsg)){
                buyRecordVo.setId(UUIDUtils.getUUID());
                buyRecordVo.setSerialNumber(getSerialNumber());
                buyRecordVo.setConcentratorId(waterMeterInfo.getConcentratorId());
                buyRecordVo.setUserId(getLoginUserId());

                //获取指令对象
                List<WaterBean> addWaterBeans = new ArrayList<WaterBean>();
                WaterBean water = getBuyWaterBean(waterMeterInfo, buyRecordVo);
                addWaterBeans.add(water);
                ConcentratorProtocolBean concentrator = instructionsService.addWaterConcent(waterMeterInfo.getWaterMeterNum(),addWaterBeans);
                String instructionJSON = new Gson().toJson(concentrator);

                //向操作指令表插入一条记录，标记为购水且状态未下发
                OperationInstructionVo operationInstruction = new OperationInstructionVo();
                operationInstruction.setId(UUIDUtils.getUUID());
                operationInstruction.setWaterMeterInfoId(buyRecordVo.getWaterMeterInfoId());
                operationInstruction.setType(MyConstant.OPERATION_TYPE_0);
                operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                operationInstruction.setUserId(buyRecordVo.getUserId());
                operationInstruction.setCollectId(waterMeterInfo.getCollectId());
                operationInstruction.setCollectIp(waterMeterInfo.getCollectIp());
                operationInstruction.setApartmentId(waterMeterInfo.getApartmentId());
                operationInstruction.setConcentratorId(waterMeterInfo.getConcentratorId());
                operationInstruction.setWaterMeterNum(waterMeterInfo.getWaterMeterNum());
                operationInstruction.setInstructionDetail(instructionJSON);
                insertOperationInstruction(operationInstruction);

                //向指令明细表插入一条现金购水记录
                InstructionDetailVo instructionDetail = new InstructionDetailVo();
                instructionDetail.setId(UUIDUtils.getUUID());
                instructionDetail.setWaterMeterInfoId(buyRecordVo.getWaterMeterInfoId());
                instructionDetail.setRecordId(buyRecordVo.getId());
                instructionDetail.setType(MyConstant.OPERATION_TYPE_0);
                instructionDetail.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                instructionDetail.setUserId(buyRecordVo.getUserId());
                instructionDetail.setCollectId(waterMeterInfo.getCollectId());
                instructionDetail.setCollectIp(waterMeterInfo.getCollectIp());
                instructionDetail.setApartmentId(waterMeterInfo.getApartmentId());
                instructionDetail.setOperationId(operationInstruction.getId());
                instructionDetail.setWaterMeterNum(operationInstruction.getWaterMeterNum());
                instructionDetail.setInstructionDetail(instructionJSON);
                insertInstructionDetail(instructionDetail);

                //向购水记录表插入一条现金购水记录
                buyRecordVo.setOperationId(operationInstruction.getId());
                insertBuyRecord(buyRecordVo);

                //下发购水命令
                instructionsService.addWater(waterMeterInfo.getConcentratorNum(),concentrator);

                /**************添加操作日志start***************/
                mb_operation_log mol=new mb_operation_log();
                mol.setModuleName("水表管理-购水管理-现金购水");
                mol.setOperaContent(waterMeterInfo.getAreaName()+"-"+waterMeterInfo.getApartmentName()+"-"+
                        waterMeterInfo.getFloorName()+"-"+waterMeterInfo.getRoomNum()+"[现金购水]");
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
     * 批量设置状态名称
     * @param waterMeterInfoList
     */
    private void setStatusNameList(List<WaterMeterInfoVo> waterMeterInfoList){
        for (WaterMeterInfoVo item : waterMeterInfoList){
            setStatusName(item);
        }
    }

    /**
     * 设置状态名称
     * @param info
     */
    private void setStatusName(WaterMeterInfoVo info){
        info.setInitStatusName(MyConstant.INIT_STATUS_MAP.get(info.getInitStatus()));
        //集中器状态
        if(ServerHandler.channelMap.containsKey(info.getConcentratorNum())){
            info.setModuleStatusName("在线");
        }else{
            info.setModuleStatusName("离线");
        }
        info.setValveStatusName(MyConstant.VALVE_STATUS_MAP.get(info.getValveStatus()));
    }

    /**
     * 获取前台传入参数
     * @param request
     * @return
     */
    private BuyRecordVo getBuyRecordVo(HttpServletRequest request){
        BuyRecordVo buyRecordVo = new BuyRecordVo();
        buyRecordVo.setWaterMeterInfoId(request.getParameter("waterMeterInfoId"));
        BigDecimal buyMoney = new BigDecimal(request.getParameter("buyMoney"));
        buyRecordVo.setBuyMoney(buyMoney.doubleValue());
        BigDecimal actualMoney = new BigDecimal(request.getParameter("actualMoney"));
        buyRecordVo.setActualMoney(actualMoney.doubleValue());
        BigDecimal returnMoney = new BigDecimal(request.getParameter("returnMoney"));
        buyRecordVo.setReturnMoney(returnMoney.doubleValue());
        BigDecimal buyWater = new BigDecimal(request.getParameter("buyWater"));
        buyRecordVo.setBuyWater(buyWater.doubleValue());
        buyRecordVo.setPayerName(request.getParameter("payerName"));
        buyRecordVo.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
        buyRecordVo.setType(MyConstant.PAY_TYPE_STATUS_0);
        buyRecordVo.setRedrush(MyConstant.REDRUSH_STATUS_0);

        return buyRecordVo;
    }

    /**
     * 校验购水数据是否合法
     * 购水量不能超过该房间的最大囤积量与剩余水量之差（剩余购水+剩余补水）+ 透支量
     * @param buyRecordVo
     * @return
     */
    private String checkBuyData(BuyRecordVo buyRecordVo, WaterMeterInfoVo waterMeterInfo){
        String result = "";
        //允许购水量 最大囤积量与剩余水量之差（剩余购水+剩余补水）+ 透支量
        if(buyRecordVo.getBuyWater() > waterMeterInfo.getAllowWater()){
            result = "购水量不能超过该房间的最大囤积量+透支量与剩余水量之差。";
        }
        BigDecimal price = new BigDecimal(waterMeterInfo.getPrice());
        if(buyRecordVo.getBuyWater() != new BigDecimal(buyRecordVo.getBuyMoney()).divide(price,2,BigDecimal.ROUND_HALF_UP).doubleValue()){
            result = "现金支付金额和所购水量金额不一致！";
        }

        return result;
    }

    /**
     * 获取登陆用户的UserId
     * @return
     */
    public String getLoginUserId(){
        String userId = "";
        Object obj = SecurityUtils.getSubject().getSession().getAttribute("workerSession");
        if(obj instanceof mb_worker){
            userId = ((mb_worker) obj).getId();
        }
        return  userId;
    }

    /**
     * 获取流水号
     * @return
     */
    public String getSerialNumber(){
        String serialNumber = UUIDUtils.getUUID();
        return serialNumber;
    }

    /**
     * 设置最大购水量
     * @param waterMeterInfo
     */
    public void allowWater(WaterMeterInfoVo waterMeterInfo){
        //允许最大购水量=最大囤积量-剩余水量（剩余购水+剩余补水）+ 透支量
        BigDecimal maxWater = new BigDecimal(waterMeterInfo.getMaxWater());//最大囤积量
        BigDecimal buyWaterTotal = new BigDecimal(waterMeterInfo.getBuyWaterTotal());//剩余购水量
        BigDecimal supplementWater = new BigDecimal(waterMeterInfo.getSupplementWater());//剩余补水量
        BigDecimal overWater = new BigDecimal(waterMeterInfo.getOverWater());//已透支水量
        Double allowWater = maxWater.add(overWater).subtract(buyWaterTotal).subtract(supplementWater).doubleValue();
        //保留两位小数
        DecimalFormat df1 = new DecimalFormat("0.00");
        String str = df1.format(allowWater);
        waterMeterInfo.setAllowWater(Double.parseDouble(str));
    }

    /**
     * 查询未完成的补水购水退水指令
     * @param waterMeterInfoId
     * @return
     */
    public boolean getNotFinishByMeterId(String waterMeterInfoId){
        boolean result = false;
        List<OperationInstructionVo> operationList = waterPurchaseMgtDao.getNotFinishInstruct(waterMeterInfoId);
        if(operationList != null && operationList.size() > 0){
            result = true;
        }
        return result;
    }

    /**
     * 查询未完成的指令
     * @param waterMeterInfoId
     * @return
     */
    public boolean getNotFinishByMeterIdAndType(String waterMeterInfoId, Integer type){
        boolean result = false;
        List<OperationInstructionVo> operationList = waterPurchaseMgtDao.getNotFinishByMeterIdAndType(waterMeterInfoId, type);
        if(operationList != null && operationList.size() > 0){
            result = true;
        }
        return result;
    }

    /**
     * 查询未完成的指令-基于集中器ID
     * @param concentratorId
     * @return
     */
    public boolean getNotFinishByConIdAndType(String concentratorId, Integer type){
        boolean result = false;
        List<OperationInstructionVo> operationList = waterPurchaseMgtDao.getNotFinishByConIdAndType(concentratorId, type);
        if(operationList != null && operationList.size() > 0){
            result = true;
        }
        return result;
    }

    /**
     * 向操作指令表插入一条记录
     * @param operationInstruction
     */
    public void insertOperationInstruction(OperationInstructionVo operationInstruction){
        waterPurchaseMgtDao.insertOperationInstruction(operationInstruction);
    }

    /**
     * 向指令明细表插入一条记录
     * @param instructionDetail
     */
    public void insertInstructionDetail(InstructionDetailVo instructionDetail){
        waterPurchaseMgtDao.insertInstructionDetail(instructionDetail);
    }

    /**
     * 向采集水表信息记录插入一条记录
     * @param collectRecord
     */
    public void insertCollectRecord(CollectRecordVo collectRecord){
        waterPurchaseMgtDao.insertCollectRecord(collectRecord);
    }

    /**
     * 向房间参数下发记录插入一条记录
     * @param roomParamRecordVo
     */
    public void insertRoomParamRecord(RoomParamRecordVo roomParamRecordVo){
        waterPurchaseMgtDao.insertRoomParamRecord(roomParamRecordVo);
    }

    /**
     * 向购水记录表插入一条现金购水记录
     * @param buyRecord
     */
    public void insertBuyRecord(BuyRecordVo buyRecord){
        waterPurchaseMgtDao.insertBuyRecord(buyRecord);
    }

    /**
     * 获取系统运行参数
     */
    public SysoperatParamVo getSysoperatParam(){
        SysoperatParamVo sysoperatParam = waterPurchaseMgtDao.getSysoperatParam();
        return sysoperatParam;
    }

    /**
     * 查询房间类型
     * @return
     */
    public List<RoomTypeVo> getRoomTypeList(){
        List<RoomTypeVo> roomTypeVos = waterPurchaseMgtDao.getRoomTypeList();
        return roomTypeVos;
    }

    /**
     * 从采集获取数据刷新水表信息
     * @param waterMeterInfo
     */
    public void sendWaterMeterStatus(WaterMeterInfoVo waterMeterInfo){

        //当集中器在线时发送指令
        if(ServerHandler.channelMap.containsKey(waterMeterInfo.getConcentratorNum())){
            //检查当前房间水表是否存在未下发完成的采集指令
            if(getNotFinishByMeterIdAndType(waterMeterInfo.getId(), MyConstant.OPERATION_TYPE_8)){
                //重新下发
            }else{
                CollectRecordVo collectRecord = new CollectRecordVo();
                collectRecord.setId(UUIDUtils.getUUID());
                collectRecord.setConcentratorId(waterMeterInfo.getConcentratorId());
                collectRecord.setWaterMeterInfoId(waterMeterInfo.getId());
                collectRecord.setUserId(getLoginUserId());
                collectRecord.setCurrentStatus(MyConstant.CURRENT_STATUS_0);

                //获取指令对象
                List<String> meterNumList = new ArrayList<String>();
                meterNumList.add(waterMeterInfo.getWaterMeterNum());
                ConcentratorProtocolBean concentrator = instructionsService.getWaterMeterStatusConcent(waterMeterInfo.getConcentratorNum(),meterNumList);
                String instructionJSON = new Gson().toJson(concentrator);

                //向操作指令表插入一条记录，标记为购水且状态未下发
                OperationInstructionVo operationInstruction = new OperationInstructionVo();
                operationInstruction.setId(UUIDUtils.getUUID());
                operationInstruction.setWaterMeterInfoId(collectRecord.getWaterMeterInfoId());
                operationInstruction.setType(MyConstant.OPERATION_TYPE_8);
                operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                operationInstruction.setUserId(collectRecord.getUserId());
                operationInstruction.setCollectId(waterMeterInfo.getCollectId());
                operationInstruction.setCollectIp(waterMeterInfo.getCollectIp());
                operationInstruction.setApartmentId(waterMeterInfo.getApartmentId());
                operationInstruction.setConcentratorId(waterMeterInfo.getConcentratorId());
                operationInstruction.setWaterMeterNum(waterMeterInfo.getWaterMeterNum());
                operationInstruction.setInstructionDetail(instructionJSON);
                insertOperationInstruction(operationInstruction);

                //向指令明细表插入一条现金购水记录
                InstructionDetailVo instructionDetail = new InstructionDetailVo();
                instructionDetail.setId(UUIDUtils.getUUID());
                instructionDetail.setWaterMeterInfoId(collectRecord.getWaterMeterInfoId());
                instructionDetail.setRecordId(collectRecord.getId());
                instructionDetail.setType(MyConstant.OPERATION_TYPE_8);
                instructionDetail.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                instructionDetail.setUserId(collectRecord.getUserId());
                instructionDetail.setCollectId(waterMeterInfo.getCollectId());
                instructionDetail.setCollectIp(waterMeterInfo.getCollectIp());
                instructionDetail.setApartmentId(waterMeterInfo.getApartmentId());
                instructionDetail.setOperationId(operationInstruction.getId());
                instructionDetail.setWaterMeterNum(operationInstruction.getWaterMeterNum());
                instructionDetail.setInstructionDetail(instructionJSON);
                insertInstructionDetail(instructionDetail);

                //向采集记录表插入一条记录
                collectRecord.setOperationId(operationInstruction.getId());
                insertCollectRecord(collectRecord);

                //下发命令
                instructionsService.getWaterMeterStatus(waterMeterInfo.getConcentratorNum(),concentrator);
            }
        }
    }

    /**
     * 批量从采集获取数据刷新水表信息
     * @param waterMeterInfoList
     */
    public void sendGroupWaterMeterStatus(List<WaterMeterInfoVo> waterMeterInfoList){
        //检查当前集中器是否存在未下发完成的采集指令
        if(getNotFinishByConIdAndType(waterMeterInfoList.get(0).getConcentratorId(), MyConstant.OPERATION_TYPE_8)){
            //重新下发
        }else{
            //获取指令对象
            List<String> meterNumList = new ArrayList<String>();
            for (WaterMeterInfoVo waterMeterInfo:waterMeterInfoList) {
                meterNumList.add(waterMeterInfo.getWaterMeterNum());
            }

            ConcentratorProtocolBean concentrator = instructionsService.getWaterMeterStatusConcent(waterMeterInfoList.get(0).getConcentratorNum(),meterNumList);
            String instructionJSON = new Gson().toJson(concentrator);
            //向操作指令表插入一条记录，标记为购水且状态未下发
            OperationInstructionVo operationInstruction = new OperationInstructionVo();
            operationInstruction.setId(UUIDUtils.getUUID());
            operationInstruction.setType(MyConstant.OPERATION_TYPE_8);
            operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
            operationInstruction.setUserId(getLoginUserId());
            operationInstruction.setCollectId(waterMeterInfoList.get(0).getCollectId());
            operationInstruction.setCollectIp(waterMeterInfoList.get(0).getCollectIp());
            operationInstruction.setApartmentId(waterMeterInfoList.get(0).getApartmentId());
            operationInstruction.setConcentratorId(waterMeterInfoList.get(0).getConcentratorId());
            operationInstruction.setInstructionDetail(instructionJSON);
            insertOperationInstruction(operationInstruction);

            for (WaterMeterInfoVo waterMeterInfo:waterMeterInfoList) {
                CollectRecordVo collectRecord = new CollectRecordVo();
                collectRecord.setId(UUIDUtils.getUUID());
                collectRecord.setConcentratorId(waterMeterInfo.getConcentratorId());
                collectRecord.setWaterMeterInfoId(waterMeterInfo.getId());
                collectRecord.setUserId(getLoginUserId());
                collectRecord.setCurrentStatus(MyConstant.CURRENT_STATUS_0);

                //向指令明细表插入一条现金购水记录
                InstructionDetailVo instructionDetail = new InstructionDetailVo();
                instructionDetail.setId(UUIDUtils.getUUID());
                instructionDetail.setWaterMeterInfoId(collectRecord.getWaterMeterInfoId());
                instructionDetail.setRecordId(collectRecord.getId());
                instructionDetail.setType(MyConstant.OPERATION_TYPE_8);
                instructionDetail.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                instructionDetail.setUserId(collectRecord.getUserId());
                instructionDetail.setCollectId(waterMeterInfo.getCollectId());
                instructionDetail.setCollectIp(waterMeterInfo.getCollectIp());
                instructionDetail.setApartmentId(waterMeterInfo.getApartmentId());
                instructionDetail.setOperationId(operationInstruction.getId());
                instructionDetail.setWaterMeterNum(waterMeterInfo.getWaterMeterNum());
                instructionDetail.setInstructionDetail(instructionJSON);
                insertInstructionDetail(instructionDetail);

                //向采集记录表插入一条记录
                collectRecord.setOperationId(operationInstruction.getId());
                insertCollectRecord(collectRecord);
            }
            //下发命令
            instructionsService.getWaterMeterStatus(waterMeterInfoList.get(0).getConcentratorNum(),concentrator);
        }
    }

    public void setRoomTypeName(WaterMeterInfoVo waterMeterInfo){
        if(waterMeterInfo.getRoomTypeId() != null){
            waterMeterInfo.setRoomTypeName(waterPurchaseMgtDao.getRoomTypeName(waterMeterInfo.getRoomTypeId()));
        }
    }

    /**
     * 获取购水bean
     * @param waterMeterInfo
     * @param buyRecordVo
     * @return
     */
    private WaterBean getBuyWaterBean(WaterMeterInfoVo waterMeterInfo, BuyRecordVo buyRecordVo){
        WaterBean waterBean = new WaterBean();
        waterBean.setWaterMeterNum(Integer.parseInt(waterMeterInfo.getWaterMeterNum()));
        waterBean.setBuyWater((new Double(buyRecordVo.getBuyWater()*10)).intValue());
        waterBean.setAddWater(0);
        waterBean.setAddNum(waterMeterInfo.getAddNum()+1);
        return waterBean;
    }

    /**
     * 下载模版
     * @param fileName
     * @param request
     * @param response
     */
    public void download(String fileName, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName="+ new String(fileName.getBytes(), "ISO-8859-1"));
            String path = getClass().getResource("/template/" + fileName).getPath();
            InputStream inputStream = new FileInputStream(new File(path));
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.close();
            inputStream.close();
        } catch (Exception e) {
            logger.error("模版下载报错！", e);
        }
    }

    /**
     * 重新下发命令
     * @param id
     * @return
     */
    public Message again(String id){
        Message message = new Message(MyConstant.MSG_SUCCESS,"重新下发命令成功！");
        OperationInstructionVo opera = concentratorMgtService.findInstructionsById(id);
        //判断集中器是否在线
        if(!instructionsService.existChannel(opera.getConcentratorNum())){
            message.setMsg("集中器不在线！");
            message.setStatus(MyConstant.MSG_FAIL);
        }else if(!MyConstant.CURRENT_STATUS_0.equals(opera.getCurrentStatus())){
            message.setMsg("此命令已下发！");
            message.setStatus(MyConstant.MSG_FAIL);
        }else{
            //下发指令
            if(StringUtils.isNotBlank(opera.getInstructionDetail())){
                ConcentratorProtocolBean concentrator = new Gson().fromJson(opera.getInstructionDetail() , ConcentratorProtocolBean.class);
                instructionsService.sendData(opera.getConcentratorNum(),concentrator);
            }
        }

        return message;
    }

    /**
     * 集中器在线情况
     * @return
     */
    public List<OnlineSituationVo> onlineSituation(){
        HashSet<String> concentratorNums = waterPurchaseMgtDao.getAllConcentratorVo();
        OnlineSituationVo exist = new OnlineSituationVo(MyConstant.MSG_EXIST);
        OnlineSituationVo notexist = new OnlineSituationVo(MyConstant.MSG_NOT_EXIST);
        exist.setNum(ServerHandler.channelMap.size());
        notexist.setNum(concentratorNums.size() - exist.getNum());
        List<OnlineSituationVo> result = new ArrayList<OnlineSituationVo>();
        result.add(exist);
        result.add(notexist);
        return result;
    }

    /**
     * 用来批量生成测试数据包括集中器，栋，楼层，房间，水表房间，水表
     */
    public void test(){
        Long jzqbase = 1020304050607080l;
        Long sbbase = 12345679l;
        Integer lcbase = 3;
        for (int i=0; i<3000; i++) {
            //集中器
            String jzqID = UUIDUtils.getUUID();
            String jzqNum = (jzqbase++)+"";
            waterPurchaseMgtDao.testaddConcentrator(jzqID, jzqNum);
            //楼层
            String lcID = UUIDUtils.getUUID();
            String lcNum = (lcbase++)+"";
            String lcname = lcbase+"层";
            waterPurchaseMgtDao.testInsertLayer(lcID, lcNum, lcname);
            for (int j=0 ; j<20; j++) {

                //水表
                String sbID = UUIDUtils.getUUID();
                String sbNum = (sbbase++)+"";
                waterPurchaseMgtDao.testSaveWaterMeter(sbID, sbNum);
                //房间
                String fjID = UUIDUtils.getUUID();
                String fjname = j>=10?lcNum+"0"+j+"室":lcNum+j+"室";
                waterPurchaseMgtDao.testInsertRoom(fjID, j+1+"",fjname,lcID);
                //房间水表
                String fjsbID = UUIDUtils.getUUID();
                waterPurchaseMgtDao.testSaveWatermeterRoomInfoMgt(fjsbID, lcID,fjID,j+1+"",jzqID,sbID);
            }
        }


    }

}
