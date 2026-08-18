package truckpacker;

public class Item {
    private String name;
    private double volume;
    private double price;
    private int quantity;

    // One iteam constructor
    public Item(String name, double volume, int price) {
        this.name = name;
        this.volume = volume;
        this.price = price;
        this.quantity = 1; // if quantity count is not given assume it is only a single iteam
    }

    // constructor for greater than one quantity
    public Item(String name, double volume, double price, int quantity) {
        this.name = name;
        this.volume = volume;
        this.price = price;
        this.quantity = quantity; // Multiple iteams of the same type
    }

    // Access methods
    public String getName() {
        return name;
    }

    public double getVolume() {
        return volume;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}