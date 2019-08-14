package com.ets.bus.waterMeterMgt.instructionOperation.service;

import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.entity.WaterBean;
import com.ets.bus.socket.server.ConstantValue;
import com.ets.bus.socket.server.ConvertCode;
import com.ets.bus.socket.server.ServerHandler;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * @author 宋晨
 * @create 2019/4/15
 * 指令服务
 */
@Service
@Transactional
public class InstructionsService {
    private static final Logger logger = Logger.getLogger(InstructionsService.class);

    /**
     * 获取集中器时间
     * @param deviceId 功能码
     */
    public void concentratorDate(String deviceId){
        ConcentratorProtocolBean concentrator = new ConcentratorProtocolBean(ConstantValue.FUNCODE_CON_DATE);
        concentrator.setDeviceId(ConvertCode.deviceIdintToHex(deviceId));
        sendData(deviceId, concentrator);
    }

    /**
     * 校时
     * @param deviceId
     */
    public void compareTime(String deviceId, ConcentratorProtocolBean concentrator){
        sendData(deviceId, concentrator);
    }

    /**
     * 校时
     * @param deviceId
     */
    public ConcentratorProtocolBean compareTimeConcent(String deviceId){
        ConcentratorProtocolBean concentrator = new ConcentratorProtocolBean(ConstantValue.FUNCODE_COM_TIME);
        concentrator.setDeviceId(ConvertCode.deviceIdintToHex(deviceId));
        byte[] content = getCompareTimeData();
        concentrator.setContent(content);
        return concentrator;
    }

    /**
     * 修改集中器服务地址
     * @param deviceId
     * @param ip
     * @param port
     */
    public void changeAddress(String deviceId, String ip, Integer port){
        ConcentratorProtocolBean concentrator = new ConcentratorProtocolBean(ConstantValue.FUNCODE_CHA_ADDRESS);
        concentrator.setDeviceId(ConvertCode.deviceIdintToHex(deviceId));
        byte[] content = getChangeAddressData(ip, port);
        concentrator.setContent(content);
        sendData(deviceId, concentrator);
    }

    /**
     * 获取集中器参数
     * @param deviceId 功能码
     */
    public void getConcentratorParam(String deviceId){
        ConcentratorProtocolBean concentrator = new ConcentratorProtocolBean(ConstantValue.FUNCODE_CON_PARAM);
        concentrator.setDeviceId(ConvertCode.deviceIdintToHex(deviceId));
        sendData(deviceId, concentrator);
    }

    /**
     * 设置集中器参数
     * @param deviceId 功能码
     */
    public void setConcentratorParam(String deviceId,ConcentratorProtocolBean concentrator){
        sendData(deviceId, concentrator);
    }

    /**
     * 设置集中器参数
     * @param deviceId 功能码
     */
    public ConcentratorProtocolBean setConcentratorParamConcent(String deviceId,Integer hour, Integer minute){
        ConcentratorProtocolBean concentrator = new ConcentratorProtocolBean(ConstantValue.FUNCODE_SET_PARAM);
        concentrator.setDeviceId(ConvertCode.deviceIdintToHex(deviceId));
        byte[] content = getConcentratorParamData(hour, minute);
        concentrator.setContent(content);
        return concentrator;
    }

    /**
     * 获取水表状态（包括剩余水量）
     * @param deviceId 功能码
     */
    public void getWaterMeterStatus(String deviceId, ConcentratorProtocolBean concentrator){
        sendData(deviceId, concentrator);
    }

    /**
     * 获取水表状态（包括剩余水量）
     * @param deviceId 功能码
     */
    public ConcentratorProtocolBean getWaterMeterStatusConcent(String deviceId, List<String> meterNumList){
        ConcentratorProtocolBean concentrator = new ConcentratorProtocolBean(ConstantValue.FUNCODE_WATER_METER);
        concentrator.setDeviceId(ConvertCode.str2Bcd(meterNumList.get(0)));
        return concentrator;
    }

    /**
     * 水表清零
     * @param deviceId 功能码
     */
    public void getWaterMeterReset(String deviceId, ConcentratorProtocolBean concentrator){
        sendData(deviceId, concentrator);
    }

    /**
     * 水表清零
     * @param deviceId 功能码
     */
    public ConcentratorProtocolBean getWaterMeterResetConcent(String deviceId, List<WaterMeterInfoVo> numBeans){
        ConcentratorProtocolBean concentrator = new ConcentratorProtocolBean(ConstantValue.FUNCODE_WATER_RESET);
        concentrator.setDeviceId(ConvertCode.str2Bcd(numBeans.get(0).getWaterMeterNum()));
        return concentrator;
    }

