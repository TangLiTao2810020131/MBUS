package com.ets.bus.waterMeterMgt.instructionOperation.service;

import com.alibaba.fastjson.JSON;
import com.ets.bus.reportQuery.entity.report.RedRushVo;
import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.server.ConstantValue;
import com.ets.bus.socket.server.ConvertCode;
import com.ets.bus.waterMeterMgt.instructionOperation.dao.InstructionOperationDao;
import com.ets.bus.waterMeterMgt.instructionOperation.entity.InstructionData;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.OperationInstructionVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.RoomParamRecordVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.common.MyConstant;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 宋晨
 * @create 2019/4/16
 * 收取集中器消息
 */
@Service
@Transactional
@SuppressWarnings("all")
public class OperationService {

    @Resource
    private InstructionOperationDao instructionOperationDao;

    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;

    /**
     * 收取的集中器消息
     *
     * @param data
     */
    public void
    operationMsg(ConcentratorProtocolBean data,String deviceId) {

        switch (data.getFunctionCode()) {
            //心跳 集中器主动上传，采集收到无需应答。
            case ConstantValue.FUNCODE_LIVE:
                break;

            //获取集中器时间
            case ConstantValue.FUNCODE_CON_DATE:
                funcodeConDate(data);
                break;

            //校时
            case ConstantValue.FUNCODE_COM_TIME:
                funcodeComTime(data);
                break;

            //修改集中器服务地址
            case ConstantValue.FUNCODE_CHA_ADDRESS:
                funcodeChaAddress(data);
                break;

            //获取集中器参数
            case ConstantValue.FUNCODE_CON_PARAM:
                funcodeConParam(data);
                break;

            //设置集中器参数
            case ConstantValue.FUNCODE_SET_PARAM:
                funcodeSetParam(data);
                break;

            //获取水表状态（包括剩余水量）
            case ConstantValue.FUNCODE_WATER_METER:
                funcodeWaterMeter(data);
                break;

            //水表清零
            case ConstantValue.FUNCODE_WATER_RESET:
                funcodeWaterReset(data);
                break;

            //重启集中器
            case ConstantValue.FUNCODE_WATER_RESTART:
                funcodeWaterRestart(data);
                break;

            //购水、补水
            case ConstantValue.FUNCODE_ADD_WATER:
                funcodeAddWater(data,deviceId);
                break;

            //退水
            case ConstantValue.FUNCODE_RETURN_WATER:
                funcodeReturnWater(data);
                break;

            //更新水表信息
            case ConstantValue.FUNCODE_UPDATE_WATER:
                funcodeUpdateWater(data);
                break;

            //获取水表参数
            case ConstantValue.FUNCODE_GET_WATER_PARAM:
                funcodeGetWaterParam(data);
                break;

            //开关阀
            case ConstantValue.FUNCODE_OPEN:
                funcodeOpen(data);
                break;

            //终端报警
            case ConstantValue.FUNCODE_WARN:
                funcodeWarn(data);
                break;

            default:
                break;
        }
    }

    /**
     * 获取集中器时间返回参数
     *
     * @param data
     */
    private void funcodeConDate(ConcentratorProtocolBean data) {

    }

    /**
     * 校时返回参数
     *
     * @param data
     */
    private void funcodeComTime(ConcentratorProtocolBean data) {
        //获取集中器编号
        String deviceId = ConvertCode.receiveHexToString(data.getDeviceId());
        String result = new Gson().toJson(data);
        //获取数据域
//      InstructionData instructionData = assembleComTime(deviceId,data.getContent());
        InstructionData instructionData = getBean();
        //获取操作指令表记录
        OperationInstructionVo opera = getOperationInstructionRecord(deviceId, null, MyConstant.OPERATION_TYPE_9);
        if (opera != null) {
            //更新指令记录
            updateInstructionRecord(opera.getId(), result, instructionData.getCurrentStatus());
        }
    }

    /**
     * 修改集中器服务地址返回参数
     *
     * @param data
     */
    private void funcodeChaAddress(ConcentratorProtocolBean data) {

    }

    /**
     * 获取集中器参数返回参数
     *
     * @param data
     */
    private void funcodeConParam(ConcentratorProtocolBean data) {

    }

