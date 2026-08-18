package truckpacker;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class Main {

    static class Bundle {
        String name;
        int volume;
        int price;
        int count;

        public Bundle(String name, int volume, int price, int count) {
            this.name = name;
            this.volume = volume;
            this.price = price;
            this.count = count;
        }
    }

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

    public static List<Bundle> bundleItems(List<Item> inventory){
        List<Bundle> Bundles = new ArrayList<>();
        Bundles.add(new Bundle("", 0, 0, 0));

        for (Item item : inventory) {
            int quantity = item.getQuantity();
            int k = 1;
            
            while (quantity > 0) {
                int take = Math.min(k, quantity);
                
                Bundles.add(new Bundle(
                    item.getName(), 
                    item.getVolume() * take, 
                    item.getPrice() * take, 
                    take
                ));
                quantity -= take;
                k *= 2;
            }
        }
        return Bundles;
    }

    public static int getOptimalMaximum(int n, int maxVolume, int maxItems, int[] prices, int[] itemSizes, int[] itemCounts, int[][][] data){
        for(int item = 1; item <= n; item += 1){
            for(int volume = 1; volume <= maxVolume; volume += 1){
                for(int count = 1; count <= maxItems; count += 1){
                    
                    if(itemSizes[item] <= volume && itemCounts[item] <= count){
                        data[item][volume][count] = Math.max(
                            (prices[item] + data[item - 1][volume - itemSizes[item]][count - itemCounts[item]]), 
                            data[item - 1][volume][count]
                        );
                    }
                    else {
                        data[item][volume][count] = data[item - 1][volume][count];
                    }
                }
            }
        }

        return data[n][maxVolume][maxItems];
    }

    public static void PrintIncludedItems(int n, int maxVolume, int maxItems, int[] itemSizes, int[] itemCounts, int[][][] data, String[] itemNames)
    {
        int currentVolume = maxVolume;
        int currentCount = maxItems; 

        System.out.println("Items included in the truck:");

        for (int i = n; i > 0; i--)
        {
            if (data[i][currentVolume][currentCount] != data[i - 1][currentVolume][currentCount])
            {
                System.out.printf("Packed %d unit(s) of %s (Total Volume: %d)\n", itemCounts[i], itemNames[i], itemSizes[i]);
                
                currentVolume -= itemSizes[i];
                currentCount -= itemCounts[i];
            }
        }
    }
}