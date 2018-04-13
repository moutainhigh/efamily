package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;

import com.winterframework.efamily.server.protocol.Delimiters;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.ProtocolUtil;

public class CliReqEncode extends MessageToByteEncoder<FmlRequest> {
	@Override
	protected void encode(ChannelHandlerContext ctx, FmlRequest msg, ByteBuf out) throws Exception {
		ByteBuffer headBuffer = ByteBuffer.allocate(57);
		/**
		 * 先组织报文头
		 */
		headBuffer.put(msg.getEncode()); 
		headBuffer.put(msg.getEncrypt());
		headBuffer.put(msg.getVersion());
		headBuffer.put(msg.getClinetType());
		headBuffer.put(msg.getExtend());
		headBuffer.putLong(msg.getSessionId());
		headBuffer.putInt(msg.getStatus());
		headBuffer.putInt(msg.getCommand());
		headBuffer.put(msg.getToken().getBytes());
		/**
		 * 组织报文的数据部分
		 */
		ByteBuf dataBuffer = ProtocolUtil.encode(msg.getEncode(), msg.getData(), ctx);
		int length = dataBuffer.readableBytes();
		headBuffer.putInt(length);
		/**
		 * 非常重要
		 * ByteBuffer需要手动flip()，ChannelBuffer不需要
		 */
		headBuffer.flip();
		ByteBuf totalBuffer = ctx.alloc().buffer();
		totalBuffer.writeBytes(headBuffer);
		totalBuffer.writeBytes(dataBuffer);	
		totalBuffer.writeBytes(Delimiters.getDelimiter());
		//totalBuffer.writeByte('\n');
		out.writeBytes(totalBuffer);
	}

}