public class Task2 {
    public static void displayArray(int[] array) {
        // Цикл for-each для вывода всех элементов массива
        for (int element : array) {
            System.out.println(element);
        }
    }

    public static void main(String[] args) {
        int[] testArray = {1, 2, 3};

        displayArray(testArray);
    }
}
