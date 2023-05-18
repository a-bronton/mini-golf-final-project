package creation_panel;

import course.Tile;
import creation_panel.tool_panel.ToolPanel;
import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class CreationScreen {

    public final float SCALE;
    public final int TILE_SIZE;
    public final int SCREEN_WIDTH;
    public final int SCREEN_HEIGHT;

    // TODO: CREATING
    private Spot[][] spots;
    private boolean erasing;

    // TODO: HUD
    private HUD hud;
    private boolean hudEnabled = true;

    // TODO: MENU
    private EditorMenu editorMenu;
    private boolean menuEnabled = false;

    private GamePanel gp;
    private ToolPanel toolPanel;

    // TODO:
    private Tile selectedTile;

    public CreationScreen(GamePanel gp) {
        this.gp = gp;

        SCALE = gp.SCALE;
        //TILE_SIZE = (int) (38 * SCALE);
        TILE_SIZE = (int) (gp.TILE_SIZE / 1.25);
        SCREEN_WIDTH = gp.SCREEN_WIDTH;
        SCREEN_HEIGHT = gp.SCREEN_HEIGHT;

        initClasses();

        initSpots();
    }

    public void initClasses() {
        hud = new HUD(this);
        editorMenu = new EditorMenu(gp.TILE_SIZE * 2, gp.TILE_SIZE * 2, gp.TILE_SIZE * 6, gp.TILE_SIZE * 12, this);

        toolPanel = new ToolPanel(TILE_SIZE, TILE_SIZE * 2, TILE_SIZE * 6, TILE_SIZE * 20, this);
    }

    public void update() {
        if (menuEnabled) {
            editorMenu.update();
        }

        toolPanel.update();
    }

    public void draw(Graphics2D g2) {
        // TODO: BG
        g2.setColor(new Color(37, 131, 33));
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

        if (menuEnabled) {
            editorMenu.draw(g2);
        }

        if (!menuEnabled) {
            for (Spot[] spotRow : spots) {
                for (Spot s : spotRow) {
                    s.draw(g2);
                }
            }
        }

        toolPanel.draw(g2);

        if (hudEnabled) {
            hud.draw(g2);
        }
    }

    public void mouseMoved(MouseEvent e) {
        toolPanel.mouseMoved(e);

        for (Spot[] spotRow : spots) {
            for (Spot s : spotRow) {
                s.mouseMoved(e);
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        toolPanel.mousePressed(e);

        for (Spot[] spotRow : spots) {
            for (Spot s : spotRow) {
                s.mousePressed(e);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        toolPanel.mouseReleased(e);
    }

    public void mouseDragged(MouseEvent e) {
        if (!toolPanel.isDragDropping()) {
            for (Spot[] spotRow : spots) {
                for (Spot s : spotRow) {
                    s.mouseDragged(e);
                    s.mousePressed(e);
                }
            }
        }
        toolPanel.mouseDragged(e);
    }

    public void initSpots() {
        spots = new Spot[28][20];
        int xOffs = gp.SCREEN_WIDTH - TILE_SIZE * 28;
        int yOffs = TILE_SIZE * 2;

        for (int i = 0; i < spots.length; i++) {
            for (int j = 0; j < spots[i].length; j++) {
                spots[i][j] = new Spot(xOffs + i * TILE_SIZE, yOffs + j * TILE_SIZE, this);
            }
        }
    }

    public void saveCourse() {
        boolean hasHole = false;
        boolean hasPlayer = false;
        for (Spot[] spotRow : spots) {
            for (Spot s : spotRow) {
                if (s.getTileType() == 9) {
                    hasHole = true;
                }
                if (s.getTileType() == 1) {
                    hasPlayer = true;
                }
            }
        }

        if (!hasPlayer) {
            JOptionPane.showMessageDialog(null, "You must place the player");
            return;
        }

        if (!hasHole) {
            JOptionPane.showMessageDialog(null, "You must place the hole");
            return;
        }

        // TODO: CREATE FILE
        String courseName = JOptionPane.showInputDialog(null, "Enter a name for your course");
        if (courseName == null) {
            return;
        }
        File courseFile = new File("dataFiles/courses/" + courseName + ".txt");
        // TODO: SAVE DATA TO FILE
        try {
            if (courseFile.createNewFile()) {
                PrintWriter out = new PrintWriter(new FileWriter(courseFile));
                out.println("[HIGHSCORE = 0]");

                for (int i = 0; i < spots[0].length; i++) {
                    for (int j = 0; j < spots.length; j++) {
                        int type = spots[j][i].getTileType();
                        if (j >= spots.length) {
                            out.print(type);
                        } else {
                            out.print(type + " ");
                        }
                    }
                    if (i != spots[0].length - 1) {
                        out.println();
                    }
                }

                out.flush();
                out.close();

                gp.setGameState(gp.PLAYING);
                gp.getCourseMenu().initCourseList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Spot[][] getSpots() {
        return spots;
    }

    public void resetSpots() {
        initSpots();
    }

    public void setHudEnabled(boolean hudEnabled) {
        this.hudEnabled = hudEnabled;
    }

    public boolean isHUDEnabled() {
        return hudEnabled;
    }

    public boolean isErasing() {
        return erasing;
    }

    public void setErasing(boolean erasing) {
        this.erasing = erasing;
    }

    public boolean isMenuEnabled() {
        return menuEnabled;
    }

    public EditorMenu getEditorMenu() {
        return editorMenu;
    }

    public GamePanel getGamePanel() {
        return gp;
    }
}
