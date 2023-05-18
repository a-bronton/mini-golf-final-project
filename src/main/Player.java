package main;

import course.Tile;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.FileReader;

public class Player {

    private GamePanel gp;

    private double x, y;
    private int width, height;
    private double centerX, centerY;
    private Rectangle hitBox;

    private Point2D.Double jumpStretchCursor = new Point2D.Double();
    private Point2D.Double jumpStretchTarget = new Point2D.Double();

    private double jumpStretchRotation;
    private int jumpStretchDistance = 0;
    private int maxStretchDistance = 150;

    private boolean rolling = false;
    private double xVel, yVel;

    private Color jumpStretchColor = new Color(255, 255, 255);
    private boolean jumpStretching = false;
    private boolean controlEnabled = true;

    private int strokes;

    private int ORIGINAL_X, ORIGINAL_Y;

    // TODO: INDICATOR RING ALPHA
    private int indRingAlpha = 180;
    private int indRingTick;
    private int indRingSpeed = 2;

    public Player(GamePanel gp, int x, int y) {
        this.gp = gp;

        this.x = x;
        this.y = y;

        ORIGINAL_X = x;
        ORIGINAL_Y = y;

        width = gp.TILE_SIZE;
        height = gp.TILE_SIZE;

        hitBox = new Rectangle(x, y, width, height);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        ORIGINAL_X = x;
        ORIGINAL_Y = y;
        hitBox.x = x;
        hitBox.y = y;
    }

    public void reset() {
        xVel = 0;
        yVel = 0;

        x = ORIGINAL_X;
        y = ORIGINAL_Y;

        strokes = 0;

        controlEnabled = true;
    }

