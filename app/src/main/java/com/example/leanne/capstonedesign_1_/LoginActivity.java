package com.example.leanne.capstonedesign_1_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by imsuyeon on 16. 4. 2..
 * 로그인 페이지
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private BackPressCloseHandler backPressCloseHandler;
    private EditText editTextID, editTextPW;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
//        editTextID.setText("yddnsw");
//        editTextPW.setText("6nj1v0");
	    editTextID.setText("2ss97tt");
	    editTextPW.setText("o29czg7");
    }

    private void initView() {
        backPressCloseHandler = new BackPressCloseHandler(this);
        Button buttonLogin = (Button) findViewById(R.id.button_login);
        TextView textViewRegister = (TextView) findViewById(R.id.textView_register);
        editTextID = (EditText)findViewById(R.id.editText_id);
        editTextPW = (EditText)findViewById(R.id.editText_pw);
        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                String id = editTextID.getText().toString();
                String pw = editTextPW.getText().toString();

                // temp
                //String dbID = "root";
                //String dbPW = "root";
                // end temp

                if (id.equals("") || pw.equals("")) {
                    Toast.makeText(this, "아이디 혹은 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    break;
                }
                RequestMsgSender loginMsgSender = (RequestMsgSender) new RequestMsgSender()
		                .execute("5;" + id + ";" + pw + ";");
                String loginResult = null;

                try {
                    loginResult = loginMsgSender.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }


	            assert loginResult != null;
	            if (loginResult.equals("false")) {
                    Toast.makeText(this, "아이디 혹은 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    editTextID.setText("");
                    editTextPW.setText("");
                } else {
                    String[] tokens = loginResult.split(";");
                    for (int i = 1; i < tokens.length; i++) {
                        if (tokens[i].equals("!")) tokens[i] = "";
                    }
                    LoggedInUser.getLoggedinUser().setId(tokens[1]);
                    LoggedInUser.getLoggedinUser().setUserName(tokens[2]);
                    LoggedInUser.getLoggedinUser().setPassWd(tokens[3]);
                    LoggedInUser.getLoggedinUser().setToeic(Integer.parseInt(tokens[4]));
                    LoggedInUser.getLoggedinUser().setAge(Integer.parseInt(tokens[5]));
                    LoggedInUser.getLoggedinUser().setMajor(tokens[6]);
                    LoggedInUser.getLoggedinUser().setCom_type(tokens[7]);
                    LoggedInUser.getLoggedinUser().setDuty(tokens[8]);
                    LoggedInUser.getLoggedinUser().setCom_name(tokens[9]);
                    LoggedInUser.getLoggedinUser().setGender(Boolean.parseBoolean(tokens[10]));
                    LoggedInUser.getLoggedinUser().setUniv(tokens[11]);
                    LoggedInUser.getLoggedinUser().setCertifi(tokens[12]);
                    LoggedInUser.getLoggedinUser().setIsEmp(Boolean.parseBoolean(tokens[13]));
                    LoggedInUser.getLoggedinUser().setGPA(Double.parseDouble(tokens[14]));
                    LoggedInUser.getLoggedinUser().setMaxGPA(Double.parseDouble(tokens[15]));
                    LoggedInUser.getLoggedinUser().setCareer(tokens[16]);
                    LoggedInUser.getLoggedinUser().setSearch_major(tokens[17]);
                    LoggedInUser.getLoggedinUser().setSearch_com_type(tokens[18]);
                    LoggedInUser.getLoggedinUser().setSearch_duty(tokens[19]);
                    LoggedInUser.getLoggedinUser().setSearch_com_name(tokens[20]);
                    LoggedInUser.getLoggedinUser().setSearch_gender(Boolean.parseBoolean(tokens[21]));
                    LoggedInUser.getLoggedinUser().setSearch_univ(tokens[22]);
                    Log.d("check loggedInUser",""+LoggedInUser.getLoggedinUser().getSearch_Univ());

                    LoggedInUser.getLoggedinUser().setSearch_age(Integer.parseInt(tokens[23]));
                    Log.d("check loggedInUser",""+LoggedInUser.getLoggedinUser().getSearch_Age());

                    LoggedInUser.getLoggedinUser().setFav_ids(tokens[24]);

                    Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                    Intent intentHome = new Intent(this, HomeActivity.class);
                    startActivity(intentHome);
                    this.overridePendingTransition(R.anim.animation_enter_right2left,
                            R.anim.animation_leave_right2left);
                    editTextID.setText("");
                    editTextPW.setText("");
                }
                break;
            case R.id.textView_register:
                Intent intentRegister = new Intent(this, RegisterActivity.class);
                startActivity(intentRegister);
                this.overridePendingTransition(R.anim.animation_enter_right2left,
                        R.anim.animation_leave_right2left);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();}
}
