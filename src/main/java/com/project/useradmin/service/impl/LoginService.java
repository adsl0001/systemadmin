package com.project.useradmin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.project.useradmin.service.ILoginService;
/**
 * 用户登录服务类
 * @author lenovo
 *
 */
@Service
public class LoginService implements ILoginService {
	/**
	 * 固定的用户名和密码
	 */
	private String username = "admin";
	private String password = "admin";

	@Override
	public boolean login(String username, String password) {
		return StringUtils.equalsIgnoreCase(this.username, username)
				&& StringUtils.equalsIgnoreCase(this.password, password);
	}
}
