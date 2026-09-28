package com.example;

import com.example.semifinaltravelassistant.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MapActivity extends Activity {

	private GoogleMap googleMap;
	MarkerOptions marker;

	double place_latitude;
	double place_longitude;
	String title;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Getting intent data
		Intent i = getIntent();

		// Users current geo location
		place_latitude = Double.parseDouble(i.getStringExtra("place_latitude"));
		place_longitude = Double.parseDouble(i
				.getStringExtra("place_longitude"));
		title = i.getStringExtra("title");

		setContentView(R.layout.map_activity);

		// create marker
		marker = new MarkerOptions().position(
				new LatLng(place_latitude, place_longitude)).title(title);

		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressLint("NewApi")
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// adding marker
			googleMap.addMarker(marker);

			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(new LatLng(place_latitude, place_longitude))
					.zoom(12).build();

			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					place_latitude, place_longitude), 20));

			googleMap.getUiSettings().setCompassEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

}
