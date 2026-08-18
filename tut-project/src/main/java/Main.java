package truckpacker;

public class Main {
    public static void main(String[] argv){
        
        int[] weights = { 0, 8, 2, 6, 1};
        int[] values = { 0, 50, 150, 210, 30 };

        String[] itemNames = { "", "Microwave", "Drone", "Monitor", "Kettle" };

        int[][] data = new int[5][11];

        int maxCapacity = 10;
        int n = 4;

        int result = getOptimalMaximum(n, maxCapacity, values, weights, data);
        PrintIncludedItems(n, maxCapacity, weights, data, itemNames);

        return;
    }

    public static int getOptimalMaximum(int n, int maxCapacity, int[] values, int[] weights, int[][] data){
        for(int iteam = 0; iteam <= n; iteam += 1){
            for(int capacity = 0; capacity <= maxCapacity; capacity += 1){
                if(iteam == 0 || capacity == 0){
                    data[iteam][capacity] = 0;
                }
                else if(weights[iteam] <= capacity){
                    data[iteam][capacity] = Math.max(
                        (values[iteam] + data[iteam - 1][capacity - weights[iteam]]), 
                        data[iteam - 1][capacity]
                    );
                }
                else {
                    data[iteam][capacity] = data[iteam - 1][capacity];
                }
            }
        }

        return data[n][maxCapacity];
    }

    public static void PrintIncludedItems(int n, int maxCapacity, int[] weights, int[][] data, String[] itemNames)
    {
        int currentCapacity = maxCapacity;

        System.out.println("Items included in the container:");

        // Loop backwards from the last item down to the first
        for (int i = n; i > 0; i--)
        {
            if (data[i][currentCapacity] != data[i - 1][currentCapacity])
            {
                System.out.printf("Item %s (Weight: %d) was INCLUDED.\n", itemNames[i], weights[i]);
                
                currentCapacity -= weights[i];
            }
            else
            {
                System.out.printf("Item %s (Weight: %d) was INCLUDED.\n", itemNames[i], weights[i]);
            }
        }
    }

}