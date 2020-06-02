package com.example.myapplication.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.fragment.*;
import com.example.myapplication.utils.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import com.example.myapplication.R;

//食堂通知界面
public class fragmentNotice extends Fragment {
    private GridView lvNewsList;
    private List<Notice> newsList = new ArrayList();
    private NewsListAdapter newsListAdapter;
    private TextView tvDay;
    private TextView tvWeek;
    private TextView tvName;

    // 初始化控件
    @Override
    public View onCreateView(LayoutInflater paramLayoutInflater,
                             @Nullable ViewGroup paramViewGroup,
                             @Nullable Bundle savedInstanceState) {
        View localView = paramLayoutInflater.inflate(R.layout.fragement_school,
                paramViewGroup, false);
        tvWeek = ((TextView) localView.findViewById(R.id.schoool_week));
        tvDay = ((TextView) localView.findViewById(R.id.school_day));
        lvNewsList = ((GridView) localView.findViewById(R.id.gv_news));
        tvName = ((TextView) localView.findViewById(R.id.tv_name));

        setTime();
        initData(BmobUser.getCurrentUser(_User.class).getUsername());

        newsListAdapter = new NewsListAdapter(getActivity(), newsList);
        lvNewsList.setAdapter(newsListAdapter);

        getNewsData();

        // 点击通知列表里的某一条跳转到详情界面
        lvNewsList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent toNews = new Intent(getContext(), NewsContentActivity.class);
                toNews.putExtra("title", newsList.get(arg2).getTitle());
                toNews.putExtra("time", newsList.get(arg2).getCreatedAt());
                toNews.putExtra("content", newsList.get(arg2).getContent());
                startActivity(toNews);
            }
        });

        return localView;
    }

    public void initData(String username) {
        // 通过用户明获取到名字等信息，并赋值到指定控件
        BmobQuery<_User> localBmobQuery = new BmobQuery<_User>();
        localBmobQuery.addWhereEqualTo("username", username);
        localBmobQuery.findObjects(new FindListener<_User>() {
            @Override
            public void done(List<_User> list, BmobException e) {
                if (e == null) {
                    // 获取成功，执行方法，设置用户名字
                    tvName.setText("欢迎" + list.get(0).getName() + "使用");
                } else {

                }
            }
        });
    }

    // 从bomb数据库获取通知数据并用列表展示
    public void getNewsData() {
        BmobQuery<Notice> query = new BmobQuery<Notice>();
        query.setLimit(50);
        query.findObjects(new FindListener<Notice>() {
            @Override
            public void done(List<Notice> list, BmobException e) {
                if (e == null) {
                    newsList = list;
                    newsListAdapter.refresh((ArrayList<Notice>) newsList);
                } else {
                    toast("查询失败");
                }
            }
        });

    }

    // 获取当前日期等信息并把它赋值到指定位置
    public void setTime() {
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = calendar.get(Calendar.MONTH) + 1 + "";
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        String week = calendar.get(Calendar.WEEK_OF_YEAR) - 9 + "";
        String dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) + "";
        if ("1".equals(dayOfWeek)) {
            dayOfWeek = "天";
        } else if ("2".equals(dayOfWeek)) {
            dayOfWeek = "一";
        } else if ("3".equals(dayOfWeek)) {
            dayOfWeek = "二";
        } else if ("4".equals(dayOfWeek)) {
            dayOfWeek = "三";
        } else if ("5".equals(dayOfWeek)) {
            dayOfWeek = "四";
        } else if ("6".equals(dayOfWeek)) {
            dayOfWeek = "五";
        } else if ("7".equals(dayOfWeek)) {
            dayOfWeek = "六";
        }
        tvWeek.setText(" 星期 " + dayOfWeek);
        tvDay.setText(year + " 年 " + month + " 月 " + day + " 日 ");
    }

    //提示框
    public void toast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    //展示这个界面的时候刷新里面的数据
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        getNewsData();
    }

}