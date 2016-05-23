package example.cs454.sunshine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mark on 5/4/16.
 */
public class WeatherDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "weather.db";

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //put your sql create table statements here
        final String SQL_CREATE_WEATHER_TABLE = "CREATE TABLE " + example.cs454.sunshine.data.WeatherContract.WeatherEntry.TABLE_NAME + " ( " +
                example.cs454.sunshine.data.WeatherContract.WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_LOC_KEY + " INTEGER NOT NULL, " +
                example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_WEATHER_ID + " INTEGER NOT NULL, " +
                example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL, " +
                example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_MIN_TEMP + " REAL NOT NULL, " +
                example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_MAX_TEMP + " REAL NOT NULL, " +
                example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_HUMIDITY + " REAL NOT NULL, " +
                example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_PRESSURE + " REAL NOT NULL, " +
                example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_WIND_SPEED + " REAL NOT NULL, " +
                example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_DEGREES + " REAL NOT NULL, " +
                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_LOC_KEY + ") REFERENCES " +
                example.cs454.sunshine.data.WeatherContract.LocationEntry.TABLE_NAME + " (" + example.cs454.sunshine.data.WeatherContract.LocationEntry._ID + "), " +

                // To assure the application have just one weather entry per day
                // per location, it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_DATE + ", " +
                example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";


        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + example.cs454.sunshine.data.WeatherContract.LocationEntry.TABLE_NAME + " (" +
                example.cs454.sunshine.data.WeatherContract.LocationEntry._ID + " INTEGER PRIMARY KEY," +
                example.cs454.sunshine.data.WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING + " TEXT UNIQUE NOT NULL, " +
                example.cs454.sunshine.data.WeatherContract.LocationEntry.COLUMN_CITY_NAME + " TEXT NOT NULL, " +
                example.cs454.sunshine.data.WeatherContract.LocationEntry.COLUMN_COORD_LAT + " REAL NOT NULL, " +
                example.cs454.sunshine.data.WeatherContract.LocationEntry.COLUMN_COORD_LONG + " REAL NOT NULL " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + example.cs454.sunshine.data.WeatherContract.LocationEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + example.cs454.sunshine.data.WeatherContract.WeatherEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
