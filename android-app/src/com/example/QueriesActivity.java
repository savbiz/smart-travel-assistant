package com.example;

import java.util.HashMap;
import java.util.List;

import loginregister.SessionManager;
import utils.CustomListAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.semifinaltravelassistant.R;

import database.DatabaseHandler;
import database.Query;
import database.User;

public class QueriesActivity extends ListActivity {

	List<Query> listQueries;
	User userFound = null;
	DatabaseHandler db;
	SessionManager session;
	Intent results;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.list_queries);

		db = new DatabaseHandler(this);

		// Session Manager
		session = new SessionManager(getApplicationContext());

		// get user data from session
		HashMap<String, String> user = session.getUserDetails();

		// name
		String username = user.get(SessionManager.KEY_SESSION_USERNAME);

		listQueries = db.getAllQueryByUser(username);

		final ListView lv1 = getListView();

		lv1.setAdapter(new CustomListAdapter(this, listQueries));

		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				// DEVE APRIRE RESULTACTIVITY CON TUTTI I POI.

				Object o = lv1.getItemAtPosition(position);
				Query query = (Query) o;

				results = new Intent(QueriesActivity.this, ResultActivity.class);

				results.putExtra("type", query.getType());
				results.putExtra("raggio", query.getRadius());
				Bundle b = new Bundle();
				b.putDouble("latitude", query.getLatitude());
				b.putDouble("longitude", query.getLongitude());
				results.putExtras(b);
				startActivity(results);

				finish();
			}

		});

	}

}