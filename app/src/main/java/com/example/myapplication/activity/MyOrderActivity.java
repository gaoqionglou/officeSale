package com.example.myapplication.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.com.wyxin.activity.GuideActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import com.example.myapplication.R;
import com.example.myapplication.utils.SharedPreferencesUtils;

//我的订单界面
public class MyOrderActivity extends Activity {
	private ListView lvOrder;
	private MyOrderAdapter thingsAdapter;
	private List<Indent> thingsList;

	private ImageView ivBack;

	private String username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myorder);

		//获取到我的账号
		username=SharedPreferencesUtils.getString(getApplicationContext(), "username", "");
		//寻找布局中的控件
		ivBack=(ImageView) findViewById(R.id.iv_back);
		lvOrder = (ListView) findViewById(R.id.lv_order);

		thingsList = new ArrayList<Indent>();
		thingsAdapter = new MyOrderAdapter(getApplicationContext(), thingsList);
		lvOrder.setAdapter(thingsAdapter);

		initData();

		//点击左侧按钮退出这个页面
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void initData(){
		//通过在bmob数据中的的username这一项和用户的账号一样查询到数据就是这个用户的所有订单
		BmobQuery<Indent> query = new BmobQuery<Indent>();
		query.addWhereEqualTo("username", username);
		query.setLimit(500);
		query.findObjects(new FindListener<Indent>() {
			@Override
			public void done(List<Indent> list, BmobException e) {
				if(e==null){
					thingsList = list;
					thingsAdapter.refresh((ArrayList<Indent>) thingsList);
				}else {
					toast("查询失败");
				}
			}
		});
	}

	public void toast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
}
