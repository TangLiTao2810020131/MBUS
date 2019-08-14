package com.ets.bus.reportQuery.service.buywaterrecord;

import com.ets.bus.reportQuery.dao.buywaterrecord.BuyWaterRecordDao;
import com.ets.bus.reportQuery.entity.buywaterrecord.BuyWaterRecord;
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
public class BuyWaterRecordService
{
    @Autowired
    BuyWaterRecordDao buyWaterRecordDao;

    //购水类型常量
    private final static int BUY_WATER_0=0;//现金
    private final static int BUY_WATER_1=1;//一卡通
    private final static int BUY_WATER_2=2;//微信
    private final static int BUY_WATER_3=3;//终端
    private final static int BUY_WATER_4=4;//客户端

    //购水操作状态常量
    private final static int CURRENT_STATUS_0=0;//未下发
    private final static int CURRENT_STATUS_1=1;//下发成功
    private final static int CURRENT_STATUS_2=2;//下发失败

    /**
     * 统一从request对象中获取参数信息，并封装到map集合中
     * @param request
     * @return
     */
    public Map<String,Object> getParameters(HttpServletRequest request)
    {
        Map<String,Object> map=new HashMap<String,Object>();
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
        return this.getId(map,request);
    }

    /**
     * 根据前台传递的参数（level)判断树节点的级别
     * @param map
     * @param request
     * @return
     */
    public Map<String,Object> getId(Map<String,Object> map,HttpServletRequest request)
    {
        if(map.get("level")!=null)
        {
            if(map.get("level").equals("2")){
                map.put("area_id",request.getParameter(("id")));
            }else if(map.get("level").equals("3")){
                map.put("apartment_id",request.getParameter(("id")));
            }else if(map.get("level").equals("4")){
                map.put("build_id",request.getParameter(("id")));
            }else if(map.get("level").equals("5")){
                map.put("floor_id",request.getParameter(("id")));
            }else if(map.get("level").equals("6")){
                map.put("room_id",request.getParameter(("id")));
            }
        }
        return map;
    }

    /**
     * 获取所有的购水记录信息
     * @param map
     * @return
     */
    public List<BuyWaterRecord> getBuyWaterRecords(Map<String, Object> map)
    {
        List<BuyWaterRecord> list=buyWaterRecordDao.getBuyWaterRecords(map);
        for(BuyWaterRecord bw:list)
        {
            if(bw.getCurrent_status()==CURRENT_STATUS_0){
                bw.setCurrent_status_name("未下发");
            }else  if(bw.getCurrent_status()==CURRENT_STATUS_1){
                bw.setCurrent_status_name("下发成功");
            }else if(bw.getCurrent_status()==CURRENT_STATUS_2){
                bw.setCurrent_status_name("下发失败");
            }else{
                 bw.setCurrent_status_name("其他");
            }
        }
        return list;
    }

    /**
     * 购水记录导出
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

        List<BuyWaterRecord> bList=getBuyWaterRecords(getId(map,request));
        //excel标题
        String[] title =str.split(",");
        String [][] content = new String[bList.size()][];
        for(int i=0;i<bList.size();i++)
        {
            content[i] = new String[title.length];
            content[i][0]=((BuyWaterRecord)bList.get(i)).getArea_name();
            content[i][1]=((BuyWaterRecord)bList.get(i)).getApartment_name();
            content[i][2]=((BuyWaterRecord)bList.get(i)).getFloor_name();
            content[i][3]=((BuyWaterRecord)bList.get(i)).getRoom_num();
            content[i][4]=((BuyWaterRecord)bList.get(i)).getCurrent_status_name();
            content[i][5]=((BuyWaterRecord)bList.get(i)).getCreate_time();
            content[i][6]=((BuyWaterRecord)bList.get(i)).getPayer_name();
            content[i][7]=((BuyWaterRecord)bList.get(i)).getBuy_money()+"";
            content[i][8]=((BuyWaterRecord)bList.get(i)).getBuy_water()+"";
            content[i][9]=((BuyWaterRecord)bList.get(i)).getUser_name();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        ExcelUtil.returnClient(response,fileName,wb);
    }

    /**
     * 根据购水表id查询购水信息
     * @param id
     * @return
     */
    public BuyWaterRecord findBuyWaterRecordById(String id)
    {
        BuyWaterRecord bw=buyWaterRecordDao.findBuyWaterRecordById(id);
        if(bw.getRoom_type_name()==null){
            bw.setRoom_type_name("--");
        }
        return bw;
    }

    /**
     * 设置购水类型
     * @param bw
     * @return
     */
    public BuyWaterRecord setBuyWaterType(BuyWaterRecord bw)
    {
            try{
                if (bw.getType() == BUY_WATER_0) {
                    bw.setType_name("现金");
                } else if (bw.getType() == BUY_WATER_1) {
                    bw.setType_name("一卡通");
                } else if (bw.getType() == BUY_WATER_2) {
                    bw.setType_name("微信");
                } else if (bw.getType() == BUY_WATER_3) {
                    bw.setType_name("终端");
                } else if (bw.getType() == BUY_WATER_4) {
                    bw.setType_name("客户端");
                } else if (bw.getType() == BUY_WATER_1) {
                    bw.setType_name("其他");
                }
            }catch(NullPointerException ex){

            }
        return bw;
    }
}