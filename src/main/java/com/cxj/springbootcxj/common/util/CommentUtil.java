/**
 * @author ChuXianJie
 */
package com.cxj.springbootcxj.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ChuXianJie
 * @date 2019年1月1日
 */
public class CommentUtil {
	/**
	 * 字符串转大写,如果原本大写,以_隔开
	 * 
	 * @param string
	 * @return
	 */
	public static String stringToUpperCase(String string){
		char[] array = string.toCharArray();
		StringBuffer sbf = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			// 如果是大写
			if (Character.isUpperCase(array[i])) {
				sbf.append("_").append(String.valueOf(array[i]).toUpperCase());
			} else {
				sbf.append(String.valueOf(array[i]).toUpperCase());
			}
		}
		return sbf.toString();

	}
	/**
	 * 描述：过滤字段值为null时不作修改，为‘’时才修改
	 * 
	 * @param updateFields
	 * @return
	 */
	public static Map<String, Object> autoFilterNullFields(Map<String, Object> updateFields) {
		Map<String, Object> relMap = new HashMap<>();
		for (String key : updateFields.keySet()) {
			if (updateFields.get(key) != null) {
				relMap.put(key, updateFields.get(key));
			}
			if ("".equals(updateFields.get(key))) {
				relMap.put(key, null);
			}
		}
		return relMap;
	}
}
