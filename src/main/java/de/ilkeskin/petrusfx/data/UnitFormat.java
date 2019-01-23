package de.ilkeskin.petrusfx.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Enum class that is used to properly display units according to a given unit
 * standard.
 *
 * @author Ilyas Keskin
 */
public enum UnitFormat {

    STANDARD("K", " m/s"),
    METRIC("°C", " m/s"),
    IMPERIAL("°F", " mph");

    private final StringProperty temperatureUnit;
    private final StringProperty speedUnit;
    private final StringProperty percentageUnit;
    private final StringProperty pressureUnit;
    private final StringProperty directionUnit;

    private UnitFormat(String temperatureUnit, String speedUnit) {
        this.percentageUnit = new SimpleStringProperty(this, "humidityUnit", " %");
        this.pressureUnit = new SimpleStringProperty(this, "pressureUnit", " hPa");
        this.directionUnit = new SimpleStringProperty(this, "directionUnit", " °");
        this.temperatureUnit = new SimpleStringProperty(this, "temperatureUnit", temperatureUnit);
        this.speedUnit = new SimpleStringProperty(this, "speedUnit", speedUnit);
    }

    public StringProperty temperatureUnitProperty() {
        return temperatureUnit;
    }

    public final String getTemperatureUnit() {
        return temperatureUnit.get();
    }

    public StringProperty speedUnitProperty() {
        return speedUnit;
    }

    public final String getSpeedUnit() {
        return speedUnit.get();
    }

    public StringProperty percentageUnitProperty() {
        return percentageUnit;
    }

    public final String getHumidityUnit() {
        return percentageUnit.get();
    }

    public StringProperty pressureUnitProperty() {
        return pressureUnit;
    }

    public final String getPressureUnit() {
        return pressureUnit.get();
    }

    public StringProperty directionUnitProperty() {
        return directionUnit;
    }

    public final String getDirectionUnit() {
        return directionUnit.get();
    }

}
