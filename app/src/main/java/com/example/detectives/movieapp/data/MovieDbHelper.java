/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.detectives.movieapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.detectives.movieapp.data.MovieContract.MostPopular;
import com.example.detectives.movieapp.data.MovieContract.HighestRated;
import com.example.detectives.movieapp.data.MovieContract.Favorite;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "movieApp.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOST_POPULAR_TABLE = "CREATE TABLE " + MostPopular.TABLE_NAME + " (" +
                MostPopular._ID + " INTEGER PRIMARY KEY, " +
                MostPopular.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MostPopular.COLUMN_TITLE + " TEXT NOT NULL, " +
                MostPopular.COLUMN_OVERVIEW + " TEXT, " +
                MostPopular.COLUMN_VOTE_AVERAGE + " REAL, " +
                MostPopular.COLUMN_POSTER + " BLOB, " +
                MostPopular.COLUMN_BACKDROOP + " BLOB, " +
                " );";

        final String SQL_CREATE_HIGHEST_RATED_TABLE = "CREATE TABLE " + HighestRated.TABLE_NAME + " (" +
                HighestRated._ID + " INTEGER PRIMARY KEY, " +
                HighestRated.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                HighestRated.COLUMN_TITLE + " TEXT NOT NULL, " +
                HighestRated.COLUMN_OVERVIEW + " TEXT, " +
                HighestRated.COLUMN_VOTE_AVERAGE + " REAL, " +
                HighestRated.COLUMN_POSTER + " BLOB, " +
                HighestRated.COLUMN_BACKDROOP + " BLOB, " +
                " );";

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + Favorite.TABLE_NAME + " (" +
                Favorite._ID + " INTEGER PRIMARY KEY, " +
                Favorite.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                Favorite.COLUMN_TITLE + " TEXT NOT NULL, " +
                Favorite.COLUMN_OVERVIEW + " TEXT, " +
                Favorite.COLUMN_VOTE_AVERAGE + " REAL, " +
                Favorite.COLUMN_POSTER + " BLOB, " +
                Favorite.COLUMN_BACKDROOP + " BLOB, " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_MOST_POPULAR_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_HIGHEST_RATED_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MostPopular.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HighestRated.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Favorite.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
