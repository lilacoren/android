package com.example.project2;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	//public final static String USER_NAME ="com.example.project2.MESSAGE";
	//public final static String PASS_WORD ="com.example.project2.MESSAGE";
	private int screenHeight ;
	private int screenWidth;
	private ImageView digipassLogo;
	private EditText userNameEditText;  
	private EditText passWordEditText;
	public final static String SCREEN_DIMENSIONS = "screenDimensions";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("LOGIN");
		setContentView(R.layout.activity_main);
		// method to get the device screen dimensions and save them for entire application:
		saveScreenDimensionsForApp(); 
		getLayoutObjectIds(); // set this layouts objects Ids
		setLayoutObjects();
		
	}

	private void getLayoutObjectIds() {
		digipassLogo = (ImageView)findViewById(R.id.imageView1);
		userNameEditText = (EditText)findViewById(R.id.userName);
		passWordEditText = (EditText)findViewById(R.id.passWordPass);
	}

	private void setLayoutObjects() {
		digipassLogo.getLayoutParams().width = (int) (screenWidth * 0.9);

	}

	private void saveScreenDimensionsForApp() {
		SharedPreferences settings = getApplicationContext().getSharedPreferences(SCREEN_DIMENSIONS, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		Display display = getWindowManager().getDefaultDisplay(); 
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();
		editor.putInt("screenHeight", screenHeight);
		editor.putInt("screenWidth", screenWidth);
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void displayMenu(View view)
	{
		Intent intent = new Intent(this, ActivitiesMenu.class);
		
		String userNameDisplayString;
		String passWordDisplayString;
		
		userNameDisplayString = userNameEditText.getText().toString();
		
		passWordDisplayString = passWordEditText.getText().toString();
		
		intent.putExtra("userName", userNameDisplayString);	
		intent.putExtra("passWord", passWordDisplayString);
		
		//if(userNameDisplayString != null && passWordDisplayString != null)
    	//  The intent to start. 
		if(passWordDisplayString.equals(""))
			//startActivityForResult(intent, 1);
			startActivity(intent);

	}
	
	public void signInForm(View view)
	{
		Intent intent = new Intent(this, SignIn.class);
		startActivity(intent);
	}
}
