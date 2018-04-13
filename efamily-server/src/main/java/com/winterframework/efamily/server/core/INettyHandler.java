package com.winterframework.efamily.server.core;

import io.netty.channel.ChannelHandlerContext;

import com.winterframework.efamily.server.exception.ServerException;
 
/**
 * 服务主处理接口
 * @ClassName
 * @Description
 * @author ibm
 * 2015年3月17日
 */
public interface INettyHandler {
	void active(final ChannelHandlerContext ctx) throws ServerException;
    void read(final ChannelHandlerContext ctx, final Object obj) throws ServerException;
    void inactive(final ChannelHandlerContext ctx) throws ServerException;
}
