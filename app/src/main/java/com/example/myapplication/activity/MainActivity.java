package com.example.myapplication.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.com.wyxin.activity.GuideActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.example.myapplication.R;
import com.example.myapplication.fragment.fragmentMine;
import com.example.myapplication.utils.SharedPreferencesUtils;


//主界面（包含4个模块）
public class MainActivity extends FragmentActivity {
	private FragmentManager fm;
	private ArrayList<Fragment> frags = new ArrayList();
	private int i = 0;
	private RadioButton rb0;
	private RadioButton rb1;
	private RadioButton rb2;
	private RadioButton rb3;
	private RadioGroup rg;

	private String username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		username=SharedPreferencesUtils.getString(getApplicationContext(), "username", "");

		initView();

		initFragment();

		//同步主界面点击radiobutton和fragment的显示同步
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				FragmentTransaction ft = fm.beginTransaction();
				String tag = ""; // 用于存储每一个Fragment被添加到屏幕上设置的tag标识
				// 根据点击的Button设置对应的tag标识
				switch (checkedId) {
					case R.id.rb0:
						rbnoshow(i);
						i = 0;
						tag = "0";
						break;
					case R.id.rb1:
						rbnoshow(i);
						i = 1;
						tag = "1";
						break;
					case R.id.rb2:
						rbnoshow(i);
						i = 2;
						tag = "2";
						break;
					case R.id.rb3:
						rbnoshow(i);
						i = 3;
						tag = "3";
						break;
				}
				//把所有标签都加到集合中
				Fragment f = fm.findFragmentByTag(tag);
				if (f == null) {
					int num = Integer.parseInt(tag);
					ft.add(R.id.ll, frags.get(num), tag);
				}

				//判断点击的那个模块把其他3个模块隐藏，显示点击的那个模块，就是切换
				for (int i = 0; i < frags.size(); i++) {
					Fragment fragment = frags.get(i);
					if (fragment.getTag() != null) {
						if (fragment.getTag().toString().equals(tag)) {
							//显示
							ft.show(fragment);
						} else {
							//隐藏
							ft.hide(fragment);
						}
					}
				}
				ft.commit();
			}
		});
	}

	//寻找布局中的控件（下面4个点击按钮）
	private void initView() {
		rg = ((RadioGroup) findViewById(R.id.rg));
		rb0 = ((RadioButton) findViewById(R.id.rb0));
		rb1 = ((RadioButton) findViewById(R.id.rb1));
		rb2 = ((RadioButton) findViewById(R.id.rb2));
		rb3 = ((RadioButton) findViewById(R.id.rb3));
	}

	//判断哪个radiobutton被选择
	private void rbnoshow(int j) {
		RadioButton rb = (RadioButton) rg.getChildAt(j);
	}

	//把需要加载的fragment放到集合中，并默认首先显示第一个
	public void initFragment() {
		fm = getSupportFragmentManager();
		fm.beginTransaction();
		frags.add(new fragmentMine());
		fm.beginTransaction().add(R.id.ll, (Fragment) frags.get(0), "0")
				.commit();
	}

	//点击手机返回键
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
}
