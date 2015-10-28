package com.project.useradmin.dao;

import com.project.useradmin.model.Application;

/**
 * application数据访问接口
 * 
 * @author dbz
 */
public interface IApplicationDao {
	/**
	 * 注册应用
	 * @param application
	 *            应用信息
	 */
	public void registApplication(final Application application);

	/**
	 * 根据应用名称查找应用信息
	 * @param appName 应用名称
	 */
	public Application getApplication(String appName);
}
