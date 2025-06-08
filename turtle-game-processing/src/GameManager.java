import processing.core.PApplet;

import java.util.Random;


public class GameManager {
    private final PApplet app;
    private final EndingsUI endingsUI;
    private Field field;
    private Turtle turtle;
    private Fox enemy;
    private boolean enemyEnabled = false;
    private boolean uiRemoved = false;
    private float scaleFactor = 1;
    private float offsetX = 0, offsetY = 0;
    private float lastMouseX, lastMouseY;
    private boolean dragging = false;

    public GameManager(PApplet app, EndingsUI endingsUI) {
        this.app = app;
        this.endingsUI = endingsUI;
    }

    public void updateAndDraw() {
        if (enemyEnabled && enemy != null) {
            enemy.update();
        }

        app.pushMatrix();
        app.translate(offsetX, offsetY);
        app.scale(scaleFactor);

        field.display(app);
        if (enemyEnabled && enemy != null) {
            enemy.display(app);
        }
        turtle.display(app);

        app.popMatrix();
    }

    public void start(int gridSize, int minePercentage, boolean enemyEnabled, int foxSpeed) {
        this.enemyEnabled = enemyEnabled;

        field = new Field(gridSize, app);
        FieldGenerator generator = new FieldGenerator(field, new Random());
        generator.generate(minePercentage);
        turtle = new Turtle(0, 0, true, field);

        if (enemyEnabled) {
            int ex = Math.max(0, field.getFlagX() - 1);
            int ey = field.getFlagY();
            enemy = new Fox(ex, ey, field, turtle, endingsUI, (int) field.getCellSize(), app);

            enemy.setSpeed(foxSpeed);
        } else {
            enemy = null;
        }

        uiRemoved = true;
    }

    public boolean isReady() {
        return uiRemoved;
    }

    public void moveTurtle(int dx, int dy) {
        int newX = turtle.getX() + dx;
        int newY = turtle.getY() + dy;

        if (field.isOutOfBounds(newX, newY)) {
            stopEnemies();
            endingsUI.showRavineDeathScreen();
            return;
        }

        if (field.getCell(newX, newY) == '#') {
            stopEnemies();
            endingsUI.showLossScreen();
            return;
        }

        if (newX == field.getFlagX() && newY == field.getFlagY()) {
            stopEnemies();
            endingsUI.showWinScreen();
            return;
        }

        turtle.moveTo(newX, newY);
        if (turtle.isPenDown()) {
            field.markCell(newX, newY, '*');
        }
    }

    public void togglePen() {
        turtle.togglePen();
    }

    public void handleZoom(float zoomFactor) {
        scaleFactor *= zoomFactor;
        float centerX = app.width / 2.0f;
        float centerY = app.height / 2.0f;
        offsetX += (centerX - offsetX) * (1 - zoomFactor);
        offsetY += (centerY - offsetY) * (1 - zoomFactor);
    }

    public void startDragging(float mouseX, float mouseY) {
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        dragging = true;
    }

    public void dragTo(float mouseX, float mouseY) {
        if (dragging) {
            offsetX += mouseX - lastMouseX;
            offsetY += mouseY - lastMouseY;
            lastMouseX = mouseX;
            lastMouseY = mouseY;
        }
    }

    public void stopDragging() {
        dragging = false;
    }

    public void reset() {
        uiRemoved = false;
        field = null;
        turtle = null;
    }

    public void stopEnemies() {
        if (enemy != null) {
            enemy.stop();
        }
    }
}
