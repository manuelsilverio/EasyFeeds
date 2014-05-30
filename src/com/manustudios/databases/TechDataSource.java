package com.manustudios.databases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.manustudios.easyfeeds.ManagerSubTopics;
import com.manustudios.extra.Feed;

import android.content.Context;
import android.content.SharedPreferences;

public class TechDataSource {

	private final String PREFTITLE = "TitleTech";
	private final String PREF_FEED_URL = "feedUrlTech";
	private final String PREF_IMG_URL = "imgUrlTech";
	
	private SharedPreferences titlePrefs;
	private SharedPreferences feedPrefs;
	private SharedPreferences imgPrefs;
	
	private String mKey;
	
	public TechDataSource(Context context, String key){
		titlePrefs = context.getSharedPreferences(PREFTITLE, Context.MODE_PRIVATE);
		feedPrefs = context.getSharedPreferences(PREF_FEED_URL, Context.MODE_PRIVATE);
		imgPrefs = context.getSharedPreferences(PREF_IMG_URL, Context.MODE_PRIVATE);
		
		mKey = key;
	}
	
	public boolean checkStored(String feedUrl){
		
		boolean result = false;
		if(feedPrefs.getString(feedUrl, null)!=null){
			result = true;
		}
		return result;
	}
	
	public void saveTitle(String title){
		SharedPreferences.Editor editor = titlePrefs.edit();
		editor.putString(mKey, title);
		editor.commit();
	}
	
	public void saveImgUrl(String imgUrl){
		SharedPreferences.Editor editor = imgPrefs.edit();
		editor.putString(mKey, imgUrl);
		editor.commit();
	}
	
	public void saveFeedUrl(String feedUrl){
		SharedPreferences.Editor editor = feedPrefs.edit();
		editor.putString(mKey, feedUrl);
		editor.commit();
	}
	
	
	public ArrayList<Feed> getFeeds(){
		Map<String, ?> titles = titlePrefs.getAll();
		Map<String, ?> feedUrls = feedPrefs.getAll();
		Map<String, ?> imgUrls = imgPrefs.getAll();
		
		@SuppressWarnings("unchecked")
		Collection<String> newTitles = (Collection<String>) titles.values();
		@SuppressWarnings("unchecked")
		Collection<String> newFeedUrls = (Collection<String>) feedUrls.values();
		@SuppressWarnings("unchecked")
		Collection<String> newImgUrls = (Collection<String>) imgUrls.values();
		
		ArrayList<Feed> feedList = new ArrayList<Feed>();
		if(titles.size()>0){
			for (int i = 0; i < titles.size(); i++) {
				//CREATE FEED SET THE VALUES AND STORE INSIDE ARRAYLIST
				Feed feed = new Feed();
				feed.setTitle(newTitles.toArray()[i].toString());
				feed.setFeedUrl(newFeedUrls.toArray()[i].toString());
				feed.setImgUrl(newImgUrls.toArray()[i].toString());
				feedList.add(feed);
			}
		}else{
			feedList = null;
		}
		
		
		return feedList;
	}
	
	public Map<String, ?> getTitles(){
		return titlePrefs.getAll();
	}
	
	public Map<String, ?> getFeedUrl(){	
		return feedPrefs.getAll();
	}
	
	public Map<String, ?> getImgUrl(){	
		return imgPrefs.getAll();
	}
	
	public void deleteData(String key){
		if(titlePrefs.contains(key)){
			SharedPreferences.Editor editorTitle = titlePrefs.edit();
			editorTitle.remove(key);
			editorTitle.commit();
		}
		if(imgPrefs.contains(key)){
			SharedPreferences.Editor editorImg = imgPrefs.edit();
			editorImg.remove(key);
			editorImg.commit();
		}
		if(feedPrefs.contains(key)){
			SharedPreferences.Editor editorUrl = feedPrefs.edit();
			editorUrl.remove(key);
			editorUrl.commit();
		}
		
		
	}
	
}
