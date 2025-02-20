import java.util.Arrays;

public class Task10 {
    public static int[] squareArray(int[] array) {
        int[] squaredArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            squaredArray[i] = array[i] * array[i];
        }

        return squaredArray;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4};

        System.out.print(Arrays.toString(squareArray(array))); // Метод для преоброзования массива в строку
    }
}
