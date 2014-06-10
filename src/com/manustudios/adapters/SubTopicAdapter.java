package com.manustudios.adapters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.manustudios.adapters.TopicAdapter.CropSquareTransformation;
import com.manustudios.easyfeeds.MainActivity;
import com.manustudios.easyfeeds.ManagerSubTopics;
import com.manustudios.easyfeeds.R;
import com.manustudios.extra.FeedSaver;
import com.manustudios.extra.SubTopicDataSource;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class SubTopicAdapter extends ArrayAdapter<HashMap<String, String>>{

	private Context mContext;
	private int layout;
	private LruCache<String, Bitmap> mMemoryCacheBitmap;
	private HashMap<String, String> mMemoryCacheString = new HashMap<String, String>();
	private int mTopicPosition;
	
	private final String TAG = "SubTopicAdapter-General";
	private final String TAG_TITLE = "SubTopicAdapter-Title";
	private final String TAG_DESC = "SubTopicAdapter-Desc";
	private final String GOOGLE_API_POSTS_QTY = "&num=1";
	
	private TextView title;
	private TextView description;
	private ImageView icon;
	private Button addButton;
	
	private DrawerLayout mDrawerLayout;
	
	public SubTopicAdapter(Context context, int resource, ArrayList<HashMap<String, String>> objects, int topicPosition, DrawerLayout drawerLayout) {
		super(context, resource, objects);
		mContext = context;
		layout = resource;
		mTopicPosition = topicPosition;
		mDrawerLayout = drawerLayout;
				
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		 
		 // Use 1/4th of the available memory for this memory cache.
		 final int cacheSize = maxMemory / 6;
		
		 // Log.i(TAG, "cacheSize: "+cacheSize);
		 mMemoryCacheBitmap = new LruCache<String, Bitmap>(cacheSize) {
			 @Override
			 protected int sizeOf(String key, Bitmap bitmap) {
				 // The cache size will be measured in kilobytes rather than
				 // number of items.
				 return bitmap.getByteCount() / 1024;
			 }
		 };
		
		Log.i(TAG, "cacheSize: "+mMemoryCacheBitmap);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		
		if(convertView==null){
			LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = (View) vi.inflate(layout, null);	
		}

		HashMap<String, String> subTopic = getItem(position);

		title = (TextView) convertView.findViewById(R.id.SubTopicTitle);
		description = (TextView) convertView.findViewById(R.id.SubTopicDesc);
		icon = (ImageView) convertView.findViewById(R.id.subTopicImageView);
		addButton = (Button) convertView.findViewById(R.id.sub_topic_add_button);
		
		
		
		
		final String feedUrl = subTopic.get(ManagerSubTopics.KEY_URL_SUBTOPIC);
		final String feedUrlLoad = ManagerSubTopics.GOOGLE_API_LOAD + feedUrl + GOOGLE_API_POSTS_QTY;
		final String imageUrl = subTopic.get(ManagerSubTopics.KEY_IMG_SUBTOPIC);
		
		FeedSaver feedSaver = new FeedSaver(mContext);
		boolean checkAdded = feedSaver.checkAdded(feedUrl, mTopicPosition);
		
		if(checkAdded){
			addButton.setText(mContext.getResources().getString(R.string.text_added_button_text));
			addButton.setTextSize(mContext.getResources().getDimension(R.dimen.text_size_added_button_text));
		}else{
			addButton.setText(mContext.getResources().getString(R.string.text_added_button_sign));
			addButton.setTextSize(mContext.getResources().getDimension(R.dimen.text_size_added_button_sign));
		}
		
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FeedSaver feedSaver = new FeedSaver(mContext);
				boolean checkAdded = feedSaver.checkAdded(feedUrl, mTopicPosition);
				if(checkAdded){
					Log.i("MANU-DEBUG", "Was added");
					feedSaver.deleteFeed(feedUrl, mTopicPosition);

				}else{
					Log.i("MANU-DEBUG", "Was not added");
					feedSaver.saveFeed(feedUrl, imageUrl, mTopicPosition);
					mDrawerLayout.closeDrawers();
					mDrawerLayout.openDrawer(Gravity.START);
					
				}
				
			}
		});
		
		
		
		//SET THE RIGHT IMAGE INTO THE IMAGE VIEW WITH THE RIGHT SIZE AND ROUNDED CORNER
		//SETTING THE KEY FOR MEMORY CACHE
       

		
		final String mKeyBitmap= String.valueOf(icon.getId()) + imageUrl; 
        final String mKeyTitle= String.valueOf(title.getId()) + feedUrlLoad;
        final String mKeyDescription= String.valueOf(description.getId()) + feedUrlLoad;
       
        //AMAZING LIBRARY IT HELPED ME TO SUBSTITUTE A LOT OF CODE.. THE DOWNLOAD IMAGE ASYNK TASK AND THE BITMAP CACHING TOO.
        Picasso.with(mContext).load(imageUrl).resize(80, 80).transform(new CropSquareTransformation()).centerCrop().into(icon);
        
        
        //TODO ADD FADE EFFECT TO TEXT TITLE AND DESCRIPTION
        //TODO CACHE TITLE AND DESCRIPTION INSIDE ADD PREFERENCES DB
        
       // Bitmap bitmap = getBitmapFromMemCache(keyBitmap);	// TRYING TO GET MEMORY CACHE
        String titleString = getTextFromMemCache(mKeyTitle);
        String descriptionString = getTextFromMemCache(mKeyDescription);
        
        //ACCESING DATA BASE FOR GETTING STRING DATA POSIBLY STORED IN THE PHONE.. THIS WAY AVOID TO UNNECESARILY SEARCH THESE STRING THROUGH HTTP 
        SubTopicDataSource dbSubTopic = new SubTopicDataSource(mContext);
		
           	
        //CACHING THE TITLE TEXT
        if(titleString != null){
        	title.setText(titleString);
        	Log.i("Sub Topic Origin", "Cache memory");
        }else{
        	//IF NOT GETTING THE TEXT FROM DATABASE
        	String stringData = dbSubTopic.getTitleData(mKeyTitle);
        	if(stringData !=null){
        		title.setText(stringData);
        		Log.i("Sub Topic Origin", "shared memory");
        	}else{
        		//IF NOT GETTING THE TEXT FROM HTTP
        		Log.i("Sub Topic Origin", "http");
        		DownloadTitleTask titleLoader = new DownloadTitleTask(title);
        		titleLoader.execute(feedUrlLoad);		
        	}
			
        }
        
        //CACHING THE DESCRIPTION TEXT
        if( descriptionString != null){
        	description.setText(descriptionString);
        	Log.i("Sub Topic Origin", "Cache memory - desc");
        }else{
        	//IF NOT GETTING THE TEXT FROM DATABASE
        	String stringData = dbSubTopic.getDescData(mKeyDescription);
        	if(stringData !=null){
        		description.setText(stringData);
        		Log.i("Sub Topic Origin", "shared memory - desc");
        	}else{
        		//IF NOT GETTING THE TEXT FROM HTTP
        		Log.i("Sub Topic Origin", "http - desc");
        		DownloadDescTask descriptionLoader = new DownloadDescTask(description);
        		descriptionLoader.execute(feedUrlLoad);		
        	}
        	
        }
        
        //CACHING THE LOGO BITMAP
       /* if (bitmap != null) {
        	Log.i("is Cached ","Cached");
            
            bitmap = getRoundedCornerBitmap(bitmap);
            icon.setImageBitmap(bitmap);
            
        } else {
        	Log.i("Is not cached ","not cached");
        	DownloadImageTask imageLoader = new DownloadImageTask(icon);
			imageLoader.execute(subTopic.get(ManagerSubTopics.KEY_IMG_SUBTOPIC));
        }
		*/
		
		return convertView;
	}

	
	private class CropSquareTransformation implements Transformation {
		@Override public Bitmap transform(Bitmap source) {
			int size = Math.min(source.getWidth(), source.getHeight());
			int x = (source.getWidth() - size) / 2;
			int y = (source.getHeight() - size) / 2;
			Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
			if (result != source) {
				source.recycle();
			}
			result = getRoundedCornerBitmap(result);
			return result;
		}

		@Override public String key() { return "square()"; }
	}
	
	
	
	private class DownloadTitleTask extends AsyncTask<String, Void, String>{

		TextView textViewTitle;
		String result;
		String mKey;
		public DownloadTitleTask (TextView title){
			textViewTitle = title;
		}
		
		protected String doInBackground(String... urls) {
			//VARIABLES
			
			String urldisplay = urls[0];	
			JSONObject jsonResponse = null;
			int responseCode = -1;
			mKey = String.valueOf(textViewTitle.getId()) + urldisplay;
			//GET THE JSONOBJECT
			try {
				URL searchFeedUrl = new URL(urldisplay); 
				HttpURLConnection connection = (HttpURLConnection) searchFeedUrl.openConnection();
				connection.connect();
				
				responseCode = connection.getResponseCode();
				if(responseCode == HttpURLConnection.HTTP_OK){				
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
					Log.i(TAG_TITLE, "Code: "+responseCode);
				}
				
			} 
			catch (MalformedURLException e) {
				Log.e(TAG_TITLE, "Exception caught:"+e);
			}
			catch (IOException e) {
				Log.e(TAG_TITLE, "Exception caught:"+e);
			}
			catch (Exception e) {
				Log.e(TAG_TITLE, "Exception caught:"+e);
			}
		
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			
			fadeTextToTextView(result, textViewTitle);
			
			addTextToMemoryCache(mKey, result);
			SubTopicDataSource dbSubTopic = new SubTopicDataSource(mContext);
			dbSubTopic.insertTitle(mKey, result);
		}
		
		
		
	}

	private class DownloadDescTask extends AsyncTask<String, Void, String>{

		TextView textViewDescription;
		String result;
		String mKey;
		public DownloadDescTask (TextView description){
			this.textViewDescription = description;
		
		}
		
		protected String doInBackground(String... urls) {
			//VARIABLES
			JSONObject jsonResponse = null;
			String urldisplay = urls[0];
			int responseCode = -1;
			mKey = String.valueOf(textViewDescription.getId()) + urldisplay;
			//FIRST LOOK FOR THE INFO IN THE CACHE MEMORY
			
			
			
			//GET THE JSONOBJECT AND DATA IN IT
			try {
				URL searchFeedUrl = new URL(urldisplay); 
				HttpURLConnection connection = (HttpURLConnection) searchFeedUrl.openConnection();
				connection.connect();
				
				responseCode = connection.getResponseCode();
				if(responseCode == HttpURLConnection.HTTP_OK){
					
					//GET DATA INTO JSONObject
					
					String line;
					StringBuilder builder = new StringBuilder();
					BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					while((line = reader.readLine()) != null) {
					 builder.append(line);
					}
					
					jsonResponse = new JSONObject(builder.toString());
					
					JSONObject jsonData = jsonResponse.getJSONObject("responseData");
					jsonData = jsonData.getJSONObject("feed");
					result = Html.fromHtml(jsonData.getString("description")).toString();
					//String[] descFirst = result.split(".");
					//result = descFirst[0];
					
				}else{
					Log.i(TAG_DESC, "Code: "+responseCode);
				}
				
			} 
			catch (MalformedURLException e) {
				Log.e(TAG_DESC, "Exception caught:"+e);
			}
			catch (IOException e) {
				Log.e(TAG_DESC, "Exception caught:"+e);
			}
			catch (Exception e) {
				Log.e(TAG_DESC, "Exception caught:"+e);
			}
			
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			fadeTextToTextView(result, textViewDescription);
			
			addTextToMemoryCache(mKey, result);
			SubTopicDataSource dbSubTopic = new SubTopicDataSource(mContext);
			dbSubTopic.insertDescription(mKey, result);
		}
		
		
		
	}

	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;
	    String mKey;
	    Bitmap mBitmap = null;
	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        
	        mKey= String.valueOf(bmImage.getId()) + urldisplay;  
	        
	        try {
	        	//GET THE BITMAP AND SET IT TO THE RIGHT SIZE
	        	
	        	InputStream in = new java.net.URL(urldisplay).openStream();      	
	        		        	
	        	mBitmap = BitmapFactory.decodeStream(in);
	        	 
	        } catch (Exception e) {
	        	Log.e("Error", e.getMessage());
	        	e.printStackTrace();
	        }

	        return mBitmap;
	    }

	    protected void onPostExecute(Bitmap result) {
	    	
	    	result = getRoundedCornerBitmap(result);
	    	//Log.i("element saved", "Saved id: "+mKey);
	    	addBitmapToMemoryCache(mKey, mBitmap);
	    	bmImage.setImageBitmap(result);
	    }
	}
	
	
	
	//------------------------------------------------------------------

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
		    bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		//final Rect rect = new Rect(0, 0, 64, 64);
		final RectF rectF = new RectF(rect);
		final float roundPx = bitmap.getWidth()/6;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
	
	
	//-------------------------------------
	
	private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
	    if (getBitmapFromMemCache(key) == null) {
	        mMemoryCacheBitmap.put(key, bitmap);
	        
	    }
	}
	
	private void addTextToMemoryCache(String key, String text) {
	    if (getTextFromMemCache(key) == null) {
	        mMemoryCacheString.put(key, text);
	        
	    }
	}

	private Bitmap getBitmapFromMemCache(String key) {
		
	    return mMemoryCacheBitmap.get(key);
	}
	
	private String getTextFromMemCache(String key) {
		
	    return mMemoryCacheString.get(key);
	}
	
	private void fadeTextToTextView(String text, TextView textview){
		AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
		textview.startAnimation(fadeIn);
		fadeIn.setDuration(500);
		fadeIn.setFillAfter(true);
		textview.setText(text);
	}
	
}

