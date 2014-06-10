package com.manustudios.adapters;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


import com.manustudios.easyfeeds.MainActivity;
import com.manustudios.easyfeeds.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainScreenAdapter extends ArrayAdapter<SparseArray<String>>{

	private LruCache<String, Bitmap> mMemoryCacheBitmap;
	private Context mContext;
	private int layout;
	public MainScreenAdapter(Context context, int resource, ArrayList<SparseArray<String>> objects, ViewGroup container) {
		super(context, resource, objects);
		mContext = context;
		layout = resource;
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		 // Use 1/4th of the available memory for this memory cache.
		 final int cacheSize = maxMemory / 4;
		 mMemoryCacheBitmap = new LruCache<String, Bitmap>(cacheSize) {
			 @Override
			 protected int sizeOf(String key, Bitmap bitmap) {
				 // The cache size will be measured in kilobytes rather than
				 // number of items.
				 return bitmap.getByteCount() / 1024;
			 }
		 };
		// Log.i("mainScreenAdapter", "chk 02");
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//Log.i("mainScreenAdapter", "chk 03");
		if(convertView == null){
			LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			try {
				convertView = (View) vi.inflate(layout, null);
			} catch (InflateException e) {
				Log.e("Inflate Expection" , "the list doesnt want to inflate");
			}
			
		}
		
		SparseArray<String> mainScreenContent = getItem(position);
		
		//LAYOUT RESOURCES
		TextView title = (TextView) convertView.findViewById(R.id.title_mainScreen);
		TextView content = (TextView) convertView.findViewById(R.id.content_mainScreen);
		TextView date = (TextView) convertView.findViewById(R.id.date_mainScreen);
		ImageView icon = (ImageView) convertView.findViewById(R.id.image_mainScreen);
		
		//SETTING TEXTS
		title.setText(mainScreenContent.get(MainActivity.KEY_TITLE));
		content.setText(mainScreenContent.get(MainActivity.KEY_CONTENT));
		date.setText(mainScreenContent.get(MainActivity.KEY_DATE));
		
		//PIECE OF CODE FOR DISTRIBUTING 4 LINES AT MAIN_SCREEN_LIT LAYOUT GIVING PRIORITY TO TITLE
			
		title.setEllipsize(TextUtils.TruncateAt.END);
		content.setEllipsize(TextUtils.TruncateAt.END);
		
		
		//content.setMaxLines(2);
		
		//KEYS AND BITMAP
		//TODO CHECK IMAGE RATIO AND IF EXCEEDS 2:1 OR 3:2 THEN DONT LOAD THE IMAGE
		String imageUrl = mainScreenContent.get(MainActivity.KEY_IMAGE);
		if(!imageUrl.equals("")){
			//Picasso.with(mContext).load(imageUrl).resize(200, 200).transform(new CropSquareTransformation()).centerCrop().into(icon);
			Picasso.with(mContext).load(imageUrl).transform(new CropSquareTransformation()).resize(160, 160).centerCrop().into(icon);
		}
		
				
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
	
	
		
	
	//------------------------------------------------------------------

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
		    bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = bitmap.getWidth()/10;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
	

	
}
