package com.example.myapplication.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.fragment.*;
import com.example.myapplication.utils.*;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.myapplication.R;

//留言列表适配器，往GridView（列表）放入数据
public class MessageAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater = null;
	private List<Message> mNewsList = null;

	//初始化适配器的方法，需要传递上下文和数据集合
	public MessageAdapter(Context context, List<Message> newsList) {
		mContext = context;
		mNewsList = newsList;
		mInflater = LayoutInflater.from(context);
	}

	//判断数据的长度
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
	public void refresh(ArrayList<Message> list) {
		mNewsList = list;
		notifyDataSetChanged();
	}

	//将集合中的数据分别赋值到listview上，展示出来
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MessageHolder messageHolder;
		if (convertView == null) {
			//设置列表样式的布局并且寻找控件
			convertView = mInflater.inflate(R.layout.gridview_message, null);
			messageHolder = new MessageHolder();
			messageHolder.tvNewsContent = (TextView) convertView
					.findViewById(R.id.news_content);
			messageHolder.tvNewsTitle = (TextView) convertView
					.findViewById(R.id.news_title);
			messageHolder.tvNewsDate = (TextView) convertView
					.findViewById(R.id.news_date);
			convertView.setTag(messageHolder);
		} else {
			messageHolder = (MessageHolder) convertView.getTag();
		}
		// 拆分字符串，只取年月日
		String[] ss = new String[2];
		ss = mNewsList.get(position).getCreatedAt().split(" ");
		messageHolder.tvNewsTitle.setText(mNewsList.get(position).getTitle()); // 留言标题
		messageHolder.tvNewsContent.setText(mNewsList.get(position).getContent()); // 留言标题
		messageHolder.tvNewsDate.setText(ss[0]); // 留言发布日期
		return convertView;
	}

	public class MessageHolder {
		public TextView tvNewsContent; // 留言分类
		public TextView tvNewsTitle; // 留言标题
		public TextView tvNewsDate; // 留言时间
	}
}