package de.ilkeskin.petrusfx.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Bean that stores basic weather data.
 *
 * @author Ilyas Keskin
 */
public class BasicWeather {

    private final IntegerProperty temperature;
    private final IntegerProperty pressure;
    private final IntegerProperty humidity;
    private final DoubleProperty minTemperature;
    private final DoubleProperty maxTemperature;
    private final StringProperty timestamp;

    public BasicWeather(int temperature, int pressure, int humidity,
            double minTemperature, double maxTemperature, String timestamp) {
        this.temperature = new SimpleIntegerProperty(this, "temperature", temperature);
        this.pressure = new SimpleIntegerProperty(this, "pressure", pressure);
        this.humidity = new SimpleIntegerProperty(this, "humidity", humidity);
        this.minTemperature = new SimpleDoubleProperty(this, "minTemperature", minTemperature);
        this.maxTemperature = new SimpleDoubleProperty(this, "maxTemperature", maxTemperature);
        this.timestamp = new SimpleStringProperty(this, "timestamp", timestamp);
    }

    public final IntegerProperty temperatureProperty() {
        return this.temperature;
    }

    public final int getTemperature() {
        return this.temperatureProperty().get();
    }

    public final void setTemperatur(final int temperature) {
        this.temperatureProperty().set(temperature);
    }

    public final IntegerProperty pressureProperty() {
        return this.pressure;
    }

    public final double getPressure() {
        return this.pressureProperty().get();
    }

    public final void setPressure(final int pressure) {
        this.pressureProperty().set(pressure);
    }

    public final IntegerProperty humidityProperty() {
        return this.humidity;
    }

    public final double getHumidity() {
        return this.humidityProperty().get();
    }

    public final void setHumidity(final int humidity) {
        this.humidityProperty().set(humidity);
    }

    public final DoubleProperty minTemperatureProperty() {
        return this.minTemperature;
    }

    public final double getMinTemperature() {
        return this.minTemperatureProperty().get();
    }

    public final void setMinTemperature(final double minTemperature) {
        this.minTemperatureProperty().set(minTemperature);
    }

    public final DoubleProperty maxTemperatureProperty() {
        return this.maxTemperature;
    }

    public final double getMaxTemperature() {
        return this.maxTemperatureProperty().get();
    }

    public final void setMaxTemperature(final double maxTemperature) {
        this.maxTemperatureProperty().set(maxTemperature);
    }

    public final StringProperty timestampProperty() {
        return this.timestamp;
    }

    public final String getTimestamp() {
        return this.timestampProperty().get();
    }

    public final void setTimestamp(final String timestamp) {
        this.timestampProperty().set(timestamp);
    }
}
