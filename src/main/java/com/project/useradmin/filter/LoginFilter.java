package com.project.useradmin.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;

import com.project.useradmin.dao.impl.SessionDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 登录过滤器
 * 
 * @author dbz
 *
 */
public class LoginFilter implements Filter {
	@SuppressWarnings("unused")
	private FilterConfig filterCon = null;
	/**
	 * session中存储用户信息的字段
	 */
	public static String USERNAME = "username";

	@Override
	public void init(FilterConfig config) throws ServletException {
		filterCon = config;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		// 取得请求的URL
		String url = httpRequest.getRequestURL().toString();
		// 对登录与注销请求直接放行,不予拦截
		if (url.indexOf("/login.jsp") != -1
				|| url.indexOf("checkUser.do") != -1) {
			filterChain.doFilter(request, response);
		} else {
			// 验证Session是否过期
			if (!httpRequest.isRequestedSessionIdValid()) {
				// session过期,转向session过期提示页,最终跳转至登录页面
				if ("XMLHttpRequest".equals(httpRequest
						.getHeader("x-requested-with"))) {
					// ajax
					httpResponse.addHeader("__timeout", "true");
					JSONObject result = new JSONObject();
					result.put("success", Boolean.valueOf(false));
					result.put("errorInfo", "session超时");
					this.writeJson(httpResponse, result);
					return;
				} else {
					httpResponse.sendRedirect(httpRequest.getContextPath()
							+ "/login.jsp");
				}
			} else {
				String userInfo = (String) httpRequest.getSession()
						.getAttribute(LoginFilter.USERNAME);
				// 验证是否已经登录
				if (userInfo == null) {
					// session过期,转向session过期提示页,最终跳转至登录页面
					if ("XMLHttpRequest".equals(httpRequest
							.getHeader("x-requested-with"))) {// ajax
						httpResponse.addHeader("__timeout", "true");
						JSONObject result = new JSONObject();
						result.put("success", Boolean.valueOf(false));
						result.put("errorInfo", "session超时");
						this.writeJson(httpResponse, result);
						return;
					} else
						httpResponse.sendRedirect(httpRequest.getContextPath()
								+ "/login.jsp");
				} else {
					// 已登录直接放行
					SessionDao sessionDao = (SessionDao) ContextLoader.getCurrentWebApplicationContext()
						.getBean("sessionDao");
					sessionDao.saveSession(httpRequest.getSession());
					filterChain.doFilter(request, response);
				}
			}
		}
	}

	/**
	 * 发送json数据
	 * 
	 * @param response
	 *            响应对象
	 * @param obj
	 *            要发送的对象
	 */
	private void writeJson(HttpServletResponse response, Object obj) {
		Object object;
		if (((obj instanceof Collection)) || (obj.getClass().isArray())) {
			object = JSONArray.fromObject(obj);
		} else {
			object = JSONObject.fromObject(obj);
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.write(object.toString());
		writer.flush();
		writer.close();
	}

	@Override
	public void destroy() {
		filterCon = null;
	}
}
