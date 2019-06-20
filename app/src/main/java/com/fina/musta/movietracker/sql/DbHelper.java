package com.fina.musta.movietracker.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fina.musta.movietracker.model.User;

/**
 * Created by musta on 1/3/2018.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MovieManager.db";
    private static final String TABLE_USER = "user";
    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLMN_USER_ID = "user_id";
    private static final String COLMN_USER_NAME = "user_name";
    private static final String COLMN_USER_PASSWORD = "user_password";
    private static final String COLMN_USER_LIKED = "liked";
    private static final String COLMN_MOVIE_IMAGE = "movieimage";
    private static final String COLMN_MOVIE_OVERVIEW = "movieoverview";
    private static final String COLMN_MOVIE_RATING = "movierating";
    private static final String COLMN_MOVIE_ID = "movieid";
    private static final String COLMN_BACKDROP_PATH = "backdroppath";
    private static final String COLMN_RELEASE_DATE = "releasedate";
    private static final String COLMN_Poster_Path = "posterpath";



    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLMN_USER_NAME + " TEXT,"
            + COLMN_USER_PASSWORD + " TEXT"
            + ")";
    private String CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
            + COLMN_USER_NAME + " TEXT,"
            + COLMN_USER_LIKED + " TEXT,"
            + COLMN_MOVIE_ID + " TEXT,"
            + COLMN_RELEASE_DATE + " TEXT,"
            + COLMN_MOVIE_OVERVIEW + " TEXT,"
            + COLMN_BACKDROP_PATH + " TEXT,"
            + COLMN_MOVIE_RATING + " TEXT,"
            + COLMN_Poster_Path + " TEXT"
            + ")";
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_FAVORITES_TABLE = "DROP TABLE IF EXISTS " + TABLE_FAVORITES;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_USER_TABLE);
        sqLiteDatabase.execSQL(DROP_FAVORITES_TABLE);

        onCreate(sqLiteDatabase);
    }
    public boolean checkLike(String UserName,String MovieName)
    {
        String [] columns = {COLMN_USER_NAME,COLMN_USER_LIKED};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLMN_USER_NAME + " = ?" + " AND " + COLMN_USER_LIKED + " =?";
        String[] selectionArgs = {UserName,MovieName};

        Cursor cursor = db.query(TABLE_FAVORITES,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLMN_USER_NAME, user.getName());
        values.put(COLMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);

        db.close();
    }

    public boolean checkUser(String UserName) {
        String[] columns = {COLMN_USER_ID};
        Log.d("columns is equal to",columns.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLMN_USER_NAME + " = ?";
        String[] selectionArgs = {UserName};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password) {
        String[] columns = {
                COLMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLMN_USER_NAME + " = ?" + " AND " + COLMN_USER_PASSWORD + " =?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
}