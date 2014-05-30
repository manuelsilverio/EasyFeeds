package com.manustudios.adapters;

import java.util.ArrayList;
import java.util.Map;

import com.manustudios.extra.Feed;
import com.manustudios.extra.FeedSaver;
import com.manustudios.extra.ListGroup;
import com.manustudios.extra.ListTopicArray;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.manustudios.easyfeeds.MainActivity;
import com.manustudios.easyfeeds.R;
import com.manustudios.easyfeeds.SearchPostsTask;

public class SavedTopicAdapter extends BaseExpandableListAdapter /*implements ExpandableListView.OnChildClickListener*/{

	private Context mContext;
	private ArrayList<ListTopicArray> mTopics;
	private int mLayoutGroup;
	private int mLayoutChild;
	private ViewPager mPager;
	final ProgressBar mProgressBar;
	private FragmentManager mFragMan;
	private DrawerLayout mDrawerLayout;
	
	public SavedTopicAdapter(Context context, ArrayList<ListTopicArray> topics, int layoutGroup, int layoutChild, DrawerLayout drawerLayout, ViewPager pager, 
			ProgressBar progressBar, FragmentManager fm){
		mContext = context;
		mTopics = topics;
		mLayoutGroup = layoutGroup;
		mLayoutChild = layoutChild;
		mDrawerLayout = drawerLayout;
		mPager = pager;
		mProgressBar = progressBar;
		mFragMan = fm;
	}
	
	
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ListTopicArray topic = (ListTopicArray) getGroup(groupPosition);
		return topic.getItems().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean arg2, View view,
			ViewGroup arg4) {
		
		if(view == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = (View) inflater.inflate(mLayoutChild, null);
		}
		
		
		
		Feed feed = (Feed) getChild(groupPosition, childPosition);
		TextView feedText = (TextView) view.findViewById(R.id.navLeftSubTopicChild);
		feedText.setText(feed.getTitle());
		ImageButton removeFeedButton = (ImageButton) view.findViewById(R.id.buttonRemoveNavLeftChild);

		//SET CLICK LISTENER SO THE USER CAN DELETE FAVORITE FEEDS FROM FAVORITE LIST
		ListTopicArray topic = (ListTopicArray) getGroup(groupPosition);
		Feed feedChild = topic.getItems().get(childPosition);
		final String feedChildUrl = feedChild.getFeedUrl();
		final int topicPositionChild = topic.getTopicPosition();
		final int groupPos = groupPosition;
		final int childPos = childPosition;


		feedText.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				FeedSaver feedSaver = new FeedSaver(mContext);
				Feed feed = feedSaver.getSavedFeeds().get(groupPos).getItems().get(childPos);
				
				SearchPostsTask blogSearcher = new SearchPostsTask(mContext, mProgressBar, mPager, mFragMan);
				blogSearcher.execute(feed.getFeedUrl().toString());
				mDrawerLayout.closeDrawers();
			}
		});


		
		removeFeedButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FeedSaver feedSaver = new FeedSaver(mContext);
				feedSaver.deleteFeed(feedChildUrl, topicPositionChild);
				MainActivity.refreshLeftNav();
				if(getChildrenCount(groupPos)>1){
					MainActivity.expandLeftNavGroup(groupPos);
				}
				
				MainActivity.refreshRightSubNav(topicPositionChild);
			}
		});

		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		ListTopicArray topic = (ListTopicArray) getGroup(groupPosition);
		//Log.i("SAVED TOPICS DEBUG", ""+topic.getItems().size());
		return topic.getItems().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		
		return mTopics.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mTopics.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		//ArrayList<ListTopicArray> topics = ;
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean childPosition, View view, ViewGroup parent) {
		
		if(view == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = (View) inflater.inflate(mLayoutGroup, null);
		}
		
		ListTopicArray topic = (ListTopicArray) getGroup(groupPosition);
		
		TextView topicText = (TextView) view.findViewById(R.id.navLeftTopicTextView);
		topicText.setText(topic.getName());
		
		//topicText.setText("Test");
		return view;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	


}
