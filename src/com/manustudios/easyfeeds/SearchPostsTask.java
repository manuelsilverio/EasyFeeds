package com.manustudios.easyfeeds;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.ObjectInputStream.GetField;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.manustudios.easyfeeds.R;
import com.manustudios.extra.ManagerDate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment.SavedState;
import android.app.FragmentManager.BackStackEntry;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class SearchPostsTask extends AsyncTask<String, Void, JSONObject>{

	private Context mContext;
	protected ProgressBar mProgressBar;
	private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private FragmentManager mFragmentMan;
	
	public SearchPostsTask(Context context, ProgressBar bar, ViewPager pager, FragmentManager fm){
		mContext = context;
		mProgressBar = bar;
		mPager = pager;
		mFragmentMan = fm;
	}
	
	
	@Override
	protected JSONObject doInBackground(String... arg0) {
		
		String feedUrl = arg0[0];
		JSONObject jsonResponse = null;
		int responseCode = -1;
		try {
			URL searchFeedUrl = new URL(MainActivity.GOOGLE_API_LOAD + feedUrl +MainActivity.GOOGLE_API_POSTS_QTY); 
			HttpURLConnection connection = (HttpURLConnection) searchFeedUrl.openConnection();
			connection.connect();
			
			responseCode = connection.getResponseCode();
			if(responseCode == HttpURLConnection.HTTP_OK){
				Log.i("HTTP Connection", "Feed Url connected");
				//GET DATA INTO JSONObject
				
				String line;
				StringBuilder builder = new StringBuilder();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
				 builder.append(line);
				}
				
				jsonResponse = new JSONObject(builder.toString());
				
			}else{
				Log.i(MainActivity.TAG, "Code: "+responseCode);
			}
			
		} 
		catch (MalformedURLException e) {
			Log.e(MainActivity.TAG, "Exception caught:"+e);
		}
		catch (IOException e) {
			Log.e(MainActivity.TAG, "Exception caught:"+e);
		}
		catch (Exception e) {
			Log.e(MainActivity.TAG, "Exception caught:"+e);
		}
		
		return jsonResponse;
	}
	@Override
	protected void onPostExecute(JSONObject result) {
		showJsonAnswer(result);
	}
	
	
