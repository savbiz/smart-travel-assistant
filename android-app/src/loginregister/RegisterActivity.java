package loginregister;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.MenuActivity;
import com.example.semifinaltravelassistant.R;
import database.DatabaseHandler;
import database.User;

public class RegisterActivity extends Activity {

	// Alert Dialog Manager
	AlertDialogManagerLoginRegister alert = new AlertDialogManagerLoginRegister();

	// Name, username, password edittexts
	EditText txtName, txtUsername, txtPassword, txtEta;

	TextView loginScreen;

	// register button
	Button btnRegister;

	RadioGroup radioSexGroup;
	RadioButton radioSexButton;

	String sex;

	// Session Manager Class
	SessionManager session;

	DatabaseHandler db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set View to register.xml
		setContentView(R.layout.register);

		loginScreen = (TextView) findViewById(R.id.link_to_login);

		txtName = (EditText) findViewById(R.id.reg_name);
		txtUsername = (EditText) findViewById(R.id.reg_username);
		txtPassword = (EditText) findViewById(R.id.reg_password);
		txtEta = (EditText) findViewById(R.id.reg_eta);

		radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);

		// Register
		btnRegister = (Button) findViewById(R.id.btnRegister);

		db = new DatabaseHandler(this);

		// Login button click event
		btnRegister.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("DefaultLocale")
			@Override
			public void onClick(View arg0) {

				// Session Manager
				session = new SessionManager(getApplicationContext());

				// get selected radio button from radioGroup
				int selectedIdSex = radioSexGroup.getCheckedRadioButtonId();
				switch (selectedIdSex) // switch on the button selected
				{
				case R.id.radioButton3:
					radioSexButton = (RadioButton) findViewById(R.id.radioButton3);
					sex = (String) radioSexButton.getText();
					sex = sex.trim();
					break;

				case R.id.radioButton4:
					radioSexButton = (RadioButton) findViewById(R.id.radioButton4);
					sex = (String) radioSexButton.getText();
					sex = sex.trim();
					break;

				default:
					sex = "";
					break;
				}

				// Get name, username, password from EditText
				String name = txtName.getText().toString();
				String username = txtUsername.getText().toString();
				String password = txtPassword.getText().toString();

				// Check if username, password is filled
				if (username.trim().length() > 0 && name.trim().length() > 0
						&& password.trim().length() >= 6) {

					int age = Integer.parseInt(txtEta.getText().toString());
					User user = new User();

					if (age < 110 && age > 8) {
						user.setName(name);
						user.setUsername(username);
						user.setPassword(password);
						user.setAge(age);
						user.setSex(sex);

						db.createUser(user);

						// Creating user login session
						// For testing i am storing username, password as follow
						// Use user real data
						session.createLoginSession(username, password);

						AlertDialog.Builder builder = new AlertDialog.Builder(
								RegisterActivity.this);
						builder.setMessage("Ciao " + user.getName() + "!")
								.setIcon(R.drawable.success)
								.setCancelable(false)
								.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												// Staring MainActivity
												Intent menu = new Intent(
														getApplicationContext(),
														MenuActivity.class);
												startActivity(menu);
												finish();

											}
										});

						AlertDialog alert = builder.create();
						alert.show();
					} else {
						alert.showAlertDialog(RegisterActivity.this,
								"Registrazione fallita...",
								"L'età ammessa è compresa tra 8 e 110 anni!",
								false);
					}
				}

				else if (password.length() < 6) {
					alert.showAlertDialog(RegisterActivity.this,
							"Registrazione fallita...",
							"La password deve contenere almeno 6 caratteri!",
							false);
					txtPassword.setText("");
				} else {
					// user didn't entered username or password
					// Show alert asking him to enter the details
					alert.showAlertDialog(RegisterActivity.this,
							"Registrazione fallita...",
							"Prego, inserisci correttamente tutti i dati...",
							false);
				}

			}
		});

		// Listening to Login Screen link
		loginScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// Closing registration screen
				// Switching to Login Screen/closing register screen
				finish();
			}
		});
	}

}