package com.manustudios.easyfeeds;

import java.util.ArrayList;
import java.util.HashMap;

import com.manustudios.adapters.MainScreenAdapter;
import com.manustudios.easyfeeds.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class ScreenPagerFragment extends Fragment{

		
		public static final String ARG_PAGE = "page";
		public static final String KEY_ARRAY_FEEDS = "KeyForArrayList";   
		
		private int mPageNumber;
		private static int mPostPerPage = MainActivity.mPostPerPage;
		private Context mContext;
		private ArrayList<SparseArray<String>> feedList;
		
		private static int startIndex;
		private static int endIndex;
		
	    public static Fragment create(int pageNumber, ArrayList<SparseArray<String>> feeds, Context context) {
	    	ScreenPagerFragment fragment = new ScreenPagerFragment(context);
	    	
	    	Bundle args = new Bundle();
	        args.putInt(ARG_PAGE, pageNumber);
	        
	       
	        
	        startIndex = (pageNumber+1)*mPostPerPage - mPostPerPage;					//THE START INDEX.. 1 OR 6 OR 11
	        endIndex = (pageNumber+1)*mPostPerPage	;					//THE END INDEX 5 OR 10 OR 15
	        //Log.i("pageNumber", ""+pageNumber); 
	        //Log.i("Start Index", ""+startIndex); 
	        //Log.i("End Index", ""+endIndex); 
	        //THIS ARRAY WILL INSERT ARRAYS
	       	for (int i = startIndex; i < endIndex; i++) {
	        	SparseArray<String> feedContent = feeds.get(i);
	        	if(feedContent != null){		//IF THE SPARSE ARRAY IS NOT NULL THEN SAVE IT INSIDE AN ARRAY AND PUT IT IN ARGS BUNDLE
	        		String[] feedArray = new String[5]; 
		        	feedArray[0] = feedContent.get(MainActivity.KEY_TITLE);
		        	feedArray[1] = feedContent.get(MainActivity.KEY_CONTENT);
		        	feedArray[2] = feedContent.get(MainActivity.KEY_DATE);
		        	feedArray[3] = feedContent.get(MainActivity.KEY_IMAGE);
		        	feedArray[4] = feedContent.get(MainActivity.KEY_LINK);
		        	args.putStringArray(KEY_ARRAY_FEEDS + String.valueOf(i), feedArray);
	        	}
	        	

			}
	       	
	       	//STORE THE BUNDLE INSIDE THE FRAGMENT
	        fragment.setArguments(args);
	        
	        return fragment;
	    }

	    //CLASS CONSTRUCT
	    public ScreenPagerFragment(Context context) {
	    	mContext = context;
	    }

	
	
	
	 	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	       
	        mPageNumber = getArguments().getInt(ARG_PAGE);									//PAGE NUMBER
	        int startIndex = (mPageNumber+1)*mPostPerPage - mPostPerPage;					//THE START INDEX.. 1 OR 6 OR 11
	        int endIndex = (mPageNumber+1)*mPostPerPage;
	        feedList = new ArrayList<SparseArray<String>>();
	        Log.i("pageNumber", ""+mPageNumber); 
	        Log.i("Start Index", ""+startIndex); 
	        Log.i("End Index", ""+endIndex); 
	        //NOW WE NEED TO GET BACK THE ARRAYLIST OF SPARSE ARRAYS
	        for (int i = startIndex; i < endIndex; i++) {
	        	String[] feedArray = getArguments().getStringArray(KEY_ARRAY_FEEDS + String.valueOf(i));
	        	
	        	if(feedArray != null){	//IF THERE IS DATA STORE THE ARRAY STRING INSIDE A SPARSE ARRAY LATER INSIDE AN ARRAYLIST
	        		SparseArray<String> feedContent = new SparseArray<String>();
	        		
		        	feedContent.append(MainActivity.KEY_TITLE, feedArray[0]);
		        	feedContent.append(MainActivity.KEY_CONTENT, feedArray[1]);
		        	feedContent.append(MainActivity.KEY_DATE, feedArray[2]);
		        	feedContent.append(MainActivity.KEY_IMAGE, feedArray[3]);
		        		 
		        	feedContent.append(MainActivity.KEY_LINK, feedArray[4]);
		        	//Log.i("LINK_Url", feedArray[4]);
					feedList.add(feedContent);
	        	}
	        	
	        	
			}
       
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        // Inflate the layout containing a title and body text.
	        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.view_pager_fragment_main_list, container, false);

	        //NOW CALL AN INSTANCE OF THE MAIN SCREEN ADAPTER - Context, resource to load, data on arraylist, viewgroup parent of resource
	        MainScreenAdapter adapter = new MainScreenAdapter(mContext, R.layout.main_screen_lit, feedList, rootView);
	        ListView MainDrawerList = (ListView) rootView.findViewById(R.id.main_list);
	        MainDrawerList.setAdapter(adapter);
	       
	        //SHOW THE PAGE NUMBER UNDER EVERY PAGE
	        TextView page = (TextView) rootView.findViewById(R.id.mainScreenPageNumberTextView);
	        int pageNumberShow = mPageNumber+1;
	        page.setText(getResources().getString(R.string.page)+" "+pageNumberShow+"/"+MainActivity.num_pages);
	        
	        MainDrawerList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					SparseArray<String> mainScreenContent = feedList.get(position);
					//TODO ADD KEY URL
					String url = mainScreenContent.get(MainActivity.KEY_LINK);
					Log.i("URL CHECK", url);
					Intent intent = new Intent(mContext, MainWebActivity.class);
					intent.setData(Uri.parse(url));
					startActivity(intent);
				}
			
	        
	        });
	        Log.i("Debug - screenSlidePager", "chk");
	        return rootView;
	    }
	
	    
	    
	    @Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
		}

		@Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			super.onAttach(activity);
		}

		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
		}

		public int getPageNumber() {
	        return mPageNumber;
	    }
}
