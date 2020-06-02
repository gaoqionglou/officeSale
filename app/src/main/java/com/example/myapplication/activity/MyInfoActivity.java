package com.example.myapplication.activity;

import java.util.List;

import android.app.Activity;
import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.myapplication.R;
import com.example.myapplication.bean._User;
import com.example.myapplication.utils.SharedPreferencesUtils;


//个人信息界面
public class MyInfoActivity extends Activity {
    private ImageView ivBack;
    private ImageView ivPhoto;
    private TextView tvSave;
    private TextView tvExit;
    private EditText etNum;
    private EditText etName;
    private EditText etGender;
    private EditText etPhone;
    private EditText etAddress;

    private String id;
    private String upName = "";
    private String gender = "";
    private String phone = "";
    private String address = "";
    private TextView save;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_myinfo);

        //寻找布局中的控件
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivPhoto = (ImageView) findViewById(R.id.iv_photo);
        tvSave = (TextView) findViewById(R.id.tv_save);
        tvExit = (TextView) findViewById(R.id.tv_exit);
        etNum = (EditText) findViewById(R.id.et_num);
        etName = (EditText) findViewById(R.id.et_name);
        etGender = (EditText) findViewById(R.id.et_gender);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etAddress = (EditText) findViewById(R.id.et_address);
        save = (TextView) findViewById(R.id.save);
        //获取上个界面和本地存储文件的值
        id = getIntent().getStringExtra("id");
        token = SharedPreferencesUtils.getString(getApplicationContext(),
                "token", "");
        initData();

        //点击左侧按钮退出这个页面
        ivBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        // 点击修改信息
        tvSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //获取每个输入框中的文字
                phone = etPhone.getText().toString().trim();
                gender = etGender.getText().toString().trim();
                upName = etName.getText().toString().trim();
                address = etAddress.getText().toString().trim();
                //如果有空的或者输入不对的就回出现提示
                if ((phone.equals("")) || (gender.equals(""))
                        || (upName.equals("")) || (address.equals(""))) {
                    toast("请填写完整");
                } else if (!("男".equals(gender)) && !("女".equals(gender))) {
                    toast("性别输入不对");
                } else {
                    updataData();
                }
            }
        });
        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //获取每个输入框中的文字
                phone = etPhone.getText().toString().trim();
                gender = etGender.getText().toString().trim();
                upName = etName.getText().toString().trim();
                address = etAddress.getText().toString().trim();
                //如果有空的或者输入不对的就回出现提示
                if ((phone.equals("")) || (gender.equals(""))
                        || (upName.equals("")) || (address.equals(""))) {
                    toast("请填写完整");
                } else if (!("男".equals(gender)) && !("女".equals(gender))) {
                    toast("性别输入不对");
                } else {
                    updataData();
                }
            }
        });
        //点击退出登录
        tvExit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                Intent toLogin = new Intent(MyInfoActivity.this,
                        LoginActivity.class);
                startActivity(toLogin);
            }
        });
    }

    // 获取个人信息展示在界面上
    public void initData() {
        BmobQuery<_User> query = new BmobQuery<_User>();
        // 查询bmob表中此用户的信息
        query.addWhereEqualTo("objectId", id);
        // 执行查询方法
        query.getObject(id, new QueryListener<_User>() {
            @Override
            public void done(_User object, BmobException e) {
                if(e==null){
                    //获取到信息之后设置到对应的位置并且判断性别设置不同的头像
                    etNum.setText("账号：" + object.getUsername());
                    etName.setText(object.getName());
                    etGender.setText(object.getGender());
                    etPhone.setText(object.getPhone());
                    etAddress.setText(object.getAddress());
                    if ("男".equals(object.getGender())) {
                        ivPhoto.setImageResource(R.drawable.boy);
                    } else if ("女".equals(object.getGender())) {
                        ivPhoto.setImageResource(R.drawable.girl);
                    }
                }else{
                    toast("获取失败，请检查网络");
                }
            }
        });
    }

    // 修改个人信息
    public void updataData() {
        //把输入框中的各个值获取到并且赋值到创建的用户对象上去，不同的值会覆盖原来的
        _User user = new _User();
        user.setObjectId(id);
        user.setName(upName);
        user.setGender(gender);
        user.setPhone(phone);
        user.setSessionToken(token);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    toast("修改成功");
                }else{
                    toast("修改失败");
                }
            }
        });
    }

    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}