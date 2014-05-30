package com.manustudios.extra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.manustudios.databases.BusinessDataSource;
import com.manustudios.databases.CookingDataSource;
import com.manustudios.databases.DoityourselfDataSource;
import com.manustudios.databases.FashionDataSource;
import com.manustudios.databases.GamingDataSource;
import com.manustudios.databases.InspirationDataSource;
import com.manustudios.databases.MoviesDataSource;
import com.manustudios.databases.ScienceDataSource;
import com.manustudios.databases.TechDataSource;
import com.manustudios.easyfeeds.MainActivity;
import com.manustudios.easyfeeds.ManagerTopics;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

public class FeedSaver {

	private Context mContext;
	
	public FeedSaver(Context context){
		mContext = context;
	}
	
	public ArrayList<ListTopicArray> getSavedFeeds(){
		//Define ListTopicArray Objects
		ListTopicArray tech = new ListTopicArray();
		tech.setItems(getTechFeeds(""));
		tech.setName("Tech");
		tech.setTopicPosition(0);
		
		ListTopicArray science = new ListTopicArray();
		science.setItems(getScienceFeeds(""));
		science.setName("Science");
		science.setTopicPosition(1);
		
		ListTopicArray inspiration = new ListTopicArray();
		inspiration.setItems(getInspirationFeeds(""));
		inspiration.setName("Inspiration");
		inspiration.setTopicPosition(2);
		
		
		ListTopicArray business = new ListTopicArray();
		business.setItems(getBusinessFeeds(""));
		business.setName("Business");
		business.setTopicPosition(3);

		ListTopicArray movies = new ListTopicArray();
		movies.setItems(getMoviesFeeds(""));
		movies.setName("Movies");
		movies.setTopicPosition(4);
		
		ListTopicArray gaming = new ListTopicArray();
		gaming.setItems(getGamingFeeds(""));
		gaming.setName("Gaming");
		gaming.setTopicPosition(5);
		
		ListTopicArray cooking = new ListTopicArray();
		cooking.setItems(getCookingFeeds(""));
		cooking.setName("Cooking");
		cooking.setTopicPosition(6);
		
		ListTopicArray doityourself = new ListTopicArray();
		doityourself.setItems(getDoityourselfFeeds(""));
		doityourself.setName("Do it yourself");
		doityourself.setTopicPosition(7);
		
		ListTopicArray fashion = new ListTopicArray();
		fashion.setItems(getFashionFeeds(""));
		fashion.setName("Fashion");
		fashion.setTopicPosition(8);
		
		//---------------------
		ArrayList<ListTopicArray> savedFeeds = new ArrayList<ListTopicArray>();
		if(getTechFeeds("")!=null){savedFeeds.add(tech);}
		if(getScienceFeeds("")!=null){savedFeeds.add(science);}
		if(getInspirationFeeds("")!=null){savedFeeds.add(inspiration);}
		if(getBusinessFeeds("")!=null){savedFeeds.add(business);}
		if(getMoviesFeeds("")!=null){savedFeeds.add(movies);}
		if(getGamingFeeds("")!=null){savedFeeds.add(gaming);}
		if(getCookingFeeds("")!=null){savedFeeds.add(cooking);}
		if(getDoityourselfFeeds("")!=null){savedFeeds.add(doityourself);}
		if(getFashionFeeds("")!=null){savedFeeds.add(fashion);}
		
		return savedFeeds;
	}
	
	
	//--------------------- GET X FEEDS ----------------------------
	
	public ArrayList<Feed> getTechFeeds(String feedUrl){
		TechDataSource feedGetter = new TechDataSource(mContext, feedUrl);
		ArrayList<Feed> feedList = feedGetter.getFeeds();
		return feedList;
	}
	
	public ArrayList<Feed> getScienceFeeds(String feedUrl){
		ScienceDataSource feedGetter = new ScienceDataSource(mContext, feedUrl);
		ArrayList<Feed> feedList = feedGetter.getFeeds();
		return feedList;
	}
	
	public ArrayList<Feed> getInspirationFeeds(String feedUrl){
		InspirationDataSource feedGetter = new InspirationDataSource(mContext, feedUrl);
		ArrayList<Feed> feedList = feedGetter.getFeeds();
		return feedList;
	}
	

