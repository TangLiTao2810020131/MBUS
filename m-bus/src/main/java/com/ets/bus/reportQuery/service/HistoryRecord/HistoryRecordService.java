package com.ets.bus.reportQuery.service.HistoryRecord;

import com.ets.bus.reportQuery.dao.historyrecord.HistoryRecordDao;
import com.ets.bus.reportQuery.entity.historyrecord.HistoryRecord;
import com.ets.bus.reportQuery.service.buywaterrecord.BuyWaterRecordService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HistoryRecordService
{
    @Autowired
    HistoryRecordDao historyRecordDao;
    @Autowired
    BuyWaterRecordService buyWaterRecordService;
    @Autowired
    OperationLogService operationLogService;

    /**
     * 统一从request中获取参数封装到map集合中
     * @param request
     * @return
     */
    public Map<String, Object> getRequestParams(HttpServletRequest request)
    {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("type",request.getParameter("type"));
        map.put("startTime",request.getParameter("startTime"));
        map.put("endTime",request.getParameter("endTime"));
        map.put("floor_id",request.getParameter("floor_id"));
        map.put("room_num",request.getParameter("room_num"));
        map.put("payer_name",request.getParameter("payer_name"));
        map.put("id",request.getParameter("id"));
        map.put("level",request.getParameter("level"));
        map.put("apartment_name",request.getParameter("apartment_name"));
        map.put("floor_name",request.getParameter("floor_name"));
        return buyWaterRecordService.getId(map,request);
    }

    /**
     * 查询所有的历史抄表记录
     * @param map
     * @return
     */
    public List<HistoryRecord> selectHistoryRecords(Map<String, Object> map)
    {
        return historyRecordDao.selectHistoryRecords(map);
    }

    /**
     * 通过历史抄表表ID查询历史抄表信息
     * @param id
     * @return
     */
    public HistoryRecord findHistoryRecordById(String id)
    {
        HistoryRecord hr=historyRecordDao.findHistoryRecordById(id);
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-历史抄表");
        mol.setOperaContent("查看:["+hr.getAreaName()+"-"+hr.getApartmentName()+"-"+hr.getRoomNum()+"]历史抄表记录");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return hr;
    }

    /**
     * 历史抄表信息导出
     * @param request
     * @param response
     * @param str
     * @param name
     */
    public void export(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        //excel文件名
        String fileName = name+".xls";

        //sheet名
        String sheetName =name;
        Map<String,Object> map=new HashMap<String,Object>();
        Enumeration<String> en= request.getParameterNames();
        while(en.hasMoreElements())
        {
            String key=en.nextElement();
            String value=request.getParameter(key);
            map.put(key,value);
        }
        List<HistoryRecord> bList=selectHistoryRecords(buyWaterRecordService.getId(map,request));
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
            content[i][6]=((HistoryRecord)bList.get(i)).getCurrentStatusName();
            content[i][7]=((HistoryRecord)bList.get(i)).getCreateTime();
            content[i][8]=((HistoryRecord)bList.get(i)).getBuyWaterTotal().toString();
            content[i][9]=((HistoryRecord)bList.get(i)).getSupplementWater().toString();
            content[i][10]=((HistoryRecord)bList.get(i)).getOverWater().toString();
            content[i][11]=((HistoryRecord)bList.get(i)).getUserWater().toString();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        ExcelUtil.returnClient(response,fileName,wb);

        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-历史抄表");
        mol.setOperaContent("导出历史抄表信息");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
    }
}