    /**
     * 重启集中器
     * @param deviceId 功能码
     */
    public void getWaterMeterRestart(String deviceId,ConcentratorProtocolBean concentrator){
        sendData(deviceId, concentrator);
    }

    /**
     * 重启集中器
     * @param deviceId 功能码
     */
    public ConcentratorProtocolBean getWaterMeterRestartConcent(String deviceId){
        ConcentratorProtocolBean concentrator = new ConcentratorProtocolBean(ConstantValue.FUNCODE_WATER_RESTART);
        concentrator.setDeviceId(ConvertCode.deviceIdintToHex(deviceId));
        return concentrator;
    }

    /**
     * 购水、补水
     * @param deviceId 功能码
     */
    public void addWater(String deviceId, ConcentratorProtocolBean concentrator){
        sendData(deviceId, concentrator);
    }

    /**
     * 购水、补水
     * @param deviceId 功能码
     */
    public ConcentratorProtocolBean addWaterConcent(String deviceId, List<WaterBean> addWaterBeans){
        ConcentratorProtocolBean concentrator = new ConcentratorProtocolBean(ConstantValue.FUNCODE_ADD_WATER);
        concentrator.setHead(ConstantValue.PROTOCOL_SEND_START);
        concentrator.setDeviceId(ConvertCode.deviceIdintToHex(deviceId));
        concentrator.setVersion(ConstantValue.CONCENTRATOR_VERSION);
        concentrator.setType(ConstantValue.CONCENTRATOR_TYPE);
        concentrator.setEnd(ConstantValue.PROTOCOL_SEND_END);
        byte[] content = getAddWaterData(addWaterBeans);
        concentrator.setContent(content);
        //设置包长和校验位
        setUpLengthAndCheck(concentrator);
        return concentrator;
    }

    /**
     * 退水
     * @param deviceId 功能码
     */
    public void returnWater(String deviceId, ConcentratorProtocolBean concentrator){
        sendData(deviceId, concentrator);
    }

    /**
     * 退水
     * @param deviceId 功能码
     */
    public ConcentratorProtocolBean returnWaterConcent(String deviceId, List<WaterBean> returnWaterBeans){
        ConcentratorProtocolBean concentrator = new ConcentratorProtocolBean(ConstantValue.FUNCODE_RETURN_WATER);
        concentrator.setDeviceId(ConvertCode.str2Bcd(returnWaterBeans.get(0).getWaterMeterNum().toString()));
        byte[] content = getAddWaterData(returnWaterBeans);
        concentrator.setContent(content);
        return concentrator;
    }

    /**
     * 更新水表信息
     * @param deviceId 功能码
     */
    public void updateWater(String deviceId, ConcentratorProtocolBean concentrator){
        System.out.println("concentrator======"+ new Gson().toJson(concentrator));
        sendData(deviceId, concentrator);
    }

    /**
     * 更新水表信息
     * @param deviceId 功能码
     */
    public ConcentratorProtocolBean updateWaterConcent(String deviceId, List<WaterBean> updateWaterBeans){
        ConcentratorProtocolBean concentrator = new ConcentratorProtocolBean(ConstantValue.FUNCODE_UPDATE_WATER);
        //这里的设置设备的ID改为设置成水表的ID
        concentrator.setDeviceId(ConvertCode.str2Bcd(updateWaterBeans.get(0).getWaterMeterNum().toString()));
        byte[] content = getUpdateWaterData(updateWaterBeans);
        concentrator.setContent(content);
        return concentrator;
    }

    /**
     * 获取水表参数
     * @param deviceId 功能码
     */
    public void getWaterMeterParam(String deviceId, List<WaterMeterInfoVo> meterNumList){
        ConcentratorProtocolBean concentrator = new ConcentratorProtocolBean(ConstantValue.FUNCODE_GET_WATER_PARAM);
        concentrator.setDeviceId(ConvertCode.deviceIdintToHex(deviceId));
        byte[] content = getWaterMeterStatusData(meterNumList);
        concentrator.setContent(content);
        sendData(deviceId, concentrator);
    }

    /**
     * 开关阀
     * @param deviceId 功能码
     */
    public void openWaterMeter(String deviceId, ConcentratorProtocolBean concentrator){
        sendData(deviceId, concentrator);
    }

