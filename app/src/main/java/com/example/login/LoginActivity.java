package com.example.login;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.app9010.supermarket.R;

import com.example.application.AppStatus;
import com.example.application.BaseActivity;
import com.example.application.UserStatus;
import com.example.main.MainActivity;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.VolleyNet.VollerCallBack;
import com.example.network.bean.LoginBean;
import com.example.utils.MD5Util;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;
import com.tencent.connect.UserInfo;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginActivity extends BaseActivity implements OnClickListener{

	public static final int LOGIN_NORMAL = 0;
	public static final int LOGIN_QQ = 1;
	public static final int LOGIN_WECHAT = 2;
	private LoadingDialog dialog = null;
	private Button mLoginBtn;
	private EditText mLoginEdit,mPwdEdit;
	private VolleyNet net;
	private ImageView mWXbtn,mQQbtn;
	private BaseUiListener listener;//腾讯登录
	private Button mForgetBtn;
	private Tencent mTencent;
	private String openid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initTitle();
		init();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
		}
	}
	public void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText(getResources().getString(R.string.login));
		rightView.setText(getResources().getString(R.string.register));
		rightView.setOnClickListener(this);
		leftView.setOnClickListener(this);
	}
	public void init(){
		mLoginBtn = (Button)findViewById(R.id.login_btn);
		mLoginEdit = (EditText)findViewById(R.id.login_username_edit);
		mPwdEdit = (EditText)findViewById(R.id.login_password_edit);
		mWXbtn = (ImageView)findViewById(R.id.wx_login_btn);
		mQQbtn = (ImageView)findViewById(R.id.qq_btn);
		mForgetBtn = (Button)findViewById(R.id.forget_btn);
		listener = new BaseUiListener();
		mLoginEdit.setText(AppStatus.getLoginUserName());
		mPwdEdit.setText(AppStatus.getPassWord());
		mLoginBtn.setOnClickListener(this);
		mWXbtn.setOnClickListener(this);
		mQQbtn.setOnClickListener(this);
		mForgetBtn.setOnClickListener(this);
		net = new VolleyNet();
		net.setOnVellerCallBack(callBack);
	}
	public void login(){
		String name = mLoginEdit.getText().toString();
		String pass = mPwdEdit.getText().toString();
		if(name.equals("")){
			ToastUtil.show(this, "手机号不能为空！");
			return;
		}
		if(pass.equals("")){
			ToastUtil.show(this, "密码不能为空！");
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name",name);
		map.put("pass",MD5Util.stringMD5(name+pass));
		dialog = new LoadingDialog(this);
		dialog.show("登录中...", true, null);
		dialog.show();
		net.Post(ServerURL.LOGIN_URL, map, 0);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.common_title_left_btn:
				finish();
				break;
			case R.id.common_title_right_tv:
				Intent register = new Intent(this,RegisterActivity.class);
				register.putExtra("isstart", false);
				startActivity(register);
				break;
			case R.id.login_btn:
				login();
				break;
			case R.id.forget_btn:
				Intent forget = new Intent(this,ComfirePhoneActivity.class);
				startActivity(forget);
				break;
			case R.id.wx_login_btn:
				dialog = new LoadingDialog(this);
				dialog.show("登录中...", true, null);
				dialog.show();
				//微信登录
				final IWXAPI api = WXAPIFactory.createWXAPI(this,"wxe43806bd58c4856e",false);
				// 将该app注册到微信
				api.registerApp("wxe43806bd58c4856e");
				SendAuth.Req req = new SendAuth.Req();
				req. scope = "snsapi_userinfo";
				req. state = "wechat_sdk_demo_test";
				api.sendReq(req);//执行完毕这句话之后，会在WXEntryActivity回调
				break;
			case R.id.qq_btn:
				dialog = new LoadingDialog(this);
				dialog.show("登录中...", true, null);
				dialog.show();
				mTencent = Tencent.createInstance("1105941896", this.getApplicationContext());
				mTencent.login(this, "all", listener);
				break;
		}
	}
	private VollerCallBack callBack = new VollerCallBack() {
		@Override
		public void OnSuccess(int requestCode, String result) {
			if(requestCode == 0){
				try {
					JSONObject jsonObject = new JSONObject(result);
					if(jsonObject.getInt("code") == VolleyNet.ONE_CODE){
						//登录成功
						LoginBean bean = GsonHelper.getGson().fromJson(result, LoginBean.class);
                        loginSuccess(bean,0);
						dialog.dismiss();
					}
					else {
						//登录失败
						dialog.dismiss();
						ToastUtil.show(LoginActivity.this, "账号或密码错误！");
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			if(requestCode == 1){
				LoginBean bean = GsonHelper.getGson().fromJson(result, LoginBean.class);
				if(bean.getCode() == VolleyNet.ONE_CODE){
					loginSuccess(bean,1);
				}
			}
		}
		@Override
		public void OnError(int requestCode, String errorMsg) {
			dialog.dismiss();
		}
	};

	private void loginSuccess(LoginBean bean,int loginType){
        if(bean.getData() != null){
			AppStatus.setLoginUserName(mLoginEdit.getText().toString());
			AppStatus.setPassWord(mPwdEdit.getText().toString());
            LoginBean.LoginData data = bean.getData();
            UserStatus.setID(data.getID());
            UserStatus.setUUID(data.getUuid());
            UserStatus.setNick(data.getNick());
            UserStatus.setGender(data.getGender());
            UserStatus.setCents(data.getCents());
            UserStatus.setCard(data.getCard());
            UserStatus.setRmb(data.getRmb());
            UserStatus.setHeadUrl(data.getHeadurl());
        }
        UserStatus.setLogin(true);
        UserStatus.setLoginType(loginType);
        Intent login = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(login);
        finish();
	}

	//登陆
	private void qqlogin(String openid,String headurl,String nickname){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("openid",openid);
		map.put("headurl",headurl);
		map.put("nickname",nickname);
		map.put("type","1");//qq联合登陆
		net.setOnVellerCallBack(callBack);
		net.Post(ServerURL.LOGIN_UNION_URL, map, 1);
	}

	private IUiListener getQQinfoListener = new IUiListener() {
		@Override
		public void onComplete(Object response) {
			try {
				JSONObject jsonObject = (JSONObject) response;
				String nickname = jsonObject.getString("nickname");
				String headurl = jsonObject.getString("figureurl_qq_1");
				UserStatus.setNick(nickname);
                UserStatus.setHeadUrl(headurl);
				qqlogin(openid, headurl, nickname);
				//处理自己需要的信息
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onError(UiError uiError) {

		}

		@Override
		public void onCancel() {

		}
	};

	private class BaseUiListener implements IUiListener {

		@Override
		public void onCancel() {

		}

		@Override
		public void onComplete(Object object) {
			JSONObject response = ((JSONObject) object);
			try {
				String openID = response.getString("openid");
				openid = openID;
				String accessToken = response.getString("access_token");
				String expires = response.getString("expires_in");
                UserStatus.setOpenId(openID);
				mTencent.setOpenId(openID);
				mTencent.setAccessToken(accessToken, expires);
				UserInfo userInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());
				userInfo.getUserInfo(getQQinfoListener);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void onError(UiError arg0) {

		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Tencent.onActivityResultData(requestCode,resultCode,data,listener);
	}
}
