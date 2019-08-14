package com.ets.bus.waterMeterMgt.waterAddMgt.service;

import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.entity.WaterBean;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.instructionOperation.service.InstructionsService;
import com.ets.bus.waterMeterMgt.waterAddMgt.dao.WaterAddMgtDao;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.AddRecordVo;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.dao.WaterPurchaseMgtDao;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.InstructionDetailVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.OperationInstructionVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.common.MyConstant;
import com.ets.utils.Message;
import com.ets.utils.UUIDUtils;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author 宋晨
 * @create 2019/3/28
 * 补水管理服务
 */
@Service
@Transactional
public class WaterAddMgtService {
    @Resource
    private WaterAddMgtDao waterAddMgtDao;
    @Resource
    private WaterPurchaseMgtDao waterPurchaseMgtDao;
    @Autowired
    private WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    private InstructionsService instructionsService;
    @Autowired
    private OperationLogService operationLogService;

    private static final Logger logger = Logger.getLogger(WaterAddMgtService.class);

    /**
     * 若当前房间存在未下发成功的水量（包含补水或购水等）或者补水周期内已补过水
     * @param id
     * @return
     */
    public String checkAllowAddWater(String id){
        String allow = waterPurchaseMgtService.checkAllowBuyWater(id);
        if(StringUtils.isBlank(allow)){
            //补水周期内是否已补过水
            allow = checkAllowAddWaterOnCycle(id);
        }
        return allow;
    }

    /**
     * 补水周期内是否已补过水
     * @param id
     * @return
     */
    public String checkAllowAddWaterOnCycle(String id){
        String result = "";
        //查询补水周期
        Integer cycle = waterAddMgtDao.getAddCycleById(id);
        //周期内是否有补水记录
        if(!MyConstant.ADDCYCLE_TYPE_2.equals(cycle)){
            List<AddRecordVo> addRecordList = waterAddMgtDao.getAddRecordByCycle(id, cycle);
            if(addRecordList != null && addRecordList.size() >0){
                result = "补水周期内已补过水！";
            }
        }
        return result;
    }

