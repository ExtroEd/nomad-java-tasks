package leetcode_35;
import java.util.Scanner;

public class Solution {
    public static int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите количество тестов: ");
        int testCases = scanner.nextInt();

        for (int t = 0; t < testCases; t++) {
            System.out.print("Введите размер массива: ");
            int n = scanner.nextInt();
            int[] nums = new int[n];
            System.out.print("Введите элементы массива");
            for (int i = 0; i < n; i++) {
                nums[i] = scanner.nextInt();
            }

            System.out.print("Введите целое значение: ");
            int target = scanner.nextInt();

            int result = searchInsert(nums, target);
            System.out.println("Результат" + result);
        }

        scanner.close();
    }
}