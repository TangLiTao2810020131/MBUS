package com.ets.bus.warning.dao;

import com.ets.bus.warning.entity.Warning;

import java.util.List;
import java.util.Map;

public interface WarningDao
{
    /**
     * 获取所有的报警记录信息
     * @param map
     * @return
     */
    List<Warning> selectAllCell(Map<String, Object> map);

    /**
     * 通过报警记录ID查询报警记录信息
     * @param id
     * @return
     */
    Warning findWarningById(String id);
}