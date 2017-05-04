package com.example.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.adapter.MyCouponAdapter;
import com.example.network.GsonHelper;
import com.example.network.ServerURL;
import com.example.network.VolleyNet;
import com.example.network.bean.CouponBean;
import com.example.utils.SharePerencesUtil;
import com.example.utils.ToastUtil;
import com.example.view.commonview.LoadingDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.HashMap;

import cn.app9010.supermarket.R;

/**
 * Created by Administrator on 2017/4/13.
 */

public class CouponFragment extends Fragment implements VolleyNet.VollerCallBack {

	private int index = 0;
	private PullToRefreshListView mListView;
	private View emptyView;
	private MyCouponAdapter mAdapter;
	private VolleyNet net;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			mAdapter.setBean((CouponBean)msg.obj);
		};
	};
	public static final Fragment getInstance(int index){
		CouponFragment fragment = new CouponFragment();
		Bundle b = new Bundle();
		b.putInt("index",index);
		fragment.setArguments(b);
		return fragment;
	}
	public void init(View view){
		mListView = (PullToRefreshListView)view.findViewById(R.id.my_coupon_list);
		mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				getMyCoupon(index);
			}
		});
		mAdapter = new MyCouponAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.list_empty_view, null);
		mListView.setEmptyView(emptyView);
		net = new VolleyNet();
		net.setOnVellerCallBack(this);
		getMyCoupon(index);//默认获取所有
	}
	private void getMyCoupon(int code){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", SharePerencesUtil.getInstances().getID());
		map.put("uuid",SharePerencesUtil.getInstances().getUUID());
		map.put("type",code+"");
		net.Post(ServerURL.COUPON_USERLIST_URL, map, 0);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		index = getArguments().getInt("index");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_coupon,null);
		init(view);
		return view;
	}

	@Override
	public void OnSuccess(int requestCode, String result) {
		if(requestCode == 0){
			CouponBean bean = GsonHelper.getGson().fromJson(result, CouponBean.class);
			mListView.onRefreshComplete();
			if(bean.getCode() == VolleyNet.ONE_CODE){
				Message message = new Message();
				message.obj = bean;
				handler.sendMessage(message);
			}
			else
			{
				ToastUtil.show(getActivity(), "获取优惠券信息失败");
			}
		}
	}

	@Override
	public void OnError(int requestCode, String errorMsg) {
		mListView.onRefreshComplete();
	}
}
