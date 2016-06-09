package com.example.leanne.capstonedesign_1_;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Created by Chloe on 5/10/2016.
 * 합격률 보여주는 페이지
 */
public class AcceptRateActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_rate);

        initView();
    }

    private void initView() {
        TextView acceptRate = (TextView)findViewById(R.id.accept_rate);
        // 합격 가능성 % 값 받기

        RequestMsgSender acceptRateMsgSender = (RequestMsgSender) new RequestMsgSender().execute("13;");
        String acceptRateResult = null;

		try {
			acceptRateResult = acceptRateMsgSender.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		acceptRate.setText(acceptRateResult);  // 그냥 테스트값
	}

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(R.anim.animation_enter_left2right, R.anim.animation_leave_left2right);
    }
}
