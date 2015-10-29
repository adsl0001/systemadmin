package com.project.useradmin.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.project.core.struts2.base.AbsBaseStruts2Action;
import com.project.useradmin.filter.LoginFilter;
import com.project.useradmin.service.ILoginService;

/**
 * 用户登录
 * 
 * @author lenovo
 *
 */
@Controller
@Scope("prototype")
@Namespace("test")
public class LoginAction extends AbsBaseStruts2Action {
	@Resource
	private ILoginService loginService;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;

	/**
	 * 检查用户名密码
	 * 
	 * @throws Exception
	 */
	public void checkUser() throws Exception {
		boolean loginStatus = loginService.login(username, password);
		if (loginStatus) {
			this.getRequest().getSession()
					.setAttribute(LoginFilter.USERNAME, username);
			this.getRequest()
					.getSession()
					.setAttribute("sessionId",
							this.getRequest().getSession().getId());
			this.sendSuccess();
		} else {
			this.sendError("用户名或密码不正确，请修改后重试！");
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
