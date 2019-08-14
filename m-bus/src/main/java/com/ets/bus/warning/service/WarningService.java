package com.ets.bus.warning.service;

import com.ets.bus.reportQuery.service.buywaterrecord.BuyWaterRecordService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.warning.dao.WarningDao;
import com.ets.bus.warning.entity.Warning;
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
public class WarningService
{
    @Autowired
    WarningDao cellWarningDao;

    @Autowired
    BuyWaterRecordService buyWaterRecordService;
    @Autowired
    OperationLogService operationLogService;

    /**
     * 统一从request中获取参数信息,并封装到map集合中
     * @param request
     * @return
     */
    public Map<String,Object> getRequestParameter(HttpServletRequest request)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("startTime",request.getParameter("startTime"));
        map.put("endTime",request.getParameter("endTime"));
        map.put("type",request.getParameter("type"));
        map.put("flag",request.getParameter("flag"));
        map.put("id",request.getParameter("id"));
        map.put("level",request.getParameter("level"));
        return buyWaterRecordService.getId(map,request);
    }

    /**
     * 查询所有的报警记录信息
     * @param map
     * @return
     */
    public List<Warning> selectAllCell(Map<String, Object> map)
    {
        return cellWarningDao.selectAllCell(map);
    }

    /**
     * 通过ID查询报警记录信息
     * @param id
     * @return
     */
    public Warning findWarningById(String id)
    {
        Warning warning=cellWarningDao.findWarningById(id);
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-报警记录");
        mol.setOperaContent("查看:["+warning.getAreaName()+"-"+warning.getApartmentName()+"-"+warning.getRoomNum()+"]报警记录信息");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return warning;
    }

    /**
     * 报警信息导出
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
        List<Warning> bList=selectAllCell(buyWaterRecordService.getId(map,request));
        //excel标题
        String[] title =str.split(",");

        String [][] content = new String[bList.size()][];
        for(int i=0;i<bList.size();i++)
        {
            content[i] = new String[title.length];
            content[i][0]=((Warning)bList.get(i)).getAreaName();
            content[i][1]=((Warning)bList.get(i)).getApartmentName();
            content[i][2]=((Warning)bList.get(i)).getBuildName();
            content[i][3]=((Warning)bList.get(i)).getFloorName();
            content[i][4]=((Warning)bList.get(i)).getRoomNum();
            content[i][5]=((Warning)bList.get(i)).getWaterNum();
            content[i][6]=((Warning)bList.get(i)).getIpAddress();
            content[i][7]=((Warning)bList.get(i)).getWarningTime();
            content[i][8]=((Warning)bList.get(i)).getTypeName();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        ExcelUtil.returnClient(response,fileName,wb);

        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-报警记录");
        mol.setOperaContent("报警记录信息导出");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
    }
}