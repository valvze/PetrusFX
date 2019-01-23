package de.ilkeskin.petrusfx.data;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * The central data model that consits of various beans.
 *
 * @author Ilyas Keskin
 */
public class DataModel {

    private final ObjectProperty<UnitFormat> unitFormat;
    private final ObjectProperty<City> currentCity;
    private final ObjectProperty<BasicWeather> currentBasicWeather;
    private final ObjectProperty<WeatherCondition> currentWeatherCondition;
    private final ObjectProperty<ExtendedWeather> currentExtendedWeather;

    public DataModel() {
        this.unitFormat = new SimpleObjectProperty(this, "units", UnitFormat.METRIC);
        this.currentCity = new SimpleObjectProperty(this, "currentCity", null);
        this.currentBasicWeather = new SimpleObjectProperty(this, "currentBasicWeather", null);
        this.currentWeatherCondition = new SimpleObjectProperty(this, "currentWeatherCondition", null);
        this.currentExtendedWeather = new SimpleObjectProperty(this, "currentExtendedWeather", null);
    }

    public ObjectProperty<UnitFormat> unitFormatProperty() {
        return unitFormat;
    }

    public final UnitFormat getUnitFormat() {
        return unitFormat.get();
    }

    public final void setUnitFormat(UnitFormat unitFormat) {
        this.unitFormat.set(unitFormat);
    }

    public ObjectProperty<City> currentCityProperty() {
        return currentCity;
    }

    public final City getCurrentCity() {
        return currentCity.get();
    }

    public final void setCurrentCity(City city) {
        currentCity.set(city);
    }

    public ObjectProperty<BasicWeather> currentBasicWeatherProperty() {
        return currentBasicWeather;
    }

    public final BasicWeather getCurrentBasicWeather() {
        return currentBasicWeather.get();
    }

    public final void setCurrentBasicWeather(BasicWeather basicWeather) {
        currentBasicWeather.set(basicWeather);
    }

    public ObjectProperty<WeatherCondition> currentWeatherConditionProperty() {
        return currentWeatherCondition;
    }

    public final WeatherCondition getCurrentWeatherCondition() {
        return currentWeatherCondition.get();
    }

    public final void setCurrentWeatherCondition(WeatherCondition weatherCondition) {
        currentWeatherCondition.set(weatherCondition);
    }

    public ObjectProperty<ExtendedWeather> currentExtendedWeatherProperty() {
        return currentExtendedWeather;
    }

    public final ExtendedWeather getCurrentExtendedWeather() {
        return currentExtendedWeather.get();
    }

    public final void setCurrentExtendedWeather(ExtendedWeather extendedWeather) {
        currentExtendedWeather.set(extendedWeather);
    }

}
