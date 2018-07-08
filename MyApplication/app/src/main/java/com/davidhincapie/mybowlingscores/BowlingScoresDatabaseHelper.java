package com.davidhincapie.mybowlingscores;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by davidhincapie on 10/18/16.
 */

public class BowlingScoresDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "MyBowlingScores.SQLite";
    public static final int DB_VERSION = 1;
    public static String BOWLING_SCORES_TABLE = "BowlingScoresTable";
    public static String RECORD_ID = "ID";
    public static String DATE = "Date";
    public static String GAME1 = "Game1";
    public static String GAME2 = "Game2";
    public static String GAME3 = "Game3";


    public BowlingScoresDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase bowlingScoresDB) {
        String sqlStatement = "create table " + BOWLING_SCORES_TABLE
                + " (" + RECORD_ID + " integer primary key autoincrement not null,"
                + DATE + " integer,"
                + GAME1 + " integer,"
                + GAME2 + " integer,"
                + GAME3 + " integer"
                + ");";
        Log.d("Bowling Database", sqlStatement);

        bowlingScoresDB.execSQL(sqlStatement);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
