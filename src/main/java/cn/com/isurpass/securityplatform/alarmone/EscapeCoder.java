package cn.com.isurpass.securityplatform.alarmone;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

public class EscapeCoder extends MessageToMessageCodec<ByteBuf , byte[]> 
{
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(EscapeCoder.class);
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf content, List<Object> out) throws Exception 
	{
		byte[] c = new byte[content.readableBytes()];
		content.readBytes(c);
		out.add(c);
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, byte[] content, List<Object> out) throws Exception 
	{	
		out.add(Unpooled.wrappedBuffer(content));
		out.add(Unpooled.wrappedBuffer(new byte[]{13}));
	}

}