	public ArrayList<Feed> getBusinessFeeds(String feedUrl){
		BusinessDataSource feedGetter = new BusinessDataSource(mContext, feedUrl);
		ArrayList<Feed> feedList = feedGetter.getFeeds();
		return feedList;
	}
	public ArrayList<Feed> getMoviesFeeds(String feedUrl){
		MoviesDataSource feedGetter = new MoviesDataSource(mContext, feedUrl);
		ArrayList<Feed> feedList = feedGetter.getFeeds();
		return feedList;
	}
	public ArrayList<Feed> getGamingFeeds(String feedUrl){
		GamingDataSource feedGetter = new GamingDataSource(mContext, feedUrl);
		ArrayList<Feed> feedList = feedGetter.getFeeds();
		return feedList;
	}
	public ArrayList<Feed> getCookingFeeds(String feedUrl){
		CookingDataSource feedGetter = new CookingDataSource(mContext, feedUrl);
		ArrayList<Feed> feedList = feedGetter.getFeeds();
		return feedList;
	}
	public ArrayList<Feed> getDoityourselfFeeds(String feedUrl){
		DoityourselfDataSource feedGetter = new DoityourselfDataSource(mContext, feedUrl);
		ArrayList<Feed> feedList = feedGetter.getFeeds();
		return feedList;
	}
	public ArrayList<Feed> getFashionFeeds(String feedUrl){
		FashionDataSource feedGetter = new FashionDataSource(mContext, feedUrl);
		ArrayList<Feed> feedList = feedGetter.getFeeds();
		return feedList;
	}
	
	
	//------------------------ LOAD X FEEDS ------------------------
	
	public ArrayList<Map<String, ?>> loadTechFeeds(String feedUrl){
		TechDataSource feedGetter = new TechDataSource(mContext, feedUrl);
		ArrayList<Map<String, ?>> feedList = new ArrayList<Map<String,?>>();
		feedList.add(feedGetter.getTitles());
		feedList.add(feedGetter.getFeedUrl());
		feedList.add(feedGetter.getImgUrl());

		return feedList;
	}
	
	public ArrayList<Map<String, ?>> loadScienceFeeds(String feedUrl){
		ScienceDataSource feedGetter = new ScienceDataSource(mContext, feedUrl);
		ArrayList<Map<String, ?>> feedList = new ArrayList<Map<String,?>>();
		feedList.add(feedGetter.getTitles());
		feedList.add(feedGetter.getFeedUrl());
		feedList.add(feedGetter.getImgUrl());

		return feedList;
	}
	
	public ArrayList<Map<String, ?>> loadInspirationFeeds(String feedUrl){
		InspirationDataSource feedGetter = new InspirationDataSource(mContext, feedUrl);
		ArrayList<Map<String, ?>> feedList = new ArrayList<Map<String,?>>();
		feedList.add(feedGetter.getTitles());
		feedList.add(feedGetter.getFeedUrl());
		feedList.add(feedGetter.getImgUrl());

		return feedList;
	}
	
	
	public ArrayList<Map<String, ?>> loadBusinessFeeds(String feedUrl){
		BusinessDataSource feedGetter = new BusinessDataSource(mContext, feedUrl);
		ArrayList<Map<String, ?>> feedList = new ArrayList<Map<String,?>>();
		feedList.add(feedGetter.getTitles());
		feedList.add(feedGetter.getFeedUrl());
		feedList.add(feedGetter.getImgUrl());

		return feedList;
	}
	
