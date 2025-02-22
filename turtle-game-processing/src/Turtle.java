import processing.core.PApplet;

import static processing.core.PApplet.println;


public class Turtle {
    private int x, y;
    private boolean penDown;
    private final Field field;

    public Turtle(int startX, int startY, boolean penDown, Field field) {
        this.x = startX;
        this.y = startY;
        this.penDown = penDown;
        this.field = field;
        if (penDown) {
            field.markCell(x, y, '*'); // Оставляем след, если перо опущено
        }
    }

    public void moveTo(int newX, int newY) {
        println("Черепашка перемещается на: (" + newX + ", " + newY + ")"); // Отладочное сообщение
        x = newX;
        y = newY;
        if (penDown) {
            field.markCell(x, y, '*'); // Оставляем след, если перо опущено
        }
    }

    public void togglePen() {
        penDown = !penDown;
        println("Перо " + (penDown ? "опущено" : "поднято"));
    }

    public boolean isPenDown() {
        return penDown;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Отрисовка черепашки
    public void display(PApplet applet) {
        if (penDown) { // Отрисовываем черепашку только если перо опущено
            int cellSize = applet.width / field.getSize();
            int drawX = x * cellSize + cellSize / 2;
            int drawY = y * cellSize + cellSize / 2;

            applet.fill(0, 0, 255); // Синий цвет черепашки
            applet.noStroke(); // Убираем обводку
            applet.ellipse(drawX, drawY, cellSize * 0.6f, cellSize * 0.6f); // Рисуем черепашку
        }
    }
}
