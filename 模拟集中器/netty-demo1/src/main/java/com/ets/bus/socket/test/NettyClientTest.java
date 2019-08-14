package com.ets.bus.socket.test;

import com.ets.bus.socket.coder.ConcentratorDecoder;
import com.ets.bus.socket.coder.ConcentratorEncoder;
import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.server.ConstantValue;
import com.ets.bus.socket.test.entity.LiveTestBean;
import com.google.gson.Gson;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Random;

/**
 * @author 宋晨
 * @create 2019/4/12
 * 集中器
 */
public class NettyClientTest {

    /**
     * 连接服务器
     *
     * @param port
     * @param host
     * @throws Exception
     */
    public void connect(int port, String host,Long deviceId) throws Exception {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 客户端辅助启动类 对客户端配置
            Bootstrap b = new Bootstrap();
            b.group(group)//
                    .channel(NioSocketChannel.class)//
                    .option(ChannelOption.TCP_NODELAY, true)//
                    .handler(new MyChannelHandler());//

            // 异步链接服务器 同步等待链接成功
            ChannelFuture f = b.connect(host, port).sync();
            Long currDeviceId = deviceId;
            while (f.channel().isActive()){
                NettyClientHandler.sendImLive(f.channel(),currDeviceId);
            }
            // 等待链接关闭
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
            System.out.println("客户端优雅的释放了线程资源...");
        }

    }



    /**
     * 网络事件处理器
     */
    public class MyChannelHandler extends ChannelInitializer<SocketChannel> {
        protected void initChannel(SocketChannel ch) throws Exception {
            // 添加自定义协议的编解码工具
            ByteBuf delimiter = Unpooled.copiedBuffer(new byte[]{ConstantValue.PROTOCOL_END}); //数据结束标识位
            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(ConstantValue.MAX_DATA_LENGTH, delimiter)); //分隔符解码器
            ch.pipeline().addLast(new ConcentratorDecoder());
            ch.pipeline().addLast(new ConcentratorEncoder());
            // 处理网络IO
            ch.pipeline().addLast(new NettyClientHandler());
        }

    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i<600; i++) {
//            Thread.sleep(200);
            Thread testClient = new Thread(new TestClient());
            testClient.start();
        }
    }

}
