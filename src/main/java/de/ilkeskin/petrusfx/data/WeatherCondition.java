package de.ilkeskin.petrusfx.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Bean that stores information about weather conditions.
 *
 * @author Ilyas Keskin
 */
public class WeatherCondition {

    private final IntegerProperty id;
    private final StringProperty group;
    private final StringProperty description;
    private final StringProperty iconId;

    public WeatherCondition(int id, String group, String description,
            String iconId) {
        this.id = new SimpleIntegerProperty(this, "id", id);
        this.group = new SimpleStringProperty(this, "group", group);
        this.description = new SimpleStringProperty(this, "description", description);
        this.iconId = new SimpleStringProperty(this, "iconId", iconId);
    }

    public final IntegerProperty idProperty() {
        return this.id;
    }

    public final double getId() {
        return this.idProperty().get();
    }

    public final void setId(final int id) {
        this.idProperty().set(id);
    }

    public final StringProperty groupProperty() {
        return this.group;
    }

    public final String getGroup() {
        return this.groupProperty().get();
    }

    public final void setGroup(final String group) {
        this.groupProperty().set(group);
    }

    public final StringProperty descriptionProperty() {
        return this.description;
    }

    public final String getDescription() {
        return this.descriptionProperty().get();
    }

    public final void setDescription(final String description) {
        this.descriptionProperty().set(description);
    }

    public final StringProperty iconIdProperty() {
        return this.iconId;
    }

    public final String getIconId() {
        return this.iconIdProperty().get();
    }

    public final void setIconId(final String iconId) {
        this.iconIdProperty().set(iconId);
    }
}
