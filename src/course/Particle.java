package course;

import java.awt.*;

public class Particle extends Rectangle {

    private int x, y;
    private Color col;
    private double xVel, yVel;

    private boolean active = true;

    public Particle(int x, int y, Color col, double xVel, double yVel) {
        this.x = x;
        this.y = y;
        this.col = col;
        this.xVel = xVel;
        this.yVel = yVel;
    }

    public void update() {
        x += xVel;
        xVel *= 0.95;
        y += yVel;
        yVel += 0.2;

        if (xVel < 0.2 && xVel > 0 || xVel < 0 && xVel > -0.2) {
            active = false;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(col);
        g2.fillRect(x, y, 5, 5);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
