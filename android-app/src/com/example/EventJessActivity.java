package com.example;

import java.io.File;

import jess.JessException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import expertsystem.Logic;
import expertsystem.Question;

public class EventJessActivity extends Activity {

	private static final int LENGTH_SHORT = 0;

	public Logic logic;

	private TextView tv;
	private Button bt;

	public String result;
	Question Q;

	String baseDir = Environment.getExternalStorageDirectory()
			.getAbsolutePath();
	// UNICO MODO PERCHE' NON CONFRONTA I FATTI CREATI DIRETTAMENTE DA JAVA E
	// NON BATCHA DUE FILE DIVERSI NELL'ENGINE

	String quandoeventi;
	String comeeventi;
	String fileEventiMagg = "EventiMaggiorenne.clp";
	String fileEventiMin = "EventiMinorenne.clp";

	File f;

	public void onBackPressed() {
		Log.d("CDA", "onBackPressed Called");
		Intent setIntent = new Intent(this, MenuActivity.class);
		setIntent.addCategory(Intent.CATEGORY_HOME);
		setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(setIntent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Log.e("JESS.Smart Travel Assistant", "onCreate");

		System.setProperty("java.bean", "com.googlecode.openbeans");
		System.setProperty("java.bean.beancontext",
				"com.googlecode.openbeans.beancontext");
		Log.e("JESS.Smart Travel Assistant", "onInit");

		Log.e("JESS.Smart Travel Assistant", "making file");

		// Getting intent data
		Intent i = getIntent();

		int age = Integer.parseInt(i.getStringExtra("age"));

		if (age >= 18) {
			// FileMaggiorenne Uomo
			f = new File(baseDir + File.separator + "/clp/" + fileEventiMagg);
		} else if (age < 18) {
			// FileMinorenne Uomo
			f = new File(baseDir + File.separator + "/clp/" + fileEventiMin);
		}

		Log.e("JESS.Smart Travel Assistant", "File made");

		// logic = new Logic(f);
		logic = new Logic(f);

		result = "Start";

		try {
			this.run();
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() throws JessException {
		Q = null;

		if (logic.hasNext())// if a diagnosis has not yet been given
			Q = logic.logicOp(result);// get the next question/diagnosis
		else
			Q = null;

		if (Q == null) {

			// PASSA A GOOGLE PLACES PASSANDO LA STRINGA DEL RISULTATO CON LA
			// CATEGORIA E SE E' AUTOMUNITO
			// UTILIZZA DATA, ORA, (ORARIO APERTURA) METEO,
		}

		if (Q.questionType == Question.Type.YN) {
			if (Q.question.equals("prova")) {
				result = "Start";
				run();
			} else if (Q.question
					.equals("Fino a quanto sei disposto a camminare?")) {
				showYNAlertDialog("", Q.question, "1km", "2km");
			} else if (Q.question.equals("Quanto si è disposti a guidare?")) {
				showYNAlertDialog("", Q.question, "50km", "100km");
			} else if (Q.question
					.equals("Si è interessati ad eventi che si stanno tenendo ora o che si terranno in futuro?")) {
				showYNAlertDialogFuturoOra("", Q.question, "Ora", "Futuro");
			} else if (Q.question.equals("Con chi si viaggia?")) {
				showYNAlertDialog("", Q.question, "Solo", "Con Altri");
			} else {
				showYNAlertDialog("", Q.question, "Si", "No");
			}

		} else if (Q.questionType == Question.Type.number) {
			if (Q.question.equals("prova")) {
				result = "Start";
				run();
			}
		}

		else if (Q.questionType == Question.Type.SA) {
			// MOSTRA UI GRAFICA PER DOMANDE SOLO/ALTRI
			if (Q.question.equals("prova")) {
				result = "Start";
				run();
			}
		}

		else if (Q.questionType == Question.Type.statement) {
			// NON E' UNA DOMANDA QUINDI SETTO RESULT A START COSI' RIPRENDE LA
			// RICERCA

			// EVENTI
			boolean quandoev = false;

			if (Q.question.equals("prova")) {
				result = "Start";
				run();
			} else if (Q.question.equals("ora")) {
				quandoeventi = Q.question;
				result = "Start";
				run();
				quandoev = true;

			} else if (Q.question.equals("futuro")) {
				quandoeventi = Q.question;
				result = "Start";
				run();
				quandoev = true;
			}

			if (Q.question.equals("apiedi")) {
				comeeventi = Q.question;

				Intent events = new Intent(this, EventActivity.class);

				events.putExtra("quandoeventi", quandoeventi);
				events.putExtra("comeeventi", comeeventi);
				startActivity(events);
				this.onDestroy();
				System.exit(RESULT_OK);
			}

			if (Q.question.equals("50km")) {
				comeeventi = Q.question;

				Intent events = new Intent(this, EventActivity.class);

				events.putExtra("quandoeventi", quandoeventi);
				events.putExtra("comeeventi", comeeventi);
				startActivity(events);
				this.onDestroy();
				System.exit(RESULT_OK);
			}

			if (Q.question.equals("100km")) {
				comeeventi = Q.question;

				Intent events = new Intent(this, EventActivity.class);

				events.putExtra("quandoeventi", quandoeventi);
				events.putExtra("comeeventi", comeeventi);
				startActivity(events);
				this.onDestroy();
				System.exit(RESULT_OK);
			}
			//

			result = "Start";
			run();
		}

		else if (Q.questionType == Question.Type.terminate)// risultati
		{

			// RESTITUISCE I POI

			this.onDestroy();
			System.exit(RESULT_OK);
		}

		Log.e("prova", result);

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
						if (option1.equals("Sì")) {
							result = "si";
							dialog.cancel();
						} else if (option1.equals("50km")) {
							result = "cinquantakm";
							dialog.cancel();
						} else {
							result = option1.toLowerCase(); // per solo
							dialog.cancel();
						}
						try {
							run();
						} catch (JessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

		builder.setNeutralButton(option2,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						if (option2.equals("100km")) {
							result = "centokm";
							dialog.cancel();
						} else if (option2.equals("Con Altri")) {
							result = "altri";
							dialog.cancel();
						} else {
							result = option2.toLowerCase();
							dialog.cancel();
						}
						try {
							run();
						} catch (JessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		builder.show().setCanceledOnTouchOutside(false); // per non chiudere se
															// si tappa fuori
	}

	// OPZIONI A DUE RISPOSTE
	public void showYNAlertDialogFuturoOra(String title, String message,
			final String option1, final String option2) {

		Builder builder = new AlertDialog.Builder(this);
		// builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(option1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						result = option1.toLowerCase(); // per solo
						dialog.cancel();

						try {
							run();
						} catch (JessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

		builder.setNeutralButton(option2,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						result = option2.toLowerCase();
						dialog.cancel();
						try {
							run();
						} catch (JessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		builder.show().setCanceledOnTouchOutside(false); // per non chiudere se
															// si tappa fuori
	}

}