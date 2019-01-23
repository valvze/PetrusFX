package de.ilkeskin.petrusfx.menu;

import de.ilkeskin.petrusfx.data.City;
import de.ilkeskin.petrusfx.utils.GzippedUrlReader;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Task that retrieves a list of all cities for a given country available from
 * the OpenWeatherMap API.
 *
 * @author Ilyas Keskin
 */
public class GetCityListForCountry extends Task<ObservableList<City>> {

    private static final Logger log = LogManager.getLogger(GetCityListForCountry.class);
    private String countryFilter;
    private GzippedUrlReader gzippedUrlReader;

    public GetCityListForCountry(String countryFilter) {
        this.countryFilter = countryFilter;
        this.gzippedUrlReader = new GzippedUrlReader();
    }

    @Override
    protected ObservableList<City> call() throws Exception {

        ObservableList<City> cityList = FXCollections.observableArrayList();
        JSONArray bulkCityJson = null;
        String BULK_DATA_URL = "http://bulk.openweathermap.org/sample/city.list.json.gz";

        bulkCityJson = new JSONArray(gzippedUrlReader.readUrl(BULK_DATA_URL));

        // filter cities based on the passed country code
        for (int i = 0; i < bulkCityJson.length(); i++) {
            if (countryFilter.equals(bulkCityJson.getJSONObject(i).getString("country"))) {
                JSONObject city = bulkCityJson.getJSONObject(i);
                cityList.add(new City(
                        city.getInt("id"),
                        city.getString("name"),
                        city.getJSONObject("coord").getDouble("lat"),
                        city.getJSONObject("coord").getDouble("lon"),
                        city.getString("country"))
                );
            }
        }
        cityList.sort(Comparator.comparing(City::getName));
        return cityList;
    }
}
