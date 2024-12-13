import processing.core.PApplet;

public class Game15 extends PApplet {

    private int[][] gameField;
    private float x, y, extent;
    private int emptyRow, emptyCol;
    private String errorMessage = "";
    private float alpha = 0;
    private boolean showInstructions = true;

    @Override
    public void settings() {
        fullScreen();
    }

    @Override
    public void setup() {
        gameField = new int[4][4];
        int count = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gameField[i][j] = count++;
            }
        }
        gameField[3][3] = 16; // Пустая клетка
        emptyRow = 3;
        emptyCol = 3;

        x = width / 3f;
        y = 200f;
        extent = (width / 3f) / 4;
    }

    @Override
    public void draw() {
        background(0);

        if (showInstructions) {
            drawInstructions();
        } else {
            init();
            drawTiles();

            if (!errorMessage.isEmpty()) {
                fill(255, alpha);
                textSize(40);
                textAlign(CENTER, CENTER);
                text(errorMessage, width / 2f, height - 100);
                alpha -= 2;
                if (alpha <= 0) {
                    errorMessage = "";
                    alpha = 0;
                }
            }
        }
    }

    private void drawInstructions() {
        fill(255);
        textSize(60);
        textAlign(CENTER, CENTER);
        text("15 Puzzle Game\n\nControls:\nUse arrow keys or click tiles to move\nPress SPACE to shuffle\nPress ESC to exit\n\nPress 'OK' to start the game", width / 2f, height / 2f - 100);

        fill(200, 100, 0);
        rect(width / 2f - 100, height / 2f + 350, 200, 80, 20);
        fill(255);
        textSize(40);
        text("OK", width / 2f, height / 2f + 390);
    }

    private void drawTiles() {
        textAlign(LEFT, BASELINE);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameField[i][j] != 16) {
                    fill(255, 255, 0);
                    textSize(40);
                    text(gameField[i][j], x + j * extent + extent / 2f - 20, y + i * extent + extent / 2f + 10);
                }
            }
        }
    }

    public void init() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                fill(100, 0, 255);
                square(x + j * extent, y + i * extent, extent);
            }
        }
    }

    @Override
    public void mousePressed() {
        if (showInstructions && mouseX >= width / 2f - 100 && mouseX <= width / 2f + 100 &&
                mouseY >= height / 2f + 350 && mouseY <= height / 2f + 430) {
            showInstructions = false;
        } else if (!showInstructions) {
            handleTileClick();
        }
    }

    private void handleTileClick() {
        int col = (int) ((mouseX - x) / extent);
        int row = (int) ((mouseY - y) / extent);

        if (row >= 0 && row < 4 && col >= 0 && col < 4) {
            if (isAdjacentToEmpty(row, col)) {
                swap(row, col, emptyRow, emptyCol);
                emptyRow = row;
                emptyCol = col;
            }
        }
    }

    private boolean isAdjacentToEmpty(int row, int col) {
        return (Math.abs(row - emptyRow) == 1 && col == emptyCol) ||
                (Math.abs(col - emptyCol) == 1 && row == emptyRow);
    }

    @Override
    public void keyPressed() {
        if (showInstructions) return;

        switch (keyCode) {
            case UP -> moveDown();
            case DOWN -> moveUp();
            case LEFT -> moveRight();
            case RIGHT -> moveLeft();
            case ' ' -> shuffleGameField();
            case ESC -> exit();
            default -> displayError();
        }
    }

    private void moveUp() {
        if (emptyRow > 0) {
            swap(emptyRow, emptyCol, emptyRow - 1, emptyCol);
            emptyRow--;
        }
    }

    private void moveDown() {
        if (emptyRow < 3) {
            swap(emptyRow, emptyCol, emptyRow + 1, emptyCol);
            emptyRow++;
        }
    }

    private void moveLeft() {
        if (emptyCol > 0) {
            swap(emptyRow, emptyCol, emptyRow, emptyCol - 1);
            emptyCol--;
        }
    }

    private void moveRight() {
        if (emptyCol < 3) {
            swap(emptyRow, emptyCol, emptyRow, emptyCol + 1);
            emptyCol++;
        }
    }

    private void shuffleGameField() {
        for (int i = 0; i < 1000; i++) {
            int randDir = (int) random(4);
            switch (randDir) {
                case 0 -> moveUp();
                case 1 -> moveDown();
                case 2 -> moveLeft();
                case 3 -> moveRight();
            }
        }
    }

    private void swap(int row1, int col1, int row2, int col2) {
        int temp = gameField[row1][col1];
        gameField[row1][col1] = gameField[row2][col2];
        gameField[row2][col2] = temp;
    }

    private void displayError() {
        errorMessage = "Incorrect button pressed!";
        alpha = 255;
    }

    public static void main(String[] args) {
        PApplet.main("Game15");
    }
}
