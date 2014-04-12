package com.example.androidnfctry2;

import java.io.IOException;

import android.nfc.NdefMessage;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity {
	
	private TextView myText ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		NdefMessage newMessage = null;
		Tag detectedTag = null;

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			newMessage = (NdefMessage) extras.get("message");
			detectedTag = (Tag) extras.get("tag");
		}

		myText = (TextView) findViewById(R.id.SecondMyText);

		myText.setText(detectedTag.toString());
		
		writeNdefMessageToTag(newMessage, detectedTag);

	}


	private boolean writeNdefMessageToTag(NdefMessage message, Tag detectedTag)
	{
		int size = message.toByteArray().length;

		try {
			Ndef ndef = Ndef.get(detectedTag);
			if (ndef != null) {
				ndef.connect();
				if (!ndef.isWritable()) {
					Toast.makeText(this, "Tag is read-only.", Toast.LENGTH_SHORT).show();
					return false;
				}

				if (ndef.getMaxSize() < size) {
					Toast.makeText(this, "The data cannot written to tag," +
							"Tag capacity is " + ndef.getMaxSize() + " bytes, message is "
							+ size + " bytes.", Toast.LENGTH_SHORT).show();
					return false;
				}

				ndef.writeNdefMessage(message);
				ndef.close();
				Toast.makeText(this, "Message is written to tag.",
						Toast.LENGTH_SHORT).show();
				return true;
			}
			else
			{
				NdefFormatable ndefFormat = NdefFormatable.get(detectedTag);
				if (ndefFormat != null) {
					try {
						ndefFormat.connect();
						ndefFormat.format(message);
						ndefFormat.close();
						Toast.makeText(this, "The data is written to the tag ",
								Toast.LENGTH_SHORT).show();
						return true;
					} catch (IOException e) {
						Toast.makeText(this, "Failed to format tag",
								Toast.LENGTH_SHORT).show();
						return false;
					}
				} 
				else
				{
					Toast.makeText(this, "NDEF is not supported",
							Toast.LENGTH_SHORT).show();
					return false;
				}
			}
		} catch (Exception e) {
			Toast.makeText(this, "Write opreation is failed",
					Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

}
