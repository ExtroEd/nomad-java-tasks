import java.util.*;
import processing.core.PApplet;


public class Fox {
    private int x, y;
    private final Turtle turtle;
    private final EndingsUI endingsUI;
    private final int cellSize;
    private final AStarPathfinder pathfinder;
    private List<int[]> path = new ArrayList<>();
    private int pathIndex = 0;
    private int lastTargetX = -1;
    private int lastTargetY = -1;
    private boolean active = true;

    private int speed = 2;
    private long lastMoveTime = 0;
    private int delayMs = 1000 / speed;

    private final PApplet applet;

    Field field;

    public Fox(int x, int y, Field field, Turtle turtle, EndingsUI endingsUI, int cellSize, PApplet applet) {
        this.x = x;
        this.y = y;
        this.turtle = turtle;
        this.endingsUI = endingsUI;
        this.cellSize = cellSize;
        this.pathfinder = new AStarPathfinder(field);
        this.field = field;
        this.applet = applet;
    }

    public void setSpeed(int speed) {
        if (speed < 1) speed = 1;
        if (speed > 6) speed = 6;
        this.speed = speed;
        delayMs = 1000 / speed;
    }

    private boolean hasTargetChanged(int tx, int ty) {
        boolean changed = tx != lastTargetX || ty != lastTargetY;
        lastTargetX = tx;
        lastTargetY = ty;
        return changed;
    }

    public void update() {
        if (!active) return;
        if (!turtle.isPenDown()) return;

        long currentTime = applet.millis();
        if (currentTime - lastMoveTime < delayMs) return;

        lastMoveTime = currentTime;

        int tx = turtle.getX();
        int ty = turtle.getY();

        if (path == null || pathIndex >= path.size() || hasTargetChanged(tx, ty)) {
            path = pathfinder.findPath(x, y, tx, ty);
            pathIndex = 0;
        }

        if (path != null && pathIndex < path.size()) {
            int[] next = path.get(pathIndex);
            x = next[0];
            y = next[1];
            pathIndex++;

            if (x == tx && y == ty) {
                endingsUI.showEatenByFoxScreen();
            }
        }
    }

    public void display(PApplet applet) {
        int drawX = x * cellSize + cellSize / 2;
        int drawY = y * cellSize + cellSize / 2;
        applet.fill(255, 100, 100);
        applet.noStroke();
        applet.ellipse(drawX, drawY, cellSize * 0.6f, cellSize * 0.6f);
    }

    public void stop() {
        active = false;
    }
}
