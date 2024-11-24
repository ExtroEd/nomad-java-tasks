import processing.core.PApplet;
import java.util.ArrayList;

public class SnowflakesAnimation extends PApplet {
    ArrayList<Snowflake> snowflakes = new ArrayList<>();

    public static void main(String[] args) {
        PApplet.main("SnowflakesAnimation");
    }

    public void settings() {
        fullScreen();
    }

    public void setup() {
        background(255);
    }

    public void draw() {
        background(0);
        fill(255);
        noStroke();

        if (snowflakes.size() < 200) {
            snowflakes.add(new Snowflake(random(width), -10, random(1, 3)));
        }

        for (Snowflake snowflake : snowflakes) {
            snowflake.update();
            snowflake.display();
        }
    }

    class Snowflake {
        float x, y;
        float speed;
        float size;
        int numBranches = 6; // Количество веточек снежинки
        float angleOffset;
        ArrayList<Branch> branches = new ArrayList<>(); // Храним ветви

        Snowflake(float x, float y, float speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.size = random(5, 10); // Увеличим размер для лучшего эффекта
            this.angleOffset = random(TWO_PI);

            // Генерация уникальных ветвей только один раз
            for (int i = 0; i < numBranches; i++) {
                float angle = TWO_PI / numBranches * i;
                branches.add(new Branch(angle)); // Создаём ветви при генерации
            }
        }

        void update() {
            y += speed;
            if (y > height) {
                y = -size;
                x = random(width);
            }
        }

        void display() {
            pushMatrix();
            translate(x, y);
            rotate(angleOffset); // Вращение для случайности
            stroke(255);
            noFill();

            // Рисуем уже готовые ветви
            for (Branch branch : branches) {
                branch.display();
            }
            popMatrix();
        }

        class Branch {
            float angle;
            float branchLength;
            float branchThickness;
            ArrayList<SubBranch> subBranches = new ArrayList<>(); // Храним подветви

            Branch(float angle) {
                this.angle = angle;
                this.branchLength = size * random(1.2f, 1.5f);
                this.branchThickness = random(1, 3);

                // Генерация подветвей для каждой ветви
                for (int i = 0; i < 3; i++) {
                    float subBranchAngle = angle + random(-PI/3, PI/3); // Угол подветви
                    subBranches.add(new SubBranch(subBranchAngle)); // Создаём подветви при генерации
                }
            }

            void display() {
                float x1 = cos(angle) * branchLength;
                float y1 = sin(angle) * branchLength;

                // Основная ветка
                line(0, 0, x1, y1);

                // Рисуем подветви
                for (SubBranch subBranch : subBranches) {
                    subBranch.display(x1, y1); // Передаём координаты для начала подветви
                }
            }
        }

        class SubBranch {
            float angle;
            float branchLength;

            SubBranch(float angle) {
                this.angle = angle;
                this.branchLength = size * random(0.3f, 0.7f);
            }

            void display(float startX, float startY) {
                float x2 = startX + cos(angle) * branchLength;
                float y2 = startY + sin(angle) * branchLength;
                line(startX, startY, x2, y2);
            }
        }
    }
}