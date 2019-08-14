package com.ets.bus.waterMeterMgt.waterReset.service;

import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.instructionOperation.service.InstructionsService;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.dao.WaterPurchaseMgtDao;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.InstructionDetailVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.OperationInstructionVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.bus.waterMeterMgt.waterReset.dao.WaterResetDao;
import com.ets.bus.waterMeterMgt.waterReset.entity.ClearRecordVo;
import com.ets.common.MyConstant;
import com.ets.utils.Message;
import com.ets.utils.UUIDUtils;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 宋晨
 * @create 2019/3/28
 * 水量清零管理服务
 */
@Service
@Transactional
public class WaterResetService {
    @Resource
    private WaterResetDao waterResetDao;
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private WaterPurchaseMgtDao waterPurchaseMgtDao;
    @Autowired
    private InstructionsService instructionsService;
    @Autowired
    private OperationLogService operationLogService;


    /**
     * 确认清零
     *
     * @param request
     * @return
     */
    public Message resetConfirm(HttpServletRequest request) {
        Message message = new Message(MyConstant.MSG_SUCCESS, "清零成功！");
        String waterMeterInfoId = request.getParameter("waterMeterInfoId");
        String remark = request.getParameter("remark");
        //校验该房间未完成的指令，不允许进行清零
        if (waterPurchaseMgtService.getNotFinishByMeterIdAndType(waterMeterInfoId, null)) {
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg("该房间存在未完成指令，不允许清零");
        } else {
            sendClear(waterMeterInfoId, remark, MyConstant.CLEAR_TYPE_1);
            //通过水表房间ID查询水表房间信息
            WaterMeterInfoVo wmi=waterPurchaseMgtService.getWaterMeterInfoById(waterMeterInfoId);
            /**************添加操作日志start***************/
            mb_operation_log mol=new mb_operation_log();
            mol.setModuleName("水表管理-房间水量清零");
            mol.setOperaContent("为:["+wmi.getAreaName()+"-"+wmi.getApartmentName()+"-"+wmi.getRoomNum()+"]房间水量清零");
            operationLogService.addLog(mol);
            /**************添加操作日志end****************/
        }
        return message;
    }

    /**
     * 向补水记录表插入一条补水记录
     *
     * @param clearRecordVo
     */
    public void insertClearRecord(ClearRecordVo clearRecordVo) {
        waterResetDao.insertClearRecord(clearRecordVo);
    }

    /**
     * 水量清零
     *
     * @param waterMeterInfoId
     * @param remark
     */
    public void sendClear(String waterMeterInfoId, String remark, Integer type) {
        //查询最新的水表数据
        WaterMeterInfoVo newMeterInfo = waterPurchaseMgtService.collectAndGetWaterMeterInfo(waterMeterInfoId);

        ClearRecordVo clearRecordVo = new ClearRecordVo();
        clearRecordVo.setId(UUIDUtils.getUUID());
        clearRecordVo.setWaterMeterInfoId(newMeterInfo.getId());
        clearRecordVo.setType(type);
        clearRecordVo.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
        clearRecordVo.setUserWater(newMeterInfo.getUserWater());
        clearRecordVo.setSupplementWater(newMeterInfo.getSupplementWater());
        clearRecordVo.setBuyWaterTotal(newMeterInfo.getBuyWaterTotal());
        clearRecordVo.setCardBuyWater(newMeterInfo.getCardBuyWater());
        clearRecordVo.setCashBuyWater(newMeterInfo.getCashBuyWater());
        clearRecordVo.setReturnWater(newMeterInfo.getReturnWater());
        clearRecordVo.setCloseValveWater(newMeterInfo.getCloseValveWater());
        clearRecordVo.setCumulateWater(newMeterInfo.getCumulateWater());
        clearRecordVo.setRemark(remark);
        clearRecordVo.setUserId(waterPurchaseMgtService.getLoginUserId());
        clearRecordVo.setConcentratorId(newMeterInfo.getConcentratorId());

        //获取指令对象
        List<WaterMeterInfoVo> numBeans = new ArrayList<>();
        numBeans.add(newMeterInfo);
        ConcentratorProtocolBean concentrator = instructionsService.getWaterMeterResetConcent(newMeterInfo.getConcentratorNum(), numBeans);
        String instructionJSON = new Gson().toJson(concentrator);

        //先向操作指令表插入一条清零指令
        OperationInstructionVo operationInstruction = new OperationInstructionVo();
        operationInstruction.setId(UUIDUtils.getUUID());
        operationInstruction.setWaterMeterInfoId(clearRecordVo.getWaterMeterInfoId());
        operationInstruction.setType(MyConstant.OPERATION_TYPE_6);
        operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
        operationInstruction.setUserId(clearRecordVo.getUserId());
        operationInstruction.setCollectId(newMeterInfo.getCollectId());
        operationInstruction.setCollectIp(newMeterInfo.getCollectIp());
        operationInstruction.setApartmentId(newMeterInfo.getApartmentId());
        operationInstruction.setConcentratorId(newMeterInfo.getConcentratorId());
        operationInstruction.setWaterMeterNum(newMeterInfo.getWaterMeterNum());
        operationInstruction.setInstructionDetail(instructionJSON);
        waterPurchaseMgtService.insertOperationInstruction(operationInstruction);

        //向指令明细表标记为清零，标记为清零
        InstructionDetailVo instructionDetail = new InstructionDetailVo();
        instructionDetail.setId(UUIDUtils.getUUID());
        instructionDetail.setWaterMeterInfoId(clearRecordVo.getWaterMeterInfoId());
        instructionDetail.setRecordId(clearRecordVo.getId());
        instructionDetail.setType(MyConstant.OPERATION_TYPE_6);
        instructionDetail.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
        instructionDetail.setUserId(clearRecordVo.getUserId());
        instructionDetail.setCollectId(newMeterInfo.getCollectId());
        instructionDetail.setCollectIp(newMeterInfo.getCollectIp());
        instructionDetail.setApartmentId(newMeterInfo.getApartmentId());
        instructionDetail.setOperationId(operationInstruction.getId());
        instructionDetail.setWaterMeterNum(operationInstruction.getWaterMeterNum());
        instructionDetail.setInstructionDetail(instructionJSON);
        waterPurchaseMgtService.insertInstructionDetail(instructionDetail);

        //向清零记录表插入一条记录
        clearRecordVo.setOperationId(operationInstruction.getId());
        insertClearRecord(clearRecordVo);

        //通知采集下发命令
        instructionsService.getWaterMeterReset(newMeterInfo.getConcentratorNum(), concentrator);
    }

