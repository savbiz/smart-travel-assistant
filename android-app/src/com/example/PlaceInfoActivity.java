package com.example;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.semifinaltravelassistant.R;

public class PlaceInfoActivity extends Activity {

	TextView textTitle;
	TextView textWhere;
	TextView textDesc;
	TextView textWebsite;
	Button btnShow;
	Button btnGuide;
	Button btnCall;

	String title;
	String description;
	String where;
	String phone;
	String website;
	double event_latitude;
	double event_longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.placeinfo);

		textTitle = (TextView) findViewById(R.id.textTitle);
		textWhere = (TextView) findViewById(R.id.textWhere);
		textDesc = (TextView) findViewById(R.id.textDesc);
		textWebsite = (TextView) findViewById(R.id.textWebsite);

		btnShow = (Button) findViewById(R.id.btnShow);
		btnGuide = (Button) findViewById(R.id.btnGuide);

		btnCall = (Button) findViewById(R.id.btnCall);

		// Getting intent data
		Intent i = getIntent();
		title = i.getStringExtra("title");
		description = i.getStringExtra("description");
		where = i.getStringExtra("where");
		phone = i.getStringExtra("telephone");
		website = i.getStringExtra("website");

		if (phone != null) {
			btnCall.setText(phone);
		} else {
			btnCall.setText("Numero telefonico non disponibile");
		}

		Bundle b = i.getExtras();
		event_latitude = b.getDouble("latitude");
		event_longitude = b.getDouble("longitude");

		// event_latitude = i.getDoubleExtra("latitude", defaultValue);
		// event_latitude = parseDouble(i.getStringExtra("latitude"));
		// event_longitude = Double.parseDouble(i.getStringExtra("longitude"));

		textTitle.setText(title);
		if (where != null)
			textWhere.setText(where);
		else
			textWhere.setText("");
		textDesc.setText(description);
		textDesc.setMovementMethod(LinkMovementMethod.getInstance());

		textWebsite.setText(website);
		textWebsite.setMovementMethod(LinkMovementMethod.getInstance());

		btnCall.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				try {
					Intent callintent = new Intent(Intent.ACTION_CALL);
					callintent.setData(Uri.parse("tel:" + phone.trim()));
					startActivity(callintent);
				} catch (Exception e) {
					Log.e("STA", "Chiamata fallita", e);
				}

			}

		});

		btnShow.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// Switching to QueriesActivity screen
				Intent map = new Intent(getApplicationContext(),
						MapActivity.class);

				// Sending user current geo location
				map.putExtra("place_latitude", Double.toString(event_latitude));
				map.putExtra("place_longitude",
						Double.toString(event_longitude));
				map.putExtra("title", title);

				startActivity(map);
			}
		});

		btnGuide.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Intent navigator = new Intent(Intent.ACTION_VIEW, Uri
						.parse("google.navigation:q=" + event_latitude + ","
								+ event_longitude));
				startActivity(navigator);
			}
		});

	}

}