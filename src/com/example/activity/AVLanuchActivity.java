package com.example.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.avos.avoscloud.AVUser;
import com.example.MainActivity;
import com.example.uiwork.R;

/**
 * Created by wli on 15/8/20.
 * Launch 页面
 */
public class AVLanuchActivity extends AVBaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_launch);
    
    AVUser currentUser = AVUser.getCurrentUser();    
    if(currentUser != null) {
    	/**
         * 默认等待 1.5 秒后跳转到主页面
         */
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
        	startActivity(MainActivity.class);
            //finish();
          }
        }, 1500);
    } else {
    	/**
         * 默认等待 1.5 秒后跳转到登陆页面
         */
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
        	startActivity(LoginActivity.class);
            //finish();
          }
        }, 1500);
    }

    
  }
}
