package truckpacker;

public class Item {
    private String name;
    private int volume;
    private int price;
    private int quantity;
    
    // One iteam constructor
    public Item(String name, int volume, int price) {
        this.name = name;
        this.volume = volume;
        this.price = price;
        this.quantity = 1; // if quantity count is not given assume it is only a single iteam
    }

    // constructor for greater than one quantity
    public Item(String name, int volume, int price, int quantity) {
        this.name = name;
        this.volume = volume;
        this.price = price;
        this.quantity = quantity; // Multiple iteams of the same type
    }

    // Access methods
    public String getName() {
        return name;
    }

    public int getVolume() {
        return volume;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}