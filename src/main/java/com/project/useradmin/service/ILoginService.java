package com.project.useradmin.service;

/**
 * 用户登录服务类
 * 
 * @author dbz
 *
 */
public interface ILoginService {

	/**
	 * 登录系统
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 登录成功返回true ，否则返回false
	 */
	public boolean login(String username, String password);
}
