package com.example.myapplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//基本的工具类
public class Util {
	//判断是否有网络连接
	public static boolean isNetworkConnected(Context paramContext) {
		if (paramContext != null) {
			@SuppressLint("WrongConstant") NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext
					.getSystemService("connectivity")).getActiveNetworkInfo();
			if (localNetworkInfo != null)
				return localNetworkInfo.isAvailable();
		}
		return false;
	}

	//判断是否是正确号码
	public static boolean isPhoneNumberValid(String paramString) {
		boolean bool1 = Pattern
				.compile(
						"((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))")
				.matcher(paramString).matches();
		boolean bool2 = false;
		if (bool1)
			bool2 = true;
		return bool2;
	}
}