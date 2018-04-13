package com.example.activity;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.uiwork.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AVBaseActivity{
	Button registerButton;
	EditText userName;
	EditText userEmail;
	EditText userPassword;
	EditText userPasswordAgain;
	private ProgressDialog progressDialog;

	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.fragment_register);
	    
	    registerButton = (Button) findViewById(R.id.need_register_button);
		userName = (EditText) findViewById(R.id.editText_register_userName);
		userEmail = (EditText) findViewById(R.id.editText_register_email);
		userPassword = (EditText) findViewById(R.id.editText_register_userPassword);
		userPasswordAgain = (EditText) findViewById(R.id.editText_register_userPassword_again);

		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (userPassword.getText().toString()
						.equals(userPasswordAgain.getText().toString())) {
					if (!userPassword.getText().toString().isEmpty()) {
						if (!userName.getText().toString().isEmpty()) {
							if (isEmail(userEmail.getText().toString())) {
								progressDialogShow();
								register();
							} else {
								showError(getString(R.string.error_register_email_address_null));
							}
						} else {
							showError(getString(R.string.error_register_user_name_null));
						}
					} else {
						showError(getString(R.string.error_register_password_null));
					}
				} else {
					showError(getString(R.string.error_register_password_not_equals));
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void register() {
		AVUser user = new AVUser();
		user.setUsername(userName.getText().toString());
		user.setPassword(userPassword.getText().toString());
		user.setEmail(userEmail.getText().toString());
		user.signUpInBackground(new SignUpCallback() {
			public void done(AVException e) {
				progressDialogDismiss();
				if (e == null) {
					showRegisterSuccess();
					startActivity(LoginActivity.class);
					finish();
				} else {
					switch (e.getCode()) {
					case 202:
						showError(getString(R.string.error_register_user_name_repeat));
						break;
					case 203:
						showError(getString(R.string.error_register_email_repeat));
						break;
					default:
						showError(getString(R.string.network_error));
						break;
					}
				}
			}
		});
	}
	
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

	private void showRegisterSuccess() {
		new AlertDialog.Builder(this)
				.setTitle(
						this.getResources().getString(
								R.string.dialog_message_title))
				.setMessage(
						this.getResources().getString(
								R.string.success_register_success))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}
	
	
	 public static boolean isEmail(String strEmail) {
	        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
	        if (TextUtils.isEmpty(strEmail)) {
	            return false;
	        } else {
	            return strEmail.matches(strPattern);
	        }
	    }
}
