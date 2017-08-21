package nl.hva.summeracademy.dustify;


import android.location.Location;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

public class SensorState {

    private static int SMILEY_FACE = 0x1F603;
    private static int SICK_FACE = 0x1F637;
    private static double EU_NORM = 10.0;
    private double particleDensity;
    private Location sensorLocation;
    private Location currentLocation;
    private Date messurmentTime;

    public SensorState(Location currentLocation) {
        this.currentLocation = currentLocation;

        //Todo load data
        this.messurmentTime = new Date();
        this.sensorLocation = new Location("Location");
        this.sensorLocation.setLongitude(7.923);
        this.sensorLocation.setLatitude(41.23);
        this.particleDensity = 10;

    }

    public double getParticleDensity() {
        return particleDensity;
    }

    public String getSmilieyString() {
        if (denistyOverLimit()) {
            return getEmojiByUnicode(SICK_FACE);
        } else {
            return getEmojiByUnicode(SMILEY_FACE);
        }
    }

    public String getPercentage() {

        double percentage = particleDensity / EU_NORM * 100;

        if (denistyOverLimit()) {
            return ((int) percentage - 100) + "% over EU-Limits";
        } else {
            return ((int) percentage - 100) * -1 + "% under EU-Limits";
        }
    }

    private String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    public Date getMessurmentTime() {
        return messurmentTime;
    }

    public String getDistance() {

        float distance = currentLocation.distanceTo(sensorLocation);

        if (distance > 1000) {
            DecimalFormat df = new DecimalFormat("#.#");
            df.setRoundingMode(RoundingMode.CEILING);

            return df.format(distance / 1000) + " km";
        } else {
            return distance + " m";
        }
    }

    private boolean denistyOverLimit() {
        return particleDensity > EU_NORM;
    }
}
