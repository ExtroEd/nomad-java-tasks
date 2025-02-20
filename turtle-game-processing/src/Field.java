import processing.core.PApplet;
import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

public class Field {
    private final int size;
    private final char[][] grid;
    private int flagX, flagY;
    private final Random random;

    public Field(int size, Random random) {
        this.size = size;
        this.random = random;
        grid = new char[size][size];
    }

    // Инициализация поля: заполнение пустыми клетками, размещение флага и мин
    public void setup(int minePercentage) {
        for (int i = 0; i < size; i++) {
            Arrays.fill(grid[i], '.');
        }
        placeFlag();
        placeMines(minePercentage);
    }

    // Размещает флаг на правом или нижнем краю поля, избегая стартовой точки (0,0)
    private void placeFlag() {
        boolean onBottomBorder = random.nextBoolean();
        if (onBottomBorder) {
            flagX = random.nextInt(size);
            flagY = size - 1;
        } else {
            flagX = size - 1;
            flagY = random.nextInt(size);
        }
        if(flagX == 0 && flagY == 0) {
            flagX = 1;
        }
        grid[flagY][flagX] = '$';
    }

    // Размещает мины с проверкой достижимости флага от старта
    private void placeMines(int minePercentage) {
        int numberOfMines = (int) (size * size * (minePercentage / 100.0));
        boolean validField = false;

        while (!validField) {
            // Очищаем поле, оставляя флаг неизменным
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if(i == flagY && j == flagX) continue;
                    grid[i][j] = '.';
                }
            }
            // Размещаем мины случайным образом
            for (int i = 0; i < numberOfMines; i++) {
                int mineX, mineY;
                do {
                    mineX = random.nextInt(size);
                    mineY = random.nextInt(size);
                } while (
                        (mineX == 0 && mineY == 0) ||
                                (mineX == flagX && mineY == flagY) ||
                                grid[mineY][mineX] == '#'
                );
                grid[mineY][mineX] = '#';
            }
            validField = canReachFlag();
        }
    }

    // Проверяет, существует ли путь от (0,0) до флага
    private boolean canReachFlag() {
        boolean[][] visited = new boolean[size][size];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});
        visited[0][0] = true;
        int[][] directions = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

        while (!queue.isEmpty()){
            int[] pos = queue.poll();
            int x = pos[0], y = pos[1];
            if(x == flagX && y == flagY) return true;
            for(int[] dir : directions){
                int newX = x + dir[0];
                int newY = y + dir[1];
                if(isOutOfBounds(newX, newY) || visited[newY][newX] || grid[newY][newX] == '#'){
                    continue;
                }
                queue.add(new int[]{newX, newY});
                visited[newY][newX] = true;
            }
        }
        return false;
    }

    public boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= size || y < 0 || y >= size;
    }

    public void markCell(int x, int y, char mark) {
        if (!isOutOfBounds(x, y)) {
            grid[y][x] = mark;
        }
    }

    public char getCell(int x, int y) {
        return grid[y][x];
    }

    public int getFlagX() {
        return flagX;
    }

    public int getFlagY() {
        return flagY;
    }

    public int getSize() {
        return size;
    }

    // Отрисовка поля: клетки, мины, флаг и след черепашки
    public void display(PApplet applet) {
        int cellSize = applet.width / size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = j * cellSize;
                int y = i * cellSize;

                // Отрисовка клетки
                if (i == flagY && j == flagX) {
                    applet.fill(0, 255, 0); // флаг – зелёный
                } else if (grid[i][j] == '#') {
                    applet.fill(255, 0, 0); // мина – красная
                } else if (grid[i][j] == '*') {
                    applet.fill(200); // след – светло-серый
                } else {
                    applet.fill(255); // пустая клетка – белая
                }

                // Отрисовка клетки с сеткой
                applet.stroke(0); // Чёрная сетка
                applet.rect(x, y, cellSize, cellSize);
            }
        }
    }
}
