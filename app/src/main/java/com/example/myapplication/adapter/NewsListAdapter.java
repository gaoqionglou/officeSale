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
public class NewsListAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater = null;
	private List<Notice> mNewsList = null;

	//初始化适配器的方法，需要传递上下文和数据集合
	public NewsListAdapter(Context context, List<Notice> newsList) {
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
	public void refresh(ArrayList<Notice> list) {
		mNewsList = list;
		notifyDataSetChanged();
	}

	//将集合中的数据分别赋值到listview上，展示出来
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NewsHolder newsHolder;
		if (convertView == null) {
			//设置列表样式的布局并且寻找控件
			convertView = mInflater.inflate(R.layout.gridview_news, null);
			newsHolder = new NewsHolder();
			newsHolder.tvNewsContent = (TextView) convertView
					.findViewById(R.id.news_content);
			newsHolder.tvNewsTitle = (TextView) convertView
					.findViewById(R.id.news_title);
			newsHolder.tvNewsDate = (TextView) convertView
					.findViewById(R.id.news_date);
			convertView.setTag(newsHolder);
		} else {
			newsHolder = (NewsHolder) convertView.getTag();
		}
		// 拆分字符串，只取年月日
		String[] ss = new String[2];
		ss = mNewsList.get(position).getCreatedAt().split(" ");
		newsHolder.tvNewsTitle.setText(mNewsList.get(position).getTitle()); // 通知标题
		newsHolder.tvNewsContent.setText(mNewsList.get(position).getContent()); // 通知内容
		newsHolder.tvNewsDate.setText(ss[0]); // 通知发布日期
		return convertView;
	}

	public class NewsHolder {
		public TextView tvNewsContent; // 通知内容
		public TextView tvNewsTitle; // 通知标题
		public TextView tvNewsDate; // 通知时间
	}
}