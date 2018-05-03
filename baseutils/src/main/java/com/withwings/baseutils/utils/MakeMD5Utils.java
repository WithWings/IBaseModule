package com.withwings.baseutils.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("unused")
public class MakeMD5Utils {

    public static String getMD5(String text) {
		String result = "";
		// 防止用户输入字符串过短，被暴力破解
		// 有些第三方将所有可能列出来进行比对，这种暴力破解全靠数据库对比。字符串越长，越难比较出来。
		// text = "llwangmiss" + text + "manmanqingyi";
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] digest = md.digest(text.getBytes());
			StringBuilder sb = new StringBuilder();
			// sb.length()恒等于16
			for (byte b : digest) {
				// 将得到的byte数据通过补位的方式获得int类型的整数
				// 将int类型的数字，转换为16进制数。
				// ---0x56 以0x开头的数为16进制数
				// ---056 以0开头的数为8进制数
				// ---56 什么也不写为10进制数
				// ---0b110110 以0b开头的数是2进制数
				int single = b & 0xFF;
				// 将16进制数转换成字符串使用。
				String hexString = Integer.toHexString(single);
				// 判断字符串是否为一位，如果是，在前面补0；
				if (hexString.length() == 1) {
					hexString = "0" + hexString;
				}
				// 将字符串通过缓冲字符串拼接
				sb.append(hexString);
				// 可以不写trim，因为本身就是字符串流。而且也不可能生成空格。
				result = sb.toString().trim();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}
}
