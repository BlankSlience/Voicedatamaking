package com.example.activity;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.MainActivity;
import com.example.uiwork.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AVBaseActivity{
	Button loginButton;
	Button registerButton;
	Button forgetPasswordButton;
	EditText userNameEditText;
	EditText userPasswordEditText;
	private ProgressDialog progressDialog;
	  
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.fragment_login);
	    
	    AVAnalytics.trackAppOpened(this.getIntent());

		//PushService.setDefaultPushCallback(this,LoginFragment.class);
		//PushService.subscribe(this, "public", LoginFragment.class);
		AVInstallation.getCurrentInstallation().saveInBackground();

		loginButton = (Button) findViewById(R.id.login_button);
		registerButton = (Button) findViewById(R.id.register_button);
		forgetPasswordButton = (Button) findViewById(R.id.forget_button);
		userNameEditText = (EditText) findViewById(R.id.username_edit);
		userPasswordEditText = (EditText) findViewById(R.id.password_edit);

		loginButton.setOnClickListener(loginListener);
		registerButton.setOnClickListener(registerListener);
		forgetPasswordButton.setOnClickListener(forgetPasswordListener);
	}
	
	OnClickListener loginListener = new OnClickListener() {

		@SuppressLint("NewApi")
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void onClick(View arg0) {
			if (userNameEditText.getText().toString().isEmpty()) {
				showUserNameEmptyError();
				return;
			}
			if (userPasswordEditText.getText().toString().isEmpty()) {
				showUserPasswordEmptyError();
				return;
			}
			progressDialogShow();
			AVUser.logInInBackground(userNameEditText.getText().toString(),
					userPasswordEditText.getText().toString(),
					new LogInCallback() {
						public void done(AVUser user, AVException e) {
							if (user != null) {
								progressDialogDismiss();
								startActivity(new Intent(LoginActivity.this, MainActivity.class));
								finish();
								
							} else {
								progressDialogDismiss();
								showLoginError();
							}
						}
					});
		}
	};

	OnClickListener forgetPasswordListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			startActivity(new Intent(LoginActivity.this , ForgetPasswordActivity.class));
			finish();
		}
	};
	
	OnClickListener registerListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			finish();
		}
	};

	private void progressDialogDismiss() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	private void progressDialogShow() {
		progressDialog = ProgressDialog
				.show(this,
						this.getResources().getText(
								R.string.dialog_message_title),
								this.getResources().getText(
								R.string.dialog_text_wait), true, false);
	}

	private void showLoginError() {
		new AlertDialog.Builder(this)
				.setTitle(
						this.getResources().getString(
								R.string.dialog_error_title))
				.setMessage(
						this.getResources().getString(
								R.string.error_login_error))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}

	private void showUserPasswordEmptyError() {
		new AlertDialog.Builder(this)
				.setTitle(
						this.getResources().getString(
								R.string.dialog_error_title))
				.setMessage(
						this.getResources().getString(
								R.string.error_register_password_null))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}

	private void showUserNameEmptyError() {
		new AlertDialog.Builder(this)
				.setTitle(
						this.getResources().getString(
								R.string.dialog_error_title))
				.setMessage(
						this.getResources().getString(
								R.string.error_register_user_name_null))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}
}
