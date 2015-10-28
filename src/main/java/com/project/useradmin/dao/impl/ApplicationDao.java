package com.project.useradmin.dao.impl;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.project.useradmin.dao.IApplicationDao;
import com.project.useradmin.model.Application;

/**
 * IApplicationDao实现类
 * 
 * @author dbz
 */
@Repository
public class ApplicationDao implements IApplicationDao {
	@Resource
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	/**
	 * 应用信息存活时间
	 */
	private static int TTL = 10;

	@Override
	public void registApplication(final Application application) {
		// 先删后保存
		redisTemplate.delete(application.getName());
		redisTemplate.opsForValue().set(application.getName(), application, TTL, TimeUnit.SECONDS);
	}

	@Override
	public Application getApplication(String appName) {
		return (Application) redisTemplate.opsForValue().get(appName);
	}
}
