package com.example.myapplication.activity;

import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.fragment.*;
import com.example.myapplication.utils.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



import com.example.myapplication.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

//物品-选择支付方式界面
public class ChoosePayStateActivity extends Activity {
	private RadioGroup rgPay;
	private RadioButton rbTohome;
	private RadioButton rbPay;

	private TextView tvPay;
	private ImageView ivBack;

	private LinearLayout llAdd;
	private ImageView ivJian;
	private ImageView ivJia;
	private EditText etNum;
	private Button btnAdd;

	private String TYPE = "1";// 默认直接支付
	private String name;
	private String allId;
	private double price;
	private int gdNum;
	private boolean isCar;

	private int count;
	private String num;

	private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_pay_state);

		// 获取本地存储的用户账号
		username = SharedPreferencesUtils.getString(getApplicationContext(),
				"username", "");

		// 接收上一个界面传递过来的值
		name = getIntent().getStringExtra("name");
		allId = getIntent().getStringExtra("allId");
		price = getIntent().getDoubleExtra("price", 0);
		gdNum = getIntent().getIntExtra("gdNum", -1);
		isCar = getIntent().getBooleanExtra("isCar", false);

		// 寻找布局中的控件
		rgPay = (RadioGroup) findViewById(R.id.RG_pay);
		rbTohome = (RadioButton) findViewById(R.id.rb_pay_tohome);// 选择货到付款
		rbPay = (RadioButton) findViewById(R.id.rb_direct_pay);// 选择直接支付
		tvPay = (TextView) findViewById(R.id.tv_pay);
		ivBack = (ImageView) findViewById(R.id.iv_back);

		llAdd = (LinearLayout) findViewById(R.id.ll_add_car);
		ivJian = (ImageView) findViewById(R.id.car_jian);
		ivJia = (ImageView) findViewById(R.id.car_jia);
		etNum = (EditText) findViewById(R.id.et_num);
		btnAdd = (Button) findViewById(R.id.add_car);

		//判断如果是从购物车那边点击过来的，就把加入购物车那块隐藏
		if (isCar) {
			llAdd.setVisibility(View.GONE);
		}

		// 点击左侧按钮退出这个页面
		ivBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		// 支付方式（点击选择会打对号）
		rgPay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				int radioButtonId = radioGroup.getCheckedRadioButtonId();
				switch (radioButtonId) {
					case R.id.rb_direct_pay:// 选择直接支付
						TYPE = "1";// 选择直接支付
						break;
					case R.id.rb_pay_tohome:// 选择货到付款
						TYPE = "2";// 选择货到付款
						break;
				}
			}
		});

		// 点击确定按钮跳转到填写信息的界面
		tvPay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent toWrite = new Intent(getApplicationContext(),
						WriteInfoActivity.class);
				toWrite.putExtra("name", name);
				toWrite.putExtra("price", price);
				toWrite.putExtra("type", TYPE);
				toWrite.putExtra("gdNum", gdNum);
				toWrite.putExtra("isCar", isCar);
				toWrite.putExtra("allId", allId);
				startActivity(toWrite);
				finish();
			}
		});

		// 减数量
		ivJian.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				num = etNum.getText().toString().trim();
				count = Integer.parseInt(num);
				count--;
				etNum.setText(count + "");
			}
		});
		// 加数量
		ivJia.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//获取文本框的文字赋值给num
				num = etNum.getText().toString().trim();
				count = Integer.parseInt(num);
				count++;
				etNum.setText(count + "");
			}
		});

		//点击加入购物车
		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addCar();
			}
		});
	}

	//把选择的数据加入到bmob数据库中
	public void addCar() {
		num = etNum.getText().toString().trim();
		count = Integer.parseInt(num);
		if ("".equals(num)) {
			toast("数量不能为空");
		}else if (count <= 0) {
			toast("请填写正确的数量");
		} else {
			// 创建一个Shopcar对象设置好参数，通过bmob的特定方法存储到bmob数据库中
			Shopcar shopcar = new Shopcar();
			shopcar.setUsername(username);
			shopcar.setName(name);
			shopcar.setPrice(price * count);
			shopcar.setNum(count);
			shopcar.save(new SaveListener<String>() {
				@Override
				public void done(String s, BmobException e) {
					if(e==null){
						toast("加入成功！");
						finish();// 关闭这个页面
					}else{
						toast("加入失败！");
						e.printStackTrace();
					}
				}
			});
		}
	}

	// 弹出提示框的方法
	public void toast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
}
