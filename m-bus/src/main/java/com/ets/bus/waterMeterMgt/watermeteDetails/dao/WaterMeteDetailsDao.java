package com.ets.bus.waterMeterMgt.watermeteDetails.dao;

import com.ets.bus.reportQuery.entity.report.RedRushVo;
import com.ets.bus.waterMeterMgt.watermeteDetails.entity.TransactionDetailsVo;

import java.util.List;

/**
 * @author 宋晨
 * @create 2019/3/28
 * 水表交易明细Dao
 */
public interface WaterMeteDetailsDao {

    /**
     * 查询水表交易记录集合
     * @param transDetailParam
     * @return
     */
    List<TransactionDetailsVo> selectTransactionDetailsList(TransactionDetailsVo transDetailParam);

    /**
     * 查询水表交易记录集合
     * @param transDetailParam
     * @return
     */
    Integer selectTransactionDetailsCount(TransactionDetailsVo transDetailParam);

    /**
     * 查询购水记录集合
     * @param transDetailParam
     * @return
     */
    List<TransactionDetailsVo> selectBuyRecordList(TransactionDetailsVo transDetailParam);

    /**
     * 查询补水记录集合
     * @param transDetailParam
     * @return
     */
    List<TransactionDetailsVo> selectSupplementRecordList(TransactionDetailsVo transDetailParam);

    /**
     * 查询退水记录集合
     * @param transDetailParam
     * @return
     */
    List<TransactionDetailsVo> selectReturnRecordList(TransactionDetailsVo transDetailParam);

    /**
     * 查询清零记录集合
     * @param transDetailParam
     * @return
     */
    List<TransactionDetailsVo> selectClearRecordList(TransactionDetailsVo transDetailParam);

    /**
     * 查询冲红记录集合
     * @param transDetailParam
     * @return
     */
    List<TransactionDetailsVo> selectRedrushRecordList(TransactionDetailsVo transDetailParam);

    /**
     * 获取补水记录信息
     * @param id
     * @return
     */
    TransactionDetailsVo selectSupplementRecordById(String id);

    /**
     * 获取退水记录信息
     * @param id
     * @return
     */
    TransactionDetailsVo selectReturnRecordById(String id);

    /**
     * 冲红记录表插入一条冲红记录
     * @param redRushVo
     */
    void insertRedRushVo(RedRushVo redRushVo);

    TransactionDetailsVo findWaterMeterDetails(String id);
}
