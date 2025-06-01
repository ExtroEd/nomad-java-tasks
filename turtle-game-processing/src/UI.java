import controlP5.*;
import processing.core.PApplet;


public class UI {
    private final PApplet parent;
    private final Main mainApp;
    private final ControlP5 cp5;

    private boolean showEndScreen = false;
    private String endMessage = "";

    public Button returnToMenuButton;
    private String errorMessage = "";
    private long errorStartTime = 0;
    private final int errorDuration = 2000;

    public UI(PApplet parent, Main mainApp) {
        this.parent = parent;
        this.cp5 = new ControlP5(parent);
        this.mainApp = mainApp;
    }

    public void setup() {
        cp5.setAutoDraw(true);

        cp5.addTextfield("gridSizeInput")
                .setPosition(parent.width / 2.0f - 100, parent.height / 2.0f - 80)
                .setSize(300, 50)
                .setAutoClear(false)
                .setText("10")
                .setFont(parent.createFont("Arial", 32))
                .setCaptionLabel("");

        cp5.addTextfield("minePercentageInput")
                .setPosition(parent.width / 2.0f - 100, parent.height / 2.0f + 20)
                .setSize(300, 50)
                .setAutoClear(false)
                .setText("10")
                .setFont(parent.createFont("Arial", 32))
                .setCaptionLabel("");

        cp5.addButton("startGame")
                .setLabel("Start Game")
                .setPosition(parent.width / 2.0f - 100, parent.height / 2.0f + 120)
                .setSize(200, 50)
                .setColorBackground(parent.color(50, 150, 250))
                .setColorForeground(parent.color(30, 100, 200))
                .setColorActive(parent.color(20, 80, 180))
                .setColorLabel(parent.color(255))
                .setFont(parent.createFont("Arial", 24))
                .onClick(_ -> mainApp.startGame());
    }

    void showTooltip(String message) {
        errorMessage = message;
        errorStartTime = System.currentTimeMillis();
    }

    void hideTooltip() {
        errorMessage = "";
    }

    public void drawMenu() {
        parent.background(230);
        parent.fill(20, 80, 200);
        parent.textSize(60);
        parent.textAlign(PApplet.CENTER, PApplet.CENTER);
        parent.text("Turtle Minesweeper", parent.width / 2f, parent.height * 0.15f);

        parent.fill(0);
        parent.textSize(24);
        parent.textAlign(PApplet.RIGHT, PApplet.CENTER);
        parent.text("Grid size:", parent.width / 2f - 110, parent.height / 2f - 55);
        parent.text("Mine %:", parent.width / 2f - 110, parent.height / 2f + 45);

        parent.textAlign(PApplet.LEFT, PApplet.CENTER);
        parent.text("10 - 500", parent.width / 2f + 210, parent.height / 2f - 55);
        parent.text("0 - 60", parent.width / 2f + 210, parent.height / 2f + 45);

        cp5.draw();

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

    public void remove() {
        Textfield gridInput = cp5.get(Textfield.class, "gridSizeInput");
        if (gridInput != null) gridInput.hide();

        Textfield mineInput = cp5.get(Textfield.class, "minePercentageInput");
        if (mineInput != null) mineInput.hide();

        Button startBtn = cp5.get(Button.class, "startGame");
        if (startBtn != null) startBtn.hide();

        hideTooltip();
    }

    public void showMenuElements() {
        Textfield gridInput = cp5.get(Textfield.class, "gridSizeInput");
        if (gridInput != null) gridInput.show();

        Textfield mineInput = cp5.get(Textfield.class, "minePercentageInput");
        if (mineInput != null) mineInput.show();

        Button startBtn = cp5.get(Button.class, "startGame");
        if (startBtn != null) startBtn.show();
    }

    public int getGridSize() {
        Textfield field = cp5.get(Textfield.class, "gridSizeInput");
        return Integer.parseInt(field.getText());
    }

    public int getMinePercentage() {
        Textfield field = cp5.get(Textfield.class, "minePercentageInput");
        return Integer.parseInt(field.getText());
    }

    public void drawEndScreen() {
        if (showEndScreen) {
            parent.background(0, 0, 0, 150);
            parent.fill(255);
            parent.textSize(50);
            parent.textAlign(PApplet.CENTER, PApplet.CENTER);
            parent.text(endMessage, parent.width / 2f, parent.height / 2f - 50);

            if (returnToMenuButton != null) {
                returnToMenuButton.setVisible(true);
                returnToMenuButton.setLock(false);
            }
        } else {
            if (returnToMenuButton != null) {
                returnToMenuButton.setVisible(false);
                returnToMenuButton.setLock(true);
            }
        }
    }

    public void showEndScreen(String message) {
        this.endMessage = message;
        this.showEndScreen = true;

        if (returnToMenuButton == null) {
            returnToMenuButton = cp5.addButton("returnToMenu")
                    .setPosition(parent.width / 2f - 100, parent.height / 2f + 50)
                    .setSize(300, 50)
                    .setLabel("Back to menu")
                    .setFont(parent.createFont("Arial", 24))
                    .setColorBackground(parent.color(50, 150, 250))
                    .setColorForeground(parent.color(30, 100, 200))
                    .setColorActive(parent.color(20, 80, 180))
                    .setColorLabel(parent.color(255))
                    .onClick(_ -> {
                        showEndScreen = false;
                        endMessage = "";
                        mainApp.resetToMenu();
                        cp5.remove("returnToMenu");
                        returnToMenuButton = null;
                    });
        } else {
            returnToMenuButton.setVisible(true);
            returnToMenuButton.setLock(false);
        }
    }

    public void showWinScreen() {
        showEndScreen("The turtle fell into the sea! Victory!");
    }

    public void showLossScreen() {
        showEndScreen("The turtle is dead. Try again.");
    }

    public void resetEndScreen() {
        showEndScreen = false;
        endMessage = "";

        if (returnToMenuButton != null) {
            cp5.remove("returnToMenu");
            returnToMenuButton = null;
        }
    }
}
