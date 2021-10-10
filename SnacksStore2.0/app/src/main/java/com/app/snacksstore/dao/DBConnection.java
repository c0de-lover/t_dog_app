package com.app.snacksstore.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBConnection extends SQLiteOpenHelper {
    // 数据库版本号
    private final static int DATABASE_VERSION = 1;
    // 数据库名
    private final static String DATABASE_NAME = "snacks_store.db";

    public DBConnection(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE cart" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR, " +
                "weight DOUBLE CHECK(weight>=0), " +
                "heat DOUBLE CHECK(heat>=0), " +
                "price DOUBLE CHECK(price>=0), " +
                "expire_date VARCHAR, " +
                "num INTEGER CHECK(num>=0)," +
                "checked INTEGER CHECK(checked in (0,1)))");
        db.execSQL("CREATE TABLE inventory" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR, " +
                "weight DOUBLE CHECK(weight>=0), " +
                "heat DOUBLE CHECK(heat>=0), " +
                "price DOUBLE CHECK(price>=0), " +
                "expire_date VARCHAR, " +
                "num INTEGER CHECK(num>=0)," +
                "checked INTEGER CHECK(checked in (0,1)))");
    }

    public SQLiteDatabase getConnection() {
        return getWritableDatabase();
    }

    public void close(SQLiteDatabase db) {
        db.close();
    }
}
