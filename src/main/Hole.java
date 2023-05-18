package main;

import java.awt.*;

public class Hole {

    private int x, y;
    private int width, height;
    private int centerX, centerY;

    private Rectangle hitBox;

    private GamePanel gp;

    public Hole(int x, int y, GamePanel gp) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        width = gp.TILE_SIZE;
        height = gp.TILE_SIZE;

        centerX = x + (width / 2);
        centerY = y + (height / 2);

        hitBox = new Rectangle(x + 8, y + 8, width - 16, height - 16);
    }

    public void draw(Graphics2D g2) {
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
        g2.setStroke(new BasicStroke(1));
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
}
