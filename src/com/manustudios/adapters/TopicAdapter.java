package com.manustudios.adapters;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.manustudios.easyfeeds.ManagerTopics;
import com.manustudios.easyfeeds.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class TopicAdapter extends ArrayAdapter<HashMap<String, String>>{

	private Context mContext;
	private int layout;
	public TopicAdapter(Context context, int resource, ArrayList<HashMap<String, String>> objects) {
		super(context, resource, objects);
		mContext = context;
		layout = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		
		if(convertView==null){
			LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = (View) vi.inflate(layout, null);
			
		}

		HashMap<String, String> topic = getItem(position);

		TextView title = (TextView) convertView.findViewById(R.id.topicTitle);
		ImageView icon = (ImageView) convertView.findViewById(R.id.imageView1);
		String imageUrl = topic.get(ManagerTopics.KEY_IMG_TOPIC);
		
		title.setText(topic.get(ManagerTopics.KEY_TITLE_TOPIC));
		Picasso.with(mContext).load(imageUrl).resize(80, 80).transform(new CropSquareTransformation()).centerCrop().into(icon);
		
		//DownloadImageTask imageLoader = new DownloadImageTask(icon);		//SET THE RIGHT IMAGE INTO THE IMAGE VIEW WITH THE RIGHT SIZE AND ROUNDED CORNER
		//imageLoader.execute(imageUrl);
		
		return convertView;
	}

	
	public class CropSquareTransformation implements Transformation {
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
	
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        
	    	result = getRoundedCornerBitmap(result);
	    	bmImage.setImageBitmap(result);
	    }
	}
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
		    bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = bitmap.getWidth();

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
	
	
	
}