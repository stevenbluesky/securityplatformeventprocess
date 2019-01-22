package cn.com.isurpass.securityplatform.alarmtwo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


public class AlarmTwoClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new IdleStateHandler(180, 15, 15, TimeUnit.SECONDS));
        //pipeline.addLast("escapecoder", new EscapeCoder());
        pipeline.addLast("framer", new FixedLengthFrameDecoder(1));
        pipeline.addLast("handler", new AlarmTwoHeartBeatHandler());

        ch.pipeline().channel();
    }


}
