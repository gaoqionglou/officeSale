package com.example.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

//我的-关于软件界面
public class AboutAppActivity extends Activity {
	private TextView tvText;
	private TextView tvTitle;
	private ImageView ivBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_other);
		tvText = (TextView) findViewById(R.id.tv_text);
		tvTitle=(TextView) findViewById(R.id.tv_titile);
		ivBack=(ImageView) findViewById(R.id.iv_back);

		//设置标题和软件描述的文字
		tvTitle.setText("关于软件");
		tvText.setText("		这是一款校园办公用品的销售系统，订购物品填写地址之后会送货上门或者前来自取，一定时间之内订购的物品，我们会同意送达，定期上架信物品，快来订购吧");

		//点击左侧按钮退出这个界面
		ivBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
