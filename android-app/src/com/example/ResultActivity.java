package com.example;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
//import org.gmarz.googleplaces.GooglePlaces;
//import org.gmarz.googleplaces.models.DetailsResult;
//import org.gmarz.googleplaces.models.Place;
//import org.gmarz.googleplaces.models.PlaceDetails;
//import org.gmarz.googleplaces.models.PlacesResult;
//import org.gmarz.googleplaces.models.Result.StatusCode;

import com.a2plab.googleplaces.*;
import com.a2plab.googleplaces.models.Place;
import com.a2plab.googleplaces.models.PlaceDetails;
import com.a2plab.googleplaces.query.NearbySearchQuery;
import com.a2plab.googleplaces.result.Result;
import com.a2plab.googleplaces.result.Result.StatusCode;

import utils.CustomListAdapterFilteredPlaceDetails;
import utils.CustomListAdapterPlaces;

import com.arcusweather.forecastio.ForecastIO;
import com.arcusweather.forecastio.ForecastIOResponse;
import com.example.semifinaltravelassistant.R;

import loginregister.SessionManager;
import database.DatabaseHandler;
import database.Query;
import database.User;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ResultActivity extends ListActivity {

	Button btnShowOthers;
	Button btnFilter;
	TextView txtMeteo;
	TextView txtTemp;

	String meteo;
	String temperature;

	String alternativa;

	LocationManager locationManager;
	Location location;
	double latitude = 0; // latitude
	double longitude = 0; // longitude

	private CheckBox checkClima, checkTemperatura, checkOrari;

	private CheckBox checkSpiaggia, checkGiardini, checkParchi;
	private CheckBox checkArte, checkMusei, checkBiblioteca;

	List<Query> queries;
	// List<Place> places;
	// Place somePlace;

	float[] listDistancesPlace = new float[100];

	// Places Listview
	TextView tv;

	// Progress dialog
	ProgressDialog pDialog;

	SessionManager session;

	// ListItems data
	ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String, String>>();

	// KEY Strings
	public static String KEY_REFERENCE = "reference"; // id of the place
	public static String KEY_NAME = "name"; // name of the place
	public static String KEY_VICINITY = "vicinity"; // Place area name

	public static String GOOGLE_PLACES_API_KEY = "YOUR_GOOGLE_PLACES_API_KEY";

	String raggio = null;
	String tipo;

	String type;

	ArrayList<String> types;

	int radius;

	DatabaseHandler db;
	Query q;

	Result result;
	ArrayList<Place> placesList;
	ArrayList<Place> filteredPlacesList;
	ArrayList<PlaceDetails> listPlaceDetails;
	ArrayList<PlaceDetails> listfilteredPlaceDetails;

	ArrayList<String> food;
	ArrayList<String> fun;
	ArrayList<String> culture;

	GooglePlaces gP;

	String firstDailyIcon;
	String deduzione;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.list_places);

		btnShowOthers = (Button) findViewById(R.id.btnShowOthers);
		btnFilter = (Button) findViewById(R.id.btnFilter);

		txtMeteo = (TextView) findViewById(R.id.meteo);
		txtTemp = (TextView) findViewById(R.id.temperatura);

		culture = new ArrayList<String>();
		fun = new ArrayList<String>();
		food = new ArrayList<String>();

		btnShowOthers.setVisibility(View.INVISIBLE);
		btnFilter.setVisibility(View.INVISIBLE);

		btnFilter.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if (culture.contains(tipo) || food.contains(tipo)) {
					if (listfilteredPlaceDetails == null
							|| listfilteredPlaceDetails.isEmpty() == true) {

						Toast.makeText(
								getApplicationContext(),
								"Non sono disponibili attualmente luoghi aperti!",
								Toast.LENGTH_LONG).show();
						// invece di getApplicationContext() prova
						// ResultActivity.this
					} else {
						showPopUp3();
						Toast.makeText(getApplicationContext(),
								"Sono presenti luoghi attualmente aperti!",
								Toast.LENGTH_LONG).show();
					}
				} else if (fun.contains(tipo)) {
					showPopUp3();
				}

			}
		});

		btnShowOthers.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("ShowToast")
			public void onClick(View v) {

				if (tipo.equals("shopping")) {
					Intent results = new Intent(getApplicationContext(),
							ResultActivity.class);

					results.putExtra("type", "nightclub");
					results.putExtra("raggio", radius);
					startActivity(results);
					finish();
				}
				if (tipo.equals("spa")) {
					filtroRelax();
				}

				if (tipo.equals("occidentali") || tipo.equals("orientali")
						|| tipo.equals("sannicola") || tipo.equals("chiese")
						|| tipo.equals("castelli") || tipo.equals("monumenti")) {
					filtroCultura();
				}
			}
		});

		placesList = new ArrayList<Place>();
		filteredPlacesList = new ArrayList<Place>();

		listPlaceDetails = new ArrayList<PlaceDetails>();
		listfilteredPlaceDetails = new ArrayList<PlaceDetails>();

		db = new DatabaseHandler(this);

		q = new Query();

		// Session Manager
		session = new SessionManager(getApplicationContext());

		// Getting intent data
		Intent i = getIntent();
		Bundle b = i.getExtras();
		if (!b.isEmpty()) {
			latitude = b.getDouble("latitude");
			longitude = b.getDouble("longitude");
			radius = i.getIntExtra("raggio", radius);
		}

		raggio = i.getStringExtra("raggio");
		tipo = i.getStringExtra("type");

		type = tipo;

		types = new ArrayList<String>();

		if (raggio != null) {
			// RADIUS CONTROL
			if (raggio.equals("1km"))
				radius = 1000;
			if (raggio.equals("2km"))
				radius = 2000;
			if (raggio.equals("10km"))
				radius = 10000;
			if (raggio.equals("50km"))
				radius = 50000;
			if (raggio.equals("100km"))
				radius = 100000;
		}
		// TYPE CONTROL FOR FOOD
		if (tipo.equals("salato"))
			type = "food"; // aggiunti PIZZA keyword
		if (tipo.equals("dolce"))
			type = "bakery"; // OK
		if (tipo.equals("pizza"))
			type = "food"; // aggiunti PIZZA come keyword
		if (tipo.equals("italiano"))
			type = "restaurant"; //
		if (tipo.equals("orientale"))
			type = "restaurant"; // aggiungere CINESE
		if (tipo.equals("occidentale"))
			type = "restaurant"; // aggiungere MESSICANO, SPAGNOLO come keyword

		food.add("salato");
		food.add("dolce");
		food.add("pizza");
		food.add("italiano");
		food.add("orientale");
		food.add("occidentale");
		// if (food.contains(tipo))
		// deduzione="Ho ";

		// TYPE CONTROL FOR CULTURE
		if (tipo.equals("arte"))
			type = "art_gallery";
		if (tipo.equals("musei"))
			type = "museum";
		if (tipo.equals("biblioteca"))
			type = "library";
		if (tipo.equals("occidentali"))
			type = "synagogue";
		if (tipo.equals("orientali")) {
			// type = "mosque|place_of_worship|hindu_temple"; //LISTA
			types.add("mosque");
			types.add("hindu_temple");
			types.add("place_of_worship");
		}
		if (tipo.equals("sannicola"))
			type = "Basilica San Nicola"; // come textsearch
		if (tipo.equals("chiese"))
			type = "church";
		if (tipo.equals("castelli"))
			type = "castello"; // ? come textsearch
		if (tipo.equals("monumenti"))
			type = "piazza"; // ? come textsearch

		culture.add("arte");
		culture.add("musei");
		culture.add("biblioteca");
		culture.add("occidentali");
		culture.add("orientali");
		culture.add("sannicola");
		culture.add("chiese");
		culture.add("castelli");
		culture.add("monumenti");

		// if (culture.contains(tipo))
		// deduzione="Ho ";

		// TYPE CONTROL FOR FUN
		if (tipo.equals("shopping"))
			type = "shopping_mall"; // DA CONTROLLARE PERCHE NON RESTITUISCE
									// NULLA
		if (tipo.equals("spa")) // DA CONTROLLARE PERCHE NON RESTITUISCE NULLA
			type = "spa";
		if (tipo.equals("spiaggia"))
			type = "spiaggia";
		if (tipo.equals("giardini"))
			type = "park"; // aggiunta keyword "giardini"
		if (tipo.equals("parchi"))
			type = "park";
		if (tipo.equals("giochi"))
			type = "amusement_park";
		if (tipo.equals("animali")) {
			// type = "zoo|aquarium"; //LISTA
			types.add("zoo");
			// types.add("aquarium");
		}
		if (tipo.equals("sport"))
			type = "stadium"; // ?
		if (tipo.equals("nightclub"))
			type = "night_club"; // ?

		fun.add("shopping");
		fun.add("spa");
		fun.add("spiaggia");
		fun.add("giardini");
		fun.add("parchi");
		fun.add("giochi");
		fun.add("animali");
		fun.add("sport");
		fun.add("nightclub");

		if (tipo.equals("spiaggia") || tipo.equals("giardini")
				|| tipo.equals("parchi") || tipo.equals("giochi")
				|| tipo.equals("animali") || tipo.equals("sport")
				|| tipo.equals("nightclub"))
			btnShowOthers.setVisibility(View.INVISIBLE);
		if (tipo.equals("arte") || tipo.equals("musei")
				|| tipo.equals("biblioteca"))
			btnShowOthers.setVisibility(View.INVISIBLE);

		// calling background Async task to load Google Places
		// After getting places from Google all the data is shown in listview
		new LoadPlaces().execute();

	}

	private void filtroRelax() {

		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		helpBuilder.setTitle("Alternative...");

		LayoutInflater inflater = getLayoutInflater();
		View checkboxLayout = inflater.inflate(R.layout.filtrosvagorelax, null);
		helpBuilder.setView(checkboxLayout);

		checkSpiaggia = (CheckBox) checkboxLayout.findViewById(R.id.spiaggia);
		checkGiardini = (CheckBox) checkboxLayout.findViewById(R.id.giardini);
		checkParchi = (CheckBox) checkboxLayout.findViewById(R.id.parchi);

		helpBuilder.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// Do nothing but close the dialog

						if (checkSpiaggia.isChecked()) {
							Intent results = new Intent(
									getApplicationContext(),
									ResultActivity.class);
							// popup
							results.putExtra("type", "spiaggia");
							results.putExtra("raggio", radius);
							startActivity(results);
							finish();

						} else if (checkGiardini.isChecked()) {
							Intent results = new Intent(
									getApplicationContext(),
									ResultActivity.class);
							// popup
							results.putExtra("type", "giardini");
							results.putExtra("raggio", radius);
							startActivity(results);
							finish();

						} else if (checkParchi.isChecked()) {
							Intent results = new Intent(
									getApplicationContext(),
									ResultActivity.class);
							// popup
							results.putExtra("type", "parchi");
							results.putExtra("raggio", radius);
							startActivity(results);
							finish();

						} else
							Toast.makeText(getApplicationContext(),
									"Non è stata fatta alcuna scelta.",
									Toast.LENGTH_SHORT).show();
					}
				});
		// Remember, create doesn't show the dialog
		AlertDialog helpDialog = helpBuilder.create();
		helpDialog.show();
	}

	private void filtroCultura() {

		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		helpBuilder.setTitle("Alternative...");

		LayoutInflater inflater = getLayoutInflater();
		View checkboxLayout = inflater.inflate(R.layout.filtrocultura, null);
		helpBuilder.setView(checkboxLayout);

		checkArte = (CheckBox) checkboxLayout.findViewById(R.id.arte);
		checkMusei = (CheckBox) checkboxLayout.findViewById(R.id.musei);
		checkBiblioteca = (CheckBox) checkboxLayout
				.findViewById(R.id.biblioteca);

		helpBuilder.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// Do nothing but close the dialog

						if (checkArte.isChecked()) {
							Intent results = new Intent(
									getApplicationContext(),
									ResultActivity.class);
							// popup
							results.putExtra("type", "arte");
							results.putExtra("raggio", radius);
							startActivity(results);
							finish();

						} else if (checkMusei.isChecked()) {
							Intent results = new Intent(
									getApplicationContext(),
									ResultActivity.class);
							// popup
							results.putExtra("type", "musei");
							results.putExtra("raggio", radius);
							startActivity(results);
							finish();
						} else if (checkBiblioteca.isChecked()) {
							Intent results = new Intent(
									getApplicationContext(),
									ResultActivity.class);
							// popup
							results.putExtra("type", "biblioteca");
							results.putExtra("raggio", radius);
							startActivity(results);
							finish();
						} else
							Toast.makeText(getApplicationContext(),
									"Non è stata fatta alcuna scelta.",
									Toast.LENGTH_SHORT).show();
					}
				});
		// Remember, create doesn't show the dialog
		AlertDialog helpDialog = helpBuilder.create();
		helpDialog.show();
	}

	private void showPopUp3() {

		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		helpBuilder.setTitle("Filtra per...");

		LayoutInflater inflater = getLayoutInflater();
		if (fun.contains(tipo)) {
			View checkboxLayout = inflater.inflate(R.layout.popup, null);

			helpBuilder.setView(checkboxLayout);

			checkClima = (CheckBox) checkboxLayout.findViewById(R.id.clima);
			checkTemperatura = (CheckBox) checkboxLayout
					.findViewById(R.id.temperatura);
			checkOrari = (CheckBox) checkboxLayout.findViewById(R.id.orari);

			helpBuilder.setPositiveButton("Filtra!",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// Do nothing but close the dialog

							double temp = Double.parseDouble(temperature);

							if (checkClima.isChecked()) { // filter();

								if (meteo.equals("Neve")
										|| meteo.equals("Pioggia")
										|| meteo.equals("Grandine")
										|| meteo.equals("Nebbia")) {
									if (tipo.equals("parchi")
											|| tipo.equals("giardini")
											|| tipo.equals("spiaggia")) {
										Intent results = new Intent(
												getApplicationContext(),
												ResultActivity.class);

										results.putExtra("type", "nightclub");
										results.putExtra("raggio", radius);
										startActivity(results);
										finish();
									} else
										Toast.makeText(
												getApplicationContext(),
												"Le condizioni metereologiche non sono ottimali, ma i luoghi scelti sono coperti!",
												Toast.LENGTH_LONG).show();

								} else
									Toast.makeText(
											getApplicationContext(),
											"Le condizioni metereologiche sono buone, quindi ogni luogo è potenzialmente adatto!",
											Toast.LENGTH_LONG).show();

							} else if (checkTemperatura.isChecked()) {
								if (tipo.equals("parchi")
										|| tipo.equals("giardini"))
									if (temp >= 32) {
										Intent results = new Intent(
												getApplicationContext(),
												ResultActivity.class);

										results.putExtra("type", "spiaggia");
										results.putExtra("raggio", radius);
										startActivity(results);
										finish();
									} else
										Toast.makeText(
												getApplicationContext(),
												"La temperatura è ottima, quindi gli spazi verdi sono potenzialmente adatti!",
												Toast.LENGTH_LONG).show();

								if (tipo.equals("spiaggia"))
									if (temp < 32) {
										Intent results = new Intent(
												getApplicationContext(),
												ResultActivity.class);

										results.putExtra("type", "parchi");
										results.putExtra("raggio", radius);
										startActivity(results);
										finish();
									} else
										Toast.makeText(
												getApplicationContext(),
												"La temperatura è ottima, quindi le spiagge sono potenzialmente adatte!",
												Toast.LENGTH_LONG).show();
								else
									Toast.makeText(
											getApplicationContext(),
											"Il filtraggio per temperatura non è necessario, vista la categoria.",
											Toast.LENGTH_LONG).show();
							} else if (checkOrari.isChecked()) {
								if (listfilteredPlaceDetails == null
										|| listfilteredPlaceDetails.isEmpty() == true) {
									Toast.makeText(
											getApplicationContext(),
											"Purtroppo non ci sono punti di interesse aperti attualmente!",
											Toast.LENGTH_LONG).show();
								} else
									filter();
							} else
								Toast.makeText(
										getApplicationContext(),
										"Non è stato selezionato alcun filtro.",
										Toast.LENGTH_SHORT).show();
						}
					});

		} else if (food.contains(tipo) || culture.contains(tipo)) {
			View checkboxLayout = inflater.inflate(R.layout.popupcibocultura,
					null);

			helpBuilder.setView(checkboxLayout);
			checkOrari = (CheckBox) checkboxLayout.findViewById(R.id.orari);

			helpBuilder.setPositiveButton("Filtra!",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// Do nothing but close the dialog

							if (checkOrari.isChecked()) {
								if (listfilteredPlaceDetails == null
										|| listfilteredPlaceDetails.isEmpty() == true) {
									Toast.makeText(
											getApplicationContext(),
											"Purtroppo non ci sono punti di interesse aperti attualmente!",
											Toast.LENGTH_LONG).show();
								} else
									filter();
							} else
								Toast.makeText(
										getApplicationContext(),
										"Non è stato selezionato alcun filtro.",
										Toast.LENGTH_SHORT).show();
						}
					});
		}

		// Remember, create doesn't show the dialog
		AlertDialog helpDialog = helpBuilder.create();
		helpDialog.show();

	}

	/**
	 * Background Async Task to Load Google places
	 * */
	class LoadPlaces extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ResultActivity.this);
			pDialog.setMessage(Html
					.fromHtml("<b>Ricerca</b><br/>Sto caricando i Punti di Interesse...."));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Places JSON
		 * */
		protected String doInBackground(String... args) {
			// android.os.Debug.waitForDebugger();//debug

			// creating Places class object

			try {
				// GooglePlaces googlePlaces = new GooglePlaces(
				// GOOGLE_PLACES_API_KEY);
				// PlacesResult result = null;
				// PlaceDetails details = null;
				// DetailsResult detailsResult = null;
				// places = null;

				locationManager = (LocationManager) ResultActivity.this
						.getSystemService(LOCATION_SERVICE);
				location = locationManager
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				if (latitude == 0)
					latitude = location.getLatitude();
				if (longitude == 0)
					longitude = location.getLongitude();

				if (latitude == 0 && longitude == 0) {
					locationManager = (LocationManager) ResultActivity.this
							.getSystemService(LOCATION_SERVICE);
					location = locationManager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					latitude = location.getLatitude();
					longitude = location.getLongitude();

					LocationManager myLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
					Location location = myLocationManager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					latitude = location.getLatitude();
					longitude = location.getLongitude();
				}

				// GooglePlaces gp = new GooglePlaces(GOOGLE_PLACES_API_KEY);

				// Result result = gp.getNearbyPlaces(type, "pizza", radius,
				// latitude, longitude);
				// PlacesResult placesList = (PlacesResult) result.getResults();

				// placesList.results.get(0).getName();

				// int i=placesList.results.size();

				// if (result.getStatusCode() == StatusCode.OK) {
				// List<Place> placesList = (List<Place>) result.getResults();

				// }

				// save query
				q.setLatitude(latitude);
				q.setLongitude(longitude);
				q.setRadius(radius);
				q.setType(type);

				long idquery = db.createQuery(q);

				// get user data from session
				HashMap<String, String> user = session.getUserDetails();

				// name
				String username = user.get(SessionManager.KEY_SESSION_USERNAME);

				User u = db.getUser(username);

				long iduser = u.getID();

				long idqueryuser = db.createUserQuery(iduser, idquery);

				ForecastIO FIO = new ForecastIO(
						"YOUR_FORECAST_IO_API_KEY", latitude, longitude);

				// ability to set the units, exclude blocks, extend options
				// and // user agent for the request. This is not required.
				HashMap<String, String> requestParam = new HashMap<String, String>();
				// requestParam.put("units", "us");
				requestParam.put("units", "si");
				requestParam.put("userAgent", "Custom User Agent 1.0");
				FIO.setRequestParams(requestParam);
				FIO.makeRequest();

				String responseString = FIO.getResponseString();
				ForecastIOResponse FIOR = new ForecastIOResponse(responseString);

				// The library provides an easy way to access values as
				// strings // and data points as a list. String currentSummary =
				// FIOR.getValue("current-summary");

				temperature = FIOR.getValue("hourly-2-temperature");

				// firstDailyIcon = FIOR.getValue("daily-0-icon"); GIORNALIERO
				// // (rain, // sleet, // snow, // fog, // wind)
				firstDailyIcon = FIOR.getValue("currently-icon");

				gP = new GooglePlaces("YOUR_GOOGLE_PLACES_API_KEY");

				try {
					// result = googlePlaces.getPlaces("type", "mcdonald's",
					// radius, latitude, longitude); // type, radius, lat,
					// lon

					// CIBO
					if (tipo.equals("salato")) {
						result = gP.getNearbyPlaces(type, "pizza", radius,
								latitude, longitude); // type, radius,
														// lat,
														// lon
					} else if (tipo.equals("pizza")) {
						result = gP.getNearbyPlaces(type, "pizzeria", radius,
								latitude, longitude); // type, radius,
														// lat,
														// lon
					} else if (tipo.equals("occidentale")) {
						result = gP.getNearbyPlaces(type, "messicano", radius,
								latitude, longitude); // type, radius,
														// lat,
														// lon
					} else if (tipo.equals("orientale")) {
						result = gP.getNearbyPlaces(type, "japanese", radius,
								latitude, longitude); // type, radius, lat,
														// lon
					}

					// CULTURA
					else if (type.equals("Basilica San Nicola")) {
						result = gP.getNearbyPlaces("church", type, radius,
								latitude, longitude);
					} else if (type.equals("piazza")) {
						NearbySearchQuery query = new NearbySearchQuery(
								"YOUR_GOOGLE_PLACES_API_KEY",
								latitude, longitude);
						query.setKeyword("monumento");
						query.setKeyword("piazza"); // prende solo una
						result = gP.getPlaces(query); // type, radius,
														// lat,
						// lon
					} else if (tipo.equals("orientali")) {
						result = gP.getNearbyPlaces(types, radius, latitude,
								longitude); // type, radius, lat,
											// lon
					} else if (type.equals("castello")) {
						NearbySearchQuery query = new NearbySearchQuery(
								"YOUR_GOOGLE_PLACES_API_KEY",
								latitude, longitude);
						query.setKeyword("castello");
						result = gP.getPlaces(query); // type, radius,
														// lat,
						// lon
					}

					// SVAGO
					else if (tipo.equals("giardini")) {
						result = gP.getNearbyPlaces(type, "giardini", radius,
								latitude, longitude); // type, radius,
														// lat,
														// lon
					} else if (tipo.equals("animali")) {
						NearbySearchQuery query = new NearbySearchQuery(
								"YOUR_GOOGLE_PLACES_API_KEY",
								latitude, longitude);
						query.setKeyword("zoo");
						result = gP.getPlaces(query);

						// result = gP.getNearbyPlaces(types, radius, latitude,
						// longitude); // type, radius, lat,
						// lon
					} else if (type.equals("spiaggia")) {
						NearbySearchQuery query = new NearbySearchQuery(
								"YOUR_GOOGLE_PLACES_API_KEY",
								latitude, longitude);
						query.setKeyword("spiaggia");
						result = gP.getPlaces(query); // type, radius,
														// lat,
						// lon
					} else if (tipo.equals("shopping")) {
						NearbySearchQuery query = new NearbySearchQuery(
								"YOUR_GOOGLE_PLACES_API_KEY",
								latitude, longitude);
						query.addType("shopping_mall");
						// query.setKeyword("ipercoop");
						// query.setKeyword("mongolfiera");
						// query.setKeyword("bariblu");
						result = gP.getPlaces(query); // type, radius,
														// lat,
						// lon
					} else if (tipo.equals("nightclub")) {
						NearbySearchQuery query = new NearbySearchQuery(
								"YOUR_GOOGLE_PLACES_API_KEY",
								latitude, longitude);
						query.addType("night_club");
						// query.setKeyword("disco");
						result = gP.getPlaces(query); // type, radius,
														// lat,
						// lon
					} else if (tipo.equals("giochi")) {
						NearbySearchQuery query = new NearbySearchQuery(
								"YOUR_GOOGLE_PLACES_API_KEY",
								latitude, longitude);
						query.addType("amusement_park");
						result = gP.getPlaces(query); // type, radius,
														// lat,
						// lon
					} else {
						result = gP.getNearbyPlaces(type, "", radius, latitude,
								longitude); // ?????
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (result.getStatusCode() == StatusCode.OK) {
					Place generalPlace;

					int distCounter = 0;

					int counterPlaces = 0; // il limite dovrebbe essere comunque
											// di 20
					Iterator placeIterator = result.getResults().iterator();
					while (placeIterator.hasNext() && counterPlaces != 25) {
						generalPlace = (Place) placeIterator.next();
						placesList.add(generalPlace);
						counterPlaces++;
						String reference = generalPlace.getReference();
						Result resultdetails = gP.getPlaceDetails(reference);
						if (resultdetails.getStatusCode() == StatusCode.OK) {
							PlaceDetails detailsResult = null;
							if (resultdetails.getResults().get(0) != null) {
								detailsResult = (PlaceDetails) resultdetails
										.getResults().get(0);
								listPlaceDetails.add(detailsResult);

								String add = detailsResult
										.getFormattedAddress();
								Geocoder geocoder = new Geocoder(
										ResultActivity.this);
								List<Address> addresses;
								addresses = geocoder
										.getFromLocationName(add, 1);
								if (addresses.size() > 0) {
									float[] resultsDistance = new float[5];
									double latitudePlace = addresses.get(0)
											.getLatitude();
									double longitudePlace = addresses.get(0)
											.getLongitude();
									location.distanceBetween(latitude,
											longitude, latitudePlace,
											longitudePlace, resultsDistance);
									listDistancesPlace[distCounter] = resultsDistance[0];
									distCounter++;

								}

							}
						}
					}

					if (listPlaceDetails != null) {

						// int counterfilter = 0;
						Iterator placeIterator2 = listPlaceDetails.iterator();
						while (placeIterator2.hasNext()) {

							PlaceDetails generalPlaceDetails;
							generalPlaceDetails = (PlaceDetails) placeIterator2
									.next();

							// prendi orario se c'è
							Boolean opennow = false;
							if (generalPlaceDetails.getOpeningHours() != null) {
								opennow = generalPlaceDetails.getOpeningHours().open_now;
								if (opennow == true)
									listfilteredPlaceDetails
											.add(generalPlaceDetails);
							}
						}
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {

			if (food.contains(tipo) || tipo.equals("spiaggia")
					|| tipo.equals("giardini") || tipo.equals("parchi")
					|| tipo.equals("giochi") || tipo.equals("animali")
					|| tipo.equals("sport") || tipo.equals("nightclub")
					|| tipo.equals("arte") || tipo.equals("musei")
					|| tipo.equals("biblioteca"))
				btnShowOthers.setVisibility(View.INVISIBLE);
			else
				btnShowOthers.setVisibility(View.VISIBLE);

			btnFilter.setVisibility(View.VISIBLE);

			// if (food.contains(tipo))
			// btnShowOthers.setVisibility(View.INVISIBLE); else
			// btnShowOthers.setVisibility(View.VISIBLE);

			// dismiss the dialog after getting all products
			pDialog.dismiss();

			if (firstDailyIcon != null) {
				if (firstDailyIcon.equals("clear-day")
						|| firstDailyIcon.equals("clear-night"))
					meteo = "Giorno Sereno";
				// else if (firstDailyIcon.equals("clear-night"))
				// meteo="Notte Serena";
				else if (firstDailyIcon.equals("rain"))
					meteo = "Pioggia";
				else if (firstDailyIcon.equals("snow"))
					meteo = "Neve";
				else if (firstDailyIcon.equals("sleet"))
					meteo = "Grandine";
				else if (firstDailyIcon.equals("wind"))
					meteo = "Vento";
				else if (firstDailyIcon.equals("fog"))
					meteo = "Nebbia";
				else if (firstDailyIcon.equals("cloudy"))
					meteo = "Nuvoloso";
				else if (firstDailyIcon.equals("partly-cloudy-day")
						|| firstDailyIcon.equals("partly-cloudy-night"))
					meteo = "Parzialmente Nuvoloso";
				// else if (firstDailyIcon.equals("partly-cloudy-night"))
				// meteo=" Parzialmente Nuvolosa";

				double temperatureD = Double.valueOf(temperature).doubleValue();

				txtTemp.setText("Temp.: " + (int) (Math.round(temperatureD))
						+ "°C");

				txtMeteo.setText("Meteo: " + meteo);
				if (meteo.equals("Neve") || meteo.equals("Pioggia")
						|| meteo.equals("Grandine") || meteo.equals("Nebbia"))
					txtMeteo.setTextColor(Color.RED);
			}

			if (tipo.equals("salato"))
				deduzione = "Mi hai detto di voler mangiare qualcosa di leggero e di tipo salato, ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("dolce"))
				deduzione = "Mi hai detto di voler mangiare qualcosa di leggero e di tipo dolce, ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("pizza"))
				deduzione = "Mi hai detto che hai bambini con te e di voler un pasto completo. In quanto a quasi nessun bambino non piace la pizza, ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("italiano"))
				deduzione = "Mi hai detto di voler un pasto completo e che non ti piace la cucina etnica, quindi ecco i tipici ristoranti presenti nelle vicinanze!";
			if (tipo.equals("orientale"))
				deduzione = "Mi hai detto che non hai bambini con te e di voler un pasto completo di tipo etnico, quindi ecco i ristoranti orientali presenti nelle vicinanze!";
			if (tipo.equals("occidentale"))
				deduzione = "Mi hai detto che non hai bambini con te e di voler un pasto completo di tipo etnico, quindi ecco i ristoranti occidentali presenti nelle vicinanze!";

			if (tipo.equals("arte"))
				deduzione = "Mi hai detto che l'arte potrebbe interessarti, quindi ecco alcune gallerie d'arte nelle tue vicinanze!";
			if (tipo.equals("musei"))
				deduzione = "Mi hai detto che i musei potrebbero interessarti, quindi ecco alcuni musei nelle tue vicinanze!";
			if (tipo.equals("biblioteca"))
				deduzione = "Mi hai detto che le biblioteche potrebbero interessarti, quindi ecco alcune biblioteche nelle tue vicinanze!";
			if (tipo.equals("occidentali"))
				deduzione = "Mi hai detto che la religione occidentale potrebbe interessarti, quindi ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("orientali"))
				deduzione = "Mi hai detto che la religione orientale potrebbe interessarti, quindi ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("sannicola"))
				deduzione = "Mi hai detto che la storia di San Nicola potrebbe interessarti, quindi ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("chiese"))
				deduzione = "Mi hai detto che la religione cristiana potrebbe interessarti, quindi ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("castelli"))
				deduzione = "Mi hai detto che i castelli potrebbero interessarti, quindi ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("monumenti"))
				deduzione = "Mi hai detto che i monumenti potrebbero interessarti, quindi ecco cosa ho trovato per te nelle vicinanze!";

			if (tipo.equals("shopping"))
				deduzione = "Mi hai detto che vorresti divertirti, ho dedotto che a quasi tutte le donne piace fare shopping, quindi ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("spa"))
				deduzione = "Mi hai detto che vorresti rilassarti, ho dedotto che a quasi tutte le donne piacciono i centri benessere, quindi ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("spiaggia"))
				deduzione = "Mi hai detto che vorresti rilassarti e che le spiagge potrebbero interessarti, quindi ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("giardini"))
				deduzione = "Mi hai detto che vorresti rilassarti e che dei giardinetti potrebbero interessarti, quindi ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("parchi"))
				deduzione = "Mi hai detto che vorresti rilassarti e che dei parchi potrebbero interessarti, quindi ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("giochi"))
				deduzione = "Mi hai detto che hai bambini con te che vogliono divertirsi giocando, quindi ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("animali"))
				deduzione = "Mi hai detto che hai bambini con te che vogliono divertirsi ammirando degli animali, quindi ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("sport"))
				deduzione = "Mi hai detto che vorresti divertirti e che ti piace lo sport, quindi ecco cosa ho trovato per te nelle vicinanze!";
			if (tipo.equals("nightclub"))
				deduzione = "Mi hai detto che vorresti divertirti, quindi ecco alcuni night clubs e discoteche nelle tue vicinanze!";

			if (result != null) {
				showOkAlertDialog("Spiegazione", deduzione, "Ok");
			} else
				showYNAlertDialog(
						"Attenzione!",
						"Il dispositivo non è connesso ad internet oppure il sensore GPS non è riuscito a localizzare la tua posizione...",
						"Ok");

			final ListView lv1 = getListView();

			if (placesList == null || placesList.isEmpty() == true) {

				if (result != null) {
					if (result.getStatusCode() == StatusCode.OverQueryLimit)
						showYNAlertDialog(
								"Attenzione!",
								"Purtroppo è stato superato il limite di richieste a Google Places! Riprovare più tardi...",
								"Ok");

					else if (result.getStatusCode() == StatusCode.ZeroResults)
						showYNAlertDialog(
								"Attenzione!",
								"Purtroppo non è stato trovato nessun Punto di Interesse a cui potresti essere interessato nelle tue vicinanze!",
								"Ok");
				}
			}

			lv1.setAdapter(new CustomListAdapterPlaces(ResultActivity.this,
					placesList, listDistancesPlace));

			lv1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> a, View v, int position,
						long id) {
					// DEVE APRIRE RESULTACTIVITY CON TUTTI I POI.

					Object o = lv1.getItemAtPosition(position);
					Place somePlace = (Place) o;

					Intent eventinfo = new Intent(getApplicationContext(),
							PlaceInfoActivity.class);

					// Sending user current geo location
					// eventinfo.putExtra("place_latitude",
					// Double.toString(somePlace.getLatitude()));
					// eventinfo.putExtra("place_longitude",
					// Double.toString(somePlace.getLongitude()));

					Bundle b = new Bundle();
					b.putDouble("latitude",
							somePlace.getGeometry().location.lat);
					b.putDouble("longitude",
							somePlace.getGeometry().location.lng);
					eventinfo.putExtras(b);

					eventinfo.putExtra("title", somePlace.getName());

					Object o2 = listPlaceDetails.get(position);
					PlaceDetails somePlaceDetails = (PlaceDetails) o2;

					String website;
					website = somePlaceDetails.getWebsite();

					if (somePlaceDetails != null) {
						if (website == null)
							website = "Sito Web non disponibile";
						eventinfo.putExtra("description",
								somePlaceDetails.getUrl());
						eventinfo.putExtra("website", website);
						eventinfo.putExtra("where",
								somePlaceDetails.getFormattedAddress());
						eventinfo.putExtra("telephone",
								somePlaceDetails.getFormattedPhoneNumber());
					}

					startActivity(eventinfo);

				}

			});

		}

	}

	public void filter() {

		final ListView lv1 = getListView();

		lv1.setAdapter(new CustomListAdapterFilteredPlaceDetails(
				ResultActivity.this, listfilteredPlaceDetails));

		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {

				Object o = lv1.getItemAtPosition(position);
				Place somePlace = (Place) o;

				Intent eventinfo = new Intent(getApplicationContext(),
						PlaceInfoActivity.class);

				Bundle b = new Bundle();
				b.putDouble("latitude", somePlace.getGeometry().location.lat);
				b.putDouble("longitude", somePlace.getGeometry().location.lng);
				eventinfo.putExtras(b);

				eventinfo.putExtra("title", somePlace.getName());

				Object o2 = listfilteredPlaceDetails.get(position);
				PlaceDetails somePlaceDetails = (PlaceDetails) o2;

				String website;
				website = somePlaceDetails.getWebsite();

				if (somePlaceDetails != null) {
					if (website == null)
						website = "Sito Web non disponibile";
					eventinfo.putExtra("description", somePlaceDetails.getUrl());
					eventinfo.putExtra("website", website);
					eventinfo.putExtra("where",
							somePlaceDetails.getFormattedAddress());
					eventinfo.putExtra("telephone",
							somePlaceDetails.getFormattedPhoneNumber());
				}

				startActivity(eventinfo);

			}

		});

	}

	public void showYNAlertDialog(String title, String message,
			final String option1) {

		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(option1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						finish();

					}
				});
		builder.show().setCanceledOnTouchOutside(false); // per non chiudere se
															// si tappa fuori
	}

	public void showOkAlertDialog(String title, String message,
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
