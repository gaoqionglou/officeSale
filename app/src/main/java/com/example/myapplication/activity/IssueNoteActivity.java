package com.example.myapplication.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.fragment.*;
import com.example.myapplication.utils.*;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import cn.bmob.v3.listener.SaveListener;

import com.example.myapplication.R;

//留言-发布留言
public class IssueNoteActivity extends Activity {
    private ImageView ivBack;
    private EditText etTitle;
    private TextView tvTitleSum;
    private EditText etContent;
    private TextView tvContentSum;
    private TextView issueMessage;
    private TextView save;
    private String title = "";
    private String content = "";
    private String userName = "";
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_issue_note);
        initInfo(BmobUser.getCurrentUser(_User.class).getUsername());
        initView();

        //对输入框输入情况进行监听
        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvTitleSum.setText(s.toString().length() + "/20");
            }
        });

        //对输入框输入情况进行监听
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvContentSum.setText(s.toString().length() + "/1000");
            }
        });

        //点击留言
        issueMessage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                issueMessage();
            }
        });

        //点击左侧按钮退出这个页面
        ivBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    public void initInfo(String username) {
        // 通过username（唯一，账号）查询到用户列表中的信息
        BmobQuery<_User> localBmobQuery = new BmobQuery<_User>();
        localBmobQuery.addWhereEqualTo("username", username);
        localBmobQuery.findObjects(new FindListener<_User>() {
            @Override
            public void done(List<_User> list, BmobException e) {
                if (e == null) {
                    // 获取成功，执行方法
                    userName = list.get(0).getUsername();
                    name = list.get(0).getName();
                    return;
                } else {
                    toast("加入失败！");
                    e.printStackTrace();
                }
            }
        });
    }

    //寻找布局中的控件
    public void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        etTitle = (EditText) findViewById(R.id.tv_title);
        tvTitleSum = (TextView) findViewById(R.id.tv_title_sum);
        etContent = (EditText) findViewById(R.id.tv_content);
        tvContentSum = (TextView) findViewById(R.id.tv_content_sum);
        issueMessage = (TextView) findViewById(R.id.tv_issue);
        save = (TextView) findViewById(R.id.save);
        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                issueMessage();
            }
        });
    }

    //填写好标题和内容点击发布留言
    public void issueMessage() {
        title = etTitle.getText().toString().trim();
        content = etContent.getText().toString().trim();
        if ("".equals(title) || "".equals(title)) {
            toast("标题和内容不能为空");
        } else {
            //创建一个Message对象设置好参数，通过bmob的特定方法存储到bmob数据库中
            Message note = new Message();
            note.setUserName(userName);
            note.setName(name);
            note.setTitle(title);
            note.setContent(content);
            note.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        toast("加入成功！");
                        finish();// 关闭这个页面
                    } else {
                        toast("加入失败！");
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    //弹出提示框的方法
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
