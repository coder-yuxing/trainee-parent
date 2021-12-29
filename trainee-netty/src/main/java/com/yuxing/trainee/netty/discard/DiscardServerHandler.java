package com.yuxing.trainee.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 抛弃服务器处理服务端 channel
 *
 * 处理器的职责是释放所有传递到处理器的请求(引用计数)
 *
 * @author yuxing
 * @since 2021/12/29
 */
@Slf4j
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * channelRead 是事件处理方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("接收到请求：{}，该请求将会被放弃！", msg);
        ByteBuf in = (ByteBuf) msg;
        try {
            // 打印接收到的数据
            while (in.isReadable()) {
                System.err.println((char) in.readByte());
                System.err.flush();
            }
        } finally {
            // 丢弃接收到的数据
            ((ByteBuf) msg).release();
        }

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
