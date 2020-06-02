package com.example.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

//我的-联系我们界面
public class ContactMeActivity extends Activity {
	private TextView tvText;
	private TextView tvTitle;

	private ImageView ivBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_other);

		//寻找布局中的控件
		tvText = (TextView) findViewById(R.id.tv_text);
		tvTitle = (TextView) findViewById(R.id.tv_titile);
		ivBack = (ImageView) findViewById(R.id.iv_back);

		//设置标题和联系人信息的文字
		tvTitle.setText("联系我们");
		tvText.setText("联系人：王玉新\n\n联系号码：15755058017\n\nQQ：863842947");

		//点击左侧按钮退出这个页面
		ivBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
