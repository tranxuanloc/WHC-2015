/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserLogin extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "scanDB";

	// Login table name
	private static final String TABLE_LOGIN = "loginScan";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_FullNAME = "fullname";
	private static final String KEY_POSITIONGROUP = "PositionGroup";

	public UserLogin(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_FullNAME + " TEXT," + KEY_POSITIONGROUP + " TEXT)";
		db.execSQL(CREATE_LOGIN_TABLE);

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void adduser(String Pname, String Pfullname,String pPositionGroup) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, Pname);
		values.put(KEY_FullNAME, Pfullname);
		values.put(KEY_POSITIONGROUP, pPositionGroup);
		// Inserting Row
		db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection
	}

	// public void update_user(String name) {
	// SQLiteDatabase db = this.getWritableDatabase();
	// ContentValues values = new ContentValues();
	// values.put(KEY_NAME, name);
	// db.update(TABLE_LOGIN, values, KEY_NAME + " = " + name, null);
	// db.close(); // Closing database connection
	// }

	//
	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		return rowCount;
	}

	//
	public String getUser() {
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE "
				+ KEY_ID + " = '1'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.moveToFirst();
		String getname = cursor.getString(1);
		db.close();
		cursor.close();
		return getname;
	}
	public String getPositionGroup() {
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE "
				+ KEY_ID + " = '1'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.moveToFirst();
		String getname = cursor.getString(3);
		db.close();
		cursor.close();
		return getname;
	}
	
	 public String getFullName() {
	 String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
	 SQLiteDatabase db = this.getReadableDatabase();
	 Cursor cursor = db.rawQuery(countQuery, null);
	 cursor.moveToFirst();
	 String rowCount = cursor.getString(2);
	 db.close();
	 cursor.close();
	
	 return rowCount;
	 }
	

	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_LOGIN, null, null);
		db.close();
	}

}
