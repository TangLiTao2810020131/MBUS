package com.ets.bus.socket.server;

import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.entity.WaterBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author 宋晨
 * @create 2019/4/16
 * 收取集中器消息
 */
@Service
public class OperationService {
    private static final byte RNT_SUCCESS = 00;
    private static final byte OPEN = 01;
    private static final byte POWER = 00;
    private static final byte ATTACK= 01;

    /**
     * 收取的集中器消息
     * @param data
     */
    public ConcentratorProtocolBean operationMsg(ConcentratorProtocolBean data){
        ConcentratorProtocolBean result = data;
        switch (data.getFunctionCode()){
            //心跳 集中器主动上传，采集收到无需应答。
            case  ConstantValue.FUNCODE_LIVE:
                break;

            //获取集中器时间
            case ConstantValue.FUNCODE_CON_DATE:
                funcodeConDate(data);
                break;

            //校时
            case ConstantValue.FUNCODE_COM_TIME:
                funcodeComTime(data);
                break;

            //修改集中器服务地址
            case ConstantValue.FUNCODE_CHA_ADDRESS:
                funcodeChaAddress(data);
                break;

            //获取集中器参数
            case ConstantValue.FUNCODE_CON_PARAM:
                funcodeConParam(data);
                break;

            //设置集中器参数
            case ConstantValue.FUNCODE_SET_PARAM:
                funcodeSetParam(data);
                break;

            //获取水表状态（包括剩余水量）
            case ConstantValue.FUNCODE_WATER_METER:
                result = funcodeWaterMeter(data);
                break;

            //水表清零
            case ConstantValue.FUNCODE_WATER_RESET:
                funcodeWaterReset(data);
                break;

            //重启集中器
            case ConstantValue.FUNCODE_WATER_RESTART:
                funcodeWaterRestart(data);
                break;

            //购水、补水
            case ConstantValue.FUNCODE_ADD_WATER:
                funcodeAddWater(data);
                break;

            //退水
            case ConstantValue.FUNCODE_RETURN_WATER:
                funcodeReturnWater(data);
                break;

            //更新水表信息
            case ConstantValue.FUNCODE_UPDATE_WATER:
                funcodeUpdateWater(data);
                break;

            //获取水表参数
            case ConstantValue.FUNCODE_GET_WATER_PARAM:
                funcodeGetWaterParam(data);
                break;

            //开关阀
            case ConstantValue.FUNCODE_OPEN:
                funcodeOpen(data);
                break;

            //终端报警
            case ConstantValue.FUNCODE_WARN:
                funcodeWarn(data);
                break;

            default:
                break;
        }

        return result;
    }

    /**
     * 获取集中器时间返回参数
     * @param data
     */
    private void funcodeConDate(ConcentratorProtocolBean data){

    }

    /**
     * 校时返回参数
     * @param data
     */
    private void funcodeComTime(ConcentratorProtocolBean data){

    }

    /**
     * 修改集中器服务地址返回参数
     * @param data
     */
    private void funcodeChaAddress(ConcentratorProtocolBean data){

    }

    /**
     * 获取集中器参数返回参数
     * @param data
     */
    private void funcodeConParam(ConcentratorProtocolBean data){

    }

    /**
     * 设置集中器参数返回参数
     * @param data
     */
    private void funcodeSetParam(ConcentratorProtocolBean data){

    }

    /**
     * 获取水表状态返回参数
     * @param data
     */
    private ConcentratorProtocolBean funcodeWaterMeter(ConcentratorProtocolBean data){
        //获取水表编号
        List<WaterBean> list = getWaterBeanByFuncodeWaterMeter(data);
        //获取水表水量信息
        getWater(list);

        ConcentratorProtocolBean result = new ConcentratorProtocolBean(ConstantValue.FUNCODE_WATER_METER);
        result.setDeviceId(data.getDeviceId());
        byte[] content = getWaterData(list);
        result.setContent(content);
        //设置包长和校验位
        setUpLengthAndCheck(result);
        return result;
    }

    /**
     * 水表清零返回参数
     * @param data
     */
    private void funcodeWaterReset(ConcentratorProtocolBean data){

    }

    /**
     * 重启集中器返回参数
     * @param data
     */
    private void funcodeWaterRestart(ConcentratorProtocolBean data){

    }

    /**
     * 购水、补水返回参数
     * @param data
     */
    private void funcodeAddWater(ConcentratorProtocolBean data){

    }