    public void update() {
        centerX = x + (width / 2.0);
        centerY = y + (height / 2.0);

        // TODO: JUMP STRETCH ROTATION
        double deltaX = jumpStretchCursor.x - centerX;
        double deltaY = jumpStretchCursor.y - centerY;
        jumpStretchRotation = Math.atan2(deltaY, deltaX);

        // TODO: TARGET
        jumpStretchTarget.x = (int) (centerX + deltaX);
        jumpStretchTarget.y = (int) (centerY + deltaY);

        // TODO: DISTANCES
        jumpStretchDistance = (int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        int power = jumpStretchDistance - maxStretchDistance;
        power = Math.min(power, 255);
        power = Math.max(power, 0);
        jumpStretchColor = new Color(255, 255 - power, 255 - power);

        // TODO: ROLL
        if (rolling) {
            x += xVel;
            y += yVel;
            xVel *= 0.97;
            yVel *= 0.97;

            if ((xVel < 0 && xVel > -0.05) || (xVel > 0 && xVel < 0.05)) {
                xVel = 0;
            }
            if ((yVel < 0 && yVel > -0.05) || (yVel > 0 && yVel < 0.05)) {
                yVel = 0;
            }

            if (xVel == 0 && yVel == 0) {
                rolling = false;
                controlEnabled = true;
            }
        }

        // TODO: COLLIDE SCREEN BOUNDS
        collideScreenBounds();

        hitBox.x = (int) x;
        hitBox.y = (int) y;

        collideTiles();
        collideHole();
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        // TODO: LIGHT IF NIGHT
        if (gp.getTime() == gp.NIGHT) {
            Color[] colors = {Color.YELLOW, new Color(0, 0, 0, 0)};
            int radius = 90;
            RadialGradientPaint paint = new RadialGradientPaint(new Point((int) centerX, (int) centerY), radius, new float[]{0.05f, 0.95f}, colors, MultipleGradientPaint.CycleMethod.REFLECT);
            g2.setPaint(paint);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2.fillOval((int) (centerX - 90), (int) (centerY - 90), 180, 180);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        // TODO: RING INDICATOR
        if (controlEnabled && !jumpStretching) {
            g2.setColor(new Color(255, 255, 255, indRingAlpha));
            g2.setStroke(new BasicStroke(8));
            g2.drawOval((int) (centerX - (25 * gp.SCALE)), (int) (centerY - (25 * gp.SCALE)), (int) (50 * gp.SCALE), (int) (50 * gp.SCALE));
            indRingTick++;
            if (indRingTick > indRingSpeed) {
                indRingAlpha -= 5;
                if (indRingAlpha <= 0) {
                    indRingAlpha = 180;
                }
                indRingTick = 0;
            }
        }

        // TODO: STRETCH
        if (jumpStretching) {
            g2.setColor(jumpStretchColor);
            g2.rotate(jumpStretchRotation, centerX, centerY);

            int[] polygonPointsX = {(int) centerX, (int) centerX, (int) (centerX + jumpStretchDistance)};
            int[] polygonPointsY = {(int) (centerY + (width / 2)), (int) (centerY - (height / 2)), (int) centerY};

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2.fillPolygon(polygonPointsX, polygonPointsY, polygonPointsX.length);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        g2.setColor(Color.WHITE);
        g2.fillOval((int) (centerX - (width / 2)), (int) (centerY - (height / 2)), width, height);
    }

    public void mouseDragged(MouseEvent e) {
        if (controlEnabled) {
            // TODO: UPDATES
            jumpStretching = true;
            jumpStretchCursor.x = e.getX();
            jumpStretchCursor.y = e.getY();
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (jumpStretching) {
            jumpStretching = false;
            hit();
            strokes++;

            jumpStretchDistance = 0;
        }
    }

    public void hit() {
        controlEnabled = false;
        // TODO: CALCULATE VELOCITIES AND ACCELERATION
        double deltaX = centerX - jumpStretchCursor.x;
        double deltaY = centerY - jumpStretchCursor.y;
        xVel = deltaX / 10f;
        yVel = deltaY / 10f;

        if (xVel > gp.TILE_SIZE / 2f) {
            xVel = gp.TILE_SIZE / 2f;
        }
        if (yVel > gp.TILE_SIZE / 2f) {
            xVel = gp.TILE_SIZE / 2f;
        }
        if (yVel < -gp.TILE_SIZE / 2f) {
            yVel = -gp.TILE_SIZE / 2f;
        }
        if (xVel < -gp.TILE_SIZE / 2f) {
            xVel = -gp.TILE_SIZE / 2f;
        }

        // TODO: INITIATE MOVEMENT
        rolling = true;
    }

    public void collideScreenBounds() {
        if (x > gp.getPreferredSize().getWidth() - width || x < 0) {
            xVel = -xVel;

            if (x < 0) {
                x = 0;
            } else {
                x = gp.getPreferredSize().getWidth() - width;
            }
        }
        if (y > gp.getPreferredSize().getHeight() - height || y < 0) {
            yVel = -yVel;

            if (y < 0) {
                y = 0;
            } else {
                y = gp.getPreferredSize().getHeight() - height;
            }
        }
    }

    public void collideHole() {
        Hole hole = gp.getCurrentCourse().getHole();
        // TODO: VERTICAL COLLISION
        hitBox.y += yVel;
        // CHECK IF ABOUT TO TOUCH TILE
        if (hole.getHitBox().intersects(hitBox)) {
            // MOVE BACK
            hitBox.y -= yVel;

            // GET HIT BOX.Y TO HIT TILE
            while (!hole.getHitBox().intersects(hitBox)) {
                hitBox.y += Math.signum(yVel);
            }

            // MOVE HIT BOX BACK
            hitBox.y -= Math.signum(yVel);
            // MOVE PLAYER TO NEW Y
            yVel = 0;
            xVel = 0;
            y = hitBox.y;

            gp.win();
        }

        // TODO: HORIZONTAL COLLISION
        hitBox.x += xVel;
        // CHECK IF ABOUT TO TOUCH TILE
        if (hole.getHitBox().intersects(hitBox)) {
            // MOVE BACK
            hitBox.x -= xVel;

            // GET HIT BOX.X TO HIT TILE
            while (!hole.getHitBox().intersects(hitBox)) {
                hitBox.x += Math.signum(xVel);
            }

            // MOVE HIT BOX BACK
            hitBox.x -= Math.signum(xVel);
            // MOVE PLAYER TO NEW X
            xVel = 0;
            yVel = 0;
            x = hitBox.x;

            gp.win();
        }
    }

    public void collideTiles() {
        // TODO: VERTICAL COLLISION
        hitBox.y += yVel;
        for (Tile[] tileRow : gp.getCurrentCourse().getTiles()) {
            for (Tile t : tileRow) {
                if (t.getType() != 2 && t.getType() != 3) {
                    continue;
                }
                // CHECK IF ABOUT TO TOUCH TILE
                if (t.getHitBox().intersects(hitBox)) {
                    // MOVE BACK
                    hitBox.y -= yVel;

                    // TODO: GET HIT BOX.Y TO HIT TILE
                    while (!t.getHitBox().intersects(hitBox)) {
                        hitBox.y += Math.signum(yVel);
                    }
                    hitBox.y -= Math.signum(yVel); // MOVE HIT BOX BACK


                    // MOVE PLAYER TO NEW Y
                    if (t.getType() == 2) {
                        yVel = -yVel;
                    } else if (t.getType() == 3) {
                        yVel = 0;
                        xVel = 0;
                        t.startHitParticles();
                    }
                    y = hitBox.y;
                }
            }
        }

        // TODO: HORIZONTAL COLLISION
        hitBox.x += xVel;
        for (Tile[] tileRow : gp.getCurrentCourse().getTiles()) {
            for (Tile t : tileRow) {
                if (t.getType() != 2 && t.getType() != 3) {
                    continue;
                }
                // CHECK IF ABOUT TO TOUCH TILE
                if (t.getHitBox().intersects(hitBox)) {
                    // MOVE BACK
                    hitBox.x -= xVel;

                    // TODO: GET HIT BOX.X TO HIT TILE
                    while (!t.getHitBox().intersects(hitBox)) {
                        hitBox.x += Math.signum(xVel);
                    }
                    hitBox.x -= Math.signum(xVel); // MOVE HIT BOX BACK


                    // MOVE PLAYER TO NEW X
                    if (t.getType() == 2) {
                        xVel = -xVel;
                    } else if (t.getType() == 3) {
                        xVel = 0;
                        yVel = 0;
                        t.startHitParticles();
                    }
                    x = hitBox.x;
                }
            }
        }

    }

    public int getStrokes() {
        return strokes;
    }

    public void disableControl() {
        controlEnabled = false;
    }

    public void enableControl() {
        controlEnabled = true;
    }

    public double getXVel() {
        return xVel;
    }

    public double getYVel() {
        return yVel;
    }
}
