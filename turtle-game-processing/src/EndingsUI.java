import controlP5.*;
import processing.core.*;


public class EndingsUI {
    private final PApplet parent;
    private final Main mainApp;
    private final ControlP5 cp5;

    private boolean showEndScreen = false;
    private String endMessage = "";
    public Button returnToMenuButton;

    public EndingsUI(PApplet parent, Main mainApp, ControlP5 cp5) {
        this.parent = parent;
        this.mainApp = mainApp;
        this.cp5 = cp5;
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
        showEndScreen("The turtle fell into the water and survived!");
    }

    public void showLossScreen() {
        showEndScreen("The turtle stepped on a mine and died.");
    }

    public void showEatenByFoxScreen() {
        showEndScreen("The turtle was eaten by the Fox.");
    }

    public void showRavineDeathScreen() {
        showEndScreen("The turtle fell into a ravine and died.");
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
