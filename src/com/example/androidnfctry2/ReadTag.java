package com.example.androidnfctry2;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class ReadTag extends Activity {

	private NfcAdapter myNfcAdapter;
	private TextView myText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_tag);

		myText = (TextView) findViewById(R.id.readActivityTextView);
		myNfcAdapter = NfcAdapter.getDefaultAdapter(this);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Tag detectedTag = (Tag) extras.get("tag");
			myText.setText(detectedTag.toString());
		}

		NdefMessage[] messages = getNdefMessages(getIntent());	
		
	}
	
	private NdefMessage[] getNdefMessages(Intent intent)
	{
		NdefMessage[] message = null;
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()))
		{
			Parcelable[] rawMessages =
					intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMessages != null) 
			{
				message = new NdefMessage[rawMessages.length];
				for (int i = 0; i < rawMessages.length; i++) 
				{
					message[i] = (NdefMessage) rawMessages[i];
				}
			} 
			else 
			{
				byte[] empty = new byte[] {};
				NdefRecord record = new NdefRecord ( NdefRecord.TNF_UNKNOWN,
						empty, empty, empty );
				NdefMessage msg = new NdefMessage( new NdefRecord[] { record } );
				message = new NdefMessage[] { msg };
			}
		}
		else {
			Log.d("", "Unknown intent.");
			finish();
		}
		return message;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.read_tag, menu);
		return true;
	}

}
