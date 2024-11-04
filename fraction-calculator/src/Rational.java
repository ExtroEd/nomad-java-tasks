public class Rational {
    private int num; // Numerator
    private int den; // Denominator

    // Constructor that takes a string representation of the rational number
    public Rational(String otherRational) {
        String[] nums = otherRational.split("/"); // Split input by "/"
        
        if (nums.length == 1) {
            this.num = Integer.parseInt(nums[0]); // Whole number
            this.den = 1; // Denominator is 1 for whole numbers
        } else if (nums.length == 2) {
            this.num = Integer.parseInt(nums[0]); // Numerator
            this.den = Integer.parseInt(nums[1]); // Denominator

            if (this.den == 0) {
                throw new ArithmeticException("Cannot divide by 0"); // Check for division by zero
            }
        } else {
            throw new IllegalArgumentException("Invalid format"); // Handle invalid input format
        }

        simplify(); // Simplify the fraction
    }

    // Method to simplify the fraction
    private void simplify() {
        int gcd = gcd(Math.abs(num), Math.abs(den)); // Find greatest common divisor
        num /= gcd; // Reduce numerator
        den /= gcd; // Reduce denominator

        // Make the denominator positive
        if (den < 0) {
            den = -den;
            num = -num; // Change the sign of numerator if denominator is negative
        }
    }

    // Method to find the greatest common divisor (GCD)
    private int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b; // Remainder
            a = t; // Update a
        }
        return a; // Return GCD
    }

    // Getter for numerator
    public int getNum() {
        return this.num;
    }

    // Getter for denominator
    public int getDen() {
        return this.den;
    }

    // Method to add two rational numbers
    public Rational add(Rational other) {
        int newNum = this.num * other.den + other.num * this.den; // Calculate new numerator
        int newDen = this.den * other.den; // Calculate new denominator
        return new Rational(newNum + "/" + newDen); // Return new rational number
    }

    // Method to subtract two rational numbers
    public Rational subtract(Rational other) {
        int newNum = this.num * other.den - other.num * this.den; // Calculate new numerator
        int newDen = this.den * other.den; // Calculate new denominator
        return new Rational(newNum + "/" + newDen); // Return new rational number
    }

    // Method to multiply two rational numbers
    public Rational multiply(Rational other) {
        int newNum = this.num * other.num; // Multiply numerators
        int newDen = this.den * other.den; // Multiply denominators
        return new Rational(newNum + "/" + newDen); // Return new rational number
    }

    // Method to divide two rational numbers
    public Rational divide(Rational other) {
        if (other.num == 0) {
            throw new ArithmeticException("Cannot divide by 0"); // Check for division by zero
        }
        int newNum = this.num * other.den; // New numerator
        int newDen = this.den * other.num; // New denominator
        return new Rational(newNum + "/" + newDen); // Return new rational number
    }
}
