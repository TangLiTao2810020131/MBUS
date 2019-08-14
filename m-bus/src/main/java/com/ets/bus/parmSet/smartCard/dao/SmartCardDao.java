package com.ets.bus.parmSet.smartCard.dao;

import com.ets.bus.parmSet.smartCard.entity.CardTerminalVo;

import java.util.List;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/4/15
 * 一卡通终端
 */
public interface SmartCardDao {

    /**
     * 查询一卡通终端集合
     * @param param
     * @return
     */
    List<CardTerminalVo> getCardTerminalList(Map<String, Object> param);

    /**
     * 添加一卡通终端
     * @param ctv
     */
    void addSmartCard(CardTerminalVo ctv);

    /**
     * 更新一卡通终端删除状态
     * @param ids
     */
    void updatedelStatus(String[] ids);

    /**
     * 编辑一卡通终端
     * @param cv
     */
    void editSmartCard(CardTerminalVo cv);

    /**
     * 更新一卡通终端启用状态
     * @param ids
     */
    void updateStartStatus(String[] ids);

    /**
     * 更新一卡通终端禁用状态
     * @param ids
     */
    void updateEndStatus(String[] ids);

    /**
     * 通过终端编号查询一卡通终端信息
     * @param terminalNum
     * @return
     */
    CardTerminalVo checkTerminalNum(String terminalNum);

    /**
     * 通过一卡通IP地址查询一卡通终端
     * @param terminalAddress
     * @return
     */
    CardTerminalVo checkTerminalAddress(String terminalAddress);

    /**
     * 通过一卡通终端ID查询一卡通终端信息
     * @param id
     * @return
     */
    CardTerminalVo findCardTerminalVoById(String id);

}
