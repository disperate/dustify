package nl.hva.summeracademy.dustify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updatePollutionData();

    }

    private void updatePollutionData() {

        //TODO: Get all Data

        LuftDatenRestClient.get(new GetLuftDatenResponseHandler(this));

        //TODO: Get current posistion

        //TODO find closest sensor

        //TODO: callculate distance to Sensor

        //TODO: Load Data to Activity

    }
}
