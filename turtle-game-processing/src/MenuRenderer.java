import processing.core.PApplet;
import controlP5.ControlP5;
import processing.core.PFont;


public class MenuRenderer {
    private final PApplet parent;
    private final ControlP5 cp5;
    private final Main mainApp;
    private String errorMessage = "";
    private long errorStartTime = 0;

    public MenuRenderer(PApplet parent, ControlP5 cp5, Main mainApp) {
        this.parent = parent;
        this.cp5 = cp5;
        this.mainApp = mainApp;
    }

    public void drawMenu() {
        parent.background(230);
        parent.fill(20, 80, 200);
        parent.textSize(60);
        parent.textAlign(PApplet.CENTER, PApplet.CENTER);
        parent.text("Turtle Minesweeper", parent.width / 2f, parent.height * 0.15f);

        drawSplashText();

        parent.fill(0);
        parent.textSize(24);
        parent.textAlign(PApplet.RIGHT, PApplet.CENTER);
        parent.text("Grid size:", parent.width / 2f - 110, parent.height / 2f - 80);
        parent.text("Mine %:", parent.width / 2f - 110, parent.height / 2f - 10);

        parent.textAlign(PApplet.LEFT, PApplet.CENTER);
        parent.text("10 - 500", parent.width / 2f + 210, parent.height / 2f - 80);
        parent.text("0 - 60", parent.width / 2f + 210, parent.height / 2f - 10);

        parent.text("Enable enemy fox:", parent.width / 2f - 305, parent.height / 2f + 50);

        parent.text("Fox speed:", parent.width / 2f - 225, parent.height / 2f + 120);
        parent.text("1 - 6 squares/sec", parent.width / 2f + 210, parent.height / 2f + 120);

        cp5.draw();

        final int errorDuration = 2000;

        if (!errorMessage.isEmpty() && System.currentTimeMillis() - errorStartTime < errorDuration) {
            parent.fill(255, 80, 80);
            parent.rectMode(PApplet.CENTER);
            parent.rect(parent.width / 2f, parent.height / 2f - 150, 400, 60, 10);
            parent.fill(255);
            parent.textAlign(PApplet.CENTER, PApplet.CENTER);
            parent.textSize(20);
            parent.text(errorMessage, parent.width / 2f, parent.height / 2f - 150);
            parent.rectMode(PApplet.CORNER);
        } else if (System.currentTimeMillis() - errorStartTime >= errorDuration) {
            hideTooltip();
        }
    }

    void drawSplashText() {
        float time = parent.millis() / 1000.0f;
        float scale = 1.0f + 0.1f * PApplet.sin(time * 2);
        float angle = PApplet.radians(-5);

        parent.pushMatrix();
        parent.translate(parent.width / 2f + 200, parent.height / 2f - 300);
        parent.scale(scale);
        parent.rotate(angle); // 👈 наклон
        parent.textAlign(PApplet.CENTER, PApplet.CENTER);

        PFont font = parent.createFont("Arial", 32, true);
        parent.textFont(font);

        parent.fill(0);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    parent.text(mainApp.selectedSplash, dx, dy);
                }
            }
        }

        parent.fill(255, 255, 0);
        parent.text(mainApp.selectedSplash, 0, 0);

        parent.popMatrix();
    }

    public void showTooltip(String message) {
        errorMessage = message;
        errorStartTime = System.currentTimeMillis();
    }

    public void hideTooltip() {
        errorMessage = "";
    }
}
