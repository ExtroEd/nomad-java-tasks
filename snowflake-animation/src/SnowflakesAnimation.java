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

        Snowflake(float x, float y, float speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.size = random(5, 10);
        }

        void update() {
            y += speed;
            if (y > height) {
                y = -size;
                x = random(width);
            }
        }

        void display() {
            ellipse(x, y, size, size);
        }
    }
}
