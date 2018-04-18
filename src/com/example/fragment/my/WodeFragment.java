package com.example.fragment.my;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.fragment.BasicFragment;
import com.example.model.LeanUser;
import com.example.uiwork.R;
import com.example.utils.UiHelper;
import com.example.activity.LoginActivity;
import com.squareup.picasso.Picasso;

public class WodeFragment extends BasicFragment{

	private RelativeLayout bianjiziliao;
	private LinearLayout upLoadList;
	private LinearLayout logout;
	private ImageView touxiang;
	TextView nameTextView;
	
	public WodeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_wode,
				container, false);
		
		LeanUser currentUser = LeanUser.getCurrentUser();
		//头像
		touxiang = (ImageView) rootView.findViewById(R.id.wode_tx);
		if(currentUser.getAvatarUrl() == null){
			Picasso.with(getActivity()).load(R.drawable.article_moren).into(touxiang);
		}else{
			Picasso.with(getActivity()).load(currentUser.getAvatarUrl()).into(touxiang);
		}
		
		
		//用户名
		nameTextView = (TextView) rootView.findViewById(R.id.textView_userName);
		nameTextView.setText(currentUser.getUsername());

		return rootView;
	}
	
	

	@Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		TextView title = (TextView) v.findViewById(R.id.title_text);
		title.setText("个人中心");
		
		//编辑资料
		bianjiziliao = (RelativeLayout) v.findViewById(R.id.wode_ziliao);
		bianjiziliao.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				UiHelper.showFragment(WodeFragment.this, null, new BianjiziliaoFragment());
			}
			
		});
		
		//上传记录
		upLoadList = (LinearLayout) v.findViewById(R.id.m_voicelist);
		upLoadList.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//UiHelper.showFragment(WodeFragment.this, null, new GuanzhuCounselorFragment());
			}
			
		});
		
		//退出账号
		logout = (LinearLayout) v.findViewById(R.id.logout);
		logout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(getActivity());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
				dialog.setContentView(R.layout.dialog_logout_confirm);
				dialog.findViewById(R.id.btn_ok).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						Logout();
					}

					private void Logout() {
						AVUser.logOut();
						Intent mainIntent = new Intent(getActivity(),
								LoginActivity.class);
						startActivity(mainIntent);
						getActivity().finish();
					}
				});

				dialog.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});
	}
}
