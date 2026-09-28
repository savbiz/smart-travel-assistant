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

public class FunJessActivity extends Activity {

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

	String fileNameGiovaneUomo = "SvagoGiovaneUomo.clp";
	String fileNameAdultoUomo = "SvagoAdultoUomo.clp";
	String fileNameGiovaneDonna = "SvagoGiovaneDonna.clp";
	String fileNameAdultoDonna = "SvagoAdultoDonna.clp";

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

		String sex = i.getStringExtra("sex");
		int age = Integer.parseInt(i.getStringExtra("age"));

		if (age >= 25 && sex.equals("M")) {
			f = new File(baseDir + File.separator + "/clp/"
					+ fileNameAdultoUomo);
		}

		if (age < 25 && sex.equals("M")) {
			f = new File(baseDir + File.separator + "/clp/"
					+ fileNameGiovaneUomo);
		}

		if (age >= 25 && sex.trim().equals("F")) {
			f = new File(baseDir + File.separator + "/clp/"
					+ fileNameAdultoDonna);
		}

		if (age < 25 && sex.trim().equals("F")) {
			f = new File(baseDir + File.separator + "/clp/"
					+ fileNameGiovaneDonna);
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
			} else if (Q.question.equals("A cosa si è più propensi?")) {
				showYNAlertDialog("", Q.question, "Relax", "Divertimento");
			} else if (Q.question.equals("A cosa si è interessati?")) {
				showYNAlertDialog("", Q.question, "SpazioVerde", "Spiaggia");
			} else if (Q.question
					.equals("Si è interessati a giardini comunali o ad grandi parchi?")) {
				showYNAlertDialog("", Q.question, "Giardini", "Parchi");
			} else if (Q.question
					.equals("Cosa si pensa potrebbe divertire di più i bambini?")) {
				showYNAlertDialog("", Q.question, "Giochi", "Animali");
			} else

			{
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

			if (Q.question.equals("shopping")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("spa")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("spiaggia")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("giardini")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("parchi")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("giochi")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("animali")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("sport")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("nightclub")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			}

			// RAGGIO

			if (Q.question.equals("1km")) {
				raggiobool = true;
				raggio = Q.question;

				results = new Intent(this, ResultActivity.class);

				results.putExtra("type", type);
				results.putExtra("raggio", raggio);
				startActivity(results);

				this.onDestroy();
				System.exit(RESULT_OK);
			}

			if (Q.question.equals("2km")) {
				raggiobool = true;
				raggio = Q.question;

				results = new Intent(this, ResultActivity.class);

				results.putExtra("type", type);
				results.putExtra("raggio", raggio);
				startActivity(results);

				this.onDestroy();
				System.exit(RESULT_OK);
			}

			if (Q.question.equals("10km")) {
				raggiobool = true;
				raggio = Q.question;

				results = new Intent(this, ResultActivity.class);

				results.putExtra("type", type);
				results.putExtra("raggio", raggio);
				startActivity(results);

				this.onDestroy();
				System.exit(RESULT_OK);
			}

			if (Q.question.equals("50km")) {
				raggiobool = true;
				raggio = Q.question;

				results = new Intent(this, ResultActivity.class);

				results.putExtra("type", type);
				results.putExtra("raggio", raggio);
				startActivity(results);

				this.onDestroy();
				System.exit(RESULT_OK);
			}

			if (Q.question.equals("100km")) {
				raggiobool = true;
				raggio = Q.question;

				results = new Intent(this, ResultActivity.class);

				results.putExtra("type", type);
				results.putExtra("raggio", raggio);
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