import controlP5.*;
import processing.core.PApplet;


public class UI {
    private final PApplet parent;
    private final Main mainApp;
    private final ControlP5 cp5;
    private boolean showEndScreen = false;
    private String endMessage = "";

    private Button returnToMenuButton;

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
                .setText("10")
                .setFont(parent.createFont("Arial", 32))
                .setCaptionLabel("");

        // Настройка текстового поля для процента мин
        cp5.addTextfield("minePercentage")
                .setPosition(parent.width / 2.0f - 100, parent.height / 2.0f + 20)
                .setSize(300, 50)
                .setAutoClear(false)
                .setText("10")
                .setFont(parent.createFont("Arial", 32))
                .setCaptionLabel("");

        // Настроим кнопку для старта игры
        cp5.addButton("startGame")
                .setLabel("Начать игру")
                .setPosition(parent.width / 2.0f - 100, parent.height / 2.0f + 120)
                .setSize(200, 50)
                .setColorBackground(parent.color(50, 150, 250))
                .setColorForeground(parent.color(30, 100, 200))
                .setColorActive(parent.color(20, 80, 180))
                .setColorLabel(parent.color(255))
                .setFont(parent.createFont("Arial", 24))
                .onClick(_ -> {
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

        parent.fill(0);
        parent.textSize(24);
        parent.textAlign(PApplet.RIGHT, PApplet.CENTER);
        parent.text("Размер поля:", parent.width / 2f - 110, parent.height / 2f - 55);
        parent.text("Процент мин:", parent.width / 2f - 110, parent.height / 2f + 45);
    }

    public void remove() {
        // Скрываем текстовые поля и кнопку, если они существуют
        if (cp5.get(Textfield.class, "gridSize") != null) {
            cp5.get(Textfield.class, "gridSize").hide();
        }
        if (cp5.get(Textfield.class, "minePercentage") != null) {
            cp5.get(Textfield.class, "minePercentage").hide();
        }
        if (cp5.get(Button.class, "startGame") != null) {
            cp5.get(Button.class, "startGame").hide();
        }
    }

    public void showMenuElements() {
        // Показываем текстовые поля и кнопку, если они существуют
        if (cp5.get(Textfield.class, "gridSize") != null) {
            cp5.get(Textfield.class, "gridSize").show();
        }
        if (cp5.get(Textfield.class, "minePercentage") != null) {
            cp5.get(Textfield.class, "minePercentage").show();
        }
        if (cp5.get(Button.class, "startGame") != null) {
            cp5.get(Button.class, "startGame").show();
        }
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

            // Отображаем кнопки, если они созданы
            if (returnToMenuButton != null) {
                returnToMenuButton.setVisible(true);
            }
        } else {
            // Скрываем кнопки, если экран завершения не отображается
            if (returnToMenuButton != null) {
                returnToMenuButton.setVisible(false);
            }
        }
    }

    public void showEndScreen(String message) {
        this.endMessage = message;
        this.showEndScreen = true;

        // Создаём кнопку "Вернуться в меню", если она ещё не создана
        if (returnToMenuButton == null) {
            returnToMenuButton = cp5.addButton("returnToMenu")
                    .setPosition(parent.width / 2f - 100, parent.height / 2f + 50) // Позиция кнопки
                    .setSize(300, 50) // Размер кнопки
                    .setLabel("Вернуться в меню") // Текст кнопки
                    .setFont(parent.createFont("Arial", 24)) // Шрифт текста
                    .setColorBackground(parent.color(50, 150, 250)) // Цвет фона (синий)
                    .setColorForeground(parent.color(30, 100, 200)) // Цвет при наведении (тёмно-синий)
                    .setColorActive(parent.color(20, 80, 180)) // Цвет при нажатии (ещё темнее)
                    .setColorLabel(parent.color(255)) // Цвет текста (белый)
                    .onClick(_ -> {
                        showEndScreen = false;
                        mainApp.resetToMenu(); // Вызываем метод для возврата в меню
                        cp5.remove("returnToMenu"); // Удаляем кнопку
                        returnToMenuButton = null; // Сбрасываем ссылку на кнопку
                    });
        } else {
            returnToMenuButton.setVisible(true); // Убедимся, что кнопка видима
        }
    }

    public void showWinScreen() {
        showEndScreen("Черепашка коснулась флага! Победа!");
    }

    public void showLossScreen() {
        showEndScreen("Черепашка погибла. Попробуйте снова.");
    }
}
