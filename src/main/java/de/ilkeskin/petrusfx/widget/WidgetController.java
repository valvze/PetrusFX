package de.ilkeskin.petrusfx.widget;

import de.ilkeskin.petrusfx.data.BasicWeather;
import de.ilkeskin.petrusfx.data.DataModel;
import de.ilkeskin.petrusfx.data.ExtendedWeather;
import de.ilkeskin.petrusfx.data.WeatherCondition;
import de.ilkeskin.petrusfx.utils.FetchJsonFromOwmApi;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import javafx.beans.binding.Bindings;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author apollo
 */
public class WidgetController {

    private static final Logger log = LogManager.getLogger(WidgetController.class);
    private DataModel model;
    private final FetchJsonFromOwmApi fetchJsonFromOwmApi;

    @FXML
    private Label tempLabel;
    @FXML
    private Label conditionLabel;
    @FXML
    private Label minTempLabel;
    @FXML
    private Label maxTempLabel;
    @FXML
    private Label pressureLabel;
    @FXML
    private Label humidityLabel;
    @FXML
    private Label cloudinessLabel;
    @FXML
    private Label windLabel;
    @FXML
    private Label sunriseLabel;
    @FXML
    private Label sunsetLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private GridPane dataTable;

    public WidgetController() {
        fetchJsonFromOwmApi = new FetchJsonFromOwmApi();
    }

    /**
     * Initializes the the model.
     *
     * @param model The model that this controller will manipulate.
     */
    public void initModel(DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;

        // Listen for a change in the current basic weather data in the model
        model.currentBasicWeatherProperty().addListener((obs, oldValue, newValue) -> {
            // If an previous value is displayed unbind it
            if (oldValue != null) {
                tempLabel.textProperty().unbind();
                pressureLabel.textProperty().unbind();
                humidityLabel.textProperty().unbind();
                minTempLabel.textProperty().unbind();
                maxTempLabel.textProperty().unbind();
                dateLabel.textProperty().unbind();
            }
            // No data, nothing to display
            if (newValue == null) {
                tempLabel.setText("");
                pressureLabel.setText("");
                humidityLabel.setText("");
                minTempLabel.setText("");
                maxTempLabel.setText("");
                dateLabel.setText("");
            } else {
                // Convert properties to observable Strings and append the corresponding unit
                // according to the currently defined unit standard
                tempLabel.textProperty().bind(Bindings
                        .convert(newValue.temperatureProperty())
                        .concat(model.unitFormatProperty().get().temperatureUnitProperty()));
                pressureLabel.textProperty().bind(Bindings
                        .convert(newValue.pressureProperty())
                        .concat(model.unitFormatProperty().get().pressureUnitProperty()));
                humidityLabel.textProperty().bind(Bindings
                        .convert(newValue.humidityProperty())
                        .concat(model.unitFormatProperty().get().percentageUnitProperty()));
                minTempLabel.textProperty().bind(Bindings
                        .convert(newValue.minTemperatureProperty())
                        .concat(model.unitFormatProperty().get().temperatureUnitProperty()));
                maxTempLabel.textProperty().bind(Bindings
                        .convert(newValue.maxTemperatureProperty())
                        .concat(model.unitFormatProperty().get().temperatureUnitProperty()));
                dateLabel.textProperty().bind(Bindings.convert(newValue.timestampProperty()));
            }
        });

        model.currentExtendedWeatherProperty().addListener((obs, oldValue, newValue) -> {
            if (oldValue != null) {
                cloudinessLabel.textProperty().unbind();
                windLabel.textProperty().unbind();
                sunriseLabel.textProperty().unbind();
                sunsetLabel.textProperty().unbind();
            }
            if (newValue == null) {
                cloudinessLabel.setText("");
                windLabel.setText("");
                sunriseLabel.setText("");
                sunsetLabel.setText("");
            } else {
                cloudinessLabel.textProperty().bind(Bindings
                        .convert(newValue.cloudinessProperty())
                        .concat(model.unitFormatProperty().get().percentageUnitProperty()));
                windLabel.textProperty().bind(Bindings
                        .convert(newValue.windSpeedProperty())
                        .concat(model.unitFormatProperty().get().speedUnitProperty()));
                sunriseLabel.textProperty().bind(Bindings.convert(newValue.sunriseProperty()));
                sunsetLabel.textProperty().bind(Bindings.convert(newValue.sunsetProperty()));
            }
        });

        model.currentWeatherConditionProperty().addListener((obs, oldValue, newValue) -> {
            if (oldValue != null) {
                conditionLabel.textProperty().unbind();
            }
            if (newValue == null) {
                conditionLabel.setText("");
            } else {
                conditionLabel.textProperty().bind(Bindings.convert(newValue.descriptionProperty()));
            }
        });

        // Listen for a change in the current city set in the model
        // and in case the city changed rerun the data retrieval to update the values
        model.currentCityProperty().addListener((obs, oldValue, newValue) -> {
            try {
                if (oldValue != newValue && newValue != null) {
                    fetchJsonFromOwmApi.setUrl("weather",
                            new HashMap<String, String>() {
                        {
                            put("id", Integer.toString(newValue.getId()));
                            put("units", (model.getUnitFormat() != null) ? model.getUnitFormat().toString().toLowerCase() : "");
                        }
                    }
                    );
                }
            } catch (URISyntaxException | MalformedURLException ex) {
                log.error(ex);
            }
            fetchJsonFromOwmApi.restart();
        });

        // Listen for a change in the current unit standard set in the model
        // and in case the unit standard changed rerun the data retrieval to update the values
        model.unitFormatProperty().addListener((obs, oldValue, newValue) -> {
            try {
                if (oldValue != newValue && newValue != null) {
                    fetchJsonFromOwmApi.setUrl("weather",
                            new HashMap<String, String>() {
                        {
                            put("id", Integer.toString(model.getCurrentCity().getId()));
                            put("units", newValue.toString().toLowerCase());
                        }
                    }
                    );
                }
            } catch (URISyntaxException | MalformedURLException ex) {
                log.error(ex);
            }
            fetchJsonFromOwmApi.restart();
        });
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() throws Exception {
        hideGuiElements();
        fetchJsonFromOwmApi.setOnSucceeded((WorkerStateEvent workerStateEvent) -> {
            JSONObject jsonData = (JSONObject) workerStateEvent.getSource().getValue();
            model.currentBasicWeatherProperty().set(parseBasicWeatherFromJson(jsonData));
            model.currentExtendedWeatherProperty().set(parseExtWeatherFromJson(jsonData));
            model.currentWeatherConditionProperty().set(parseWeatherConditionFromJson(jsonData));
            showGuiElements();
        });
    }

    /**
     * Helper method that parses basic weather data from the OWM APIs raw JSON
     * response
     *
     * @param jsonData Raw API response data in form of a JSON object.
     * @return BasicWeather BasicWeather bean with data from the OWM API.
     */
    public BasicWeather parseBasicWeatherFromJson(JSONObject jsonData) {
        JSONObject main = jsonData.getJSONObject("main");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd.MM.yy HH:mm:ss (z)");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return new BasicWeather(
                (int) Math.round(main.optDouble("temp")),
                main.optInt("pressure"),
                main.optInt("humidity"),
                (int) Math.round(main.optDouble("temp_min")),
                (int) Math.round(main.optDouble("temp_max")),
                "last updated: " + sdf.format(new Date(jsonData.getLong("dt") * 1000L))
        );
    }

