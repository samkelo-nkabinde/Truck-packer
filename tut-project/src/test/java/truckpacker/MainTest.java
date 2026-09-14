package truckpacker;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;

public class MainTest {

    // json tests
    @Test
    public void testValidPackingIntegration() {
        String inputPath = "src/test/resources/valid_input.json";
        String outputPath = "src/test/resources/valid_input_output.json";
        new File(outputPath).delete();

        Main.main(new String[]{ "--file", inputPath }); // Fixed flag!

        File resultFile = new File(outputPath);
        assertTrue(resultFile.exists());
    }

    @Test
    public void testImpossiblePackingIntegration() {
        String inputPath = "src/test/resources/impossible_input.json";
        String outputPath = "src/test/resources/impossible_input_output.json";
        new File(outputPath).delete();

        Main.main(new String[]{ "--file", inputPath }); // Fixed flag!

        File resultFile = new File(outputPath);
        assertFalse(resultFile.exists());
    }

    @Test
    public void testBadFormedIntegration() {
        String inputPath = "src/test/resources/bad_input.json";
        Main.main(new String[]{ "--file", inputPath }); // Fixed flag!
    }

    @Test
    public void testMultipleTrucksIntegration() {
        String inputPath = "src/test/resources/multiple_trucks_input.json";
        String outputPath = "src/test/resources/multiple_trucks_input_output.json";
        new File(outputPath).delete();

        Main.main(new String[]{ "--file", inputPath }); // Fixed flag!

        File resultFile = new File(outputPath);
        assertTrue("Output file for multiple trucks should be created", resultFile.exists());
    }

    // xml tests

    @Test
    public void testValidXmlPackingIntegration() {
        String inputPath = "src/test/resources/valid_input.xml";
        String outputPath = "src/test/resources/valid_input_output.xml";
        new File(outputPath).delete();

        Main.main(new String[]{ "--file", inputPath });

        File resultFile = new File(outputPath);
        assertTrue("Output XML file should be created", resultFile.exists());
    }

    @Test
    public void testImpossibleXmlPackingIntegration() {
        String inputPath = "src/test/resources/impossible_input.xml";
        String outputPath = "src/test/resources/impossible_input_output.xml";
        new File(outputPath).delete();

        Main.main(new String[]{ "--file", inputPath });

        File resultFile = new File(outputPath);
        assertFalse("Impossible XML packing should not create an output file", resultFile.exists());
    }

    @Test
    public void testBadFormedXmlIntegration() {
        String inputPath = "src/test/resources/bad_input.xml";
        // Just tests that it handles the XML parsing exception gracefully
        Main.main(new String[]{ "--file", inputPath }); 
    }

    @Test
    public void testMultipleTrucksXmlIntegration() {
        String inputPath = "src/test/resources/multiple_trucks_input.xml";
        String outputPath = "src/test/resources/multiple_trucks_input_output.xml";
        new File(outputPath).delete();

        Main.main(new String[]{ "--file", inputPath });

        File resultFile = new File(outputPath);
        assertTrue("Output XML file for multiple trucks should be created", resultFile.exists());
    }
}