package main.course_menu;

import course.Tile;
import main.GamePanel;
import main.Hole;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MiniMap {

    public int x, y, width, height;
    private File tileData;
    private Tile[][] tiles;
    private GamePanel gp;

    private Hole hole;

    private BufferedImage image;

    private RoundRectangle2D clipArea;

    public MiniMap(int x, int y, int width, int height, File tileData, GamePanel gp) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tileData = tileData;
        this.gp = gp;

        initTileArray();

        clipArea = new RoundRectangle2D.Double(x, y, width, height, 20, 20);

        image = new BufferedImage(gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    public void initTileArray() {
        tiles = new Tile[20][28];

        try {
            BufferedReader in = new BufferedReader(new FileReader(tileData));

            String line = in.readLine();
            for (int row = 0; row < tiles.length; row++) {
                line = in.readLine();
                String[] data = line.split(" ");
                for (int col = 0; col < tiles[row].length; col++) {
                    tiles[row][col] = new Tile(col * gp.TILE_SIZE, row * gp.TILE_SIZE, Integer.parseInt(data[col]), gp);
                    if (tiles[row][col].getType() == 1) {
                        gp.getPlayer().setPosition(col * gp.TILE_SIZE, row * gp.TILE_SIZE);
                        tiles[row][col] = new Tile(col * gp.TILE_SIZE, row * gp.TILE_SIZE, 1, gp);
                    }
                    if (tiles[row][col].getType() == 9) {
                        hole = new Hole(col * gp.TILE_SIZE, row * gp.TILE_SIZE, gp);
                        tiles[row][col] = new Tile(col * gp.TILE_SIZE, row * gp.TILE_SIZE, 9, gp);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("File provided has invalid dimensions (32 x 24 required)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawImage() {
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        for (Tile[] tileRow : tiles) {
            for (Tile t : tileRow) {
                if (t != null) {
                    t.draw(g2);
                }
            }
        }
        hole.draw(g2);
    }

    public void draw(Graphics2D g2) {
        // TODO: BACKGROUND
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y, width, height, 20, 20);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(x, y, width, height, 20, 20);

        // TODO: IMAGE
        drawImage();
        g2.setClip(clipArea);
        g2.drawImage(image, x, y, width, height, null);
        g2.setClip(null);
    }

    public void setTileData(File tileData) {
        this.tileData = tileData;
        image = new BufferedImage(gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        initTileArray();
    }
}
