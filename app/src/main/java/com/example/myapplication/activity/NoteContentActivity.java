package com.example.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.com.wyxin.activity.GuideActivity;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


import com.example.myapplication.R;

//留言的详情界面
public class NoteContentActivity extends Activity {
    private String id;
    private TextView tvTitle;
    private TextView tvName;
    private TextView tvData;
    private TextView tvContent;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_note_content);

        //上个界面传递过来的留言id
        id = getIntent().getStringExtra("id");

        initView();

        initData();

        //点击左侧按钮退出这个页面
        ivBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    //寻找布局中的控件
    public void initView() {
        tvTitle = (TextView) findViewById(R.id.message_title);
        tvName = (TextView) findViewById(R.id.message_name);
        tvData = (TextView) findViewById(R.id.message_date);
        tvContent = (TextView) findViewById(R.id.message_content);
        ivBack = (ImageView) findViewById(R.id.message_back);
    }

    //通过id在bmob中寻找这条留言的具体信息并且赋值到界面上
    public void initData() {
        BmobQuery<Message> query = new BmobQuery<Message>();
        query.getObject(id, new QueryListener<Message>() {
            @Override
            public void done(Message message, BmobException e) {
                if (e == null) {
                    tvTitle.setText(message.getTitle());
                    tvName.setText(message.getName());
                    tvData.setText(message.getCreatedAt());
                    tvContent.setText(message.getContent());
                } else {
                    toast("查询失败：" + e.getMessage());
                }
            }
        });

    }

    //提示框
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
