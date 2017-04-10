package com.sinaleju.lifecircle.app.exception;

/**
 * 服务器端接口异常
 * @author dpf
 *
 */
public class ADRemoteException extends Exception {
	private static final long serialVersionUID = -941436309411083520L;
	private String msg;
	public ADRemoteException(String msg) {
		super(msg);
		this.msg = msg;
	}
	public String msg(){
		return msg;
	}
}
