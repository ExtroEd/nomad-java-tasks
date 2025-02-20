public class Task9 {
    public static int sumArray(int[] array) {
        int sumOfArray = 0;

        for (int i = 0; i < array.length; i++) {
            sumOfArray += array[i];
        }

        return sumOfArray;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4};

        System.out.print("Суммарное число массива: " + sumArray(array));
    }
}
