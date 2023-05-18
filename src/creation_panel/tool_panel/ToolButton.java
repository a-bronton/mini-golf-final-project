package creation_panel.tool_panel;

import course.Tile;
import creation_panel.CreationScreen;
import creation_panel.Spot;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ToolButton {

    private ToolPanel tp;
    private int x, y;
    private int originalX, originalY;
    private int tileType;
    private Tile tile;

    private boolean hover, mouseDown;

    private static Point mousePoint = new Point();

    private CreationScreen cs;

    private Rectangle hitBox;

    public ToolButton(ToolPanel tp, int x, int y, int tileType) {
        this.tp = tp;
        this.x = x;
        this.y = y;
        originalX = x;
        originalY = y;
        this.tileType = tileType;
        cs = tp.getCreationScreen();

        tile = new Tile(x, y, tileType, tp.getGamePanel());

        hitBox = new Rectangle(x, y, tile.getWidth(), tile.getHeight());
    }

    public ToolButton(ToolPanel tp, int tileType) {
        this.tp = tp;
        this.x = 0;
        this.y = 0;
        originalX = x;
        originalY = y;
        this.tileType = tileType;
        cs = tp.getCreationScreen();

        tile = new Tile(x, y, tileType, tp.getGamePanel());

        hitBox = new Rectangle(x, y, tile.getWidth(), tile.getHeight());
    }

    public void update() {
        if (mouseDown) {
            x = mousePoint.x - (tile.getWidth() / 2);
            y = mousePoint.y - (tile.getHeight() / 2);
        }

        tile.setX(x);
        tile.setY(y);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(255, 255, 255, 80));
        g2.fillRoundRect(x - 5, y - 5, hitBox.width + 10, hitBox.height + 10, 5, 5);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        Tile ghostTile = new Tile(originalX, originalY, tile.getType(), tp.getGamePanel());
        ghostTile.draw(g2);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        tile.draw(g2);
    }

    public void mouseMoved(MouseEvent e) {
        hover = false;
        if (hitBox.contains(e.getPoint())) {
            hover = true;
        }

        mousePoint.x = e.getX();
        mousePoint.y = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
        mousePoint.x = e.getX();
        mousePoint.y = e.getY();
    }

    public void mousePressed(MouseEvent e) {
        mouseDown = false;
        if (hitBox.contains(e.getPoint())) {
            mouseDown = true;
            tp.setDragDropping(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (mouseDown) {
            mouseDown = false;

            x = originalX;
            y = originalY;
            tile.setX(x);
            tile.setY(y);

            mousePoint.x = e.getX();
            mousePoint.y = e.getY();

            tp.setDragDropping(false);

            // TODO: PLACE
            Spot[][] spots = tp.getCreationScreen().getSpots();
            for (Spot[] spotRow : spots) {
                for (Spot s : spotRow) {
                    if (s.getHitBox().contains(e.getPoint())) {
                        s.setTileType(tileType);
                    }
                }
            }
        }
    }

    public void setLocation(Point p) {
        this.x = p.x;
        this.y = p.y;
        originalX = x;
        originalY = y;
        hitBox.x = x;
        hitBox.y = y;
    }
}
