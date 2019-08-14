package com.ets.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 宋晨
 * @create 2019/3/27
 * 常量类
 */
public class MyConstant {

    /**********************************水表类型常量**********************************/

    public final static String WATER_TYPE_0="0";//冷水水表
    public final static String WATER_TYPE_1="1";//生活热水表

    /********************************** 分页START **********************************/
    public static final String PAGE_KEY = "page"; //页数关键字
    public static final int PAGE_DEFULT = 1;    //默认页数
    public static final String LIMIT_KEY = "limit"; //一页显示条数关键字
    public static final int LIMIT_DEFULT = 10;  //默认一页显示条数

    /********************************** 分页END **********************************/

    /********************************** 状态常量START **********************************/

    //初始化状态MAP
    public static final Integer INIT_STATUS_0 = 0; //未初始化
    public static final Integer INIT_STATUS_1 = 1; //已初始化
    public static final Integer INIT_STATUS_2 = 2; //清零初始化
    public static final Map<Integer, String> INIT_STATUS_MAP = new HashMap<Integer, String>(){{
        put(0, "未初始化");
        put(1, "已初始化");
        put(2, "清零初始化");
    }};


    //阀门状态MAP
    public static final Integer VALVE_STATUS_0 = 0; //关阀
    public static final Integer IVALVE_STATUS_1 = 1; //一般开阀
    public static final Integer IVALVE_STATUS_2 = 2; //水用完
    public static final Integer IVALVE_STATUS_3 = 3; //窃水关阀
    public static final Map<Integer, String> VALVE_STATUS_MAP = new HashMap<Integer, String>(){{
        put(0, "关阀");
        put(1, "一般开阀");
        put(2, "水用完");
        put(3, "窃水关阀");
    }};

    //模块状态MAP
    public static final Integer MODULE_STATUS_0 = 0; //未知
    public static final Integer MODULE_STATUS_1 = 1; //正常
    public static final Map<Integer, String> MODULE_STATUS_MAP = new HashMap<Integer, String>(){{
        put(0, "未知");
        put(1, "正常");
    }};

    //操作状态MAP
    public static final Integer CURRENT_STATUS_0 = 0; //未下发
    public static final Integer CURRENT_STATUS_1 = 1; //下发成功
    public static final Integer CURRENT_STATUS_2 = 2; //下发失败
    public static final Integer CURRENT_STATUS_3 = 3; //计数错误
    public static final Integer CURRENT_STATUS_4 = 4; //购水量超出
    public static final Integer CURRENT_STATUS_5 = 5; //下发超时
    public static final Integer CURRENT_STATUS_6 = 6; //退水量超出
    public static final Map<Integer, String> CURRENT_STATUS_MAP = new HashMap<Integer, String>(){{
        put(0, "未下发");
        put(1, "下发成功");
        put(2, "下发失败");
    }};

    //红冲状态MAP
    public static final Integer REDRUSH_STATUS_0 = 0; //是否红冲（0：否，1：是）
    public static final Integer REDRUSH_STATUS_1 = 1; //是否红冲（0：否，1：是）
    public static final Map<Integer, String> REDRUSH_STATUS_MAP = new HashMap<Integer, String>(){{
        put(0, "否");
        put(1, "是");
    }};

    //支付类型MAP 类型（0:现金，1:一卡通, 2:微信）
    public static final Integer PAY_TYPE_STATUS_0 = 0; //现金
    public static final Integer PAY_TYPE_STATUS_1 = 1; //一卡通
    public static final Integer PAY_TYPE_STATUS_2 = 2; //微信
    public static final Map<Integer, String> PAY_TYPE_STATUS_MAP = new HashMap<Integer, String>(){{
        put(0, "现金");
        put(1, "一卡通");
        put(2, "微信");
    }};

    //补水类型（0:房间补水，1:楼层补水，2：按导入补水,3：换房补水）
    public static final Integer ADD_TYPE_STATUS_0 = 0; //房间补水
    public static final Integer ADD_TYPE_STATUS_1 = 1; //楼层补水
    public static final Integer ADD_TYPE_STATUS_2 = 2; //按导入补水
    public static final Integer ADD_TYPE_STATUS_3 = 3; //换房补水

    //类型（0:退水，1:换房退水
    public static final Integer RETURN_TYPE_STATUS_0 = 0; //退水
    public static final Integer RETURN_TYPE_STATUS_1 = 1; //换房退水

