package de.ilkeskin.petrusfx.menu;

import com.jfoenix.controls.JFXComboBox;
import de.ilkeskin.petrusfx.data.City;
import de.ilkeskin.petrusfx.data.DataModel;
import de.ilkeskin.petrusfx.data.UnitFormat;
import de.ilkeskin.petrusfx.utils.GzippedUrlReader;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

/**
 * Controller for the app menu that holds 3 combo boxes for the country code,
 * the city and a unit standard.
 *
 * @author Ilyas Keskin
 */
public class MenuController {

    private static final Logger log = LogManager.getLogger(MenuController.class);
    private DataModel model;
    private GzippedUrlReader gzippedUrlReader;
    private final Task<ArrayList<String>> getFullCountryList;
    private GetCityListForCountry getCityListForCountry;
    @FXML
    private JFXComboBox<String> countryComboBox;
    @FXML
    private JFXComboBox<City> cityComboBox;
    @FXML
    private JFXComboBox<UnitFormat> unitComboBox;

    public MenuController() {

        this.gzippedUrlReader = new GzippedUrlReader();

        // This task gets a list of all countrys (codes) available from the OpenWeatherMap API
        getFullCountryList = new Task() {
            @Override
            protected ArrayList<String> call() throws Exception {

                ArrayList<String> countryList = new ArrayList();
                JSONArray bulkCityJson = null;
                String BULK_DATA_URL = "http://bulk.openweathermap.org/sample/city.list.json.gz";

                bulkCityJson = new JSONArray(gzippedUrlReader.readUrl(BULK_DATA_URL));
                log.debug("succesfully parsed JSON Array from bulk city list");

                log.debug("searching bulk city list for available countries");
                for (int i = 0; i < bulkCityJson.length(); i++) {
                    String country = bulkCityJson.getJSONObject(i).getString("country");
                    if (!countryList.contains(country)) {
                        countryList.add(country);
                    }
                }
                countryList.sort(String.CASE_INSENSITIVE_ORDER);
                log.debug("got and sorted list for available countries");

                return countryList;
            }
        };
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
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() throws Exception {

        // Fill the country combo box with the retrieved list of availbale countries (codes)
        getFullCountryList.setOnSucceeded((WorkerStateEvent gotFullCountryList) -> {
            countryComboBox.getItems().addAll(getFullCountryList.getValue());
        });
        // Listen for user seletion in the country combo box
        countryComboBox.getSelectionModel().selectedItemProperty().addListener(
                (options, oldValue, newValue) -> {
                    // Retrieve a list of all cities available for the chosen country
                    // and fill the city combo box with it
                    getCityListForCountry = new GetCityListForCountry(newValue);
                    getCityListForCountry.setOnSucceeded((WorkerStateEvent gotFullCityList) -> {
                        cityComboBox.setItems(getCityListForCountry.getValue());
                    });
                    getCityListForCountry.run();
                });

        // Properly display city names in the corresponding combo box
        cityComboBox.setConverter(new StringConverter<City>() {
            @Override
            public String toString(City object) {
                return object.getName();
            }

            @Override
            public City fromString(String string) {
                return null;
            }
        });

        // Listen for user seletion in the city combo box and update the model
        cityComboBox.getSelectionModel().selectedItemProperty().addListener(
                (options, oldValue, newValue) -> {
                    model.setCurrentCity(newValue);
                });

        getFullCountryList.run();

        unitComboBox.setItems((FXCollections.observableArrayList(UnitFormat.values())));

        // Listen for user seletion in the unit standard combo box and update the model
        unitComboBox.getSelectionModel().selectedItemProperty().addListener(
                (options, oldValue, newValue) -> {
                    model.setUnitFormat(newValue);
                });

    }
}
