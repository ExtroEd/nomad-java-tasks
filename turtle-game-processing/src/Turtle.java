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
            field.markCell(x, y, '*');
        }
    }

    public void moveTo(int newX, int newY) {
        x = newX;
        y = newY;
        if (penDown) {
            field.markCell(x, y, '*');
        }
    }

    public void togglePen() {
        penDown = !penDown;
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

    public void display(PApplet applet) {
        if (penDown) {
            int cellSize = applet.width / field.getSize();
            int drawX = x * cellSize + cellSize / 2;
            int drawY = y * cellSize + cellSize / 2;

            applet.fill(0, 255, 0);
            applet.noStroke();
            applet.ellipse(drawX, drawY, cellSize * 0.6f, cellSize * 0.6f);
        }
    }
}
