package ui;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class Button {

    private int x, y;
    private int width, height;

    private ButtonListener buttonListener;

    private BufferedImage spriteSheet;
    private BufferedImage[] images = new BufferedImage[3];
    private int imageIndex;

    private Rectangle hitBox;

    private boolean hover, pressed;

    private GamePanel gp;

    public Button(int x, int y, int width, int height, BufferedImage spriteSheet, GamePanel gp) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        hitBox = new Rectangle(x, y, width, height);

        this.spriteSheet = spriteSheet;
        initImages();
    }

    public void initImages() {
        try {
            for (int i = 0; i < images.length; i++) {
                images[i] = spriteSheet.getSubimage(i * 32, 0, 32, 32);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        imageIndex = 0;

        if (hover) {
            imageIndex = 1;
        }

        if (pressed) {
            imageIndex = 2;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(images[imageIndex], x, y, width, height, null);
    }

    public void addButtonListener(ButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public void mouseMoved(MouseEvent e) {
        if (hitBox.contains(e.getPoint())) {
            hover = true;
        } else {
            hover = false;
        }
    }

    public void mousePressed(MouseEvent e) {
        if (hitBox.contains(e.getPoint())) {
            pressed = true;
        } else {
            pressed = false;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (hitBox.contains(e.getPoint())) {
            if (hover) {
                if (buttonListener != null) {
                    buttonListener.click();
                }
            }
        }

        pressed = false;
    }
}
