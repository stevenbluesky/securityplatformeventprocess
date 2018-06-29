package cn.com.isurpass.securityplatform.alarmone;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class AlarmoneClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception 
    {
        ChannelPipeline pipeline = ch.pipeline();
        
        pipeline.addLast(new IdleStateHandler(180 , 30 , 30, TimeUnit.SECONDS));
//        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(32768,new ByteBuf[] {Unpooled.wrappedBuffer("\n".getBytes())})) ; 
//        pipeline.addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));
//        pipeline.addLast("encoder", new StringEncoder(Charset.forName("UTF-8")));
        pipeline.addLast("escapecoder", new EscapeCoder());
        pipeline.addLast("framer", new FixedLengthFrameDecoder(1)) ; 
        pipeline.addLast("handler", new AlarmoneHeartBeatHandler()) ; 
        
        ch.pipeline().channel();
    }
    
    
}
