package truckpacker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper; 
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] argv){
        
        if (argv.length > 0 && argv[0].equals("--server")) {
            try {
                TruckServer.startServer();
            } catch (IOException e) {
                System.err.println("Error: Unable to start the server.");
            }
            return;
        }

        if (argv.length < 2 || !argv[0].equals("--file")) {
            System.err.println("Error: Please provide '--server' or '--file <input path>'.");
            return;
        }
        
        String inputFilePath = argv[1]; // The path is now the second argument
        
        // XML output extension
        String outputFilePath = inputFilePath.replace(".json", "_output.json").replace(".xml", "_output.xml");
            
        if (outputFilePath.equals(inputFilePath)) {
            outputFilePath = inputFilePath + "_output.json";
        }

        InputHandler inputData;
        
        // Use XmlMapper if the file is XML 
        ObjectMapper mapper;
        if (inputFilePath.toLowerCase().endsWith(".xml")) {
            mapper = new XmlMapper();
        } else {
            mapper = new ObjectMapper();
        }

        // Parse the input file
        try {
            inputData = mapper.readValue(new File(inputFilePath), InputHandler.class);
        } catch (IOException e) {
            System.err.println("Error: Unable to read or parse the input file.");
            return; 
        }

       
        if (inputData.getFleet() != null && !inputData.getFleet().isEmpty()) {
            // tut 3
            TruckPacker packer = new TruckPacker();
            List<Truck> packedTrucks = packer.packMultipleTrucks(inputData.getFleet(), inputData.getInventory());
            
            if (packedTrucks.isEmpty()) {
                System.err.println("Error: Impossible to create a packing list that meets the requirements.");
                return;
            }

            // double totalCost = 0;
            // for (Truck truck : packedTrucks) {
            //     System.out.println("Truck ID: " + truck.getId() + " | Cost: R" + truck.getCost());
            //     System.out.println("Volume Used: " + truck.getCurrentVolume() + "/" + truck.getMaxVolume());
            //     System.out.println("Items Packed:");
            //     for (Item item : truck.getItems()) {
            //         System.out.println("- " + item.getQuantity() + "x " + item.getName());
            //     }
            //     totalCost += truck.getCost();
            // }

            // Write to output file
            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFilePath), packedTrucks);
            } catch (IOException e) {
                System.err.println("Error: Unable to write to the output file.");
            }

        } else {
            // tut 2
            Truck finalTruck = TruckPacker.packTruck(
                inputData.getTruckVolume(), 
                inputData.getMaxItems(), 
                inputData.getInventory()
            );

            if (finalTruck.getItems().isEmpty()) {
                System.err.println("Error: Impossible to create a packing list that meets the requirements.");
                return;
            }

            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFilePath), finalTruck.getItems());
            } catch (IOException e) {
                System.err.println("Error: Unable to write to the output file.");
            }
        }
    }
}