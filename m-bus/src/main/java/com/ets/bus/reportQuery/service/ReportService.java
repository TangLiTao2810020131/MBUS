package com.ets.bus.reportQuery.service;

import com.ets.bus.reportQuery.dao.ReportDao;
import com.ets.bus.reportQuery.entity.historyrecord.HistoryRecord;
import com.ets.bus.reportQuery.entity.report.*;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.systemMgt.waterMeterMgt.service.WaterMeterMgtService;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.common.MyConstant;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.ExcelUtil;
import com.ets.utils.PageListData;
import com.ets.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 宋晨
 * @create 2019/4/4
 * 报表查询服务类
 */
@Service
@Transactional
@SuppressWarnings("all")
public class ReportService {
    @Resource
    private ReportDao reportDao;

    @Resource
    WaterMeterMgtService waterMeterMgtService;

    @Autowired
    private OperationLogService operationLogService;

    //冲红类型常量
    private final static int REDRECORD_0=0;//补水
    private final static int REDRECORD_1=1;//退水


    /**
     * 组装查询条件
     * @param request
     * @return
     */
     public Map<String, Object> getReqSearchParam(HttpServletRequest request) {
         Map<String, Object> map = new HashMap<String, Object>();
         map.put("roomTypeId", request.getParameter("roomTypeId"));
         map.put("apartmentName", request.getParameter("apartmentName"));
         map.put("floorName", request.getParameter("floorNum"));
         map.put("buildName", request.getParameter("buildName"));
         map.put("concentratorIp", request.getParameter("concentratorId"));
         map.put("roomNum", StringUtils.isNotBlank(request.getParameter("roomNum"))?request.getParameter("roomNum"):request.getParameter("roomNumOut"));
         Date startTime = null;
         if(StringUtils.isNotBlank(request.getParameter("startTime"))){
             startTime = DateTimeUtils.getDateByString(request.getParameter("startTime"));
             map.put("startTime",startTime);
         }
         Date endTime = null;
         if(StringUtils.isNotBlank(request.getParameter("endTime"))){
             endTime = DateTimeUtils.getDateByString(request.getParameter("endTime"));
             map.put("endTime",endTime);
         }
         Date openTime = null;
         if(StringUtils.isNotBlank(request.getParameter("openTime"))){
             openTime = DateTimeUtils.getDateByString(request.getParameter("openTime"));
             map.put("openTime",openTime);
         }
         Date changeTime = null;
         if(StringUtils.isNotBlank(request.getParameter("changeTime"))){
             changeTime = DateTimeUtils.getDateByString(request.getParameter("changeTime"));
             map.put("changeTime",changeTime);
         }
         String monthTime = null;
         if(StringUtils.isNotBlank(request.getParameter("monthTime"))){
             monthTime = DateTimeUtils.getMonthByString(request.getParameter("monthTime"));
             map.put("monthTime",monthTime);
         }
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
     * 查询冲红记录
     * @param page
     * @param limit
     * @param map
     * @return
     */
     public PageListData<RedRushVo> getRedrushList(Integer page, Integer limit, Map<String, Object> map){
         PageHelper.startPage(page, limit);
         List<RedRushVo> list = reportDao.selectRedrushList(map);

         PageInfo<RedRushVo> pageInfo = new PageInfo<RedRushVo>(list);
         PageListData<RedRushVo> pageData = new PageListData<RedRushVo>();
         pageData.setCode("0");
         pageData.setCount(pageInfo.getTotal());
         pageData.setMessage("");
         pageData.setData(list);
         return pageData;
     }

    /**
     * 查询换表记录
     * @param page
     * @param limit
     * @param map
     * @return
     */
     public PageListData<ReplaceRecordVo> getReplaceRecordList(Integer page, Integer limit, Map<String, Object> map){
         PageHelper.startPage(page, limit);
         List<ReplaceRecordVo> list = reportDao.selectReplaceRecordList(map);
         for(ReplaceRecordVo rrv:list){
             try{
                 if(rrv.getWaterType().equals(MyConstant.WATER_TYPE_0)){
                     rrv.setWaterType("冷水表");
                 }else if(rrv.getWaterType().equals(MyConstant.WATER_TYPE_1)){
                     rrv.setWaterType("生活热水表");
                 }else{
                     rrv.setWaterType("其他");
                 }

                 if(rrv.getCurrentStatus()==MyConstant.CURRENT_STATUS_0){
                     rrv.setCurrentStatusName("未下发");
                 }else if(rrv.getCurrentStatus()==MyConstant.CURRENT_STATUS_1){
                     rrv.setCurrentStatusName("下发成功");
                 }else if(rrv.getCurrentStatus()==MyConstant.CURRENT_STATUS_0){
                     rrv.setCurrentStatusName("下发失败");
                 }else{
                     rrv.setCurrentStatusName("其他");
                 }
             }catch(NullPointerException ex){

             }
         }
         PageInfo<ReplaceRecordVo> pageInfo = new PageInfo<ReplaceRecordVo>(list);
         PageListData<ReplaceRecordVo> pageData = new PageListData<ReplaceRecordVo>();
         pageData.setCode("0");
         pageData.setCount(pageInfo.getTotal());
         pageData.setMessage("");
         pageData.setData(list);
         return pageData;
     }

    /**
     * 查询控水换房记录
     * @param page
     * @param limit
     * @param map
     * @return
     */
     public PageListData<ReplaceRoomRecordVo> getReplaceRoomRecordList(Integer page, Integer limit, Map<String, Object> map){
         PageHelper.startPage(page, limit);
         List<ReplaceRoomRecordVo> list = reportDao.selectReplaceRoomRecordList(map);
         for(ReplaceRoomRecordVo rr:list){
             if(rr.getCurrentStatus()==MyConstant.CURRENT_STATUS_0){
                 rr.setCurrentStatusName("未下发");
             }else if(rr.getCurrentStatus()==MyConstant.CURRENT_STATUS_1){
                 rr.setCurrentStatusName("下发成功");
             }else if(rr.getCurrentStatus()==MyConstant.CURRENT_STATUS_2){
                 rr.setCurrentStatusName("下发失败");
             }else{
                 rr.setCurrentStatusName("其他");
             }
         }
         PageInfo<ReplaceRoomRecordVo> pageInfo = new PageInfo<ReplaceRoomRecordVo>(list);
         PageListData<ReplaceRoomRecordVo> pageData = new PageListData<ReplaceRoomRecordVo>();
         pageData.setCode("0");
         pageData.setCount(pageInfo.getTotal());
         pageData.setMessage("");
         pageData.setData(list);
         return pageData;
     }

    /**
     * 查询用水房间日报
     * @param page
     * @param limit
     * @param map
     * @return
     */
    public PageListData<WaterDailyVo> getRoomDailyList(Integer page, Integer limit, Map<String, Object> map){
        PageHelper.startPage(page, limit);
        List<WaterDailyVo> list = reportDao.selectRoomDailyList(map);

        PageInfo<WaterDailyVo> pageInfo = new PageInfo<WaterDailyVo>(list);
        PageListData<WaterDailyVo> pageData = new PageListData<WaterDailyVo>();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(list);
        return pageData;
    }

    /**
     * 查询用水房间月报
     * @param page
     * @param limit
     * @param map
     * @return
     */
     public PageListData<WaterDailyVo> getRoomMonthList(Integer page, Integer limit, Map<String, Object> map){
         PageHelper.startPage(page, limit);
         List<WaterDailyVo> list = reportDao.selectRoomMonthList(map);

         PageInfo<WaterDailyVo> pageInfo = new PageInfo<WaterDailyVo>(list);
         PageListData<WaterDailyVo> pageData = new PageListData<WaterDailyVo>();
         pageData.setCode("0");
         pageData.setCount(pageInfo.getTotal());
         pageData.setMessage("");
         pageData.setData(list);
         return pageData;
     }

    //历史命令
    public PageListData<HistoryCommandVo> getHistoryCommandList(Integer page, Integer limit, Map<String, Object> map) {
        PageHelper.startPage(page, limit);
        List<HistoryCommandVo> list = reportDao.selectHistoryCommandList(map);
        for (HistoryCommandVo hcv : list) {
            try {
                if (hcv.getCurrentStatus() == MyConstant.CURRENT_STATUS_0) {
                    hcv.setCurrentStatusName("未下发");
                } else if (hcv.getCurrentStatus() == MyConstant.CURRENT_STATUS_1) {
                    hcv.setCurrentStatusName("下发成功");
                } else if (hcv.getCurrentStatus() == MyConstant.CURRENT_STATUS_0) {
                    hcv.setCurrentStatusName("下发失败");
                } else {
                    hcv.setCurrentStatusName("其他");
                }
            } catch (NullPointerException ex) {

            }
        }
        PageInfo<HistoryCommandVo> pageInfo = new PageInfo<HistoryCommandVo>(list);
        PageListData<HistoryCommandVo> pageData = new PageListData<HistoryCommandVo>();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(list);
        return pageData;
    }
    public PageListData<HistoryRecord> getNewReadMeterList(Integer page, Integer limit, Map<String, Object> map) {
        PageHelper.startPage(page, limit);
        List<HistoryRecord> list = reportDao.selectNewReadMeterList(map);
        PageInfo<HistoryRecord> pageInfo = new PageInfo<HistoryRecord>(list);
        PageListData<HistoryRecord> pageData = new PageListData<HistoryRecord>();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(list);
        return pageData;
    }



    public List<RedRushVo> export(Map<String,Object> map)
    {
        return reportDao.selectRedrushList(map);
    }


    //换表记录导出
    public void exportReplaceRecord(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        //excel文件名
        String fileName = name+".xls";

        //sheet名
        String sheetName =name;

        List<ReplaceRecordVo> bList=reportDao.selectReplaceRecordList(this.getReqSearchParam(request));
        for(ReplaceRecordVo rv:bList){
            rv.setOpenTime(new Timestamp(rv.getOpenTime().getTime()));
            rv.setReplaceTime(new Timestamp(rv.getReplaceTime().getTime()));
            if(rv.getCurrentStatus()==MyConstant.CURRENT_STATUS_0){
                rv.setCurrentStatusName("未下发");
            }else if(rv.getCurrentStatus()==MyConstant.CURRENT_STATUS_1){
                rv.setCurrentStatusName("下发成功");
            }else if(rv.getCurrentStatus()==MyConstant.CURRENT_STATUS_2){
                rv.setCurrentStatusName("下发失败");
            }else{
                rv.setCurrentStatusName("其他");
            }

            if(rv.getWaterType().equals(MyConstant.WATER_TYPE_0)){
                rv.setWaterType("冷水表");
            }else if(rv.getWaterType().equals(MyConstant.WATER_TYPE_1)){
                rv.setWaterType("生活热水表");
            }else{
                rv.setWaterType("其他");
            }
        }
        //excel标题
        String[] title =str.split(",");

        String [][] content = new String[bList.size()][];
        for(int i=0;i<bList.size();i++)
        {
            content[i] = new String[title.length];
            content[i][0]=((ReplaceRecordVo)bList.get(i)).getApartmentName();
            content[i][1]=((ReplaceRecordVo)bList.get(i)).getRoomNum();
            content[i][2]=((ReplaceRecordVo)bList.get(i)).getWaterType();
            content[i][3]=((ReplaceRecordVo)bList.get(i)).getCurrentStatusName();
            content[i][4]=((ReplaceRecordVo)bList.get(i)).getOpenTime().toString();
            content[i][5]=((ReplaceRecordVo)bList.get(i)).getReplaceTime().toString();
            content[i][6]=((ReplaceRecordVo)bList.get(i)).getReplaceMoney().toString();
            content[i][7]=((ReplaceRecordVo)bList.get(i)).getReplaceWaterNum().toString();
            content[i][8]=((ReplaceRecordVo)bList.get(i)).getOperationUser();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        ExcelUtil.returnClient(response,fileName,wb);

    }

    //控水换房明细导出
    public void exportReplaceRoomRecord(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        //excel文件名
        String fileName = name+".xls";

        //sheet名
        String sheetName =name;

        List<ReplaceRoomRecordVo> bList=reportDao.selectReplaceRoomRecordList(getReqSearchParam(request));
        for(ReplaceRoomRecordVo rr:bList){
            rr.setCreateTime(new Timestamp(rr.getCreateTime().getTime()));
            if(rr.getCurrentStatus()==MyConstant.CURRENT_STATUS_0){
                rr.setCurrentStatusName("未下发");
            }else  if(rr.getCurrentStatus()==MyConstant.CURRENT_STATUS_1){
                rr.setCurrentStatusName("下发成功");
            }else  if(rr.getCurrentStatus()==MyConstant.CURRENT_STATUS_2){
                rr.setCurrentStatusName("下发失败");
            }else{
                rr.setCurrentStatusName("其他");
            }
        }
        //excel标题
        String[] title =str.split(",");

        String [][] content = new String[bList.size()][];
        for(int i=0;i<bList.size();i++)
        {
            content[i] = new String[title.length];
            content[i][0]=((ReplaceRoomRecordVo)bList.get(i)).getAreaName();
            content[i][1]=((ReplaceRoomRecordVo)bList.get(i)).getApartmentName();
            content[i][2]=((ReplaceRoomRecordVo)bList.get(i)).getRoomNum();
            content[i][3]=((ReplaceRoomRecordVo)bList.get(i)).getCurrentStatusName();
            content[i][4]=((ReplaceRoomRecordVo)bList.get(i)).getCreateTime().toString();
            content[i][6]=((ReplaceRoomRecordVo)bList.get(i)).getSupplementWater().toString();
            content[i][7]=((ReplaceRoomRecordVo)bList.get(i)).getReturnWater().toString();
            content[i][8]=((ReplaceRoomRecordVo)bList.get(i)).getUserWater().toString();
            content[i][9]=((ReplaceRoomRecordVo)bList.get(i)).getUserName();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        ExcelUtil.returnClient(response,fileName,wb);
    }

    //用水房间日报导出
    public void exportRoomDaily(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        //excel文件名
        String fileName = name+".xls";

        //sheet名
        String sheetName =name;
        List<WaterDailyVo> bList=reportDao.selectRoomDailyList(getReqSearchParam(request));
        for(WaterDailyVo wd:bList){
            wd.setCreateTime(new Timestamp(wd.getCreateTime().getTime()));
        }
        //excel标题
        String[] title =str.split(",");

        String [][] content = new String[bList.size()][];
        for(int i=0;i<bList.size();i++)
        {
            content[i] = new String[title.length];
            content[i][0]=((WaterDailyVo)bList.get(i)).getAreaName();
            content[i][1]=((WaterDailyVo)bList.get(i)).getApartmentName();
            content[i][2]=((WaterDailyVo)bList.get(i)).getBuildName();
            content[i][3]=((WaterDailyVo)bList.get(i)).getRoomNum();
            content[i][4]=((WaterDailyVo)bList.get(i)).getCreateTime()+"";;
            content[i][5]=((WaterDailyVo)bList.get(i)).getSupplementWater().toString();
            content[i][6]=((WaterDailyVo)bList.get(i)).getUserWater().toString();
            content[i][7]=((WaterDailyVo)bList.get(i)).getBuyWaterTotal().toString();
            content[i][8]=((WaterDailyVo)bList.get(i)).getSupplementWater().toString();
            content[i][9]=((WaterDailyVo)bList.get(i)).getReturnWater().toString();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        ExcelUtil.returnClient(response,fileName,wb);
    }

    //用水房间月报导出
    public void exportRoomMonth(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        //excel文件名
        String fileName = name+".xls";

        //sheet名
        String sheetName =name;
        List<WaterDailyVo> list = reportDao.selectRoomMonthList(getReqSearchParam(request));
        //excel标题
        String[] title =str.split(",");

        String [][] content = new String[list.size()][];
        for(int i=0;i<list.size();i++)
        {
            content[i] = new String[title.length];
            content[i][0]=((WaterDailyVo)list.get(i)).getAreaName();
            content[i][1]=((WaterDailyVo)list.get(i)).getApartmentName();
            content[i][2]=((WaterDailyVo)list.get(i)).getFloorName();
            content[i][3]=((WaterDailyVo)list.get(i)).getRoomNum();
            content[i][4]=((WaterDailyVo)list.get(i)).getUseMonth();
            content[i][5]=((WaterDailyVo)list.get(i)).getSurplusWater().toString();
            content[i][6]=((WaterDailyVo)list.get(i)).getUserWater().toString();
            content[i][7]=((WaterDailyVo)list.get(i)).getCardBuyWater().toString();
            content[i][8]=((WaterDailyVo)list.get(i)).getSupplementWater().toString();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        ExcelUtil.returnClient(response,fileName,wb);
    }

    //导出冲红记录
    public void exportRedrush(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        //excel文件名
        String fileName = name+".xls";

        //sheet名
        String sheetName =name;
        List<RedRushVo> bList=reportDao.selectRedrushList(getReqSearchParam(request));
        for(RedRushVo rv:bList){
            rv.setCreateTime(new Timestamp(rv.getCreateTime().getTime()));
        }
        //excel标题
        String[] title =str.split(",");

        String [][] content = new String[bList.size()][];
        for(int i=0;i<bList.size();i++)
        {
            content[i] = new String[title.length];
            content[i][0]=((RedRushVo)bList.get(i)).getAreaName();
            content[i][1]=((RedRushVo)bList.get(i)).getApartmentName();
            content[i][2]=((RedRushVo)bList.get(i)).getFloorName();
            content[i][3]=((RedRushVo)bList.get(i)).getRoomNum();
            content[i][4]=((RedRushVo)bList.get(i)).getCreateTime().toString();
            content[i][5]=((RedRushVo)bList.get(i)).getCurrentStatusName();
            content[i][6]=((RedRushVo)bList.get(i)).getRedrushWater().toString();
            content[i][7]=((RedRushVo)bList.get(i)).getOperatName();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        ExcelUtil.returnClient(response,fileName,wb);
    }
    //导出历史命令记录
    public void exportHistoryCommand(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        //excel文件名
        String fileName = name+".xls";

        //sheet名
        String sheetName =name;
        List<HistoryCommandVo> hList=reportDao.selectHistoryCommandList(getReqSearchParam(request));
        for(HistoryCommandVo hc:hList){
            hc.setCreateTime(new Timestamp(hc.getCreateTime().getTime()));
        }
        //excel标题
        String[] title =str.split(",");

        String [][] content = new String[hList.size()][];
        for(int i=0;i<hList.size();i++)
        {
            content[i] = new String[title.length];
            content[i][0]=((HistoryCommandVo)hList.get(i)).getAreaName();
            content[i][1]=((HistoryCommandVo)hList.get(i)).getApartmentName();
            content[i][2]=((HistoryCommandVo)hList.get(i)).getFloorName();
            content[i][3]=((HistoryCommandVo)hList.get(i)).getRoomNum();
            content[i][4]=((HistoryCommandVo)hList.get(i)).getTypeName();
            content[i][5]=((HistoryCommandVo)hList.get(i)).getInstructionDetatil();
            content[i][6]=((HistoryCommandVo)hList.get(i)).getResult();
            content[i][7]=((HistoryCommandVo)hList.get(i)).getCurrentStatusName();
            content[i][8]=((HistoryCommandVo)hList.get(i)).getCreateTime().toString();

        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        ExcelUtil.returnClient(response,fileName,wb);

        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-历史命令");
        mol.setOperaContent("历史命令信息导出");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
    }

    //导出最新抄表
    public void exportNewReadMeter(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        //excel文件名
        String fileName = name+".xls";

        //sheet名
        String sheetName =name;
        List<HistoryRecord> bList=reportDao.selectNewReadMeterList(getReqSearchParam(request));
       //excel标题
        String[] title =str.split(",");

        String [][] content = new String[bList.size()][];
        for(int i=0;i<bList.size();i++)
        {
            content[i] = new String[title.length];
            content[i][0]=((HistoryRecord)bList.get(i)).getAreaName();
            content[i][1]=((HistoryRecord)bList.get(i)).getApartmentName();
            content[i][2]=((HistoryRecord)bList.get(i)).getBuildName();
            content[i][3]=((HistoryRecord)bList.get(i)).getFloorName();
            content[i][4]=((HistoryRecord)bList.get(i)).getRoomNum();
            content[i][5]=((HistoryRecord)bList.get(i)).getWaterMeterNum();
            content[i][6]=((HistoryRecord)bList.get(i)).getValveStatusName();
            content[i][7]=((HistoryRecord)bList.get(i)).getModuleStatusName();
            content[i][8]=((HistoryRecord)bList.get(i)).getCurrentStatusName();
            content[i][9]=((HistoryRecord)bList.get(i)).getCreateTime();
            content[i][10]=((HistoryRecord)bList.get(i)).getBuyWaterTotal().toString();
            content[i][11]=((HistoryRecord)bList.get(i)).getSupplementWater().toString();
            content[i][12]=((HistoryRecord)bList.get(i)).getOverWater().toString();
            content[i][13]=((HistoryRecord)bList.get(i)).getUserWater().toString();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        ExcelUtil.returnClient(response,fileName,wb);

        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-最新抄表");
        mol.setOperaContent("导出最新抄表信息");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/

    }


    public RedRushVo findRedRecord(String id)
    {
        RedRushVo rv=reportDao.findRedRecord(id);
        if(rv.getType()==REDRECORD_0){
            rv.setTypeName("补水");
        }else if(rv.getType()==REDRECORD_1){
            rv.setTypeName("退水");
        }else {
            rv.setTypeName("其他");
        }
        return rv;
    }

    public ReplaceRecordVo findReplaceWaterRecord(String id)
    {
        ReplaceRecordVo rv=reportDao.findReplaceWaterRecord(id);
        try{
            if(rv.getWaterType().equals(MyConstant.WATER_TYPE_0)){
                rv.setWaterType("冷水表");
            }else if(rv.getWaterType().equals(MyConstant.WATER_TYPE_1)){
                rv.setWaterType("生活热水表");
            }else{
                rv.setWaterType("其他");
            }

            if(rv.getCurrentStatus()==MyConstant.CURRENT_STATUS_0){
                rv.setCurrentStatusName("未下发");
            }else if(rv.getCurrentStatus()==MyConstant.CURRENT_STATUS_1){
                rv.setCurrentStatusName("下发成功");
            }else if(rv.getCurrentStatus()==MyConstant.CURRENT_STATUS_0){
                rv.setCurrentStatusName("下发失败");
            }else{
                rv.setCurrentStatusName("其他");
            }
        }catch(NullPointerException ex){

        }
        //根据水表id查询水表编号IMEL
        try{
            rv.setOldWaterMeterNum(waterMeterMgtService.getWaterMeterById(rv.getOldWaterMeterId()).getWater_meter_id());
            rv.setNewWaterMeterNum(waterMeterMgtService.getWaterMeterById(rv.getNewWaterMeterId()).getWater_meter_id());
        }catch(NullPointerException ex){

        }
        return rv;
    }

    public ReplaceRoomRecordVo findReplaceRoomRecord(String id)
    {
        ReplaceRoomRecordVo rrv=reportDao.findReplaceRoomRecord(id);
        try{
            if(rrv.getCurrentStatus()==MyConstant.CURRENT_STATUS_0){
                rrv.setCurrentStatusName("未下发");
            }else if(rrv.getCurrentStatus()==MyConstant.CURRENT_STATUS_0){
                rrv.setCurrentStatusName("下发成功");
            }else if(rrv.getCurrentStatus()==MyConstant.CURRENT_STATUS_0){
                rrv.setCurrentStatusName("下发失败");
            }else{
                rrv.setCurrentStatusName("其他");
            }
        }catch(Exception ex){

        }
        return rrv;
    }

    public WaterDailyVo findRoomDaily(String id)
    {
        WaterDailyVo wd=reportDao.findRoomDaily(id);
        return wd;
    }

    public ReplaceRoomRecordVo findReplaceNewRoomRecord(String id)
    {
        return reportDao.findReplaceNewRoomRecord(id);
    }

    public HistoryCommandVo findHistoryCommand(String id)
    {
        HistoryCommandVo hc=reportDao.findHistoryCommand(id);
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-历史命令");
        mol.setOperaContent("查看:["+hc.getAreaName()+"-"+hc.getApartmentName()+"-"+hc.getRoomNum()+"]历史命令");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
         return hc;
    }

    public HistoryRecord findNewReadMeter(String id)
    {
        HistoryRecord hr=reportDao.findNewReadMeter(id);
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-最新抄表");
        mol.setOperaContent("查看:["+ hr.getAreaName()+"-"+hr.getApartmentName()+"-"+hr.getRoomNum()+"]最新抄表记录");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
         return hr;
    }

    /**
     * 以日为单位每个水表房间定时生成日报
     * @return
     */
    public List<WaterDailyVo> getRoomWaterDaily(){
         return reportDao.getRoomWaterDaily();
    }

    /**
     * 向用水日报表中插入数据
     */
    public void insertDaily(){
        try{
        List<WaterMeterInfoVo> meterInfoList = reportDao.getMeterInfoList();
        List<WaterDailyVo> waterDailyVos = Lists.newArrayList();
        for(WaterMeterInfoVo item : meterInfoList){
            WaterDailyVo dailyVo = new WaterDailyVo();
            dailyVo.setId(UUIDUtils.getUUID());
            dailyVo.setWaterMeterInfoId(item.getId());
            //用水日期
            dailyVo.setUseTime(item.getUpdateTime());
            dailyVo.setSupplementWater(item.getSupplementWater());
            dailyVo.setBuyWaterTotal(item.getBuyWaterTotal());
            dailyVo.setCashBuyWater(item.getCashBuyWater());
            dailyVo.setCardBuyWater(item.getCardBuyWater());
            dailyVo.setReturnWater(item.getReturnWater());
            dailyVo.setUserWater(item.getUserWater());
            dailyVo.setSurplusWater(item.getSurplusWater());
            waterDailyVos.add(dailyVo);
        }

        reportDao.batchInsertDaily(waterDailyVos);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 向用水月报表中插入数据
     */
    public void insertMonth(List<WaterDailyVo> waterDailyVos) {
        for(WaterDailyVo wd:waterDailyVos){
            wd.setId(UUIDUtils.getUUID());
        }
        reportDao.insertMonth(waterDailyVos);
    }
}
