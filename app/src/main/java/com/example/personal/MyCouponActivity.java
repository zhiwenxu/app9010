package com.example.personal;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fragment.CouponFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.app9010.supermarket.R;
/**
 * 我的优惠券页面
 */
public class MyCouponActivity extends FragmentActivity implements OnClickListener{

	private TabLayout tabLayout;
	private ViewPager viewPager;
	private CouponPagerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_coupon);
		initTitle();
		init();
	}
	public void initTitle(){
		RelativeLayout leftView = (RelativeLayout)findViewById(R.id.common_title_left_btn);
		TextView centerView = (TextView)findViewById(R.id.common_title_center_tv);
		TextView rightView = (TextView)findViewById(R.id.common_title_right_tv);
		centerView.setText("我的优惠券");
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(this);
	}
	public void init(){
		tabLayout = (TabLayout)findViewById(R.id.tablayout);
		viewPager = (ViewPager)findViewById(R.id.viewpager);
		mAdapter = new CouponPagerAdapter(getSupportFragmentManager());
		mAdapter.addFragment(CouponFragment.getInstance(0),"全部");
		mAdapter.addFragment(CouponFragment.getInstance(2),"未使用");
		mAdapter.addFragment(CouponFragment.getInstance(1),"已使用");
		mAdapter.addFragment(CouponFragment.getInstance(4),"已过期");
		viewPager.setAdapter(mAdapter);
		tabLayout.addTab(tabLayout.newTab().setText("全部"));
		tabLayout.addTab(tabLayout.newTab().setText("未使用"));
		tabLayout.addTab(tabLayout.newTab().setText("已使用"));
		tabLayout.addTab(tabLayout.newTab().setText("已过期"));
		tabLayout.setupWithViewPager(viewPager);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.common_title_left_btn){
			finish();
		}
	}

	class CouponPagerAdapter extends FragmentPagerAdapter{

		private List<Fragment> fragments = new LinkedList<>();
		private List<String> titles =  new ArrayList<>();

		public CouponPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		public void addFragment(Fragment fragment,String title){
			this.fragments.add(fragment);
			this.titles.add(title);
		}
		@Override
		public Fragment getItem(int i) {
			return fragments.get(i);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles.get(position);
		}
	}
}
