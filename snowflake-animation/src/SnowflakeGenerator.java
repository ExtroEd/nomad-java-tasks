import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

//Нужно доделать код.

public class SnowflakeGenerator extends JPanel {
    private static final int WIDTH = 1920; // Ширина экрана
    private static final int HEIGHT = 1080; // Высота экрана

    private final Random random = new Random();

    public SnowflakeGenerator() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                repaint(); // Генерация новой снежинки при нажатии ЛКМ
            }
        });

        // Закрытие окна при нажатии на Escape
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        };
        addKeyListener(keyAdapter);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));

        // Генерация случайных параметров для снежинки
        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;
        int length = 150; // Уменьшенная длина главных веток снежинки

        // Рисуем центральную фигуру
        drawCenterShape(g2d, centerX, centerY);

        // Генерация количества веток (80% шанс на 6 веток, 20% шанс на 8)
        int numBranches = random.nextInt(5) < 4 ? 6 : 8;

        // Генерация веток
        for (int i = 0; i < numBranches; i++) { // 6 или 8 главных веток
            double angle = Math.toRadians(360.0 / numBranches * i);
            drawBranch(g2d, centerX, centerY, length, angle, 4);
        }
    }

    // Метод для рисования центральной фигуры (круг, шестиугольник, ромбы)
    private void drawCenterShape(Graphics2D g2d, int x, int y) {
        int shapeChoice = random.nextInt(5); // 0 - ничего, 1 - круг, 2 - шестиугольник, 3 - ромбы, 4 - ромбы между ветками
        int radius = 30 + random.nextInt(30); // Радиус центральной фигуры

        switch (shapeChoice) {
            case 1: // Круг
                g2d.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
                break;
            case 2: // Шестиугольник
                Polygon hexagon = new Polygon();
                for (int i = 0; i < 6; i++) {
                    double angle = Math.toRadians(60 * i);
                    int px = x + (int) (radius * Math.cos(angle));
                    int py = y + (int) (radius * Math.sin(angle));
                    hexagon.addPoint(px, py);
                }
                g2d.drawPolygon(hexagon);
                break;
            case 3: // Ромбы
                drawDiamonds(g2d, x, y, radius);
                break;
            case 4: // Ромбы между ветками
                drawDiamondsBetweenBranches(g2d, x, y, radius);
                break;
            default:
                break; // Ничего не рисуем
        }
    }

    // Метод для рисования ромбов в центре
    private void drawDiamonds(Graphics2D g2d, int x, int y, int radius) {
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);
            int dx = (int) (radius * Math.cos(angle));
            int dy = (int) (radius * Math.sin(angle));

            int[] xPoints = {x, x + dx, x + dx / 2, x - dx / 2};
            int[] yPoints = {y, y + dy, y + dy / 2, y - dy / 2};

            g2d.drawPolygon(xPoints, yPoints, 4);
        }
    }

    // Метод для рисования ромбов между ветками
    private void drawDiamondsBetweenBranches(Graphics2D g2d, int x, int y, int radius) {
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);
            int dx = (int) (radius * Math.cos(angle));
            int dy = (int) (radius * Math.sin(angle));

            int[] xPoints = {x, x + dx / 2, x + dx, x + dx / 2};
            int[] yPoints = {y, y + dy / 2, y + dy, y + dy / 2};

            g2d.drawPolygon(xPoints, yPoints, 4);
        }
    }

    // Метод для рисования ветки
    private void drawBranch(Graphics2D g2d, int x, int y, int length, double angle, int level) {
        if (level == 0) return;

        // Случайная толщина главной ветки
        int thickness = random.nextInt(3) + 1;
        g2d.setStroke(new BasicStroke(thickness));

        int endX = (int) (x + length * Math.cos(angle));
        int endY = (int) (y + length * Math.sin(angle));

        g2d.drawLine(x, y, endX, endY);

        // Разделение ветки на части для подветок
        int numParts = 4 + random.nextInt(2); // Разделяем на 4 или 5 частей
        for (int i = 1; i < numParts; i++) {
            double partX = x + (double) ((endX - x) * i) / numParts;
            double partY = y + (double) ((endY - y) * i) / numParts;
            double subBranchAngle = angle + (random.nextDouble() - 0.5) * Math.PI / 3; // Угол подветки от -60 до 60 градусов
            int subLength = length / 2 + random.nextInt(length / 4); // Случайная длина подветки
            drawBranch(g2d, (int) partX, (int) partY, subLength, subBranchAngle, level - 1);
        }

        // Генерация маленьких веточек с ограничением углов наклона
        int numSmallBranches = random.nextInt(5); // От 0 до 4 маленьких веточек
        for (int i = 0; i < numSmallBranches; i++) {
            double smallBranchAngle = angle + (random.nextDouble() - 0.5) * Math.PI / 4; // Угол маленькой веточки от -45 до 45 градусов
            int smallBranchLength = random.nextInt(10) + 5; // Случайная длина маленькой веточки
            g2d.drawLine(endX, endY,
                    (int) (endX + smallBranchLength * Math.cos(smallBranchAngle)),
                    (int) (endY + smallBranchLength * Math.sin(smallBranchAngle)));
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snowflake Generator");
        SnowflakeGenerator panel = new SnowflakeGenerator();

        // Настройка полноэкранного режима
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(frame);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        panel.requestFocusInWindow(); // Обеспечиваем, чтобы панель могла принимать события клавиш
    }
}