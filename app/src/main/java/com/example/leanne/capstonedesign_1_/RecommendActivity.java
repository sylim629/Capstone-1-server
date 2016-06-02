package com.example.leanne.capstonedesign_1_;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Chloe on 5/11/2016.
 */
public class RecommendActivity extends Activity {

    private TextView rec_company_1, rec_company_2, rec_company_3, rec_duty_1, rec_duty_2, rec_duty_3, rec_rankings_id1, rec_rankings_id2, rec_rankings_id3, rec_rankings_major1, rec_rankings_major2, rec_rankings_major3, rec_rankings_wish_duty1, rec_rankings_wish_duty2, rec_rankings_wish_duty3, rec_rankings_certificates1, rec_rankings_certificates2, rec_rankings_certificates3, rec_rankings_toeicScore1, rec_rankings_toeicScore2, rec_rankings_toeicScore3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        initView();
    }

    private void initView() {
        rec_company_1 = (TextView) findViewById(R.id.rec_company_1);
        rec_company_2 = (TextView) findViewById(R.id.rec_company_2);
        rec_company_3 = (TextView) findViewById(R.id.rec_company_3);
        rec_duty_1 = (TextView) findViewById(R.id.rec_duty_1);
        rec_duty_2 = (TextView) findViewById(R.id.rec_duty_2);
        rec_duty_3 = (TextView) findViewById(R.id.rec_duty_3);
        rec_rankings_id1 = (TextView) findViewById(R.id.rec_rankings_id1);
        rec_rankings_id2 = (TextView) findViewById(R.id.rec_rankings_id2);
        rec_rankings_id3 = (TextView) findViewById(R.id.rec_rankings_id3);
        rec_rankings_major1 = (TextView) findViewById(R.id.rec_rankings_major1);
        rec_rankings_major2 = (TextView) findViewById(R.id.rec_rankings_major2);
        rec_rankings_major3 = (TextView) findViewById(R.id.rec_rankings_major3);
        rec_rankings_wish_duty1 = (TextView) findViewById(R.id.rec_rankings_wish_duty1);
        rec_rankings_wish_duty2 = (TextView) findViewById(R.id.rec_rankings_wish_duty2);
        rec_rankings_wish_duty3 = (TextView) findViewById(R.id.rec_rankings_wish_duty3);
        rec_rankings_certificates1 = (TextView) findViewById(R.id.rec_rankings_certificates1);
        rec_rankings_certificates2 = (TextView) findViewById(R.id.rec_rankings_certificates2);
        rec_rankings_certificates3 = (TextView) findViewById(R.id.rec_rankings_certificates3);
        rec_rankings_toeicScore1 = (TextView) findViewById(R.id.rec_rankings_toeicScore1);
        rec_rankings_toeicScore2 = (TextView) findViewById(R.id.rec_rankings_toeicScore2);
        rec_rankings_toeicScore3 = (TextView) findViewById(R.id.rec_rankings_toeicScore3);
        showInfo();
    }

    private void showInfo() {
        // 서버에서 받은 값들로 TextView에서 보여주기
        String rec_company1 = "한국전력공사";
        String rec_company2 = "한국수력원자력";
        String rec_company3 = "인천국제공항공사";

        String rec_duty1 = "시스템프로그래머";
        String rec_duty2 = "서버∙네트워크∙보안";
        String rec_duty3 = "데이터베이스 DBA";

        String rec_rankings_id_1 = "sdf12hf";
        String rec_rankings_id_2 = "abcde123";
        String rec_rankings_id_3 = "hellokitty";

        String rec_rankings_major_1= "컴퓨터공학부";
        String rec_rankings_major_2= "컴퓨터공학부";
        String rec_rankings_major_3= "컴퓨터공학부";

        String rec_rankings_wish_duty_1 = "시스템프로그래머";
        String rec_rankings_wish_duty_2 = "데이터베이스 DBA";
        String rec_rankings_wish_duty_3 = "서버∙네트워크∙보안";

        String rec_rankings_certificates_1 = "정보처리기사";
        String rec_rankings_certificates_2 = "정보처리기사";
        String rec_rankings_certificates_3 = "정보처리기사, 정보보안기사";

        String rec_rankings_toeicScore_1 = "750";
        String rec_rankings_toeicScore_2 = "785";
        String rec_rankings_toeicScore_3 = "830";

        rec_company_1.setText(rec_company1);
        rec_company_2.setText(rec_company2);
        rec_company_3.setText(rec_company3);

        rec_duty_1.setText(rec_duty1);
        rec_duty_2.setText(rec_duty2);
        rec_duty_3.setText(rec_duty3);

        rec_rankings_id1.setText(rec_rankings_id_1);
        rec_rankings_id2.setText(rec_rankings_id_2);
        rec_rankings_id3.setText(rec_rankings_id_3);

        rec_rankings_major1.setText(rec_rankings_major_1);
        rec_rankings_major2.setText(rec_rankings_major_2);
        rec_rankings_major3.setText(rec_rankings_major_3);

        rec_rankings_wish_duty1.setText(rec_rankings_wish_duty_1);
        rec_rankings_wish_duty2.setText(rec_rankings_wish_duty_2);
        rec_rankings_wish_duty3.setText(rec_rankings_wish_duty_3);

        rec_rankings_certificates1.setText(rec_rankings_certificates_1);
        rec_rankings_certificates2.setText(rec_rankings_certificates_2);
        rec_rankings_certificates3.setText(rec_rankings_certificates_3);

        rec_rankings_toeicScore1.setText(rec_rankings_toeicScore_1);
        rec_rankings_toeicScore2.setText(rec_rankings_toeicScore_2);
        rec_rankings_toeicScore3.setText(rec_rankings_toeicScore_3);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(R.anim.animation_enter_left2right, R.anim.animation_leave_left2right);
    }
}
