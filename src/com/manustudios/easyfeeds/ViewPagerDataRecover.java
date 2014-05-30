package com.manustudios.easyfeeds;

import android.content.Context;
import android.content.SharedPreferences;

public class ViewPagerDataRecover {

	private final String KEY_POS_PREFS ="positionPrefskey";
	private final String KEY_PREFS_GROUP_POSITION = "mainScreenViewPagerGroupPos";
	private final String KEY_PREFS_CHILD_POSITION = "mainScreenViewPagerChildPos";
	
	private SharedPreferences positionPrefs;
	public ViewPagerDataRecover(Context context){
		positionPrefs = context.getSharedPreferences(KEY_POS_PREFS, Context.MODE_PRIVATE);
	}
	
	public void setLastGroupPos(int groupPos){
		SharedPreferences.Editor editor = positionPrefs.edit();
		editor.putInt(KEY_PREFS_GROUP_POSITION, groupPos);
		editor.commit();
	}
	public void setLastChildPos(int childPos){
		SharedPreferences.Editor editor = positionPrefs.edit();
		editor.putInt(KEY_PREFS_CHILD_POSITION, childPos);
		editor.commit();
	}
	
	public int getLastGroupPos(){
		return positionPrefs.getInt(KEY_PREFS_GROUP_POSITION, 0);
	}
	
	public int getLastChildPost(){
		return positionPrefs.getInt(KEY_PREFS_CHILD_POSITION, 0);
	}
}
