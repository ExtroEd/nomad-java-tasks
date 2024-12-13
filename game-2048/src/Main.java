import processing.core.PApplet;

public class Main extends PApplet {

    private LogicFor2048 logicFor2048;

    @Override
    public void settings() {
        fullScreen();
    }

    @Override
    public void setup() {
        logicFor2048 = new LogicFor2048();
    }

    @Override
    public void draw() {
        background(0);
        drawBoard();
    }

    private void drawBoard() {
        // Увеличим расстояние между клетками
        float leftBorder = width / 3f;
        float topBorder = 200;
        float rectWidthSize = leftBorder / 5;  // Увеличиваем расстояние между кубиками
        float rectHeightSize = leftBorder / 5; // Увеличиваем расстояние между кубиками

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // Задаем цвет для ячеек в зависимости от значения
                int value = logicFor2048.getCoordinates(i, j);
                if (value == 0) {
                    fill(169, 169, 169);  // Серый цвет для пустых клеток
                } else {
                    // Цвета в зависимости от значений
                    switch (value) {
                        case 2: fill(238, 228, 218); break;
                        case 4: fill(237, 224, 200); break;
                        case 8: fill(242, 85, 40); break;
                        case 16: fill(245, 124, 95); break;
                        case 32: fill(247, 124, 95); break;
                        case 64: fill(246, 94, 59); break;
                        case 128: fill(246, 208, 109); break;
                        case 256: fill(245, 182, 87); break;
                        case 512: fill(245, 149, 35); break;
                        case 1024: fill(245, 124, 45); break;
                        case 2048: fill(255, 200, 0); break;
                        default: fill(255, 255, 255); break;  // Цвет по умолчанию
                    }
                }
                rect(leftBorder + j * rectWidthSize, topBorder + i * rectHeightSize, rectWidthSize, rectHeightSize);

                // Цвет текста
                if (value == 0) {
                    fill(169, 169, 169); // Светлый серый для пустых клеток
                } else {
                    fill(value > 4 ? 255 : 0); // Темный цвет для светлых значений (2 и 4)
                }

                // Жирный шрифт
                textSize(50);
                textAlign(CENTER, CENTER);
                textFont(createFont("Arial", 50, true));  // Устанавливаем жирный шрифт

                // Печатаем значение в клетке
                if (value != 0) {
                    text(value, leftBorder + j * rectWidthSize + rectWidthSize / 2, topBorder + i * rectHeightSize + rectHeightSize / 2);
                }
            }
        }
    }

    @Override
    public void keyPressed() {
        switch (keyCode) {
            case RIGHT:
                logicFor2048.moveRight();
                logicFor2048.randomNumber();
                drawBoard();
                break;
            case LEFT:
                logicFor2048.moveLeft();
                logicFor2048.randomNumber();
                drawBoard();
                break;
            case UP:
                logicFor2048.moveUp();
                logicFor2048.randomNumber();
                drawBoard();
                break;
            case DOWN:
                logicFor2048.moveDown();
                logicFor2048.randomNumber();
                drawBoard();
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}
