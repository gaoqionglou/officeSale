package com.example.myapplication.bean;

import cn.bmob.v3.BmobObject;

//留言对象表，和bomb表对应
public class Message extends BmobObject {
	private String userName;
	private String title;
	private String content;
	private String name;

	//set和get方法，一个给它设置值，另外是获取设置上的值
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
