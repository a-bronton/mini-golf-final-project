package creation_panel.tool_panel;

import creation_panel.CreationScreen;
import main.GamePanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ToolPanel {

    private int x, y, width, height;

    private ArrayList<ToolButton> buttons = new ArrayList<>();

    private CreationScreen cs;
    private GamePanel gp;

    private static boolean dragDropping = false;

    // TODO: TOOL BUTTON PLACEMENT
    int bX;
    int bY;

    public ToolPanel(int x, int y, int width, int height, CreationScreen cs) {
        this.cs = cs;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        gp = cs.getGamePanel();
        bX = x + 15;
        bY = y + 15;

        initButtons();
    }

    public void initButtons() {
        ToolButton holeButton = new ToolButton(this, x + gp.TILE_SIZE / 2, y + gp.TILE_SIZE / 2, 9);
        addButton(holeButton);

        ToolButton playerButton = new ToolButton(this, x + gp.TILE_SIZE * 2, y + gp.TILE_SIZE / 2, 1);
        addButton(playerButton);

        ToolButton sandButton = new ToolButton(this, x + gp.TILE_SIZE / 2, y + gp.TILE_SIZE * 2, 3);
        addButton(sandButton);
    }

    public void addButton(ToolButton b) {
        b.setLocation(new Point(bX, bY));
        buttons.add(b);

        bX += gp.TILE_SIZE + 15;
        if (bX >= (gp.TILE_SIZE * 5)) {
            bX = x + 15;
            bY += gp.TILE_SIZE + 15;
        }
    }

    public void update() {
        for (ToolButton tb : buttons) {
            tb.update();
        }
    }

    public void mouseMoved(MouseEvent e) {
        for (ToolButton tb : buttons) {
            tb.mouseMoved(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        for (ToolButton tb : buttons) {
            tb.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (ToolButton tb : buttons) {
            tb.mouseReleased(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        for (ToolButton tb : buttons) {
            tb.mouseDragged(e);
        }
    }

    public void draw(Graphics2D g2) {
        // TODO: BACKGROUND
        g2.setStroke(new BasicStroke(3));
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y, width, height, 5, 5);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(x, y, width, height, 5, 5);

        // TODO: BUTTONS
        for (ToolButton tb : buttons) {
            tb.draw(g2);
        }
    }

    public GamePanel getGamePanel() {
        return cs.getGamePanel();
    }

    public CreationScreen getCreationScreen() {
        return cs;
    }

    public void setDragDropping(boolean dragDropping) {
        this.dragDropping = dragDropping;
    }

    public boolean isDragDropping() {
        return dragDropping;
    }
}
