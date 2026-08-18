package truckpacker;

import java.util.ArrayList;
import java.util.List;

public class Truck {
    private int maxVolume;
    private int maxItems;
    private List<Item> items; // items currently packed

    public Truck(int maxVolume, int maxItems) {
        this.maxVolume = maxVolume;
        this.maxItems = maxItems;
        this.items = new ArrayList<>();
    }

    // Acess methods
    public int getMaxVolume() {
        return this.maxVolume;
    }

    public int getMaxItems() {
        return this.maxItems;
    }

    public List<Item> getItems() {
        return this.items;
    }

    // total volume of all elemnts packed in the truck
    public int getCurrentVolume() {
        int volume = 0;

        for (Item item : this.items) {
            volume += item.getVolume();
        }

        return volume;
    }

    public boolean canAddItem(Item item) {
        return getCurrentVolume() + item.getVolume() <= this.maxVolume
                && this.items.size() < this.maxItems;
    }

    public boolean addItem(Item item) {
        if (!canAddItem(item)) {
            return false;
        }

        this.items.add(item);
        return true;
    }

    public int getTotalPriceValue() {
        int priceValue = 0;

        for (Item item : this.items) {
            priceValue += item.getPrice();
        }

        return priceValue;
    }
}