    /**
     * 确认补水
     * @param request
     * @return
     */
    public Message addConfirm(HttpServletRequest request){
        AddRecordVo addRecordVo = getAddRecordVo(request);
        Message message = new Message(MyConstant.MSG_SUCCESS,"补水成功！");
        //检查当前房间水表是否存在未下发成功的水量（包含补水或购水等）;
        String errmsg = checkAllowAddWater(addRecordVo.getWaterMeterInfoId());
        if(StringUtils.isNotBlank(errmsg)){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg(errmsg);
        }else{
            //查询最新的水表数据
            WaterMeterInfoVo waterMeterInfo = waterPurchaseMgtService.getWaterMeterInfoById(addRecordVo.getWaterMeterInfoId());
            waterPurchaseMgtService.allowWater(waterMeterInfo);
            //校验补水是否合法
            errmsg = checkAddData(addRecordVo.getAddWater(), waterMeterInfo);
            if(StringUtils.isBlank(errmsg)){
                addRecordVo.setId(UUIDUtils.getUUID());
                addRecordVo.setSerialNumber(waterPurchaseMgtService.getSerialNumber());
                addRecordVo.setConcentratorId(waterMeterInfo.getConcentratorId());
                addRecordVo.setUserId(waterPurchaseMgtService.getLoginUserId());

                //获取指令对象
                List<WaterBean> addWaterBeans = new ArrayList<WaterBean>();
                WaterBean water = getAddWaterBean(waterMeterInfo, addRecordVo);
                addWaterBeans.add(water);
                ConcentratorProtocolBean concentrator = instructionsService.addWaterConcent(waterMeterInfo.getWaterMeterNum(),addWaterBeans);
                String instructionJSON = new Gson().toJson(concentrator);

                //向操作指令表插入一条记录，标记为补水且状态未下发
                OperationInstructionVo operationInstruction = new OperationInstructionVo();
                operationInstruction.setId(UUIDUtils.getUUID());
                operationInstruction.setWaterMeterInfoId(addRecordVo.getWaterMeterInfoId());
                operationInstruction.setType(MyConstant.OPERATION_TYPE_2);
                operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                operationInstruction.setUserId(addRecordVo.getUserId());
                operationInstruction.setCollectId(waterMeterInfo.getCollectId());
                operationInstruction.setCollectIp(waterMeterInfo.getCollectIp());
                operationInstruction.setApartmentId(waterMeterInfo.getApartmentId());
                operationInstruction.setConcentratorId(waterMeterInfo.getConcentratorId());
                operationInstruction.setWaterMeterNum(waterMeterInfo.getWaterMeterNum());
                operationInstruction.setConcentratorNum(waterMeterInfo.getConcentratorNum());
                operationInstruction.setInstructionDetail(instructionJSON);
                waterPurchaseMgtService.insertOperationInstruction(operationInstruction);

                //向指令明细表插入一条补水记录
                InstructionDetailVo instructionDetail = new InstructionDetailVo();
                instructionDetail.setId(UUIDUtils.getUUID());
                instructionDetail.setWaterMeterInfoId(addRecordVo.getWaterMeterInfoId());
                instructionDetail.setRecordId(addRecordVo.getId());
                instructionDetail.setType(MyConstant.OPERATION_TYPE_2);
                instructionDetail.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                instructionDetail.setUserId(addRecordVo.getUserId());
                instructionDetail.setCollectId(waterMeterInfo.getCollectId());
                instructionDetail.setCollectIp(waterMeterInfo.getCollectIp());
                instructionDetail.setApartmentId(waterMeterInfo.getApartmentId());
                instructionDetail.setOperationId(operationInstruction.getId());
                instructionDetail.setWaterMeterNum(operationInstruction.getWaterMeterNum());
                instructionDetail.setInstructionDetail(instructionJSON);
                waterPurchaseMgtService.insertInstructionDetail(instructionDetail);

                //向补水记录表插入一条补水记录
                addRecordVo.setOperationId(operationInstruction.getId());
                insertAddRecord(addRecordVo);

                //通知采集下发命令
                instructionsService.addWater(waterMeterInfo.getConcentratorNum(),concentrator);

                /**************添加操作日志start***************/
                mb_operation_log mol=new mb_operation_log();
                mol.setModuleName("水表管理-补水管理");
                mol.setOperaContent(waterMeterInfo.getAreaName()+"-"+waterMeterInfo.getRoomNum()+"[按房间补水]");
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
     * 按楼层确认补水
     * @param request
     * @return
     */
    public Message addFloorConfirm(HttpServletRequest request){
        Message message = new Message(MyConstant.MSG_SUCCESS,"补水成功！");
        String roomTypeId = request.getParameter("roomType");
        String floorId = request.getParameter("floorId");
        String remark = request.getParameter("remark");
        BigDecimal addWater = new BigDecimal(request.getParameter("addWater"));
        BigDecimal operatMoney = new BigDecimal(request.getParameter("operatMoney"));
        boolean flag = true;
        //根据房间类型和楼层ID获取房间水表信息
        List<WaterMeterInfoVo> waterMeterInfoList = getWaterMeterInfoByFloor(floorId, roomTypeId);
        for (WaterMeterInfoVo waterMeterInfo : waterMeterInfoList) {
            //检查房间水表是否存在未下发成功的水量（包含补水或购水等）;
            String errmsg = checkAllowAddWater(waterMeterInfo.getId());
            if(StringUtils.isNotBlank(errmsg)){
                message.setStatus(MyConstant.MSG_FAIL);
                message.setMsg(errmsg);
                flag = false;
                break;
            }else{
                waterPurchaseMgtService.allowWater(waterMeterInfo);
                //校验购水是否合法
                String msg = checkAddData(addWater.doubleValue(), waterMeterInfo);
                if(StringUtils.isNotBlank(msg)){
                    message.setStatus(MyConstant.MSG_FAIL);
                    message.setMsg(msg);
                    flag = false;
                    break;
                }
            }
        }
        if(flag){

            Set<String> waterMeterNum = new HashSet<String>();
            Map<String, List<WaterBean>> waterBeanMap = new HashMap<String, List<WaterBean>>();
            Map<String, String> instructionJSONMap = new HashMap<String, String>();
            List<OperationInstructionVo> operationInstructionList = new ArrayList<OperationInstructionVo>();
            List<InstructionDetailVo> instructionDetailList = new ArrayList<InstructionDetailVo>();
            Map<String, ConcentratorProtocolBean> concentMap = new HashMap<String, ConcentratorProtocolBean>();

            for (WaterMeterInfoVo waterMeterInfo : waterMeterInfoList) {
                AddRecordVo addRecordVo = new AddRecordVo();
                addRecordVo.setUserId(waterPurchaseMgtService.getLoginUserId());
                addRecordVo.setOperatMoney(operatMoney.doubleValue());
                addRecordVo.setAddWater(addWater.doubleValue());
                addRecordVo.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                addRecordVo.setType(MyConstant.ADD_TYPE_STATUS_1);
                addRecordVo.setRedrush(MyConstant.REDRUSH_STATUS_0);
                addRecordVo.setRemark(remark);
                addRecordVo.setId(UUIDUtils.getUUID());
                addRecordVo.setSerialNumber(waterPurchaseMgtService.getSerialNumber());
                addRecordVo.setConcentratorId(waterMeterInfo.getConcentratorId());
                addRecordVo.setWaterMeterInfoId(waterMeterInfo.getId());

                WaterBean water = getAddWaterBean(waterMeterInfo, addRecordVo);
                List<WaterBean> waterBeans = waterBeanMap.get(waterMeterInfo.getWaterMeterNum());
                if(waterBeans == null){
                    waterBeans = new ArrayList<WaterBean>();
                }
                waterBeans.add(water);
                waterBeanMap.put(waterMeterInfo.getWaterMeterNum(), waterBeans);
                waterMeterNum.add(waterMeterInfo.getWaterMeterNum());

                //向操作指令表插入一条记录，标记为补水且状态未下发
                OperationInstructionVo operationInstruction = new OperationInstructionVo();
                operationInstruction.setId(UUIDUtils.getUUID());
                operationInstruction.setWaterMeterInfoId(addRecordVo.getWaterMeterInfoId());
                operationInstruction.setType(MyConstant.OPERATION_TYPE_2);
                operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                operationInstruction.setUserId(addRecordVo.getUserId());
                operationInstruction.setCollectId(waterMeterInfo.getCollectId());
                operationInstruction.setCollectIp(waterMeterInfo.getCollectIp());
                operationInstruction.setApartmentId(waterMeterInfo.getApartmentId());
                operationInstruction.setConcentratorId(waterMeterInfo.getConcentratorId());
                operationInstruction.setWaterMeterNum(waterMeterInfo.getWaterMeterNum());
                operationInstruction.setConcentratorNum(waterMeterInfo.getConcentratorNum());
                operationInstructionList.add(operationInstruction);

                //向指令明细表插入一条补水记录
                InstructionDetailVo instructionDetail = new InstructionDetailVo();
                instructionDetail.setId(UUIDUtils.getUUID());
                instructionDetail.setWaterMeterInfoId(addRecordVo.getWaterMeterInfoId());
                instructionDetail.setRecordId(addRecordVo.getId());
                instructionDetail.setType(MyConstant.OPERATION_TYPE_2);
                instructionDetail.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                instructionDetail.setUserId(addRecordVo.getUserId());
                instructionDetail.setCollectId(waterMeterInfo.getCollectId());
                instructionDetail.setCollectIp(waterMeterInfo.getCollectIp());
                instructionDetail.setApartmentId(waterMeterInfo.getApartmentId());
                instructionDetail.setOperationId(operationInstruction.getId());
                instructionDetail.setWaterMeterNum(operationInstruction.getWaterMeterNum());
                instructionDetailList.add(instructionDetail);

                //向补水记录表插入一条补水记录
                addRecordVo.setOperationId(operationInstruction.getId());
                insertAddRecord(addRecordVo);

            }
            for (String connum : waterMeterNum) {
                ConcentratorProtocolBean concentrator = instructionsService.addWaterConcent(connum,waterBeanMap.get(connum));
                if(instructionJSONMap.get(connum) == null){
                    instructionJSONMap.put(connum, new Gson().toJson(concentrator));
                }
                concentMap.put(connum,concentrator);
            }
            //向操作指令表插入数据
            for (OperationInstructionVo item : operationInstructionList) {
                item.setInstructionDetail(instructionJSONMap.get(item.getWaterMeterNum()));
                waterPurchaseMgtService.insertOperationInstruction(item);
            }
            //向指令明细表插入数据
            for (InstructionDetailVo item : instructionDetailList) {
                item.setInstructionDetail(instructionJSONMap.get(item.getWaterMeterNum()));
                waterPurchaseMgtService.insertInstructionDetail(item);
            }
            for (String connum : waterMeterNum) {
                //通知采集下发命令
                instructionsService.addWater("22023",concentMap.get(connum));
            }
        }
        return message;
    }

    /**
     * 根据楼层ID查询所有房间类型
     * @param floorId
     * @return
     */
    public List<RoomTypeVo> getRoomTypeByFloorId(String floorId){
        List<RoomTypeVo> roomTypeList = waterAddMgtDao.getRoomTypeByFloorId(floorId);
        return roomTypeList;
    }

    /**
     * 根据房间类型和楼层ID获取房间水表信息
     * @param floorId
     * @param roomTypeId
     * @return
     */
    public List<WaterMeterInfoVo> getWaterMeterInfoByFloor(String floorId, String roomTypeId){
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("floorId", floorId);
        map.put("roomTypeId", roomTypeId);
        List<WaterMeterInfoVo> waterMeterInfoList = waterPurchaseMgtDao.selectWaterMeterInfoList(map);
        return waterMeterInfoList;
    }


    /**
     * 获取前台传入参数
     * @param request
     * @return
     */
    private AddRecordVo getAddRecordVo(HttpServletRequest request){
        AddRecordVo recordVo = new AddRecordVo();
        recordVo.setWaterMeterInfoId(request.getParameter("waterMeterInfoId"));
        BigDecimal addWater = new BigDecimal(request.getParameter("addWater"));
        recordVo.setAddWater(addWater.doubleValue());
        BigDecimal operatMoney = new BigDecimal(request.getParameter("operatMoney"));
        recordVo.setOperatMoney(operatMoney.doubleValue());
        recordVo.setRemark(request.getParameter("remark"));
        recordVo.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
        recordVo.setType(MyConstant.ADD_TYPE_STATUS_0);
        recordVo.setRedrush(MyConstant.REDRUSH_STATUS_0);

        return recordVo;
    }

    /**
     * 校验补水数据是否合法
     * 补水量不能超过该房间的最大囤积量与剩余水量之差（剩余购水+剩余补水）+ 透支量
     * @param addWater
     * @param waterMeterInfo
     * @return
     */
    private String checkAddData(Double addWater, WaterMeterInfoVo waterMeterInfo){
        String result = "";
        //允许补水量 最大囤积量与剩余水量之差（剩余购水+剩余补水）+ 透支量
        if(addWater > waterMeterInfo.getAllowWater()){
            result = "补水量不能超过该房间的最大囤积量+透支量与剩余水量之差"+waterMeterInfo.getAllowWater()+"。";
        }
        return result;
    }

    /**
     * 向补水记录表插入一条补水记录
     * @param addRecordVo
     */
    public void insertAddRecord(AddRecordVo addRecordVo){
        waterAddMgtDao.insertAddRecord(addRecordVo);
    }

    /**
     * 获取补水bean
     * @param waterMeterInfo
     * @param addRecordVo
     * @return
     */
    private WaterBean getAddWaterBean(WaterMeterInfoVo waterMeterInfo, AddRecordVo addRecordVo){
        WaterBean waterBean = new WaterBean();
        waterBean.setWaterMeterNum(Integer.parseInt(waterMeterInfo.getWaterMeterNum()));
        waterBean.setBuyWater(0);
        waterBean.setAddWater((new Double(addRecordVo.getAddWater()*10)).intValue());
        waterBean.setAddNum(waterMeterInfo.getAddNum()+1);
        return waterBean;
    }

    /**
     * 补水导入Excel文件
     * @throws IOException
     */
    public Message excelUpload(MultipartFile excelFile) throws IOException {
        Message message = new Message(MyConstant.MSG_SUCCESS,"补水导入成功！");
        InputStream inputStream = excelFile.getInputStream();
        Workbook wookbook = new XSSFWorkbook(inputStream);
//        if(excelFile.getOriginalFilename().endsWith(".xls")){
//            //2003版本的excel，用.xls结尾
//            wookbook = new HSSFWorkbook(inputStream);//得到工作簿
//        }
//        if(excelFile.getOriginalFilename().endsWith(".xlsx")){
//            //2007版本的excel，用.xlsx结尾
//            wookbook = new XSSFWorkbook(inputStream);//得到工作簿
//        }
        List<AddRecordVo> addRecordList = new ArrayList<AddRecordVo>();
        List<String> errorList = new ArrayList<String>();
        //得到一个工作表
        Sheet sheet = wookbook.getSheetAt(0);
        //获得表头
        Row rowHead = sheet.getRow(0);
        //判断表头是否正确
        if(rowHead.getPhysicalNumberOfCells() != 6){
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
            //补水量
            Double supplementWater = 0d;
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
                        || row.getCell(3) == null
                        || row.getCell(4) == null){
                    errorList.add("第"+ i + "行存在必填项未填写或补水量小于等于零。");
                    continue;
                }
                //获得获得第i行第0列的 String类型对象
                apartmentName = row.getCell(0).getStringCellValue();
                buildName = row.getCell(1).getStringCellValue();
                floorName = row.getCell(2).getStringCellValue();
                roomNum = row.getCell(3).getStringCellValue();
                try{
                    supplementWater = row.getCell(4).getNumericCellValue();
                }catch (Exception e){
                    //请输入数字
                    errorList.add("第"+ i + "行补水量格式错误！必须为数字，精确到小数点后一位。");
                }
                if(StringUtils.isBlank(apartmentName)
                        || StringUtils.isBlank(buildName)
                        || StringUtils.isBlank(floorName)
                        || StringUtils.isBlank(roomNum)
                        || supplementWater <= 0){
                    errorList.add("第"+ i + "行存在必填项未填写或补水量小于等于零。");
                    continue;
                }
                remark = row.getCell(5).getStringCellValue();
                if(remark.length()>200){
                    errorList.add("第"+ i + "行备注信息大于200字。");
                }
                //查询水表房间
                WaterMeterInfoVo waterMeterInfoVo = waterPurchaseMgtDao.getWaterMeterInfoByRoom(apartmentName,buildName,floorName,roomNum);
                if(waterMeterInfoVo == null){
                    errorList.add("第"+ i + "行房间信息不存在。请检查公寓名称，栋名称，楼层名称，房间号。");
                    continue;
                }
                //检查当前房间水表是否存在未下发成功的水量（包含补水或购水等）;
                String errmsg = checkAllowAddWater(waterMeterInfoVo.getId());
                if(StringUtils.isNotBlank(errmsg)){
                    errorList.add("第"+ i + "行" + errmsg);
                    continue;
                }
                //校验补水是否合法
                waterPurchaseMgtService.allowWater(waterMeterInfoVo);
                errmsg = checkAddData(supplementWater, waterMeterInfoVo);
                if(StringUtils.isNotBlank(errmsg)){
                    errorList.add("第"+ i + "行" + errmsg);
                    continue;
                }

                AddRecordVo recordVo = new AddRecordVo();
                recordVo.setId(UUIDUtils.getUUID());
                recordVo.setWaterMeterInfoId(waterMeterInfoVo.getId());
                recordVo.setAddWater(supplementWater);
                recordVo.setRemark(remark);
                recordVo.setOperatMoney(0d);
                recordVo.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
                recordVo.setType(MyConstant.ADD_TYPE_STATUS_2);
                recordVo.setRedrush(MyConstant.REDRUSH_STATUS_0);
                recordVo.setSerialNumber(waterPurchaseMgtService.getSerialNumber());
                recordVo.setConcentratorId(waterMeterInfoVo.getConcentratorId());
                recordVo.setUserId(waterPurchaseMgtService.getLoginUserId());
                recordVo.setWaterMeterInfoVo(waterMeterInfoVo);
                addRecordList.add(recordVo);
            }
        }
        //提示错误
        if(errorList.size() > 0){
            message.setStatus(MyConstant.MSG_FAIL);
            message.setMsg("补水导入失败！");
            message.setBody(errorList);
        }else{
            //执行补水操作
            executeAddWater(addRecordList);
        }
        return message;
    }

    /**
     * 批量执行导入补水操作
     * @param addRecordList
     */
    public void  executeAddWater(List<AddRecordVo> addRecordList){
        for (AddRecordVo addRecordVo : addRecordList) {

            //获取指令对象
            List<WaterBean> addWaterBeans = new ArrayList<WaterBean>();
            WaterBean water = getAddWaterBean(addRecordVo.getWaterMeterInfoVo(), addRecordVo);
            addWaterBeans.add(water);
            ConcentratorProtocolBean concentrator = instructionsService.addWaterConcent(addRecordVo.getWaterMeterInfoVo().getConcentratorNum(),addWaterBeans);
            String instructionJSON = new Gson().toJson(concentrator);

            //向操作指令表插入一条记录，标记为补水且状态未下发
            OperationInstructionVo operationInstruction = new OperationInstructionVo();
            operationInstruction.setId(UUIDUtils.getUUID());
            operationInstruction.setWaterMeterInfoId(addRecordVo.getWaterMeterInfoId());
            operationInstruction.setType(MyConstant.OPERATION_TYPE_2);
            operationInstruction.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
            operationInstruction.setUserId(addRecordVo.getUserId());
            operationInstruction.setCollectId(addRecordVo.getWaterMeterInfoVo().getCollectId());
            operationInstruction.setCollectIp(addRecordVo.getWaterMeterInfoVo().getCollectIp());
            operationInstruction.setApartmentId(addRecordVo.getWaterMeterInfoVo().getApartmentId());
            operationInstruction.setConcentratorId(addRecordVo.getWaterMeterInfoVo().getConcentratorId());
            operationInstruction.setWaterMeterNum(addRecordVo.getWaterMeterInfoVo().getWaterMeterNum());
            operationInstruction.setConcentratorNum(addRecordVo.getWaterMeterInfoVo().getConcentratorNum());
            operationInstruction.setInstructionDetail(instructionJSON);
            waterPurchaseMgtService.insertOperationInstruction(operationInstruction);

            //向指令明细表插入一条补水记录
            InstructionDetailVo instructionDetail = new InstructionDetailVo();
            instructionDetail.setId(UUIDUtils.getUUID());
            instructionDetail.setWaterMeterInfoId(addRecordVo.getWaterMeterInfoId());
            instructionDetail.setRecordId(addRecordVo.getId());
            instructionDetail.setType(MyConstant.OPERATION_TYPE_2);
            instructionDetail.setCurrentStatus(MyConstant.CURRENT_STATUS_0);
            instructionDetail.setUserId(addRecordVo.getUserId());
            instructionDetail.setCollectId(addRecordVo.getWaterMeterInfoVo().getCollectId());
            instructionDetail.setCollectIp(addRecordVo.getWaterMeterInfoVo().getCollectIp());
            instructionDetail.setApartmentId(addRecordVo.getWaterMeterInfoVo().getApartmentId());
            instructionDetail.setOperationId(operationInstruction.getId());
            instructionDetail.setWaterMeterNum(operationInstruction.getWaterMeterNum());
            instructionDetail.setInstructionDetail(instructionJSON);
            waterPurchaseMgtService.insertInstructionDetail(instructionDetail);

            //向补水记录表插入一条补水记录
            addRecordVo.setOperationId(operationInstruction.getId());
            insertAddRecord(addRecordVo);

            //通知采集下发命令
            instructionsService.addWater(addRecordVo.getWaterMeterInfoVo().getConcentratorNum(),concentrator);
        }
    }

    public List<WaterMeterInfoVo> getRoomsByFloorId(String floorId) {
        return waterAddMgtDao.getRoomsByFloorId(floorId);
    }
}
