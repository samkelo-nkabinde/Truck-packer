package truckpacker;

public class Main {
    public static void main(String[] argv){
        
        int[] itemSizes = { 0, 8, 2, 6, 1 };
        int[] prices = { 0, 50, 150, 210, 30 };
        String[] itemNames = { "", "Microwave", "Drone", "Monitor", "Kettle" };

        int maxVolume = 10;
        int maxItems = 2; //Theft cap limit
        int n = 4;

        int[][][] data = new int[n + 1][maxVolume + 1][maxItems + 1];

        int result = getOptimalMaximum(n, maxVolume, maxItems, prices, itemSizes, data);
        System.out.println("Max Value: " + result);
        
        PrintIncludedItems(n, maxVolume, maxItems, itemSizes, data, itemNames);
    }

    public static int getOptimalMaximum(int n, int maxVolume, int maxItems, int[] prices, int[] itemSizes, int[][][] data){
        // Start loops at 1 since index 0 represents "0 items", "0 volume", or "0 count"
        for(int item = 1; item <= n; item += 1){
            for(int volume = 1; volume <= maxVolume; volume += 1){
                for(int count = 1; count <= maxItems; count += 1){
                    
                    // If the item fits in the truck
                    if(itemSizes[item] <= volume){
                        data[item][volume][count] = Math.max(
                            // Include the item: add its value, subtract its weight, subtract 1 from item count
                            (prices[item] + data[item - 1][volume - itemSizes[item]][count - 1]), 
                            // Exclude the item
                            data[item - 1][volume][count]
                        );
                    }
                    else {
                        // Item doesn't fit, carry over the previous best
                        data[item][volume][count] = data[item - 1][volume][count];
                    }
                }
            }
        }

        return data[n][maxVolume][maxItems];
    }

    public static void PrintIncludedItems(int n, int maxVolume, int maxItems, int[] itemSizes, int[][][] data, String[] itemNames)
    {
        int currentVolume = maxVolume;
        int currentCount = maxItems; // NEW: Track remaining allowed count

        System.out.println("Items included in the truck:");

        // Loop backwards from the last item down to the first
        for (int i = n; i > 0; i--)
        {
            // If the value changed from the row above, we included this item
            if (data[i][currentVolume][currentCount] != data[i - 1][currentVolume][currentCount])
            {
                System.out.printf("Item %s (Volume: %d) was INCLUDED.\n", itemNames[i], itemSizes[i]);
                
                // Deduct both the volume and the item count
                currentVolume -= itemSizes[i];
                currentCount -= 1;
            }
        }
    }
}