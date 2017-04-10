package com.sinaleju.lifecircle.app.exception;

/**
 * 服务器端接口异常
 * @author dpf
 *
 */
public class ADNetworkNotAvailableException extends Exception {
	private static final long serialVersionUID = -941436309411083520L;

	public ADNetworkNotAvailableException(String msg) {
		super(msg);
	}
	public ADNetworkNotAvailableException(String msg,Throwable e) {
		super(msg,e);
	}
}
