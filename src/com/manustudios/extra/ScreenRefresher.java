package com.manustudios.extra;

import java.util.ArrayList;
import java.util.HashMap;

import com.manustudios.adapters.SavedTopicAdapter;
import com.manustudios.adapters.TopicAdapter;
import com.manustudios.easyfeeds.ManagerTopics;
import com.manustudios.easyfeeds.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ScreenRefresher extends Activity{

	private Context mContext;
	private ListView mRightDrawerList;
	private ExpandableListView mLeftDrawerList;
	private LinearLayout mleftDrawerFather;
	
	public ScreenRefresher(Context context, View view){
		mContext = context;
		mRightDrawerList = (ListView) findViewById(R.id.nav_list_right);
		mLeftDrawerList = (ExpandableListView) findViewById(R.id.nav_list_left);
		mleftDrawerFather = (LinearLayout) findViewById(R.id.father_nav_left);
	}
	
	
	public void refreshRightNav(){
		//CREATE TOPICS AND ADAPTER... PLUS SET CUSTOMIZED ADAPTER
		ManagerTopics topics =  new ManagerTopics();												//INSTANTIATE CLASS WITH TOPICS
		ArrayList<HashMap<String, String>> TopicsList = new ArrayList<HashMap<String, String>>();	// INSTANTIATE ARRAYLIST TO STORE TOPICS
		TopicsList = topics.getTopics();															//GET TOPICS ARRAYLSIT FROM CLASS
		TopicAdapter adapter = new TopicAdapter(mContext, R.layout.topic_list, TopicsList);				//INSTANTIATE CUSTOMIZED ADAPTER

		mRightDrawerList.setAdapter(adapter);															//SET ADAPTER
	}
	/*
	public void refreshLeftNav(){
		//CREATE LEFT NAV ADAPTER
		FeedSaver feedProvider = new FeedSaver(mContext);
		ArrayList<ListTopicArray> topicList = feedProvider.getSavedFeeds();
		SavedTopicAdapter leftAdapter = new SavedTopicAdapter(mContext, topicList, R.layout.nav_left_topic_group, 
				R.layout.nav_left_sub_topic_child);
		mLeftDrawerList.setAdapter(leftAdapter);
	}
*/
	
	
}
