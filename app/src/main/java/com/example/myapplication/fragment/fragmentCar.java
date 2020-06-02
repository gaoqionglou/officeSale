package com.example.myapplication.fragment;

import java.util.ArrayList;
import java.util.List;


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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;

import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.fragment.*;
import com.example.myapplication.utils.*;

public class fragmentCar extends Activity {
    private Context context;

    private String username;

    private ListView lvCar;
    private Button goPay;
    private ImageView ivKong;

    private List<Shopcar> list;
    private MyShopCarListAdapter myShopCarListAdapter;

    private String name;
    private String id;
    private double price;

    private boolean isKong = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shop_car);

        lvCar = (ListView) findViewById(R.id.lv_mycar);
        goPay = (Button) findViewById(R.id.go_pay);
        ivKong = (ImageView) findViewById(R.id.iv_kong);

        context = this;
        // 获取软件本地存储的账号
        username = SharedPreferencesUtils.getString(context, "username", "");

        // 创建集合以及适配器，并且设置上去
        list = new ArrayList<Shopcar>();
        myShopCarListAdapter = new MyShopCarListAdapter(context, list);
        lvCar.setAdapter(myShopCarListAdapter);

        initData();

        // 长按购物车一条数据提示删除框
        lvCar.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int position, long arg3) {
                // TODO Auto-generated method stubget
                AlertDialogUtils.show(fragmentCar.this, "确定从购物车删除该物品?",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteMessage(list.get(position).getObjectId());
                            }
                        });
                return true;
            }
        });

        // 点击去支付
        goPay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (isKong) {
                    toast("购物车为空");
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (i == 0) {
                            name = list.get(i).getNum() + "个"
                                    + list.get(i).getName();
                            price = list.get(i).getPrice();
                            id = list.get(i).getObjectId();
                        } else {
                            name = name + "；" + list.get(i).getNum() + "个"
                                    + list.get(i).getName();
                            price = price + list.get(i).getPrice();
                            id = id + "," + list.get(i).getObjectId();
                        }
                    }
                    Intent toOrder = new Intent(context,
                            ChoosePayStateActivity.class);
                    toOrder.putExtra("name", name);
                    toOrder.putExtra("price", price);
                    toOrder.putExtra("isCar", true);
                    toOrder.putExtra("allId", id);
                    startActivity(toOrder);
                }
            }
        });

    }

    // 从bmob数据库根据id删除预约中的某条数据
    public void deleteMessage(String id) {
        Shopcar shopcar = new Shopcar();
        shopcar.setObjectId(id);
        shopcar.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    toast("删除成功");
                    initData();
                } else {
                    toast("删除失败");
                }
            }
        });
    }

    // 从bmob数据库获取数据的方法
    public void initData() {
        // 通过在bmob数据中的的username这一项和用户的账号一样查询到数据就是这个用户的所有订单
        BmobQuery<Shopcar> query = new BmobQuery<Shopcar>();
        query.addWhereEqualTo("username", username);
        query.setLimit(500);
        query.findObjects(new FindListener<Shopcar>() {
            @Override
            public void done(List<Shopcar> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 0) {
                        ivKong.setVisibility(View.VISIBLE);
                        lvCar.setVisibility(View.GONE);
                        isKong = true;
                    } else {
                        isKong = false;
                        ivKong.setVisibility(View.GONE);
                        lvCar.setVisibility(View.VISIBLE);
                        list = object;
                        myShopCarListAdapter.refresh((ArrayList<Shopcar>) list);
                    }
                } else {
                    toast("查询失败");
                }
            }
        });
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initData();
    }

    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
