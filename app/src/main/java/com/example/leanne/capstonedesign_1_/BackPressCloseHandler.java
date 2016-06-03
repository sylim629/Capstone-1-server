package com.example.leanne.capstonedesign_1_;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by imsuyeon on 16. 4. 2..
 * 첫 페이지에서 뒤로 버튼을 두번눌러야 종료 가능
 */
public class BackPressCloseHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",
                Toast.LENGTH_SHORT);
        toast.show();
    }
}
