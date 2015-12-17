package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Version extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "scanDB";

	// Login table name
	private static final String TABLE_VERSION = "Version";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_DATE = "date";
	

	public Version(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_VERSION + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"  +KEY_DATE +" TEXT)";
		db.execSQL(CREATE_LOGIN_TABLE);

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VERSION);
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addVersion(String name, String pDate) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_DATE, pDate);
		// Inserting Row
		db.insert(TABLE_VERSION, null, values);
		db.close(); // Closing database connection
	}

	 public void updateVersion(String name) {
	 SQLiteDatabase db = this.getWritableDatabase();
	 ContentValues values = new ContentValues();
	 values.put(KEY_NAME, name);
	 db.update(TABLE_VERSION, values, KEY_NAME + " = " + name, null);
	 db.close(); // Closing database connection
	 }

	//
	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_VERSION;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		return rowCount;
	}

	//
	public String getVersion() {
		String countQuery = "SELECT  * FROM " + TABLE_VERSION + " WHERE "
				+ KEY_ID + " = '1'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.moveToFirst();
		String getname = cursor.getString(1);
		db.close();
		cursor.close();
		return getname;
	}

	// public String getName() {
	// String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
	// SQLiteDatabase db = this.getReadableDatabase();
	// Cursor cursor = db.rawQuery(countQuery, null);
	// cursor.moveToFirst();
	// String rowCount = cursor.getString(2);
	// db.close();
	// cursor.close();
	//
	// return rowCount;
	// }
	//

	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_VERSION, null, null);
		db.close();
	}

}
