/**
 * @author ChuXianJie
 */
package com.cxj.springbootcxj.common.util;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

/**
 * @author ChuXianJie
 * @date 2019年1月1日
 */
public class MapUtil {
	private static final String MESSAGE = "MESSAGE";
	private static final String STATUS = "STATUS";
	private static final String SUCCESS = "SUCCESS";

	public static Map<String, Object> toMap() {
		return new HashMap<String, Object>();
	}

	public static Map<String, Object> toMap(String key, Object value) {
		Map<String, Object> map = new HashMap<>();
		map.put(key, value);
		return map;
	}

	public static Map<String, Object> toMap(String k1, Object v1, String k2, Object v2) {
		Map<String, Object> map = new HashMap<>();
		map.put(k1, v1);
		map.put(k2, v2);
		return map;
	}

	public static Map<String, Object> toMap(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
		Map<String, Object> map = new HashMap<>();
		map.put(k1, v1);
		map.put(k2, v2);
		map.put(k3, v3);
		return map;
	}

	public static Map<String, Object> toSuccess() {
		return toMap(STATUS, SUCCESS);
	}

	public static Map<String, Object> toSuccess(Map<String, Object> map) {
		if (!map.containsKey(STATUS)) {
			map.put(STATUS, SUCCESS);
		}
		return map;
	}

	public static boolean isSuccess(Map<String, Object> map) {
		String success = (String) map.get(STATUS);
		if (success != null) {
			if (success.equalsIgnoreCase(SUCCESS)) {
				return true;
			} else {
			}
			return false;
		}
		return false;
	}

	public static Map<String, Object> toMessage(Map<String, Object> map, String message) {
		map.put(MESSAGE, message);
		return map;
	}

	/**
	 * map转object对象
	 * 
	 * @author ChuXianJie
	 * @param map
	 * @param beanClass
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass)
			throws IllegalAccessException, InvocationTargetException, InstantiationException {
		if (map == null) {
			return null;
		}
		// 注册转换器
		ConvertUtils.register(new Converter() {

			@Override
			public <T> T convert(Class<T> type, Object value) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try {
					return (T) simpleDateFormat.parse(value.toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
		}, Date.class);
		Object obj = beanClass.newInstance();
		org.apache.commons.beanutils.BeanUtils.populate(obj, map);
		return obj;
	}

	/**
	 * object对象转map
	 * 
	 * @author ChuXianJie
	 * @param obj
	 * @return
	 */
	public static Map<?, ?> objectToMap(Object obj) {
		if (obj == null) {
			return null;
		}
		return new org.apache.commons.beanutils.BeanMap(obj);
	}

}
