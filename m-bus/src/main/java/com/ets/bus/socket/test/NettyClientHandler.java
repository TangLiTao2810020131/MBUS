package com.ets.bus.socket.test;

import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.server.ConstantValue;
import com.ets.bus.socket.server.ConvertCode;
import com.ets.bus.socket.test.entity.LiveTestBean;
import com.google.gson.Gson;
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
        System.out.println("=====================连接成功，开始发送心跳包==============");
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LiveTestBean liveTest = new LiveTestBean();
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
            byte[] checkArr = ConvertCode.intToByteArray(sum);
            byte[] contentLengthArr = ConvertCode.intToByteArray(contentLength);

            ConcentratorProtocolBean msg = new ConcentratorProtocolBean();
           // msg.setSendHead(TcpConstantValue.PROTOCOL_REC_START);
            msg.setFunctionCode(ConstantValue.FUNCODE_LIVE);
            msg.setDeviceId(liveTest.getDeviceId());
            msg.setContent(content);
            //msg.setCheck(checkArr[3]);
            msg.setCheck((short) 10);
            msg.setContentLength(ConvertCode.byteToshort(new byte[]{contentLengthArr[2],contentLengthArr[3]}));

            ctx.writeAndFlush(msg);
            System.out.println("集中器发送心跳："+ new Gson().toJson(msg));
        }
    }

    //收到消息的回调方法
    protected void channelRead0(ChannelHandlerContext ctx, Object msg)throws Exception {
        try {
            // 用于获取客户端发来的数据信息
            ConcentratorProtocolBean body = (ConcentratorProtocolBean) msg;
            System.out.println("收到采集消息 :" + new Gson().toJson(body));
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