    /**
     * 开关阀
     * @param deviceId 功能码
     */
    public ConcentratorProtocolBean openWaterMeterConcent(String deviceId, List<WaterBean> waterBeans){
        ConcentratorProtocolBean concentrator = new ConcentratorProtocolBean(ConstantValue.FUNCODE_OPEN);
        concentrator.setDeviceId(ConvertCode.deviceIdintToHex(deviceId));
        byte[] content = getOpenWaterMeterData(waterBeans);
        concentrator.setContent(content);
        return concentrator;
    }

    /**
     * 判断集中器是否在线
     * @param deviceId
     */
    public boolean existChannel(String deviceId){
        boolean result = true;
        //获取channel
        Channel channel = ServerHandler.channelMap.get(deviceId);
        if(channel== null){
            result = false;
        }

        return result;
    }

    /**
     * 发送指令
     * @param deviceId
     * @param concentrator
     */
    public void sendData(String deviceId, ConcentratorProtocolBean concentrator){
        // todo 更改检验方式和数据的长度
        //设置包长和校验位
        setUpLengthAndCheck(concentrator);
        //获取channel
        Channel channel = ServerHandler.channelMap.get(deviceId);
        if(channel!= null){
            System.out.println("采集发送消息 ，消息类型>>>>" + concentrator.getFunctionCode());
            System.out.println("采集发送消息 ，消息JSON>>>>" + new Gson().toJson(concentrator));
            channel.writeAndFlush(concentrator);
        }else {
            System.out.println("集中器编号："+deviceId+"  不在线 ，消息发送失败>>>>>>>>" + new Gson().toJson(concentrator));
        }
    }

