package de.eurogo.exam.util;

import de.eurogo.exam.endpoint.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Utility class for handling file operations
 *
 * Created by haykhayryan on 8/31/15.
 */
public final class FileHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileHandler.class);

    private static final String NEW_LINE_SEPARATOR = "\r\n";
    private static final String COMMA_DELIMITER = ",";
    private static final String FILE_NAME = "Result.csv";
    private static final String UTF8_ENCODING = "UTF-8";


    private FileHandler() {
    }

    /**
     * Generate CSV file
     *
     * @param endpointList - List<Endpoint> positions endpoints list
     * @return boolean - true and only true when CSV file has been successfully generated.
     */
    public static boolean generateCSV(List<Endpoint> endpointList) throws IOException {
        boolean status = false;

        //get runned JAR file path
        String path = FileHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        //remove JAR file name from path
        path = path.substring(0, path.lastIndexOf(File.separator) + 1);
        String decodedPath = URLDecoder.decode(path, UTF8_ENCODING);

        FileWriter fileWriter = new FileWriter(decodedPath + FILE_NAME);
        try {
            for (Endpoint endpoint : endpointList) {
                fileWriter.append(endpoint.getId());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(endpoint.getName());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(endpoint.getGeoPosition().getLatitude());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(endpoint.getGeoPosition().getLongitude());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                status = true;
            } catch (IOException e) {
                LOGGER.error("Internal error {}", Errors.INTERNAL_ERROR);
            }
        }


        return status;
    }
}
