package com.example.myapplication.activity;

import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.fragment.*;
import com.example.myapplication.utils.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class WriteInfoActivity extends Activity {
    private LinearLayout llAdd;

    private TextView tvName;
    private TextView tvPrice;
    private TextView tvOrder;
    private ImageView ivBack;

    private EditText etDong;
    private EditText etPhone;
    private EditText etNum;

    private ImageView ivJian;
    private ImageView ivJia;
    private ImageView ivPic;

    private String name = "";
    private String allId = "";
    private String type = "";
    private double price;
    private int gdNum;
    private String address = "";
    private String phone;
    private String num = "";
    private boolean isCar;

    private String[] idArr;

    private String username;
    private int count = 1;

    //本地存储的10张图片（和bmob的数据库对应）
    private int[] bgIcon = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3,
            R.drawable.bg4, R.drawable.bg5, R.drawable.bg6, R.drawable.bg7,
            R.drawable.bg8, R.drawable.bg9, R.drawable.bg10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_write_info);
        username = SharedPreferencesUtils.getString(getApplicationContext(),
                "username", "");

        // 获取布局文件中的控件
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvOrder = (TextView) findViewById(R.id.tv_order);

        ivBack = (ImageView) findViewById(R.id.iv_back);

        etDong = (EditText) findViewById(R.id.et_address);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etNum = (EditText) findViewById(R.id.et_num);

        ivJian = (ImageView) findViewById(R.id.num_jian);
        ivJia = (ImageView) findViewById(R.id.num_jia);
        ivPic = (ImageView) findViewById(R.id.iv_things_pic);

        llAdd = (LinearLayout) findViewById(R.id.ll_add);

        // 获取从上个界面传递过来的数据
        name = getIntent().getStringExtra("name");
        allId = getIntent().getStringExtra("allId");
        type = getIntent().getStringExtra("type");
        price = getIntent().getDoubleExtra("price", 0);
        gdNum = getIntent().getIntExtra("gdNum", -1);
        isCar = getIntent().getBooleanExtra("isCar", false);

        // 判断物品设置图片
        if (gdNum < 10 && gdNum >= 0) {
            ivPic.setImageResource(bgIcon[gdNum - 1]);
        } else {
            ivPic.setImageResource(R.drawable.no_pic);
        }

        if (isCar) {
            llAdd.setVisibility(View.GONE);
        }

        // 设置选择五篇的名称和单价
        tvName.setText("物品名称：" + name);
        tvPrice.setText("物品价格：" + price + "元");

        // 减数量
        ivJian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                num = etNum.getText().toString().trim();
                count = Integer.parseInt(num);
                count--;
                etNum.setText(count + "");
            }
        });
        // 加数量
        ivJia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                num = etNum.getText().toString().trim();
                count = Integer.parseInt(num);
                count++;
                etNum.setText(count + "");
            }
        });

        // 点击确定按钮
        tvOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 获取输入框所有输入信息
                address = etDong.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                num = etNum.getText().toString().trim();
                count = Integer.parseInt(num);
                if ("".equals(address) || "".equals(num) || "".equals(phone)) {
                    toast("对不起，你的收货信息没有填写完整！");
                } else if (!Util.isPhoneNumberValid(phone)) {
                    toast("请填写正确的手机号码");
                } else if (count <= 0) {
                    toast("请填写正确的数量");
                } else {
//					AlertDialogUtils.show(WriteInfoActivity.this, "确定订单？",
//							new View.OnClickListener() {
//								@Override
//								public void onClick(View v) {
//									//判断是不是从购物车过来的支付
//									if (isCar) {
//										if ("1".equals(type)) {
//											// 选择的直接支付，直接付款
//											Intent toPay = new Intent(
//													WriteInfoActivity.this,
//													PayActivity.class);
//											toPay.putExtra("name", name);
//											toPay.putExtra("price", price + "");
//											toPay.putExtra("address", address);
//											toPay.putExtra("phone", phone);
//											toPay.putExtra("isCar", isCar);
//											toPay.putExtra("allId", allId);
//											startActivity(toPay);
//											finish();
//										} else if ("2".equals(type)) {
//											// 在bmob里面设置订单信息，货到付款的标志
//											setOrderCarInfo();
//											//循环购物车中的所有物品在bmob对应的id，并且删除（清空购物车操作）
//											idArr = convertStrToArray(allId);
//											for (int i = 0; i < idArr.length; i++) {
//												deleteMessage(idArr[i]);
//											}
//										}
//									} else {
//										if ("1".equals(type)) {
//											// 选择的直接支付，直接付款
//											Intent toPay = new Intent(
//													WriteInfoActivity.this,
//													PayActivity.class);
//											toPay.putExtra("name", name);
//											toPay.putExtra("price",
//													(price * count) + "");
//											toPay.putExtra("address", address);
//											toPay.putExtra("phone", phone);
//											toPay.putExtra("allId", allId);
//											toPay.putExtra("isCar", isCar);
//											startActivity(toPay);
//											finish();
//										} else if ("2".equals(type)) {
//											// 在bmob里面设置订单信息，货到付款的标志
//											setOrderInfo();
//										}
//									}
//								}
//							});
                    //判断是不是从购物车过来的支付
                    if (isCar) {
                        if ("1".equals(type)) {
                            // 选择的直接支付，直接付款
                            Intent toPay = new Intent(
                                    WriteInfoActivity.this,
                                    PayActivity.class);
                            toPay.putExtra("name", name);
                            toPay.putExtra("price", price + "");
                            toPay.putExtra("address", address);
                            toPay.putExtra("phone", phone);
                            toPay.putExtra("isCar", isCar);
                            toPay.putExtra("allId", allId);
                            startActivity(toPay);
                            finish();
                        } else if ("2".equals(type)) {
                            // 在bmob里面设置订单信息，货到付款的标志
                            setOrderCarInfo();
                            //循环购物车中的所有物品在bmob对应的id，并且删除（清空购物车操作）
                            idArr = convertStrToArray(allId);
                            for (int i = 0; i < idArr.length; i++) {
                                deleteMessage(idArr[i]);
                            }
                        }
                    } else {
                        if ("1".equals(type)) {
                            // 选择的直接支付，直接付款
                            Intent toPay = new Intent(
                                    WriteInfoActivity.this,
                                    PayActivity.class);
                            toPay.putExtra("name", name);
                            toPay.putExtra("price",
                                    (price * count) + "");
                            toPay.putExtra("address", address);
                            toPay.putExtra("phone", phone);
                            toPay.putExtra("allId", allId);
                            toPay.putExtra("isCar", isCar);
                            startActivity(toPay);
                            finish();
                        } else if ("2".equals(type)) {
                            // 在bmob里面设置订单信息，货到付款的标志
                            setOrderInfo();
                        }
                    }
                }
            }
        });

        // 点击左侧按钮退出这个页面
        ivBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    // 设置订单信息
    public void setOrderInfo() {
        Indent indent = new Indent();
        indent.setPhone(phone);
        indent.setAddress(address);
        indent.setName(name);
        indent.setPay(false);
        indent.setPrice((price * Double.parseDouble(num)) + "");
        indent.setUsername(username);
        indent.setIsfinish(false);
        indent.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    toast("下单成功");
                    finish();
                } else {
                    toast("下单失败");
                }
            }
        });
    }

    // 设置订单信息
    public void setOrderCarInfo() {
        Indent indent = new Indent();
        indent.setPhone(phone);
        indent.setAddress(address);
        indent.setName(name);
        indent.setPay(false);
        indent.setPrice(price + "");
        indent.setUsername(username);
        indent.setIsfinish(false);
        indent.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    toast("下单成功");
                    finish();
                } else {
                    toast("下单失败");
                }
            }
        });
    }

    // 删除购物车的物品
    public void deleteMessage(String id) {
        Shopcar shopcar = new Shopcar();
        shopcar.setObjectId(id);
        shopcar.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    toast("delete成功");

                } else {
                    toast("delete失败");
                }
            }
        });
    }

    // 使用String的split方法
    public static String[] convertStrToArray(String str) {
        String[] strArray = null;
        strArray = str.split(","); // 拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }

    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
