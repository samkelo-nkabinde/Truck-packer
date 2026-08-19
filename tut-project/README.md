# Truck packer    

## Background     
This project implements a version of the [Knapsack algorithm](https://en.wikipedia.org/wiki/Knapsack_problem), to help a delivery company to effectively pack goods or items given the followng costrains: 
- The maximum volume of the truck
- The number of items or goods that can be packed per load, apparently there's criminals in this company 
- The number of avaible goods to pack

## Knapsack Algorithm 
Instead of a painfull to understand top down recursive approach (even [NASA does not like recursion](https://www.cs.otago.ac.nz/cosc345/resources/nasa-10-rules.htm)), this project uses a bottom up approach, to find the optimal maximum value of items that can be fit into the truck. 
Since the standard 0/1 Knapsack algorithm strictly assumes you either take an item 0 or 1 times. To handle inventory items with a `quantity > 1` , this project uses a Binary Splitting technique, to bundle items into groups of powers of 2. It uses the following bundle helper class: 
``` java
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
```
And the following function to perform the binary spliting:
``` java
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
```

Once the binary spiting is complete, the following function as the core logic of the Knapsack algorithm. It is an extended version from this [video](https://www.youtube.com/watch?v=hagBB17_hvg&list=WL&index=1&t=649s), adapted to include a third dimension [count] for the item limits.
``` java
private static int 
getOptimalMaximum(int n, int maxVolume, 
                int maxItems, int[] prices, 
                int[] itemSizes, int[] itemCounts, 
                int[][][] data) 
{
    for(int item = 0; item < n; item += 1){
        for(int volume = 1; volume <= maxVolume; volume += 1){
            for(int count = 1; count <= maxItems; count += 1){
                
                if(itemSizes[item] <= volume && itemCounts[item] <= count){
                    if (item == 0) {
                        data[item][volume][count] = prices[item];
                    } else {
                
                        data[item][volume][count] = Math.max(
                            (prices[item] 
                            + data[item - 1][volume - itemSizes[item]][count - itemCounts[item]]), 
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
```
## Prerequisites
- Java Development Kit (JDK): Version 21
- Apache Maven: 3.9+

## Usage
``` bash 
# Compile the project:
mvn clean compile

# Run 
mvn exec:java -Dexec.mainClass="truckpacker.Main" -Dexec.args="path/to/inputfile.json"
```

## Input and Output
Only JSON input files are supported! The following is an example of what the JSON file should look like. 
``` JSON
{
  "truckVolume": 10,
  "maxItems": 4,
  "inventory": [
    { "name": "Microwave", "volume": 8, "price": 50, "quantity": 1 },
    { "name": "Drone", "volume": 2, "price": 150, "quantity": 3 },
    { "name": "Monitor", "volume": 6, "price": 210, "quantity": 2 },
    { "name": "Kettle", "volume": 1, "price": 30, "quantity": 5 }
  ]
}
```

The ouput is in JSON format as well, here is an example of output format:
``` JSON
[ {
  "name" : "Monitor",
  "volume" : 6,
  "price" : 210,
  "quantity" : 1
}, {
  "name" : "Drone",
  "volume" : 2,
  "price" : 150,
  "quantity" : 2
} ]
```
## Testing
Unit and integration tests are written with JUnit 4.
``` bash 
# Run tests
mvn test
```
## Continuous integration
This repository uses GitLab CI to automatically build and test the code on every push.

- Docker Image: maven:3.9-eclipse-temurin-21
- Runner: Uses a shared-docker-runner tag.

 Pipeline Stages:
 - build-job: Verifies the project compiles successfully.
 - test-job: Runs the JUnit test suite to verify algorithm correctness and data validation.

## References 
This [video](https://www.youtube.com/watch?v=hagBB17_hvg&list=WL&index=1&t=649s) was used to help with the knapsack algorithm. 