package com.example.myapplication.bean;

import cn.bmob.v3.BmobObject;

//通知对象表，和bomb对象对应
public class Notice extends BmobObject {
	//通知对象，其中包含的属性
	private String content;
	private String title;

	//set和get方法，一个给它设置值，另外是获取设置上的值
	public String getContent() {
		return this.content;
	}

	public String getTitle() {
		return this.title;
	}

	public void setContent(String paramString) {
		this.content = paramString;
	}

	public void setTitle(String paramString) {
		this.title = paramString;
	}

}