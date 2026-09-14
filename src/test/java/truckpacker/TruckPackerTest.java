package truckpacker;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class TruckPackerTest {

    @Test
    public void testEmptyInventory() {
        List<Item> emptyInventory = new ArrayList<>();

        Truck packedTruck = TruckPacker.packTruck(10, 5, emptyInventory);
        
        assertTrue(packedTruck.getItems().isEmpty());
        assertEquals(0, packedTruck.getTotalPriceValue());
        assertEquals(0, packedTruck.getCurrentVolume());
        assertEquals(0, packedTruck.getCurrentItemCount());
    }

    @Test
    public void testBasicPacking() {
        List<Item> inventory = new ArrayList<>();
        inventory.add(new Item("Laptop", 5, 100, 1));
        inventory.add(new Item("Monitor", 6, 120, 1)); 
        
        Truck packedTruck = TruckPacker.packTruck(10, 2, inventory);
        
        assertEquals(120, packedTruck.getTotalPriceValue());
        assertEquals(6, packedTruck.getCurrentVolume());
        assertEquals(1, packedTruck.getCurrentItemCount());
    }

    @Test
    public void testTheftCapLimitation() {
        List<Item> inventory = new ArrayList<>();
        inventory.add(new Item("Phone", 1, 50, 5)); 
        
        Truck packedTruck = TruckPacker.packTruck(10, 3, inventory);
        
        assertEquals(150, packedTruck.getTotalPriceValue()); 
        assertEquals(3, packedTruck.getCurrentVolume());     
        assertEquals(3, packedTruck.getCurrentItemCount());
    }

    @Test
    public void testVolumeLimitationOverridesQuantity() {
        List<Item> inventory = new ArrayList<>();
        inventory.add(new Item("Drone", 3, 200, 4));
        
        Truck packedTruck = TruckPacker.packTruck(8, 10, inventory);
        
        assertEquals(400, packedTruck.getTotalPriceValue());
        assertEquals(6, packedTruck.getCurrentVolume());
        assertEquals(2, packedTruck.getCurrentItemCount());
    }

    @Test
    public void testComplexOptimization() {
        List<Item> inventory = new ArrayList<>();
        inventory.add(new Item("Item A", 2, 100, 1));
        inventory.add(new Item("Item B", 3, 120, 1));  
        inventory.add(new Item("Item C", 4, 150, 1)); 

        Truck packedTruck = TruckPacker.packTruck(6, 2, inventory);
        
        assertEquals(250, packedTruck.getTotalPriceValue());
        assertEquals(6, packedTruck.getCurrentVolume());
        assertEquals(2, packedTruck.getCurrentItemCount());
    }

    @Test
    public void testImpossiblePacking() {
        List<Item> inventory = new ArrayList<>();

        inventory.add(new Item("Microwave", 5, 50, 1));
        
        Truck packedTruck = TruckPacker.packTruck(2, 2, inventory);
        
        assertTrue(packedTruck.getItems().isEmpty());
        assertEquals(0, packedTruck.getTotalPriceValue());
        assertEquals(0, packedTruck.getCurrentVolume());
        assertEquals(0, packedTruck.getCurrentItemCount());
    }

    // tut 3
    @Test
    public void testPackMultipleTrucks_MinimizesCost() {
        Item bigBoxes = new Item("Big Box", 50, 0, 2);   
        Item smallBox = new Item("Small Box", 30, 0, 1); 
        List<Item> inventory = Arrays.asList(bigBoxes, smallBox);

        Truck cheapSmall = new Truck("T1-Cheap-Small", 50, 5, 40.0);
        Truck medium = new Truck("T2-Medium", 100, 5, 100.0);
        Truck expensiveBig = new Truck("T3-Expensive-Big", 150, 5, 150.0);
        
        List<Truck> fleet = Arrays.asList(cheapSmall, medium, expensiveBig);

        TruckPacker packer = new TruckPacker();
        List<Truck> result = packer.packMultipleTrucks(fleet, inventory);

        assertEquals("Should use 2 trucks to minimize cost", 2, result.size());
        
        double totalCost = 0;
        for (Truck t : result) {
            totalCost += t.getCost();
        }
        assertEquals("Optimal cost should be 140.0", 140.0, totalCost, 0.001);
        
        int totalItemsPacked = 0;
        for (Truck t : result) {
            totalItemsPacked += t.getCurrentItemCount();
        }
        assertEquals("All 3 items must be packed", 3, totalItemsPacked);
    }
    
    @Test
    public void testPackMultipleTrucks_ImpossibleToFit() {
        Item hugeBox = new Item("Huge Box", 500, 0, 1);
        List<Item> inventory = Arrays.asList(hugeBox);
        
        Truck smallTruck = new Truck("T1-Small", 100, 5, 50.0);
        List<Truck> fleet = Arrays.asList(smallTruck);
        
        TruckPacker packer = new TruckPacker();
        List<Truck> result = packer.packMultipleTrucks(fleet, inventory);
        
        assertTrue("Should return empty list when impossible to pack", result.isEmpty());
    }
}