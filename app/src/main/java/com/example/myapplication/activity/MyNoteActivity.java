package com.example.myapplication.activity;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.myapplication.R;

//我的留言界面
public class MyNoteActivity extends Activity {
	private GridView lvMessage;
	private TextView tvIssue;
	private ImageView ivBack;

	private String userName;

	private MessageAdapter messageAdapter;
	private List<Message> messageList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_note);

		//寻找布局中的控件
		userName = getIntent().getStringExtra("username");
		lvMessage = (GridView) findViewById(R.id.gv_message);
		tvIssue = (TextView) findViewById(R.id.tv_issue);
		ivBack = (ImageView) findViewById(R.id.iv_back);

		//创建盛放数据的集合，并且创建适配器（列表赋值数据的文件）
		messageList = new ArrayList();
		messageAdapter = new MessageAdapter(this, messageList);
		lvMessage.setAdapter(messageAdapter);


		initData();

		// 点击留言，发布留言
		tvIssue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent toIssue = new Intent(MyNoteActivity.this,
						IssueNoteActivity.class);
				startActivity(toIssue);
			}
		});

		// 点击列表中的留言查看详情
		lvMessage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
				Intent toMessage = new Intent(MyNoteActivity.this,NoteContentActivity.class);
				toMessage.putExtra("id", messageList.get(arg2).getObjectId());
				startActivity(toMessage);
			}
		});

		//长按留言列表中的一条，删除功能
		lvMessage.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent,
										   View view, final int position, long id) {

				AlertDialogUtils.show(MyNoteActivity.this,
						"继续?", new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								deleteMessage(messageList.get(position)
										.getObjectId());
							}
						});
				return true;
			}
		});

		//点击左侧的返回图标，关闭此页面
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	//bmob获取我的留言数据并且展示在列表中
	public void initData() {
		//通过在bmob数据中的的username这一项和用户的账号一样查询到数据就是这个用户的所有留言
		BmobQuery<Message> query = new BmobQuery<Message>();
		query.addWhereEqualTo("userName", userName);
		query.setLimit(500);
		query.findObjects(new FindListener<Message>() {
			@Override
			public void done(List<Message> list, BmobException e) {
				if(e==null){
					messageList = list;
					messageAdapter.refresh((ArrayList<Message>) messageList);
				}else{
					toast("查询失败");
				}
			}
		});
	}

	//删除某条留言
	public void deleteMessage(String id) {
		//通过获取列表中对应一行的id删除bmob数据库中的一行数据
		Message board = new Message();
		board.setObjectId(id);
		board.delete(new UpdateListener() {
			@Override
			public void done(BmobException e) {
				if(e==null){
					toast("删除成功");
					initData();
				}else{
					toast("删除失败");
				}
			}
		});
	}

	public void toast(String text) {
		Toast.makeText(this, text, 0).show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}
}