	public ArrayList<Map<String, ?>> loadMoviesFeeds(String feedUrl){
		MoviesDataSource feedGetter = new MoviesDataSource(mContext, feedUrl);
		ArrayList<Map<String, ?>> feedList = new ArrayList<Map<String,?>>();
		feedList.add(feedGetter.getTitles());
		feedList.add(feedGetter.getFeedUrl());
		feedList.add(feedGetter.getImgUrl());

		return feedList;
	}
	public ArrayList<Map<String, ?>> loadGamingFeeds(String feedUrl){
		GamingDataSource feedGetter = new GamingDataSource(mContext, feedUrl);
		ArrayList<Map<String, ?>> feedList = new ArrayList<Map<String,?>>();
		feedList.add(feedGetter.getTitles());
		feedList.add(feedGetter.getFeedUrl());
		feedList.add(feedGetter.getImgUrl());

		return feedList;
	}
	public ArrayList<Map<String, ?>> loadCookingFeeds(String feedUrl){
		CookingDataSource feedGetter = new CookingDataSource(mContext, feedUrl);
		ArrayList<Map<String, ?>> feedList = new ArrayList<Map<String,?>>();
		feedList.add(feedGetter.getTitles());
		feedList.add(feedGetter.getFeedUrl());
		feedList.add(feedGetter.getImgUrl());

		return feedList;
	}
	public ArrayList<Map<String, ?>> loadDoityourselfFeeds(String feedUrl){
		DoityourselfDataSource feedGetter = new DoityourselfDataSource(mContext, feedUrl);
		ArrayList<Map<String, ?>> feedList = new ArrayList<Map<String,?>>();
		feedList.add(feedGetter.getTitles());
		feedList.add(feedGetter.getFeedUrl());
		feedList.add(feedGetter.getImgUrl());

		return feedList;
	}
	public ArrayList<Map<String, ?>> loadFashionFeeds(String feedUrl){
		FashionDataSource feedGetter = new FashionDataSource(mContext, feedUrl);
		ArrayList<Map<String, ?>> feedList = new ArrayList<Map<String,?>>();
		feedList.add(feedGetter.getTitles());
		feedList.add(feedGetter.getFeedUrl());
		feedList.add(feedGetter.getImgUrl());

		return feedList;
	}

	
	//------------------------------ CHECKADDED ------------------------------
	public boolean checkAdded(String feedUrl, int topicPosition){
		boolean answer = false;
		switch (topicPosition) {
		case 0:
			TechDataSource techData = new TechDataSource(mContext, feedUrl);
			answer = techData.checkStored(feedUrl);
		break;
		case 1:
			ScienceDataSource scienceData = new ScienceDataSource(mContext, feedUrl);
			answer = scienceData.checkStored(feedUrl);
		break;
		case 2:
			InspirationDataSource inspirationData = new InspirationDataSource(mContext, feedUrl);
			answer = inspirationData.checkStored(feedUrl);
		break;
		case 3:
			BusinessDataSource businessData = new BusinessDataSource(mContext, feedUrl);
			answer = businessData.checkStored(feedUrl);
		break;
		case 4:
			MoviesDataSource moviesData = new MoviesDataSource(mContext, feedUrl);
			answer = moviesData.checkStored(feedUrl);
		break;
		case 5:
			GamingDataSource gamingData = new GamingDataSource(mContext, feedUrl);
			answer = gamingData.checkStored(feedUrl);
		break;
		case 6:
			CookingDataSource cookingData = new CookingDataSource(mContext, feedUrl);
			answer = cookingData.checkStored(feedUrl);
		break;
		case 7:
			DoityourselfDataSource doityourselfData = new DoityourselfDataSource(mContext, feedUrl);
			answer = doityourselfData.checkStored(feedUrl);
		break;
		case 8:
			FashionDataSource fashionData = new FashionDataSource(mContext, feedUrl);
			answer = fashionData.checkStored(feedUrl);
		break;


		default:
			break;
		}
		
		return answer;
	}
	
	
	
	
	public void deleteFeed(String feedUrl, int topicPosition){
		switch (topicPosition) {
		case 0:
			TechDataSource techData = new TechDataSource(mContext, feedUrl);
			techData.deleteData(feedUrl);
			break;
		case 1:
			ScienceDataSource scienceData = new ScienceDataSource(mContext, feedUrl);
			scienceData.deleteData(feedUrl);
			break;
		case 2:
			InspirationDataSource inspirationData = new InspirationDataSource(mContext, feedUrl);
			inspirationData.deleteData(feedUrl);
			break;
		case 3:
			BusinessDataSource businessData = new BusinessDataSource(mContext, feedUrl);
			businessData.deleteData(feedUrl);
		break;
		case 4:
			MoviesDataSource moviesData = new MoviesDataSource(mContext, feedUrl);
			moviesData.deleteData(feedUrl);
		break;
		case 5:
			GamingDataSource gamingData = new GamingDataSource(mContext, feedUrl);
			gamingData.deleteData(feedUrl);
		break;
		case 6:
			CookingDataSource cookingData = new CookingDataSource(mContext, feedUrl);
			cookingData.deleteData(feedUrl);
		break;
		case 7:
			DoityourselfDataSource doityourselfData = new DoityourselfDataSource(mContext, feedUrl);
			doityourselfData.deleteData(feedUrl);
		break;
		case 8:
			FashionDataSource fashionData = new FashionDataSource(mContext, feedUrl);
			fashionData.deleteData(feedUrl);
		break;
		default:
			break;
		}
		
		
		MainActivity.refreshRightSubNav(topicPosition);
	}
	
	
	
	
	
