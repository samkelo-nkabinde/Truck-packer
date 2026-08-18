package truckpacker;

import java.util.List;

public class InputHandler {
    private int truckVolume;
    private int maxItems;
    private List<Item> inventory;

    
    public InputHandler() {
    }

    // Access methods
    public int getTruckVolume() { return truckVolume; }
    public int getMaxItems() { return maxItems; }
    public List<Item> getInventory() { return inventory; }

    // modifier methos
    public void setTruckVolume(int truckVolume) { this.truckVolume = truckVolume; }
    public void setMaxItems(int maxItems) { this.maxItems = maxItems; }
    public void setInventory(List<Item> inventory) { this.inventory = inventory; }
}