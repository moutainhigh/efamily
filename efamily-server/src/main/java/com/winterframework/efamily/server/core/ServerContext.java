package com.winterframework.efamily.server.core;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerContext {
	protected Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();
	private final static ThreadLocal<ServerContext> context = new ThreadLocal<ServerContext>();
	private final Long userId;
	private final ChannelHandlerContext channelHandlerContext;

	// private final EjlUser user;
	public ServerContext(Long userId,
			ChannelHandlerContext channelHandlerContext) {
		this.userId = userId;
		this.channelHandlerContext = channelHandlerContext;
		// this.user=null;
	}

	/*
	 * public ServerContext(EjlUser user){ this.user=user; this.userId=null; }
	 */
	public Long getUserId() {
		return userId;
	}

	/*
	 * public EjlUser getCurUser(){ return user; }
	 */
	public void set(String key, Object value) {
		attributes.put(key, value);
	}

	public Object get(String key) {
		return attributes.get(key);
	}

	public ChannelHandlerContext getChannelHandlerContext() {
		return channelHandlerContext;
	}

}
