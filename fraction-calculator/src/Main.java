import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Welcome message and instructions
        System.out.println("Welcome to the rational number calculator!");
        System.out.println("Enter a fraction in the format 'numerator/denominator' or a whole number.");
        System.out.println("Type 'exit' to quit.");

        Rational r1 = null; // First rational number
        Rational r2 = null; // Second rational number
        char operation = ' '; // Operation to perform
        boolean validInput = false; // Flag to check if the operation input is valid

        while (true) {
            // Input for the first rational number
            while (r1 == null) {
                System.out.print("\nEnter the first fraction: ");
                String input = in.next();
                if (input.equalsIgnoreCase("exit")) {
                    return; // Exit if the user types 'exit'
                }

                try {
                    r1 = new Rational(input); // Create a new Rational object
                } catch (Exception e) {
                    System.out.println("Error entering the first fraction: " + e.getMessage());
                }
            }

            // Input for the operation
            while (!validInput) {
                System.out.print("Enter operation (+, -, *, /): ");
                operation = in.next().charAt(0);
                if ("+-*/".indexOf(operation) != -1) {
                    validInput = true; // Set validInput to true if operation is valid
                } else {
                    System.out.println("Invalid operation! Please use +, -, * or /.");
                }
            }

            // Input for the second rational number
            while (r2 == null) {
                System.out.print("Enter the second fraction: ");
                String input = in.next();
                try {
                    r2 = new Rational(input); // Create a new Rational object
                } catch (Exception e) {
                    System.out.println("Error entering the second fraction: " + e.getMessage());
                }
            }

            // Calculate the result based on the chosen operation
            Rational result;
            switch (operation) {
                case '+':
                    result = r1.add(r2); // Addition
                    break;
                case '-':
                    result = r1.subtract(r2); // Subtraction
                    break;
                case '*':
                    result = r1.multiply(r2); // Multiplication
                    break;
                case '/':
                    result = r1.divide(r2); // Division
                    break;
                default:
                    continue; // This case should not occur
            }

            // Output the result
            if (result.getDen() == 1) {
                System.out.printf("Result: %d\n", result.getNum()); // Output as an integer
            } else {
                System.out.printf("Result: %d/%d\n", result.getNum(), result.getDen()); // Output as a fraction
            }

            // Reset for the next calculation
            r1 = null;
            r2 = null;
            validInput = false;
        }
    }
}
