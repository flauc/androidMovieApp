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

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.detectives.movieapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOST_POPULAR = "most_popular";
    public static final String PATH_HIGHEST_RATED = "highest_rated";
    public static final String PATH_FAVORITE = "highest_rated";

    public static final class MostPopular implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOST_POPULAR).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOST_POPULAR;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOST_POPULAR;

        // Table name
        public static final String TABLE_NAME = "most_popular";

        // Table columns

        // The id returned from the API
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";

        // Stored as float
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        // Stored as blob
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_BACKDROOP = "backdrop";

        // Stored as string
        public static final String COLUMN_RELEASE_DATE = "release_date";


        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class HighestRated implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_HIGHEST_RATED).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HIGHEST_RATED;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HIGHEST_RATED;

        // Table name
        public static final String TABLE_NAME = "most_popular";

        // Table columns

        // The id returned from the API
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";

        // Stored as float
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        // Stored as blob
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_BACKDROOP = "backdrop";

        // Stored as string
        public static final String COLUMN_RELEASE_DATE = "release_date";


        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class Favorite implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;

        // Table name
        public static final String TABLE_NAME = "most_popular";

        // Table columns

        // The id returned from the API
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";

        // Stored as float
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        // Stored as blob
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_BACKDROOP = "backdrop";

        // Stored as string
        public static final String COLUMN_RELEASE_DATE = "release_date";


        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
