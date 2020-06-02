package com.example.myapplication.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

public class _User extends BmobUser {
	//用户信息对象表，其中包含的属性，和bomb数据中的表对应
	private String phone;
	private String gender;
	private String name;
	private String address;

	//set和get方法，一个给它设置值，另外是获取设置上的值
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	//set和get方法，一个给它设置值，另外是获取设置上的值
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String paramString) {
		this.phone = paramString;
	}

}