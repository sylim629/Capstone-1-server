package com.example.leanne.capstonedesign_1_;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Chloe on 5/11/2016.
 *
 */
public class RecommendActivity extends Activity {

	private TextView rec_company_1, rec_company_2, rec_company_3, rec_duty_1, rec_duty_2, rec_duty_3, rec_rankings_id1, rec_rankings_id2, rec_rankings_id3, rec_rankings_major1, rec_rankings_major2, rec_rankings_major3, rec_rankings_wish_duty1, rec_rankings_wish_duty2, rec_rankings_wish_duty3, rec_rankings_certificates1, rec_rankings_certificates2, rec_rankings_certificates3, rec_rankings_toeicScore1, rec_rankings_toeicScore2, rec_rankings_toeicScore3;

	private List<ListViewItem> mItems;
	private String rankingResult;   // 여기에 서버에서 스트링 받기
	String comp1, comp2, comp3, duty1, duty2, duty3;

	private String[] idInfo = new String[3];
	private String[] majorInfo = new String[3];
	private String[] dutyInfo = new String[3];
	private String[] certifiInfo = new String[3];
	private String[] toeicInfo = new String[3];
	private String[] ageInfo = new String[3];
	private String[] wishCompTypeInfo = new String[3];
	private String[] wishCompInfo = new String[3];
	private String[] genderInfo = new String[3];
	private String[] uniInfo = new String[3];
	private String[] empInfo = new String[3];
	private String[] gpaInfo = new String[3];
	private String[] careerInfo = new String[3];


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        Resources resources = getResources();

        RequestMsgSender recommendedInfoMsgSender = (RequestMsgSender) new RequestMsgSender().execute("14;");
        String rankingResult = null;

        try {
            rankingResult = recommendedInfoMsgSender.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

		String[] tokens = rankingResult.split(";");
		for(int i = 0 ; i < tokens.length ; i++){
			if(tokens[i].equals("!")) tokens[i]="";
		}

		ArrayList<String> idFavs = new ArrayList<String>();
		idFavs = LoggedInUser.getLoggedinUser().getFav_ids();
		boolean[] isFavArray = new boolean[3];
		int k=0;
		for(int i=1; k<3; i+=14, k++) {
			String id = tokens[i];
			for(int l=0; l<idFavs.size(); l++) {
				if (id.equals(idFavs.get(l))) {    // LoggedInUser의 Fav ID와 비교해서 일치하면 isFav = true. 아니면 false
					isFavArray[k] = true;
					break;
				}else
					isFavArray[k] = false;
			}
		}

		comp1 = tokens[0];
		comp2 = tokens[1];
		comp3 = tokens[2];
		duty1 = tokens[3];
		duty2 = tokens[4];
		duty3 = tokens[5];

		String strToDelete = comp1 + ";" + comp2 + ";" + comp3 + ";" + duty1 + ";" + duty2 + ";" + duty3 +  ";";
		rankingResult = rankingResult.replace(strToDelete, "");
		String[] cutTokens = rankingResult.split(";");

		int j = 0;
		for(int i = 0 ; j < 3 ; i+=14, j++ ) {
			////////////
			String cert = cutTokens[i+9];
			String[] cert_tokens = cert.split("\\|");
			String tmp="";
			for(int l=0; l<cert_tokens.length; l++){
				if(!cert_tokens[l].equals("")){
					tmp = tmp + cert_tokens[l] + " | ";
				}
			}
			tmp = tmp.substring(0, tmp.length()-2);
			////////////
			String str_idInfo = new String("ID : ");
			String str_majorInfo = new String("전공 : ");
			String str_dutyInfo = new String("직무 : ");
			String str_certifiInfo = new String("자격증 : ");
			String str_toeicInfo = new String("토익 :  ");
			String str_ageInfo = new String("나이 : ");
			String str_wishCompTypeInfo = new String("희망 회사 종류: ");
			String str_wishCompInfo = new String("희망 회사: ");
			String str_genderInfo = new String("성별: ");
			String str_uniInfo = new String("대학: ");
			String str_empInfo = new String("취업여부: ");
			String str_gpaInfo = new String("학점: ");
			String str_careerInfo = new String("경력: ");
			idInfo[j] = str_idInfo += cutTokens[i];
			majorInfo[j] = str_majorInfo += modifyNullString(cutTokens[i + 3]);
			dutyInfo[j] = str_dutyInfo += modifyNullString(cutTokens[i + 5]);
			toeicInfo[j] = str_toeicInfo += modifyNullString(cutTokens[i + 1]);
			certifiInfo[j] = str_certifiInfo + modifyNullString(tmp);
			ageInfo[j] = str_ageInfo += modifyNullString(cutTokens[i + 2]);
			wishCompTypeInfo[j] = str_wishCompTypeInfo += modifyNullString(cutTokens[i + 4]);
			wishCompInfo[j] = str_wishCompInfo += modifyNullString(cutTokens[i + 6]);
			genderInfo[j] = str_genderInfo += modifyNullString(cutTokens[i + 7]);
			uniInfo[j] = str_uniInfo += modifyNullString(cutTokens[i + 8]);
			empInfo[j] = str_empInfo += modifyNullString(cutTokens[i + 10]);
			gpaInfo[j] = str_gpaInfo += (modifyNullString(cutTokens[i + 11]) + "/" + modifyNullString(cutTokens[i + 12]));
			careerInfo[j] = str_careerInfo += alterCareer(cutTokens[i + 13]);
		}

		setContentView(R.layout.activity_recommend);

		initView();
	}

	public String modifyNullString(String input) {
		String output = input.trim();
		if(output.equals("null")) {
			output = "없음";
		}
		return output;
	}

	public String alterCareer(String input){
		String result = "";
		if(input.equals("")) {
			result = "없음";
		}else{
			input = input.replace("|","");
			//input = input.substring(1, input.length()-1);
			if(!input.equals("")){
				String[] tokens = input.split(":",0);
				for(int j = 0 ; j < tokens.length ; j++ ){
					result += (tokens[j] + " ");
				}
			}
		}
		return result;
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

		rec_company_1.setText(comp1);
		rec_company_2.setText(comp2);
		rec_company_3.setText(comp3);

		rec_duty_1.setText(duty1);
		rec_duty_2.setText(duty2);
		rec_duty_3.setText(duty3);

		rec_rankings_id1.setText(idInfo[0]);
		rec_rankings_id2.setText(idInfo[1]);
		rec_rankings_id3.setText(idInfo[2]);

		rec_rankings_major1.setText(majorInfo[0]);
		rec_rankings_major2.setText(majorInfo[1]);
		rec_rankings_major3.setText(majorInfo[2]);

		rec_rankings_wish_duty1.setText(dutyInfo[0]);
		rec_rankings_wish_duty2.setText(dutyInfo[1]);
		rec_rankings_wish_duty3.setText(dutyInfo[2]);

		rec_rankings_certificates1.setText(certifiInfo[0]);
		rec_rankings_certificates2.setText(certifiInfo[1]);
		rec_rankings_certificates3.setText(certifiInfo[2]);

		rec_rankings_toeicScore1.setText(toeicInfo[0]);
		rec_rankings_toeicScore2.setText(toeicInfo[1]);
		rec_rankings_toeicScore3.setText(toeicInfo[2]);
	}

	@Override
	public void onBackPressed() {
		this.finish();
		overridePendingTransition(R.anim.animation_enter_left2right, R.anim.animation_leave_left2right);
	}
}
