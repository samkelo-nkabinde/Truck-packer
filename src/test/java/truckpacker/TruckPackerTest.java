package truckpacker;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


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
}