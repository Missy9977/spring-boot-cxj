/**
 * @author ChuXianJie
 */
package com.cxj.springbootcxj.cxjUtil;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
 * @author ChuXianJie
 * @date 2019年1月1日
 */
public class ValidateUtil {
//	public static boolean isEmpty(Object obj) {
//		if (obj instanceof String) {
//			return obj == null || (String)obj
//		}
//		return true;
//	}
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}

	/**
	 * 科学计数法的id转为String
	 * 
	 * @param id
	 * @return
	 */
	public static Integer idToInteger(Object id) {
//		if (id == null)
//			return null;
//		BigDecimal bd = new BigDecimal((Double) id);
//		return bd.intValue();

		BigDecimal bd = new BigDecimal(-1);
		if (id == null)
			return null;
		if (id instanceof Integer)
			bd = new BigDecimal((Integer) id);
		if (id instanceof Double)
			bd = new BigDecimal((Double) id);
		if (id instanceof String)
			bd = new BigDecimal((String) id);
		return bd.intValue();
	}

	/**
	 * MD5加密
	 * 
	 * @author ChuXianJie
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String md5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// 确定计算方法
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		// 加密
		return base64en.encode(md5.digest(str.getBytes("UTF-8")));
	}
}
