import java.util.*;
import processing.core.PApplet;


public class Field {
    private final int size;
    private final char[][] grid;
    private final boolean[][] hasMine;
    private final PApplet parent;
    private int flagX, flagY;

    public Field(int size, PApplet parent) {
        this.size = size;
        this.parent = parent;
        this.grid = new char[size][size];
        this.hasMine = new boolean[size][size];
        clear();
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            Arrays.fill(grid[i], '.');
            Arrays.fill(hasMine[i], false);
        }
    }

    public void placeFlag(int x, int y) {
        this.flagX = x;
        this.flagY = y;
        grid[y][x] = '$';
    }

    public void placeMine(int x, int y) {
        grid[y][x] = '#';
        hasMine[y][x] = true;
    }

    public boolean isMine(int x, int y) {
        return isInBounds(x, y) && hasMine[y][x];
    }

    public boolean isSafe(int x, int y) {
        char c = getCell(x, y);
        return c != '#';
    }

    public boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= size || y < 0 || y >= size;
    }

    public boolean isInBounds(int x, int y) {
        return !isOutOfBounds(x, y);
    }

    public char getCell(int x, int y) {
        return grid[y][x];
    }

    public void markCell(int x, int y, char mark) {
        if (isInBounds(x, y)) grid[y][x] = mark;
    }

    public int getSize() {
        return size;
    }

    public int getFlagX() {
        return flagX;
    }

    public int getFlagY() {
        return flagY;
    }

    public float getCellSize() {
        return (float) parent.width / size;
    }

    public void display(PApplet applet) {
        int cellSize = applet.width / size;
        float strokeWeight = PApplet.constrain(cellSize * 0.025f, 0.05f, 1.2f);
        applet.strokeWeight(strokeWeight);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int px = x * cellSize;
                int py = y * cellSize;

                if (x == flagX && y == flagY) {
                    applet.fill(0, 0, 255);
                } else if (hasMine[y][x]) {
                    applet.fill(255, 0, 0);
                } else if (grid[y][x] == '*') {
                    applet.fill(200);
                } else {
                    applet.fill(255);
                }

                applet.stroke(0);
                applet.rect(px, py, cellSize, cellSize);
            }
        }
    }
}