    /**
     * 设置集中器参数返回参数
     *
     * @param data
     */
    private void funcodeSetParam(ConcentratorProtocolBean data) {
        //获取集中器编号
        String deviceId = ConvertCode.receiveHexToString(data.getDeviceId());
        String result = new Gson().toJson(data);
        //获取数据域
//      InstructionData instructionData = assembleComTime(deviceId,data.getContent());
        InstructionData instructionData = getBean();
        //获取操作指令表记录
        OperationInstructionVo opera = getOperationInstructionRecord(deviceId, null, MyConstant.OPERATION_TYPE_10);
        if (opera == null) {
            opera = getOperationInstructionRecord(deviceId, null, MyConstant.OPERATION_TYPE_11);
        }
        if (opera != null) {
            //更新指令记录
            updateInstructionRecord(opera.getId(), result, instructionData.getCurrentStatus());
        }
    }

    /**
     * 获取水表状态返回参数
     *
     * @param data
     */
    private void funcodeWaterMeter(ConcentratorProtocolBean data) {
        Long now = System.currentTimeMillis();
        //获取集中器编号
        String deviceId = ConvertCode.receiveHexToString(data.getDeviceId());
        String result = new Gson().toJson(data);
        //获取数据域
        List<InstructionData> instructionDatas = assembleAddWater(deviceId, data);
        //是否是集中器采集数据
        OperationInstructionVo opera = getOperaInstRecordByDevice(deviceId, MyConstant.OPERATION_TYPE_8);
        if (opera != null) {
            //更新操作指令表
            updateOperationInstruction(opera.getId(), result, MyConstant.CURRENT_STATUS_1);
        }
        for (InstructionData instructionData : instructionDatas) {
            if (opera == null) {
                //获取操作指令表记录-获取水表状态
                opera = getOperationInstructionRecord(deviceId, instructionData.getWaterMeterNum() + "", MyConstant.OPERATION_TYPE_8);
                if (opera != null) {
                    //更新操作指令表
                    updateOperationInstruction(opera.getId(), result, instructionData.getCurrentStatus());
                }
            }

            if (opera != null) {
                instructionData.setOperationId(opera.getId());
                if (opera.getWaterMeterInfoId() == null) {
                    instructionData.setWaterMeterInfoId(getWaterMeterInfoId(instructionData.getWaterMeterNum()));
                } else {
                    instructionData.setWaterMeterInfoId(opera.getWaterMeterInfoId());
                }

                //更新指令明细表
                updateInstructionDetail(opera.getId(), instructionData.getWaterMeterNum(), result, instructionData.getCurrentStatus());
                //是否成功
                if (ConstantValue.RETURN_RNT_SUCCESS == instructionData.getRtn()) {
                    //更新水表房间信息
                    updateWaterMeteInfo(instructionData);
                }
                //更新采集记录信息
                updateCollectRecord(instructionData);
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println("耗时》》》》》》》》》》》》》》》》》》》》：" + (end - now) / 1000 + "秒。开始时间：" + now + "结束时间：" + end);
    }

    /**
     * 水表清零返回参数
     *
     * @param data
     */
    private void funcodeWaterReset(ConcentratorProtocolBean data) {
        //获取集中器编号
        String deviceId = ConvertCode.receiveHexToString(data.getDeviceId());
        String result = new Gson().toJson(data);
        //获取数据域
        List<InstructionData> instructionDatas = assembleResetWater(data);

        for (InstructionData instructionData : instructionDatas) {
            //获取操作指令表记录
            OperationInstructionVo opera = getOperationInstructionRecord(deviceId, instructionData.getWaterMeterNum(), MyConstant.OPERATION_TYPE_6);
            if (opera != null) {
                instructionData.setOperationId(opera.getId());
                instructionData.setWaterMeterInfoId(opera.getWaterMeterInfoId());
                //更新指令记录
                updateInstructionRecord(opera.getId(), result, instructionData.getCurrentStatus());
                //是否成功
                if (ConstantValue.RETURN_RNT_SUCCESS == instructionData.getRtn()) {
                    //水表房间信息清零-更新房间水表清零状态
                    waterMeteInfoClear(instructionData);
                }
                //更新记录信息
                updateClearRecord(instructionData);
            }
        }
    }

    /**
     * 重启集中器返回参数
     *
     * @param data
     */
    private void funcodeWaterRestart(ConcentratorProtocolBean data) {
        //获取集中器编号
        String deviceId = ConvertCode.receiveHexToString(data.getDeviceId());
        String result = new Gson().toJson(data);
        //获取数据域
//      InstructionData instructionData = assembleComTime(deviceId,data.getContent());
        InstructionData instructionData = getBean();
        //获取操作指令表记录
        OperationInstructionVo opera = getOperationInstructionRecord(deviceId, null, MyConstant.OPERATION_TYPE_13);
        if (opera != null) {
            //更新指令记录
            updateInstructionRecord(opera.getId(), result, instructionData.getCurrentStatus());
        }
    }

    /**
     * 购水、补水返回参数
     *
     * @param data
     */
    private void funcodeAddWater(ConcentratorProtocolBean data,String cconcentratorNum) {
        String result = new Gson().toJson(data);
        //获取数据域
        List<InstructionData> instructionDatas = assembleAddWater(cconcentratorNum,data);

        for (InstructionData instructionData : instructionDatas) {
            //获取操作指令表记录
            int type = 0;
            //购水
            OperationInstructionVo opera = getOperationInstructionRecord(cconcentratorNum,ConvertCode.bytes2HexString(data.getDeviceId()), MyConstant.OPERATION_TYPE_0);//集中器编号 水表编号 购水
            //补水
            if(opera == null){
                opera = getOperationInstructionRecord(cconcentratorNum,ConvertCode.bytes2HexString(data.getDeviceId()),MyConstant.OPERATION_TYPE_2);
                type = 1;
            }
            //冲红
            if(opera == null){
                opera = getOperationInstructionRecord(cconcentratorNum,ConvertCode.bytes2HexString(data.getDeviceId()),MyConstant.OPERATION_TYPE_7);
                type = 2;
            }
            if(opera != null){
                instructionData.setOperationId(opera.getId());
                instructionData.setWaterMeterInfoId(opera.getWaterMeterInfoId());
                //更新指令记录
                updateInstructionRecord(opera.getId(),result,instructionData.getCurrentStatus());
                //是否成功
                if(true){
                    //更新水表房间信息
                    updateWaterMeteInfo(instructionData);
                }
                if(type == 0){
                    //更新购水记录信息
                    updateBuyWaterRecord(instructionData);
                }else if(type == 1){
                    //更新补水记录信息
                    updateAddWaterRecord(instructionData);
                }else{
                    //冲红操作
                    updateRedRush(instructionData);
                }
            }
        }
    }

    /**
     * 退水返回参数
     *
     * @param data
     */
    private void funcodeReturnWater(ConcentratorProtocolBean data) {
        //获取集中器编号
        String deviceId = ConvertCode.receiveHexToString(data.getDeviceId());
        String result = new Gson().toJson(data);
        //获取数据域
        List<InstructionData> instructionDatas = assembleQuitWater(data);

        for (InstructionData instructionData : instructionDatas) {
            int type = 0;
            //获取操作指令表记录
            OperationInstructionVo opera = getOperationInstructionRecord(deviceId, instructionData.getWaterMeterNum(), MyConstant.OPERATION_TYPE_1);
            //冲红
            if (opera == null) {
                opera = getOperationInstructionRecord(deviceId, instructionData.getWaterMeterNum(), MyConstant.OPERATION_TYPE_7);
                type = 1;
            }

            if (opera != null) {
                WaterMeterInfoVo waterMeterInfo = waterPurchaseMgtService.getWaterMeterInfoById(opera.getWaterMeterInfoId());
                instructionData.setOperationId(opera.getId());
                instructionData.setWaterMeterInfoId(opera.getWaterMeterInfoId());
                if(null !=waterMeterInfo){
                    instructionData.setReturnWater(waterMeterInfo.getReturnWater());
                }
                //更新指令记录
                updateInstructionRecord(opera.getId(), result, instructionData.getCurrentStatus());
                //更新退水量
                updataReturnWater(instructionData,opera);
                //是否成功
                if (ConstantValue.RETURN_RNT_SUCCESS == instructionData.getRtn()) {
                    //更新水表房间信息
                    updateWaterMeteInfo(instructionData);
                }
                if (type == 0) {
                    //更新退水信息
                    updateReturnMeteInfo(instructionData);
                } else {
                    //冲红操作
                    updateRedRush(instructionData);
                }

            }
        }
    }

    private void updataReturnWater(InstructionData instructionData,OperationInstructionVo opera){
        //{"head":126,"version":18,"deviceId":[68,68,68,68],"type":38,"functionCode":33,"content":[0,16,0,0,0,24],"end":3338}
        HashMap<String,Object> map = (HashMap<String,Object>) JSON.parseObject(opera.getInstructionDetail(), Map.class);
        List<Byte> content = JSON.parseArray(map.get("content").toString(),Byte.class);
        byte[] data = new byte[4];
        data[0]=content.get(0);
        data[1] = content.get(1);
        System.out.println(JSON.toJSON(data));
        int s = ConvertCode.byteToshort(data);
        if(null != instructionData.getReturnWater()){
            instructionData.setReturnWater(new Double(s)/10+instructionData.getReturnWater());
        }else {
            instructionData.setReturnWater(new Double(s)/10);
        }

    }

    /**
     * 更新水表信息返回参数
     *
     * @param data
     */
    private void funcodeUpdateWater(ConcentratorProtocolBean data) {
        //获取集中器编号
        String deviceId = ConvertCode.receiveHexToString(data.getDeviceId());
        String result = new Gson().toJson(data);
        //获取数据域
        List<InstructionData> instructionDatas = assembleUpdateWater(data);
        for (InstructionData instructionData : instructionDatas) {
            //获取操作指令表记录
            OperationInstructionVo opera = getOperationInstructionRecord(deviceId, instructionData.getWaterMeterNum(), MyConstant.OPERATION_TYPE_12);
            if (opera != null) {
                instructionData.setOperationId(opera.getId());
                instructionData.setWaterMeterInfoId(opera.getWaterMeterInfoId());
                //获取下发参数指令记录
                RoomParamRecordVo roomParamRecord = getRoomParamRecordVo(instructionData);
                //更新指令记录
                updateInstructionRecord(opera.getId(), result, instructionData.getCurrentStatus());
                //是否成功
                if (ConstantValue.RETURN_RNT_SUCCESS == instructionData.getRtn()) {
                    instructionData.setOverWater(roomParamRecord.getOverWater());
                    instructionData.setValveWater(roomParamRecord.getValveWater());
                    instructionData.setHoardWater(roomParamRecord.getHoardWater());
                    instructionData.setWarnWater(roomParamRecord.getWarnWater());
                    instructionData.setBuyMoney(roomParamRecord.getBuyMoney());
                    instructionData.setReturnMoney(roomParamRecord.getReturnMoney());
                    instructionData.setAddCycle(roomParamRecord.getAddCycle());
                    //更新水表房间参数信息
                    updateWaterMeteParam(instructionData);
                }
                //更新房间发参数记录
                updateRoomParamRecord(instructionData);
            }
        }
    }

    /**
     * 获取水表参数返回参数
     *
     * @param data
     */
    private void funcodeGetWaterParam(ConcentratorProtocolBean data) {

    }

    /**
     * 开关阀返回参数
     *
     * @param data
     */
    private void funcodeOpen(ConcentratorProtocolBean data) {
        //获取集中器编号
        String deviceId = ConvertCode.receiveHexToString(data.getDeviceId());
        String result = new Gson().toJson(data);
        //获取数据域
//      List<InstructionData> instructionDatas = assembleUpdateWater(data.getContent());
        List<InstructionData> instructionDatas = getBeanList();
        for (InstructionData instructionData : instructionDatas) {
            //获取操作指令表记录
            OperationInstructionVo opera = getOperationInstructionRecord(deviceId, instructionData.getWaterMeterNum() + "", MyConstant.OPERATION_TYPE_14);
            if (opera == null) {
                opera = getOperationInstructionRecord(deviceId, instructionData.getWaterMeterNum() + "", MyConstant.OPERATION_TYPE_15);
            }
            if (opera == null) {
                opera = getOperationInstructionRecord(deviceId, instructionData.getWaterMeterNum() + "", MyConstant.OPERATION_TYPE_16);
            }
            if (opera == null) {
                opera = getOperationInstructionRecord(deviceId, instructionData.getWaterMeterNum() + "", MyConstant.OPERATION_TYPE_17);
            }
            if (opera != null) {
                instructionData.setOperationId(opera.getId());
                instructionData.setWaterMeterInfoId(opera.getWaterMeterInfoId());
                updateInstructionRecord(opera.getId(), result, instructionData.getCurrentStatus());
                //是否成功
                if (ConstantValue.RETURN_RNT_SUCCESS == instructionData.getRtn()) {
                    //更新水表房间阀门状态
                    updateWaterMeteValveStatus(instructionData);
                }
            }
        }
    }

    /**
     * 终端报警返回参数
     *
     * @param data
     */
    private void funcodeWarn(ConcentratorProtocolBean data) {

    }

    /**
     * 组装退水的指令
     *
     * @param data
     * @return
     */

    private List<InstructionData> assembleQuitWater(ConcentratorProtocolBean data) {
        byte[] content = data.getContent();
        List<InstructionData> list = new ArrayList<InstructionData>();
        InstructionData instructionData = new InstructionData();
        //拼装水表的编号将bcd转成string
        instructionData.setWaterMeterNum(ConvertCode.bcd2Str(data.getDeviceId()));

        //返回状态RTN 00表示成功，11表示计数错误，12退水量超出，31下发超时
        instructionData.setRtn(content[0]);
        switch (content[0]) {
            case ConstantValue.RETURN_RNT_SUCCESS:
                instructionData.setCurrentStatus(MyConstant.CURRENT_STATUS_1);
                break;

            case ConstantValue.RETURN_RNT_NUMERROR:
                instructionData.setCurrentStatus(MyConstant.CURRENT_STATUS_3);
                break;

            case ConstantValue.RETURN_RNT_OVER:
                instructionData.setCurrentStatus(MyConstant.CURRENT_STATUS_6);
                break;

            case ConstantValue.RETURN_RNT_OVERTIME:
                instructionData.setCurrentStatus(MyConstant.CURRENT_STATUS_5);
                break;
        }
        //剩余购水量
        byte[] buyarr = new byte[4];
        buyarr[0] = 0;
        buyarr[1] = 0;
        buyarr[2] = content[1];
        buyarr[3] = content[2];
        Integer buy = ConvertCode.byteArrayToInt(buyarr);
        instructionData.setBuyWaterTotal(((double) buy / 10));
        //剩余补水量
        byte[] addarr = new byte[4];
        addarr[0] = 0;
        addarr[1] = 0;
        addarr[2] = content[3];
        addarr[3] = content[4];
        Integer add = ConvertCode.byteArrayToInt(addarr);
        instructionData.setSupplementWater(((double) add / 10));
        //下发计数
        byte[] addnumarr = new byte[4];
        addnumarr[0] = 0;
        addnumarr[1] = 0;
        addnumarr[2] = content[5];
        addnumarr[3] = content[6];
        Integer addnum = ConvertCode.byteArrayToInt(addnumarr);
        instructionData.setAddNum(addnum);
        //总用水量
        byte[] userarr = new byte[4];
        userarr[0] = 0;
        userarr[1] = content[7];
        userarr[2] = content[8];
        userarr[3] = content[9];
        Integer user = ConvertCode.byteArrayToInt(userarr);
        instructionData.setUserWater(((double) user / 10));

        //阀门状态
        instructionData.setValveStatus(content[10]);

        //透水量
        byte[] overarr = new byte[4];
        overarr[0] = 0;
        overarr[1] = 0;
        overarr[2] = content[11];
        overarr[3] = content[12];
        Integer over = ConvertCode.byteArrayToInt(overarr);
        instructionData.setOverWater(((double) over / 10));

        //电池状态
        instructionData.setPowerStatus(content[13]);

        //磁攻击次数
        instructionData.setAttackNum(content[14]);

        list.add(instructionData);

        return list;
    }

    /**
     * 组装购水，补水，获取水表信息返回数据
     *
     * @return
     */
    private List<InstructionData> assembleAddWater(String deviceId, ConcentratorProtocolBean data) {
        byte[] content = data.getContent();
        List<InstructionData> list = new ArrayList<InstructionData>();
        InstructionData instructionData = new InstructionData();
        instructionData.setTime(new Date());
        instructionData.setWaterMeterNum(ConvertCode.bcd2Str(data.getDeviceId()));

        //返回状态RTN 00表示成功，11表示计数错误，12购水量超出，31下发超时
        instructionData.setRtn(content[0]);
        switch (content[0]) {
            case ConstantValue.RETURN_RNT_SUCCESS:
                instructionData.setCurrentStatus(MyConstant.CURRENT_STATUS_1);
                break;

            case ConstantValue.RETURN_RNT_NUMERROR:
                instructionData.setCurrentStatus(MyConstant.CURRENT_STATUS_3);
                break;

            case ConstantValue.RETURN_RNT_OVER:
                instructionData.setCurrentStatus(MyConstant.CURRENT_STATUS_4);
                break;

            case ConstantValue.RETURN_RNT_OVERTIME:
                instructionData.setCurrentStatus(MyConstant.CURRENT_STATUS_5);
                break;
        }
        //剩余购水量
        byte[] buyarr = new byte[4];
        buyarr[0] = 0;
        buyarr[1] = 0;
        buyarr[2] = content[1];
        buyarr[3] = content[2];
        Integer buy = ConvertCode.byteArrayToInt(buyarr);
        instructionData.setBuyWaterTotal(((double) buy / 10));
        //剩余补水量
        byte[] addarr = new byte[4];
        addarr[0] = 0;
        addarr[1] = 0;
        addarr[2] = content[3];
        addarr[3] = content[4];
        Integer add = ConvertCode.byteArrayToInt(addarr);
        instructionData.setSupplementWater(((double) add / 10));
        //下发计数
        byte[] addnumarr = new byte[4];
        addnumarr[0] = 0;
        addnumarr[1] = 0;
        addnumarr[2] = content[5];
        addnumarr[3] = content[6];
        Integer addnum = ConvertCode.byteArrayToInt(addnumarr);
        instructionData.setAddNum(addnum);
        //总用水量
        byte[] userarr = new byte[4];
        userarr[0] = 0;
        userarr[1] = content[7];
        userarr[2] = content[8];
        userarr[3] = content[9];
        Integer user = ConvertCode.byteArrayToInt(userarr);
        instructionData.setUserWater(((double) user / 10));

        //阀门状态
        instructionData.setValveStatus(content[10]);

        //透水量
        byte[] overarr = new byte[4];
        overarr[0] = 0;
        overarr[1] = 0;
        overarr[2] = content[11];
        overarr[3] = content[12];
        Integer over = ConvertCode.byteArrayToInt(overarr);
        instructionData.setOverWater(((double) over / 10));

        //电池状态
        instructionData.setPowerStatus(content[13]);

        //磁攻击次数
        instructionData.setAttackNum(content[14]);

        list.add(instructionData);

        return list;
    }


    /**
     * 组装返回数据
     *
     * @return
     */
    private InstructionData assembleComTime(String deviceId, byte[] content) {
        InstructionData instructionData = new InstructionData();
        //00表示成功，其他表示失败具体见错误代码
        instructionData.setSuccessStatus(ConvertCode.byteArrayToInt(content));
        return instructionData;
    }

    /**
     * 组装返回数据
     *
     * @return
     */
    private List<InstructionData> assembleUpdateWater(ConcentratorProtocolBean data) {
        byte[] content = data.getContent();
        List<InstructionData> list = new ArrayList<InstructionData>();
        InstructionData instructionData = new InstructionData();
        String waterMeterNum = ConvertCode.bcd2Str(data.getDeviceId());
        instructionData.setWaterMeterNum(waterMeterNum);

        //返回状态RTN 00表示成功，11表示计数错误，12购水量超出，31下发超时
        instructionData.setRtn(content[0]);
        switch (content[0]) {
            case ConstantValue.RETURN_RNT_SUCCESS:
                instructionData.setCurrentStatus(MyConstant.CURRENT_STATUS_1);
                break;

            case ConstantValue.RETURN_RNT_NUMERROR:
                instructionData.setCurrentStatus(MyConstant.CURRENT_STATUS_3);
                break;

            case ConstantValue.RETURN_RNT_OVER:
                instructionData.setCurrentStatus(MyConstant.CURRENT_STATUS_4);
                break;

            case ConstantValue.RETURN_RNT_OVERTIME:
                instructionData.setCurrentStatus(MyConstant.CURRENT_STATUS_5);
                break;
        }
        list.add(instructionData);
        //}
        return list;
    }

    /**
     * 组装水表清零
     *
     * @param data
     * @return
     */
    private List<InstructionData> assembleResetWater(ConcentratorProtocolBean data) {
        byte[] content = data.getContent();
        List<InstructionData> list = new ArrayList<InstructionData>();
        InstructionData instructionData = new InstructionData();
        String waterMeterNum = ConvertCode.bcd2Str(data.getDeviceId());
        instructionData.setWaterMeterNum(waterMeterNum);

        //返回状态RTN 00表示成功，01表示返回数据异常错误
        instructionData.setRtn(content[0]);
        switch (content[0]) {
            case ConstantValue.RETURN_RNT_SUCCESS:
                instructionData.setCurrentStatus(MyConstant.CURRENT_STATUS_1);
                break;

            case ConstantValue.RETURN_RNT_ERROR:
                instructionData.setCurrentStatus(MyConstant.CURRENT_STATUS_2);
                break;
        }
        list.add(instructionData);
        //}
        return list;
    }

    /**
     * 更新补水记录信息
     *
     * @param instructionData
     */
    private void updateAddWaterRecord(InstructionData instructionData) {
        instructionOperationDao.updateAddWaterRecord(instructionData);
    }

    /**
     * 更新冲红记录信息
     *
     * @param instructionData
     */
    private void updateRedrushRecord(InstructionData instructionData) {
        instructionOperationDao.updateRedrushRecord(instructionData);
    }

    /**
     * 更新补水记录冲红状态
     *
     * @param recordId
     */
    private void updateRedAddRecord(String recordId) {
        instructionOperationDao.updateRedAddRecord(recordId);
    }

    /**
     * 更新退水记录冲红状态
     *
     * @param recordId
     */
    private void updateRedReturnRecord(String recordId) {
        instructionOperationDao.updateRedReturnRecord(recordId);
    }

    /**
     * 更新购水记录信息
     *
     * @param instructionData
     */
    private void updateBuyWaterRecord(InstructionData instructionData) {
        instructionOperationDao.updateBuyWaterRecord(instructionData);
    }

    /**
     * 更新水表房间信息
     *
     * @param instructionData
     */
    private void updateWaterMeteInfo(InstructionData instructionData) {
        instructionOperationDao.updateWaterMeteInfo(instructionData);
    }

    /**
     * 更新水表房间参数信息
     *
     * @param instructionData
     */
    private void updateWaterMeteParam(InstructionData instructionData) {
        instructionOperationDao.updateWaterMeteParam(instructionData);
    }

    /**
     * 更新采集记录表
     *
     * @param instructionData
     */
    private void updateCollectRecord(InstructionData instructionData) {
        instructionOperationDao.updateCollectRecord(instructionData);
    }

    /**
     * 更新操作指令表
     *
     * @param operaId
     * @param result
     * @param successStatus
     */
    private void updateOperationInstruction(String operaId, String result, Integer successStatus) {
        instructionOperationDao.updateOperationInstruction(operaId, result, successStatus);
    }

    /**
     * 更新指令明细表
     *
     * @param operaId
     * @param result
     * @param successStatus
     */
    private void updateInstructionDetail(String operaId, String waterMeterNum, String result, Integer successStatus) {
        instructionOperationDao.updateInstructionDetail(operaId, waterMeterNum, result, successStatus);
    }

    /**
     * 更新清零记录表
     *
     * @param instructionData
     */
    private void updateClearRecord(InstructionData instructionData) {
        instructionOperationDao.updateClearRecord(instructionData);
    }

    /**
     * 更新水表房间阀门状态
     *
     * @param instructionData
     */
    private void updateWaterMeteValveStatus(InstructionData instructionData) {
        instructionOperationDao.updateWaterMeteValveStatus(instructionData);
    }

    /**
     * 获取操作指令表记录
     *
     * @param deviceId
     * @param waterMeterNum
     * @param operationType
     * @return
     */
    private OperationInstructionVo getOperationInstructionRecord(String deviceId, String waterMeterNum, Integer operationType) {
        OperationInstructionVo operationInstruction = instructionOperationDao.getOperationInstructionRecord(waterMeterNum, operationType);
        return operationInstruction;
    }

    /**
     * 获取操作指令表记录
     *
     * @param deviceId
     * @param operationType
     * @return
     */
    private OperationInstructionVo getOperaInstRecordByDevice(String deviceId, Integer operationType) {
        OperationInstructionVo operationInstruction = instructionOperationDao.getOperaInstRecordByDevice(deviceId, operationType);
        return operationInstruction;
    }

    /**
     * 更新指令记录
     *
     * @param operaId
     * @param result
     */
    private void updateInstructionRecord(String operaId, String result, Integer successStatus) {
        //更新操作指令表
        updateOperationInstruction(operaId, result, successStatus);
        //更新指令明细表
        updateInstructionDetail(operaId, null, result, successStatus);
    }

    /**
     * 清零房间
     *
     * @param instructionData
     */
    private void waterMeteInfoClear(InstructionData instructionData) {
        instructionOperationDao.waterMeteInfoClear(instructionData);
    }

    /**
     * 更新退水记录
     *
     * @param instructionData
     */
    private void updateReturnMeteInfo(InstructionData instructionData) {
        instructionOperationDao.updateReturnMeteInfo(instructionData);
    }

    /**
     * 冲红数据会写
     */
    private void updateRedRush(InstructionData instructionData) {
        //获取冲红记录
        RedRushVo redRushVo = instructionOperationDao.getRedrushByOperationId(instructionData.getOperationId());
        //更新冲红记录信息
        updateRedrushRecord(instructionData);
        //更新对应记录冲红状态
        if (MyConstant.REDRUSH_TYPE_0.equals(redRushVo.getType())) {
            updateRedAddRecord(redRushVo.getRecordId());
        } else {
            updateRedReturnRecord(redRushVo.getRecordId());
        }
    }

    private List<InstructionData> getBeanList() {
        List<InstructionData> instructionDatas = new ArrayList<InstructionData>();
        instructionDatas.add(getBean());
        return instructionDatas;
    }

    //什么意思？
    private InstructionData getBean() {
        InstructionData instructionData2 = new InstructionData();
        instructionData2.setDeviceId("0102030405060708");
        instructionData2.setTime(new Date());
        instructionData2.setWaterMeterNum("12345678");
        instructionData2.setCurrentStatus(MyConstant.CURRENT_STATUS_1);
        instructionData2.setBuyWaterTotal(12d);
        instructionData2.setSupplementWater(12d);
        instructionData2.setAddNum(2);
        instructionData2.setUserWater(52d);
        instructionData2.setOverWater(0d);
        instructionData2.setSurplusWater(24d);
        instructionData2.setValveStatus(ConstantValue.FUNCODE_LIVE);
        instructionData2.setPowerStatus(ConstantValue.RETURN_RNT_SUCCESS);
        instructionData2.setAttackNum(ConstantValue.FUNCODE_LIVE);

        return instructionData2;
    }

    /**
     * 更新房间发参数记录
     *
     * @return
     */
    private void updateRoomParamRecord(InstructionData instructionData) {
        instructionOperationDao.updateRoomParamRecord(instructionData);
    }

    /**
     * 房间发参数记录
     *
     * @return
     */
    private RoomParamRecordVo getRoomParamRecordVo(InstructionData instructionData) {
        RoomParamRecordVo roomParamRecordVo = instructionOperationDao.getRoomParamRecordVo(instructionData);
        return roomParamRecordVo;
    }

    /**
     * 根据水表编号获取房间ID
     *
     * @param waterMeterNum
     * @return
     */
    private String getWaterMeterInfoId(String waterMeterNum) {
        String id = instructionOperationDao.getWaterMeterInfoId(waterMeterNum);
        return id;
    }

}
