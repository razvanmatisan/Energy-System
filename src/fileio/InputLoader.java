package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Class that reads all information from a json file.
 */
public final class InputLoader {
    private final String inputFile;

    public InputLoader(final String inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * Method that reads the database
     * @return an Input object
     */
    public Input readData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input input = objectMapper.readValue(new File(inputFile), Input.class);
        return input;
    }
}

