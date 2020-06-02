package com.example.myapplication.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.R;
import com.example.myapplication.bean.*;
import com.example.myapplication.adapter.*;
import com.example.myapplication.activity.*;
import com.example.myapplication.fragment.*;
import com.example.myapplication.utils.*;

import android.app.Activity;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import com.example.myapplication.R;

//主界面-留言界面
public class fragmentNote extends Activity {
    private GridView gvMessage;
    private TextView tvIssue;

    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_community);

        //寻找控件
        gvMessage = (GridView) findViewById(R.id.gv_message);
        tvIssue = (TextView) findViewById(R.id.tv_issue);
        messageList = new ArrayList();
        messageAdapter = new MessageAdapter(this, messageList);
        gvMessage.setAdapter(messageAdapter);

        //初始化数据
        initList();

        //点击留言，跳转到填写留言信息界面
        tvIssue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent toIssue = new Intent(fragmentNote.this, IssueNoteActivity.class);
                startActivity(toIssue);
            }
        });

        //点击留言列表数据跳转到详情界面
        gvMessage.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent toMessage = new Intent(fragmentNote.this, NoteContentActivity.class);
                toMessage.putExtra("id", messageList.get(arg2).getObjectId());
                startActivity(toMessage);
            }
        });
    }


    //查询留言列表数据并展示出来
    public void initList() {
        BmobQuery<Message> query = new BmobQuery<Message>();
        query.setLimit(500);
        query.findObjects(new FindListener<Message>() {
            @Override
            public void done(List<Message> list, BmobException e) {
                if (e == null) {
                    //往适配器里设置数据
                    messageList = list;
                    messageAdapter.refresh((ArrayList<Message>) messageList);
                } else {
                    toast("查询失败");
                }
            }
        });
    }

    public void toast(String text) {
        Toast.makeText(this, text, 0).show();
    }

    //显示这个界面的时候刷新一下数据
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initList();
    }
}