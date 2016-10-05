package com.shops.caching;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

public class CachingKeyGenerator implements KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		return "completeShopingList";
	}

}
