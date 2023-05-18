package ui;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class UI {

    private GamePanel gp;

    private ArrayList<Button> buttons = new ArrayList<>();

    private BufferedImage strokesIcon;

    public UI(GamePanel gp) {
        this.gp = gp;

        initButtons();
        initImages();
    }

    private void initImages() {
        try {
            strokesIcon = ImageIO.read(getClass().getResourceAsStream("/icons/strokes_icon.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        for (Button b : buttons) {
            b.update();
        }
    }

    public void draw(Graphics2D g2) {
        for (Button b : buttons) {
            b.draw(g2);
        }

        // TODO: DRAW STROKES
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRoundRect(-10, 10, 85, 45, 20, 20);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetica Bold", Font.BOLD, 25));
        g2.drawImage(strokesIcon, 5, 15, 35, 35, null);
        g2.drawString(String.valueOf(gp.getPlayer().getStrokes()), 45, 40);
    }

    public void initButtons() {
        try {
            BufferedImage editorButtonImage = ImageIO.read(getClass().getResourceAsStream("/buttons/editor_button_sheet.png"));
            Button editorButton = new Button((int) gp.getPreferredSize().getWidth() - gp.TILE_SIZE * 2, 5, (int) (gp.TILE_SIZE * 1.5), (int) (gp.TILE_SIZE * 1.5), editorButtonImage, gp);
            editorButton.addButtonListener(new ButtonListener() {
                @Override
                public void click() {
                    gp.setGameState(gp.CREATING);
                }
            });
            buttons.add(editorButton);

            BufferedImage courseSelectionButtonImage = ImageIO.read(getClass().getResourceAsStream("/buttons/course_selection_button_sheet.png"));
            Button courseSelectButton = new Button((int) gp.getPreferredSize().getWidth() - gp.TILE_SIZE * 4, 5, (int) (gp.TILE_SIZE * 1.5), (int) (gp.TILE_SIZE * 1.5), courseSelectionButtonImage, gp);
            courseSelectButton.addButtonListener(new ButtonListener() {
                @Override
                public void click() {
                    gp.setCourseSelecting(true);
                    gp.getPlayer().disableControl();
                }
            });
            buttons.add(courseSelectButton);

            BufferedImage lightBulbImage = ImageIO.read(getClass().getResourceAsStream("/buttons/light_bulb.png"));
            Button dayNightButton = new Button((int) gp.getPreferredSize().getWidth() - (gp.TILE_SIZE * 6), 5, (int) (gp.TILE_SIZE * 1.5), (int) (gp.TILE_SIZE * 1.5), lightBulbImage, gp);
            dayNightButton.addButtonListener(new ButtonListener() {
                @Override
                public void click() {
                    gp.switchTime();
                }
            });
            buttons.add(dayNightButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }
}
