import processing.core.PApplet;
import java.util.ArrayList;

public class SnowflakesAnimation extends PApplet {
    ArrayList<Snowflake> snowflakes = new ArrayList<>();
    float windAngle = 0;
    float targetWindAngle = 0;
    float cursorRadius = 30;

    public static void main(String[] args) {
        PApplet.main("SnowflakesAnimation");
    }

    public void settings() {
        fullScreen();
    }

    public void setup() {
        background(255);
        noCursor();
    }

    public void draw() {
        background(0);

        windAngle = lerp(windAngle, targetWindAngle, 0.05f);

        if (snowflakes.size() < 300) {
            snowflakes.add(new Snowflake(random(width), -10, random(0.5f, 2)));
        }

        for (int i = snowflakes.size() - 1; i >= 0; i--) {
            Snowflake snowflake = snowflakes.get(i);
            snowflake.update();
            snowflake.display();

            if (snowflake.y - snowflake.size > height) {
                snowflakes.remove(i);
            }
        }

        drawCursor();
    }

    public void keyPressed() {
        if (keyCode == LEFT) {
            targetWindAngle = -PI / 6;
        } else if (keyCode == RIGHT) {
            targetWindAngle = PI / 6;
        } else if (keyCode == DOWN) {
            targetWindAngle = 0;
        }
    }

    void drawCursor() {
        pushMatrix();
        translate(mouseX, mouseY);

        int layers = 30;
        float maxAlpha = 150;
        float minAlpha = 10;
        float radiusStep = cursorRadius / layers;

        for (int i = layers; i > 0; i--) {
            float alpha = map(i, 1, layers, maxAlpha, minAlpha);
            fill(255, 255, 0, alpha);
            noStroke();
            ellipse(0, 0, radiusStep * i * 2, radiusStep * i * 2);
        }

        popMatrix();
    }

    class Snowflake {
        float x, y;
        float speed;
        float size;
        float rotation;
        float rotationSpeed;
        int numBranches;
        String centerShape;
        ArrayList<Branch> branches = new ArrayList<>();
        int colorShade;

        Snowflake(float x, float y, float speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.size = random(2, 6);
            colorShade = (int) map(speed, 0.5f, 2, 100, 255);

            if (random(1) < 0.8) {
                numBranches = 6;
            } else {
                numBranches = 8;
            }

            float shapeChance = random(1);
            if (shapeChance < 0.25) {
                centerShape = "circle";
            } else if (shapeChance < 0.5) {
                centerShape = (numBranches == 6) ? "hexagon" : "octagon";
            } else if (shapeChance < 0.75) {
                centerShape = (numBranches == 6) ? "triangle" : "square";
            } else {
                centerShape = "none";
            }

            rotationSpeed = random(-0.02f, 0.02f);

            for (int i = 0; i < numBranches; i++) {
                float angle = TWO_PI / numBranches * i;
                branches.add(new Branch(angle));
            }
        }

        void update() {
            x += speed * sin(windAngle);
            y += speed * cos(windAngle);

            rotation += rotationSpeed;

            float distanceToCursor = dist(x, y, mouseX, mouseY);
            if (distanceToCursor < cursorRadius) {
                float angleToCursor = atan2(mouseY - y, mouseX - x);
                x -= cos(angleToCursor) * speed * 2;
                y -= sin(angleToCursor) * speed * 2;
            }

            if (x < -size) x = width + size;
            if (x > width + size) x = -size;
        }

        void display() {
            pushMatrix();
            translate(x, y);
            rotate(rotation);
            stroke(colorShade);
            noFill();

            switch (centerShape) {
                case "circle":
                    ellipse(0, 0, size * 2, size * 2);
                    break;
                case "hexagon":
                case "octagon":
                    drawPolygon(numBranches, size);
                    break;
                case "triangle":
                    drawPolygon(3, size);
                    break;
                case "square":
                    drawPolygon(4, size);
                    break;
            }

            for (Branch branch : branches) {
                branch.display();
            }
            popMatrix();
        }

        void drawPolygon(int sides, float radius) {
            beginShape();
            for (int i = 0; i < sides; i++) {
                float angel = TWO_PI / sides * i;
                float x = cos(angel) * radius;
                float y = sin(angel) * radius;
                vertex(x, y);
            }
            endShape(CLOSE);
        }

        class Branch {
            float angle;
            float branchLength;
            ArrayList<SubBranch> subBranches = new ArrayList<>();

            Branch(float angle) {
                this.angle = angle;
                this.branchLength = size * random(1.5f, 2f);

                for (int i = -1; i <= 1; i += 2) {
                    float subBranchAngle = angle + i * random(PI / 6, PI / 4);
                    subBranches.add(new SubBranch(subBranchAngle));
                }
            }

            void display() {
                float x1 = cos(angle) * branchLength;
                float y1 = sin(angle) * branchLength;
                line(0, 0, x1, y1);

                for (SubBranch subBranch : subBranches) {
                    subBranch.display(x1, y1);
                }
            }
        }

        class SubBranch {
            float angle;
            float subBranchLength;

            SubBranch(float angle) {
                this.angle = angle;
                this.subBranchLength = size * random(0.5f, 1f);
            }

            void display(float startX, float startY) {
                float x2 = startX + cos(angle) * subBranchLength;
                float y2 = startY + sin(angle) * subBranchLength;
                line(startX, startY, x2, y2);
            }
        }
    }
}
