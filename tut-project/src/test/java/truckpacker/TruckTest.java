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

    // tut 3
    @Test
    public void testNewConstructorAndGetters() {
        Truck truck = new Truck("T-001", 100, 50, 125.50);
        
        assertEquals("T-001", truck.getId());
        assertEquals(100, truck.getMaxVolume());
        assertEquals(50, truck.getMaxItems());
        assertEquals(125.50, truck.getCost(), 0.001); // 0.001 is the delta for comparing doubles
    }

    @Test
    public void testOldConstructorDefaults() {
        Truck truck = new Truck(10, 4);
        
        assertEquals("T-Single", truck.getId());
        assertEquals(0.0, truck.getCost(), 0.001);
    }

    @Test
    public void testRemoveLastItem_Completely() {
        Truck truck = new Truck("T1", 20, 10, 50.0);
        Item item = new Item("Drone", 2, 150, 1);
        truck.addItem(item);
        
        assertEquals(1, truck.getItems().size());
        
        // Backtrack and remove it
        truck.removeLastItem(new Item("Drone", 2, 150, 1));
        
        assertTrue(truck.getItems().isEmpty());
        assertEquals(0, truck.getCurrentVolume());
        assertEquals(0, truck.getCurrentItemCount());
    }

    @Test
    public void testRemoveLastItem_PartialQuantity() {
        Truck truck = new Truck("T1", 20, 10, 50.0);
        Item item = new Item("Drone", 2, 150, 3);
        truck.addItem(item);
        
        assertEquals(3, truck.getCurrentItemCount());
        
        // Backtrack just 1 drone 
        truck.removeLastItem(new Item("Drone", 2, 150, 1));
        
        assertEquals(1, truck.getItems().size()); // Still 1 item object in list
        assertEquals(2, truck.getCurrentItemCount()); // But the quantity dropped to 2
        assertEquals(2, truck.getItems().get(0).getQuantity());
        assertEquals(4, truck.getCurrentVolume());
    }
}