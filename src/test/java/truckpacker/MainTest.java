package truckpacker;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;


public class MainTest {

    @Test
    public void testValidPackingIntegration() {
        String inputPath = "src/test/resources/valid_input.json";
        String outputPath = "src/test/resources/valid_input_output.json";

        // Clean up any old files before testing
        new File(outputPath).delete();

        // Run the whole program
        Main.main(new String[]{ inputPath, outputPath });

        // The program must successfully create the output file
        File resultFile = new File(outputPath);
        assertTrue(resultFile.exists());
    }

    @Test
    public void testImpossiblePackingIntegration() {
        String inputPath = "src/test/resources/impossible_input.json";
        String outputPath = "src/test/resources/impossible_input_output.json";

        new File(outputPath).delete();

        // Run the whole program
        Main.main(new String[]{ inputPath, outputPath });

        // Because packing was impossible, it should print an error and NOT create a file
        File resultFile = new File(outputPath);
        assertFalse(resultFile.exists());
    }

    @Test
    public void testBadFormedIntegration() {
        String inputPath = "src/test/resources/bad_input.json";

        Main.main(new String[]{ inputPath });
    }
}