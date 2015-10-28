package com.project.useradmin.service;

import com.project.useradmin.model.Application;
import com.project.useradmin.model.SystemInfo;

/**
 * 系统信息服务接口
 * 
 * @author dbz
 * 
 */
public interface ISystemInfoService {
	/**
	 * 获取系统管理系统的系统信息
	 * 
	 * @param application
	 *            系统管理系统的应用信息
	 * @return 系统管理的系统信息json串
	 */
	public String getSystemInfo();

	/**
	 * 获取服务端口
	 * 
	 * @param protocol
	 *            协议
	 * @return 协议对应的端口号
	 */
	public String getServerPort(String protocol);

	/**
	 * 获取本机ip地址
	 * 
	 * @return 本机所有ip地址，以分号分隔
	 */
	public String getIp();

 
}
