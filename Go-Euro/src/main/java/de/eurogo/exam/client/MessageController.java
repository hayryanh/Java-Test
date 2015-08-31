package de.eurogo.exam.client;

import de.eurogo.exam.endpoint.Endpoint;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.http.HTTPException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Message Controller is an HTTP methods response handler
 * <p/>
 * Created by haykhayryan on 8/31/15.
 */
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    public MessageController() {
    }


    /**
     * Send HTTP GET request using provided URL as argument and parsing received JSON data
     *
     * @param url - String HTTP url
     * @return List<Endpoint> - parsed JSON endpoints list
     *
     * @throws IllegalArgumentException - if provided url is null or empty
     * @throws HTTPException - if received HTTP error status code instead 200 OK
     * @throws IOException - if HTTP response buffered reading error
     */
    public List<Endpoint> doGet(final String url) throws IllegalArgumentException, HTTPException, IOException {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL must be initialized");
        }

        List<Endpoint> endpoints = null;
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);

        HttpResponse response = client.execute(get);
        int responseCode = response.getStatusLine().getStatusCode();

        if (responseCode == 200) {
            String responseBody = getJsonContent(response);
            if (!responseBody.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                endpoints = mapper.readValue(responseBody, new TypeReference<List<Endpoint>>() {
                });
            }
        } else {
            LOGGER.error("HTTP status code error: {}", responseCode);
            throw new HTTPException(responseCode);
        }

        return endpoints;
    }

    private String getJsonContent(HttpResponse response) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOGGER.error("BufferedReader closing error", e);
                }
            }
        }

        return sb.toString();
    }
}
