package de.ilkeskin.petrusfx.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Bean that stores city data.
 *
 * @author Ilyas Keskin
 */
public class City {

    private final IntegerProperty id;
    private final StringProperty name;
    private final DoubleProperty latitude;
    private final DoubleProperty longitude;
    private final StringProperty countryCode;
    //private int zipCode;

    public City(int id, String name, double latitude, double longitude,
            String countryCode /*,int zipCode*/) {
        this.id = new SimpleIntegerProperty(this, "id", id);
        this.name = new SimpleStringProperty(this, "name", name);
        this.latitude = new SimpleDoubleProperty(this, "latitude", latitude);
        this.longitude = new SimpleDoubleProperty(this, "longitude", longitude);
        this.countryCode = new SimpleStringProperty(this, "countryCode", countryCode);
        //this.zipCode = zipCode;
    }

    public final IntegerProperty idProperty() {
        return this.id;
    }

    public final int getId() {
        return this.idProperty().get();
    }

    public final void setId(final int id) {
        this.idProperty().set(id);
    }

    public final StringProperty nameProperty() {
        return this.name;
    }

    public final String getName() {
        return this.nameProperty().get();
    }

    public final void setName(final String name) {
        this.nameProperty().set(name);
    }

    public final DoubleProperty latitudeProperty() {
        return this.latitude;
    }

    public final double getLatitude() {
        return this.latitudeProperty().get();
    }

    public final void setLatitude(final double latitude) {
        this.latitudeProperty().set(latitude);
    }

    public final DoubleProperty longitudeProperty() {
        return this.longitude;
    }

    public final double getLongitude() {
        return this.longitudeProperty().get();
    }

    public final void setLongitude(final double longitude) {
        this.longitudeProperty().set(longitude);
    }

    public final StringProperty countryCodeProperty() {
        return this.countryCode;
    }

    public final String getCountryCode() {
        return this.countryCodeProperty().get();
    }

    public final void setCountryCode(final String countryCode) {
        this.countryCodeProperty().set(countryCode);
    }

}
