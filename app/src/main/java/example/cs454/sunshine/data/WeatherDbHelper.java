package example.cs454.sunshine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import example.cs454.sunshine.data.Contract.*;

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
        final String SQL_CREATE_WEATHER_TABLE = "CREATE TABLE " + Contract.WeatherEntry.TABLE_NAME + " ( " +
                Contract.WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.WeatherEntry.COLUMN_LOC_KEY + " INTEGER NOT NULL, " +
                Contract.WeatherEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                Contract.WeatherEntry.COLUMN_WEATHER_ID + " INTEGER NOT NULL, " +
                Contract.WeatherEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL, " +
                Contract.WeatherEntry.COLUMN_MIN_TEMP + " REAL NOT NULL, " +
                Contract.WeatherEntry.COLUMN_MAX_TEMP + " REAL NOT NULL, " +
                Contract.WeatherEntry.COLUMN_HUMIDITY + " REAL NOT NULL, " +
                Contract.WeatherEntry.COLUMN_PRESSURE + " REAL NOT NULL, " +
                Contract.WeatherEntry.COLUMN_WIND_SPEED + " REAL NOT NULL, " +
                Contract.WeatherEntry.COLUMN_DEGREES + " REAL NOT NULL, " +
                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + Contract.WeatherEntry.COLUMN_LOC_KEY + ") REFERENCES " +
                Contract.LocationEntry.TABLE_NAME + " (" + Contract.LocationEntry._ID + "), " +

                // To assure the application have just one weather entry per day
                // per location, it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + Contract.WeatherEntry.COLUMN_DATE + ", " +
                Contract.WeatherEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";


        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + Contract.LocationEntry.TABLE_NAME + " (" +
                Contract.LocationEntry._ID + " INTEGER PRIMARY KEY," +
                Contract.LocationEntry.COLUMN_LOCATION_SETTING + " TEXT UNIQUE NOT NULL, " +
                Contract.LocationEntry.COLUMN_CITY_NAME + " TEXT NOT NULL, " +
                Contract.LocationEntry.COLUMN_COORD_LAT + " REAL NOT NULL, " +
                Contract.LocationEntry.COLUMN_COORD_LONG + " REAL NOT NULL " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.LocationEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.WeatherEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
