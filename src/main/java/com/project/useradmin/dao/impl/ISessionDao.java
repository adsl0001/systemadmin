package com.project.useradmin.dao.impl;

import java.util.Map;

import javax.servlet.http.HttpSession;
/**
 * 持久化session数据
 * @author dbz
 *
 */
public interface ISessionDao {
	/**
	 * 保存session中的数据
	 * @param session 
	 */
	public abstract void saveSession(HttpSession session);
	/**
	 * 根据sessionid获取session中保存的值
	 * @param sessionId  
	 */
	public abstract Map<Object, Object> getSessionById(String sessionId);

}