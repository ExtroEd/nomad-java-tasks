import controlP5.*;
import processing.core.PApplet;

public class UI {
    private final PApplet parent;
    private final Main mainApp;
    private final ControlP5 cp5;
    private boolean showEndScreen = false;
    private String endMessage = "";

    private Button returnToMenuButton;
    private Button resetGameButton;

    public UI(PApplet parent, Main mainApp) {
        this.parent = parent;
        this.cp5 = new ControlP5(parent);
        this.mainApp = mainApp;
    }

    public void setup() {
        // Настройка текстового поля для размера поля
        cp5.addTextfield("gridSize")
                .setPosition(parent.width / 2.0f - 100, parent.height / 2.0f - 80)
                .setSize(300, 50)
                .setAutoClear(false)
                .setText("10")  // Значение по умолчанию
                .setFont(parent.createFont("Arial", 32))
                .setCaptionLabel(""); // Убираем встроенную метку

// Настройка текстового поля для процента мин
        cp5.addTextfield("minePercentage")
                .setPosition(parent.width / 2.0f - 100, parent.height / 2.0f + 20)
                .setSize(300, 50)
                .setAutoClear(false)
                .setText("10")  // Значение по умолчанию
                .setFont(parent.createFont("Arial", 32))
                .setCaptionLabel(""); // Убираем встроенную метку

        // Настройка метки для "Размер поля"
        cp5.addLabel("gridSizeLabel")
                .setText("Размер поля:")
                .setPosition(parent.width / 2.0f - 280, parent.height / 2.0f - 70)
                .setFont(parent.createFont("Arial", 24))
                .setColor(parent.color(0));

        // Настройка метки для "Процент мин"
        cp5.addLabel("minePercentageLabel")
                .setText("Процент мин:")
                .setPosition(parent.width / 2.0f - 280, parent.height / 2.0f + 30)
                .setFont(parent.createFont("Arial", 24))
                .setColor(parent.color(0));

        // Настроим кнопку для старта игры
        cp5.addButton("startGame")
                .setLabel("Начать игру")
                .setPosition(parent.width / 2.0f - 100, parent.height / 2.0f + 120)
                .setSize(200, 50)
                .setColorBackground(parent.color(50, 150, 250)) // Цвет фона кнопки
                .setColorForeground(parent.color(30, 100, 200)) // Цвет при наведении
                .setColorActive(parent.color(20, 80, 180)) // Цвет при нажатии
                .setColorLabel(parent.color(255)) // Цвет текста (белый)
                .setFont(parent.createFont("Arial", 24)) // Устанавливаем шрифт и размер текста
                .onClick(event -> {
                    String gridSizeText = cp5.get(Textfield.class, "gridSize").getText();
                    String minePercentageText = cp5.get(Textfield.class, "minePercentage").getText();

                    try {
                        int gridSize = Integer.parseInt(gridSizeText);
                        int minePercentage = Integer.parseInt(minePercentageText);

                        mainApp.setGridSize(gridSize);
                        mainApp.setMinePercentage(minePercentage);

                        mainApp.startGame();
                    } catch (NumberFormatException ex) {
                        PApplet.println("Ошибка ввода! Пожалуйста, введите целое число.");
                    }
                });
    }

    public void drawMenu() {
        parent.background(230);
        parent.fill(20, 80, 200);
        parent.textSize(60);
        parent.textAlign(PApplet.CENTER, PApplet.CENTER);
        parent.text("Черепаший сапёр", parent.width / 2f, parent.height * 0.15f);
    }

    public void remove() {
        cp5.hide();
    }

    public int getGridSize() {
        return Integer.parseInt(cp5.get(Textfield.class, "gridSize").getText());
    }

    public int getMinePercentage() {
        return Integer.parseInt(cp5.get(Textfield.class, "minePercentage").getText());
    }

    public void drawEndScreen() {
        if (showEndScreen) {
            parent.background(0, 0, 0, 150);
            parent.fill(255);
            parent.textSize(50);
            parent.textAlign(PApplet.CENTER, PApplet.CENTER);
            parent.text(endMessage, parent.width / 2f, parent.height / 2f - 50);

            if (returnToMenuButton == null) {
                returnToMenuButton = cp5.addButton("returnToMenu")
                        .setPosition(parent.width / 2f - 150, parent.height / 2f + 50)
                        .setSize(300, 60)
                        .setLabel("Вернуться в меню")
                        .setFont(parent.createFont("Arial", 28))
                        .setColorBackground(parent.color(200, 0, 0))
                        .setColorActive(parent.color(150, 0, 0))
                        .setColorForeground(parent.color(255))
                        .onClick(event -> {
                            showEndScreen = false;
                            mainApp.gameState = 0;
                        });
            }

            if (resetGameButton == null) {
                resetGameButton = cp5.addButton("resetGame")
                        .setPosition(parent.width / 2f - 150, parent.height / 2f + 120)
                        .setSize(300, 60)
                        .setLabel("Перезапустить")
                        .setFont(parent.createFont("Arial", 28))
                        .setColorBackground(parent.color(0, 200, 0))
                        .setColorActive(parent.color(0, 255, 0))
                        .setColorForeground(parent.color(255))
                        .onClick(event -> {
                            mainApp.resetGame();
                            showEndScreen = false;
                        });
            }
        }
    }

    public void showEndScreen(String message) {
        this.endMessage = message;
        this.showEndScreen = true;
    }

    public void showWinScreen() {
        showEndScreen("Черепашка коснулась флага! Победа!");
    }

    public void showLossScreen() {
        showEndScreen("Черепашка погибла. Попробуйте снова.");
    }

}
