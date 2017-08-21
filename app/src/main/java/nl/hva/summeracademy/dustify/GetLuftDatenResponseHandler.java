package nl.hva.summeracademy.dustify;


import android.content.Context;
import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GetLuftDatenResponseHandler extends ContextualAsyncHttpResponseHandler {


    public GetLuftDatenResponseHandler(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


        List<SensorValue> results = new ArrayList<SensorValue>();

        try {
            String body = Arrays.toString(responseBody);

            Log.d("Dustify", body);

            JSONArray json = new JSONArray(body);
            Log.e("biscuit", json.get(1).toString());

            for (int i = 0; i < json.length(); i++) {
                SensorValue value = new SensorValue();
                JSONObject object = (JSONObject) json.get(i);
                JSONObject locationObject = object.getJSONObject("location");
                value.setLat(locationObject.getDouble("latitude"));
                value.setLng(locationObject.getDouble("longitude"));

                JSONArray sensorValues = object.getJSONArray("sensordatavalues");

                boolean isPSensor = false;
                for (int s = 0; s < sensorValues.length(); s++) {
                    JSONObject sensorValueObject = (JSONObject) sensorValues.get(s);
                    String valueType = sensorValueObject.getString("value_type");

                    if (valueType.equals("P1")) {
                        value.setP1(sensorValueObject.getDouble("value"));
                        isPSensor = true;
                    }
                    if (valueType.equals("P2")) {
                        value.setP2(sensorValueObject.getDouble("value"));
                        isPSensor = true;
                    }
                }

                value.setTimestamp(object.getString("timestamp"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Location myCurrentLocation = new Location("here");
        myCurrentLocation.setLatitude(52.3590450);
        myCurrentLocation.setLongitude(4.9102020);
        SensorValue closesSensor = filterByLocationAndTime(results, myCurrentLocation);
        Log.d("Dustify", closesSensor.getP1() + "");


        Log.d("Dustify", "Sucessfull REST call");
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.d("Dustify", "Failed REST call");
    }

    public SensorValue filterByLocationAndTime(List<SensorValue> originalList, Location myLocation) {
        List<SensorValue> filteredList = new ArrayList<SensorValue>();
        Location closestLocation = searchClosestLocation(originalList, myLocation);

        for (SensorValue value : originalList) {
            if (value.getLocation().equals(closestLocation)) {
                filteredList.add(value);
            }
        }

        return filterByTime(filteredList);

    }

    public Location searchClosestLocation(List<SensorValue> originalList, Location myLocation) {
        float closestDistance = 999F;
        Location closestLocation = null;
        for (SensorValue value : originalList) {
            if (value.getLocation().distanceTo(myLocation) < closestDistance) {
                closestLocation = new Location(value.getLocation());
            }

        }
        return closestLocation;

    }

    public SensorValue filterByTime(List<SensorValue> originalList) {
        Date closestDate = originalList.get(0).getTimestamp();
        SensorValue filteredSensorValue = new SensorValue();

        for (SensorValue value : originalList) {
            if (closestDate.after(value.getTimestamp())) {
                filteredSensorValue = value;
            }
        }

        return filteredSensorValue;
    }
}