private void showJsonAnswer(JSONObject jsonData) {
		
		mProgressBar.setVisibility(ProgressBar.INVISIBLE);
		
		if(jsonData == null){
			showError();
		}else{
			
			try {
				Log.i("Debugger", "chk json answer");
				JSONObject jsonResponseData = jsonData.getJSONObject("responseData");
				jsonResponseData = jsonResponseData.getJSONObject("feed");
				JSONArray jsonFeedResult = jsonResponseData.getJSONArray("entries"); 
				ArrayList<SparseArray<String>> feedResults = 
						new ArrayList<SparseArray<String>>();	
				//GET THE RESULS INSIDE THE ARRAYLIST
				MainActivity.num_pages = jsonFeedResult.length()/MainActivity.mPostPerPage;		//IMPORTANT***
				Log.i("Num Pages", ""+MainActivity.num_pages);
				for (int i = 0; i < jsonFeedResult.length(); i++) {
					JSONObject feedResult = jsonFeedResult.getJSONObject(i);
					
					String title = feedResult.getString("title");
					String content = feedResult.getString("contentSnippet");
					String image = feedResult.getString("content");
					String date = feedResult.getString("publishedDate");
					
					String linkUrl = feedResult.getString("link");
					
					title = Html.fromHtml(title).toString();
					content = Html.fromHtml(content).toString();
					date = Html.fromHtml(date).toString();
					//linkUrl = Html.fromHtml(linkUrl).toString();
					//Log.i("Debug - Title", ""+title);
					//Log.v("Original Image", image);
					image = getImageFromFeed(image);
					//Log.v("New Image, Position", image+", "+i);
					date = getDateFromFeed(date);
					
					//HashMap<String, String> feedPosts = new HashMap<String, String>();
					SparseArray<String> feedPosts = new SparseArray<String>();
					feedPosts.append(MainActivity.KEY_TITLE, title);
					feedPosts.append(MainActivity.KEY_CONTENT, content);
					feedPosts.append(MainActivity.KEY_IMAGE, image);
					feedPosts.append(MainActivity.KEY_DATE, date);
					
					feedPosts.append(MainActivity.KEY_LINK, linkUrl);
					
					feedResults.add(feedPosts);
				}

				mPagerAdapter = new ScreenSlidePagerAdapter(mFragmentMan, feedResults);
				mPager.setAdapter(mPagerAdapter);

				//String feedTitle = jsonResponseData.getString("title");
				//Toast.makeText(mContext, feedTitle, Toast.LENGTH_LONG);
				
				/*mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			            @Override
			            public void onPageSelected(int position) {

			                //invalidateOptionsMenu();
			            }

			        });*/

			
			} catch (JSONException e) {
				Log.e(MainActivity.TAG, "json error caught: ", e);
			}
							
		}
		
		
	}	
	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
	
	ArrayList<SparseArray<String>> auxFeeds;
	
	public ScreenSlidePagerAdapter(FragmentManager fragman, ArrayList<SparseArray<String>> feeds) {
        super(fragman);
        auxFeeds = feeds;
        
    }

    @Override
    public Fragment getItem(int position) {		//This is called several times if THERE ARE 3 PAGES THEN IS CALLED 3 TIMES
    	
    	return ScreenPagerFragment.create(position, auxFeeds, mContext);	//POSITION REPRESENTS THE PAGE NUMBER
    }

    
    
    @Override
    public int getCount() {
        return MainActivity.num_pages;
    }
}

	
	private String getDateFromFeed(String date) {
		ManagerDate dateManager = new ManagerDate();
		return dateManager.convertDate(date);
	}

	private String getImageFromFeed(String image) {
		//TODO CHECK THE IMAGES THAT ARE NOT OBTAINED ON THE LAYOUT AND CHECK ON THE API TO DEFINE IF WHEN
		//THERE ARE MORE THAN ONE IMAGE THE IMAGE IS NOT BEING TRACKED
		//String regex ="(http+)(.*)(.[.](jpg|JPEG|PNG)+)";
		//String regex ="((http|https)+)(.*)(.[.](jpg|JPEG|PNG))";
		//String regex = "(http+|https+)(.*?)(jpg|JPEG|PNG)";
		
		
		String regex ="((img)+)(.*)(.[.](jpg|JPEG|PNG))";
		
		Pattern patChecker = Pattern.compile(regex);
		Matcher regexMatcher = patChecker.matcher(image);
		
		String newImage = "";
		while(regexMatcher.find()){
			if(regexMatcher.group().length() != 0){
				newImage+=image.substring(regexMatcher.start(), regexMatcher.end());
			}
		}
		
		regex="((http|https)+)(.*)(.[.](jpg|JPEG|PNG))";
		patChecker = Pattern.compile(regex);
		regexMatcher = patChecker.matcher(newImage);
		
		String newSecImage="";
		while(regexMatcher.find()){
			if(regexMatcher.group().length() != 0){
				newSecImage+=newImage.substring(regexMatcher.start(), regexMatcher.end());
			}
		}
		
		regex="(jpg|JPEG|PNG)";
		patChecker = Pattern.compile(regex);
		regexMatcher = patChecker.matcher(newSecImage);
		int valSubString=0;
		boolean aux =true;
		while(regexMatcher.find()){
			if(regexMatcher.group().length() != 0){
				if(aux){
					valSubString = regexMatcher.end();
					aux=false;
				}
				
			}
		}
		
		newSecImage = newSecImage.substring(0, valSubString);
		
		Log.i("Image URL", newSecImage);		
		return newSecImage;
	}
	
	private void showError(){
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(R.string.error_title);
		builder.setMessage(R.string.error_message);
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();

		//TextView emptyText = (TextView) findViewById(R.id.empty_textview);
		//emptyText.setText(getString(R.string.no_item_to_display));
	}

	
}
