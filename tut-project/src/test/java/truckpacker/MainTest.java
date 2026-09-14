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
        Main.main(new String[]{ inputPath });

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
        Main.main(new String[]{ inputPath });

        // Because packing was impossible, it should print an error and NOT create a file
        File resultFile = new File(outputPath);
        assertFalse(resultFile.exists());
    }

    @Test
    public void testBadFormedIntegration() {
        String inputPath = "src/test/resources/bad_input.json";
        
        // This just tests that the program doesn't crash (throw an unhandled exception) 
        // when given bad input.
        Main.main(new String[]{ inputPath });
    }

    // tut3
    @Test
    public void testMultipleTrucksIntegration() {
        String inputPath = "src/test/resources/multiple_trucks_input.json";
        String outputPath = "src/test/resources/multiple_trucks_input_output.json";

        new File(outputPath).delete();

        Main.main(new String[]{ inputPath });

        File resultFile = new File(outputPath);
        // We only assert it exists because checking the exact JSON string match is brittle
        assertTrue("Output file for multiple trucks should be created", resultFile.exists());
    }
}