    /**
     * 退水返回参数
     * @param data
     */
    private void funcodeReturnWater(ConcentratorProtocolBean data){

    }

    /**
     * 更新水表信息返回参数
     * @param data
     */
    private void funcodeUpdateWater(ConcentratorProtocolBean data){

    }

    /**
     * 获取水表参数返回参数
     * @param data
     */
    private void funcodeGetWaterParam(ConcentratorProtocolBean data){

    }

    /**
     * 开关阀返回参数
     * @param data
     */
    private void funcodeOpen(ConcentratorProtocolBean data){

    }

    /**
     * 终端报警返回参数
     * @param data
     */
    private void funcodeWarn(ConcentratorProtocolBean data){

    }

    /**
     * 年	xx（年份前两位）	1个字节	7位16进制数，上送时间
     * 年	xx（年份后两位）	1个字节
     * 月	xx	1个字节
     * 日	xx	1个字节
     * 时	xx	1个字节
     * 分	xx	1个字节
     * 秒	xx	1个字节
     * 水表个数	00 00	2字节	水表水量
     * 水表编码（地址）1	xx	1个字节	水表编码（地址）：四个字节采用BCD码格式。
     * 	xx	1个字节
     * 	xx	1个字节
     * 	xx	1个字节
     * RTN	D0	1个字节	00表示获取正常 31表示获取超时
     * 剩余购水量	D0 D1 	2个字节	2字节16进制数
     * 范围0~65535 *0.1吨
     * 剩余补水量	D0 D1 	2个字节	2字节16进制数
     * 范围0~65535 *0.1吨
     * 下发计数	D0 D1 	2个字节	2字节16进制数
     * 范围0~65535
     * 总用水量	D0 D1 D2	3个字节	3字节16进制数
     * 范围0~    *0.1吨
     * 透水量	D0 D1	2字节	2字节16进制数
     * 范围0~65535 *0.1吨
     * 阀门状态	D0 	1个字节	1字节16进制数
     * 0x00表示阀门关闭，
     * 0x01表示阀门开放状态
     * 0x02表示阀门强制开放状态
     * 0x03表示阀门强制关闭状态示）
     * 电池状态	D0 	1个字节	1字节16进制数
     * 00表示电池正常
     * 01表示电池亏电
     * 磁攻击次数	D0	1个字节	1字节16进制数
     * 表示磁攻击次数，该值不大于3
     * 水表编码（地址）2	xxH	1个字节	水表编码（地址）：四个字节采用HEX码格式。
     * 	xxH	1个字节
     * 	xxH	1个字节
     * 	xxH	1个字节
     * 水表地址N……
     * @param addWaterBeans
     * @return
     */
    public byte[] getWaterData(List<WaterBean> addWaterBeans){
        byte[] leangth = ConvertCode.intToByteArray(addWaterBeans.size());
        byte[] data = new byte[addWaterBeans.size()*19+9];
        byte[] time = getCompareTimeData();
        data[0] = time[0];
        data[1] = time[1];
        data[2] = time[2];
        data[3] = time[3];
        data[4] = time[4];
        data[5] = time[5];
        data[6] = time[6];
        data[7] = leangth[2];
        data[8] = leangth[3];
        for (int i=0; i < addWaterBeans.size(); i++) {
            byte[] itemBCD = ConvertCode.str2Bcd(addWaterBeans.get(i).getWaterMeterNum());
            data[i*19+9] = itemBCD[0];
            data[i*19+10] = itemBCD[1];
            data[i*19+11] = itemBCD[2];
            data[i*19+12] = itemBCD[3];

            data[i*19+13] = addWaterBeans.get(i).getRnt();

            byte[] itemBuyArr = ConvertCode.intToByteArray(addWaterBeans.get(i).getBuyWater());
            data[i*19+14] = itemBuyArr[2];
            data[i*19+15] = itemBuyArr[3];
            byte[] itemAddArr = ConvertCode.intToByteArray(addWaterBeans.get(i).getAddWater());
            data[i*19+16] = itemAddArr[2];
            data[i*19+17] = itemAddArr[3];
            byte[] itemNumArr = ConvertCode.intToByteArray(addWaterBeans.get(i).getAddNum());
            data[i*19+18] = itemNumArr[2];
            data[i*19+19] = itemNumArr[3];
            byte[] itemTotalArr = ConvertCode.intToByteArray(addWaterBeans.get(i).getTotalWater());
            data[i*19+20] = itemTotalArr[1];
            data[i*19+21] = itemTotalArr[2];
            data[i*19+22] = itemTotalArr[3];
            byte[] itemOverArr = ConvertCode.intToByteArray(addWaterBeans.get(i).getOverWater());
            data[i*19+23] = itemOverArr[2];
            data[i*19+24] = itemOverArr[3];

            data[i*19+25] = addWaterBeans.get(i).getOpenType();
            data[i*19+26] = addWaterBeans.get(i).getPower();
            data[i*19+27] = addWaterBeans.get(i).getAttack();
        }
        return data;
    }