    /**
     * Helper method that parses extended weather data from the OWM APIs raw
     * JSON response
     *
     * @param jsonData Raw API response data in form of a JSON object.
     * @return ExtendedWeather ExtendedWeather bean with data from the OWM API.
     */
    public ExtendedWeather parseExtWeatherFromJson(JSONObject jsonData) {
        JSONObject wind = jsonData.getJSONObject("wind");
        JSONObject sys = jsonData.getJSONObject("sys");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm (z)");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return new ExtendedWeather(
                jsonData.optInt("gust"),
                wind.optDouble("speed"),
                wind.optDouble("deg"),
                wind.optDouble("gust"),
                jsonData.optJSONObject("clouds").optInt("all"),
                sdf.format(new Date(sys.getLong("sunrise") * 1000L)),
                sdf.format(new Date(sys.getLong("sunset") * 1000L))
        );
    }

    /**
     * Helper method that parses weather condition data from the OWM APIs raw
     * JSON response
     *
     * @param jsonData Raw API response data in form of a JSON object.
     * @return WeatherCondition WeatherCondition bean with data from the OWM
     * API.
     */
    public WeatherCondition parseWeatherConditionFromJson(JSONObject jsonData) {
        // omitting multiple conditions for now...
        JSONObject weather = jsonData.getJSONArray("weather").getJSONObject(0);
        return new WeatherCondition(
                weather.getInt("id"),
                weather.getString("main"),
                weather.getString("description"),
                weather.getString("icon")
        );
    }

    /**
     * Helper method to hide all labels from the GUI
     */
    public void hideGuiElements() {
        tempLabel.setVisible(false);
        conditionLabel.setVisible(false);
        minTempLabel.setVisible(false);
        maxTempLabel.setVisible(false);
        pressureLabel.setVisible(false);
        humidityLabel.setVisible(false);
        cloudinessLabel.setVisible(false);
        windLabel.setVisible(false);
        sunriseLabel.setVisible(false);
        sunsetLabel.setVisible(false);
        dataTable.setVisible(false);
    }

    /**
     * Helper method to make all labels visible in the GUI
     */
    public void showGuiElements() {
        tempLabel.setVisible(true);
        conditionLabel.setVisible(true);
        minTempLabel.setVisible(true);
        maxTempLabel.setVisible(true);
        pressureLabel.setVisible(true);
        humidityLabel.setVisible(true);
        cloudinessLabel.setVisible(true);
        windLabel.setVisible(true);
        sunriseLabel.setVisible(true);
        sunsetLabel.setVisible(true);
        dataTable.setVisible(true);
    }

}
