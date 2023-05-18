package creation_panel;

import course.Course;
import main.course_menu.CourseEntrance;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

public class EditorMenu {

    public int x, y, width, height;

    public CreationScreen cs;

    private ArrayList<CourseEntrance> courseEntrances = new ArrayList<>();

    public EditorMenu(int x, int y, int width, int height, CreationScreen cs) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cs = cs;

        initEntrances();
    }

    public void initEntrances() {
        // TODO: POPULATE DROP DOWN
        int x = this.x + cs.TILE_SIZE / 2;
        int y = this.y + cs.TILE_SIZE / 2;
        int width = this.width - cs.TILE_SIZE;
        int height = 40;
        File dir = new File("dataFiles/courses");
        for (File f : dir.listFiles()) {
            String fileName = f.toString();
            String beginning = "dataFiles/courses/";
            String courseName = fileName.substring(beginning.length(), fileName.length() - 4);
            courseEntrances.add(new CourseEntrance(x, y, width, height, courseName));
            y += height + 10;
        }
    }

    public void update() {
        for (CourseEntrance ce : courseEntrances) {
            ce.update();
        }
    }

    public void draw(Graphics2D g2) {
        // TODO: BACKGROUND
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y, width, height, 20, 20);

        g2.setColor(Color.WHITE);
        g2.drawRoundRect(x, y, width, height, 20, 20);

        for (CourseEntrance ce : courseEntrances) {
            ce.draw(g2);
        }
    }

    public void mousePressed(MouseEvent e) {
        for (CourseEntrance ce : courseEntrances) {
            ce.setSelected(false);
            ce.mousePressed(e);
        }

        for (CourseEntrance ce : courseEntrances) {
            ce.mousePressed(e);
        }
    }

    public void mouseMoved(MouseEvent e) {
        for (CourseEntrance ce : courseEntrances) {
            ce.mouseMoved(e);
        }
    }
}
