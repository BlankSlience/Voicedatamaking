package com.example.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.event.EmptyEvent;
import com.example.uiwork.R;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by wli on 15/8/13.
 * 基类，封装了一些常用方法以及 ButterKnife
 */
public class AVBaseActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
    onViewCreated();
  }

  @Override
  public void setContentView(View view) {
    super.setContentView(view);
    ButterKnife.bind(this);
    onViewCreated();
  }

  @Override
  public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(view, params);
    ButterKnife.bind(this);
    onViewCreated();
  }

  @Override
  protected void onResume() {
    super.onResume();
    EventBus.getDefault().register(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    EventBus.getDefault().unregister(this);
  }

  protected void onViewCreated() {}

  protected boolean filterException(Exception e) {
    if (e != null) {
      e.printStackTrace();
      toast(e.getMessage());
      return false;
    } else {
      return true;
    }
  }

  protected void toast(String str) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
  }

  protected void showToast(String content) {
    Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
  }

  protected void showToast(int resId) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
  }


  protected void startActivity(Class<?> cls) {
    Intent intent = new Intent(this, cls);
    startActivity(intent);
  }

  protected void startActivity(Class<?> cls, String... objs) {
    Intent intent = new Intent(this, cls);
    for (int i = 0; i < objs.length; i++) {
      intent.putExtra(objs[i], objs[++i]);
    }
    startActivity(intent);
  }

  public void onEvent(EmptyEvent event) {}
  
  protected void showError(String errorMessage) {
		new AlertDialog.Builder(this)
				.setTitle(
						this.getResources().getString(
								R.string.dialog_message_title))
				.setMessage(errorMessage)
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}
}
