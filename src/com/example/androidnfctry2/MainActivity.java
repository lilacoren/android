package com.example.androidnfctry2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Parcelable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private NfcAdapter myNfcAdapter;
	private TextView myText;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myText = (TextView) findViewById(R.id.mytextview);

		// check the device ability to work with nfc 
		myNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		
		if (myNfcAdapter == null)
			myText.setText("NFC is not available for the device!!!");
		else
			myText.setText("NFC is available for the device");

		//detect the nfc. "ACTION_TECH_DISCOVERED" related to a tag without 
		// data on it. saves the tag that was detected as an intent

		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction()))
		{

			Tag detectedTag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
			
			//PREPARING A MESSAGE TO WRITING OPERATION 
			Locale locale= new Locale("en","US");
			byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
			boolean encodeInUtf8=false;
			Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") :
				Charset.forName("UTF-16");
			int utfBit = encodeInUtf8 ? 0 : (1 << 7);
			char status = (char) (utfBit + langBytes.length);
			String RTD_TEXT= "This is Evia's message!";
			byte[] textBytes = RTD_TEXT.getBytes(utfEncoding); // copy the string above
			byte[] data = new byte[1 + langBytes.length + textBytes.length];
			data[0] = (byte) status;
			System.arraycopy(langBytes, 0, data, 1, langBytes.length);
			System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
			NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
					NdefRecord.RTD_TEXT, new byte[0], data);
			NdefMessage newMessage= new NdefMessage(new NdefRecord[] { textRecord });
			 
			NdefMessage[] messages = getNdefMessages(getIntent());

			//CALL THE WRITING ACTIVITY AFTER THAT THE MESSAGE WAS SET
			//Intent i = new Intent(getApplicationContext(), SecondActivity.class);
			//i.putExtra("message", newMessage);
			//i.putExtra("tag", detectedTag);
			//startActivity(i);
			
			String payload = "";
			myText.setText("");
			
			
			//AFTER READING: MESSAGE DISPLAY:
			for(int i=0;i<messages.length;i++)
			{
				myText.append("Message " + (i+1) + ":\n");
				for(int j=0;j<messages[0].getRecords().length;j++)
				{
					NdefRecord record = messages[i].getRecords()[j];
					byte statusByte = record.getPayload()[0];
					int languageCodeLength = statusByte & 0x3F;
					myText.append("Language Code Length:" + languageCodeLength+"\n");
					String languageCode = new String( record.getPayload(), 1,
							languageCodeLength, Charset.forName("UTF-8"));
					myText.append("Language Code:" + languageCode+"\n");
					int isUTF8 = statusByte-languageCodeLength;
					if(isUTF8 == 0x00)
					{
						myText.append((j+1) + "th. Record is UTF-8\n");
						payload = new String( record.getPayload(), 1+languageCodeLength,
								record.getPayload().length-1-languageCodeLength,
								Charset.forName("UTF-8"));
					} 
					else if (isUTF8==-0x80)
					{
						myText.append((j+1) + "th. Record is UTF-16\n");
						payload = new String( record.getPayload(), 1+languageCodeLength,
								record.getPayload().length-1-languageCodeLength,
								Charset.forName("UTF-16"));
					}
					myText.append((j+1) + "th. Record Tnf: " + record.getTnf() + "\n");
					myText.append((j+1) + "th. Record type: " +
							new String(record.getType()) + "\n");
					myText.append((j+1) + "th. Record id: " +
							new String(record.getId()) + "\n");
					myText.append((j+1) + "th. Record payload: " + payload + "\n");
				}
			}
			}
		}


		private NdefMessage[] getNdefMessages(Intent intent)
		{
			NdefMessage[] message = null;
			if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction()))
			{
				Parcelable[] rawMessages =
						intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
				if (rawMessages != null) 
				{
					message = new NdefMessage[rawMessages.length];
					for (int i = 0; i < rawMessages.length; i++) 
						message[i] = (NdefMessage) rawMessages[i];
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
			}
			return message;
		}


		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}

	}
