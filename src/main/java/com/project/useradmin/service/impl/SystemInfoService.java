package com.project.useradmin.service.impl;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.useradmin.model.Application;
import com.project.useradmin.model.SystemInfo;
import com.project.useradmin.service.IApplicationService;
import com.project.useradmin.service.ISystemInfoService;
import com.project.useradmin.utils.HttpUtil;
import com.sun.management.OperatingSystemMXBean;

/**
 * 系统信息服务类
 * 
 * @author dbz
 * 
 */
@SuppressWarnings("restriction")
@Service
public class SystemInfoService implements ISystemInfoService {
	private static Logger logger = LoggerFactory
			.getLogger(SystemInfoService.class);
	@Resource
	private IApplicationService applicationService;

	/**
	 * 注册的系统管理应用名称
	 */
	private static String remoteApplicationName = "application.userAdmin";
	/**
	 * 请求的路径
	 */
	@Value("${remotePath}")
	private static String path;

 
	@Override
	public String getServerPort(String protocol) {
		MBeanServer mBeanServer = null;
		if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
			mBeanServer = (MBeanServer) MBeanServerFactory
					.findMBeanServer(null).get(0);
		}
		Set<ObjectName> names = null;
		try {
			names = mBeanServer.queryNames(new ObjectName(
					"Catalina:type=Connector,*"), null);
		} catch (MalformedObjectNameException e1) {
			logger.error("读取端口失败", e1);
			return "";
		}
		Iterator<ObjectName> it = names.iterator();
		ObjectName oname = null;
		try {
			while (it.hasNext()) {
				oname = it.next();
				String pvalue = (String) mBeanServer.getAttribute(oname,
						"scheme");
				if (protocol.equals(pvalue)) {
					return ((Integer) mBeanServer.getAttribute(oname, "port"))
							.toString();
				}
			}
		} catch (AttributeNotFoundException | InstanceNotFoundException
				| MBeanException | ReflectionException e) {
			logger.error("读取端口失败", e);
		}
		return "";
	}

	@Override
	public String getIp() {
		// 如果没有配置外网IP则返回本地IP
		String localip = null;
		// 外网IP
		String netip = null;
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 外网IP
						netip = ip.getHostAddress() + ";";
					} else if (ip.isSiteLocalAddress()
							&& !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
						localip = ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			logger.error("查找ip地址出错：", e);
		}
		if (StringUtils.isNotBlank(netip)) {
			return netip;
		} else {
			return localip;
		}

	}

	@Override
	public String getSystemInfo( ) {
		Application application = this.applicationService
				.getApplication(remoteApplicationName);
		if (application == null) {
			return "未找到在线的系统管理服务器";
		}
		return HttpUtil.postMessage(this.getServiceUrl(application), "");
	}
	/**
	 * 获取服务url
	 * @param application 应用信息
	 * @return  服务地址
	 */
	private String getServiceUrl(Application application) {
		StringBuilder builder = new StringBuilder();
		builder.append("http://").append(application.getIp()).append(":")
				.append(application.getPort()).append(path);
		return builder.toString();
	}
}
