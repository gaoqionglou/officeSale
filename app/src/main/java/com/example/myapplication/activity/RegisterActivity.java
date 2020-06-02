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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import com.example.myapplication.R;

//注册界面
public class RegisterActivity extends Activity implements OnClickListener {
    private static final String TAG = "RegisterActivity";
    private Button btnReg;
    private EditText etComfirmPsd;
    private EditText etPassword;
    private EditText etPhone;
    private EditText etGender;
    private EditText etUsername;
    private EditText etName;
    private EditText etAddress;
    private ImageView ivBack;

    // 创建存放相关信息的字符串
    private String username = "";// 账号
    private String password = "";// 密码
    private String comfirmPsd = "";// 确认密码
    private String name = "";// 姓名
    private String phone = "";// 电话号码
    private String gender = "";// 性别
    private String address = "";// 地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reg);
        // 初始化控件
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etComfirmPsd = (EditText) findViewById(R.id.et_comfirm_psd);
        etGender = (EditText) findViewById(R.id.et_gender);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etName = (EditText) findViewById(R.id.et_name);
        etAddress = (EditText) findViewById(R.id.et_address);
        btnReg = (Button) findViewById(R.id.btn_reg_now);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        btnReg.setOnClickListener(this);

        // 点击左侧按钮退出这个页面
        ivBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 点击注册按钮，获取输入值，并把它存储到bmob数据库的_User（用户表）中
            case R.id.btn_reg_now:
                //获取注册界面所有输入框的输入值
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                comfirmPsd = etComfirmPsd.getText().toString();
                gender = etGender.getText().toString();
                phone = etPhone.getText().toString();
                name = etName.getText().toString();
                address = etAddress.getText().toString();

                if (!Util.isNetworkConnected(this)) {
                    toast("网络不给力 ");
                    return;
                } else if ((username.equals("")) || (password.equals(""))
                        || (comfirmPsd.equals("")) || (phone.equals(""))
                        || (gender.equals("")) || (name.equals(""))
                        || (address.equals(""))) {
                    toast(" 不填完整, 不能注册");
                    return;
                } else if (!Util.isPhoneNumberValid(phone)) {
                    toast(" 输入的手机号码不正确");
                    return;
                } else if (!("男".equals(gender)) && !("女".equals(gender))) {
                    toast("性别输入不对");
                } else if (!comfirmPsd.equals(password)) {
                    toast("两次密码输入不一致");
                    return;
                } else {
                    //创建一个用户对象，并且把所有输入的值赋值到对象的每个值上
                    _User localUser = new _User();
                    localUser.setUsername(username);// 注册的名字
                    localUser.setPassword(password);
                    localUser.setPhone(phone);
                    localUser.setGender(gender);
                    localUser.setName(name);
                    localUser.setAddress(address);
                    //bmob的注册方法
                    localUser.signUp(new SaveListener<_User>() {
                        @Override
                        public void done(_User s, BmobException e) {

                            if (e == null) {
                                RegisterActivity.this.toast("注册成功!");
                                Intent localIntent = new Intent(
                                        getApplicationContext(), LoginActivity.class);
                                startActivity(localIntent);
                                finish();

                            } else {
                                toast("注册失败:" + s);
                                e.printStackTrace();
                            }
                        }
                    });

                }
                break;

            default:
                break;
        }
    }

    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}