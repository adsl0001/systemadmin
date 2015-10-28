package com.project.useradmin.service;

import com.project.useradmin.model.Application;

/**
 * 应用信息服务接口
 * @author dbz
 *
 */
public interface IApplicationService {
	/**
	 * 注册应用信息
	 * @param application 应用信息
	 */
	public void registApplication( Application application);
	/**
	 * 根据名称获取应用信息，用以检测应用是否在线
	 * @param appName 应用名称 
	 * @return 如果在线返回应用信息，否则返回null
	 */
	public Application getApplication(String appName);
}
