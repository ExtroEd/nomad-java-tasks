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
                field = new Field(gridSize, new Random());
                field.setup(minePercentage);
                turtle = new Turtle(0, 0, true, field);
                uiRemoved = true;
            }
            pushMatrix();
            translate(offsetX, offsetY);
            scale(scaleFactor);
            field.display(this);
            turtle.display(this); // Отрисовываем черепашку только если перо опущено
            popMatrix();
        }
        ui.drawEndScreen();
    }

    public void startGame() {
        gridSize = ui.getGridSize();
        minePercentage = ui.getMinePercentage();
        gameState = 1;
    }

    public void resetGame() {
        field = new Field(field.getSize(), new Random());
        turtle = null;
        ui.drawMenu();
        gameState = 0;
    }

    public void keyPressed() {
        if (gameState == 1) {
            int dx = 0, dy = 0;
            if (keyCode == UP) dy = -1;
            else if (keyCode == DOWN) dy = 1;
            else if (keyCode == LEFT) dx = -1;
            else if (keyCode == RIGHT) dx = 1;
            else if (keyCode == 'P') turtle.togglePen();
            if (dx != 0 || dy != 0) moveTurtle(dx, dy);
        }
    }

    public void mouseWheel(processing.event.MouseEvent event) {
        float zoomFactor = event.getCount() > 0 ? 0.9f : 1.1f; // Определяем направление зума

        // Применяем новый масштаб
        scaleFactor *= zoomFactor;

        // Вычисляем смещение для центрирования зума
        float centerX = width / 2.0f; // Центр экрана по X
        float centerY = height / 2.0f; // Центр экрана по Y

        // Пересчитываем смещение для нового масштаба
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
            println("Черепашка вышла за границы поля и умерла!");
            ui.showLossScreen();
            gameState = 0;
            return;
        }

        turtle.moveTo(newX, newY);
        char cell = field.getCell(newX, newY);

        if (cell == '#') {
            println("Черепашка наткнулась на мину и погибла!");
            ui.showLossScreen();
            gameState = 0;
            return;
        }
        if (newX == field.getFlagX() && newY == field.getFlagY()) {
            println("Черепашка коснулась флага! Победа!");
            ui.showWinScreen();
            gameState = 0;
            return;
        }
        if (turtle.isPenDown()) { // Оставляем след только если перо опущено
            field.markCell(newX, newY, '*');
        }
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public void setMinePercentage(int minePercentage) {
        this.minePercentage = minePercentage;
    }
}
