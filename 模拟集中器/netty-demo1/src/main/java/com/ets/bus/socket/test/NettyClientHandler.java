package com.ets.bus.socket.test;

import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.server.ConstantValue;
import com.ets.bus.socket.server.OperationService;
import com.ets.bus.socket.test.entity.LiveTestBean;
import com.ets.utils.SpringContextHolder;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * @author 宋晨
 * @create 2019/4/12
 * 集中器发送接收消息
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {

    // 链接处于活动状态的回调方法
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    //收到消息的回调方法
    protected void channelRead0(ChannelHandlerContext ctx, Object msg)throws Exception {
        try {
            // 用于获取客户端发来的数据信息
            ConcentratorProtocolBean data = (ConcentratorProtocolBean) msg;
            //执行业务
            OperationService operationService = new OperationService();
            Channel channel = ctx.channel();
            ConcentratorProtocolBean result = operationService.operationMsg(data);
            channel.writeAndFlush(result);
            System.out.println("收到采集消息 ，消息类型>>>>" + result.getFunctionCode());
            System.out.println("收到采集消息 ，消息JSON>>>>" + new Gson().toJson(result));
        } catch (Exception e){
            System.out.println("采集异常！>>>>" + e);
            System.out.println("抛弃收到的数据！>>>>" + new Gson().toJson(msg));
            //抛弃收到的数据
            ReferenceCountUtil.release(msg);
        } finally{

        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("集中器异常,数据抛弃！>>>>" + cause.getMessage());
//        ctx.close();
    }

    public static void sendImLive(Channel channel,Long deviceId) throws Exception{
        Thread.sleep(10000);
        LiveTestBean liveTest = new LiveTestBean(deviceId);
        //心跳内容
        byte[] content = new byte[8];
        content[0] = liveTest.getSignalIntensity();
        content[1] = liveTest.getVersion();
        content[2] = liveTest.getPingNum();
        content[3] = liveTest.getPongNum();
        for (int i=0; i<liveTest.getSpare().length; i++){
            content[4+i] = liveTest.getSpare()[i];
        }
        //包长
        int contentLength = ConstantValue.BASE_LENGTH + content.length;
        int sum = ConstantValue.PROTOCOL_START + ConstantValue.FUNCODE_LIVE + contentLength;
        for (byte item:liveTest.getDeviceId()) {
            sum += item;
        }
        for (byte item:content) {
            sum += item;
        }
        byte[] checkArr = intToByteArray(sum);
        byte[] contentLengthArr = intToByteArray(contentLength);

        ConcentratorProtocolBean msg = new ConcentratorProtocolBean();
        msg.setFunctionCode(ConstantValue.FUNCODE_LIVE);
        msg.setDeviceId(liveTest.getDeviceId());
        msg.setContent(content);
        msg.setCheck(checkArr[3]);
        msg.setContentLength(byteToshort(new byte[]{contentLengthArr[2],contentLengthArr[3]}));

        channel.writeAndFlush(msg);
        System.out.println("集中器发送心跳："+ new Gson().toJson(msg));
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static short byteToshort(byte[] b){
        short l = 0;
        for (int i = 0; i < 2; i++) {
            l<<=8; //<<=和我们的 +=是一样的，意思就是 l = l << 8
            l |= (b[i] & 0xff); //和上面也是一样的  l = l | (b[i]&0xff)
        }
        return l;
    }


}
