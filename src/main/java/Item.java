package truckpacker;

public class Item {
    private String name;
    private int volume;
    private int price;
    private int quantity;
    
    // Constructor for the json parser
    public Item() {}
    
    // One item constructor
    public Item(String name, int volume, int price) {
        setName(name);
        setVolume(volume);
        setPrice(price);
        setQuantity(1);
    }

    // Constructor for greater than one quantity
    public Item(String name, int volume, int price, int quantity) {
        setName(name);
        setVolume(volume);
        setPrice(price);
        setQuantity(quantity); 
    }

    public void addQuantity(int extraQuantity) {
        if (extraQuantity < 0) {
            throw new IllegalArgumentException("Cannot add negative quantity");
        }
        this.quantity += extraQuantity;
    }

    // modifiers 
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty.!");
        }
        this.name = name;
    }

    public void setVolume(int volume) {
        if (volume <= 0) {
            throw new IllegalArgumentException("Item volume must be greater than zero!");
        }
        this.volume = volume;
    }

    public void setPrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Item price cannot be negative!");
        }
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Item quantity must be greater than zero!");
        }
        this.quantity = quantity;
    }

    // access methods
    public String getName() { return this.name; }
    public int getVolume() { return this.volume; }
    public int getPrice() { return this.price; }
    public int getQuantity() { return this.quantity; }
}