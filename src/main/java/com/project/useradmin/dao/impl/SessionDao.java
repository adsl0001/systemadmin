package com.project.useradmin.dao.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SessionDao implements ISessionDao {
	@Resource
	private RedisTemplate<Serializable, Serializable> redisTemplate;

	public void saveSession(HttpSession session) {
		@SuppressWarnings("unchecked")
		Enumeration<String> keys = (Enumeration<String>) session
				.getAttributeNames();
		String key = "session_" + session.getId();
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		while (keys.hasMoreElements()) {
			String string = (String) keys.nextElement();
			map.put(string, (Serializable) session.getAttribute(string));
		}
		redisTemplate.delete(key);
		redisTemplate.opsForHash().putAll(key, map);
		int t = session.getMaxInactiveInterval();
		if (t > 0) {
			Calendar lastDate = Calendar.getInstance();
			lastDate.setTimeInMillis(session.getLastAccessedTime());
			lastDate.add(Calendar.SECOND, t);
			redisTemplate.expireAt(key, lastDate.getTime());
		}
	}

	public Map<Object, Object> getSessionById(String sessionId) {
		Map<Object, Object> map = redisTemplate.opsForHash().entries(
				"session_" + sessionId);
		return map;
	}
}
