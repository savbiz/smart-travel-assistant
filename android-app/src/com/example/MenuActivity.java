package com.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import loginregister.LoginActivity;
import loginregister.SessionManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import check.AlertToUserConnGpsManager;
import check.AlertToUserGeneralManager;
import check.ConnectionDetector;

import com.example.semifinaltravelassistant.R;

import database.DatabaseHandler;
import database.Query;
import database.User;

public class MenuActivity extends Activity {

	// Controllo Connessione
	// flag for Internet connection status
	Boolean isInternetPresent = false;

	// Connection detector class
	ConnectionDetector cd;
	AlertToUserConnGpsManager alert;
	AlertToUserGeneralManager alertgeneral;
	LocationManager locationManager;
	Location location;

	List<Query> listQueries;
	User userFound = null;
	DatabaseHandler db;

	List<User> listUsers;

	Button btnLoad, btnEventi, btnCultura, btnSvago, btnCibo;
	SessionManager session;

	boolean isNowLunchTime;
	boolean isNowDinnerTime;

	String choice = "inizio";

	int numberQueries;

	boolean isNowBetweenDateTime(final int a, final int b) {
		SimpleDateFormat sd = new SimpleDateFormat("kk");

		String now = sd.format(new Date());
		int now1 = Integer.parseInt(now);

		return (now1 >= a && now1 <= b);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_menu);

		// New button
		btnSvago = (Button) findViewById(R.id.btnSvago);
		btnCultura = (Button) findViewById(R.id.btnCultura);
		btnCibo = (Button) findViewById(R.id.btnCibo);
		btnEventi = (Button) findViewById(R.id.btnEventi);
		// Load button
		btnLoad = (Button) findViewById(R.id.btnLoad);

		// creating connection detector and alert manager class instance
		cd = new ConnectionDetector(MenuActivity.this);
		alert = new AlertToUserConnGpsManager(MenuActivity.this);

		// controllo connessione internet MOBILE (no WIFI)
		isInternetPresent = cd.isConnectingToInternet(); // true or false

		if (isInternetPresent) {
			Toast.makeText(MenuActivity.this,
					"Connessione dati correttamente attiva!",
					Toast.LENGTH_SHORT).show();
		} else {
			alert.showConnectionDisabledAlertToUser();
		}

