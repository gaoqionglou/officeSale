package com.example.myapplication.bean;

import cn.bmob.v3.BmobObject;


//订单表，用户下单成功会在这个表中添加数据，并且管理人员可以通过操作bmob设置是否完成订单
public class Indent extends BmobObject{
	public String phone;//手机号码
	public String address;//宿舍地址
	public String name;//物品名
	public String username;//预订人的账号
	public String price;//订单的价格
	public boolean isfinish;//订单是否完成
	public boolean isPay;//订单是否支付


	public boolean isPay() {
		return isPay;
	}
	public void setPay(boolean isPay) {
		this.isPay = isPay;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isIsfinish() {
		return isfinish;
	}
	public void setIsfinish(boolean isfinish) {
		this.isfinish = isfinish;
	}

}
