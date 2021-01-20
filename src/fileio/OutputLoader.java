package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Class that is used for printing information in json file.
 */
public final class OutputLoader {
    /**
     * Path to the output file.
     */
    private final String outputFile;

    public OutputLoader(final String outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * Method that writes data in pretty format in a json file.
     * @return an Output object.
     */
    public void writeData(final Output output) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFile), output);
    }
}
