import java.util.*;

public class TurtleGame {
    private static int size;
    private static char[][] field;
    private static int turtleX = 0;
    private static int turtleY = 0;
    private static int flagX, flagY;
    private static boolean penDown = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("====================================");

        while (true) {
            System.out.print("Введите размер карты (минимум 3, максимум 90, или 'random'): ");
            String input = scanner.next().toLowerCase();
            
            if (input.equals("random")) {
                size = random.nextInt(88) + 3;
                System.out.println("Случайный размер карты: " + size);
                break;
            } 
            try {
                size = Integer.parseInt(input);
                if (size < 3 || size > 90) {
                    System.out.println("Ошибка: Размер карты должен быть от 3 до 90.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите целое число или 'random'.");
            }
        }

        field = new char[size][size];
        System.out.println("====================================");

        int minePercentage = 0;
        while (true) {
            System.out.print("Введите процент покрытия минами (0-50%, или 'random'): ");
            String input = scanner.next().toLowerCase();
            
            if (input.equals("random")) {
                minePercentage = random.nextInt(51);
                System.out.println("Случайный процент покрытия минами: " + minePercentage + "%");
                break;
            }
            try {
                minePercentage = Integer.parseInt(input);
                if (minePercentage < 0 || minePercentage > 50) {
                    System.out.println("Ошибка: Процент покрытия должен быть от 0 до 50.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите целое число или 'random'.");
            }
        }

        System.out.println("====================================");

        placeFlag();
        placeMines(minePercentage);
        field[turtleY][turtleX] = '*';

        scanner.nextLine();

        String command;
        while (true) {
            printField();
            System.out.println("------------------------------------");
            System.out.print("Введите команду (up/down/left/right + число, pendown/penup, exit): ");
            command = scanner.nextLine();
            processCommand(command);
            System.out.println("------------------------------------");
        }
    }

    private static void placeFlag() {
        Random random = new Random();
        boolean onTopBorder = random.nextBoolean();

        if (onTopBorder) {
            flagX = random.nextInt(size);
            flagY = size - 1;
        } else {
            flagX = size - 1;
            flagY = random.nextInt(size);
        }

        while (flagX == turtleX && flagY == turtleY) {
            if (onTopBorder) {
                flagX = random.nextInt(size);
            } else {
                flagY = random.nextInt(size);
            }
        }

        field[flagY][flagX] = '$';
    }

    private static void placeMines(int minePercentage) {
        Random random = new Random();
        int numberOfMines = (int) (size * size * (minePercentage / 100.0));

        boolean validField = false;
        while (!validField) {
            for (int i = 0; i < size; i++) {
                Arrays.fill(field[i], '.');
            }

            for (int i = 0; i < numberOfMines; i++) {
                int mineX, mineY;
                do {
                    mineX = random.nextInt(size);
                    mineY = random.nextInt(size);
                } while (
                    (mineX == turtleX && mineY == turtleY) ||
                    (mineX == flagX && mineY == flagY) ||
                    field[mineY][mineX] == '#'
                );

                field[mineY][mineX] = '#';
            }

            validField = canReachFlag();
        }
    }

    private static boolean canReachFlag() {
        boolean[][] visited = new boolean[size][size];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {turtleX, turtleY});
        visited[turtleY][turtleX] = true;

        int[][] directions = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int x = pos[0], y = pos[1];

            if (x == flagX && y == flagY) {
                return true;
            }

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (isOutOfBounds(newX, newY) || visited[newY][newX] || field[newY][newX] == '#') {
                    continue;
                }

                queue.add(new int[] {newX, newY});
                visited[newY][newX] = true;
            }
        }

        return false;
    }

    private static boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= size || y < 0 || y >= size;
    }

    private static void processCommand(String command) {
        String[] parts = command.split(" ");
        String cmd = parts[0].toLowerCase();
        int steps = 1;

        if (parts.length > 1) {
            try {
                steps = Integer.parseInt(parts[1]);
                if (steps <= 0) {
                    System.out.println("Ошибка: Количество шагов должно быть положительным числом.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Количество шагов должно быть числом.");
                return;
            }
        }

        switch (cmd) {
            case "pendown":
                penDown = true;
                System.out.println("Перо опущено.");
                break;
            case "penup":
                penDown = false;
                System.out.println("Перо поднято.");
                break;
            case "up":
                move(0, steps);
                break;
            case "down":
                move(0, -steps);
                break;
            case "left":
                move(-steps, 0);
                break;
            case "right":
                move(steps, 0);
                break;
            case "exit":
                System.out.println("Игра завершена. До новых встреч!");
                System.exit(0);
                break;
            default:
                System.out.println("Ошибка: Неизвестная команда.");
        }
    }

    private static void move(int dx, int dy) {
        for (int step = 0; step < Math.abs(dy) + Math.abs(dx); step++) {
            int newX = turtleX + (dx != 0 ? Integer.signum(dx) : 0);
            int newY = turtleY + (dy != 0 ? Integer.signum(dy) : 0);

            if (isOutOfBounds(newX, newY)) {
                System.out.println("Черепашка вышла за границы поля и умерла!");
                System.exit(0);
            }

            turtleX = newX;
            turtleY = newY;

            if (field[turtleY][turtleX] == '#') {
                System.out.println("Черепашка наткнулась на мину и погибла!");
                System.exit(0);
            }

            if (penDown) {
                field[turtleY][turtleX] = '*';
            }
        }

        if (turtleX == flagX && turtleY == flagY) {
            System.out.println("Черепашка коснулась флага! Победа!");
            System.exit(0);
        }
    }

    private static void printField() {
        for (int y = size - 1; y >= 0; y--) {
            for (int x = 0; x < size; x++) {
                if (x == flagX && y == flagY) {
                    System.out.print("$ ");
                } else if (x == turtleX && y == turtleY && penDown) {
                    System.out.print("Ж ");
                } else if (field[y][x] == '#') {
                    System.out.print("# ");
                } else {
                    System.out.print((field[y][x] == '*' ? '*' : '.') + " ");
                }
            }
            System.out.println();
        }
    }
}
