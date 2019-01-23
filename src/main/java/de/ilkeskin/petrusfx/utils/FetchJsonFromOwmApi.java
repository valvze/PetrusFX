package de.ilkeskin.petrusfx.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * Scheduled service that queries the OpenWeatherMap API continously to retrieve
 * its data in the form of a JSON object.
 *
 * @author apollo
 */
public class FetchJsonFromOwmApi extends ScheduledService<JSONObject> {

    private static final Logger log = LogManager.getLogger(FetchJsonFromOwmApi.class);
    private final String API_KEY = "97517582eb73ba175d97b91bee031261";     // hardcoded for now...should be stored in preference object
    private final String BASE_URL = "https://api.openweathermap.org";
    private final String BASE_PATH = "/data/2.5";
    private URL url;
    
    public FetchJsonFromOwmApi() {
        // For demonstration purposes...real value should be much higher to avoid
        // massive traffic (-> paid plans)
        this.setPeriod(Duration.minutes(30));
    }

    /**
     * Setter for the URL field
     *
     * @param resource The resource of the OWM API that is to be queried.
     * @param queryParams A map of query paramters and their names stored as
     * KVPs.
     * @throws java.net.URISyntaxException
     * @see URISyntaxException
     * @throws java.net.MalformedURLException
     * @see MalformedURLException
     */
    public void setUrl(String resource, HashMap<String, String> queryParams)
            throws URISyntaxException, MalformedURLException {
        queryParams.put("appid", API_KEY);
        URIBuilder uriBuilder = new URIBuilder(BASE_URL)
                .setPath(String.format("%s/%s", BASE_PATH, resource));
        queryParams.forEach((k, v) -> uriBuilder.addParameter(k, v));
        this.url = uriBuilder.build().toURL();
    }

    public URL getUrl() {
        return this.url;
    }

    /**
     * Reads response from an URL and stores it in a string
     *
     * @param url The URL the response should be read from.
     * @return String The response.
     * @throws IOException
     */
    private static String readUrl(URL url) throws IOException {
        try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = inputStream.readLine()) != null) {
                buffer.append(line);
            }
            log.debug("Read: " + buffer.toString() + "from " + url.toString());

            log.debug("closing open connections");
            IOUtils.closeQuietly(inputStream);
            log.debug("closed open connections");

            return buffer.toString();
        }
    }

    @Override
    protected Task<JSONObject> createTask() {
        return new Task<JSONObject>() {
            @Override
            protected JSONObject call() throws Exception {
                return new JSONObject(readUrl(getUrl()));
            }
        };
    }
}
