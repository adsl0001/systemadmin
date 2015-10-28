package com.project.useradmin.service.impl;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.useradmin.model.Application;
import com.project.useradmin.service.IApplicationService;
import com.project.useradmin.service.ISystemInfoService;

/**
 * 初始化应用服务类<br>
 * 在这个类在初始化时注册应用程序，间隔 {@link InitApplicationService.period}毫秒注册一次，以保证服务的在线状态。
 * 
 * @author dbz
 * 
 */
@Service
public class InitApplicationService {
	@Resource
	IApplicationService applicationService;
	@Resource
	ISystemInfoService systemInfoService;
	/**
	 * 应用名称，在配置文件中配置，启动时会按这个名称注册到数据库中
	 */
	@Value("${applicationName}")
	private String applicationName;
	/**
	 * 本机ip
	 */
	private static String ip;
	/**
	 * http协议端口
	 */
	private static String port;

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * 协议名称
	 */
	private static String protocol = "http";
	/**
	 * 应用信息
	 */
	private static Application application;
	/**
	 * 注册服务执行间隔时间
	 */
	private static long period = 9 * 1000;

	@PostConstruct
	public void init() {
		regist();
		// 间隔9s注册一次
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				regist();
			}
		}, Calendar.getInstance().getTime(), period);
	}

	/**
	 * 注册应用信息
	 */
	private void regist() {
		if (ip == null) {
			ip = systemInfoService.getIp();
		}
		if (port == null) {
			port = systemInfoService.getServerPort(protocol);
		}
		if (application == null) {
			application = new Application();
			application.setIp(ip);
			application.setPort(port);
			application.setName(applicationName);
		}
		this.applicationService.registApplication(application);
	}
}