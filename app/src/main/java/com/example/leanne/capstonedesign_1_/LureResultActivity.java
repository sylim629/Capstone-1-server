package com.example.leanne.capstonedesign_1_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
 * Created by Chloe on 4/5/2016.
 */
public class LureResultActivity extends Activity implements View.OnClickListener {

	private TextView scoreToeic;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lure_result);

		initView();
	}

	private void initView() {
		Button buttonNext = (Button) findViewById(R.id.go_next_button);
		buttonNext.setOnClickListener(this);
		scoreToeic = (TextView) findViewById(R.id.result_text_toeic);
		getToeicScore();    // show score
	}

	private void getToeicScore() {
		String score;
		// get score from server as String
		score = SocketConnector.getConnectedSocket().getReplyMsg();
		scoreToeic.setText(score + "%");
	}

	@Override
	public void onClick(View v) {
	    /**/
		Intent goNext = new Intent(LureResultActivity.this, LureResult2Activity.class);
		startActivity(goNext);
		overridePendingTransition(
				R.anim.animation_enter_right2left,
				R.anim.animation_leave_right2left);
	}

	@Override
	public void onBackPressed() {
		this.finish();
		overridePendingTransition(R.anim.animation_enter_left2right, R.anim.animation_leave_left2right);
	}
}