    //交易类型
    public static final String TRANSAC_TYPE_1 = "1"; //现金购水
    public static final String TRANSAC_TYPE_2 = "2"; //一卡通购水
    public static final String TRANSAC_TYPE_3 = "3"; //房间补水
    public static final String TRANSAC_TYPE_4 = "4"; //楼层补水
    public static final String TRANSAC_TYPE_5 = "5"; //按导入补水
    public static final String TRANSAC_TYPE_6 = "6"; //退水
    public static final String TRANSAC_TYPE_7 = "7"; //水量清零
    public static final String TRANSAC_TYPE_8 = "8"; //按导入水量清零
    public static final String TRANSAC_TYPE_9 = "9"; //换房补水
    public static final String TRANSAC_TYPE_10 = "10"; //换房退水
    public static final String TRANSAC_TYPE_11 = "11"; //交易冲红


    //操作指令类型（0:购水，1:退水，2:补水，3:换房，4：更换水表等）
    public static final int OPERATION_TYPE_0 = 0; //购水
    public static final int OPERATION_TYPE_1 = 1; //退水
    public static final int OPERATION_TYPE_2 = 2; //补水
    public static final int OPERATION_TYPE_3 = 3; //换房
    public static final int OPERATION_TYPE_4 = 4; //更换水表
    public static final int OPERATION_TYPE_5 = 5; //普通指令
    public static final int OPERATION_TYPE_6 = 6; //清零
    public static final int OPERATION_TYPE_7 = 7; //冲红
    public static final int OPERATION_TYPE_8 = 8; //获取房间信息
    public static final int OPERATION_TYPE_9 = 9; //校时
    public static final int OPERATION_TYPE_10 = 10; //集中器发参数
    public static final int OPERATION_TYPE_11 = 11; //集中器及水表发参数
    public static final int OPERATION_TYPE_12 = 12; //水表发参数
    public static final int OPERATION_TYPE_13 = 13; //重启
    public static final int OPERATION_TYPE_14 = 14; //一般开发
    public static final int OPERATION_TYPE_15 = 15; //一般关阀
    public static final int OPERATION_TYPE_16 = 16; //强制开阀
    public static final int OPERATION_TYPE_17 = 17; //强制关阀
    public static final int OPERATION_TYPE_18 = 18; //更换水表

    public static final Map<Integer, String> OPERATION_TYPE_STATUS_MAP = new HashMap<Integer, String>(){{
        put(0, "购水");
        put(1, "退水");
        put(2, "补水");
        put(3, "换房");
        put(4, "更换水表");
        put(5, "普通指令");
        put(6, "清零");
        put(7, "冲红");
        put(8, "获取水表状态");
        put(9, "校时");
        put(10, "集中器发参数");
        put(11, "集中器及水表发参数");
        put(12, "水表发参数");
        put(13, "重启");
        put(14, "一般开阀");
        put(15, "一般关阀");
        put(16, "强制开阀");
        put(17, "强制关阀");
        put(18, "更换水表");
    }};


    //软件补水周期（0：每日补一次，1：每月补一次，2：不限补水周期）
    public static final Integer ADDCYCLE_TYPE_0 = 0; //每日补一次
    public static final Integer ADDCYCLE_TYPE_1 = 1; //每月补一次
    public static final Integer ADDCYCLE_TYPE_2 = 2; //不限补水周期

    //清零记录类型（0:初始化，1:清零，2:按导入清零）
    public static final Integer CLEAR_TYPE_0 = 0; //初始化
    public static final Integer CLEAR_TYPE_1 = 1; //清零
    public static final Integer CLEAR_TYPE_2 = 2; //按导入清零

    //冲红记录类型（0:补水，1:退水）
    public static final Integer REDRUSH_TYPE_0 = 0; //补水
    public static final Integer REDRUSH_TYPE_1 = 1; //退水


    //目录树等级
    public static final String TREE_LEVEL_1 = "1"; //全部
    public static final String TREE_LEVEL_2 = "2"; //区域
    public static final String TREE_LEVEL_3 = "3"; //公寓
    public static final String TREE_LEVEL_4 = "4"; //栋
    public static final String TREE_LEVEL_5 = "5"; //楼层


    /********************************** 状态常量END **********************************/

    /********************************** 消息常量START **********************************/
    public static final String MSG_FAIL = "fail"; //返回失败
    public static final String MSG_SUCCESS = "success"; //返回成功

    public static final String MSG_EXIST = "在线"; //集中器在线
    public static final String MSG_NOT_EXIST = "离线"; //集中器离线


    public static final String EXCEL_TEM_ADDWATER = "补水导入模版.xlsx";
    public static final String EXCEL_TEM_RESETWATER = "清零导入模版.xlsx";
    public static final String EXCEL_TEM_ADDWATERTemplate = "水表导入模版.xlsx";



    /********************************** 消息常量END **********************************/

}
