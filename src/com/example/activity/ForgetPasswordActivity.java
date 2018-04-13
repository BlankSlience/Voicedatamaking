package com.example.activity;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.example.MainActivity;
import com.example.uiwork.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPasswordActivity extends AVBaseActivity{
	EditText emailText;
	Button findPasswordButton;
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.fragment_forget_password);
	    
	    emailText = (EditText) findViewById(R.id.editText_forget_password_email);
		findPasswordButton = (Button) findViewById(R.id.button_find_password);
		findPasswordButton.setOnClickListener(findPasswordListener);
	}
	
	OnClickListener findPasswordListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (emailText.getText().toString() != null) {
				AVUser.requestPasswordResetInBackground(emailText.getText()
						.toString(), new RequestPasswordResetCallback() {
					public void done(AVException e) {
						if (e == null) {
							Toast.makeText(ForgetPasswordActivity.this,
									R.string.forget_password_send_email,
									Toast.LENGTH_LONG).show();
							startActivity(MainActivity.class);
							finish();
						} else {
							showError(getString(R.string.forget_password_email_error));
						}
					}
				});
			} else {
				showError(getResources().getString(
						R.string.error_register_email_address_null));
			}
		}
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
