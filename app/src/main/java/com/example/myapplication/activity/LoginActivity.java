package com.example.myapplication.activity;

import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.com.wyxin.activity.GuideActivity;
import com.example.myapplication.fragment.*;
import com.example.myapplication.utils.*;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

import cn.bmob.v3.listener.SaveListener;


import com.example.myapplication.R;

//登录界面
public class LoginActivity extends Activity implements OnClickListener {
    private static final String TAG = "LoginActicity";
    private Button btnLogin;
    private Button btnReg;
    private EditText etPassword;
    private EditText etUsername;
    private String password = "";
    private String username = "";
    private Button btn;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};
    //请求状态码动态申请权限
    private static int REQUEST_PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_login);
        //权限申请
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
        }
        //寻找布局中的控件
        btnLogin = ((Button) findViewById(R.id.btn_login));
        btnReg = ((Button) findViewById(R.id.btn_register));
        btn = (Button) findViewById(R.id.btn_reg);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        // 获取上次登录输入的账号和密码，如果存在就填在输入框中
        String[] arrayOfString = UsernameAndPassword.getUserInfo(this);
        if (arrayOfString != null) {
            etUsername.setText(arrayOfString[0]);
            etPassword.setText(arrayOfString[1]);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "申请权限成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "请进入系统设置赋予应用访问手机存储权限", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public void onClick(View paramView) {
        switch (paramView.getId()) {
            // 点击登录，并和bmob数据库对比是否有此账号，如有把账号和密码覆盖本地用来存储的文件中
            case R.id.btn_login:
                //点击登录
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                UsernameAndPassword.saveUserInfo(this, username, password);
                if (!Util.isNetworkConnected(this)) {
                    toast("网络连接失败 ");
                    return;
                }
                if ((username.equals("")) || (password.equals(""))) {
                    toast(" 请输入账号和密码");
                    return;
                }
                final _User localUser = new _User();
                localUser.setUsername(username);
                localUser.setPassword(password);
                localUser.login(new SaveListener<BmobUser>() {

                    @Override
                    public void done(BmobUser s, BmobException e) {
                        if (e == null) {
                            //保存登录验证（用于修改个人信息）并且跳转主界面
                            SharedPreferencesUtils.saveString(getApplicationContext(), "id", localUser.getObjectId());
                            SharedPreferencesUtils.saveString(getApplicationContext(), "token", localUser.getSessionToken());
                            SharedPreferencesUtils.saveString(getApplicationContext(), "username", username);

                            Intent toHome = new Intent(getApplicationContext(),
                                    GuideActivity.class);
                            startActivity(toHome);
                            finish();
                        } else {
                            toast("账号或者密码错误");//提示
                            e.printStackTrace();
                        }
                    }
                });
                break;
            // 注册按钮
            case R.id.btn_register:
                startActivity(new Intent(this, RegisterActivity.class));
            default:
                return;
        }
    }

    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}