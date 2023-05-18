package course;

import main.GamePanel;
import main.Hole;

import java.io.*;

public class Course {

    private File tileData;

    private Tile[][] tiles;

    private GamePanel gp;

    private Hole hole;

    private int highScore;

    public Course(File tileData, GamePanel gp) {
        this.tileData = tileData;
        this.gp = gp;

        tiles = new Tile[20][28];
        loadTiles();
    }

    public void loadTiles() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(tileData));

            String line = in.readLine();
            highScore = Integer.parseInt(line.substring(line.length() - 2, line.length() - 1));
            for (int row = 0; row < tiles.length; row++) {
                line = in.readLine();
                String[] data = line.split(" ");
                for (int col = 0; col < tiles[row].length; col++) {
                    tiles[row][col] = new Tile(col * gp.TILE_SIZE, row * gp.TILE_SIZE, Integer.parseInt(data[col]), gp);
                    if (tiles[row][col].getType() == 1) {
                        gp.getPlayer().setPosition(col * gp.TILE_SIZE, row * gp.TILE_SIZE);
                        tiles[row][col] = new Tile(col * gp.TILE_SIZE, row * gp.TILE_SIZE, 0, gp);
                    }
                    if (tiles[row][col].getType() == 9) {
                        hole = new Hole(col * gp.TILE_SIZE, row * gp.TILE_SIZE, gp);
                        tiles[row][col] = new Tile(col * gp.TILE_SIZE, row * gp.TILE_SIZE, 9, gp);
                        hole = new Hole(col * gp.TILE_SIZE, row * gp.TILE_SIZE, gp);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("File provided has invalid dimensions (32 x 24 required)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveTiles() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(tileData));
            out.println("[HIGHSCORE = " + highScore + "]");
            for (int row = 0; row < tiles.length; row++) {
                for (int col = 0; col < tiles[row].length; col++) {
                    if (tiles[row][col].getType() == 9) {
                        System.out.println("Found hole");
                    }
                    if (tiles[row][col].getType() == 1) {
                        System.out.println("Found player");
                    }
                    out.print(tiles[row][col].getType() + " ");
                }
                out.println();
            }
            out.close();
            out.flush();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("File provided has invalid dimensions (32 x 24 required)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Hole getHole() {
        return hole;
    }

    public File getTileData() {
        return tileData;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
        saveTiles();
    }
}
