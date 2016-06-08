package com.example.leanne.capstonedesign_1_;

/**
 * Created by Chloe on 4/13/2016.
 * 랭킹 화면에 나타나는 각 아이템에 출력될 내용물들 정의 클래스
 */
public class ListViewItem {
    private final String id;
    private final String major;
    private final String wish_duty;
    private final String certificates;
    private final String toeicScore;
    private boolean isFav;
    //---- see more ----
    private final String age;
    private final String wish_comp_type;
    private final String wish_comp;
    private final String gender;
    private final String uni;
    private final String isEmp;
    private final String gpa;
    private final String career;


    public ListViewItem(String id, String major, String wish_duty, String certificates, String toeicScore, boolean isFav, String age, String wish_comp_type, String wish_comp, String gender, String uni, String isEmp, String gpa, String career) {
        this.id = id;
        this.major = major;
        this.wish_duty = wish_duty;
        this.certificates = certificates;
        this.toeicScore = toeicScore;
        this.isFav = isFav;
        //---- see more ----
        this.age = age;
        this.wish_comp_type = wish_comp_type;
        this.wish_comp = wish_comp;
        this.gender = gender;
        this.uni = uni;
        this.isEmp = isEmp;
        this.gpa = gpa;
        this.career = career;
    }

    public String getId() {
        return id;
    }

    public String getMajor() {
        return major;
    }

    public String getWish_duty() {
        return wish_duty;
    }

    public String getCertificates() {
        return certificates;
    }

    public String getToeicScore() {
        return toeicScore;
    }

    public boolean getIsFav() {
        return isFav;
    }

    public void setIsFav(boolean isFav) {
        this.isFav = isFav;
    }

    public String getAge() {
        return age;
    }

    public String getWish_comp_type() {
        return wish_comp_type;
    }

    public String getWish_comp() {
        return wish_comp;
    }

    public String getGender() {
        return gender;
    }

    public String getUni() {
        return uni;
    }

    public String getIsEmp() {
        return isEmp;
    }

    public String getGpa() {
        return gpa;
    }

    public String getCareer() {
        return career;
    }
}