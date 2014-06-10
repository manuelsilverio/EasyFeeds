package com.manustudios.easyfeeds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;

import android.app.Fragment;
import android.app.FragmentManager;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;

import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.manustudios.adapters.MainScreenAdapter;
import com.manustudios.adapters.SavedTopicAdapter;
import com.manustudios.adapters.SubTopicAdapter;
import com.manustudios.adapters.TopicAdapter;
import com.manustudios.easyfeeds.R;
import com.manustudios.extra.Feed;
import com.manustudios.extra.FeedSaver;
import com.manustudios.extra.ListTopicArray;
import com.manustudios.extra.ManagerDate;

public class MainActivity extends FragmentActivity {

	public String mSearchText = "xataka";
	public static final String GOOGLE_API_URL = "https://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q=xataka";
	public static final String GOOGLE_API_LOAD = "https://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q=";
	public static final String GOOGLE_API_POSTS_QTY = "&num=25";
	public static final String TAG = "EasyNews TAG";
	
	//KEYS FOR GETTING THE STRING DATA IN AND OUT OF THE SPARSE ARRAYS WHICH GOES ON THE MAIN ACTIVITY LISTVIEW
	public static final int KEY_TITLE = 11111;
	public static final int KEY_CONTENT = 22222;
	public static final int KEY_IMAGE = 33333;
	public static final int KEY_DATE = 44444;
	public static final int KEY_LINK = 55555;
	
	
	public static int num_pages = 3;
	public static int mPostPerPage = 5;
	
	protected JSONObject jsonData = null;
	protected static ProgressBar mProgressBar;
	
	private static Context mContext;
	static ArrayList<HashMap<String, String>> mSubTopicsList; 	// INSTANTIATE ARRAYLIST TO STORE TOPICS
	
	
	//DRAWER VARIABLES DECLARATION
	private static DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private static ListView mRightDrawerList;
	private static ExpandableListView mLeftDrawerList;
	private LinearLayout mleftDrawerFather;
	private LinearLayout mRightDrawerFather;
	
	private static ViewPager mPager;
    private PagerAdapter mPagerAdapter;
   
    private Button mAddContentButton;
    private Button mRemoveContentButton;
    private Button mChooseTopic;

    private static FragmentManager mFragMan;
    // ----------------------------------------------------------------
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i("Activity Recreation", "Create");
		
		mFragMan = getFragmentManager();
		mContext = this;
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		mSubTopicsList = new ArrayList<HashMap<String, String>>();
		
		//CHECK FOR NETWORK.. IF THERE IS NETWORK DO THE HEAVY WORK.. LOAD THE FIRST SCREEN
		if(checkNetwork()){
			mProgressBar.setVisibility(ProgressBar.VISIBLE);
			
		}
		else{ 		//IF NOT SHOW A MERCYFULL MESSAGE
			Toast.makeText(this, "Network is off!", Toast.LENGTH_LONG);
		}
		
		
		
		//DECLARE DRAWER LAYOUT AND DRAWER NAVIGATION LIST
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mRightDrawerList = (ListView) findViewById(R.id.nav_list_right);
		mRightDrawerFather = (LinearLayout) findViewById(R.id.father_nav_right);
		
		mLeftDrawerList = (ExpandableListView) findViewById(R.id.nav_list_left);
		mleftDrawerFather = (LinearLayout) findViewById(R.id.father_nav_left);
		
		mAddContentButton = (Button) findViewById(R.id.nav_left_add_button);
		mRemoveContentButton = (Button) findViewById(R.id.nav_left_remove_button);
		mChooseTopic = (Button) findViewById(R.id.nav_right_topic_button);
		mChooseTopic.setVisibility(View.INVISIBLE);
		
		mPager = (ViewPager) findViewById(R.id.pager_main_list);
		
		//REFRESHING AND SETTING LISTENERS
		refreshLeftNav();
		//mLeftDrawerList.setOnChildClickListener(new DrawerLeftClickListener());
		mleftDrawerFather.bringToFront();
		
		refreshRightNav();
		mRightDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mRightDrawerFather.bringToFront();
		
		
		
		//Add Buttons Listeners
		
		mAddContentButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawers();
				mDrawerLayout.openDrawer(Gravity.END);
			}
		});
		

		
		mChooseTopic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refreshRightNav();
				mChooseTopic.setVisibility(View.INVISIBLE);
				mRightDrawerList.setOnItemClickListener(new DrawerItemClickListener());
				mRightDrawerFather.bringToFront();
			}
		});
		
		// enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        mDrawerToggle = new ActionBarDrawerToggle
        		(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){

					@Override
					public void onDrawerClosed(View drawerView) {
						super.onDrawerClosed(drawerView);
					}

					@Override
					public void onDrawerOpened(View drawerView) {
						if(drawerView.getId() == R.id.father_nav_left){
							mDrawerLayout.closeDrawer(Gravity.END);
							Log.i("Nav Drawer", "Chk");
						}
						super.onDrawerOpened(drawerView);
						
					}
        	
        };
		
        mDrawerLayout.setDrawerListener(mDrawerToggle);
		
        
	}
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i("Activity Recreation", "Start");
	}



	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}



	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}



	@Override
	protected void onResume() {
		super.onResume();
		ViewPagerDataRecover mainScreenDataRec = new ViewPagerDataRecover(MainActivity.this);
		refreshMainScreen(mainScreenDataRec.getLastGroupPos(), mainScreenDataRec.getLastChildPost());
	}



	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		Log.i("Activity Recreation", "Restore");
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("Activity Recreation", "Pause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i("Activity Recreation", "Stop");
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("Activity Recreation", "Destroy");
	}

	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_nav_drawer, menu);
		return true;
	}
	
	
	//Search button press --- NAVIGATION DRAWER
	@SuppressLint("InlinedApi")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
       if (mDrawerToggle.onOptionsItemSelected(item)) {
           return true;
       }
		
       switch(item.getItemId()) {
       case R.id.menu_nav_drawer_search:
    	   mDrawerLayout.closeDrawers();
    	   mDrawerLayout.openDrawer(Gravity.END);        

    	   return true;
        default:
            return super.onOptionsItemSelected(item);
        }
	}
	
	
	
	
	//------------- STATIC FUNCTIONS ----------------	
	
	
	

	public static void refreshRightNav(){
		//CREATE TOPICS AND ADAPTER... PLUS SET CUSTOMIZED ADAPTER
		ManagerTopics topics =  new ManagerTopics();												//INSTANTIATE CLASS WITH TOPICS
		ArrayList<HashMap<String, String>> TopicsList = new ArrayList<HashMap<String, String>>();	// INSTANTIATE ARRAYLIST TO STORE TOPICS
		TopicsList = topics.getTopics();															//GET TOPICS ARRAYLSIT FROM CLASS
		TopicAdapter adapter = new TopicAdapter(mContext, R.layout.topic_list, TopicsList);				//INSTANTIATE CUSTOMIZED ADAPTER

		mRightDrawerList.setAdapter(adapter);															//SET ADAPTER
	}
	
	public static void refreshLeftNav(){
		//CREATE LEFT NAV ADAPTER
		FeedSaver feedProvider = new FeedSaver(mContext);
		ArrayList<ListTopicArray> topicList = feedProvider.getSavedFeeds();
		SavedTopicAdapter leftAdapter = new SavedTopicAdapter(mContext, topicList, R.layout.nav_left_topic_group, 
				R.layout.nav_left_sub_topic_child, mDrawerLayout, mPager, mProgressBar, mFragMan);
		mLeftDrawerList.setAdapter(leftAdapter);
		
	}
	
	public static void expandLeftNavGroup(int position){
		mLeftDrawerList.expandGroup(position);
	}

	public static void refreshRightSubNav(int position){
			ManagerSubTopics subTopics =  new ManagerSubTopics();												//INSTANTIATE CLASS WITH TOPICS
	
			SubTopicAdapter adapter;
    		mSubTopicsList = subTopics.getSubTopics(position);												//GET TOPICS ARRAYLSIT FROM CLASS
																								
	        adapter = new SubTopicAdapter(mContext, R.layout.sub_topic_list, mSubTopicsList, position, mDrawerLayout);		//INSTANTIATE CUSTOMIZED ADAPTER WHICH INCLUDE LRU MEMORY CACHE		
			mRightDrawerList.setAdapter(adapter);
	}




	
	//RIGHT NAV DRAWER TOPIC LISTENER
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	    		ManagerSubTopics subTopics =  new ManagerSubTopics();												//INSTANTIATE CLASS WITH TOPICS
	    		mChooseTopic.setVisibility(View.VISIBLE);
	    		if(checkNetwork()){
	    			SubTopicAdapter adapter;
		    		mSubTopicsList = subTopics.getSubTopics(position);												//GET TOPICS ARRAYLSIT FROM CLASS
																										
			        adapter = new SubTopicAdapter(MainActivity.this, R.layout.sub_topic_list, mSubTopicsList, position, mDrawerLayout);		//INSTANTIATE CUSTOMIZED ADAPTER WHICH INCLUDE LRU MEMORY CACHE		
					mRightDrawerList.setAdapter(adapter);																									//SET ADAPTER
					mRightDrawerList.setOnItemClickListener(new DrawerSubItemClickListener());
					
					//Remembering who is the last group
					ViewPagerDataRecover pageDataRecover = new ViewPagerDataRecover(MainActivity.this);
					pageDataRecover.setLastGroupPos(position);
																								
	    		}else{ 		//IF NOT SHOW A MERCYFULL MESSAGE
					Toast.makeText(MainActivity.this, "Network is off!", Toast.LENGTH_LONG);
				}	
	    			
			
	    }
	}
	
	//RIGHT NAV DRAWER SUB TOPIC LISTENER
	private class DrawerSubItemClickListener implements ListView.OnItemClickListener{
	
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,long arg3) {
			
			if(checkNetwork()){
				mProgressBar.setVisibility(ProgressBar.VISIBLE);									
	    		HashMap<String, String> subTopicMap =  mSubTopicsList.get(position);
	    		String feedUrl = subTopicMap.get(ManagerSubTopics.KEY_URL_SUBTOPIC);
				Log.i("Nav Right Feed URL", feedUrl);
	    		SearchPostsTask blogSearcher = new SearchPostsTask(MainActivity.this, mProgressBar, mPager, getFragmentManager());
				blogSearcher.execute(feedUrl);
				
				//Remembering who is the last child
				ViewPagerDataRecover pageDataRecover = new ViewPagerDataRecover(MainActivity.this);
				pageDataRecover.setLastChildPos(position);
				
				mDrawerLayout.closeDrawers();
				
							
			}
			else{ 		//IF NOT SHOW A MERCYFULL MESSAGE
				Toast.makeText(MainActivity.this, "Network is off!", Toast.LENGTH_LONG);
			}
			
		}
		
		
	}
	
	//LEFT NAV EXPANDABLE LISTVIEW LISTENER
	private class DrawerLeftClickListener implements OnChildClickListener{

		
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			
			
			FeedSaver feedProvider = new FeedSaver(mContext);
			ArrayList<ListTopicArray> topicList = feedProvider.getSavedFeeds();
			ListTopicArray feedArray = topicList.get(groupPosition);
			Feed feed = feedArray.getItems().get(childPosition);
			
			SearchPostsTask blogSearcher = new SearchPostsTask(MainActivity.this, mProgressBar, mPager, getFragmentManager());
			blogSearcher.execute(feed.getFeedUrl().toString());
			mDrawerLayout.closeDrawers();
			return true;
		}
		
		
	}
	
	
private void refreshMainScreen(int groupPos, int childPos){
	ManagerSubTopics subTopics =  new ManagerSubTopics();
	mSubTopicsList = subTopics.getSubTopics(groupPos);
	HashMap<String, String> subTopicMap =  mSubTopicsList.get(childPos);
	String feedUrl = subTopicMap.get(ManagerSubTopics.KEY_URL_SUBTOPIC);

	SearchPostsTask blogSearcher = new SearchPostsTask(MainActivity.this, mProgressBar, mPager, getFragmentManager());
	blogSearcher.execute(feedUrl);
}
	
	
	
	
	//----------------------------------------------------------------------
	
private boolean checkNetwork(){
		
		ConnectivityManager manager = (ConnectivityManager) 
					getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo informer = manager.getActiveNetworkInfo();
		
		boolean isAvailable = false;
		if(informer !=null && informer.isConnected()){
			isAvailable = true;
		}
		
		return isAvailable;
	}
	

	
}
