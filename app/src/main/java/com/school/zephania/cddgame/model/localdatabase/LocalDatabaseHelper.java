package com.school.zephania.cddgame.model.localdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user0308 on 5/30/17.
 */

public class LocalDatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "myData.db";
    public static final String TABLE_NAME = "Data";

    public LocalDatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //
        String sql = "create table if not exists " + TABLE_NAME +
                " (username text primary key not null," +
                "competition integer," +
                "win integer," +
                "credit integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
