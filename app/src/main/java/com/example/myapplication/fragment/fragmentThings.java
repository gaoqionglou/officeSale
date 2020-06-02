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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import com.example.myapplication.R;

//展示物品的界面
public class fragmentThings extends Activity implements OnClickListener {
    private GridView lvOrder;
    private ThingsAdapter thingsAdapter;
    private List<Things> thingsList;
    private String type;

    private TextView tvAll;
    private TextView tvSty;
    private TextView tvLife;
    private EditText etContent;
    private TextView tvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_order);
        lvOrder = (GridView) findViewById(R.id.gv_order);

        tvAll = (TextView) findViewById(R.id.tv_all);
        tvSty = (TextView) findViewById(R.id.tv_sty);
        tvLife = (TextView) findViewById(R.id.tv_life);
        etContent = (EditText) findViewById(R.id.et_content);
        tvSearch = (TextView) findViewById(R.id.tv_search);

        tvAll.setOnClickListener(this);
        tvSty.setOnClickListener(this);
        tvLife.setOnClickListener(this);
        tvSearch.setOnClickListener(this);

        // 创建盛放物品数据的集合，并且设置适配器赋值列表数据
        thingsList = new ArrayList<Things>();
        thingsAdapter = new ThingsAdapter(this, thingsList);
        lvOrder.setAdapter(thingsAdapter);

        initData(0);

        // 点击物品选项跳转到订购页面
        lvOrder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    final int arg2, long arg3) {
//				// TODO Auto-generated method stub
//				AlertDialogUtils.show(fragmentThings.this, "确定选择"
//						+ foodList.get(arg2).getName(), "我再看看", "确定",
//						new View.OnClickListener() {
//							@Override
//							public void onClick(View v) {
//								Intent toOrder = new Intent(fragmentThings.this,
//										ChoosePayStateActivity.class);
//								toOrder.putExtra("name", foodList.get(arg2)
//										.getName());
//								toOrder.putExtra("price", foodList.get(arg2)
//										.getPrice());
//								toOrder.putExtra("gdNum", foodList.get(arg2).getBg());
//								toOrder.putExtra("isCar", false);
//								startActivity(toOrder);
//							}
//						});
                Intent toOrder = new Intent(fragmentThings.this,
                        ChoosePayStateActivity.class);
                toOrder.putExtra("name", thingsList.get(arg2)
                        .getName());
                toOrder.putExtra("price", thingsList.get(arg2)
                        .getPrice());
                toOrder.putExtra("gdNum", thingsList.get(arg2).getBg());
                toOrder.putExtra("isCar", false);
                startActivity(toOrder);
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_all:
                tvAll.setTextColor(getResources().getColor(R.color.AppTheme));
                tvSty.setTextColor(getResources().getColor(R.color.black));
                tvLife.setTextColor(getResources().getColor(R.color.black));
                initData(0);
                break;
            case R.id.tv_sty:
                tvSty.setTextColor(getResources().getColor(R.color.AppTheme));
                tvAll.setTextColor(getResources().getColor(R.color.black));
                tvLife.setTextColor(getResources().getColor(R.color.black));
                initData(1);
                break;
            case R.id.tv_life:
                tvLife.setTextColor(getResources().getColor(R.color.AppTheme));
                tvAll.setTextColor(getResources().getColor(R.color.black));
                tvSty.setTextColor(getResources().getColor(R.color.black));
                initData(2);
                break;
            case R.id.tv_search:
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0); //强制隐藏键盘
                initData(0);
                break;
            default:
                break;
        }
    }

    // 从bmob数据中的表Things中获取所有物品的数据展示在页面上
    public void initData(final int num) {
        //获取查询内容
        String content = etContent.getText().toString();
        BmobQuery<Things> query = new BmobQuery<Things>();
        if (content.length() > 0) {
            //查询数据
            query.addWhereEqualTo("name", content);
        }
        //返回200条数据
        query.setLimit(200);
        //执行查询方法
        query.findObjects(new FindListener<Things>() {
            @Override
            public void done(List<Things> object, BmobException e) {
                if (e == null) {
                    thingsList.clear();
                    if (num == 0) {
                        thingsList = object;
                    } else if (num == 1) {
                        for (int i = 0; i < object.size(); i++) {
                            if (object.get(i).getType() == 1) {
                                thingsList.add(object.get(i));
                            }
                        }
                    } else if (num == 2) {
                        for (int i = 0; i < object.size(); i++) {
                            if (object.get(i).getType() == 2) {
                                thingsList.add(object.get(i));
                            }
                        }
                    }
                    thingsAdapter.refresh((ArrayList<Things>) thingsList);
                } else {
                    toast("检查网络连接");
                }
            }
        });

    }


    public void toast(String text) {
        Toast.makeText(fragmentThings.this, text, Toast.LENGTH_SHORT).show();
    }

}