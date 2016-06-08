package com.example.leanne.capstonedesign_1_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
 * Created by Chloe on 4/9/2016.
 */
public class LureResult2Activity extends Activity implements View.OnClickListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lure_result_2);
		initView();
	}

	private void initView() {
		Button buttonRegister = (Button) findViewById(R.id.go_register_button);
		buttonRegister.setOnClickListener(this);
		TextView textViewAlreadyMember = (TextView) findViewById(R.id.already_member);
		textViewAlreadyMember.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.go_register_button:
				Intent goRegister = new Intent(LureResult2Activity.this, RegisterActivity.class);
				startActivity(goRegister);
				overridePendingTransition(
						R.anim.animation_enter_right2left,
						R.anim.animation_leave_right2left);
				break;
			case R.id.already_member:
				DataHolder.getInstance().setSkipLure(true);
				Intent goLogin = new Intent(LureResult2Activity.this, LoginActivity.class);
				goLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(goLogin);
				break;
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
		overridePendingTransition(R.anim.animation_enter_left2right, R.anim.animation_leave_left2right);
	}

}
