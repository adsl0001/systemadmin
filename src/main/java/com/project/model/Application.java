package com.project.model;

import java.io.Serializable;

/**
 * 应用程序信息
 * 
 * @author dbz
 *
 */
public class Application implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1891062480087731286L;
	/**
	 * 系统名称
	 */
	private String name;
	/**
	 * ip地址
	 */
	private String ip;
	/**
	 * http服务端口
	 */
	private String port;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
 

}

