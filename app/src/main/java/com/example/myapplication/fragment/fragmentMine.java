package com.example.myapplication.fragment;

import java.util.List;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.example.myapplication.R;

//我的界面
public class fragmentMine extends Fragment implements OnClickListener {
    private LinearLayout llMessage;
    private LinearLayout llMyorder;
    private LinearLayout llMycar;
    private LinearLayout llMyinfo;
    private LinearLayout llContact;
    private LinearLayout llAbout;
    private TextView tvName;
    private String id;
    private String name;

    @Override
    public View onCreateView(LayoutInflater paramLayoutInflater,
                             @Nullable ViewGroup paramViewGroup,
                             @Nullable Bundle savedInstanceState) {
        View localView = paramLayoutInflater.inflate(R.layout.fragment_mine,
                paramViewGroup, false);

        //寻找布局控件
        llMessage = (LinearLayout) localView.findViewById(R.id.ll_mynote);
        llMessage.setOnClickListener(this);
        llMyinfo = (LinearLayout) localView.findViewById(R.id.ll_myinfo);
        llMyinfo.setOnClickListener(this);
        llMyorder = (LinearLayout) localView.findViewById(R.id.ll_myorder);
        llMyorder.setOnClickListener(this);
        llMycar = (LinearLayout) localView.findViewById(R.id.ll_mycar);
        llMycar.setOnClickListener(this);
        llContact = (LinearLayout) localView.findViewById(R.id.ll_contact);
        llContact.setOnClickListener(this);
        llAbout = (LinearLayout) localView.findViewById(R.id.ll_about);
        llAbout.setOnClickListener(this);
        tvName = (TextView) localView.findViewById(R.id.tv_username);
        initData(BmobUser.getCurrentUser(_User.class).getUsername());
        return localView;
    }

    public void initData(String username) {
        // 通过用户明获取到名字等信息，并赋值到指定控件
        BmobQuery<_User> localBmobQuery = new BmobQuery<_User>();
        localBmobQuery.addWhereEqualTo("username", username);
        localBmobQuery.findObjects(new FindListener<_User>() {
            @Override
            public void done(List<_User> list, BmobException e) {
                if (e == null) {
                    tvName.setText(list.get(0).getName());
                    id = list.get(0).getObjectId();
                    name = list.get(0).getName();
                    return;


                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.ll_mynote:
                //跳转到我的留言界面
                Intent toNote = new Intent(getContext(), MyNoteActivity.class);
                toNote.putExtra("userName", name);
                startActivity(toNote);
                break;
            case R.id.ll_myorder:
                //跳转到我的订单界面
                Intent toOrder = new Intent(getContext(), MyOrderActivity.class);
                startActivity(toOrder);
                break;
            case R.id.ll_contact:
                //跳转到联系我们界面
                Intent toContact = new Intent(getContext(), ContactMeActivity.class);
                startActivity(toContact);
                break;
            case R.id.ll_mycar:
                //跳转到联系我们界面
                Intent toMycar = new Intent(getContext(), MyShopCarActivity.class);
                startActivity(toMycar);
                break;
            case R.id.ll_about:
                //跳转到关于软件界面
                Intent toAbout = new Intent(getContext(), AboutAppActivity.class);
                startActivity(toAbout);
                break;
            case R.id.ll_myinfo:
                //跳转到个人信息界面
                Intent toInfo = new Intent(getContext(), MyInfoActivity.class);
                toInfo.putExtra("id", id);
                startActivity(toInfo);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initData(BmobUser.getCurrentUser(_User.class).getUsername());
    }

}