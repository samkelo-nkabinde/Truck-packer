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

        List<Bundle> bundles = bundleItems(inventory);
        int n = bundles.size();

        // if inventory is completely empty, return an empty truck
        if (n == 0) {
            return new Truck(maxVolume, maxItems);
        }

        // Convert bundles into 0-indexed primitive arrays
        int[] itemSizes = new int[n];
        int[] prices = new int[n];
        String[] itemNames = new String[n];
        int[] itemCounts = new int[n];

        for (int i = 0; i < n; i++) {
            Bundle bundle = bundles.get(i);
            itemSizes[i] = bundle.volume;
            prices[i] = bundle.price;
            itemNames[i] = bundle.name;
            itemCounts[i] = bundle.count;
        }

        int[][][] data = new int[n][maxVolume + 1][maxItems + 1];
        getOptimalMaximum(n, maxVolume, maxItems, prices, itemSizes, itemCounts, data);

        // Backtrack through the data array
        return loadTruck(n, maxVolume, maxItems, prices, itemSizes, itemCounts, data, itemNames);
    }

    // Bundle the items using binary splitting
    // This helps avoid increase the time complexity of the knapsack algo
    private static List<Bundle> bundleItems(List<Item> inventory) {
        List<Bundle> bundles = new ArrayList<>();

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

    // core zero one knapsack algorithm extended to fit constrains
    // source https://youtu.be/hagBB17_hvg?si=o8CAhYvalVisxy9g
    private static int getOptimalMaximum(int n, int maxVolume, int maxItems, int[] prices, int[] itemSizes, int[] itemCounts, int[][][] data) {
        for(int item = 0; item < n; item += 1){
            for(int volume = 1; volume <= maxVolume; volume += 1){
                for(int count = 1; count <= maxItems; count += 1){
                    
                    if(itemSizes[item] <= volume && itemCounts[item] <= count){
                        if (item == 0) {
                            data[item][volume][count] = prices[item];
                        } else {
                    
                            data[item][volume][count] = Math.max(
                                (prices[item] + data[item - 1][volume - itemSizes[item]][count - itemCounts[item]]), 
                                data[item - 1][volume][count]
                            );
                        }
                    }
                    else {
                        if (item > 0) {
                            // Carry forward the previous best if can't fit this item
                            data[item][volume][count] = data[item - 1][volume][count];
                        }
                    }
                }
            }
        }
        return data[n - 1][maxVolume][maxItems];
    }

    private static Truck loadTruck(int n, int maxVolume, int maxItems, int[] prices, int[] itemSizes, int[] itemCounts, int[][][] data, String[] itemNames) {
        Truck packedTruck = new Truck(maxVolume, maxItems);
        
        int currentVolume = maxVolume;
        int currentCount = maxItems; 

        for (int i = n - 1; i >= 0; i--) {
            boolean isItemPacked = false;

            if (i > 0) {
                // If it's not the first item, check if the value changed from the previous item
                if (data[i][currentVolume][currentCount] != data[i - 1][currentVolume][currentCount]) {
                    isItemPacked = true;
                }
            } else {
                // If it is the 0th item, if there's any value greater than 0, it means it packed
                if (data[i][currentVolume][currentCount] > 0) {
                    isItemPacked = true;
                }
            }

            if (isItemPacked) {
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