package com.example.myapplication.bean;

import cn.bmob.v3.BmobObject;

//物品的对象，和bomb数据中的表对应
public class Things extends BmobObject {
	public String name;
	public double price;
	private int type;
	private int bg;

	//set和get方法，一个给它设置值，另外是获取设置上的值
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getBg() {
		return bg;
	}
	public void setBg(int bg) {
		this.bg = bg;
	}
}
