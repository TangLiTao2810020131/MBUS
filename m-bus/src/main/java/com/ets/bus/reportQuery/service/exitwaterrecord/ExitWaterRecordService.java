package com.ets.bus.reportQuery.service.exitwaterrecord;

import com.ets.bus.reportQuery.dao.exitwaterrecord.ExitWaterRecordDao;
import com.ets.bus.reportQuery.entity.exitwaterrecord.ExitWaterRecord;
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
public class ExitWaterRecordService
{
    @Autowired
    ExitWaterRecordDao exitWaterRecordDao;
    @Autowired
    BuyWaterRecordService buyWaterRecordService;

    //退水操作状态常量
    private final static int RETURN_CURRENT_STATUS_0=0;//未下发
    private final static int RETURN_CURRENT_STATUS_1=1;//下发成功
    private final static int RETURN_CURRENT_STATUS_2=2;//下发失败

    //退水类型常量
    private final static int REYUTN_TYPE_0=0;//退水
    private final static int REYUTN_TYPE_1=1;//换房退水

    /**
     * 统一从request中获取参数信息，并封装到map集合中
     * @param request
     * @return
     */
    public Map<String,Object> getParamters(HttpServletRequest request)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("room_num",request.getParameter("room_num"));
        map.put("startTime",request.getParameter("startTime"));
        map.put("endTime",request.getParameter("endTime"));
        map.put("room_type_id",request.getParameter("room_type_id"));
        map.put("apartment_name",request.getParameter("apartment_name"));
        map.put("floor_name",request.getParameter("floor_name"));
        map.put("id",request.getParameter("id"));
        map.put("level",request.getParameter("level"));
        return buyWaterRecordService.getId(map,request);
    }

    /**
     * 获取所有的退水记录信息
     * @param map
     * @return
     */
    public List<ExitWaterRecord> getExitWaterRecords(Map<String, Object> map)
    {
        List<ExitWaterRecord> list=exitWaterRecordDao.getExitWaterRecords(map);
        for(ExitWaterRecord ew:list){
            if(ew.getCurrent_status()==RETURN_CURRENT_STATUS_0){
                ew.setCurrent_status_name("未下发");
            }else  if(ew.getCurrent_status()==RETURN_CURRENT_STATUS_1){
                ew.setCurrent_status_name("下发成功");
            }else  if(ew.getCurrent_status()==RETURN_CURRENT_STATUS_2){
                ew.setCurrent_status_name("下发失败");
            }
        }
        return list;
    }

    /**
     * 退水记录信息导出
     * @param request
     * @param response
     * @param str
     * @param name
     */
    public void export(HttpServletRequest request, HttpServletResponse response, String str,String name)
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
        List<ExitWaterRecord> bList=getExitWaterRecords(buyWaterRecordService.getId(map,request));
        //excel标题
        String[] title =str.split(",");

        String [][] content = new String[bList.size()][];

        for(int i=0;i<bList.size();i++)
        {
            content[i] = new String[title.length];
            content[i][0]=((ExitWaterRecord)bList.get(i)).getArea_name();
            content[i][1]=((ExitWaterRecord)bList.get(i)).getApartment_name();
            content[i][2]=((ExitWaterRecord)bList.get(i)).getFloor_name();
            content[i][3]=((ExitWaterRecord)bList.get(i)).getRoom_num();
            content[i][4]=((ExitWaterRecord)bList.get(i)).getCurrent_status_name();
            content[i][5]=((ExitWaterRecord)bList.get(i)).getCreate_time();
            content[i][6]=((ExitWaterRecord)bList.get(i)).getReturn_money()+"";
            content[i][7]=((ExitWaterRecord)bList.get(i)).getReturn_water()+"";
            content[i][8]=((ExitWaterRecord)bList.get(i)).getUser_name();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        ExcelUtil.returnClient(response,fileName,wb);
    }

    /**
     * 通过退水记录表的ID查询退水记录详细信息
     * @param id
     * @return
     */
    public ExitWaterRecord findExitRecord(String id)
    {
        ExitWaterRecord ew=exitWaterRecordDao.findExitRecord(id);
        if(ew.getCurrent_status()==RETURN_CURRENT_STATUS_0){
            ew.setCurrent_status_name("未下发");
        }else  if(ew.getCurrent_status()==RETURN_CURRENT_STATUS_0){
            ew.setCurrent_status_name("下发成功");
        }else  if(ew.getCurrent_status()==RETURN_CURRENT_STATUS_0){
            ew.setCurrent_status_name("下发失败");
        }else  if(ew.getCurrent_status()==RETURN_CURRENT_STATUS_0){
            ew.setCurrent_status_name("其他");
        }

        if(ew.getType()==REYUTN_TYPE_0){
            ew.setType_name("退水");
        }else  if(ew.getType()==REYUTN_TYPE_1){
            ew.setType_name("换房退水");
        }else{
            ew.setType_name("其他");
        }
        return ew;
    }
}