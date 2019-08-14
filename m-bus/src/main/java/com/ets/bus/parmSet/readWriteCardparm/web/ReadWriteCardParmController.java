package com.ets.bus.parmSet.readWriteCardparm.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ets.bus.parmSet.readWriteCardparm.entity.ParamCardVersion;
import com.ets.bus.parmSet.readWriteCardparm.entity.ReadWriteCardParm;
import com.ets.bus.parmSet.readWriteCardparm.service.ParamCardVersionService;
import com.ets.bus.parmSet.readWriteCardparm.service.ReadWriteCardParmServicer;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.Message;
import com.ets.utils.UUIDUtils;
import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * @author 吴浩
 * @create 2019-01-08 14:04
 */
@Controller
@RequestMapping("readWritecard")
public class ReadWriteCardParmController {
    @Resource
    ParamCardVersionService paramCardVersionService;
    @Resource
    ReadWriteCardParmServicer readWriteCardParmServicer;
    @Autowired
    OperationLogService operationLogService;
    /**
     * 查询系统中读写卡参数
     * @param request
     * @return
     */
    @RequestMapping("readWriteCardParm")
    public String readWriteCardParm(HttpServletRequest request) {
        //查出参数卡版本，需要读取数据库字典，目前参数卡版本在字典中只有一条记录
        ParamCardVersion paramCard = paramCardVersionService.getParamCard();
        //若库中没有合法读写卡参数，则直接显示该版本号
        if (!paramCard.getLawful_status().equals("0")) {
            request.setAttribute("paramCard", paramCard);
        } else{
            //查出读写参数表中的没有删除的数据参数
            ReadWriteCardParm readWriteCardParm = readWriteCardParmServicer.getParmByDelstatus();
            request.setAttribute("readWriteCardParm",readWriteCardParm);
            request.setAttribute("paramCard",paramCard);
        }


            /*ReadWriteCardParm readWriteCardParm = readWriteCardParmServicer.getreadWriteCardParmByVersionNum(paramCard.getVersion_num());*/
    /*   else if (readWriteCardParm.getVersion_num().equals(paramCard.getVersion_num())){

            request.setAttribute("readWriteCardParm",readWriteCardParm);
            request.setAttribute("paramCard",paramCard);
        }else{

            request.setAttribute("readWriteCardParm",readWriteCardParm);
            request.setAttribute("paramCard",paramCard);
        }*/
            return "bus/parm-set/read-writeCard-parm/read-writeCard-parm";
    }

    /**
     * 先比较该版本号与配置的版本号是否一致，若一致则直接加载该条读写卡参数，若不一致则弹出提示，即“版本号发生改变，请重新配置参数”
     * @param readWriteCardParm
     * @param pversion_num
     * @return
     */
    @RequestMapping(value="isCheckParmVersionNum", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String isCheckParmVersionNum(ReadWriteCardParm readWriteCardParm,String pversion_num){
        Gson gson=new Gson();
        ReadWriteCardParm parm = readWriteCardParmServicer.getParmById(readWriteCardParm.getId());
        if (!parm.getVersion_num().equals(pversion_num)){
         return gson.toJson(new Message("2","版本号发生改变，请重新配置参数！"));
        }
        return gson.toJson(new Message("1","版本号未发生改变！"));
    }
    /**
     * 保存编辑的读写卡参数
     * @param readWriteCardParm
     * @param pversion_num
     * @return
     */
    @RequestMapping(value="saveReadWritecardparm", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String saveReadWritecardparm(ReadWriteCardParm readWriteCardParm,String pversion_num){
        Gson gson=new Gson();
        //新增之前更新原先数据的del_status字段为1
         if (!readWriteCardParm.equals("")){
             readWriteCardParmServicer.UpdateParmById(readWriteCardParm.getId());
         }
         //执行新增读卡参数操作
         readWriteCardParm.setId(UUIDUtils.getUUID());
         readWriteCardParm.setCreate_time(DateTimeUtils.getnowdate());
         readWriteCardParm.setDel_status("0");
         readWriteCardParm.setVersion_num(pversion_num);
         readWriteCardParmServicer.addParm(readWriteCardParm);
         //添加编写读写卡参数的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("参数设置-读写卡参数");
        operationLog.setOperaContent("编辑读写卡参数");
        operationLogService.addLog(operationLog);
        return  gson.toJson(new Message("1","操作成功"));
    }


}