package com.example.leanne.capstonedesign_1_;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Chloe on 5/10/2016.
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
        acceptRate.setText("80%");  // 그냥 테스트값
    }

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(R.anim.animation_enter_left2right, R.anim.animation_leave_left2right);
    }
}
