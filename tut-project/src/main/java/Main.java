package truckpacker;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] argv){

        if (argv.length < 1) {
            System.err.println("Error: Please provide an input file path.");
            return;
        }
        
        String inputFilePath = argv[0];
        String outputFilePath;
        
        outputFilePath = inputFilePath.replace(".json", "_output.json");
            
        // Just in case input file didn't have a .json extension
        if (outputFilePath.equals(inputFilePath)) {
            outputFilePath = inputFilePath + "_output.json";
        }

        InputHandler inputData;
        ObjectMapper mapper = new ObjectMapper();

        // Parse the input JSON file
        try {
            inputData = mapper.readValue(new File(inputFilePath), InputHandler.class);
        } catch (IOException e) {
            System.err.println("Error: Unable to read or parse the input file.");
            return; 
        }

        // Run the packing algorithm 
        Truck finalTruck = TruckPacker.packTruck(
            inputData.getTruckVolume(), 
            inputData.getMaxItems(), 
            inputData.getInventory()
        );

        // impossible constraint
        if (finalTruck.getItems().isEmpty()) {
            System.err.println("Error: Impossible to create a packing list that meets the requirements.");
            return;
        }

        // Write the loaded truck items to the output JSON file
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFilePath), finalTruck.getItems());
        } catch (IOException e) {
            System.err.println("Error: Unable to write to the output file.");
        }
    }
}