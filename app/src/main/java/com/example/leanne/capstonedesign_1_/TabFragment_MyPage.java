package com.example.leanne.capstonedesign_1_;

/**
 * Created by Chloe on 4/11/2016.
 * 개인정보수정 페이지. 유저의 기본 정보를 보여주고 버튼으로 이동 가능하다.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TabFragment_MyPage extends Fragment implements View.OnClickListener {

    private TextView textName, textId, textMajor, textWishCompType, textWishComp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_mypage, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        Button editMyInfo = (Button) v.findViewById(R.id.button_edit_myinfo);
        Button editRankingSettings = (Button) v.findViewById(R.id.button_edit_rankings);
        Button seeAcceptRate = (Button) v.findViewById(R.id.accept_rate_btn);
        Button seeRec = (Button) v.findViewById(R.id.recommend_btn);
        editMyInfo.setOnClickListener(this);
        editRankingSettings.setOnClickListener(this);
        seeAcceptRate.setOnClickListener(this);
        seeRec.setOnClickListener(this);

        textName = (TextView) v.findViewById(R.id.mypage_name);
        textId = (TextView) v.findViewById(R.id.mypage_id);
        textMajor = (TextView) v.findViewById(R.id.mypage_major);
        textWishCompType = (TextView) v.findViewById(R.id.mypage_wish_company_type);
        textWishComp = (TextView) v.findViewById(R.id.mypage_wish_company);
        showInfo();
    }

    private void showInfo() {
        // 서버에서 받은 값들로 TextView에서 보여주기
        String sName = LoggedInUser.getLoggedinUser().getUserName();
        String sId = LoggedInUser.getLoggedinUser().getId();
        String sMajor = LoggedInUser.getLoggedinUser().getMajor();
        String sWishCompType = LoggedInUser.getLoggedinUser().getCom_type();
        String sWishComp = LoggedInUser.getLoggedinUser().getCom_name();

        textName.setText(sName);
        textId.setText(sId);
        textMajor.setText(sMajor);
        textWishCompType.setText(sWishCompType);
        textWishComp.setText(sWishComp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_edit_myinfo:
                Intent goEditMyInfo = new Intent(getActivity(), EditMyInfoActivity.class);
                startActivity(goEditMyInfo);
                getActivity().overridePendingTransition(
                        R.anim.animation_enter_right2left,
                        R.anim.animation_leave_right2left);
                break;
            case R.id.button_edit_rankings:
                Intent goEditRankings = new Intent(getActivity(), EditRankingsActivity.class);
                startActivity(goEditRankings);
                getActivity().overridePendingTransition(
                        R.anim.animation_enter_right2left,
                        R.anim.animation_leave_right2left);
                break;
            case R.id.accept_rate_btn:
                Intent goAcceptRate = new Intent(getActivity(), AcceptRateActivity.class);
                startActivity(goAcceptRate);
                getActivity().overridePendingTransition(
                        R.anim.animation_enter_right2left,
                        R.anim.animation_leave_right2left);
                break;
            case R.id.recommend_btn:
                Intent goRecommend = new Intent(getActivity(), RecommendActivity.class);
                startActivity(goRecommend);
                getActivity().overridePendingTransition(
                        R.anim.animation_enter_right2left,
                        R.anim.animation_leave_right2left);
                break;
        }
    }

}