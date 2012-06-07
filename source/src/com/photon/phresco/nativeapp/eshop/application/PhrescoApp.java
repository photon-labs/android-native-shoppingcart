/*
 * Classname: PhrescoApp
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.photon.phresco.nativeapp.eshop.db.AppConfiguration;
import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 * Base class to maintain global application state. This class will be
 * instantiated when the process for your application/package is created.
 *
 * @author viral_b
 *
 */
public class PhrescoApp extends Application {
	private static final String TAG = "PhrescoApp ***** ";
	private static final String DB_NAME = "Phresco";
	private static final int DB_VERSION = 1;

	// AppConfig table
	private static final String CONFIG_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ AppConfiguration.DATABASE_TABLE
			+ " (_id integer primary key autoincrement, "
			+ "metafield text not null," + "metavalue text not null);";

	private SQLiteDatabase writeableDB;

	/**
	 * Create the writable database object to be used to read and write data to
	 * @return SQlite database object
	 */
	public SQLiteDatabase getDatabase() {
		if (writeableDB == null) {
			try {
				writeableDB = new DatabaseHelper(this).getWritableDatabase();
			} catch (Exception ex) {
				PhrescoLogger.info(TAG + "Could not create or open database : "
						+ ex);
				PhrescoLogger.warning(ex);
			}
		}
		return writeableDB;
	}

	/**
	 * Private class to handle the SQLite database related operations like
	 * create or update the database
	 * @author viral_b
	 *
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CONFIG_TABLE_CREATE);
				PhrescoLogger.info(TAG + "onCreate fired ");
			} catch (Exception ex) {
				PhrescoLogger.info(TAG + "onCreate - Exception : " + ex);
				PhrescoLogger.warning(ex);
			}
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				db.execSQL("DROP TABLE IF EXISTS "
						+ AppConfiguration.DATABASE_TABLE);

				PhrescoLogger.info(TAG + "onUpgrade fired ");

				onCreate(db);
			} catch (Exception ex) {
				PhrescoLogger.info(TAG + "onUpgrade - Exception : " + ex);
				PhrescoLogger.warning(ex);
			}
		}

	}
}
