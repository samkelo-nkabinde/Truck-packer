public class Greeter {
    public static void greeter(String name) {
        System.out.println("Hello, " + name + 
                        ". Your name starts with the letter: " + "'" +
                        Character.toUpperCase(name.charAt(0)) + "'" + 
                        ", and end ends with the letter: " + "'"+
                        Character.toLowerCase(name.charAt(name.length() - 1)) + "'" + 
                        ", It has " + name.length() + " letters!");
        return;
    }

    public static void goodbye() {
        System.out.println("Goodbye!");
        return;
    }
    
    public static void main(String args[]) {
        greeter(args[0]); // Assuming the user will pass their name 
        return;
    }
}
