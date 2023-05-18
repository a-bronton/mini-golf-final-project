package main.course_menu;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class CourseEntrance {

    public int x, y, width, height;
    private Rectangle hitBox;
    public String courseName;

    private BufferedImage background;

    private Color bgColorPressed = new Color(44, 91, 147);
    private Color bgColorHover = new Color(84, 140, 218);
    private Color bgColorNeutral = new Color(65, 119, 187);

    private Color bgColor = bgColorNeutral;
    private Color groundColor = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));

    private boolean selected;
    private boolean hover;

    public CourseEntrance(int x, int y, int width, int height, String courseName) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.courseName = courseName;

        hitBox = new Rectangle(x, y, width, height);

        background = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void update() {
        bgColor = bgColorNeutral;

        if (hover) {
            bgColor = bgColorHover;
        }

        if (selected) {
            bgColor = bgColorPressed;
        }

        hitBox.y = y;
    }

    public void draw(Graphics2D g2) {
        drawBackground();
        g2.drawImage(background, x, y, width, height, null);

        g2.setFont(new Font("Helvetica Bold", Font.BOLD, 16));
        int courseNameLength = g2.getFontMetrics(g2.getFont()).stringWidth(courseName);
        g2.setColor(Color.BLACK);
        g2.drawString(courseName, (x + width / 2) - (courseNameLength / 2), y + height / 2);
        g2.setColor(Color.WHITE);
        g2.drawString(courseName, (x + width / 2) - (courseNameLength / 2) - 1, y - 1 + height / 2);

        if (selected) {
            g2.setColor(Color.GREEN);
            g2.drawRoundRect(x, y, width, height, 20, 20);
        } else {
            g2.setColor(Color.WHITE);
            g2.drawRoundRect(x, y, width, height, 20, 20);
        }
    }

    public void drawBackground() {
        int x = 0;
        int y = 0;
        Graphics2D g2 = (Graphics2D) background.getGraphics();
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2.setColor(bgColor);
        g2.fillRoundRect(x, y, width, height, 20, 20);

        // TODO: GROUND
        RoundRectangle2D.Double backgroundArea = new RoundRectangle2D.Double(x, y, width, height, 20, 20);
        g2.setClip(backgroundArea);
        g2.setColor(groundColor);
        g2.fillRect(x, y + height - 15, width, 15);

        // TODO: HOLE
        Rectangle groundArea = new Rectangle(x, y + height - 15, width, 15);
        g2.setClip(groundArea);
        g2.setColor(Color.WHITE);
        g2.fillOval(x + 20, y + height - 25, 15, 15);
        g2.setClip(null);

        // TODO: FLAG
        g2.setColor(Color.BLACK);
        g2.drawLine(x + 27, y + height - 15, x + 27, y + height - 30);

        int[] xPts = {x + 26, x + 26, x + 35};
        int[] yPts = {y + height - 31, y + height - 25, y + height - 27};
        Polygon flag = new Polygon(xPts, yPts, xPts.length);
        g2.setColor(Color.WHITE);
        g2.fillPolygon(flag);
    }

    public void mouseMoved(MouseEvent e) {
        hover = false;
        if (hitBox.contains(e.getPoint())) {
            hover = true;
        }
    }

    public void mousePressed(MouseEvent e) {
        if (hitBox.contains(e.getPoint())) {
            selected = true;
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
}
