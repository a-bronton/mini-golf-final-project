package creation_panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {

    private CreationScreen cp;

    private BufferedImage E_KEY_IMAGE, Q_KEY_IMAGE, SPACE_KEY_IMAGE;

    public HUD(CreationScreen cp) {
        this.cp = cp;

        setImages();
    }

    public void setImages() {
        try {
            E_KEY_IMAGE = ImageIO.read(getClass().getResourceAsStream("/keys/E_KEY.png"));
            Q_KEY_IMAGE = ImageIO.read(getClass().getResourceAsStream("/keys/Q_KEY.png"));
            SPACE_KEY_IMAGE = ImageIO.read(getClass().getResourceAsStream("/keys/SPACE_KEY.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        // TODO: DRAW INFO
        g2.setFont(new Font("HELVETICA", Font.BOLD, 20));

        // TOGGLE HUD
        g2.drawImage(E_KEY_IMAGE, 5, 5, 35, 35, null);
        g2.setColor(new Color(84, 110, 122));
        g2.drawString("Toggle HUD", 40, 30);
        g2.setColor(new Color(207, 216, 220));
        g2.drawString("Toggle HUD", 39, 29);

        // ERASE/DRAW
        String status = cp.isErasing() ? "Erasing" : "Drawing";
        g2.drawImage(Q_KEY_IMAGE, 165, 5, 35, 35, null);
        g2.setColor(new Color(84, 110, 122));
        g2.drawString(status, 200, 30);
        g2.setColor(new Color(207, 216, 220));
        g2.drawString(status, 199, 29);

        // SAVE
        g2.drawImage(SPACE_KEY_IMAGE, 285, -12, 70, 70, null);
        g2.setColor(new Color(84, 110, 122));
        g2.drawString("Save", 365, 30);
        g2.setColor(new Color(207, 216, 220));
        g2.drawString("Save", 364, 29);

        // BACK
//        g2.setColor(new Color(84, 110, 122));
//        g2.drawString("Back", 400, 30);
//        g2.setColor(new Color(207, 216, 220));
//        g2.drawString("Back", 399, 29);
    }
}
