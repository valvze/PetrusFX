/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ilkeskin.petrusfx.utils;

import de.ilkeskin.petrusfx.menu.GetCityListForCountry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class that reads gzipped response from URL
 *
 * @author Ilyas Keskin
 */
public class GzippedUrlReader {

    private static final Logger log = LogManager.getLogger(GetCityListForCountry.class);
    private InputStream fileStream;
    private InputStream gzipStream;
    BufferedReader buffered;
    StringBuilder rawData;
    String line;

    public GzippedUrlReader() {
        this.fileStream = null;
        this.gzipStream = null;
        this.buffered = null;
        this.rawData = new StringBuilder();
        this.line = "";
    }

    /**
     * Reads gzipped response from an URL decodes it and stores it in a string
     *
     * @param url The URL that points to the gzipped resource.
     * @return String The decoded response.
     * @throws java.io.IOException
     * @see IOException
     */
    public String readUrl(String url) throws IOException {

        // read the gzipped city list from the URL, unzip it, buffer it and store it in a string
        fileStream = new URL(url).openStream();
        gzipStream = new GZIPInputStream(fileStream);
        log.debug("reading from: " + url);
        buffered = new BufferedReader(new InputStreamReader(gzipStream, "UTF-8"));
        while ((line = buffered.readLine()) != null) {
            rawData.append(line);
        }

        log.debug("closing open connections");
        IOUtils.closeQuietly(fileStream);
        IOUtils.closeQuietly(gzipStream);
        IOUtils.closeQuietly(buffered);
        log.debug("closed open connections");

        return rawData.toString();
    }

}
