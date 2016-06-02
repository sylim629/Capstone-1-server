package com.example.leanne.capstonedesign_1_;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.telecom.Call;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by Chloe on 4/5/2016.
 */
public class LureActivity extends Activity implements View.OnClickListener {

    private BackPressCloseHandler backPressCloseHandler;
    private Button buttonResult, closeButton;
    private EditText lureScore;
    private CheckBox neverShow;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lure);

       //popLoginFragment(); // close previous login fragment

        initView();
    }

    private void popLoginFragment() {
        FragmentManager fm = getFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void initView() {
        backPressCloseHandler = new BackPressCloseHandler(this);
        lureScore = (EditText)findViewById(R.id.lure_score);
        buttonResult = (Button)findViewById(R.id.result_button);
        buttonResult.setOnClickListener(this);
        closeButton = (Button)findViewById(R.id.lure_close_button);
        closeButton.setOnClickListener(this);
        neverShow = (CheckBox)findViewById(R.id.neverShow);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.result_button:
                String stringScore = lureScore.getText ().toString();
                if(stringScore.equals(""))
                    Toast.makeText(this, "점수를 입력해주시오.", Toast.LENGTH_SHORT).show();
                else
                {
                    int toeicScore = Integer.parseInt(stringScore);
                    boolean scoreValidate = false;
                    if (toeicScore >= 0 && toeicScore <= 990) {
                        scoreValidate = true;
                        RequestMsgSender toeicMsgSender = (RequestMsgSender) new RequestMsgSender().execute("1;"+toeicScore+";");
                        String result = null;

                        try {
                            result = toeicMsgSender.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }



                        Intent showResult = new Intent(LureActivity.this, LureResultActivity.class);
                        startActivity(showResult);
                        overridePendingTransition(
                                R.anim.animation_enter_right2left,
                                R.anim.animation_leave_right2left);
                         /* Do something with toeicScore.
                         Show as graphic or sth..
                         And give to next activity. */
                    } else {
                        Toast.makeText(this, "올바른 점수를 입력해주시오.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.lure_close_button:
                if (neverShow.isChecked()) {
                    DataHolder.getInstance().setSkipLure(true);
                }
                Intent goLogin = new Intent(LureActivity.this, LoginActivity.class);
                goLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(goLogin);
                overridePendingTransition(R.anim.animation_enter_right2left, R.anim.animation_leave_right2left);
                //showAlertDialogue();
                break;
        }
    }

    /*private void showAlertDialogue(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("앱 종료");
        alertDialogBuilder
                .setMessage("정말 종료하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton(Html.fromHtml("<font color=@color/mint>네</font>"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LureActivity.this.finish();
                    }
                })
                .setNegativeButton(Html.fromHtml("<font color='#80BFA2'>아니요</font>"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }*/

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

}
