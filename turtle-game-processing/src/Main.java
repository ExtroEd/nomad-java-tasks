import processing.core.PApplet;
import java.util.Random;


public class Main extends PApplet {
    public static void main(String[] ignoredArgs) {
        PApplet.main("Main");
    }

    int gameState = 0;
    private boolean uiRemoved = false;

    private int gridSize, minePercentage;
    private Field field;
    private Turtle turtle;
    private UI ui;

    private float scaleFactor = 1;
    private float offsetX = 0, offsetY = 0;
    private float lastMouseX, lastMouseY;
    private boolean dragging = false;

    public void settings() {
        fullScreen();
    }

    public void setup() {
        ui = new UI(this, this);
        ui.setup();
    }

    public void draw() {
        background(230);

        if (gameState == 0) {
            ui.drawMenu();
        } else if (gameState == 1) {
            if (!uiRemoved) {
                ui.remove();
                field = new Field(gridSize, new Random(), this);
                field.setup(minePercentage);
                turtle = new Turtle(0, 0, true, field);
                uiRemoved = true;
            }

            pushMatrix();
            translate(offsetX, offsetY);
            scale(scaleFactor);
            field.display(this);
            turtle.display(this);
            popMatrix();
        }

        ui.drawEndScreen();
    }

    public void startGame() {
        try {
            gridSize = ui.getGridSize();
            minePercentage = ui.getMinePercentage();

            if (gridSize < 10 || gridSize > 500) {
                ui.showTooltip("Grid size must be from 10 to 500.");
                return;
            }

            if (minePercentage < 0 || minePercentage > 60) {
                ui.showTooltip("Mine percentage must be from 0 to 60.");
                return;
            }

            ui.hideTooltip();
            gameState = 1;

        } catch (NumberFormatException ex) {
            ui.showTooltip("Invalid input! Please enter integers.");
        }
    }

    public void keyPressed() {
        if (gameState == 1) {
            int dx = 0, dy = 0;
            if (keyCode == UP || keyCode == 'W') dy = -1;
            else if (keyCode == DOWN || keyCode == 'S') dy = 1;
            else if (keyCode == LEFT || keyCode == 'A') dx = -1;
            else if (keyCode == RIGHT || keyCode == 'D') dx = 1;
            else if (keyCode == 'E') turtle.togglePen();
            if (dx != 0 || dy != 0) moveTurtle(dx, dy);
        }
    }

    public void mouseWheel(processing.event.MouseEvent event) {
        float zoomFactor = event.getCount() > 0 ? 0.9f : 1.1f;

        scaleFactor *= zoomFactor;

        float centerX = width / 2.0f;
        float centerY = height / 2.0f;

        offsetX += (centerX - offsetX) * (1 - zoomFactor);
        offsetY += (centerY - offsetY) * (1 - zoomFactor);
    }

    public void mousePressed() {
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        dragging = true;
    }

    public void mouseDragged() {
        if (dragging) {
            offsetX += mouseX - lastMouseX;
            offsetY += mouseY - lastMouseY;
            lastMouseX = mouseX;
            lastMouseY = mouseY;
        }
    }

    public void mouseReleased() {
        dragging = false;
    }

    public void moveTurtle(int dx, int dy) {
        int newX = turtle.getX() + dx;
        int newY = turtle.getY() + dy;

        if (field.isOutOfBounds(newX, newY)) {
            ui.showLossScreen();
            gameState = 0;
            return;
        }

        char cell = field.getCell(newX, newY);
        if (cell == '#') {
            ui.showLossScreen();
            gameState = 0;
            return;
        }

        if (newX == field.getFlagX() && newY == field.getFlagY()) {
            ui.showWinScreen();
            gameState = 0;
            return;
        }

        turtle.moveTo(newX, newY);

        if (turtle.isPenDown()) {
            field.markCell(newX, newY, '*');
        }
    }

    public void resetToMenu() {
        gameState = 0;
        uiRemoved = false;
        field = null;
        turtle = null;

        ui.resetEndScreen();

        ui.showMenuElements();
        ui.drawMenu();
    }
}
