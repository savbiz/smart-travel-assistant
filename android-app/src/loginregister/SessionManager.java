package loginregister;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "SmartTravelAssistantPref";

	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";

	// User username (make variable public to access from outside)
	public static final String KEY_SESSION_USERNAME = "username";

	// User password (make variable public to access from outside)
	public static final String KEY_SESSION_PASSWORD = "password";

	// User choice for food (make variable public to access from outside)
	public static final String KEY_SESSION_FOOD = "food";

	// Constructor
	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	// Create login session
	public void createLoginSession(String username, String password) {
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);

		// Storing name in pref
		editor.putString(KEY_SESSION_USERNAME, username);

		// Storing email in pref
		editor.putString(KEY_SESSION_PASSWORD, password);

		// commit changes
		editor.commit();
	}

	// Create login session
	public void createFoodSession(String choice) {

		// Storing choice in pref
		editor.putString(KEY_SESSION_FOOD, choice);

		// commit changes
		editor.commit();
	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getFoodDetails() {
		HashMap<String, String> food = new HashMap<String, String>();
		// user name
		food.put(KEY_SESSION_FOOD, pref.getString(KEY_SESSION_FOOD, null));

		// return user
		return food;
	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.put(KEY_SESSION_USERNAME,
				pref.getString(KEY_SESSION_USERNAME, null));

		// user email id
		user.put(KEY_SESSION_PASSWORD,
				pref.getString(KEY_SESSION_PASSWORD, null));

		// return user
		return user;
	}

	// Check login method will check user login status
	// If false it will redirect user to login page
	// Else won't do anything

	public void checkLogin() {
		// Check login status
		if (!this.isLoggedIn()) {
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			_context.startActivity(i);
		}
	}

	// Clear session details
	public void logoutUser() {
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();

		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, LoginActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Staring Login Activity
		_context.startActivity(i);
	}

	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn() {
		return pref.getBoolean(IS_LOGIN, false);
	}
}
