package com.ets.bus.parmSet.readWriteCardparm.service;

import com.ets.bus.parmSet.readWriteCardparm.dao.ParamCardVersionDao;
import com.ets.bus.parmSet.readWriteCardparm.entity.ParamCardVersion;
import com.ets.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class ParamCardVersionService {
    @Resource
    ParamCardVersionDao paramCardVersionDao;
    /**
     * 获取合法的读写卡参数
     * @param
     * @return
     */
    public ParamCardVersion getParamCard(){
        ParamCardVersion paramCardVersion = paramCardVersionDao.getParamCard();
        if(paramCardVersion == null){
            paramCardVersionDao.insertDefultVersion(UUIDUtils.getUUID());
            paramCardVersion = paramCardVersionDao.getParamCard();
        }
        return  paramCardVersion;
    }




}
