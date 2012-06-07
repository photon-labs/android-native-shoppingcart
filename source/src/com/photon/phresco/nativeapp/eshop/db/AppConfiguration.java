/*
 * Classname: SplashActivity
 * Version information: 1.0
 * Date: Nov 24, 2011
 * Copyright notice:
 */
package com.photon.phresco.nativeapp.eshop.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.photon.phresco.nativeapp.eshop.logger.PhrescoLogger;

/**
 * Application configuration table in SQLite database on device
 *
 * @author viral_b
 *
 */
public class AppConfiguration {

	private static final String TAG = "AppConfiguration ********";
	public static final String DATABASE_TABLE = "appconfig";

	public static final String KEY_ROWID = "_id";
	public static final String KEY_META_FIELD = "metafield";
	public static final String KEY_META_VALUE = "metavalue";

	private SQLiteDatabase mDatabase;

	public AppConfiguration(SQLiteDatabase mDB) {
		this.mDatabase = mDB;
	}

	/**
	 * Insert the new row in database
	 *
	 * @param name
	 *            value of metafield column
	 * @param value
	 *            value of metavalue column
	 * @return
	 */
	public long createRow(String name, String value) {
		long result = 0;
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_META_FIELD, name);
		initialValues.put(KEY_META_VALUE, value);
		try {
			result = mDatabase.insert(DATABASE_TABLE, null, initialValues);
		} catch (Exception e) {
			Log.e(TAG, "sql error", e);
		}
		return result;
	}

	/**
	 * Delete the row whose row id is same as passing parameter
	 */
	public boolean deleteRow(long rowID) {
		return mDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowID, null) > 0;
	}

	/**
	 * Delete the all rows from datagase table
	 */
	public boolean deleteAllRows() {
		return mDatabase.delete(DATABASE_TABLE, "1", null) > 0;
	}

	/**
	 * Get all the rows
	 */
	public Cursor fetchAllRow() {
		return mDatabase.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_META_FIELD, KEY_META_VALUE }, null, null, null, null, null);
	}

	/**
	 * Get the row whose row id is same as passing parameter
	 */
	public Cursor fetchRow(long rowID) {
		Cursor result = mDatabase.query(DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_META_FIELD, KEY_META_VALUE }, KEY_ROWID + "="
				+ rowID, null, null, null, null);
		return result;
	}

	/**
	 * Get all the rows whose value is same as passing parameter
	 */
	public Cursor searchAllRow(String keyword)  {
		Cursor result = mDatabase.query(DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_META_FIELD, KEY_META_VALUE }, KEY_META_FIELD
				+ "='" + keyword + "'", null, null, null, null);
		return result;
	}

	/**
	 * Update the row in the database
	 *
	 * @param rowID
	 *            existing row id of record in database
	 * @param name
	 *            value of metafield column
	 * @param value
	 *            value of metavalue column
	 * @return boolean
	 */
	public boolean updateRow(long rowID, String name, String value) {
		boolean flag = false;
		try {
			PhrescoLogger.info(TAG + " - inside updateRow() : RowId: --> "
					+ rowID + ", Name: -->" + name + ", Value: -->" + value);
			ContentValues args = new ContentValues();
			args.put(KEY_META_FIELD, name);
			args.put(KEY_META_VALUE, value);
			if (mDatabase.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowID,
					null) > 0) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception ex) {
			PhrescoLogger.info(TAG + " - browseButton  - Exception : "
					+ ex.toString());
			PhrescoLogger.warning(ex);
		}
		return flag;
	}

	/**
	 * Get the no of records preset in this table
	 */
	public int recordCount() {
		int result = 0;

		result = fetchAllRow().getCount();

		return result;
	}
}
