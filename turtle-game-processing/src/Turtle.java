import processing.core.PApplet;

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
        x = newX;
        y = newY;
        if (penDown) {
            field.markCell(x, y, '*'); // Оставляем след, если перо опущено
        }
    }

    public void togglePen() {
        penDown = !penDown;
        PApplet.println("Перо " + (penDown ? "опущено" : "поднято"));
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

    // Очистка следа черепашки
    public void clearTrace(PApplet applet) {
        int cellSize = applet.width / field.getSize();
        int drawX = x * cellSize;
        int drawY = y * cellSize;

        // Очищаем только внутреннюю часть клетки, не затрагивая сетку
        applet.noStroke();
        applet.fill(255); // Цвет фона (белый)
        applet.rect(drawX + 1, drawY + 1, cellSize - 2, cellSize - 2); // Оставляем сетку нетронутой
    }
}
