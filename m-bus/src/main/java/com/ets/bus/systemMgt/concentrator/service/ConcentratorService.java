package com.ets.bus.systemMgt.concentrator.service;

import com.ets.bus.systemMgt.concentrator.dao.ConcentratorDao;
import com.ets.bus.systemMgt.concentrator.entity.Concentrator;
import com.ets.bus.systemMgt.concentrator.entity.RoomWaterInfo;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.utils.ExcelUtil;
import com.ets.utils.UUIDUtils;
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
public class ConcentratorService
{
    @Autowired
    ConcentratorDao concentratorDao;
    @Autowired
    OperationLogService operationLogService;

    public List<Concentrator> getConcentrators(Map<String, Object> map)
    {
        List<Concentrator> concentrator=concentratorDao.getConcentrators(map);
        for(Concentrator c:concentrator){
            if(c.getCommunication_mode().equals("0")){
                c.setCommunication_mode_name("TCP");
            }else{
                c.setCommunication_mode("其他");
            }
        }
        return  concentrator;
    }

    public void delConcentrators(String[] ids)
    {
        //通过集中器ID查询集中器信息
        List<Concentrator> cList=concentratorDao.getConcentratorByConcentratorId(ids);
        String str="";
        for(Concentrator c:cList){
            str+=c.getConcentrator_num()+",";
        }
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-集中器管理-设置集中器");
        mol.setOperaContent("删除编号为:["+str.substring(0,str.length()-1)+"]的集中器");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        concentratorDao.delConcentrators(ids);
    }

    public void addConcentrator(Concentrator concentrator)
    {
        concentrator.setId(UUIDUtils.getUUID());
        concentrator.setUpdate_time(concentrator.getCreate_time());
        concentratorDao.addConcentrator(concentrator);

        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-集中器管理-设置集中器");
        mol.setOperaContent("新增编号为:["+concentrator.getConcentrator_num()+"]集中器");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
    }

    public void editConcentrator(Concentrator concentrator)
    {
        concentratorDao.editConcentrator(concentrator);
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-集中器管理-设置集中器");
        mol.setOperaContent("编辑编号为:["+concentrator.getConcentrator_num()+"]集中器,编辑时间:["+concentrator.getUpdate_time()+"]");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
    }

    public Concentrator findConcentrator(String id)
    {
        Concentrator concentrator=concentratorDao.findConcentrator(id);
        if(concentrator.getCommunication_mode().equals("0")){
            concentrator.setCommunication_mode_name("TCP");
        }else{
            concentrator.setCommunication_mode_name("其他");
        }
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-集中器管理-设置集中器");
        mol.setOperaContent("查看编号为:["+concentrator.getConcentrator_num()+"]集中器详细信息");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return concentrator;
    }

    private List<Concentrator> findConcentrators(Map<String, Object> map)
    {
        return concentratorDao.findConcentrators(map);
    }

    public List<RoomWaterInfo> findRoomInfo(Map<String, Object> map)
    {
        return concentratorDao.findRoomInfo(map);
    }

    public void addRooomConcentrator(Map<String, Object> map)
    {
        concentratorDao.addRooomConcentrator(map);
        //通过水表房间ID查询水表房间信息
        String[] ids=(String[])map.get("ids");
        String str="";
        List<RoomWaterInfo> rList=concentratorDao.findWaterRoomById(ids);
        for(RoomWaterInfo rw:rList){
            str+="["+rw.getArea_name()+"-"+rw.getApartment_name()+"-"+rw.getRoom_num()+"]";
        }
        //通过集中器ID查询集中器信息
        Concentrator c=concentratorDao.findConcentrator((String)map.get("concentratorId"));
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-集中器管理-设置集中器");
        mol.setOperaContent("为编号为:["+c.getConcentrator_num()+"]集中器绑定["+str+"]房间");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
    }

    public Concentrator checkConcentratorIp(String ipAddress)
    {
        return concentratorDao.checkConcentratorIp(ipAddress);
    }

    //数据表格导出
    public void export(HttpServletRequest request, HttpServletResponse response, String str,String name)
    {
        //excel标题
        String[] title =str.split(",");

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
        List<Concentrator> bList=findConcentrators(map);
        for(Concentrator c:bList){
            if(c.getCommunication_mode().equals("0")){
                c.setCommunication_mode_name("TCP");
            }else{
                c.setCommunication_mode("其他");
            }
        }
        String [][] content = new String[bList.size()][];
        for(int i=0;i<bList.size();i++)
        {
            content[i] = new String[title.length];
            content[i][0]=((Concentrator)bList.get(i)).getConcentrator_num();
            content[i][1]=((Concentrator)bList.get(i)).getIp_address();
            content[i][2]=((Concentrator)bList.get(i)).getCommunication_mode_name();
            content[i][3]=((Concentrator)bList.get(i)).getConcentrator_version();
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        ExcelUtil.returnClient(response,fileName,wb);

        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-集中器管理-设置集中器");
        mol.setOperaContent("导出集中器信息");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
    }

    //根据集中器id查询房间信息
    public List<RoomWaterInfo> findRoomByCobcentratorId(String concentratorId)
    {
        return concentratorDao.findRoomByCobcentratorId(concentratorId);
    }

    public List<RoomWaterInfo> findRoomInfoNotConcentrator(Map<String, Object> map)
    {
        return concentratorDao.findRoomInfoNotConcentrator(map);
    }

    public void clearRoomConcentrator(String[] ids)
    {
        //通过水表房间ID查询水表房间信息
        List<RoomWaterInfo> rList=concentratorDao.findWaterRoomById(ids);
        String str="";
        for(RoomWaterInfo rw:rList){
            str+="["+rw.getArea_name()+"-"+rw.getApartment_name()+"-"+rw.getRoom_num()+"]";
        }
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-集中器管理-设置集中器");
        mol.setOperaContent("解除房间:"+str+"绑定");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        concentratorDao.clearRoomConcentrator(ids);
    }

    public List<Concentrator> findConcentratorByCollectId(String[] ids)
    {
        return concentratorDao.findConcentratorByCollectId(ids);
    }

    public List<Concentrator> findConcentratorById(String[] ids)
    {
        return concentratorDao.getConcentratorByConcentratorId(ids);
    }
}