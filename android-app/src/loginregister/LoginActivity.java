package loginregister;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MenuActivity;
import com.example.semifinaltravelassistant.R;

import database.DatabaseHandler;
import database.User;

public class LoginActivity extends Activity {

	// Alert Dialog Manager
	AlertDialogManagerLoginRegister alert = new AlertDialogManagerLoginRegister();

	// Email, password edittext
	EditText txtUsername, txtPassword;
	TextView registerScreen;

	// login button
	Button btnLogin;

	// Session Manager Class
	SessionManager session;

	List<User> listUsers;
	User userFound = null;
	DatabaseHandler db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setting default screen to login.xml

		setContentView(R.layout.login);

		db = new DatabaseHandler(this);

		// Session Manager
		session = new SessionManager(getApplicationContext());

		txtUsername = (EditText) findViewById(R.id.editusername);
		txtPassword = (EditText) findViewById(R.id.editpassword);

		// Login button
		btnLogin = (Button) findViewById(R.id.btnLogin);

		registerScreen = (TextView) findViewById(R.id.link_to_register);

		// Getting all users

		listUsers = db.getAllUsers();

		// get user data from session
		HashMap<String, String> user = session.getUserDetails();

		// name
		String username = user.get(SessionManager.KEY_SESSION_USERNAME);

		// password
		String password = user.get(SessionManager.KEY_SESSION_PASSWORD);

		if (username != "" && password != "") {
			for (User userPrimo : listUsers) {
				// delete query
				userFound = db.getUser(username);
			}

			if (userFound != null && userFound.getPassword().equals(password)) {

				if (userFound.getSex().equals("F")) {
					Toast.makeText(getApplicationContext(),
							"Benvenuta " + userFound.getName() + "!",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"Benvenuto " + userFound.getName() + "!",
							Toast.LENGTH_SHORT).show();
				}

				// Starting MenuActivity
				Intent menu = new Intent(getApplicationContext(),
						MenuActivity.class);
				startActivity(menu);
				finish();

			}
		}

		// Login button click event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get username, password from EditText
				String username = txtUsername.getText().toString();
				String password = txtPassword.getText().toString();

				// Check if username, password is filled
				if (username.trim().length() > 0
						&& password.trim().length() > 0) {

					// delete all query
					for (User user : listUsers) {
						// delete query
						userFound = db.getUser(username);
					}

					if (userFound != null
							&& userFound.getPassword().equals(password)) {

						// Creating user login session
						// For testing i am storing username, password as follow
						// Use user real data
						session.createLoginSession(username, password);

						if (userFound.getSex().equals("M")) {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									LoginActivity.this);
							builder.setMessage(
									"Benvenuto " + userFound.getName() + "!")
									.setCancelable(false)
									.setIcon(R.drawable.success)
									.setPositiveButton(
											"OK",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
													// Starting MainActivity
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
							AlertDialog.Builder builder = new AlertDialog.Builder(
									LoginActivity.this);
							builder.setMessage(
									"Benvenuta " + userFound.getName() + "!")
									.setCancelable(false)
									.setIcon(R.drawable.success)
									.setPositiveButton(
											"OK",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
													// Starting MainActivity
													Intent menu = new Intent(
															getApplicationContext(),
															MenuActivity.class);
													startActivity(menu);
													finish();

												}
											});
							AlertDialog alert = builder.create();
							alert.show();
						}

					}

					else {
						// username / password doesn't match
						alert.showAlertDialog(LoginActivity.this,
								"Login fallito...",
								"Username/Password non sono corretti!", false);
					}
				} else {
					// user didn't entered username or password
					// Show alert asking him to enter the details
					alert.showAlertDialog(LoginActivity.this,
							"Login fallito...",
							"Prego, inserisci username e password", false);
				}

			}
		});

		// Listening to register new account link
		registerScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
			}
		});
	}
}