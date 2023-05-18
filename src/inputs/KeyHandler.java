package inputs;

import creation_panel.Spot;
import main.GamePanel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private final GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gp.getGameState() == gp.CREATING) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_E:
                    gp.getCreateScreen().setHudEnabled(!gp.getCreateScreen().isHUDEnabled());
                    System.out.println(gp.getCreateScreen().isHUDEnabled());
                    break;
                case KeyEvent.VK_Q:
                    gp.getCreateScreen().setErasing(!gp.getCreateScreen().isErasing());
                    break;
                case KeyEvent.VK_SPACE:
                    gp.getCreateScreen().saveCourse();
                    break;
                case KeyEvent.VK_ESCAPE:
                    int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit without saving this course?");
                    if (answer == JOptionPane.YES_OPTION) {
                        gp.setGameState(gp.PLAYING);
                        gp.getPlayer().reset();
                        gp.getCreateScreen().resetSpots();
                    }
                    break;
            }

            Spot[][] spots = gp.getCreateScreen().getSpots();
            for (Spot[] spotRow : spots) {
                for (Spot s : spotRow) {
                    s.keyPressed(e);
                }
            }
        }

        if (gp.getGameState() == gp.PLAYING) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_R:
                    gp.getPlayer().reset();
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
