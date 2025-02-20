public class Task3 {
    public static int findMax(int[] array) {
        if (array.length == 0) {
            return Integer.MIN_VALUE; // Возврат, если массив пустой
        }

        int biggestNumber = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] > biggestNumber) {
                biggestNumber = array[i];
            }
        }

        return biggestNumber;
    }

    public static void main(String[] args) {
        int[] testArray = {4, 1, 7, 2};

        System.out.println("максимальное число: " + findMax(testArray));
    }
}
