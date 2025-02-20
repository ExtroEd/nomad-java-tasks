public class Task5 {
    public static double calculateAverage(int[] array) {
        double averageNumber = array[0];

        for (int i = 1; i < array.length; i++) {
            averageNumber += array[i];
        }
        averageNumber /= array.length;

        return averageNumber;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5};

        System.out.println("Среднее число массива: " + calculateAverage(array));
    }
}
