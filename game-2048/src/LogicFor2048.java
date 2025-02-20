import java.util.Random;

public class LogicFor2048 {
    private final int[][] gameBoard;
    private int count;
    private int chance;

    public LogicFor2048() {
        gameBoard = new int[4][4];
        gameBoard[0][3] = 2;
        gameBoard[0][2] = 2;
        gameBoard[0][1] = 2;
        count = 0;
        chance = 4;
    }

    public void moveRight() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = gameBoard[i].length - 1; j > 0; j--) {
                for (int k = j - 1; k >= 0; k--) {
                    if (gameBoard[i][j] == 0 && gameBoard[i][k] != 0) {
                        gameBoard[i][j] = gameBoard[i][k];
                        gameBoard[i][k] = 0;
                        break;
                    }
                }
            }

            for (int j = gameBoard[i].length - 1; j > 0; j--) {
                if (gameBoard[i][j] == gameBoard[i][j - 1] && gameBoard[i][j] != 0) {
                    gameBoard[i][j] *= 2;
                    gameBoard[i][j - 1] = 0;
                }
            }

            for (int j = gameBoard[i].length - 1; j > 0; j--) {
                for (int k = j - 1; k >= 0; k--) {
                    if (gameBoard[i][j] == 0 && gameBoard[i][k] != 0) {
                        gameBoard[i][j] = gameBoard[i][k];
                        gameBoard[i][k] = 0;
                        break;
                    }
                }
            }
        }
    }

    public void moveLeft() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int temp = gameBoard[i][j];
                for (int k = j - 1; k >= 0; k--) {
                    if (gameBoard[i][k] == 0) {
                        gameBoard[i][k] = temp;
                        gameBoard[i][k + 1] = 0;
                    }
                }
            }

            for (int j = 0; j < 4; j++) {
                for (int k = j - 1; k >= 0; k--) {
                    if (gameBoard[i][j] == gameBoard[i][k]) {
                        gameBoard[i][k] *= 2;
                        gameBoard[i][j] = 0;
                    }
                }
            }

            for (int j = 0; j < 4; j++) {
                int temp = gameBoard[i][j];
                for (int k = j - 1; k >= 0; k--) {
                    if (gameBoard[i][k] == 0) {
                        gameBoard[i][k] = temp;
                        gameBoard[i][k + 1] = 0;
                    }
                }
            }
        }
    }

    public void moveUp() {
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 3; i++) {
                for (int k = i + 1; k < 4; k++) {
                    if (gameBoard[k][j] != 0 && gameBoard[i][j] == 0) {
                        gameBoard[i][j] = gameBoard[k][j];
                        gameBoard[k][j] = 0;
                    }
                }
            }

            for (int i = 0; i < 3; i++) {
                if (gameBoard[i][j] == gameBoard[i + 1][j] && gameBoard[i][j] != 0) {
                    gameBoard[i][j] *= 2;
                    gameBoard[i + 1][j] = 0;
                }
            }

            for (int i = 0; i < 3; i++) {
                for (int k = i + 1; k < 4; k++) {
                    if (gameBoard[k][j] != 0 && gameBoard[i][j] == 0) {
                        gameBoard[i][j] = gameBoard[k][j];
                        gameBoard[k][j] = 0;
                    }
                }
            }
        }
    }

    public void moveDown() {
        for (int j = 0; j < 4; j++) {
            for (int i = 3; i > 0; i--) {
                for (int k = i - 1; k >= 0; k--) {
                    if (gameBoard[k][j] != 0 && gameBoard[i][j] == 0) {
                        gameBoard[i][j] = gameBoard[k][j];
                        gameBoard[k][j] = 0;
                    }
                }
            }

            for (int i = 3; i > 0; i--) {
                if (gameBoard[i][j] == gameBoard[i - 1][j] && gameBoard[i][j] != 0) {
                    gameBoard[i][j] *= 2;
                    gameBoard[i - 1][j] = 0;
                }
            }

            for (int i = 3; i > 0; i--) {
                for (int k = i - 1; k >= 0; k--) {
                    if (gameBoard[k][j] != 0 && gameBoard[i][j] == 0) {
                        gameBoard[i][j] = gameBoard[k][j];
                        gameBoard[k][j] = 0;
                    }
                }
            }
        }
    }

    public void randomNumber() {
        Random rnd = new Random();
        int emptyCells = 0;

        // Подсчитываем количество пустых клеток
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameBoard[i][j] == 0) {
                    emptyCells++;
                }
            }
        }

        // Если есть пустые клетки, добавляем новое число
        if (emptyCells > 0) {
            int num = rnd.nextInt(emptyCells);
            int count = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (gameBoard[i][j] == 0) {
                        if (count == num) {
                            gameBoard[i][j] = rnd.nextInt(10) == 0 ? 4 : 2;  // 10% шанс на 4
                            return;
                        }
                        count++;
                    }
                }
            }
        }
    }

    public int getCoordinates(int x, int y) {
        return gameBoard[x][y];
    }
}
