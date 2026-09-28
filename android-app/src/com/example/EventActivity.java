package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.semifinaltravelassistant.R;

public class EventActivity extends ListActivity {

	ArrayList<String> filteredheadlines;
	ArrayList<String> filtereddescriptions;
	ArrayList<String> filteredwhere;
	ArrayList<Double> filteredlatitude;
	ArrayList<Double> filteredlongitude;
	boolean yeah;

	LocationManager locationManager;
	Location location;
	double latitude; // latitude
	double longitude; // longitude

	String luogo, citta = "";

	// Progress dialog
	ProgressDialog pDialog;

	List headlines;
	List descriptions;
	List links;
	ArrayAdapter<String> adapter;
	String quandoeventi;

	float distanza = 0;

	int soglia = 100; // timeout
	int i = 0;

	boolean trovati = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_queries);

		// Getting intent data
		Intent inte = getIntent();
		String comeeventi = inte.getStringExtra("comeeventi");
		quandoeventi = inte.getStringExtra("quandoeventi");

		if (comeeventi.equals("50km"))
			distanza = 50000;
		if (comeeventi.equals("100km"))
			distanza = 100000;
		if (comeeventi.equals("apiedi"))
			distanza = 1500;

		// Initializing instance variables
		headlines = new ArrayList();
		links = new ArrayList();
		descriptions = new ArrayList();

		new MyTask().execute();

	}

	protected void onListItemClick(ListView l, View v, int position, long id) {

		Intent eventinfo = new Intent(getApplicationContext(),
				EventInfoActivity.class);

		Bundle b = new Bundle();
		b.putDouble("latitude", filteredlatitude.get(position));
		b.putDouble("longitude", filteredlongitude.get(position));
		eventinfo.putExtras(b);

		eventinfo.putExtra("title", filteredheadlines.get(position));
		eventinfo.putExtra("description", filtereddescriptions.get(position));
		eventinfo.putExtra("where", filteredwhere.get(position));
		// eventinfo.putExtra("latitude",
		// filteredlatitude.get(position).doubleValue());
		// eventinfo.putExtra("longitude",
		// filteredlongitude.get(position).doubleValue());

		startActivity(eventinfo);

	}

	class MyTask extends AsyncTask<Void, Void, Void> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EventActivity.this);
			pDialog.setMessage(Html
					.fromHtml("<b>Ricerca Eventi</b><br/>Sto filtrando gli eventi in zona... Potrebbe volerci un po'..."));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				URL url = new URL(
						"http://www.pugliaevents.it/it/feeds/province/0");

				XmlPullParserFactory factory = XmlPullParserFactory
						.newInstance();
				factory.setNamespaceAware(false);
				XmlPullParser xpp = factory.newPullParser();

				// We will get the XML from an input stream

				// xpp.setInput(getInputStream(url), "UTF_8");
				xpp.setInput(url.openConnection().getInputStream(), "UTF_8");

				/*
				 * We will parse the XML content looking for the "<title>" tag
				 * which appears inside the "<item>" tag. However, we should
				 * take in consideration that the rss feed name also is enclosed
				 * in a "<title>" tag. As we know, every feed begins with these
				 * lines: "<channel><title>Feed_Name</title>...." so we should
				 * skip the "<title>" tag which is a child of "<channel>" tag,
				 * and take in consideration only "<title>" tag which is a child
				 * of "<item>"
				 * 
				 * In order to achieve this, we will make use of a boolean
				 * variable.
				 */
				boolean insideItem = false;

				// Returns the type of current event: START_TAG, END_TAG, etc..
				int eventType = xpp.getEventType();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					if (eventType == XmlPullParser.START_TAG) {

						if (xpp.getName().equalsIgnoreCase("item")) {
							insideItem = true;
						} else if (xpp.getName().equalsIgnoreCase("title")) {
							if (insideItem)
								headlines.add(xpp.nextText()); // extract the
																// headline
						} else if (xpp.getName()
								.equalsIgnoreCase("description")) {
							if (insideItem)
								descriptions.add(xpp.nextText());
						} else if (xpp.getName().equalsIgnoreCase("link")) {
							if (insideItem)
								links.add(xpp.nextText()); // extract the link
															// of
															// article
						}
					} else if (eventType == XmlPullParser.END_TAG
							&& xpp.getName().equalsIgnoreCase("item")) {
						insideItem = false;
					}

					eventType = xpp.next(); // move to next element
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			filteredheadlines = new ArrayList<String>();
			filtereddescriptions = new ArrayList<String>();
			filteredwhere = new ArrayList<String>();
			filteredlatitude = new ArrayList<Double>();
			filteredlongitude = new ArrayList<Double>();

			int timeout = 0;

			Iterator iteratorheadlines = (Iterator) headlines.iterator();
			Iterator iterator = (Iterator) descriptions.iterator();
			while (iteratorheadlines.hasNext()) {
				while (iterator.hasNext()) {

					String headlinesfilt = (String) iteratorheadlines.next();
					String des = (String) iterator.next();
					String descripfilt = des;
					String descrizioneiniziale = des.substring(0,
							des.indexOf("."));

					String[] destrattino = descrizioneiniziale.split("-");
					String data = destrattino[0];

					String[] datadamanipolare = data.split("\\s+");

					String giorno = "";
					String mese = "";
					String anno = "";
					String anno1 = "";
					String anno2 = "";
					String mesedef = "";

					if (datadamanipolare.length == 8) {
						giorno = datadamanipolare[1];
						mese = datadamanipolare[2];
						anno1 = datadamanipolare[3];

						if (anno1.equals("2012") || anno1.equals("2013")
								|| anno1.equals("2014") || anno1.equals("2015")
								|| anno1.equals("2016") || anno1.equals("2017")) {
							anno = anno1;
						}
					} else if (datadamanipolare.length == 7) {
						giorno = datadamanipolare[1];
						mese = datadamanipolare[2];
						anno2 = datadamanipolare[6];

						if (anno2.equals("2012") || anno2.equals("2013")
								|| anno2.equals("2014") || anno2.equals("2015")
								|| anno2.equals("2016") || anno2.equals("2017")) {
							anno = anno2;
						}

					} else {
						giorno = datadamanipolare[0];
						mese = datadamanipolare[1];
						anno = datadamanipolare[2];
					}

					String s;
					if (giorno.equals("24"))
						s = "test";

					if (mese.equals("gennaio"))
						mesedef = "01";
					if (mese.equals("febbraio"))
						mesedef = "02";
					if (mese.equals("marzo"))
						mesedef = "03";
					if (mese.equals("aprile"))
						mesedef = "04";
					if (mese.equals("maggio"))
						mesedef = "05";
					if (mese.equals("giugno"))
						mesedef = "06";
					if (mese.equals("luglio"))
						mesedef = "07";
					if (mese.equals("agosto"))
						mesedef = "08";
					if (mese.equals("settembre"))
						mesedef = "09";
					if (mese.equals("ottobre"))
						mesedef = "10";
					if (mese.equals("novembre"))
						mesedef = "11";
					if (mese.equals("dicembre"))
						mesedef = "12";

					SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");

					String dateevent = (giorno + "-" + mesedef + "-" + anno);
					String todayAsString = sd.format(Calendar.getInstance()
							.getTime());

					Date today = null;
					Date dateeventfinal = null;
					try {
						dateeventfinal = sd.parse(dateevent);
						today = sd.parse(todayAsString);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					int cond = today.compareTo(dateeventfinal);

					if (destrattino[1] != "" || destrattino[1] != " "
							|| destrattino[1] != "  ")
						luogo = destrattino[1];
					if (destrattino.length > 2)
						citta = destrattino[2];

					locationManager = (LocationManager) EventActivity.this
							.getSystemService(LOCATION_SERVICE);
					location = locationManager
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					if (location != null) {
						latitude = location.getLatitude();
						longitude = location.getLongitude();

						Geocoder geocoder = new Geocoder(EventActivity.this);
						List<Address> addresses = null;

						if (citta != "") {
							try {
								addresses = geocoder.getFromLocationName(citta,
										1);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							if (addresses != null && addresses.size() != 0) {
								yeah = false;
								double latitudeSearch = 0;
								double longitudeSearch = 0;
								if (addresses.get(0) != null) {
									yeah = false;
									latitudeSearch = addresses.get(0)
											.getLatitude();
									longitudeSearch = addresses.get(0)
											.getLongitude();

									float[] result = new float[5];
									location.distanceBetween(latitude,
											longitude, latitudeSearch,
											longitudeSearch, result);

									if (result[0] + 10000 <= distanza) { // 50km
																			// -
																			// aggiungo
																			// 10km
																			// circa
																			// perché
																			// la
																			// funzione
																			// restituisce
																			// distanza
																			// in
																			// linea
																			// d'aria

										if (quandoeventi.equals("ora")
												&& cond >= 0) { // vecchi
																// o
																// ora

											filtereddescriptions
													.add(descripfilt);
											filteredwhere.add(luogo);
											filteredlatitude
													.add(latitudeSearch);
											filteredlongitude
													.add(longitudeSearch);
											trovati = true;
											yeah = true;
										} else if (quandoeventi
												.equals("futuro") && cond < 0) {
											filtereddescriptions
													.add(descripfilt);
											filteredwhere.add(luogo);
											filteredlatitude
													.add(latitudeSearch);
											filteredlongitude
													.add(longitudeSearch);
											trovati = true;
											yeah = true;
										}

									}
								}
								addresses.clear();
							}
							if (yeah) {
								filteredheadlines.add(headlinesfilt);
								yeah = false;
								i++;
								if (i == 15)
									break;
							}

						}
					}
					timeout++;
					if (timeout == soglia)
						break;
				}
				if (i == 15)
					break;
				if (timeout == soglia)
					break;
			}

			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Void result) {

			// dismiss the dialog after getting all products
			pDialog.dismiss();

			if (location == null)
				showYNAlertDialog(
						"Attenzione!",
						"Il dispositivo non è connesso ad internet oppure il sensore GPS non è riuscito a localizzare la tua posizione...",
						"Ok");

			if (trovati == true) {// Binding data
				adapter = new ArrayAdapter(EventActivity.this,
						android.R.layout.simple_list_item_1, filteredheadlines);
				setListAdapter(adapter);
			}
			if (trovati == false)
				showYNAlertDialog(
						"Attenzione! Purtroppo non è stato trovato nessun evento!",
						"Vorresti cercare tra più eventi? Richiederà più tempo...",
						"Va bene!", "No, grazie.");

		}

	}

	public InputStream getInputStream(URL url) {
		try {
			return url.openConnection().getInputStream();
		} catch (IOException e) {
			return null;
		}
	}

	// OPZIONI A DUE RISPOSTE
	public void showYNAlertDialog(String title, String message,
			final String option1, final String option2) {

		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(option1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						soglia = 200;
						new MyTask().execute();

					}
				});

		builder.setNeutralButton(option2,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						finish();

					}
				});
		builder.show().setCanceledOnTouchOutside(false); // per non chiudere se
															// si tappa fuori
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

}
