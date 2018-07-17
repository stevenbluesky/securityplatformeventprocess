package cn.com.isurpass.securityplatform.alarmone;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class AlarmoneClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new IdleStateHandler(180, 30, 30, TimeUnit.SECONDS));
//        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(32768,new ByteBuf[] {Unpooled.wrappedBuffer("\n".getBytes())})) ;
//        pipeline.addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));
//        pipeline.addLast("encoder", new StringEncoder(Charset.forName("UTF-8")));
        pipeline.addLast("escapecoder", new EscapeCoder());
        pipeline.addLast("framer", new FixedLengthFrameDecoder(1));
        pipeline.addLast("handler", new AlarmoneHeartBeatHandler());

        ch.pipeline().channel();
    }


}
