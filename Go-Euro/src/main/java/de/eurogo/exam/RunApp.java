package de.eurogo.exam;

import de.eurogo.exam.client.MessageController;
import de.eurogo.exam.endpoint.Endpoint;
import de.eurogo.exam.util.Errors;
import de.eurogo.exam.util.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.List;

/**
 * Application Main start-point class
 * <p/>
 * Created by haykhayryan on 8/31/15.
 */
public class RunApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunApp.class);

    private static final String BASE_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";

    /**
     * Application start point main method
     *
     * @param args - arguments array
     */
    public static void main(String... args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Please run program with location name as command-line argument, e.g. " +
                    "java -jar GoEuroTest.jar CITY_NAME");
        }

        MessageController controller = new MessageController();

        try {
            //Send HTTP GET request to Rest service
            List<Endpoint> endpoints = controller.doGet(BASE_URL + args[0]);

            if (endpoints != null && !endpoints.isEmpty()) {
                LOGGER.info("Received endpoints element size is : {}", endpoints.size());
                //Generate CSV file
                if (FileHandler.generateCSV(endpoints)) {
                    LOGGER.info("CSV file successfully generated");
                }
            }
        } catch (IOException e) {
            LOGGER.error("Internal error {}", Errors.INTERNAL_ERROR);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Not valid argument {}", Errors.ARGUMENT_ERROR);
        } catch (HTTPException ex) {
            LOGGER.error("HTTP response error {}", Errors.HTTP_ERROR);
        }
    }
}
