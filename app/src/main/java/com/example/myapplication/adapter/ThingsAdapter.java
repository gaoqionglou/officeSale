package com.example.myapplication.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.fragment.*;
import com.example.myapplication.utils.*;

import com.example.myapplication.R;

//菜谱列表适配器，往listView（列表）放入数据
public class ThingsAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater = null;
	private List<Things> mLabsList = null;

	private int[] bgIcon = { R.drawable.bg1, R.drawable.bg2, R.drawable.bg3,
			R.drawable.bg4, R.drawable.bg5, R.drawable.bg6, R.drawable.bg7,
			R.drawable.bg8, R.drawable.bg9, R.drawable.bg10 };

	// 初始化适配器的方法，需要传递上下文和数据集合
	public ThingsAdapter(Context context, List<Things> mLabsList) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	// 判断数据的长度
	@Override
	public int getCount() {
		return mLabsList == null ? 0 : mLabsList.size();
	}

	@Override
	public Object getItem(int position) {
		return mLabsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 刷新列表中的数据
	public void refresh(ArrayList<Things> list) {
		mLabsList = list;
		notifyDataSetChanged();
	}

	// 将集合中的数据分别赋值到listview上，展示出来
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ThingsHolder thingsHolder;
		if (convertView == null) {
			//设置列表样式的布局并且寻找控件
			convertView = mInflater.inflate(R.layout.gridview_order, null);
			thingsHolder = new ThingsHolder();
			thingsHolder.tvName = (TextView) convertView
					.findViewById(R.id.tv_name);
			thingsHolder.tvPrice = (TextView) convertView
					.findViewById(R.id.tv_price);
			thingsHolder.ivPic = (ImageView) convertView
					.findViewById(R.id.things_pic);
			convertView.setTag(thingsHolder);
		} else {
			thingsHolder = (ThingsHolder) convertView.getTag();
		}
		//判断设置物品图片
		if(mLabsList.get(position).getBg()<=10){
			thingsHolder.ivPic.setImageResource(bgIcon[mLabsList.get(position).getBg()-1]);
		}else{
			thingsHolder.ivPic.setImageResource(R.drawable.no_pic);
		}

		thingsHolder.tvName.setText(mLabsList.get(position).getName());// 设置物品名称
		thingsHolder.tvPrice.setText(mLabsList.get(position).getPrice() + "元");// 设置物品价格
		return convertView;
	}

	public class ThingsHolder {
		public TextView tvName; // 名称的控件
		public TextView tvPrice; // 价格控件
		public ImageView ivPic; // 物品图片控件
	}
}
