package com.yuxing.trainee.netty.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 应答服务处理类
 *
 * @author yuxing
 * @since 2021/12/29
 */
@Slf4j
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("应答服务已接收到请求，将会给出响应！");
        ctx.write(msg);
        ctx.flush();
    }

    /**
     * exceptionCaught 事件处理方法用于在 请求出现异常时会被调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
