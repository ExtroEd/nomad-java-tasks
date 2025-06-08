import java.awt.*;
import java.util.*;
import java.util.List;


public class FieldGenerator {
    private final Random random;
    private final Field field;

    public FieldGenerator(Field field, Random random) {
        this.field = field;
        this.random = random;
    }

    public void generate(int minePercentage) {
        field.clear();
        placeFlagRandom();

        int size = field.getSize();
        double reservedPercent = size < 20 ? 2.0 : 1.0;
        double maxPathPercent = 100.0 - minePercentage - reservedPercent;
        int maxPathLength = (int) Math.floor(size * size * (maxPathPercent / 100.0));

        List<Point> path;
        int attempts = 0;
        int maxAttempts = 1000;

        do {
            path = generateRandomPath(0, 0, field.getFlagX(), field.getFlagY());
            attempts++;
        } while (path.size() > maxPathLength && attempts < maxAttempts);

        if (attempts == maxAttempts) {
            System.out.println("⚠️ Warning: failed to generate valid path.");
        }

        Set<Point> pathSet = new HashSet<>(path);
        for (Point p : pathSet) {
            field.markCell(p.x, p.y, '.');
        }

        placeMinesExcludingPath(minePercentage, pathSet);
    }

    private void placeFlagRandom() {
        int size = field.getSize();
        boolean onBottomBorder = random.nextBoolean();
        int x = onBottomBorder ? random.nextInt(size) : size - 1;
        int y = onBottomBorder ? size - 1 : random.nextInt(size);

        if (x == 0 && y == 0) x = 1;
        field.placeFlag(x, y);
    }

    private void placeMinesExcludingPath(int minePercentage, Set<Point> pathSet) {
        int size = field.getSize();
        int numberOfMines = (int) (size * size * (minePercentage / 100.0));
        int placedMines = 0;

        while (placedMines < numberOfMines) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            Point candidate = new Point(x, y);

            if ((x == 0 && y == 0) || (x == field.getFlagX() && y == field.getFlagY()) || pathSet.contains(candidate))
                continue;

            if (field.isMine(x, y)) continue;

            field.placeMine(x, y);
            placedMines++;
        }
    }

    private List<Point> generateRandomPath(int startX, int startY, int endX, int endY) {
        int size = field.getSize();
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

                if (field.isOutOfBounds(nx, ny) || visited[ny][nx]) continue;

                visited[ny][nx] = true;
                stack.push(new Point(nx, ny));
                moved = true;
                break;
            }

            if (!moved) stack.pop();
        }

        return path;
    }
}
