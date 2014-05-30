package com.manustudios.extra;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;

public class SubTopicDataSource {

	private static final String PREFTITLE = "subTopicTitle";
	private static final String PREFDESCRIPTION = "subTopicDescription";
	
	private SharedPreferences titlePrefs;
	private SharedPreferences descriptionPrefs;
	
	public SubTopicDataSource(Context context){
		titlePrefs = context.getSharedPreferences(PREFTITLE, Context.MODE_PRIVATE);
		descriptionPrefs = context.getSharedPreferences(PREFDESCRIPTION, Context.MODE_PRIVATE);
	}
	
	public void insertTitle(String keyTitle,  String data){
		
		SharedPreferences.Editor editorTitle = titlePrefs.edit();
		editorTitle.putString(keyTitle, data);
		editorTitle.commit();

	}
	
	public void insertDescription(String keyDescription, String data){
				
		SharedPreferences.Editor editorDesc = descriptionPrefs.edit();
		editorDesc.putString(keyDescription, data);
		editorDesc.commit();
	}
	
	public String getTitleData(String keyTitle){
		
		String title = titlePrefs.getString(keyTitle, null);
		return title;
	}
	
	public String getDescData(String keyDescription){
	
		String desc = descriptionPrefs.getString(keyDescription, null);
		return desc;
	}
}
