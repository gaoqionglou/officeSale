package com.example.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

//通知-通知详情界面
public class NewsContentActivity extends Activity {
	private ImageView ivBack;
	private TextView tvTitle;
	private TextView tvContent;
	private TextView tvTime;

	private String title;
	private String content;
	private String time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_notice);

		//寻找布局中的控件
		ivBack = (ImageView) findViewById(R.id.iv_back);
		tvTitle = (TextView) findViewById(R.id.news_title);
		tvContent = (TextView) findViewById(R.id.news_content);
		tvTime = (TextView) findViewById(R.id.news_time);

		//获取上个界面传递过来的值
		title=getIntent().getStringExtra("title");
		content=getIntent().getStringExtra("content");
		time=getIntent().getStringExtra("time");

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
		//设置通知的标题时间和内容
		tvTitle.setText("《"+title+"》");
		tvContent.setText(content);
		tvTime.setText("发布时间："+time);
	}
}
