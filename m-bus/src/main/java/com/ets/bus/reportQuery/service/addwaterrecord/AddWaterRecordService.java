package com.ets.bus.reportQuery.service.addwaterrecord;

import com.ets.bus.reportQuery.dao.addwaterrecord.AddWaterRecordDao;
import com.ets.bus.reportQuery.entity.addwaterrecord.AddWaterRecord;
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
public class AddWaterRecordService
{
    @Autowired
    AddWaterRecordDao addWaterRecordDao;
    @Autowired
    BuyWaterRecordService buyWaterRecordService;

    //补水操作状态
    private final static int SUPPLEMENT_STATUS_0=0;//未下发
    private final static int SUPPLEMENT_STATUS_1=1;//下发成功
    private final static int SUPPLEMENT_STATUS_2=2;//下发失败
    //补水类型常量
    private final static int SUPPLEMENT_TYPE_0=0;//房间补水
    private final static int SUPPLEMENT_TYPE_1=1;//楼层补水
    private final static int SUPPLEMENT_TYPE_2=2;//导入补水
    private final static int SUPPLEMENT_TYPE_3=3;//换房补水

    /**
     * 统一从request中获取参数信息，并封装到map集合中
     * @param request
     * @return
     */
    public Map<String,Object> getParamters(HttpServletRequest request)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("startTime",request.getParameter("startTime"));
        map.put("endTime",request.getParameter("endTime"));
        map.put("room_num",request.getParameter("room_num"));
        map.put("room_type_id",request.getParameter("room_type_id"));
        map.put("floor_name",request.getParameter("floor_name"));
        map.put("apartment_name",request.getParameter("apartment_name"));
        map.put("id",request.getParameter("id"));
        map.put("level",request.getParameter("level"));
        return buyWaterRecordService.getId(map,request);
    }

    /**
     * 获取所有的补水记录信息
     * @param map
     * @return
     */
    public List<AddWaterRecord> getAddWaterRecords(Map<String, Object> map)
    {
        List<AddWaterRecord> aList= addWaterRecordDao.getAddWaterRecords(map);
        for(AddWaterRecord aw:aList)
        {
            if(aw.getCurrent_status()==SUPPLEMENT_STATUS_0){
                aw.setCurrent_status_name("未下发");
            }else  if(aw.getCurrent_status()==SUPPLEMENT_STATUS_1){
                aw.setCurrent_status_name("下发成功");
            }else  if(aw.getCurrent_status()==SUPPLEMENT_STATUS_2){
                aw.setCurrent_status_name("下发失败");
            }else{
                aw.setCurrent_status_name("未知");
            }
        }
        return aList;
    }

    /**
     * 补水记录表格数据导出
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
        List<AddWaterRecord> bList=getAddWaterRecords(buyWaterRecordService.getId(map,request));
        //excel标题
        String[] title =str.split(",");

        String [][] content = new String[bList.size()][];
        for(int i=0;i<bList.size();i++)
        {
            content[i] = new String[title.length];
            content[i][0]=((AddWaterRecord)bList.get(i)).getArea_name();
            content[i][1]=((AddWaterRecord)bList.get(i)).getApartment_name();
            content[i][2]=((AddWaterRecord)bList.get(i)).getFloor_name();
            content[i][3]=((AddWaterRecord)bList.get(i)).getRoom_num();
            content[i][4]=((AddWaterRecord)bList.get(i)).getCurrent_status_name();
            content[i][5]=((AddWaterRecord)bList.get(i)).getCreate_time();
            content[i][6]=((AddWaterRecord)bList.get(i)).getSupplement_water();
            content[i][7]=((AddWaterRecord)bList.get(i)).getUser_name();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        ExcelUtil.returnClient(response,fileName,wb);
    }

    /**
     * 通过补水记录表的ID查询补水记录信息
     * @param id
     * @return
     */
    public AddWaterRecord findAddRecord(String id)
    {
        AddWaterRecord aw=addWaterRecordDao.findAddRecord(id);
        if(aw.getCurrent_status()==SUPPLEMENT_STATUS_0){
            aw.setCurrent_status_name("未下发");
        }else  if(aw.getCurrent_status()==SUPPLEMENT_STATUS_1){
            aw.setCurrent_status_name("下发成功");
        }else  if(aw.getCurrent_status()==SUPPLEMENT_STATUS_2){
            aw.setCurrent_status_name("下发失败");
        }else{
            aw.setCurrent_status_name("未知");
        }

        if(aw.getType()==SUPPLEMENT_TYPE_0){
            aw.setType_name("房间补水");
        }else if(aw.getType()==SUPPLEMENT_TYPE_1){
            aw.setType_name("楼层补水");
        }else if(aw.getType()==SUPPLEMENT_TYPE_2){
            aw.setType_name("导入补水");
        }else if(aw.getType()==SUPPLEMENT_TYPE_3){
            aw.setType_name("换房补水");
        }
        return aw;
    }
}