import java.util.Scanner;
import java.util.Random;

public class TurtleGame {
    private static int size;
    private static char[][] field;
    private static int turtleX = 0;
    private static int turtleY = 0;
    private static int flagX, flagY;
    private static boolean penDown = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Запрашиваем размер карты у пользователя
        System.out.println("====================================");
        System.out.print("Введите размер карты (минимум 5, максимум 90): ");
        size = scanner.nextInt();
        System.out.println("====================================");

        if (size < 5) {
            System.out.println("Размер карты должен быть не меньше 5. Установлен размер 5.");
            size = 5;
        } else if (size > 90) {
            System.out.println("Размер карты должен быть не больше 90. Установлен размер 90.");
            size = 90;
        }

        field = new char[size][size];

        // Запрашиваем процент мины на карте у пользователя
        System.out.println("====================================");
        System.out.print("Введите процент покрытия минами (0-90%): ");
        int minePercentage = scanner.nextInt();
        System.out.println("====================================");

        if (minePercentage < 0) {
            minePercentage = 0;
        } else if (minePercentage > 90) {
            minePercentage = 90;
        }

        placeFlag(); // Устанавливаем флаг на границах
        placeMines(minePercentage); // Устанавливаем мины
        field[turtleY][turtleX] = '*'; // Начальная позиция черепахи закрашена

        scanner.nextLine(); // Чтобы пропустить оставшуюся строку после nextInt()

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

    // Размещаем флаг на верхней или правой границе
    private static void placeFlag() {
        Random random = new Random();
        boolean onTopBorder = random.nextBoolean(); // Случайный выбор: верхняя или правая граница

        if (onTopBorder) {
            // Верхняя граница
            flagX = random.nextInt(size);
            flagY = size - 1;
        } else {
            // Правая граница
            flagX = size - 1;
            flagY = random.nextInt(size);
        }

        // Флаг не должен располагаться на стартовой позиции черепахи
        while (flagX == turtleX && flagY == turtleY) {
            if (onTopBorder) {
                flagX = random.nextInt(size);
            } else {
                flagY = random.nextInt(size);
            }
        }

        field[flagY][flagX] = 'F'; // Устанавливаем флаг
    }

    // Размещаем мины на указанном проценте площади карты
    private static void placeMines(int minePercentage) {
        Random random = new Random();
        int numberOfMines = (int) (size * size * (minePercentage / 100.0)); // Процент от площади

        for (int i = 0; i < numberOfMines; i++) {
            int mineX, mineY;
            do {
                mineX = random.nextInt(size);
                mineY = random.nextInt(size);
            } while (
                (mineX == turtleX && mineY == turtleY) ||  // Мина не может быть на старте черепахи
                (mineX == flagX && mineY == flagY) ||      // Мина не может быть на флаге
                field[mineY][mineX] == '#'                 // Уже размещённая мина
            );

            field[mineY][mineX] = '#'; // Устанавливаем мину
        }
        
        // Проверка, можно ли добраться до флага
        if (isBlockedByMines()) {
            System.out.println("====================================");
            System.out.println("Мины заблокировали доступ к флагу. Игра перезапущена!");
            System.out.println("====================================");
            resetGame(); // Перезапуск игры
        }
    }

    // Проверка, не заблокирован ли флаг минами
    private static boolean isBlockedByMines() {
        // Простая проверка: если все соседние клетки черепахи заняты минами
        int[][] directions = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

        for (int[] dir : directions) {
            int newX = turtleX + dir[0];
            int newY = turtleY + dir[1];

            if (!isOutOfBounds(newX, newY) && field[newY][newX] != '#') {
                return false; // Есть хотя бы один путь
            }
        }
        return true; // Все пути заблокированы
    }

    // Перезапуск игры в случае, если невозможно выиграть
    private static void resetGame() {
        field = new char[size][size];
        turtleX = 0;
        turtleY = 0;
        placeFlag();
        placeMines((int) (size * size * 0.05)); // Устанавливаем мины заново
        field[turtleY][turtleX] = '*'; // Начальная позиция черепахи закрашена
    }

    // Обработка команды игрока
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

    // Передвижение черепахи
    private static void move(int dx, int dy) {
        for (int step = 0; step < Math.abs(dy) + Math.abs(dx); step++) {
            int newX = turtleX + (dx != 0 ? Integer.signum(dx) : 0);
            int newY = turtleY + (dy != 0 ? Integer.signum(dy) : 0);

            if (isOutOfBounds(newX, newY)) {
                System.out.println("====================================");
                System.out.println("Черепашка вышла за границы поля и погибла!");
                System.out.println("====================================");
                System.exit(0);
            }

            turtleX = newX;
            turtleY = newY;

            // Проверяем, коснулась ли черепашка мины
            if (field[turtleY][turtleX] == '#') {
                System.out.println("====================================");
                System.out.println("Черепашка коснулась мины и погибла!");
                System.out.println("====================================");
                System.exit(0);
            }

            // Проверяем, коснулась ли черепашка флага
            if (turtleX == flagX && turtleY == flagY) {
                System.out.println("====================================");
                System.out.println("Черепашка коснулась флага! Победа!");
                System.out.println("====================================");
                System.exit(0);
            }

            if (penDown) {
                field[turtleY][turtleX] = '*';
            }
        }
    }

    private static boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= size || y < 0 || y >= size;
    }

    // Вывод игрового поля
    private static void printField() {
        System.out.println("====================================");
        for (int y = size - 1; y >= 0; y--) {
            for (int x = 0; x < size; x++) {
                if (x == flagX && y == flagY) {
                    System.out.print("$ "); // Флаг
                } else if (x == turtleX && y == turtleY && penDown) {
                    System.out.print("Ж "); // Черепашка, если перо опущено
                } else if (field[y][x] == '#') {
                    System.out.print("# "); // Мина
                } else {
                    System.out.print((field[y][x] == '*' ? '*' : '.') + " "); // След или пустое поле
                }
            }
            System.out.println();
        }
        System.out.println("====================================");
    }
}
