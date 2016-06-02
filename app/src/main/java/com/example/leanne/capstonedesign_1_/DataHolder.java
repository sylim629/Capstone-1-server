package com.example.leanne.capstonedesign_1_;

/**
 * Created by Chloe on 4/10/2016.
 */
public class DataHolder {

    public DataHolder() {
        skipLure = false;    // 매번 누르기 귀찮아서 일단 true로 해놓음. 나중에 꼭 false로 다시 바꾸기!
    }

    private boolean skipLure;

    public void setSkipLure(boolean skipLure) {
        this.skipLure = skipLure;
    }

    public boolean getSkipLure() {
        return skipLure;
    }

    // ***
    private static final DataHolder holder = new DataHolder();

    public static DataHolder getInstance() {
        return holder;
    }
}
