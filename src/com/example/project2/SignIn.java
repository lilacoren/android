package com.example.project2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.project2.R.string;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SignIn extends Activity {

	private EditText firstName;
	private EditText lastName;
	private EditText email;
	private EditText passWord;
	private RelativeLayout formLayout;
	private ImageView digipassLogo;
	private int screenWidth;
	private int screenHeight;
	public final static String SCREEN_DIMENSIONS = "screenDimensions";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("SIGN IN");
		setupActionBar();
		setContentView(R.layout.activity_sign_in);
		getContentIds();
		setFieldsHint();
		setContentDimensions();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	private void setContentDimensions() {
		SharedPreferences settings = getApplicationContext().getSharedPreferences(SCREEN_DIMENSIONS, 0);
		screenWidth = settings.getInt("screenWidth", 0);	
		screenHeight = settings.getInt("screenHeight", 0);
		formLayout.getLayoutParams().height = (int)(screenHeight * 0.84);
		formLayout.getLayoutParams().width = (int)(screenWidth * 0.9);
		digipassLogo.getLayoutParams().width = (int)(screenWidth * 0.9);
	}

	private void getContentIds() {
		firstName = (EditText)findViewById(R.id.firstNameField);
		lastName = (EditText)findViewById(R.id.lastNameField);
		email = (EditText)findViewById(R.id.emailTextField);
		passWord = (EditText)findViewById(R.id.signInPasswordField);
		formLayout = (RelativeLayout)findViewById(R.id.signInLayout);
		digipassLogo = (ImageView)findViewById(R.id.digipassLogo);
	}

	private void setFieldsHint(){
		firstName.setHint(" First name");
		lastName.setHint(" Last name");
		email.setHint(" Email");
		passWord.setHint(" Password");
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
		getMenuInflater().inflate(R.menu.sign_in, menu);
		return true;
	}

	public void register(View view)
	{
		validateForm(view);
	}

	public void validateForm(View view)
	{
		TextView feedback = (TextView)findViewById(R.id.formFeedbackTextView);
		EditText firstNameBox = (EditText)findViewById(R.id.firstNameField);
		EditText lastNameBox = (EditText)findViewById(R.id.lastNameField);
		EditText emailBox = (EditText)findViewById(R.id.emailTextField);
		EditText passwordBox = (EditText)findViewById(R.id.signInPasswordField);

		String firstName = firstNameBox.getText().toString();
		String lastName = lastNameBox.getText().toString();
		String email = emailBox.getText().toString();
		String password = passwordBox.getText().toString();

		feedback.setText("");
		feedback.setTextSize(14);
		feedback.setTextColor(Color.RED);

		if(firstName.equals("") || lastName.equals("") || email.equals("") || password.equals(""))
			feedback.setText(string.nameFieldFeedback);

		else if(!validate(email))
			feedback.setText(string.emailFieldFeedback);
		
	}

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		return matcher.find();
	}

}

