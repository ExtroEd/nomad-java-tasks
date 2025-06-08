import processing.core.PApplet;
import controlP5.ControlP5;


public class Main extends PApplet {
    public static void main(String[] ignoredArgs) {
        PApplet.main("Main");
    }

    String[] splashTexts = {
            "Powered by TurtleForce!",
            "Now with enemy foxes!",
            "You shall not pass!",
            "Made in Processing!",
            "Mind the mines!",
            "Run or die!",
            "Victory = life.",
            "Are there still mines in Normandy?",
            "Entropy is inevitable.",
            "Pathfinding... completed!",
            "Quantum turtle is both dead and alive.",
            "Simulation speed: 1 turtleFLOP.",
            "AI is watching your moves.",
            "Fractal mines... almost.",
            "Random isn't truly random.",
            "The fox is always faster.",
            "Only 1% make it out alive.",
            "Error 404: Safety not found.",
            "Warning: May contain foxes.",
            "Certified turtle drama.",
            "This text changes randomly.",
            "Press Alt+F4 to win!",
            "Hidden ending unlocked.",
            "Yes, Normandy still has mines.",
            "Better than Minesweeper (probably).",
            "Fox AI > Doom AI?",
            "RTX ON: Not really.",
            "Based on a true simulation.",
            "Not sponsored by NVIDIA.",
    };

    String selectedSplash;

    private int gameState = 0;
    private MenuInputsUI menuInputsUI;
    private MenuRenderer menuRenderer;
    private EndingsUI endingsUI;
    private GameManager game;
    private ControlP5 cp5;

    public void settings() {
        fullScreen();
    }

    public void setup() {
        cp5 = new ControlP5(this);

        selectedSplash = splashTexts[(int) random(splashTexts.length)];

        menuInputsUI = new MenuInputsUI(this, this, cp5);
        menuInputsUI.setup();

        menuRenderer = new MenuRenderer(this, cp5, this);

        endingsUI = new EndingsUI(this, this, cp5);
        game = new GameManager(this, endingsUI);

        cp5.getController("startGame")
                .onClick(_ -> startGame());
    }

    public void draw() {
        background(230);

        if (gameState == 0) {
            menuRenderer.drawMenu();
        } else if (gameState == 1) {
            if (!game.isReady()) {
                game.start(
                        menuInputsUI.getGridSize(),
                        menuInputsUI.getMinePercentage(),
                        menuInputsUI.isEnemyEnabled(),
                        menuInputsUI.getFoxSpeed()
                );
            }
            game.updateAndDraw();
        }

        endingsUI.drawEndScreen();
    }

    public void startGame() {
        try {
            int gridSize = menuInputsUI.getGridSize();
            int minePercentage = menuInputsUI.getMinePercentage();

            if (gridSize < 10 || gridSize > 500) {
                menuRenderer.showTooltip("Grid size must be from 10 to 500.");
                return;
            }

            if (minePercentage < 0 || minePercentage > 60) {
                menuRenderer.showTooltip("Mine percentage must be from 0 to 60.");
                return;
            }

            int foxSpeed = menuInputsUI.getFoxSpeed();
            boolean enemyEnabled = menuInputsUI.isEnemyEnabled();

            game.start(gridSize, minePercentage, enemyEnabled, foxSpeed);
            menuInputsUI.remove();
            menuRenderer.hideTooltip();
            gameState = 1;
        } catch (NumberFormatException ex) {
            menuRenderer.showTooltip("Invalid input! Please enter integers.");
        }
    }

    public void keyPressed() {
        if (gameState == 1) {
            int dx = 0, dy = 0;
            if (keyCode == UP || keyCode == 'W') dy = -1;
            else if (keyCode == DOWN || keyCode == 'S') dy = 1;
            else if (keyCode == LEFT || keyCode == 'A') dx = -1;
            else if (keyCode == RIGHT || keyCode == 'D') dx = 1;
            else if (keyCode == 'E') game.togglePen();
            if (dx != 0 || dy != 0) game.moveTurtle(dx, dy);
        }
    }

    public void mouseWheel(processing.event.MouseEvent event) {
        game.handleZoom(event.getCount() > 0 ? 0.9f : 1.1f);
    }

    public void mousePressed() {
        game.startDragging(mouseX, mouseY);
    }

    public void mouseDragged() {
        game.dragTo(mouseX, mouseY);
    }

    public void mouseReleased() {
        game.stopDragging();
    }

    public void resetToMenu() {
        gameState = 0;
        game.reset();
        endingsUI.resetEndScreen();
        menuInputsUI.showMenuElements();
    }
}
