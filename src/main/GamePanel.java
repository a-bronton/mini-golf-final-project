package main;

import course.Course;
import course.Tile;
import creation_panel.CreationScreen;
import inputs.KeyHandler;
import inputs.MouseHandler;
import main.course_menu.CourseMenu;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GamePanel extends JPanel {

    private Player player;

    // TODO: SCREEN ATTRIBUTES
    public final float SCALE = 0.7f;
    public final int TILE_SIZE = (int) (48 * SCALE);
    public final int SCREEN_WIDTH = TILE_SIZE * 28;
    public final int SCREEN_HEIGHT = TILE_SIZE * 20;

    // TODO: UTILS
    private MouseHandler mHandler;
    private KeyHandler keyH;
    private UI ui;

    // TODO: COURSES
    private Course currentCourse;
    private CourseMenu courseMenu;
    private boolean courseSelecting;

    // TODO: CREATION
    private CreationScreen createScreen = new CreationScreen(this);

    // TODO: GAME STATES
    private int gameState;
    public final int PLAYING = 0;
    public final int CREATING = 2;

    private WinMenu winMenu;
    private boolean won = false;

    // TODO: TIME
    private int time;
    public final int DAY = 0;
    public final int NIGHT = 1;

    public GamePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(new Color(28, 94, 25));

        initClasses();

        addMouseListener(mHandler);
        addMouseMotionListener(mHandler);
        addMouseWheelListener(mHandler);
        addKeyListener(keyH);

        setFocusable(true);
        requestFocus();
    }

    public void initClasses() {
        player = new Player(this, (int) (getPreferredSize().getWidth() - 30) / 2, (int) (getPreferredSize().getHeight() - 30) / 2);

        ui = new UI(this);

        mHandler = new MouseHandler(this);
        keyH = new KeyHandler(this);

        courseMenu = new CourseMenu(this, TILE_SIZE * 2, TILE_SIZE * 2, TILE_SIZE * 8, TILE_SIZE * 16);
        winMenu = new WinMenu(this, TILE_SIZE * 12, TILE_SIZE * 13, (int) (SCREEN_WIDTH / 2.5), TILE_SIZE * 5);

        File coursesDir = new File("dataFiles/courses");
        currentCourse = new Course(coursesDir.listFiles()[coursesDir.listFiles().length - 1], this);
    }

    public void update() {
        if (gameState == PLAYING) {
            player.update();
            ui.update();
        }

        if (gameState == CREATING) {
            createScreen.update();
        }

        if (courseSelecting) {
            courseMenu.update();
        }
    }

    public void draw(Graphics2D g2) {
        if (gameState == PLAYING) {
            for (Tile[] tileRow : currentCourse.getTiles()) {
                for (Tile t : tileRow) {
                    t.draw(g2);
                }
            }
            currentCourse.getHole().draw(g2);

            if (time == DAY) {
                player.draw(g2);
            }
        }

        // TODO: TIME
        if (time == NIGHT) {
            g2.setColor(new Color(0, 0, 0, 200));
            g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            player.draw(g2);
        }

        if (gameState == PLAYING) {
            ui.draw(g2);
        }


        if (gameState == CREATING) {
            createScreen.draw(g2);
        }

        if (courseSelecting) {
            courseMenu.draw(g2);
        }

        if (won) {
            winMenu.draw(g2);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        update();
        draw(g2);

        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        repaint();
    }

    public void win() {
        won = true;
        setCourseSelecting(true);
        player.disableControl();

        if (player.getStrokes() < currentCourse.getHighScore() || currentCourse.getHighScore() == 0) {
            currentCourse.setHighScore(player.getStrokes());
        }
    }

    public void switchCourse(Course course) {
        currentCourse = course;
    }

    public Player getPlayer() {
        return player;
    }

    public Course getCurrentCourse() {
        return currentCourse;
    }

    public CreationScreen getCreateScreen() {
        return createScreen;
    }

    public UI getUi() {
        return ui;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int getGameState() {
        return gameState;
    }

    public boolean isCourseSelecting() {
        return courseSelecting;
    }

    public void setCourseSelecting(boolean courseSelecting) {
        if (courseSelecting) {
            courseMenu = new CourseMenu(this, TILE_SIZE * 2, TILE_SIZE * 2, TILE_SIZE * 8, TILE_SIZE * 16);
        }
        this.courseSelecting = courseSelecting;
    }

    public CourseMenu getCourseMenu() {
        return courseMenu;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public void switchTime() {
        if (time == DAY) {
            time = NIGHT;
        } else {
            time = DAY;
        }
    }

    public int getTime() {
        return time;
    }
}
