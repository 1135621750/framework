package com.libertad.demo.common.token;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class BaseContext {
	public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

	
	public static Map<String, Object> getAttributes(){
        return threadLocal.get();
    }

    public static void setAttributes(Map<String, Object> attributes){

        if(attributes == null) {
            threadLocal.remove();
        }
        threadLocal.set(attributes);
    }

	public static void set(String key, Object value) {
		Map<String, Object> map = threadLocal.get();
		if (map == null) {
			map = new HashMap<String, Object>();
			threadLocal.set(map);
		}
		map.put(key, value);
	}

	public static Object get(String key) {
		Map<String, Object> map = threadLocal.get();
		if (map == null) {
			map = new HashMap<String, Object>();
			threadLocal.set(map);
		}
		return map.get(key);
	}
	
	public static void remove() {
		threadLocal.remove();
	}
	
	//用户数据
	
	public static String getUserName(){
		return returnObjectValue(get("userName"));
	}
	
	public static String getUserId(){
		return returnObjectValue(get("userId"));
	}
	
	public static long getUserIdlong(){
		String value = returnObjectValue(get("userId"));
		return StringUtils.isBlank(value) ? null : new Long(value);
	}
	
	private static String returnObjectValue(Object value) {
		return value == null ? null : value.toString();
	}
}
