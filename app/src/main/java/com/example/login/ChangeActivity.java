package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import cn.app9010.supermarket.R;

import com.example.application.AppStatus;
import com.example.application.BaseActivity;
import com.example.application.UserStatus;
import com.example.main.MainActivity;
import com.example.utils.ImageLoaderUtil;
import com.example.utils.SharePerencesUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ChangeActivity extends BaseActivity implements OnClickListener{
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private Button mLoginBtn,mRegisterBtn,mVisitorLoginBtn;
	private ImageView changeImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change);
		init();
	}

	public void init(){
		mLoginBtn = (Button)findViewById(R.id.login_btn);
		mRegisterBtn = (Button)findViewById(R.id.register_btn);
		mVisitorLoginBtn = (Button)findViewById(R.id.visitor_login_btn);
		changeImage = (ImageView)findViewById(R.id.change_image);
		mLoginBtn.setOnClickListener(this);
		mRegisterBtn.setOnClickListener(this);
		mVisitorLoginBtn.setOnClickListener(this);
		imageLoader.displayImage(AppStatus.getChangeImage(), changeImage,
				ImageLoaderUtil.getInstance().getBaseOptions(R.drawable.change_bj,true));
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mLoginBtn){
			Intent intent = new Intent(this,LoginActivity.class);
			startActivity(intent);
			finish();
		}
		if(v == mRegisterBtn){
			Intent intent = new Intent(this,RegisterActivity.class);
			intent.putExtra("isstart", true);
			startActivity(intent);
			finish();
		}
		if(v == mVisitorLoginBtn){
			UserStatus.setID("");
			UserStatus.setUUID("");
			UserStatus.setNick("游客登录");
			UserStatus.setGender(0);
			UserStatus.setCents(0);
			UserStatus.setCard("无");
			UserStatus.setRmb(0);
			UserStatus.setHeadUrl("");
			UserStatus.setLogin(false);
			UserStatus.setLoginType(0);
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
