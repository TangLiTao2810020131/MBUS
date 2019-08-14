package com.ets.bus.reportQuery.service.waterresetrecord;

import com.ets.bus.reportQuery.dao.waterresetrecord.WaterResetRecordDao;
import com.ets.bus.reportQuery.entity.addwaterrecord.AddWaterRecord;
import com.ets.bus.reportQuery.entity.waterresetrecord.WaterResetRecord;
import com.ets.bus.reportQuery.service.buywaterrecord.BuyWaterRecordService;
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
public class WaterResetRecordService
{
    @Autowired
    WaterResetRecordDao waterResetRecordDao;
    @Autowired
    BuyWaterRecordService buyWaterRecordService;

    /**
     * 统一从request对象中获取参数信息，并封装到map集合中
     * @param request
     * @return
     */
    public Map<String, Object> getParamters(HttpServletRequest request)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("startTime",request.getParameter("startTime"));
        map.put("endTime",request.getParameter("endTime"));
        map.put("roomNum",request.getParameter("roomNum"));
        map.put("roomTypeId",request.getParameter("roomTypeId"));
        map.put("floorName",request.getParameter("floorName"));
        map.put("apartmentName",request.getParameter("apartmentName"));
        map.put("id",request.getParameter("id"));
        map.put("level",request.getParameter("level"));
        return buyWaterRecordService.getId(map,request);
    }

    /**
     * 获取所有的清零记录信息
     * @param map
     * @return
     */
    public List<WaterResetRecord> selectWaterResetRecord(Map<String, Object> map)
    {
        return waterResetRecordDao.selectWaterResetRecord(map);
    }

    /**
     * 清零记录信息导出
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
        List<WaterResetRecord> bList=selectWaterResetRecord(buyWaterRecordService.getId(map,request));
        //excel标题
        String[] title =str.split(",");

        String [][] content = new String[bList.size()][];
        for(int i=0;i<bList.size();i++)
        {
            content[i] = new String[title.length];
            content[i][0]=((WaterResetRecord)bList.get(i)).getAreaName();
            content[i][1]=((WaterResetRecord)bList.get(i)).getApartmentName();
            content[i][2]=((WaterResetRecord)bList.get(i)).getFloorName();
            content[i][3]=((WaterResetRecord)bList.get(i)).getRoomNum();
            content[i][4]=((WaterResetRecord)bList.get(i)).getUserWater().toString();
            content[i][5]=((WaterResetRecord)bList.get(i)).getSupplementWater().toString();
            content[i][6]=((WaterResetRecord)bList.get(i)).getBuyWaterTotal().toString();
            content[i][7]=((WaterResetRecord)bList.get(i)).getReturnWater().toString();
//            content[i][8]=((WaterResetRecord)bList.get(i)).getSurplusWater().toString();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        ExcelUtil.returnClient(response,fileName,wb);
    }

    /**
     * 通过清零记录表ID查询清零记录信息
     * @param id
     * @return
     */
    public WaterResetRecord findWaterReset(String id)
    {
        return waterResetRecordDao.findWaterReset(id);
    }
}