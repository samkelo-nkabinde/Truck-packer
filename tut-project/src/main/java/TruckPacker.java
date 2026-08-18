package truckpacker;

import java.util.ArrayList;
import java.util.List;

public class TruckPacker {

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

    public static Truck packTruck(int maxVolume, int maxItems, List<Item> inventory) {
        // Bundle the items using binary splitting
        List<Bundle> bundles = bundleItems(inventory);
        int n = bundles.size() - 1;

        // Convert bundles into the 1-indexed primitive arrays
        int[] itemSizes = new int[n + 1];
        int[] prices = new int[n + 1];
        String[] itemNames = new String[n + 1];
        int[] itemCounts = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            Bundle bundle = bundles.get(i);
            itemSizes[i] = bundle.volume;
            prices[i] = bundle.price;
            itemNames[i] = bundle.name;
            itemCounts[i] = bundle.count;
        }

        // Initialize the 3D array and run the core algorithm
        int[][][] data = new int[n + 1][maxVolume + 1][maxItems + 1];
        getOptimalMaximum(n, maxVolume, maxItems, prices, itemSizes, itemCounts, data);

        // Backtrack through the data array to load and return the physical Truck
        return loadTruck(n, maxVolume, maxItems, prices, itemSizes, itemCounts, data, itemNames);
    }

    private static List<Bundle> bundleItems(List<Item> inventory) {
        List<Bundle> bundles = new ArrayList<>();
        bundles.add(new Bundle("", 0, 0, 0)); // Dummy base-case for index 0

        for (Item item : inventory) {
            int quantity = item.getQuantity();
            int k = 1;
            
            while (quantity > 0) {
                int take = Math.min(k, quantity);
                bundles.add(new Bundle(
                    item.getName(), 
                    item.getVolume() * take, 
                    item.getPrice() * take, 
                    take
                ));
                quantity -= take;
                k *= 2;
            }
        }
        return bundles;
    }

    private static int getOptimalMaximum(int n, int maxVolume, int maxItems, int[] prices, int[] itemSizes, int[] itemCounts, int[][][] data) {
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

    private static Truck loadTruck(int n, int maxVolume, int maxItems, int[] prices, int[] itemSizes, int[] itemCounts, int[][][] data, String[] itemNames) {
        Truck packedTruck = new Truck(maxVolume, maxItems);
        
        int currentVolume = maxVolume;
        int currentCount = maxItems; 

        for (int i = n; i > 0; i--) {
            if (data[i][currentVolume][currentCount] != data[i - 1][currentVolume][currentCount]) {
                
                int quantity = itemCounts[i];
                int baseVolume = itemSizes[i] / quantity;
                int basePrice = prices[i] / quantity;
                
                Item item = new Item(itemNames[i], baseVolume, basePrice, quantity);
                packedTruck.addItem(item);
                
                currentVolume -= itemSizes[i];
                currentCount -= itemCounts[i];
            }
        }
        return packedTruck;
    }
}