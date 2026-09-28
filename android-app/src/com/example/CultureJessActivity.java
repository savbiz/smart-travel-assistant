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
import android.widget.ImageView;

public class CultureJessActivity extends Activity {

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

	String fileNameGiovane = "CulturaGiovane.clp";
	String fileNameAdulto = "CulturaAdulto.clp";

	File f;

	ImageView img;

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
			f = new File(baseDir + File.separator + "/clp/" + fileNameAdulto);
		}

		if (age < 25) {
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

		if (logic.hasNext())
			Q = logic.logicOp(result);
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

			} else if (Q.question
					.equals("A quale tipo di cultura si è interessati?")) {
				showYNAlertDialog("", Q.question, "Storica", "Religiosa");
			} else if (Q.question
					.equals("Si è interessati a religioni occidentali o orientali?")) {
				showYNAlertDialog("", Q.question, "Occidentali", "Orientali");
			} else if (Q.question
					.equals("Si è interessati a castelli o monumenti?")) {
				showYNAlertDialog("", Q.question, "Castelli", "Monumenti");
			} else if (Q.question
					.equals("A quale tipo di cultura, invece, si è interessati?")) {
				showYNAlertDialog("", Q.question, "Storica", "Religiosa");
			} else

			{
				showYNAlertDialog("", Q.question, "Si", "No");
			}

		}

		else if (Q.questionType == Question.Type.number) {
			if (Q.question.equals("prova")) {
				result = "Start";
				run();
			} else if (Q.question
					.equals("A quale tipo di attrazioni si è interessati?")) {
				showThreeAlertDialog("", Q.question, "Musei", "Arte", "Altro");
			}
		}

		else if (Q.questionType == Question.Type.statement) {
			// NON E' UNA DOMANDA QUINDI SETTO RESULT A START COSI' RIPRENDE
			// L'ATTIVAZIONE DELLE REGOLE

			// POI CULTURA
			raggiobool = false;

			// if (Q.question.equals("prova")) {
			// result = "Start";
			// run();
			// } else

			// TIPO POI

			if (Q.question.equals("arte")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("musei")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("biblioteca")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("occidentali")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("orientali")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("sannicola")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("chiese")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("castelli")) {
				type = Q.question;
				result = "Start";
				poibool = true;
				run();
			} else if (Q.question.equals("monumenti")) {
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

	// OPZIONI A TRE RISPOSTE
	public void showThreeAlertDialog(String title, String message,
			final String option1, final String option2, final String option3) {

		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(option1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						result = option1.toLowerCase();
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

		builder.setNegativeButton(option3,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						if (option3.equals("Altro")) {

							String res = "nessunadiqueste";
							result = res;
							// result = option3.toLowerCase()
							// .replaceAll("\\s", "");
							dialog.cancel();
						} else {
							result = option3.toLowerCase();
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

}