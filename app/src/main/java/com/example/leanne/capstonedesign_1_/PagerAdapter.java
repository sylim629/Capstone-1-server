package com.example.leanne.capstonedesign_1_;

/*
 * Created by Chloe on 4/11/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
	int mNumOfTabs;

	public PagerAdapter(FragmentManager fm, int NumOfTabs) {
		super(fm);
		this.mNumOfTabs = NumOfTabs;
	}

	@Override
	public Fragment getItem(int position) {

		switch (position) {
			case 0:
				return new TabFragment_Home();
			case 1:
				return new TabFragment_Ranking();
			case 2:
				return new TabFragment_Favs();
			case 3:
				return new TabFragment_MyPage();
			default:
				return null;
		}
	}

	@Override
	public int getCount() {
		return mNumOfTabs;
	}
}