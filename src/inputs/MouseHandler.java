package inputs;

import creation_panel.Spot;
import main.GamePanel;
import ui.Button;

import java.awt.event.*;

public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

    private GamePanel gp;

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gp.getGameState() == gp.PLAYING) {
            for (Button b : gp.getUi().getButtons()) {
                b.mousePressed(e);
            }
        }

        if (gp.getGameState() == gp.CREATING) {
           gp.getCreateScreen().mousePressed(e);
        }

        if (gp.isCourseSelecting()) {
            gp.getCourseMenu().mousePressed(e);
        }

        if (gp.getCreateScreen().isMenuEnabled()) {
            gp.getCreateScreen().getEditorMenu().mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (gp.getGameState() == gp.PLAYING) {
            for (Button b : gp.getUi().getButtons()) {
                b.mouseReleased(e);
            }

            gp.getPlayer().mouseReleased(e);
        }

        if (gp.getGameState() == gp.CREATING) {
            gp.getCreateScreen().mouseReleased(e);
        }

        if (gp.isCourseSelecting()) {
            gp.getCourseMenu().mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (gp.getGameState() == gp.PLAYING) {
            gp.getPlayer().mouseDragged(e);
        }

        if (gp.getGameState() == gp.CREATING) {
            gp.getCreateScreen().mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (gp.getGameState() == gp.PLAYING) {
            for (Button b : gp.getUi().getButtons()) {
                b.mouseMoved(e);
            }
        }

        if (gp.getGameState() == gp.CREATING) {
            gp.getCreateScreen().mouseMoved(e);
        }

        if (gp.isCourseSelecting()) {
            gp.getCourseMenu().mouseMoved(e);
        }

        if (gp.getCreateScreen().isMenuEnabled()) {
            gp.getCreateScreen().getEditorMenu().mouseMoved(e);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (gp.isCourseSelecting()) {
            gp.getCourseMenu().mouseWheelMoved(e);
        }
    }
}
