package org.shigglewitz.game.level.tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.shigglewitz.game.Utils;
import org.shigglewitz.game.config.Config;
import org.shigglewitz.game.config.Configurable;
import org.shigglewitz.game.level.tilemap.Tile.Type;

public class TileMap implements Configurable {
    private Config config = Config.getConfig();
    private int tileSize;
    private int numCols;
    private int numRows;
    private int width;
    private int height;
    private int numRowsToDraw;
    private int numColsToDraw;
    private int xmin;
    private int xmax;
    private int ymin;
    private int ymax;
    private int colOffset;
    private int rowOffset;
    private double x;
    private double y;
    private double tween;
    private Tile[][] map;
    private BufferedImage image;

    private boolean drawFloors;
    private boolean drawBorders;

    public TileMap(int tileSize, int numCols, int numRows) {
        this.tileSize = tileSize;
        map = new Tile[numCols][numRows];
        this.numCols = numCols;
        this.numRows = numRows;

        this.width = numCols * tileSize;
        this.height = numRows * tileSize;

        drawFloors = false;
        drawBorders = false;

        x = 0.0;
        y = 0.0;
        tween = 1;

        configure();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setTile(int x, int y, Tile tile) {
        map[x][y] = tile;
    }

    public int getRegion(int x, int y) {
        return map[x][y].getRegion();
    }

    public void setRegion(int x, int y, int region) {
        map[x][y].setRegion(region);
    }

    public void changeType(int x, int y, Tile.Type type) {
        map[x][y].setType(type);
    }

    public Type getType(int x, int y) {
        return map[x][y].getType();
    }

    public void debugMap() {
        saveImage(drawFullScreen());
    }

    public void fullScreen() {
        image = drawFullScreen();
        saveImage(image);
    }

    protected BufferedImage drawFullScreen() {
        BufferedImage image = new BufferedImage(config.getWidth(), config
                .getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = image.createGraphics();
        Utils.normalizeGraphics(g);
        double tileWidth = ((double) config.getWidth()) / numCols;
        double tileHeight = ((double) config.getHeight()) / numRows;
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                if (drawFloors || map[i][j].getType() != Tile.Type.FLOOR) {
                    g.setColor(config.getFillColor(map[i][j]));
                    g.fillRect(
                            (int) (i * tileWidth),
                            (int) (j * tileHeight),
                            (int) (((i + 1) * tileWidth) - (i * tileWidth) + 1),
                            (int) (((i + 1) * tileHeight) - (i * tileHeight) + 1));
                }
                if (drawBorders) {
                    g.setColor(config.getBorderColor(map[i][j].getType()));
                    g.drawRect((int) (i * tileWidth), (int) (j * tileHeight),
                            (int) (((i + 1) * tileWidth) - (i * tileWidth)),
                            (int) (((i + 1) * tileHeight) - (i * tileHeight)));
                }
            }
        }

        g.dispose();

        return image;
    }

    protected void saveImage(BufferedImage image) {
        try {
            File outputfile = new File("src/main/resources/" + "test" + "."
                    + "png");

            ImageIO.write(image, "png", outputfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPosition(double x, double y) {
        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        fixBounds();

        colOffset = (int) this.x / tileSize;
        rowOffset = (int) this.y / tileSize;
    }

    private void fixBounds() {
        if (x < xmin) {
            x = xmin;
        }
        if (x > xmax) {
            x = xmax;
        }
        if (y < ymin) {
            y = ymin;
        }
        if (y > ymax) {
            y = ymax;
        }
    }

    public void draw(Graphics2D g) {
        if (image == null) {
            for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
                if (row >= numRows) {
                    break;
                }
                for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
                    if (col >= numCols) {
                        break;
                    }

                    if (drawFloors
                            || map[col][row].getType() != Tile.Type.FLOOR) {
                        g.setColor(config.getFillColor(map[col][row]));
                        g.fillRect(col * tileSize - (int) x, row * tileSize
                                - (int) y, tileSize, tileSize);
                    }
                }
            }
        } else {
            g.drawImage(image, 0, 0, null);
        }
    }

    @Override
    public void configure() {
        xmin = 0;
        xmax = width - config.getWidth();
        ymin = 0;
        ymax = height - config.getHeight();

        numRowsToDraw = (int) (Math
                .ceil((double) config.getHeight() / tileSize) + 2);
        numColsToDraw = (int) (Math.ceil((double) config.getWidth() / tileSize) + 2);
    }
}
