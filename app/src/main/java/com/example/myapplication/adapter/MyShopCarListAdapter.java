package com.example.myapplication.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.fragment.*;
import com.example.myapplication.utils.*;

import com.example.myapplication.R;

//通知列表适配器，往GridView（列表）放入数据
public class MyShopCarListAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater = null;
	private List<Shopcar> mNewsList = null;

	// 初始化适配器的方法，需要传递上下文和数据集合
	public MyShopCarListAdapter(Context context, List list) {
		mContext = context;
		mNewsList = list;
		mInflater = LayoutInflater.from(context);
	}

	// 判断数据的长度
	@Override
	public int getCount() {
		return mNewsList.size();
	}

	@Override
	public Object getItem(int position) {
		return mNewsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 刷新列表中的数据
	public void refresh(ArrayList<Shopcar> list) {
		mNewsList = list;
		notifyDataSetChanged();
	}

	// 将集合中的数据分别赋值到listview上，展示出来
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ShopcarHolder shopcarHolder;
		if (convertView == null) {
			// 设置列表样式的布局并且寻找控件
			convertView = mInflater.inflate(R.layout.lv_shop_car, null);
			shopcarHolder = new ShopcarHolder();
			shopcarHolder.tvName = (TextView) convertView
					.findViewById(R.id.tv_name);
			shopcarHolder.tvPrice = (TextView) convertView
					.findViewById(R.id.tv_price);
			shopcarHolder.tvNum = (TextView) convertView
					.findViewById(R.id.tv_num);
			convertView.setTag(shopcarHolder);
		} else {
			shopcarHolder = (ShopcarHolder) convertView.getTag();
		}
		shopcarHolder.tvName.setText(mNewsList.get(position).getName()); // 物品名
		shopcarHolder.tvPrice.setText(mNewsList.get(position).getPrice() + ""); // 物品总价格
		shopcarHolder.tvNum.setText(mNewsList.get(position).getNum() + ""); // 物品数量
		return convertView;
	}

	public class ShopcarHolder {
		public TextView tvName; // 物品名
		public TextView tvPrice; // 物品总价格
		public TextView tvNum; // 物品数量
	}
}