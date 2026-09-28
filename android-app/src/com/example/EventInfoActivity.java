package com.example;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.semifinaltravelassistant.R;

public class EventInfoActivity extends Activity {

	TextView textTitle;
	TextView textWhere;
	TextView textDesc;
	Button btnShow;
	Button btnGuide;
	Button btnCall1;

	String title;
	String description;
	String where;
	double event_latitude;
	double event_longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.eventinfo);

		textTitle = (TextView) findViewById(R.id.textTitle);
		textWhere = (TextView) findViewById(R.id.textWhere);
		textDesc = (TextView) findViewById(R.id.textDesc);

		btnShow = (Button) findViewById(R.id.btnShow);
		btnGuide = (Button) findViewById(R.id.btnGuide);
		btnCall1 = (Button) findViewById(R.id.btnCall1);
		btnCall1.setVisibility(View.INVISIBLE);

		// Getting intent data
		Intent i = getIntent();
		title = i.getStringExtra("title");
		description = i.getStringExtra("description");
		where = i.getStringExtra("where");

		Bundle b = i.getExtras();
		event_latitude = b.getDouble("latitude");
		event_longitude = b.getDouble("longitude");

		// event_latitude = i.getDoubleExtra("latitude", defaultValue);
		// event_latitude = parseDouble(i.getStringExtra("latitude"));
		// event_longitude = Double.parseDouble(i.getStringExtra("longitude"));

		textTitle.setText(title);
		textWhere.setText(where);
		textDesc.setText(description);
		textDesc.setMovementMethod(new ScrollingMovementMethod());

		// btnCall1.setText("");

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
