import processing.core.PApplet;
import controlP5.ControlP5;
import controlP5.Textfield;
import controlP5.Toggle;
import controlP5.Controller;


public class MenuInputsUI {
    private final PApplet parent;
    private final ControlP5 cp5;
    private final Main mainApp;

    public MenuInputsUI(PApplet parent, Main mainApp, ControlP5 cp5) {
        this.parent = parent;
        this.cp5 = cp5;
        this.mainApp = mainApp;
    }

    public void setup() {
        cp5.setAutoDraw(true);

        cp5.addTextfield("gridSizeInput")
                .setPosition(parent.width / 2.0f - 100, parent.height / 2.0f - 100)
                .setSize(300, 50)
                .setAutoClear(false)
                .setText("10")
                .setFont(parent.createFont("Arial", 32))
                .setCaptionLabel("");

        cp5.addTextfield("minePercentageInput")
                .setPosition(parent.width / 2.0f - 100, parent.height / 2.0f - 30)
                .setSize(300, 50)
                .setAutoClear(false)
                .setText("10")
                .setFont(parent.createFont("Arial", 32))
                .setCaptionLabel("");

        cp5.addToggle("enableEnemyFox")
                .setPosition(parent.width / 2.0f - 100, parent.height / 2.0f + 40)
                .setSize(30, 30)
                .setLabel("")
                .setValue(false)
                .setColorBackground(parent.color(200))
                .setColorActive(parent.color(50, 150, 250))
                .setColorForeground(parent.color(0));

        cp5.addTextfield("foxSpeedInput")
                .setPosition(parent.width / 2.0f - 100, parent.height / 2.0f + 100)
                .setSize(300, 50)
                .setAutoClear(false)
                .setText("3")
                .setFont(parent.createFont("Arial", 32))
                .setCaptionLabel("");

        cp5.addButton("startGame")
                .setLabel("Start Game")
                .setPosition(parent.width / 2.0f - 100, parent.height / 2.0f + 170)
                .setSize(200, 50)
                .setColorBackground(parent.color(50, 150, 250))
                .setColorForeground(parent.color(30, 100, 200))
                .setColorActive(parent.color(20, 80, 180))
                .setColorLabel(parent.color(255))
                .setFont(parent.createFont("Arial", 24))
                .onClick(_ -> mainApp.startGame());
    }

    public void setMenuElementsVisible(boolean visible) {
        String[] elementIds = {
                "gridSizeInput",
                "minePercentageInput",
                "enableEnemyFox",
                "foxSpeedInput",
                "startGame"
        };

        for (String id : elementIds) {
            Controller<?> ctrl = cp5.getController(id);
            if (ctrl != null) {
                if (visible) ctrl.show();
                else ctrl.hide();
            }
        }
    }

    public void remove() {
        setMenuElementsVisible(false);
    }

    public void showMenuElements() {
        setMenuElementsVisible(true);
    }

    public int getGridSize() {
        Textfield field = cp5.get(Textfield.class, "gridSizeInput");
        return Integer.parseInt(field.getText());
    }

    public int getMinePercentage() {
        Textfield field = cp5.get(Textfield.class, "minePercentageInput");
        return Integer.parseInt(field.getText());
    }

    public int getFoxSpeed() {
        Textfield field = cp5.get(Textfield.class, "foxSpeedInput");
        int val = Integer.parseInt(field.getText());
        return PApplet.constrain(val, 1, 6);
    }

    public boolean isEnemyEnabled() {
        Toggle toggle = cp5.get(Toggle.class, "enableEnemyFox");
        return toggle != null && toggle.getState();
    }
}
