package com.example.leanne.capstonedesign_1_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Chloe on 4/13/2016.
 */
public class SearchActivity extends Activity implements View.OnClickListener {

    private Button search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initView();
    }

    private void initView() {
        //search = (Button)findViewById(R.id.button_search);
        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //case R.id.button_search:
               /* Intent goSearchResult = new Intent(this, SearchResultActivity.class);
                startActivity(goSearchResult);
                this.overridePendingTransition(R.anim.animation_enter_left2right, R.anim.animation_leave_left2right);
                break;*/
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(R.anim.animation_enter_left2right, R.anim.animation_leave_left2right);
    }
}