    List<WaterBean> getWaterBeanByFuncodeWaterMeter(ConcentratorProtocolBean data){
        List<WaterBean> list = new ArrayList<WaterBean>();
        //水表个数
        byte[] numarr = new byte[4];
        numarr[0]=0;
        numarr[1]=0;
        numarr[2]=data.getContent()[0];
        numarr[3]=data.getContent()[1];
        Integer num = ConvertCode.byteArrayToInt(numarr);
        for (int i=0; i < num; i++) {
            WaterBean waterBean = new WaterBean();
            //水表编号
            byte[] waterMeterArr = new byte[4];
            waterMeterArr[0] = data.getContent()[i*4+2];
            waterMeterArr[1] = data.getContent()[i*4+3];
            waterMeterArr[2] = data.getContent()[i*4+4];
            waterMeterArr[3] = data.getContent()[i*4+5];
            waterBean.setWaterMeterNum(ConvertCode.bcd2Str(waterMeterArr));
            list.add(waterBean);
        }
        return list;
    }

    //获取水表水量信息
    private void getWater(List<WaterBean> list){
        for (WaterBean waterBean : list) {
            waterBean.setRnt(RNT_SUCCESS);
            waterBean.setBuyWater(20);
            waterBean.setAddWater(30);
            waterBean.setAddNum(66);
            waterBean.setTotalWater(890);
            waterBean.setOverWater(1);
            waterBean.setOpenType(OPEN);
            waterBean.setPower(POWER);
            waterBean.setAttack(ATTACK);
        }
    }

    /**
     * 年	HEX码表示年的前两位	1个字节
     * 年	HEX码表示年份的后两位	1个字节
     * 月	HEX码表示月份	1个字节
     * 日	HEX码表示日	1个字节
     * 时	HEX码表示时	1个字节
     * 分	HEX码表示分	1个字节
     * 秒	HEX码表示秒	1个字节
     * @return
     */
    public byte[] getCompareTimeData(){
        Calendar curr = Calendar.getInstance();
        int year = curr.get(Calendar.YEAR);
        byte year1 = ConvertCode.intToByteArray(year/100)[3];
        byte year2 = ConvertCode.intToByteArray(year%100)[3];
        byte month = ConvertCode.intToByteArray(curr.get(Calendar.MONTH)+1)[3];
        byte day = ConvertCode.intToByteArray(curr.get(Calendar.DAY_OF_MONTH))[3];
        byte hour = ConvertCode.intToByteArray(curr.get(Calendar.HOUR_OF_DAY))[3];
        byte minute = ConvertCode.intToByteArray(curr.get(Calendar.MINUTE))[3];
        byte second = ConvertCode.intToByteArray(curr.get(Calendar.SECOND))[3];

        return new byte[]{year1,year2,month,day,hour,minute,second};
    }

    /**
     * 设置包长和校验位
     */
    public void setUpLengthAndCheck(ConcentratorProtocolBean concentrator){
        //包长
        int datalen = 0;
        int sum = ConstantValue.PROTOCOL_START + concentrator.getFunctionCode();
        if(concentrator.getContent() != null){
            datalen = concentrator.getContent().length;
            for (byte item:concentrator.getContent()) {
                sum += item;
            }
        }

        int contentLength = ConstantValue.BASE_LENGTH + datalen;
        sum += contentLength;
        for (byte item:concentrator.getDeviceId()) {
            sum += item;
        }
        byte[] checkArr = ConvertCode.intToByteArray(sum);
        byte[] contentLengthArr = ConvertCode.intToByteArray(contentLength);
        concentrator.setCheck(checkArr[3]);
        concentrator.setContentLength(ConvertCode.byteToshort(new byte[]{contentLengthArr[2],contentLengthArr[3]}));
    }

}
