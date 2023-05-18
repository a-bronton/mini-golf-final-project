package creation_panel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Spot {

    private int x, y;
    private int width, height;
    private int centerX, centerY;

    private CreationScreen cp;

    private Rectangle hitBox;

    private boolean hover;

    private int tileType;

    public static Point mousePoint = new Point();

    public Spot(int x, int y, CreationScreen cp) {
        this.x = x;
        this.y = y;
        this.cp = cp;

        width = cp.TILE_SIZE;
        height = cp.TILE_SIZE;

        hitBox = new Rectangle(x, y, width, height);

        centerX = x + (width / 2);
        centerY = y + (height / 2);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(28, 94, 25));
        g2.fillRect(x, y, width, height);

        if (hover) {
            g2.setColor(new Color(255, 255, 255, 100));
            g2.fillRect(x, y, width, height);
        }

        if (tileType == 2) {
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, width, height);
        }

        if (tileType == 3) {
            g2.setColor(Color.YELLOW);
            g2.fillRect(x, y, width, height);
        }

        if (tileType == 9) {
            g2.setColor(Color.RED);
            g2.fillOval(x, y, width, height);

            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(centerX, centerY, centerX, centerY - 15);

            int[] xPts = {centerX - 1, centerX - 1, centerX + 14};
            int[] yPts = {centerY - 20, centerY - 10, centerY - 15};
            Polygon flag = new Polygon(xPts, yPts, xPts.length);
            g2.fillPolygon(flag);
        }

        if (tileType == 1) {
            g2.setColor(Color.WHITE);
            g2.fillOval(centerX - (width / 2),centerY - (height / 2), width, height);
        }

        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(255, 255, 255, 20));
        g2.drawRect(x, y, width, height);
    }

    public void mouseDragged(MouseEvent e) {
        if (hitBox.contains(e.getPoint())) {
            if (cp.isErasing()) {
                tileType = 0;
            } else {
                tileType = 2;
            }
        }
        mouseMoved(e);
    }

    public void mouseMoved(MouseEvent e) {
        hover = false;
        if (hitBox.contains(e.getPoint())) {
            hover = true;
        }

        mousePoint.x = e.getX();
        mousePoint.y = e.getY();
    }

    public void mousePressed(MouseEvent e) {
        if (hitBox.contains(e.getPoint())) {
            if (cp.isErasing()) {
                tileType = 0;
            } else {
                tileType = 2;
            }
        }
    }

    public void keyPressed(KeyEvent e) {

    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setTileType(int tileType) {
        if (tileType == 1) {
            for (Spot[] spotRow : cp.getSpots()) {
                for (Spot s : spotRow) {
                    if (s.getTileType() == 1) {
                        s.setTileType(0);
                    }
                }
            }
        }
        if (tileType == 9) {
            for (Spot[] spotRow : cp.getSpots()) {
                for (Spot s : spotRow) {
                    if (s.getTileType() == 9) {
                        s.setTileType(0);
                    }
                }
            }
        }
        this.tileType = tileType;
        System.out.println("Set tile type: " + tileType);
    }

    public int getTileType() {
        return tileType;
    }
}
