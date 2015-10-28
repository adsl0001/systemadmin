package com.project.core.struts2.base;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

public class AbsBaseStruts2Action extends ActionSupport implements
		RequestAware, ServletRequestAware, ServletResponseAware {
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	protected static final String SESSION_NAME = "SESSION_NAME";
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	private static final long serialVersionUID = 7392884509104794491L;
	private Map<String, Object> requestMap = null;

	protected Object getSession() {
		Object object = this.request.getSession().getAttribute(SESSION_NAME);
		return object;
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public void setRequest(Map<String, Object> request) {
		this.setRequestMap(request);
	}

	private void setRequestMap(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

	public Map<String, Object> getRequestMap() {
		return requestMap;
	}

	protected void setSession(Object session) {
		HttpSession httpSession = this.request.getSession();
		httpSession.removeAttribute(SESSION_NAME);
		httpSession.setAttribute(SESSION_NAME, session);
	}

	protected void write(String text) throws Exception {
		PrintWriter writer = this.response.getWriter();
		this.response.setCharacterEncoding("UTF-8");
		this.response.setContentType("text/html");
		writer.write(text);
		writer.flush();
		writer.close();
	}

	protected void writeJson(Object obj) throws Exception {
		if (((obj instanceof Collection)) || (obj.getClass().isArray())) {
			JSONArray object = JSONArray.fromObject(obj);
			write(object.toString());
		} else {
			JSONObject object = JSONObject.fromObject(obj);
			write(object.toString());
		}
	}

	public void sendError(Object object) throws Exception {
		JSONObject result = new JSONObject();
		result.put("success", Boolean.valueOf(false));
		result.put("errorInfo", object);
		writeJson(result);
	}

	public void sendSuccess() throws Exception {
		JSONObject result = new JSONObject();
		result.put("success", Boolean.valueOf(true));
		writeJson(result);
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

}
