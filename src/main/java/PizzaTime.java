package pizzadelivery;

import java.util.*;
import java.io.*;
import java.util.regex.*;

public class PizzaDelivery {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to pizza delivery service, please fill in the information!");
        System.out.println("------------------------------------");

        while (true) {
            System.out.println("\nPizza Time - Online Ordering System");
            System.out.println("1. Place an Order");
            System.out.println("2. View Order History");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
           
            switch (choice) {
                case 1:
                    placeOrder(scanner);
                    break;
                case 2:
                    viewOrderHistory();
                    break;
                case 3:
                    System.out.println("Thank you for using Pizza Time!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
   
    private static void placeOrder(Scanner scanner) {
        System.out.print("Enter Street Name: ");
        String street = scanner.nextLine().trim();
       
        System.out.print("Enter Street Number: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid number, please enter again.");
            scanner.next();
        }
        int streetNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter City: ");
        String city = scanner.nextLine().trim();
       
        String postalCode;
        while (true) {
            System.out.print("Enter Postal Code (A1A 1A1): ");
            postalCode = scanner.nextLine().trim();
            if (isValidPostalCode(postalCode)) {
                break;
            } else {
                System.out.println("Invalid postal code format! Please use A1A 1A1 format.");
            }
        }
       
        System.out.print("Choose Pizza Size (Small/Medium/Large): ");
        String size = scanner.nextLine().trim();
       
        System.out.print("Enter number of toppings: ");
        int toppings = scanner.nextInt();
        scanner.nextLine();
       
        double totalCost = 10.0 + (toppings * 1.5);
        System.out.println("Total Cost: $" + totalCost);
       
        saveOrder(street, streetNumber, city, postalCode, size, toppings, totalCost);
    }
   
    private static void saveOrder(String street, int streetNumber, String city, String postalCode, String size, int toppings, double totalCost) {
        try {
            File file = new File("orders.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(new FileWriter(file, true));
            out.println("Street Number: " + streetNumber);
            out.println("Street Name: " + street);
            out.println("City: " + city);
            out.println("Postal Code: " + postalCode);
            out.println("Pizza Size: " + size);
            out.println("Toppings: " + toppings);
            out.println("Total Cost: $" + totalCost);
            out.println("----------------------------");
            out.close();
            System.out.println("Order saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving order: " + e.getMessage());
        }
    }
   
    private static void viewOrderHistory() {
        try (Scanner fileScanner = new Scanner(new File("orders.txt"))) {
            System.out.println("\nOrder History:");
            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("No order history found.");
        }
    }
   
    private static boolean isValidPostalCode(String postalCode) {
        if (postalCode.length() != 7) {
            return false; // Must be exactly 7 characters (A1A 1A1)
        }

        return Character.isLetter(postalCode.charAt(0)) &&
               Character.isDigit(postalCode.charAt(1)) &&
               Character.isLetter(postalCode.charAt(2)) &&
               postalCode.charAt(3) == ' ' &&
               Character.isDigit(postalCode.charAt(4)) &&
               Character.isLetter(postalCode.charAt(5)) &&
               Character.isDigit(postalCode.charAt(6));
    }

    }
}
