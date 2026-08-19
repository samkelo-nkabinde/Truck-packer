package truckpacker;

import static org.junit.Assert.*;
import org.junit.Test;


public class TruckTest {

    @Test
    public void testEmptyTruck() {
        Truck truck = new Truck(10, 4);
        
        assertEquals(10, truck.getMaxVolume());
        assertEquals(4, truck.getMaxItems());
        assertEquals(0, truck.getCurrentVolume());
        assertEquals(0, truck.getCurrentItemCount());
        assertEquals(0, truck.getTotalPriceValue());
        assertTrue(truck.getItems().isEmpty());
    }

    @Test
    public void testAddSingleItem() {
        Truck truck = new Truck(10, 4);
        Item item = new Item("Drone", 2, 150, 1);
        
        boolean isAdded = truck.addItem(item);
        
        assertTrue(isAdded);
        assertEquals(2, truck.getCurrentVolume());
        assertEquals(1, truck.getCurrentItemCount());
        assertEquals(150, truck.getTotalPriceValue());
        assertEquals(1, truck.getItems().size());
    }

    @Test
    public void testCombineIdenticalItems() {
        Truck truck = new Truck(10, 5);
        Item item1 = new Item("Drone", 2, 150, 1);
        Item item2 = new Item("Drone", 2, 150, 2);
        
        truck.addItem(item1);
        boolean isAdded = truck.addItem(item2);
        
        assertTrue(isAdded);
        // Should combine into a single object in the list
        assertEquals(1, truck.getItems().size());
        
        assertEquals(3, truck.getCurrentItemCount());
        assertEquals(6, truck.getCurrentVolume()); 
        assertEquals(450, truck.getTotalPriceValue()); 
        assertEquals(3, truck.getItems().get(0).getQuantity());
    }

    @Test
    public void testCannotExceedMaxVolume() {
        Truck truck = new Truck(10, 4);
        Item bigItem = new Item("Fridge", 8, 200, 1);
        Item mediumItem = new Item("Microwave", 4, 50, 1);
        
        truck.addItem(bigItem);
        boolean isAdded = truck.addItem(mediumItem); 
        
        assertFalse(isAdded);
        assertEquals(8, truck.getCurrentVolume()); // Truck state should remain unchanged
        assertEquals(1, truck.getCurrentItemCount());
    }

    @Test
    public void testCannotExceedIteamCap() {
        Truck truck = new Truck(20, 3); 
        Item kettleBundle = new Item("Kettle", 1, 30, 4);
        
        boolean isAdded = truck.addItem(kettleBundle);
        
        assertFalse(isAdded);
        assertEquals(0, truck.getCurrentItemCount());
        assertTrue(truck.getItems().isEmpty());
    }

    @Test
    public void testAddMultipleDistinctItems() {
        Truck truck = new Truck(10, 4);
        truck.addItem(new Item("Drone", 2, 150, 2));
        truck.addItem(new Item("Kettle", 1, 30, 1));
        
        assertEquals(3, truck.getCurrentItemCount());
        assertEquals(5, truck.getCurrentVolume());
        assertEquals(330, truck.getTotalPriceValue());
        assertEquals(2, truck.getItems().size());
    }
}