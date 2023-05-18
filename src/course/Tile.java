package course;

import main.GamePanel;
import main.Player;

import java.awt.*;

public class Tile {

    private int x, y;
    private int centerX, centerY;
    private int width, height;
    private Rectangle hitBox;

    private GamePanel gp;

    private int type;

    // TODO: PARTICLES
    private Particle[] particles = new Particle[5];
    private Color particleColor;

    /* TYPES
    0 = void
    1 = player
    2 = regular block
    3 = sand
    9 = hole
    */

    public Tile(int x, int y, int type, GamePanel gp) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        this.type = type;
        width = gp.TILE_SIZE;
        height = gp.TILE_SIZE;

        centerX = x + (width / 2);
        centerY = y + (height / 2);

        hitBox = new Rectangle(x, y, width, height);
    }

    public void update() {
        centerX = x + (width / 2);
        centerY = y + (height / 2);

        for (int i = 0; i < particles.length; i++) {
            if (particles[i] != null) {
                particles[i].update();
                if (!particles[i].isActive()) {
                    particles[i] = null;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        update();

        if (type == 1) {
            g2.setColor(Color.WHITE);
            g2.fillOval(x, y, width, height);
        }

        if (type == 2) {
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, width, height);
        }

        if (type == 3) {
            g2.setColor(new Color(255, 219, 0, 255));
            particleColor = new Color(255, 219, 0, 255);
            g2.fillRect(x, y, width, height);
        }

        if (type == 9) {
            g2.setColor(Color.RED);
            g2.fillOval(x + 2, y + 2, width - 4, height - 4);

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(3 * gp.SCALE));
            g2.drawLine(centerX, centerY, centerX, (int) (centerY - (35 * gp.SCALE)));

            g2.setColor(Color.WHITE);
            int[] xPts = {(centerX - 1), centerX - 1, (int) (centerX + (20 * gp.SCALE))};
            int[] yPts = {(int) (centerY - (35 * gp.SCALE) - 2), (int) (centerY - (15 * gp.SCALE) - 2), (int) (centerY - (25 * gp.SCALE))};
            Polygon flag = new Polygon(xPts, yPts, xPts.length);
            g2.fillPolygon(flag);
        }

        for (Particle p : particles) {
            if (p != null) {
                p.draw(g2);
            }
        }
    }

    public void startHitParticles() {
        for (int i = 0; i < particles.length; i++) {
            double xVel = gp.getPlayer().getXVel() + (Math.random() * -5.0) + 5.0;
            double yVel = gp.getPlayer().getYVel() + (Math.random() * -5.0) + 5.0;
            particles[i] = new Particle(centerX, centerY, particleColor, xVel, -yVel);
        }
    }

    public int getType() {
        return type;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
