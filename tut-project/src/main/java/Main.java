package truckpacker;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] argv){

        if (argv.length < 1) {
            System.err.println("Error: No input file provided.");
            return;
        }
        
        String inputFilePath = argv[0];
        InputHandler inputData;
        
        // Parse the JSON file
        ObjectMapper mapper = new ObjectMapper();
        try {
            inputData = mapper.readValue(new File(inputFilePath), InputHandler.class);
        } catch (IOException e) {
            System.err.println("Error: Unable to read or parse the input file.");
            e.printStackTrace();
            return; 
        }

        int maxVolume = inputData.getTruckVolume();
        int maxItems = inputData.getMaxItems();
        List<Item> inventory = inputData.getInventory();

        List<Bundle> Bundles = bundleItems(inventory);
    
        int n = Bundles.size() - 1;
        int[] itemSizes = new int[n + 1];
        int[] prices = new int[n + 1];
        String[] itemNames = new String[n + 1];
        int[] itemCounts = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            Bundle bundle = Bundles.get(i);
            itemSizes[i] = bundle.volume;
            prices[i] = bundle.price;
            itemNames[i] = bundle.name;
            itemCounts[i] = bundle.count;
        }

        int[][][] data = new int[n + 1][maxVolume + 1][maxItems + 1];

        int result = getOptimalMaximum(n, maxVolume, maxItems, prices, itemSizes, itemCounts, data);
        System.out.println("Max Value: " + result);
        
        PrintIncludedItems(n, maxVolume, maxItems, itemSizes, itemCounts, data, itemNames);
    }

    
}