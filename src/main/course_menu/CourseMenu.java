package main.course_menu;

import course.Course;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import main.GamePanel;
import ui.Button;
import ui.ButtonListener;

public class CourseMenu {

    // TODO: BUTTONS
    private ArrayList<Button> buttons = new ArrayList<>();
    private Button confirmButton;

    // TODO: SCROLLING LIST
    private ArrayList<CourseEntrance> courseEntrances = new ArrayList<>();
    private Rectangle scrollBox;
    private double scrollSpeed;
    private Rectangle scrollBar;
    private boolean scrollBarEnabled = false;

    private int x, y, width, height;

    private GamePanel gp;
    private MiniMap miniMap;

    public CourseMenu(GamePanel gp, int x, int y, int width, int height) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        scrollBox = new Rectangle(x, y, width, height);
        scrollBar = new Rectangle(x + width - 12, y + 20, 8, 60);

        miniMap = new MiniMap(gp.TILE_SIZE * 12, gp.TILE_SIZE * 2, (int) (gp.SCREEN_WIDTH / 2.5), (int) (gp.SCREEN_HEIGHT / 2.5), new File("dataFiles/courses/myCourse.txt"), gp);

        initCourseList();
        initConfirmButton();
    }

    public void initCourseList() {
        // TODO: POPULATE DROP DOWN
        int x = this.x + gp.TILE_SIZE / 2;
        int y = this.y + gp.TILE_SIZE / 2;
        int width = this.width - gp.TILE_SIZE;
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

    public void initConfirmButton() {
        try {
            BufferedImage playButtonImage = ImageIO.read(getClass().getResourceAsStream("/buttons/play_again_button_sheet.png"));
            confirmButton = new Button(x + width - (gp.TILE_SIZE * 2), y + height - (gp.TILE_SIZE * 2), (int) (gp.TILE_SIZE * 1.5), (int) (gp.TILE_SIZE * 1.5), playButtonImage, gp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        confirmButton.addButtonListener(new ButtonListener() {
            @Override
            public void click() {
                Course course = getCourseFromName(getSelectedCourse());
                gp.switchCourse(course);
                gp.setCourseSelecting(false);
                gp.setWon(false);

                gp.getPlayer().reset();
                gp.getPlayer().enableControl();

                System.out.println("Now playing: " + getSelectedCourse());
            }
        });

        buttons.add(confirmButton);
    }

    public void update() {
        for (Button b : buttons) {
            b.update();
        }

        // TODO: UPDATE COURSE ENTRANCES AND SCROLL SPEED
        for (CourseEntrance ce : courseEntrances) {
            ce.update();
            ce.setY((int) (ce.getY() + scrollSpeed));
        }
        scrollBar.y += -scrollSpeed / 2.0;
        scrollSpeed *= 0.8;
        if (scrollSpeed < 0 && scrollSpeed > -0.75) {
            scrollSpeed = 0;
        }
        if (scrollSpeed > 0 && scrollSpeed < 0.75) {
            scrollSpeed = 0;
        }

        Course selectedCourse = getCourseFromName(getSelectedCourse());
        miniMap.setTileData(selectedCourse.getTileData());
    }

    public void draw(Graphics2D g2) {
        // TODO: BACKGROUND
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y, width, height, 20, 20);

        // TODO: BUTTONS
        for (Button b : buttons) {
            b.draw(g2);
        }

        g2.setClip(scrollBox);
        for (CourseEntrance ce : courseEntrances) {
            ce.draw(g2);
        }
        g2.setClip(null);

        g2.setColor(Color.WHITE);
        g2.drawRoundRect(x, y, width, height, 20, 20);

        // TODO: VERTICAL SCROLL BAR
        if (scrollBarEnabled) {
            g2.setColor(new Color(255, 255, 255, 100));
            g2.fillRoundRect(scrollBar.x, scrollBar.y, scrollBar.width, scrollBar.height, 10, 10);
        }

        // TODO: DRAW
        if (miniMap != null) {
            miniMap.draw(g2);
        }

        drawInfoBar(g2);
    }

    public void drawInfoBar(Graphics2D g2) {
        // TODO: INFO BAR
        int x = miniMap.x;
        int y = miniMap.y + miniMap.height + (gp.TILE_SIZE / 2);
        int width = miniMap.width;
        int height = gp.TILE_SIZE * 2;

        // ROUND RECTANGLE
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y, width, height, 20, 20);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(x, y, width, height, 20, 20);

        // COURSE NAME
        String courseName = getSelectedCourse();
        int stringLength = g2.getFontMetrics().stringWidth(courseName);
        g2.drawString(courseName, (x + width / 2) - (stringLength / 2), y + 20);

        // HIGH SCORE
        String highScore = "High Score: " + getCourseFromName(courseName).getHighScore();
        String number = getCourseFromName(courseName).getHighScore() == 1 ? "Stroke" : "Strokes";
        highScore += " " + number;
        stringLength = g2.getFontMetrics().stringWidth(highScore);
        g2.drawString(highScore, (x + width / 2) - (stringLength / 2), y + 50);
    }

    public void mouseMoved(MouseEvent e) {
        for (Button b : buttons) {
            b.mouseMoved(e);
        }

        for (CourseEntrance ce : courseEntrances) {
            ce.mouseMoved(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        for (Button b : buttons) {
            b.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (Button b : buttons) {
            b.mouseReleased(e);
        }

        for (CourseEntrance ce : courseEntrances) {
            ce.setSelected(false);
            ce.mousePressed(e);
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (courseEntrances.get(courseEntrances.size() - 1).getY() > y + height) {
            if (scrollBox.contains(e.getPoint())) {
                if (e.getWheelRotation() < 0) {
                    scrollSpeed += 3;
                } else {
                    scrollSpeed -= 3;
                }
            }
            scrollBarEnabled = true;
        }
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public String getSelectedCourse() {
        for (CourseEntrance ce : courseEntrances) {
            if (ce.isSelected()) {
                return ce.getCourseName();
            }
        }
        return courseEntrances.get(0).getCourseName();
    }

    public ArrayList<CourseEntrance> getCourseEntrances() {
        return courseEntrances;
    }

    public int getWidth() {
        return width;
    }

    public Course getCourseFromName(String name) {
        return new Course(new File("dataFiles/courses/" + name + ".txt"), gp);
    }
}
