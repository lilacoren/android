package com.example.project2;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class ActivitiesMenu extends Activity {

	private RelativeLayout mainMenuLayout;
	private int screenWidth;
	private int screenHeight;
	public final static String SCREEN_DIMENSIONS = "screenDimensions";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("MENU");
		setContentView(R.layout.activity_activities_menu);
		// Show the Up button in the action bar.
		setupActionBar();
		getContentIds();
		setContentDimensions();
	}

	
	private void setContentDimensions() {
		SharedPreferences settings = getApplicationContext().getSharedPreferences(SCREEN_DIMENSIONS, 0);
		screenWidth = settings.getInt("screenWidth", 0);	
		screenHeight = settings.getInt("screenHeight", 0);
		mainMenuLayout.getLayoutParams().width = (int)(screenWidth * 0.9 );
		mainMenuLayout.getLayoutParams().height = (int)(screenHeight * 0.9 );
	}


	private void getContentIds() {
		mainMenuLayout = (RelativeLayout)findViewById(R.id.mainMenuLayout);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activities_menu, menu);
		return true;
	}
	
	public void goSwipe(View view)
	{
		Intent intent = new Intent(this, SwipeScreen.class);
		startActivity(intent);
	}
	
	public void goHistory(View view) 
	{
		Intent intent = new Intent(this, History.class);
		startActivity(intent);
	}

}
