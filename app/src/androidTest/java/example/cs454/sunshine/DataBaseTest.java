package example.cs454.sunshine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.Map;
import java.util.Set;

import example.cs454.sunshine.data.WeatherContract;
import example.cs454.sunshine.data.WeatherDbHelper;

/**
 * Created by mark on 5/6/16.
 */
public class DataBaseTest extends AndroidTestCase {
    Context context;
    static final String TEST_LOCATION = "99705";
    static final long TEST_DATE = 1419033600L;  // December 20th, 2014

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = getContext();
    }


    public void testInsertLocation() {
        WeatherDbHelper helper = new WeatherDbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        //create a contentvalues with north pole information

        ContentValues cv = new ContentValues();
        cv.put(example.cs454.sunshine.data.WeatherContract.LocationEntry.COLUMN_CITY_NAME, "North Pole");
        cv.put(example.cs454.sunshine.data.WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING, "Santa hourse");
        cv.put(example.cs454.sunshine.data.WeatherContract.LocationEntry.COLUMN_COORD_LAT, "" + 90);
        cv.put(example.cs454.sunshine.data.WeatherContract.LocationEntry.COLUMN_COORD_LONG, "" + 90);

        Cursor cursor = null;

        try{
            long locId = db.insert(example.cs454.sunshine.data.WeatherContract.LocationEntry.TABLE_NAME, null, cv);

            assertTrue(locId != -1);

            cursor = db.query(example.cs454.sunshine.data.WeatherContract.LocationEntry.TABLE_NAME, null, null, null, null, null, null);

            assertTrue("No records", cursor.moveToFirst());

            assertTrue(validateCurrentRecord("Records do not match", cursor, cv));

            ContentValues weatherValues = TestUtilities.createWeatherValues(locId);

            long weatherRowId = db.insert(example.cs454.sunshine.data.WeatherContract.WeatherEntry.TABLE_NAME, null, weatherValues);

            Cursor weatherCursor = db.query(
                    example.cs454.sunshine.data.WeatherContract.WeatherEntry.TABLE_NAME,  // Table to Query
                    null, // leaving "columns" null just returns all the columns.
                    null, // cols for "where" clause
                    null, // values for "where" clause
                    null, // columns to group by
                    null, // columns to filter by row groups
                    null  // sort order
            );

            assertTrue(validateCurrentRecord("testInsertReadDb weatherEntry failed to validate",
                    weatherCursor, weatherValues));


        }catch(Exception e){
            Log.e("test", e.getMessage());
        }finally{
            db.delete(example.cs454.sunshine.data.WeatherContract.LocationEntry.TABLE_NAME, null, null);
            db.delete(example.cs454.sunshine.data.WeatherContract.WeatherEntry.TABLE_NAME, null, null);

            if(cursor != null) cursor.close();
            if(db != null)db.close();
        }


    }


    static boolean validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            if (idx == -1) return false;
            String expectedValue = entry.getValue().toString();
            String retrievedValue = valueCursor.getString(idx);
            if (!retrievedValue.equals(expectedValue)) return false;

        }
        return true;
    }

    static ContentValues createWeatherValues(long locationRowId) {
        ContentValues weatherValues = new ContentValues();
        weatherValues.put(example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_LOC_KEY, locationRowId);
        weatherValues.put(example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_DATE, TEST_DATE);
        weatherValues.put(example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_DEGREES, 1.1);
        weatherValues.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, 1.2);
        weatherValues.put(example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_PRESSURE, 1.3);
        weatherValues.put(example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, 75);
        weatherValues.put(example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, 65);
        weatherValues.put(example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_SHORT_DESC, "Asteroids");
        weatherValues.put(example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, 5.5);
        weatherValues.put(example.cs454.sunshine.data.WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, 321);

        return weatherValues;
    }

}
