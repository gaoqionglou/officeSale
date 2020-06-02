package com.example.myapplication.activity;

import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.com.wyxin.activity.GuideActivity;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.myapplication.R;
import com.example.myapplication.utils.AlertDialogUtils;
import com.example.myapplication.utils.SharedPreferencesUtils;

public class PayActivity extends Activity {

	private RadioGroup rgPay;
	private RadioButton rbZhifubao;
	private RadioButton rbWeixin;
	private RadioButton rbYinlian;
	private ImageView ivBack;

	private TextView tvPay;

	private String TYPE = "支付宝";
	private String name;
	private String allId = "";
	private String price;
	private String address;
	private String phone;
	private boolean isCar;

	private Context context;
	private String username;

	private String[] idArr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pay);

		context = getApplicationContext();

		// 获取软件在手机中存储的文件中的数据值
		username = SharedPreferencesUtils.getString(context, "username", "");

		// 接收上一个界面传递过来的值
		name = getIntent().getStringExtra("name");
		allId = getIntent().getStringExtra("allId");
		price = getIntent().getStringExtra("price");
		address = getIntent().getStringExtra("address");
		phone = getIntent().getStringExtra("phone");
		isCar=getIntent().getBooleanExtra("isCar", isCar);

		// 寻找布局中的控件
		rgPay = (RadioGroup) findViewById(R.id.RG_pay);
		rbZhifubao = (RadioButton) findViewById(R.id.RB_zhifubao);// 支付宝
		rbWeixin = (RadioButton) findViewById(R.id.RB_weixin);// 微信
		rbYinlian = (RadioButton) findViewById(R.id.RB_yinlian);// 银联
		tvPay = (TextView) findViewById(R.id.tv_pay);
		ivBack = (ImageView) findViewById(R.id.iv_back);

		// 支付方式
		rgPay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				int radioButtonId = radioGroup.getCheckedRadioButtonId();
				switch (radioButtonId) {
					case R.id.RB_zhifubao:// 支付宝
						TYPE = "支付宝";// 支付宝支付
						break;
					case R.id.RB_weixin:// 微信
						TYPE = "微信";// 微信支付
						break;
					case R.id.RB_yinlian:// 银联
						TYPE = "银联";// 银联支付
						break;
				}
			}
		});

		// 点击右侧支付按钮
		tvPay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 弹窗，点击支付确定框
				AlertDialogUtils.show(PayActivity.this, "确定使用" + TYPE + "支付"
						+ price + "元？", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (isCar) {
							idArr = convertStrToArray(allId);
							for (int i = 0; i < idArr.length; i++) {
								deleteMessage(idArr[i]);
							}
						}
						addOrder();
					}
				});
			}
		});

		// 点击左侧按钮退出这个页面
		ivBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void addOrder() {
		// 支付，吧订单信息设置到订单对象上，并且存储到bmob数据中
		Indent indent = new Indent();
		indent.setPhone(phone);
		indent.setAddress(address);
		indent.setName(name);
		indent.setPrice(price);
		indent.setPay(true);
		indent.setUsername(username);
		indent.setIsfinish(false);
		indent.save(new SaveListener<String>() {
			@Override
			public void done(String s, BmobException e) {
				if(e==null){
					toast("支付成功");
					finish();
				}else {
					toast("支付失败");
					e.printStackTrace();
				}
			}
		});
	}

	// 删除购物车的物品
	public void deleteMessage(String id) {
		Shopcar shopcar = new Shopcar();
		shopcar.setObjectId(id);
		shopcar.delete(new UpdateListener() {
			@Override
			public void done(BmobException e) {
				if(e==null){
					toast("delete success");
				}else {
					toast("delete fail");
					e.printStackTrace();
				}
			}
		});
	}

	// 使用String的split 方法
	public static String[] convertStrToArray(String str) {
		String[] strArray = null;
		strArray = str.split(","); // 拆分字符为"," ,然后把结果交给数组strArray
		return strArray;
	}

	public void toast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

}
