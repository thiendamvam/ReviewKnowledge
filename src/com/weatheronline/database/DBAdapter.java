package com.weatheronline.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAdapter {

	// Database table
	public static final String TABLE_WEATHER = "TABLE_WEATHER";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_STATUS = "COLUMN_STATUS";
	public static final String COLUMN_MIN = "COLUMN_MIN";
	public static final String COLUMN_MAX = "COLUMN_MAX";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_WEATHER + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_MIN
			+ " text not null, " + COLUMN_MAX + " text not null,"
			+ COLUMN_STATUS + " text not null" + ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(DBAdapter.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);
		onCreate(database);
	}
}