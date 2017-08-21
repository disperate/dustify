package nl.hva.summeracademy.dustify;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    SensorState state;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updatePollutionData();
                swipeContainer.setRefreshing(false);
            }
        });

        updatePollutionData();

    }

    private void updatePollutionData() {

        SensorState state = new SensorState(getCurrentLocation());

        TextView smileyTextView = (TextView) findViewById(R.id.smileyTextView);
        smileyTextView.setText(state.getSmilieyString());

        TextView particleDensityTextView = (TextView) findViewById(R.id.particleDensityTextView);
        particleDensityTextView.setText("~ " + state.getParticleDensity() + " µg/m³ messured");

        TextView percentageTextView = (TextView) findViewById(R.id.percentageTextView);
        percentageTextView.setText(state.getPercentage());

        TextView distanceTextView = (TextView) findViewById(R.id.distanceTextView);
        distanceTextView.setText("Sensor located " + state.getDistance() + " away from you");

        TextView measurementTimeTextView = (TextView) findViewById(R.id.measurementTimeTextView);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        measurementTimeTextView.setText("Last measurement at " + formatter.format(state.getMessurmentTime()));
    }

    private Location getCurrentLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 123);

        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location == null) {
            Toast.makeText(this, "Could not get current location", Toast.LENGTH_LONG);
        }

        return location;
    }
}
