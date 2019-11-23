package com.zyw.common.utils;

public class StringUtil {

	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		}
		return false;
	}
	
}
