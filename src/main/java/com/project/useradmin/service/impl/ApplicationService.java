package com.project.useradmin.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.project.model.Application;
import com.project.useradmin.dao.IApplicationDao;
import com.project.useradmin.service.IApplicationService;
/**
 * 应用信息服务类
 * 
 * @author dbz
 *
 */
@Service
public class ApplicationService implements IApplicationService {
	@Resource
	IApplicationDao applicationDao ;
	@Override
	public Application getApplication(String appName) {
		return applicationDao.getApplication(appName);
	}

	@Override
	public void registApplication(Application application) {
		this.applicationDao.registApplication(application);

	}
}
