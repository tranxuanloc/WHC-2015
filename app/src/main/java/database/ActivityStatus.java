package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ActivityStatus extends SQLiteOpenHelper {
	 // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "ActivityStatusDatabase";
 
    // Todo table name
    private static final String TABLE_TODO = "ActivityStatus";
 
    // Todo Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "Name";
    private static final String KEY_Status = "Status";
 
    public ActivityStatus(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating our initial tables
    // These is where we need to write create table statements. 
    // This is called when database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
    	// Construct a table for todo items
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_Status + " INTEGER" + ")";
        db.execSQL(CREATE_TODO_TABLE);
    }
 
    // Upgrading the database between versions
    // This method is called when database is upgraded like modifying the table structure, 
    // adding constraints to database, etc
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	if (newVersion == 1) {
           // Wipe older tables if existed
           db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
           // Create tables again
           onCreate(db);
    	}
    }
    
    
    // Insert record into the database
    public void addItem(ItemActivity item) {
    	// Open database connection
        SQLiteDatabase db = this.getWritableDatabase();
        // Define values for each field
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName()); 
        values.put(KEY_Status, item.getStatus()); 
        // Insert Row
        db.insertOrThrow(TABLE_TODO, null, values);
        db.close(); // Closing database connection
    }
    
    // Returns a single todo item by id
    public ItemActivity getItemByID(int id) {
    	// Open database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Construct and execute query
        Cursor cursor = db.query(TABLE_TODO,  // TABLE
        		new String[] { KEY_ID, KEY_NAME, KEY_Status }, // SELECT 
        		KEY_ID + "= ?", new String[] { String.valueOf(id) },  // WHERE, ARGS
        		null, null, "id ASC", "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
        if (cursor != null)
            cursor.moveToFirst();
        // Load result into model object
        ItemActivity item = new ItemActivity(cursor.getString(1), cursor.getInt(2));
        item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
        // Close the cursor
        if (cursor != null)
            cursor.close();
        // return todo item
        return item;
    }
    

    
    // Returns a single todo item by id
    public ItemActivity getItemByName(String Name) {
    	// Open database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Construct and execute query
        Cursor cursor = db.query(TABLE_TODO,  // TABLE
        		new String[] { KEY_ID, KEY_NAME, KEY_Status }, // SELECT 
        		KEY_NAME + "= ?", new String[] { String.valueOf(Name) },  // WHERE, ARGS
        		null, null, "id ASC", "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
        if (cursor != null)
            cursor.moveToFirst();
        // Load result into model object
        ItemActivity item = new ItemActivity(cursor.getString(1), cursor.getInt(2));
        item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
        item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_NAME)));
        item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_Status)));
        // Close the cursor
        if (cursor != null)
            cursor.close();
        // return todo item
        return item;
    }
    
    
    
    public int getItemCountAll() {
        String countQuery = "SELECT  * FROM " + TABLE_TODO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        // Close the cursor
        cursor.close();
        // return count
        return cursor.getCount();
    }
    
    public int getItemCountByName(String pName) {
    	  SQLiteDatabase db = this.getReadableDatabase();
          // Construct and execute query
          Cursor cursor = db.query(TABLE_TODO,  // TABLE
          		new String[] { KEY_ID, KEY_NAME, KEY_Status }, // SELECT 
          		KEY_NAME + "= ?", new String[] { String.valueOf(pName) },  // WHERE, ARGS
          		null, null, "id ASC", "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
          if (cursor != null)
              cursor.moveToFirst();
          // Load result into model object
          // Close the cursor
          if (cursor != null)
              cursor.close();
          // return todo item
          return cursor.getCount();
    }
    public int UpdateItemByID(ItemActivity item) {
    	// Open database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Setup fields to update
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_Status, item.getStatus());
        // Updating row
        int result = db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
        // Close the database
        db.close();
        return result;
    }
    
    public int UpdateItemByName(String _Name, int _Status ) {
    	// Open database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Setup fields to update
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, _Name);
        values.put(KEY_Status, _Status);
        // Updating row
        int result = db.update(TABLE_TODO, values, KEY_NAME + " = ?",
                new String[] { String.valueOf(_Name) });
        // Close the database
        db.close();
        return result;
    }
    
    
    public void deleteItem(ItemActivity item) {
    	// Open database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the record with the specified id
        db.delete(TABLE_TODO, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
        // Close the database
        db.close();
    }
    
    
   
    
    // End Class
}

