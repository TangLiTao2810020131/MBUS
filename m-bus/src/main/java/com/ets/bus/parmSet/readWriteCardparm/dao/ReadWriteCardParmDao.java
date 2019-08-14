package com.ets.bus.parmSet.readWriteCardparm.dao;

import com.ets.bus.parmSet.readWriteCardparm.entity.ReadWriteCardParm;

public interface ReadWriteCardParmDao {
    ReadWriteCardParm getreadWriteCardParmByVersionNum(String version_num);
    /**
     * 根据删除状态查询出读写卡参数
     * @param
     * @return
     */
    ReadWriteCardParm getParmByDelstatus();
    /**
     * 根据id得到读写参数
     * @param id
     * @return
     */
    ReadWriteCardParm getParmById(String id);
    /**
     * 根据id删除读写卡参数
     * @param id
     * @return
     */
    void UpdateParmById(String id);
    /**
     * 新增读写卡参数
     * @param
     * @return
     */
    void addParm(ReadWriteCardParm readWriteCardParm);
}