    /**
     * 清零导入Excel文件
     * @param excelFile
     * @throws IOException
     */
    public Message upload(MultipartFile excelFile) throws IOException {
        Message message = new Message(MyConstant.MSG_SUCCESS,"清零导入成功！");
        InputStream inputStream = excelFile.getInputStream();
        Workbook wookbook = new XSSFWorkbook(inputStream);

        List<ClearRecordVo> clearRecordList = new ArrayList<ClearRecordVo>();
        List<String> errorList = new ArrayList<String>();
        //得到一个工作表
        Sheet sheet = wookbook.getSheetAt(0);
        //获得表头
        Row rowHead = sheet.getRow(0);
        //判断表头是否正确
        if(rowHead.getPhysicalNumberOfCells() != 5){
            errorList.add("导入的excel表头数量不正确!");
        }else{
            //获得数据的总行数
            int totalRowNum = sheet.getLastRowNum();
            //公寓名称
            String apartmentName;
            //栋名称
            String buildName;
            //楼层名称
            String floorName;
            //房间号
            String roomNum;
            //备注
            String remark;
            //获得所有数据
            for(int i = 1 ; i < totalRowNum ; i++){
                //获得第i行对象
                Row row = sheet.getRow(i);
                if(row == null) continue;
                if(row.getCell(0) == null
                        || row.getCell(1) == null
                        || row.getCell(2) == null
                        || row.getCell(3) == null){
                    errorList.add("第"+ i + "行存在必填项未填写。");
                    continue;
                }
                //获得获得第i行第0列的 String类型对象
                apartmentName = row.getCell(0).getStringCellValue();
                buildName = row.getCell(1).getStringCellValue();
                floorName = row.getCell(2).getStringCellValue();
                roomNum = row.getCell(3).getStringCellValue();
                if(StringUtils.isBlank(apartmentName)
                        || StringUtils.isBlank(buildName)
                        || StringUtils.isBlank(floorName)
                        || StringUtils.isBlank(roomNum)){
                    errorList.add("第"+ i + "行存在必填项未填写。");
                    continue;
                }
                remark = row.getCell(4).getStringCellValue();
                if(remark.length()>200){
                    errorList.add("第"+ i + "行备注信息大于200字。");
                }
                //查询水表房间
                WaterMeterInfoVo waterMeterInfoVo = waterPurchaseMgtDao.getWaterMeterInfoByRoom(apartmentName,buildName,floorName,roomNum);
                if(waterMeterInfoVo == null){
                    errorList.add("第"+ i + "行房间信息不存在。请检查公寓名称，栋名称，楼层名称，房间号。");
                    continue;
                }
                //校验该房间未完成的指令，不允许进行清零
                if (waterPurchaseMgtService.getNotFinishByMeterIdAndType(waterMeterInfoVo.getId(), null)){
                    errorList.add("第"+ i + "行房间存在未完成指令，不允许清零");
                    continue;
                }

                ClearRecordVo clearRecord = new ClearRecordVo();
                clearRecord.setWaterMeterInfoId(waterMeterInfoVo.getId());
                clearRecord.setRemark(remark);
                clearRecordList.add(clearRecord);
            }
        }
        //提示错误
        if(errorList.size() > 0){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg("清零导入失败！");
            message.setBody(errorList);
        }else{
            //执行补水操作
            executeClearWater(clearRecordList);
        }
        return message;
    }

    public void executeClearWater(List<ClearRecordVo> clearRecordList){
        for (ClearRecordVo clearRecord: clearRecordList) {
            sendClear(clearRecord.getWaterMeterInfoId(), clearRecord.getRemark(), MyConstant.CLEAR_TYPE_2);
        }
    }


}