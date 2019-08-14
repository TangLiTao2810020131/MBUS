package com.ets.bus.socket.server;

import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.log.entity.SocketLogBean;
import com.ets.bus.socket.log.service.SocketLogService;
import com.ets.utils.UUIDUtils;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import com.ets.utils.SpringContextHolder;

import java.util.Date;

/**
 * @author 姚轶文
 * @create 2019- 04-06 16:40
 */
public class ServerHandler extends SimpleChannelInboundHandler<Object> {

    //创建Channel组，用来保存客户端与服务器端连接的channel
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    //心跳监听
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        System.out.println("已经360秒未收到客户端的消息了！");
//        if (evt instanceof IdleStateEvent)
//        {
//            IdleStateEvent event = (IdleStateEvent)evt;
//            if (event.state()== IdleState.READER_IDLE)
//            {
//                System.out.println("关闭这个不活跃通道！");
//                /********************************************************/
//                //这里写入日志表，channel的ID，当前时间，备注：未接受到心跳
//                SocketLogService socketLogService = SpringContextHolder.getBean(SocketLogService.class);
//                SocketLogBean socketLog = new SocketLogBean(UUIDUtils.getUUID(), new Date());
//                socketLog.setChannelId(ctx.channel().id().asLongText());
//                socketLog.setRemark("未接受到心跳。");
//                socketLogService.insertSocketLog(socketLog);
//                /********************************************************/
//                ctx.channel().close();
//            }
//        }
//        else {
//            super.userEventTriggered(ctx,evt);
//        }
    }

    @Override //链接断开的回调方法
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        /********************************************************/
        //这里写入日志表，channel的ID，当前时间，备注：链接断开
        SocketLogService socketLogService = SpringContextHolder.getBean(SocketLogService.class);
        SocketLogBean socketLog = new SocketLogBean(UUIDUtils.getUUID(), new Date());
        socketLog.setChannelId(ctx.channel().id().asLongText());
        socketLog.setRemark("链接断开。");
        socketLogService.insertSocketLog(socketLog);
        /********************************************************/
        //channelGroup.writeAndFlush("[服务器]---->"+channel.remoteAddress()+" 离线"); //通知所有客户端，X离线

        //不需要显示的调用channelGroup.romve方法删除这个channel链接。 NETTY会自动处理。
    }

    @Override //链接处于活动状态的回调方法
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        /********************************************************/
        //这里写入日志表，channel的ID，当前时间，备注：链接建立
        SocketLogService socketLogService = SpringContextHolder.getBean(SocketLogService.class);
        SocketLogBean socketLog = new SocketLogBean(UUIDUtils.getUUID(), new Date());
        socketLog.setChannelId(ctx.channel().id().asLongText());
        socketLog.setRemark("链接建立。");
        socketLogService.insertSocketLog(socketLog);
        /********************************************************/
        System.out.println("[控制台打印]---->"+channel.remoteAddress()+" 上线");
    }

    @Override //链接处于非活动状态的回调方法
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[控制台打印]---->"+channel.remoteAddress()+" 下线");
    }


    @Override //收到消息的回调方法
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ConcentratorProtocolBean) {
            ConcentratorProtocolBean data= (ConcentratorProtocolBean) msg;
            //执行业务
            OperationService operationService = SpringContextHolder.getBean(OperationService.class);
            Channel channel = ctx.channel();
            ConcentratorProtocolBean result = operationService.operationMsg(data);
            channel.writeAndFlush(result);
            System.out.println("采集接收到消息并返回-"+channel.remoteAddress()+":"+ new Gson().toJson(result));
        }


    }


    @Override //出现异常的回调方法
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace(); //打印异常信息
        ctx.close();//关闭链接
    }



}
