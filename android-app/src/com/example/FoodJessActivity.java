package com.example;

import java.io.File;

import expertsystem.Logic;
import expertsystem.Question;
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

public class FoodJessActivity extends Activity {

	public Logic logic;

	public String result;

	boolean raggiobool;
	boolean poibool;
	public String raggio;

	public String type;

	Intent results;

	Question Q;

	String baseDir = Environment.getExternalStorageDirectory()
			.getAbsolutePath();

	String fileNameGiovane = "CiboGiovane.clp";
	String fileNameAdulto = "CiboAdulto.clp";

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

		if (age >= 25) {
			// FileMaggiorenne Uomo
			f = new File(baseDir + File.separator + "/clp/" + fileNameAdulto);
		}

		if (age < 25) {
			// FileMinorenne Uomo
			f = new File(baseDir + File.separator + "/clp/" + fileNameGiovane);
		}

		Log.e("JESS.Smart Travel Assistant", "File made");

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

		if (Q.questionType == Question.Type.YN) { // A DUE OPZIONI
			if (Q.question.equals("prova")) {
				result = "Start";
				run();
			} else if (Q.question
					.equals("Fino a quanto si è disposti a camminare?")) {
				showYNAlertDialog("", Q.question, "1km", "2km");
			} else if (Q.question
					.equals("Fino a quanto si è disposti a guidare?")) {
				showYNAlertDialog("", Q.question, "50km", "100km");
			} else if (Q.question.equals("Cosa si preferisce?")) {
				showYNAlertDialog("", Q.question, "Dolce", "Salato");
			} else if (Q.question
					.equals("Si desidera un pasto completo o qualcosa di leggero?")) {
				showYNAlertDialog("", Q.question, "Pasto", "Leggero");
			} else if (Q.question
					.equals("Si è interessati alla cucina occidentale od orientale?")) {
				showYNAlertDialog("", Q.question, "Occidentale", "Orientale");
			} else {
				showYNAlertDialog("", Q.question, "Si", "No");
			}

		}

		else if (Q.questionType == Question.Type.statement) {
			// NON E' UNA DOMANDA QUINDI SETTO RESULT A START COSI' RIPRENDE
			// L'ATTIVAZIONE DELLE REGOLE

			// POI SVAGO
			raggiobool = false;

			// if (Q.question.equals("prova")) {
			// result = "Start";
			// run();
			// } else

			// TIPO POI
			if (Q.question.equals("salato")) {
				type = Q.question;

				results = new Intent(this, ResultActivity.class);
				results.putExtra("type", type);
				results.putExtra("raggio", "2km");
				startActivity(results);

				this.onDestroy();
				System.exit(RESULT_OK);
			} else if (Q.question.equals("dolce")) {
				type = Q.question;

				results = new Intent(this, ResultActivity.class);
				results.putExtra("type", type);
				results.putExtra("raggio", "2km");
				startActivity(results);

				this.onDestroy();
				System.exit(RESULT_OK);
			} else if (Q.question.equals("pizza")) {
				type = Q.question;

				results = new Intent(this, ResultActivity.class);
				results.putExtra("type", type);
				results.putExtra("raggio", "2km");
				startActivity(results);

				this.onDestroy();
				System.exit(RESULT_OK);
			} else if (Q.question.equals("italiano")) {
				type = Q.question;

				results = new Intent(this, ResultActivity.class);
				results.putExtra("type", type);
				results.putExtra("raggio", "2km");
				startActivity(results);

				this.onDestroy();
				System.exit(RESULT_OK);
			} else if (Q.question.equals("orientale")) {
				type = Q.question;

				results = new Intent(this, ResultActivity.class);
				results.putExtra("type", type);
				results.putExtra("raggio", "2km");
				startActivity(results);

				this.onDestroy();
				System.exit(RESULT_OK);
			} else if (Q.question.equals("occidentale")) {
				type = Q.question;

				results = new Intent(this, ResultActivity.class);
				results.putExtra("type", type);
				results.putExtra("raggio", "2km");
				startActivity(results);

				this.onDestroy();
				System.exit(RESULT_OK);

			}

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
						} else if (option1.equals("1km")) {
							result = "unkm";
							dialog.cancel();
						} else {
							result = option1.toLowerCase(); // per solo
							dialog.cancel();
						}
						try {
							run();
						} catch (JessException e) {
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
						} else if (option2.equals("2km")) {
							result = "duekm";
							dialog.cancel();
						} else {
							result = option2.toLowerCase();
							dialog.cancel();
						}
						try {
							run();
						} catch (JessException e) {
							e.printStackTrace();
						}
					}
				});
		builder.show().setCanceledOnTouchOutside(false); // per non chiudere se
															// si tappa fuori
	}

}