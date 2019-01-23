package de.ilkeskin.petrusfx.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Bean that stores extended metroglogical data.
 *
 * @author Ilyas Keskin
 */
public class ExtendedWeather {

    private final IntegerProperty visibility;
    private final DoubleProperty windSpeed;
    private final DoubleProperty windDirection;
    private final DoubleProperty windGust;
    private final IntegerProperty cloudiness;
    private final StringProperty sunrise;
    private final StringProperty sunset;

    public ExtendedWeather(int visibility, double windSpeed, double windDirection,
            double windGust, int cloudiness, String sunrise, String sunset) {
        this.visibility = new SimpleIntegerProperty(this, "visibility", visibility);
        this.windSpeed = new SimpleDoubleProperty(this, "windSpeed", windSpeed);
        this.windDirection = new SimpleDoubleProperty(this, "windDirection", windDirection);
        this.windGust = new SimpleDoubleProperty(this, "windGust", windGust);
        this.cloudiness = new SimpleIntegerProperty(this, "cloudiness", cloudiness);
        this.sunrise = new SimpleStringProperty(this, "sunrise", sunrise);
        this.sunset = new SimpleStringProperty(this, "sunset", sunset);
    }

    public final IntegerProperty visibilityProperty() {
        return this.visibility;
    }

    public final int getVisibility() {
        return this.visibilityProperty().get();
    }

    public final void setVisibility(final int visibility) {
        this.visibilityProperty().set(visibility);
    }

    public final DoubleProperty windSpeedProperty() {
        return this.windSpeed;
    }

    public final double getWindSpeed() {
        return this.windSpeedProperty().get();
    }

    public final void setWindSpeed(final double windSpeed) {
        this.windSpeedProperty().set(windSpeed);
    }

    public final DoubleProperty windDirectionProperty() {
        return this.windDirection;
    }

    public final double getWindDirection() {
        return this.windDirectionProperty().get();
    }

    public final void setWindDirection(final double windDirection) {
        this.windDirectionProperty().set(windDirection);
    }

    public final DoubleProperty windGustProperty() {
        return this.windGust;
    }

    public final double getWindGust() {
        return this.windGustProperty().get();
    }

    public final void setWindGust(final double windGust) {
        this.windGustProperty().set(windGust);
    }

    public final IntegerProperty cloudinessProperty() {
        return this.cloudiness;
    }

    public final int getCloudiness() {
        return this.cloudinessProperty().get();
    }

    public final void setCloudiness(final int cloudiness) {
        this.cloudinessProperty().set(cloudiness);
    }

    public final StringProperty sunriseProperty() {
        return this.sunrise;
    }

    public final String getSunrise() {
        return this.sunriseProperty().get();
    }

    public final void setSunrise(final String sunrise) {
        this.sunriseProperty().set(sunrise);
    }

    public final StringProperty sunsetProperty() {
        return this.sunset;
    }

    public final String getSunset() {
        return this.sunsetProperty().get();
    }

    public final void setSunset(final String sunset) {
        this.sunsetProperty().set(sunset);
    }
}
