package com.example.user.zzzmitview.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteConnector extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "highscores";

    private static final String TABLE_SCORES = "scores";
    private static final String SCORE_ID = "id";
    private static final String SCORE_SPIELER = "title";
    private static final String SCORE_PUNKTE = "stufe";
    private static final String SCORE_GESAMTPUNKTE = "datum";

    private final SQLiteDatabase database;

    public SQLiteConnector(Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_SCORES + " (" +
                        SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        SCORE_SPIELER + " INTEGER NOT NULL, " +
                        SCORE_PUNKTE + " INTEGER NOT NULL, " +
                        SCORE_GESAMTPUNKTE + " INTEGER NOT NULL" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    @Override
    public synchronized void close() {
        super.close();
        database.close();
    }

    public long insert(int spielerCount, int punkte, int gesamtpunkte) {
        ContentValues values = new ContentValues();
        values.put(SCORE_SPIELER, spielerCount);
        values.put(SCORE_PUNKTE, punkte);
        values.put(SCORE_GESAMTPUNKTE, gesamtpunkte);
        return database.insert(TABLE_SCORES, null, values);
    }

    public Score[] getScores() {
        Cursor cursor = database.query(TABLE_SCORES, new String[]{SCORE_ID, SCORE_SPIELER, SCORE_PUNKTE, SCORE_GESAMTPUNKTE}, null, null, null, null, SCORE_ID);

        Score[] scores = new Score[cursor.getCount()];
        int i = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext(), i++) {
            scores[i] = new Score(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3));
        }
        cursor.close();

        return scores;
    }
}
