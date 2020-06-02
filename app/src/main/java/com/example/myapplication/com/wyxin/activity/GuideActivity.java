package com.example.myapplication.com.wyxin.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;


import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.fragment.*;
import com.example.myapplication.utils.*;

public class GuideActivity extends Activity {

    private TextView tvDay;
    private TextView tvWeek;
    private TextView tvName;
    private String userId;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        // 寻找控件
        tvWeek = ((TextView) findViewById(R.id.schoool_week));
        tvDay = ((TextView) findViewById(R.id.school_day));
        tvName = ((TextView) findViewById(R.id.tv_name));

        GridView gridview = (GridView) findViewById(R.id.GridView);
        ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
        //主页
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemImage", R.drawable.main_things);
        map.put("ItemText", "商品列表");
        meumList.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.drawable.main_message);
        map.put("ItemText", "写留言");
        meumList.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.drawable.main_notice);
        map.put("ItemText", "购物车");
        meumList.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.drawable.main_mine);
        map.put("ItemText", "我的资料");
        meumList.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.drawable.my_order);
        map.put("ItemText", "我的订单");
        meumList.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.drawable.my_message);
        map.put("ItemText", "我的留言");
        meumList.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.drawable.contact_me);
        map.put("ItemText", "联系我们");
        meumList.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", R.drawable.about_app);
        map.put("ItemText", "关于软件");
        meumList.add(map);

        SimpleAdapter saItem = new SimpleAdapter(this,
                meumList, //数据源
                R.layout.grade_item, //xml实现
                new String[]{"ItemImage", "ItemText"}, //对应map的Key
                new int[]{R.id.ItemImage, R.id.ItemText});  //对应R的Id

        //添加Item到网格中
        gridview.setAdapter(saItem);
        //添加点击事件
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent toHome = new Intent(getApplicationContext(),
                                fragmentThings.class);
                        startActivity(toHome);
                        break;
                    case 1:
                        //跳转留言页
                        Intent node = new Intent(getApplicationContext(),
                                fragmentNote.class);
                        startActivity(node);
                        break;
                    case 2:
                        //购物车
                        Intent myCar = new Intent(getApplicationContext(),
                                fragmentCar.class);
                        startActivity(myCar);
                        break;
                    case 3:
                        //跳转到个人信息界面
                        Intent toInfo = new Intent(getApplicationContext(),
                                MyInfoActivity.class);
                        toInfo.putExtra("id", userId);
                        startActivity(toInfo);
                        break;
                    case 4:
                        //跳转到我的订单界面
                        Intent toOrder = new Intent(getApplicationContext(),
                                MyOrderActivity.class);
                        startActivity(toOrder);
                        break;
                    case 5:
                        //我的留言
                        Intent intent = new Intent(getApplicationContext(),
                                MyNoteActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        //跳转到联系我们界面
                        Intent toContact = new Intent(getApplicationContext(),
                                ContactMeActivity.class);
                        startActivity(toContact);
                        break;
                    case 7:
                        //跳转到关于软件界面
                        Intent toAbout = new Intent(getApplicationContext(),
                                AboutAppActivity.class);
                        startActivity(toAbout);
                        break;
                }
            }
        });

        initName(BmobUser.getCurrentUser(_User.class).getUsername());
        setTime();
    }

    public void initName(String username) {
        // 通过用户明获取到名字等信息，并赋值到指定控件
        BmobQuery<_User> localBmobQuery = new BmobQuery<_User>();
        localBmobQuery.addWhereEqualTo("username", username);
        localBmobQuery.findObjects(new FindListener<_User>() {
            @Override
            public void done(List<_User> list, BmobException e) {
                if (e == null) {
                    tvName.setText("欢迎" + list.get(0).getName() + "使用");
                    userId = list.get(0).getObjectId();
                    name = list.get(0).getName();
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
}
