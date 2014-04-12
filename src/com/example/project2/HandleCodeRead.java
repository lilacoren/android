package com.example.project2;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HandleCodeRead extends Activity {

	private RelativeLayout screenLayout;
	private int screenWidth;
	private int screenHeight;
	public final static String SCREEN_DIMENSIONS = "screenDimensions";
	ImageView companyIcon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handle_code_read);
		getContentIds();
		setContentDimensions();
		// get the Nfc Value passed
		String value = "";
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    value = extras.getString("code");
		}
		
		setupActionBar();
		displayRideDetails(value);
	}
	

	private void setContentDimensions() {
		SharedPreferences settings = getApplicationContext().getSharedPreferences(SCREEN_DIMENSIONS, 0);
		screenWidth = settings.getInt("screenWidth", 0);	
		screenHeight = settings.getInt("screenHeight", 0);
		screenLayout.getLayoutParams().width = (int)(screenWidth * 0.95 );
		screenLayout.getLayoutParams().height = (int)(screenHeight * 0.9 );
	}


	private void getContentIds() {
		companyIcon = (ImageView)findViewById(R.id.CompanyIcon);
		screenLayout = (RelativeLayout)findViewById(R.id.handleCodeReadLayout);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. 

			finish(); // go back to the activity you came from.
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void displayRideDetails(String code)
	{
		String[] splited = code.split(";");
		switch (Integer.parseInt(splited[0])) {
		case 01:
			companyIcon.setImageResource(R.drawable.icon_egged);
			break;
		case 02:
			companyIcon.setImageResource(R.drawable.icon_dan);
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.handle_code_read, menu);
		return true;
	}

}
