package com.ets.bus.parmSet.sysRunningParm.dao;

import com.ets.bus.parmSet.sysRunningParm.entity.SysRunningParm;

public interface SysRunningParmDao {
    /**
     * 查出系统中唯一的系统运行参数
     * @param
     * @return
     */
    SysRunningParm info();
    /**
     * 根据id 得到对应的系统运行参数
     * @param id
     * @return
     */
    SysRunningParm getSysRunningParmById(String id);
    /**
     * 根据id 更新系统运行参数删除状态
     * @param id
     * @return
     */
    void updateSysRunningParmById(String id);
    /**
     * 新增系统运行参数
     * @param sysRunningParm
     * @return
     */
    void addSysRunningParm(SysRunningParm sysRunningParm);

    void insertDefult(String id);
}
