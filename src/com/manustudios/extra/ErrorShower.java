package com.manustudios.extra;

import com.manustudios.easyfeeds.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.widget.TextView;

public class ErrorShower extends ListActivity {

	private void showError(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.error_title);
		builder.setMessage(R.string.error_message);
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
		
		TextView emptyText = (TextView) getListView().getEmptyView();
		emptyText.setText(getString(R.string.no_item_to_display));
	}
	
	
	
}
