package de.eurogo.exam.test;

import de.eurogo.exam.client.MessageController;
import de.eurogo.exam.endpoint.Endpoint;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test for accepting functional
 * <p/>
 * Created by haykhayryan on 8/31/15.
 */
public class GoEuroTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoEuroTest.class);

    private static final String BASE_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";

    private String[] cities;

    @Before
    public void prepareData() {
        LOGGER.info("======== Preparing Data For Test ========");
        cities = new String[]{"Berlin", "Moscow", "Yerevan", "Paris", "Munich"};
    }

    @Test
    public void testMessageController() throws IOException {
        LOGGER.info("======== START MessageController Test ========");
        MessageController controller = new MessageController();

        for (String city : cities) {

            LOGGER.info("Send HTTP GET request using URL: {}", BASE_URL + city);
            List<Endpoint> endpoints = controller.doGet(BASE_URL + city);

            assertNotNull("The endpoint list should be initialized", endpoints);
            assertEquals("Location name must be equals", city, endpoints.get(0).getName());

            LOGGER.info("JSON response successfully received and parsed");
        }
    }
}
