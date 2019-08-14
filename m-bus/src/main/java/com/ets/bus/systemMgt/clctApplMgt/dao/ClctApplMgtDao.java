package com.ets.bus.systemMgt.clctApplMgt.dao;

import com.ets.bus.systemMgt.clctApplMgt.entity.ClctApplMgt;

import java.util.List;
import java.util.Map;

public interface ClctApplMgtDao
{
    List<ClctApplMgt> getClctApplMgts(Map<String, Object> map);

    long getCount(Map<String, Object> map);

    void addClctApplMgt(ClctApplMgt clctApplMgt);

    ClctApplMgt findClctApplMgtById(String id);

    void delClctApplMgts(String[] ids);

    void editClctApplMgt(ClctApplMgt clctApplMgt);

    String findInstruction(String id);

    List<ClctApplMgt> findClctApplMgts();

    ClctApplMgt checkApplicationNumber(String applicationNumber);

    ClctApplMgt checkApplicationName(String collectName);

    void updateClctApplMgtDelStatus(String[] ids);
}