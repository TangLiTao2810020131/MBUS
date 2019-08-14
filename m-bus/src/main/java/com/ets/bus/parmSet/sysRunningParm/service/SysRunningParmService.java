package com.ets.bus.parmSet.sysRunningParm.service;

import com.ets.bus.parmSet.sysRunningParm.dao.SysRunningParmDao;
import com.ets.bus.parmSet.sysRunningParm.entity.SysRunningParm;
import com.ets.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class SysRunningParmService {
    @Resource
    SysRunningParmDao sysRunningParmDao;
    /**
     * 查出系统中唯一的系统运行参数
     * @param
     * @return
     */
    public SysRunningParm info(){
        SysRunningParm sysRunningParm = sysRunningParmDao.info();
        if(sysRunningParm == null){
            sysRunningParmDao.insertDefult(UUIDUtils.getUUID());
        }
        return sysRunningParmDao.info();
    }
    /**
     * 根据id 得到对应的系统运行参数
     * @param id
     * @return
     */
    public SysRunningParm getSysRunningParmById(String id){
        return sysRunningParmDao.getSysRunningParmById(id);
    }
    /**
     * 根据id 更新系统运行参数删除状态
     * @param id
     * @return
     */
    public void updateSysRunningParmById(String id){
        sysRunningParmDao.updateSysRunningParmById(id);
    }
    /**
     * 新增系统运行参数
     * @param sysRunningParm
     * @return
     */
    public void addSysRunningParm(SysRunningParm sysRunningParm){
        sysRunningParmDao.addSysRunningParm(sysRunningParm);
    }

}
