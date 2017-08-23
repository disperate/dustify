package nl.hva.summeracademy.dustify;


import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.net.ParseException;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class GetLuftDatenResponseHandler extends ContextualAsyncHttpResponseHandler {


    public GetLuftDatenResponseHandler(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        try {


            JSONObject response = new JSONObject(new String(responseBody));

            Log.d("Dustify", new String(responseBody));
            SensorState state = new SensorState(MainActivity.state.getCurrentLocation());

            state.setParticleDensity(response.getDouble("p1"));

            Location sensorLocation = new Location("a");
            sensorLocation.setLatitude(response.getDouble("lat"));
            sensorLocation.setLongitude(response.getDouble("lng"));
            state.setSensorLocation(sensorLocation);


            String dateString = response.getString("timestamp");
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = formatter.parse(dateString);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.HOUR, 2);

                state.setMessurmentTime(calendar.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            MainActivity.state = state;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainActivity.updateStateBroadcast(this.context);
        Log.d("Dustify", "Successful REST call");
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.d("Dustify", "Failed REST call");
    }
}
