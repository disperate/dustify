package nl.hva.summeracademy.dustify;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    private SensorState state;
    private SwipeRefreshLayout swipeContainer;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            state = new SensorState(location);
                            updatePollutionData();
                        }
                    }
                });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updatePollutionData();
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void updatePollutionData() {
        if (state == null) {
            return;
        }

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
}