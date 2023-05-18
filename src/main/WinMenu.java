package main;

import javax.imageio.ImageIO;
import java.awt.*;

import java.awt.image.BufferedImage;


public class WinMenu {

    private GamePanel gp;

    private int width, height;
    private int x, y;

    public WinMenu(GamePanel gp, int x, int y, int width, int height) {
        this.gp = gp;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2) {
        // TODO: BACKGROUND
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y, width, height, 20, 20);

        g2.setColor(Color.WHITE);
        g2.drawRoundRect(x, y, width, height, 20, 20);

        // TODO: STROKES
        g2.setFont(new Font("Helvetica Bold", Font.BOLD, 25));

        int stringLength = g2.getFontMetrics().stringWidth("Strokes: " + gp.getPlayer().getStrokes());
        g2.setColor(Color.WHITE);
        g2.drawString("Strokes: " + gp.getPlayer().getStrokes(), (x + width / 2) - (stringLength / 2), y + gp.TILE_SIZE);
        stringLength = g2.getFontMetrics().stringWidth("High Score: " + gp.getCurrentCourse().getHighScore());
        g2.drawString("High Score: " + gp.getCurrentCourse().getHighScore(), (x + width / 2) - (stringLength / 2), y + gp.TILE_SIZE * 3);
    }
}