		// Controllo GPS
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(MenuActivity.this,
					"Sensore GPS correttamente attivo!", Toast.LENGTH_SHORT)
					.show();
		} else {
			alert.showGPSDisabledAlertToUser();
		}

		// Session Manager
		session = new SessionManager(getApplicationContext());

		// FOOD
		// get user data from session
		HashMap<String, String> food = session.getFoodDetails();

		// name

		choice = food.get(SessionManager.KEY_SESSION_FOOD);

		SimpleDateFormat sd = new SimpleDateFormat("kk-mm-ss");

		int twelve = 12;
		int fifteen = 15;

		int eightpm = 20;
		int elevenpm = 23;

		isNowLunchTime = isNowBetweenDateTime(twelve, fifteen);
		isNowDinnerTime = isNowBetweenDateTime(eightpm, elevenpm);

		if (choice == null)
			choice = "inizio";

		if (choice.equals("inizio") && !choice.equals("fattopranzo")
				&& !choice.equals("nopranzo") && isNowLunchTime) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					MenuActivity.this);
			builder.setMessage("È ora di pranzo! Hai voglia di mangiare qualcosa?");
			builder.setPositiveButton("Va bene!",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							choice = "fattopranzo";
							session.createFoodSession(choice);
							// Switching to Jess Engine
							Intent jess = new Intent(getApplicationContext(),
									FoodJessActivity.class);

							// get user data from session
							HashMap<String, String> user = session
									.getUserDetails();

							// name
							String username = user
									.get(SessionManager.KEY_SESSION_USERNAME);

							User u = db.getUser(username);

							jess.putExtra("age", Integer.toString(u.getAge()));

							startActivity(jess);

						}
					});
			builder.setNeutralButton("No, grazie.",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							choice = "nopranzo";
							session.createFoodSession(choice);
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
		}

		if (choice.equals("inizio") && !choice.equals("fattocena")
				&& !choice.equals("nocena") && isNowDinnerTime) {
			Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("È ora di cena! Hai voglia di mangiare qualcosa?");
			builder.setPositiveButton("Va bene!",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							choice = "fattocena";
							session.createFoodSession(choice);
							// Starting MainActivity
							// Switching to Jess Engine
							Intent jess = new Intent(getApplicationContext(),
									FoodJessActivity.class);

							// get user data from session
							HashMap<String, String> user = session
									.getUserDetails();

							// name
							String username = user
									.get(SessionManager.KEY_SESSION_USERNAME);

							User u = db.getUser(username);

							jess.putExtra("age", Integer.toString(u.getAge()));

							startActivity(jess);

						}
					});
			builder.setNeutralButton("No, grazie.",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							choice = "nocena";
							session.createFoodSession(choice);
						}
					});

			AlertDialog alert = builder.create();
			alert.show();
		}

		db = new DatabaseHandler(this);

		btnLoad.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// get user data from session
				HashMap<String, String> user = session.getUserDetails();

				// name
				String username = user.get(SessionManager.KEY_SESSION_USERNAME);

				listQueries = db.getAllQueryByUser(username);

				if (listQueries == null || listQueries.size() == 0) {
					Toast.makeText(
							getApplicationContext(),
							"Non ci sono suggerimenti precedentemente salvati!",
							Toast.LENGTH_LONG).show();
				} else {
					// Switching to QueriesActivity screen
					Intent queries = new Intent(getApplicationContext(),
							QueriesActivity.class);
					startActivity(queries);
				}

			}
		});

		btnCibo.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// Intent results = new Intent(getApplicationContext(),
				// ResultActivity.class);
				// startActivity(results);

				// Switching to Jess Engine
				Intent jess = new Intent(getApplicationContext(),
						FoodJessActivity.class);

				// get user data from session
				HashMap<String, String> user = session.getUserDetails();

				// name
				String username = user.get(SessionManager.KEY_SESSION_USERNAME);

				User u = db.getUser(username);

				jess.putExtra("age", Integer.toString(u.getAge()));

				startActivity(jess);
			}
		});

		btnCultura.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// Intent results = new Intent(getApplicationContext(),
				// ResultActivity.class);
				// startActivity(results);

				// Switching to Jess Engine
				Intent jess = new Intent(getApplicationContext(),
						CultureJessActivity.class);

				// get user data from session
				HashMap<String, String> user = session.getUserDetails();

				// name
				String username = user.get(SessionManager.KEY_SESSION_USERNAME);

				User u = db.getUser(username);

				jess.putExtra("age", Integer.toString(u.getAge()));

				startActivity(jess);
			}
		});

		btnSvago.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// Intent results = new Intent(getApplicationContext(),
				// ResultActivity.class);
				// startActivity(results);

				// Switching to Jess Engine
				Intent jess = new Intent(getApplicationContext(),
						FunJessActivity.class);

				// get user data from session
				HashMap<String, String> user = session.getUserDetails();

				// name
				String username = user.get(SessionManager.KEY_SESSION_USERNAME);

				User u = db.getUser(username);

				jess.putExtra("sex", u.getSex());
				jess.putExtra("age", Integer.toString(u.getAge()));

				startActivity(jess);
			}
		});

		btnEventi.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// Intent results = new Intent(getApplicationContext(),
				// ResultActivity.class);
				// startActivity(results);

				// Switching to Jess Engine
				Intent jess = new Intent(getApplicationContext(),
						EventJessActivity.class);

				// get user data from session
				HashMap<String, String> user = session.getUserDetails();

				// name
				String username = user.get(SessionManager.KEY_SESSION_USERNAME);

				User u = db.getUser(username);

				jess.putExtra("age", Integer.toString(u.getAge()));

				startActivity(jess);
			}
		});

	}

	// Initiating Menu XML file (menu.xml)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}

	/**
	 * Event Handling for Individual menu item selected Identify single menu
	 * item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.logout:
			// Single menu item is selected do something

			session.logoutUser();
			// Switching to Register screen
			Intent i = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(i);

			return true;
		case R.id.profile:

			// get user data from session
			HashMap<String, String> user = session.getUserDetails();

			// name
			String username = user.get(SessionManager.KEY_SESSION_USERNAME);

			User u = db.getUser(username);

			String descrizione = "Nome: " + u.getName() + "\nUsername: "
					+ u.getUsername() + "\nGenere: " + u.getSex() + "\nEtà: "
					+ u.getAge();

			showYNAlertDialog("Il mio profilo", descrizione, "Ok");

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	// OPZIONI A DUE RISPOSTE
	public void showYNAlertDialog(String title, String message,
			final String option1) {

		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(option1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.show().setCanceledOnTouchOutside(false); // per non chiudere se
															// si tappa fuori
	}

}
