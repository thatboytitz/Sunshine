package example.cs454.sunshine;

import android.app.Application;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.util.Log;


import org.junit.runner.RunWith;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
//@RunWith(AndroidJUnit4.class)
public class AndroidTest extends AndroidTestCase {


    public void runTest() throws JSONException {
        double max = getMaxTemperatureForDay("{'city':{'id':2523246,'name':'Santuario di Gibilmanna','coord':{'lon':14.01667,'lat':37.98333}," +
                "'country':'IT','population':0},'cod':'200','message':0.0084,'cnt':7,'list':[{'dt':1461063600,'temp':{'day':17.03,'min':15.91," +
                "'max':17.03,'night':15.91,'eve':16.68,'morn':17.03},'pressure':1030.83,'humidity':100,'weather':[{'id':801,'main':'Clouds'," +
                "'description':'few clouds','icon':'02d'}],'speed':4.26,'deg':326,'clouds':20},{'dt':1461150000,'temp':{'day':16.76,'min':15.72," +
                "'max':17.48,'night':17.48,'eve':17.33,'morn':15.72},'pressure':1034.23,'humidity':100,'weather':[{'id':800,'main':'Clear'," +
                "'description':'clear sky','icon':'01d'}],'speed':1.82,'deg':36,'clouds':0},{'dt':1461236400,'temp':{'day':18.04,'min':17.18," +
                "'max':18.06,'night':17.62,'eve':17.55,'morn':17.18},'pressure':1038.07,'humidity':96,'weather':[{'id':801,'main':'Clouds'," +
                "'description':'few clouds','icon':'02d'}],'speed':2.31,'deg':18,'clouds':12},{'dt':1461322800,'temp':{'day':17.34,'min':17.33," +
                "'max':17.81,'night':17.71,'eve':17.81,'morn':17.44},'pressure':1035.97,'humidity':100,'weather':[{'id':803,'main':'Clouds'," +
                "'description':'broken clouds','icon':'04d'}],'speed':0.53,'deg':340,'clouds':80},{'dt':1461409200,'temp':{'day':18.19,'min':" +
                "17.76,'max':18.27,'night':18.27,'eve':17.76,'morn':17.88},'pressure':1026.55,'humidity':97,'weather':[{'id':800,'main':'Clear'," +
                "'description':'clear sky','icon':'01d'}],'speed':3.48,'deg':70,'clouds':0},{'dt':1461495600,'temp':{'day':21.27,'min':12.76," +
                "'max':21.27,'night':12.76,'eve':18.9,'morn':15.66},'pressure':993.82,'humidity':0,'weather':[{'id':500,'main':'Rain'," +
                "'description':'light rain','icon':'10d'}],'speed':2.54,'deg':290,'clouds':12,'rain':1.6},{'dt':1461582000,'temp':{'day':19.32," +
                "'min':13.84,'max':19.32,'night':13.84,'eve':18.34,'morn':15.85},'pressure':997.08,'humidity':0,'weather':[{'id':501,'main':'Rain'," +
                "'description':'moderate rain','icon':'10d'}],'speed':0.81,'deg':46,'clouds':90,'rain':8.83}]}", 0);
        assertEquals(max, 17.03, .01);
        Log.d("test", Double.toString(max));
    }


    public static double getMaxTemperatureForDay(String weatherJsonStr, int dayIndex)
            throws JSONException {
        JSONObject weatherObj = new JSONObject(weatherJsonStr);
        JSONArray weatherList = weatherObj.getJSONArray("list");
        JSONObject dayWeather = weatherList.getJSONObject(dayIndex);
        JSONObject temp = dayWeather.getJSONObject("temp");
        double max = temp.getDouble("max");

        return max;

    }
}