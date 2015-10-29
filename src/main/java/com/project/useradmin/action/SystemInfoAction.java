package com.project.useradmin.action;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.project.core.struts2.base.AbsBaseStruts2Action;
import com.project.useradmin.service.ISystemInfoService;

/**
 * 系统信息
 * 
 * @author dbz
 *
 */
@Controller
@Scope("prototype")
@Namespace("test")
public class SystemInfoAction extends AbsBaseStruts2Action {

	private static final long serialVersionUID = 6783909754561104314L;
	@Resource
	private ISystemInfoService systemInfoService;

	/**
	 * 获取系统管理应用的系统信息
	 * 
	 * @throws Exception
	 */
	public void getSystemInfo() throws Exception {
		String data = this.systemInfoService.getSystemInfo(this.getRequest()
				.getSession().getId());
		if (!StringUtils.startsWith(data, "{")) {
			this.sendError(data == null ? "取数出错" : data);
		} else {
			this.writeJson(data);
		}
	}

}
