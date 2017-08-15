package net.egordmitriev.cheatsheets.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by egordm on 15-8-2017.
 */

public class RegistryDbHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "cs_registry.db";
	
	public RegistryDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			db.execSQL(RegistryContract.SQL_CREATE_CATEGORIES);
			db.execSQL(RegistryContract.SQL_CREATE_CHEAT_SHEETS);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.beginTransaction();
		try {
			db.execSQL(deleteQuery(RegistryContract.CheatSheetEntry.TABLE_NAME));
			db.execSQL(deleteQuery(RegistryContract.CategoryEntry.TABLE_NAME));
			onCreate(db);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
	
	public static String deleteQuery(String table) {
		return "DROP TABLE IF EXISTS " + table;
	}
}
