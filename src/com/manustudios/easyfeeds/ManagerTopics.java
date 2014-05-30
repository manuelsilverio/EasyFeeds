package com.manustudios.easyfeeds;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerTopics {
	public static final String KEY_TITLE_TOPIC = "title";
	public static final String KEY_IMG_TOPIC = "img";
	
	ArrayList<HashMap<String, String>> mSections = new ArrayList<HashMap<String,String>>();
	HashMap<String, String> mTech = new HashMap<String, String>();
	HashMap<String, String> mGaming = new HashMap<String, String>();
	HashMap<String, String> mMovies = new HashMap<String, String>();
	HashMap<String, String> mBusiness = new HashMap<String, String>();
	HashMap<String, String> mFashion = new HashMap<String, String>();
	HashMap<String, String> mScience = new HashMap<String, String>();
	HashMap<String, String> mCooking = new HashMap<String, String>();
	HashMap<String, String> mInspiration = new HashMap<String, String>();
	HashMap<String, String> mDoItYourself = new HashMap<String, String>();
	
	public ArrayList<HashMap<String, String>> getTopics(){
		
		mSections.add(getTech());
		mSections.add(getScience());
		mSections.add(getInspiration());
		mSections.add(getBusiness());
		mSections.add(getMovies());
		mSections.add(getGaming());
		mSections.add(getCooking());
		mSections.add(getDoItYourself());
		mSections.add(getFashion());
		return mSections;
	}
	
	public HashMap<String,String> getTech(){
		mTech.put(KEY_TITLE_TOPIC, "Tech");
		mTech.put(KEY_IMG_TOPIC, "http://celsiustechnology.com/wp-content/uploads/2013/11/circuit-Sqr.jpg");
		
		return mTech;
	}
	public HashMap<String,String> getGaming(){
		mGaming.put(KEY_TITLE_TOPIC, "Gaming");
		mGaming.put(KEY_IMG_TOPIC, "http://www.freshminds.net/wp-content/uploads/2010/09/VideoGamingClub.jpg");
		
		return mGaming;
	}
	public HashMap<String,String> getMovies(){
		mMovies.put(KEY_TITLE_TOPIC, "Movies");
		mMovies.put(KEY_IMG_TOPIC, "http://static5.businessinsider.com/image/51e4478a69bedd1651000006/the-15-worst-movie-knock-offs-ever-made.jpg");
		
		return mMovies;
	}
	public HashMap<String,String> getBusiness(){
		mBusiness.put(KEY_TITLE_TOPIC, "Business");
		mBusiness.put(KEY_IMG_TOPIC, "https://lh3.googleusercontent.com/-L_0SqNO3k2k/AAAAAAAAAAI/AAAAAAAAAAA/RNJhOXhamow/photo.jpg");
		
		return mBusiness;
	}
	public HashMap<String,String> getFashion(){
		mFashion.put(KEY_TITLE_TOPIC, "Fashion");
		mFashion.put(KEY_IMG_TOPIC, "http://jlwilkinson3.files.wordpress.com/2013/10/cropped-fashion-01.jpg");
		
		return mFashion;
	}
	public HashMap<String,String> getScience(){
		mScience.put(KEY_TITLE_TOPIC, "Science");
		mScience.put(KEY_IMG_TOPIC, "http://medimoon.com/wp-content/uploads/2014/04/physics-imageshutterstock-92366917.jpg");
		
		return mScience;
	}
	public HashMap<String,String> getCooking(){
		mCooking.put(KEY_TITLE_TOPIC, "Cooking");
		mCooking.put(KEY_IMG_TOPIC, "http://nutritionalcookingsolutions.net/wp-content/uploads/2013/01/cooking_classes-picture.jpg");
		
		return mCooking;
	}
	public HashMap<String,String> getInspiration(){
		mInspiration.put(KEY_TITLE_TOPIC, "Inspiration");
		mInspiration.put(KEY_IMG_TOPIC, "http://www.regiofm.info/wp-content/uploads/2014/02/inspiratie.jpg");
		
		return mInspiration;
	}
	public HashMap<String,String> getDoItYourself(){
		mDoItYourself.put(KEY_TITLE_TOPIC, "Do it yourself");
		mDoItYourself.put(KEY_IMG_TOPIC, "http://www.agencypost.com/wp-content/uploads/2013/11/tools.jpg");
		
		return mDoItYourself;
	}

}
