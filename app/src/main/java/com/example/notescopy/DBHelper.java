package com.example.notescopy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

//    public DBHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
//        super(context, name, version, openParams);
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table UserNotes(title TEXT primary key, content TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists UserNotes");
    }

    public Boolean insertuserdata(String title, String content) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("content", content);
        long setDataBaseValues = database.insert("UserNotes", null, contentValues);
        if (setDataBaseValues == -1) {
            return false;
        }
        return true;
    }

    public Boolean updateuserdata(String title, String content) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);
        Cursor cursor = database.rawQuery("Select * from UserNotes where title = ?", new String[]{title});
        if (cursor.getCount() > 0) {
            long setDataBaseValues = database.update("UserNotes", contentValues, "title=?", new String[]{title});
            if (setDataBaseValues == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deleteuserdata(String title) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from UserNotes where title = ?", new String[]{title});
        if (cursor.getCount() > 0) {
            long setDataBaseValues = database.delete("UserNotes","title=?", new String[]{title});
            if (setDataBaseValues == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getData() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from UserNotes", null);
        return cursor;
    }

    public Cursor getAllData(String key) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery("Select * from UserNotes where title = ?", new String[]{key});
    }

}
