package truckpacker;

import java.util.ArrayList;
import java.util.List;

public class Truck {
    private String id;
    private double cost;
    private int maxVolume;
    private int maxItems;
    private List<Item> items; // items currently packed

    public Truck(String id, int maxVolume, int maxItems, double cost) {
        this.id = id;
        this.cost = cost;
        this.maxVolume = maxVolume;
        this.maxItems = maxItems;
        this.items = new ArrayList<>();
    }

    // Acess methods
    public String getId(){
        return this.id;
    }

    public double getCost(){
        return this.cost;
    }

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
            // Multiply volume by quantity
            volume += (item.getVolume() * item.getQuantity());
        }
        return volume;
    }

    // total number of items
    public int getCurrentItemCount() {
        int count = 0;
        for (Item item : this.items) {
            count += item.getQuantity();
        }
        return count;
    }

    public boolean canAddItem(Item item) {
        return getCurrentVolume() + (item.getVolume() * item.getQuantity()) <= this.maxVolume
                && getCurrentItemCount() + item.getQuantity() <= this.maxItems;
    }

    public boolean addItem(Item newItem) {
        if (!canAddItem(newItem)) {
            return false;
        }

        for (Item existingItem : this.items) {
            if (existingItem.getName().equals(newItem.getName())) {
                // increase quntity instead of adding new item
                existingItem.addQuantity(newItem.getQuantity());
                return true;
            }
        }

        // if item not found, add it as new item
        this.items.add(newItem);
        return true;
    }

    public void removeLastItem(Item itemToRemove) {
        for (int i = this.items.size() - 1; i >= 0; i--) {

            Item current = this.items.get(i);

            if (current.getName().equals(itemToRemove.getName())) {

                if (current.getQuantity() > itemToRemove.getQuantity()) {
                    int newQuantity = current.getQuantity() - itemToRemove.getQuantity();
                    Item reducedItem = new Item(current.getName(), current.getVolume(), current.getPrice(), newQuantity);
                    this.items.set(i, reducedItem);

                } else {
                    this.items.remove(i);
                }

                break;
            }
        }
    }

    public int getTotalPriceValue() {
        int priceValue = 0;
        for (Item item : this.items) {
            priceValue += (item.getPrice() * item.getQuantity());
        }
        return priceValue;
    }
}