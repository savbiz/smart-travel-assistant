package database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "userManager";

	// Table names
	private static final String TABLE_USERS = "users";
	private static final String TABLE_QUERIES = "queries";
	private static final String TABLE_USER_QUERIES = "user_queries";

	// Common column names
	private static final String KEY_ID = "id";
	private static final String KEY_CREATED_AT = "created_at";

	// USERS Table - column names
	private static final String KEY_NAME = "name";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_AGE = "age";
	private static final String KEY_SEX = "sex";

	// QUERIES Table - column names
	private static final String KEY_TYPE = "type";
	private static final String KEY_RADIUS = "radius";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_KEYWORD = "keyword";
	private static final String KEY_CATEGORY = "category";

	// USER_QUERIES Table - column names
	private static final String KEY_USER_ID = "user_id";
	private static final String KEY_QUERY_ID = "query_id";

	private static final String CREATE_TABLE_USERS = "CREATE TABLE "
			+ TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
			+ " TEXT," + KEY_USERNAME + " TEXT," + KEY_PASSWORD + " TEXT,"
			+ KEY_AGE + " INTEGER," + KEY_SEX + " TEXT," + KEY_CREATED_AT
			+ " DATETIME" + ")";

	private static final String CREATE_TABLE_QUERIES = "CREATE TABLE "
			+ TABLE_QUERIES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TYPE
			+ " TEXT," + KEY_RADIUS + " INTEGER," + KEY_LATITUDE + " REAL,"
			+ KEY_LONGITUDE + " REAL," + KEY_KEYWORD + " TEXT," + KEY_CATEGORY
			+ " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";

	private static final String CREATE_TABLE_USER_QUERIES = "CREATE TABLE "
			+ TABLE_USER_QUERIES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_USER_ID + " INTEGER," + KEY_QUERY_ID + " INTEGER,"
			+ KEY_CREATED_AT + " DATETIME" + ")";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	// CREATE TABLE users(id INTEGER PRIMARY KEY,name TEXT,username
	// TEXT,password TEXT)
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_USERS);
		db.execSQL(CREATE_TABLE_QUERIES);
		db.execSQL(CREATE_TABLE_USER_QUERIES);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUERIES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_QUERIES);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// ------------------------ "users" table methods ----------------//

	// Create user
	// public long createUser(User user, long[] query_ids) {
	public void createUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, user.getName()); // User Name
		values.put(KEY_USERNAME, user.getUsername()); // User Username
		values.put(KEY_PASSWORD, user.getPassword()); // User Password
		values.put(KEY_AGE, user.getAge()); // User Password
		values.put(KEY_SEX, user.getSex()); // User Password
		values.put(KEY_CREATED_AT, getDateTime()); // User date&time creation

		// Inserting Row
		long user_id = db.insert(TABLE_USERS, null, values);

		/*
		 * // Assigning Query to User for (long query_id : query_ids) {
		 * createUserQuery(user_id, query_id); }
		 */

		// db.close(); // Closing database connection
	}

	// Getting single user
	// public User getUser(long user_id) {
	public User getUser(String username) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE "
				+ KEY_USERNAME + " = '" + username + "'";
		// +KEY_ID +user_id;
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor != null)
			cursor.moveToFirst();

		if (cursor.moveToFirst()) {

			User user = new User();
			user.setID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
			user.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
			user.setUsername(cursor.getString(cursor
					.getColumnIndex(KEY_USERNAME)));
			user.setPassword(cursor.getString(cursor
					.getColumnIndex(KEY_PASSWORD)));
			user.setAge(cursor.getInt(cursor.getColumnIndex(KEY_AGE)));
			user.setSex(cursor.getString(cursor.getColumnIndex(KEY_SEX)));
			user.setCreatedAt(cursor.getString(cursor
					.getColumnIndex(KEY_CREATED_AT)));
			cursor.close();
			return user;
		} else {
			cursor.close();
			// return contact
			return null;
		}
	}

	// Getting All Users
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<User>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_USERS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				User user = new User();
				user.setID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				user.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
				user.setUsername(cursor.getString(cursor
						.getColumnIndex(KEY_USERNAME)));
				user.setPassword(cursor.getString(cursor
						.getColumnIndex(KEY_PASSWORD)));
				user.setAge(cursor.getInt(cursor.getColumnIndex(KEY_AGE)));
				user.setSex(cursor.getString(cursor.getColumnIndex(KEY_SEX)));
				user.setCreatedAt(cursor.getString(cursor
						.getColumnIndex(KEY_CREATED_AT)));

				// Adding contact to list
				userList.add(user);

			} while (cursor.moveToNext());
		}

		cursor.close();
		// return user list
		return userList;
	}

	// Getting users Count
	public int getUsersCount() {
		String countUser = "SELECT  * FROM " + TABLE_USERS;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(countUser, null);

		int userCount = cursor.getCount();

		cursor.close();
		// return count
		return userCount;
	}

	/*
	 * // Updating single user public int updateUser(User user) { SQLiteDatabase
	 * db = this.getWritableDatabase();
	 * 
	 * ContentValues values = new ContentValues(); values.put(KEY_NAME,
	 * user.getName()); values.put(KEY_USERNAME, user.getUsername());
	 * values.put(KEY_PASSWORD, user.getPassword());
	 * 
	 * // updating row return db.update(TABLE_USERS, values, KEY_ID + " = ?",
	 * new String[] { String.valueOf(user.getID()) }); }
	 */

	// Deleting a query
	// public void deleteUser(User user, boolean should_delete_all_user_queries)
	// {
	public void deleteUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		// before deleting tag
		// check if query of this user should also be deleted
		// if (should_delete_all_user_queries) {
		// get all queries of this user
		List<Query> allQueries = getAllQueryByUser(user.getUsername());

		// delete all query
		for (Query query : allQueries) {
			// delete query
			deleteQuery(query.getID());
		}
		// }

		// now delete the tag
		db.delete(TABLE_USERS, KEY_ID + " = ?",
				new String[] { String.valueOf(user.getID()) });
	}

	// ------------------------ "queries" table methods ----------------//

	// Create query
	public long createQuery(Query query) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TYPE, query.getType()); // Query type
		values.put(KEY_RADIUS, query.getRadius()); // Query radius
		values.put(KEY_LATITUDE, query.getLatitude()); // Query latitude
		values.put(KEY_LONGITUDE, query.getLongitude()); // Query longitude
		values.put(KEY_KEYWORD, query.getKeyword()); // Query keyword
		values.put(KEY_CATEGORY, query.getCategory()); // Query category
		values.put(KEY_CREATED_AT, getDateTime()); // Query date&time creation

		// Inserting Row
		long query_id = db.insert(TABLE_QUERIES, null, values);

		// return query_id

		return query_id;

		// db.close(); // Closing database connection
	}

	// Create query
	// public long createQuery(Query query) {
	public long createQuery(long user_id, Query query) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TYPE, query.getType()); // Query query
		values.put(KEY_RADIUS, query.getRadius()); // Query radius
		values.put(KEY_LATITUDE, query.getLatitude()); // Query latitude
		values.put(KEY_LONGITUDE, query.getLongitude()); // Query longitude
		values.put(KEY_KEYWORD, query.getKeyword()); // Query keyword
		values.put(KEY_CATEGORY, query.getCategory()); // Query category
		values.put(KEY_CREATED_AT, getDateTime()); // Query date&time creation

		// Inserting Row
		long query_id = db.insert(TABLE_QUERIES, null, values);

		// return query_id

		return createUserQuery(user_id, query_id);

		// db.close(); // Closing database connection
	}

	// Getting single query
	public Query getQuery(long query_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_QUERIES + " WHERE "
				+ KEY_ID + " = " + query_id;

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor != null)
			cursor.moveToFirst();

		Query query = new Query();
		query.setID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		query.setType(cursor.getString(cursor.getColumnIndex(KEY_TYPE)));
		query.setRadius(cursor.getInt(cursor.getColumnIndex(KEY_RADIUS)));
		query.setLatitude(cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)));
		query.setLongitude(cursor.getDouble(cursor
				.getColumnIndex(KEY_LONGITUDE)));
		query.setKeyword(cursor.getString(cursor.getColumnIndex(KEY_KEYWORD)));
		query.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
		query.setCreatedAt(cursor.getString(cursor
				.getColumnIndex(KEY_CREATED_AT)));

		// return contact
		return query;
	}

	public List<Query> getAllQueries() {
		List<Query> queryList = new ArrayList<Query>();
		String selectQuery = "SELECT  * FROM " + TABLE_QUERIES;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Query query = new Query();
				query.setID(cursor.getInt((cursor.getColumnIndex(KEY_ID))));
				query.setType(cursor.getString(cursor.getColumnIndex(KEY_TYPE)));
				query.setRadius(cursor.getInt(cursor.getColumnIndex(KEY_RADIUS)));
				query.setLatitude(cursor.getDouble(cursor
						.getColumnIndex(KEY_LATITUDE)));
				query.setLongitude(cursor.getDouble(cursor
						.getColumnIndex(KEY_LONGITUDE)));
				query.setKeyword(cursor.getColumnName(cursor
						.getColumnIndex(KEY_KEYWORD)));
				query.setCategory(cursor.getString(cursor
						.getColumnIndex(KEY_CATEGORY)));

				// adding to tags list
				queryList.add(query);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return queryList;
	}

	// Getting queries Count
	public int getQueriesCount() {
		int countQueries = 0;
		String countQuery = "SELECT  * FROM " + TABLE_QUERIES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		countQueries = cursor.getCount();

		cursor.close();
		// return count
		return countQueries;
	}

	public List<Query> getAllQueryByUser(String username) {
		List<Query> queries = new ArrayList<Query>();

		String selectQuery = "SELECT * FROM " + TABLE_QUERIES + " query, "
				+ TABLE_USERS + " user, " + TABLE_USER_QUERIES
				+ " uq WHERE user." + KEY_USERNAME + " = '" + username + "'"
				+ " AND user." + KEY_ID + " = " + "uq." + KEY_USER_ID
				+ " AND query." + KEY_ID + " = " + "uq." + KEY_QUERY_ID;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Query query = new Query();
				query.setID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				query.setType(cursor.getString(cursor.getColumnIndex(KEY_TYPE)));
				query.setRadius(cursor.getInt(cursor.getColumnIndex(KEY_RADIUS)));
				query.setLatitude(cursor.getDouble(cursor
						.getColumnIndex(KEY_LATITUDE)));
				query.setLongitude(cursor.getDouble(cursor
						.getColumnIndex(KEY_LONGITUDE)));
				query.setKeyword(cursor.getColumnName(cursor
						.getColumnIndex(KEY_KEYWORD)));
				query.setCategory(cursor.getString(cursor
						.getColumnIndex(KEY_CATEGORY)));
				query.setCreatedAt(cursor.getString(cursor
						.getColumnIndex(KEY_CREATED_AT)));

				// adding to query list
				queries.add(query);
			} while (cursor.moveToNext());
		}

		cursor.close();

		return queries;
	}

	// Updating a query
	public int updateQuery(Query query) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TYPE, query.getType());
		values.put(KEY_RADIUS, query.getRadius());
		values.put(KEY_LATITUDE, query.getLatitude()); // Query latitude
		values.put(KEY_LONGITUDE, query.getLongitude()); // Query longitude
		values.put(KEY_KEYWORD, query.getKeyword()); // Query keyword
		values.put(KEY_CATEGORY, query.getCategory()); // Query category
		values.put(KEY_CREATED_AT, getDateTime()); // Query date&time creation

		// updating row
		return db.update(TABLE_QUERIES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(query.getID()) });
	}

	// Deleting single query
	public void deleteQuery(long query_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_QUERIES, KEY_ID + " = ?",
				new String[] { String.valueOf(query_id) });
		// db.close();
	}

	// ------------------------ "user_queries" table methods ----------------//
	public long createUserQuery(long user_id, long query_id) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, user_id);
		values.put(KEY_QUERY_ID, query_id);
		values.put(KEY_CREATED_AT, getDateTime());

		long id = db.insert(TABLE_USER_QUERIES, null, values);

		return id;
	}

	// ------------------------ support methods-------------------------------//
	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	// getDateTime
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd-MM-yyyy HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

}
