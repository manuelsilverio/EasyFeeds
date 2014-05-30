package com.manustudios.easyfeeds;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.manustudios.easyfeeds.R;

public class MainWebActivity extends Activity{

	protected String mUrl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_view_main);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
       // getActionBar().setHomeButtonEnabled(true);
		
		Intent intent = getIntent();
		Uri blogUri = intent.getData();
		mUrl = blogUri.toString();
		
		WebView webView = (WebView) findViewById(R.id.webViewMain);
		WebViewClient myclient = new WebViewClient();
		webView.setWebViewClient(myclient );
		webView.loadUrl(mUrl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.blog_web_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if(itemId == R.id.action_share){
			sharePost();
		}
		return super.onOptionsItemSelected(item);
	}

	
	private void sharePost() {
		Intent intentShare = new Intent(Intent.ACTION_SEND);
		intentShare.setType("text/plain");
		intentShare.putExtra(Intent.EXTRA_TEXT, mUrl);
		startActivity(Intent.createChooser(intentShare, getString(R.string.chose_favorite)));
	}
	
	
}