	private void saveData(String title, String feedUrl, String imgUrl, int topicPosition){
		switch (topicPosition) {
		case 0:
			TechDataSource techData = new TechDataSource(mContext, feedUrl);
			techData.saveTitle(title);
			techData.saveFeedUrl(feedUrl);
			techData.saveImgUrl(imgUrl);
			break;
		case 1:
			ScienceDataSource scienceData = new ScienceDataSource(mContext, feedUrl);
			scienceData.saveTitle(title);
			scienceData.saveFeedUrl(feedUrl);
			scienceData.saveImgUrl(imgUrl);
			break;
		case 2:
			InspirationDataSource inspirationData = new InspirationDataSource(mContext, feedUrl);
			inspirationData.saveTitle(title);
			inspirationData.saveFeedUrl(feedUrl);
			inspirationData.saveImgUrl(imgUrl);
			break;
		case 3:
			BusinessDataSource businessData = new BusinessDataSource(mContext, feedUrl);
			businessData.saveTitle(title);
			businessData.saveFeedUrl(feedUrl);
			businessData.saveImgUrl(imgUrl);
		break;
		case 4:
			MoviesDataSource moviesData = new MoviesDataSource(mContext, feedUrl);
			moviesData.saveTitle(title);
			moviesData.saveFeedUrl(feedUrl);
			moviesData.saveImgUrl(imgUrl);
		break;
		case 5:
			GamingDataSource gamingData = new GamingDataSource(mContext, feedUrl);
			gamingData.saveTitle(title);
			gamingData.saveFeedUrl(feedUrl);
			gamingData.saveImgUrl(imgUrl);
		break;
		case 6:
			CookingDataSource cookingData = new CookingDataSource(mContext, feedUrl);
			cookingData.saveTitle(title);
			cookingData.saveFeedUrl(feedUrl);
			cookingData.saveImgUrl(imgUrl);
		break;
		case 7:
			DoityourselfDataSource doityourselfData = new DoityourselfDataSource(mContext, feedUrl);
			doityourselfData.saveTitle(title);
			doityourselfData.saveFeedUrl(feedUrl);
			doityourselfData.saveImgUrl(imgUrl);
		break;
		case 8:
			FashionDataSource fashionData = new FashionDataSource(mContext, feedUrl);
			fashionData.saveTitle(title);
			fashionData.saveFeedUrl(feedUrl);
			fashionData.saveImgUrl(imgUrl);
		break;

		default:
			break;
		}
	}
	

	// -------------------------- NO MORE COPY PASTING
	
	//GENERAL METHOD FOR SAVING ANY FEED ---- FIRST LOOK FOR THE TITLE IF FOUND INVOKE SAVEDATA METHOD IFNOT EXECUTE ASYNK TASK
	public void saveFeed(String feedUrl, String imgUrl, int topicPosition){
		//TODO RESTORE SUBTOPICDATA SOURCE AFTER FINISHING DEBUGGING
		SubTopicDataSource titleGetter = new SubTopicDataSource(mContext);
		String title = titleGetter.getTitleData(feedUrl);
		//String title = null;
		if(title!=null){
			saveData(title, feedUrl, imgUrl, topicPosition);
		}else{
			SaveDataAsynk dataSaver = new SaveDataAsynk(feedUrl, imgUrl, topicPosition);
			dataSaver.execute(feedUrl);
		}
		
		
	}
	
	private class SaveDataAsynk extends AsyncTask<String, Void, String>{

		//String mKey;
		String mFeedUrl;
		String mImgUrl;
		
		String result;
		int mTopicPosition;
		
		public SaveDataAsynk (String feedUrl, String imgUrl, int topicPosition){
			//mKey = titleKey;
			mFeedUrl = feedUrl;
			mImgUrl = imgUrl;
			mTopicPosition = topicPosition;
		}
		
		
		@Override
		protected String doInBackground(String... params) {

			JSONObject jsonResponse = null;
			int responseCode = -1;
			try {
				URL searchFeedUrl = new URL(MainActivity.GOOGLE_API_LOAD + mFeedUrl); 
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
					JSONObject jsonData = jsonResponse.getJSONObject("responseData");
					JSONObject jsonFeed = jsonData.getJSONObject("feed");
					result = Html.fromHtml(jsonFeed.getString("title")).toString();
					
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
			
			return result;
		}


		@Override
		protected void onPostExecute(String result) {
			saveData(result, mFeedUrl, mImgUrl, mTopicPosition);
			MainActivity.refreshLeftNav();
			MainActivity.refreshRightSubNav(mTopicPosition);
			
			//FOLLOWING LINES RETREIVE WHICH GROUP HAS TO OPEN IN THE LEFT NAV DRAWER
			ArrayList<ListTopicArray> saveFeedsList = getSavedFeeds();
			ManagerTopics topicsMan = new ManagerTopics();
			ArrayList<HashMap<String, String>> topicsList = topicsMan.getTopics();
			String nameSavedFeed = topicsList.get(mTopicPosition).get(ManagerTopics.KEY_TITLE_TOPIC);
			
			int newPosition=0;
			for (int i = 0; i < saveFeedsList.size(); i++) {
				if(saveFeedsList.get(i).getName().equals(nameSavedFeed)){
					newPosition = i;
				}
			}
			
			
			MainActivity.expandLeftNavGroup(newPosition);
		}
		
		
		
		
	}
	
	
}