    /**
     * 设置包长和校验位
     */
    public void setUpLengthAndCheck(ConcentratorProtocolBean concentrator){
        //包长
        int datalen = 0;
        int sum = concentrator.getVersion()+concentrator.getType() + concentrator.getFunctionCode();
        if(concentrator.getContent() != null){
            datalen = concentrator.getContent().length;
            for (byte item:concentrator.getContent()) {
                sum += item;
            }
        }

        //int contentLength = ConstantValue.BASE_LENGTH + datalen;
        sum += datalen;
        for (byte item:concentrator.getDeviceId()) {
            sum += item;
        }
        byte[] checkArr = ConvertCode.intToByteArray(sum);
       // byte[] contentLengthArr = ConvertCode.intToByteArray(contentLength);
        //concentrator.setCheck(checkArr[3]);
        concentrator.setCheck((short)sum);
        //concentrator.setContentLength(ConvertCode.byteToshort(new byte[]{contentLengthArr[2],contentLengthArr[3]}));
        concentrator.setContentLength((short) datalen);
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
     * ip0	ip0H	1个字节	服务器的ip地址
     * ip1	ip1H	1个字节
     * ip2	ip2H	1个字节
     * ip3	ip3H	1个字节
     * port	端口号	2个字节	端口号，低字节在前，高字节在后
     * 备用字段		4个字节	用于备用
     * @return
     */
    public byte[] getChangeAddressData(String ip, Integer port){
        String[] stringarr = ip.split(".");
        byte[] data = new byte[10];
        for (int i=0; i < stringarr.length; i++) {
            data[i] = ConvertCode.intToByteArray(Integer.parseInt(stringarr[i]))[3];
        }
        byte[] portByte = ConvertCode.intToByteArray(port);
        data[4] = portByte[3];
        data[5] = portByte[2];
        data[6] = 0x00;
        data[7] = 0x00;
        data[8] = 0x00;
        data[9] = 0x00;
        return data;
    }

    /**
     * 数据上报周期
     * 2个字节	集中器上上传数据频率
     * 前一个字节表示小时，后一个字节表示分钟，分钟必须是5的倍数（0/5/10/15…），HEX码）；
     * 0x00xx00表示保持不变（即不改变之前设置的数据上报周期）
     * @param hour
     * @param minute
     * @return
     */
    public byte[] getConcentratorParamData(Integer hour,Integer minute){
        minute = (minute/5)*5;
        return new byte[]{ConvertCode.intToByteArray(hour)[3],ConvertCode.intToByteArray(minute)[3]};
    }

    /**
     * 水表个数	00 00	2字节	水表的数量
     * 水表编码（地址）1	xx	1个字节	水表编码（地址）：四个字节采用BCD码格式。
     * 	                xx	1个字节
     * 	                xx	1个字节
     * 	                xx	1个字节
     * 水表编码（地址）2	xx	1个字节	水表编码（地址）：四个字节采用BCD码格式。
     * 	                xx	1个字节
     * 	                xx	1个字节
     * 	                xx	1个字节
     * 水表地址N……
     * 水表编码（地址）M	xx	1个字节	水表编码（地址）：四个字节采用BCD码格式。
     * @param meterNumList
     * @return
     */
    public byte[] getWaterMeterStatusData(List<WaterMeterInfoVo> meterNumList){
        byte[] leangth = ConvertCode.intToByteArray(meterNumList.size());
        byte[] data = new byte[meterNumList.size()*4+2];
        data[0] = leangth[2];
        data[1] = leangth[3];
        for (int i=0; i < meterNumList.size(); i++) {
            byte[] itemBCD = ConvertCode.str2Bcd(meterNumList.get(i)+"");
            data[i*4+2] = itemBCD[0];
            data[i*4+3] = itemBCD[1];
            data[i*4+4] = itemBCD[2];
            data[i*4+5] = itemBCD[3];
        }
        return data;
    }

    /**
     * @param addWaterBeans
     * @return
     */
    public byte[] getAddWaterData(List<WaterBean> addWaterBeans){

        byte[] data = new byte[6];
        for (int i=0; i < addWaterBeans.size(); i++) {
            byte[] itemBuyArr = ConvertCode.intToByteArray(addWaterBeans.get(i).getBuyWater());
            data[0] = itemBuyArr[2];
            data[1] = itemBuyArr[3];
            byte[] itemAddArr = ConvertCode.intToByteArray(addWaterBeans.get(i).getAddWater());
            data[2] = itemAddArr[2];
            data[3] = itemAddArr[3];
            byte[] itemNumArr = ConvertCode.intToByteArray(addWaterBeans.get(i).getAddNum());
            data[4] = itemNumArr[2];
            data[5] = itemNumArr[3];
        }
        return data;
    }

    /**
     * 水表个数	00 00	2字节	水表水量
     * 报警水量	D0 D1	2个字节	2字节16进制水量
     * 0~65535 *0.1吨
     * 关阀水量	D0 D1 	2个字节	2字节16进制水量
     * 0~65535 *0.1吨余字节是整数位
     * 透支水量	D0 D1 	2个字节	2字节16进制水量
     * 0~65535 *0.1吨
     * 囤积水量	D0 D1 	2个字节	2字节16进制水量
     * 0~65535 *0.1吨
     * 水表编码（地址）N
     * @param waterBeans
     * @return
     */
    public byte[] getUpdateWaterData(List<WaterBean> waterBeans){
        // 7E10666666662622
        byte[] data = new byte[waterBeans.size()*8];
        for (int i=0; i < waterBeans.size(); i++) {
            byte[] itemWarnArr = ConvertCode.intToByteArray(waterBeans.get(i).getWarnWater());
            data[i*8] = itemWarnArr[2];
            data[i*8+1] = itemWarnArr[3];
            byte[] itemValveArr = ConvertCode.intToByteArray(waterBeans.get(i).getValveWater());
            data[i*8+2] = itemValveArr[2];
            data[i*8+3] = itemValveArr[3];
            byte[] itemOveArr = ConvertCode.intToByteArray(waterBeans.get(i).getOverWater());
            data[i*8+4] = itemOveArr[2];
            data[i*8+5] = itemOveArr[3];
            byte[] itemHoardArr = ConvertCode.intToByteArray(waterBeans.get(i).getHoardWater());
            data[i*8+6] = itemHoardArr[2];
            data[i*8+7] = itemHoardArr[3];
        }
        return data;
    }

    /**
     * 水表个数	00 00	2字节	水表水量
     * 水表编码（地址）1	xx	1个字节	水表编码（地址）：四个字节采用BCD码格式。
     * 	                xx	1个字节
     * 	                xx	1个字节
     * 	                xx	1个字节
     * 水阀操作	D0	1个字节	1字节16进制数
     *          00表示一般开阀
     *          01表示一般关阀
     *          02表示强制开阀
     *          03表是强制关阀
     * @param waterBeans
     * @return
     */
    public byte[] getOpenWaterMeterData(List<WaterBean> waterBeans){
        byte[] leangth = ConvertCode.intToByteArray(waterBeans.size());
        byte[] data = new byte[waterBeans.size()*5+2];
        data[0] = leangth[2];
        data[1] = leangth[3];
        for (int i=0; i < waterBeans.size(); i++) {
            //eg:44444444 -> [68,68,68,68]
            byte[] itemBCDArr = ConvertCode.str2Bcd(waterBeans.get(i).getWaterMeterNum()+"");
            data[i*5+2] = itemBCDArr[0];
            data[i*5+3] = itemBCDArr[1];
            data[i*5+4] = itemBCDArr[2];
            data[i*5+5] = itemBCDArr[3];
            data[i*5+6] = waterBeans.get(i).getOpenType();
        }
        return data;
    }

}
