package com.example.project2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class History extends Activity {

	TextView testView;
	TextView rideDescription1;
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		testView = (TextView)findViewById(R.id.testView);
		rideDescription1 = (TextView)findViewById(R.id.rideDescription1);


		new RetreiveFeedTask().execute("http://date.jsontest.com/");
		
		/*
		String json = null;
		json = loadJSONFromAsset();
		try {
			parse_json(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public void parse_json(String json_str) throws JSONException 
	{
		JSONObject obj = new JSONObject(json_str);
		String aJsonString = obj.getString("time");
		rideDescription1.setText("time: " + aJsonString);
	}

	// this method get the whole text in the json file (located in the assets folder) into one string
	public String loadJSONFromAsset() {
		String json = null;
		try {

			InputStream is = getAssets().open("historyDataTest.json");

			int size = is.available();

			byte[] buffer = new byte[size];

			is.read(buffer);

			is.close();

			json = new String(buffer, "UTF-8");


		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}

		return json;
	}

	
	class RetreiveFeedTask extends AsyncTask<String, Void, String> {
		
		protected String doInBackground(String... urls) {
			// Making HTTP request
			try {
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(urls[0]);
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line );
				}
				is.close();
				json = sb.toString();
			} catch (Exception e) {
				Log.e("Buffer Error", "Error converting result " + e.toString());
			}
			return json;
		}
		
		protected void onPostExecute(String json) {
			try {
				parse_json(json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			testView.setText(json);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}

}
