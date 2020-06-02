package com.example.myapplication.activity;

import java.util.ArrayList;
import java.util.List;
import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.com.wyxin.activity.GuideActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;

import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.myapplication.R;
import com.example.myapplication.utils.AlertDialogUtils;
import com.example.myapplication.utils.SharedPreferencesUtils;

//我的购物车界面
public class MyShopCarActivity extends Activity {
	private Context context;

	private String username;

	private ListView lvCar;
	private Button goPay;
	private ImageView ivBack;

	private List<Shopcar> list;
	private MyShopCarListAdapter myShopCarListAdapter;

	private String name;
	private String id;
	private double price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置文件布局寻找控件
		setContentView(R.layout.activity_shop_car);
		lvCar = (ListView) findViewById(R.id.lv_mycar);
		goPay = (Button) findViewById(R.id.go_pay);
		ivBack=(ImageView) findViewById(R.id.iv_back);

		context = getApplicationContext();
		//获取软件本地存储的账号
		username = SharedPreferencesUtils.getString(context, "username", "");

		//创建集合以及适配器，并且设置上去
		list = new ArrayList<Shopcar>();
		myShopCarListAdapter = new MyShopCarListAdapter(context, list);
		lvCar.setAdapter(myShopCarListAdapter);

		initData();

		//长按购物车一条数据提示删除框
		lvCar.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
										   final int position, long arg3) {
				// TODO Auto-generated method stub
				AlertDialogUtils.show(MyShopCarActivity.this, "确定从购物车删除该物品?",
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								deleteMessage(list.get(position).getObjectId());
							}
						});
				return true;
			}
		});

		//点击去支付
		goPay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < list.size(); i++) {
					if (i == 0) {
						name = list.get(i).getNum() + "个"
								+ list.get(i).getName();
						price = list.get(i).getPrice();
						id = list.get(i).getObjectId();
					} else {
						name = name + "；" + list.get(i).getNum() + "个"
								+ list.get(i).getName();
						price = price + list.get(i).getPrice();
						id = id + "," + list.get(i).getObjectId();
					}
				}
				Intent toOrder = new Intent(context,
						ChoosePayStateActivity.class);
				toOrder.putExtra("name", name);
				toOrder.putExtra("price", price);
				toOrder.putExtra("isCar", true);
				toOrder.putExtra("allId", id);
				startActivity(toOrder);
			}
		});

		//点击左侧按钮
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	//从bmob数据库根据id删除预约中的某条数据
	public void deleteMessage(String id) {
		Shopcar shopcar = new Shopcar();
		shopcar.setObjectId(id);
		shopcar.delete(new UpdateListener() {
			@Override
			public void done(BmobException e) {
				if(e==null){
					toast("删除成功");
					initData();
				}else {
					toast("删除失败");
				}
			}
		});
	}

	//从bmob数据库获取数据的方法
	public void initData() {
		// 通过在bmob数据中的的username这一项和用户的账号一样查询到数据就是这个用户的所有订单
		BmobQuery<Shopcar> query = new BmobQuery<Shopcar>();
		query.addWhereEqualTo("username", username);
		query.setLimit(500);
		query.findObjects(new FindListener<Shopcar>() {
			@Override
			public void done(List<Shopcar> obj, BmobException e) {
				if(e==null){
					list = obj;
					myShopCarListAdapter.refresh((ArrayList<Shopcar>) list);
				}else {
					toast("查询失败");
				}
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}

	public void toast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
}
