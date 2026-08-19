package truckpacker;

import static org.junit.Assert.*;
import org.junit.Test;

public class ItemTest {

    @Test
    public void testEmptyConstructorAndSetters() {
        Item item = new Item();
        item.setName("Monitor");
        item.setVolume(6);
        item.setPrice(210);
        item.setQuantity(2);

        assertEquals("Monitor", item.getName());
        assertEquals(6, item.getVolume());
        assertEquals(210, item.getPrice());
        assertEquals(2, item.getQuantity());
    }

    @Test
    public void testThreeArgumentConstructor() {
        Item item = new Item("Microwave", 8, 50);

        assertEquals("Microwave", item.getName());
        assertEquals(8, item.getVolume());
        assertEquals(50, item.getPrice());
        assertEquals(1, item.getQuantity()); 
    }

    @Test
    public void testFourArgumentConstructor() {
        Item item = new Item("Drone", 2, 150, 3);

        assertEquals("Drone", item.getName());
        assertEquals(2, item.getVolume());
        assertEquals(150, item.getPrice());
        assertEquals(3, item.getQuantity());
    }

    @Test
    public void testAddQuantityValid() {
        Item item = new Item("Kettle", 1, 30, 2);
        item.addQuantity(3);
        assertEquals(5, item.getQuantity());
    }

    //negative cases

    @Test
    public void testNameValidation() {
        Item item = new Item();
        
        assertThrows(IllegalArgumentException.class, () -> {
            item.setName(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            item.setName("   "); 
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Item("", 5, 100); 
        });
    }

    @Test
    public void testVolumeValidation() {
        Item item = new Item();
        
        assertThrows(IllegalArgumentException.class, () -> {
            item.setVolume(0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            item.setVolume(-5);
        });
    }

    @Test
    public void testPriceValidation() {
        Item item = new Item();
        
        assertThrows(IllegalArgumentException.class, () -> {
            item.setPrice(-10);
        });
        
    }

    @Test
    public void testQuantityValidation() {
        Item item = new Item();
        
        assertThrows(IllegalArgumentException.class, () -> {
            item.setQuantity(0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            item.setQuantity(-1);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            item.addQuantity(-5);
        });
    }
}