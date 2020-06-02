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

//我的订单适配器，往listView（列表）放入数据
public class MyOrderAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater = null;
	private List<Indent> list = null;

	// 初始化适配器的方法，需要传递上下文和数据集合
	public MyOrderAdapter(Context context, List<Indent> mLabsList) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	// 判断数据的长度
	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 刷新列表中的数据
	public void refresh(ArrayList<Indent> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	// 将集合中的数据分别赋值到listview上，展示出来
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IndentHolder indentHolder;
		if (convertView == null) {
			// 设置列表样式的布局并且寻找控件
			convertView = mInflater.inflate(R.layout.gridview_my_order, null);
			indentHolder = new IndentHolder();
			indentHolder.tvName = (TextView) convertView
					.findViewById(R.id.tv_name);
			indentHolder.tvPrice = (TextView) convertView
					.findViewById(R.id.tv_price);
			indentHolder.isFinish = (TextView) convertView
					.findViewById(R.id.tv_isfinish);
			indentHolder.isFinish = (TextView) convertView
					.findViewById(R.id.tv_isfinish);
			indentHolder.tvAddress = (TextView) convertView
					.findViewById(R.id.tv_address);
			indentHolder.tvData = (TextView) convertView
					.findViewById(R.id.tv_data);
			convertView.setTag(indentHolder);
		} else {
			indentHolder = (IndentHolder) convertView.getTag();
		}
		indentHolder.tvName.setText(list.get(position).getName());// 设置名称
		indentHolder.tvPrice.setText("￥" + list.get(position).getPrice()
		);// 设置价格
		indentHolder.tvAddress.setText("" + list.get(position).getAddress());// 设置地址
		if (list.get(position).isIsfinish()) { // 判断订单是否完成并且设置对应文字
			indentHolder.isFinish.setText("（已送达）");
		} else {
			indentHolder.isFinish.setText("（未接单）");
		}
		indentHolder.tvData.setText("" + list.get(position).getCreatedAt());// 设置下单时间

		return convertView;
	}

	public class IndentHolder {
		// 布局界面的控件
		public TextView tvName; // 物品名
		public TextView tvPrice; // 订单价格
		public TextView isFinish;// 是否完成
		public TextView tvAddress;// 地址
		public TextView tvData;// 订单时间
	}
}
