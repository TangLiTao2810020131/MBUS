package com.ets.bus.parmSet.readWriteCardparm.dao;

import com.ets.bus.parmSet.readWriteCardparm.entity.ParamCardVersion;

public interface ParamCardVersionDao {

    ParamCardVersion getParamCard();

    void insertDefultVersion(String id);
}
