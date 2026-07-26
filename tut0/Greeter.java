import java.util.Scanner;

public class Greeter {
    private static Scanner scanner = new Scanner(System.in);

    public static String getName() {
        System.out.print("Enter your name: ");
        return scanner.nextLine();
    }

    public static void main(String args[]) {
        String name = getName();
        System.out.println("Hello, " + name + "!");
    }
}