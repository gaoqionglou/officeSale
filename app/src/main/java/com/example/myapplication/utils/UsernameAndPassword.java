package com.example.myapplication.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.text.TextUtils;
public class UsernameAndPassword {
	// 存账号和密码
	public static boolean saveUserInfo(Context context, String name,
									   String password) {
		try {
			// 防止包名改动寻找不到文件
			File filesDir = context.getFilesDir();
			File file = new File(filesDir, "mima.txt");
			FileOutputStream fos = new FileOutputStream(file);
			String data = name + "##" + password;
			fos.write(data.getBytes());
			fos.flush();
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	//取账号和密码
	public static String[] getUserInfo(Context context) {
		try {
			File filesDir = context.getFilesDir();
			File file = new File(filesDir, "mima.txt");
			if(!file.exists()) {
				file.createNewFile();
			}
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String text = br.readLine();
			if (!TextUtils.isEmpty(text)) {
				String[] split = text.split("##");
				return split;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
