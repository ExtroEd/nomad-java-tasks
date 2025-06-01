import java.awt.Point;
import java.util.*;
import processing.core.PApplet;


public class Field {
    private final int size;
    private final char[][] grid;
    private int flagX, flagY;
    private final Random random;
    private final PApplet parent;

    public Field(int size, Random random, PApplet parent) {
        this.size = size;
        this.random = random;
        this.parent = parent;
        grid = new char[size][size];
    }

    public void setup(int minePercentage) {
        for (int i = 0; i < size; i++) {
            Arrays.fill(grid[i], '.');
        }
        placeFlag();

        double reservedPercent = size < 20 ? 2.0 : 1.0;
        double maxPathPercent = 100.0 - minePercentage - reservedPercent;
        int maxPathLength = (int) Math.floor(size * size * (maxPathPercent / 100.0));

        List<Point> path;
        int attempts = 0;
        int maxAttempts = 1000;

        do {
            path = generateRandomPath(0, 0, flagX, flagY);
            attempts++;
        } while (path.size() > maxPathLength && attempts < maxAttempts);

        if (attempts == maxAttempts) {
            System.out.println("⚠️ Warning: failed to generate valid path within constraints.");
        }

        Set<Point> pathSet = new HashSet<>(path);
        for (Point p : pathSet) {
            grid[p.y][p.x] = '.';
        }

        placeMinesExcludingPath(minePercentage, pathSet);
    }

    private void placeMinesExcludingPath(int minePercentage, Set<Point> pathSet) {
        int numberOfMines = (int) (size * size * (minePercentage / 100.0));
        int placedMines = 0;

        while (placedMines < numberOfMines) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            Point candidate = new Point(x, y);

            if ((x == 0 && y == 0) || (x == flagX && y == flagY) || pathSet.contains(candidate)) continue;
            if (grid[y][x] == '#') continue;

            grid[y][x] = '#';
            placedMines++;
        }
    }

    private List<Point> generateRandomPath(int startX, int startY, int endX, int endY) {
        boolean[][] visited = new boolean[size][size];
        List<Point> path = new ArrayList<>();
        Stack<Point> stack = new Stack<>();

        stack.push(new Point(startX, startY));
        visited[startY][startX] = true;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!stack.isEmpty()) {
            Point current = stack.peek();
            if (current.x == endX && current.y == endY) {
                path.addAll(stack);
                break;
            }

            List<int[]> shuffledDirs = new ArrayList<>(Arrays.asList(directions));
            Collections.shuffle(shuffledDirs, random);

            boolean moved = false;
            for (int[] dir : shuffledDirs) {
                int nx = current.x + dir[0];
                int ny = current.y + dir[1];

                if (isOutOfBounds(nx, ny) || visited[ny][nx]) continue;

                visited[ny][nx] = true;
                stack.push(new Point(nx, ny));
                moved = true;
                break;
            }

            if (!moved) {
                stack.pop(); // backtrack
            }
        }

        return path;
    }

    private void placeFlag() {
        boolean onBottomBorder = random.nextBoolean();
        if (onBottomBorder) {
            flagX = random.nextInt(size);
            flagY = size - 1;
        } else {
            flagX = size - 1;
            flagY = random.nextInt(size);
        }
        if (flagX == 0 && flagY == 0) {
            flagX = 1;
        }
        grid[flagY][flagX] = '$';
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

    public float getCellSize() {
        return (float) parent.width / size;
    }

    public void display(PApplet applet) {
        int cellSize = applet.width / size;

        float dynamicStroke = PApplet.constrain(cellSize * 0.025f, 0.05f, 1.2f);
        applet.strokeWeight(dynamicStroke);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = j * cellSize;
                int y = i * cellSize;

                if (i == flagY && j == flagX) {
                    applet.fill(0, 0, 255);
                } else if (grid[i][j] == '#') {
                    applet.fill(255, 0, 0);
                } else if (grid[i][j] == '*') {
                    applet.fill(200);
                } else {
                    applet.fill(255);
                }

                applet.stroke(0);
                applet.rect(x, y, cellSize, cellSize);
            }
        }
    }
}
