package com.ets.bus.parmSet.readWriteCardparm.service;

import com.ets.bus.parmSet.readWriteCardparm.dao.ReadWriteCardParmDao;
import com.ets.bus.parmSet.readWriteCardparm.entity.ReadWriteCardParm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class ReadWriteCardParmServicer {
    @Resource
    ReadWriteCardParmDao readWriteCardParmDao;

    public ReadWriteCardParm getreadWriteCardParmByVersionNum(String version_num){
         return readWriteCardParmDao.getreadWriteCardParmByVersionNum(version_num);
    }
    /**
     * 根据删除状态查询出读写卡参数
     * @param
     * @return
     */
    public ReadWriteCardParm getParmByDelstatus(){
        return readWriteCardParmDao.getParmByDelstatus();
    }
    /**
     * 根据id得到读写参数
     * @param id
     * @return
     */
    public ReadWriteCardParm getParmById(String id){
        return readWriteCardParmDao.getParmById(id);
    }
    /**
     * 根据id删除读写卡参数
     * @param id
     * @return
     */
    public void UpdateParmById(String id){
        readWriteCardParmDao.UpdateParmById(id);
    }
    /**
     * 新增读写卡参数
     * @param
     * @return
     */
    public void addParm(ReadWriteCardParm readWriteCardParm){
        readWriteCardParmDao.addParm(readWriteCardParm);
    }
}
