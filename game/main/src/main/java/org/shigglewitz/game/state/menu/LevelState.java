package org.shigglewitz.game.state.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.shigglewitz.game.Utils;
import org.shigglewitz.game.level.Stage;
import org.shigglewitz.game.level.Tile;
import org.shigglewitz.game.state.GameState;
import org.shigglewitz.game.state.GameStateManager;

public class LevelState extends GameState {

    private BufferedImage bg;
    private BufferedImage maze;
    private Color bgColor;
    private Stage stage;
    private int stageWidth;
    private int stageHeight;
    private int pixelBufferX;
    private int pixelBufferY;
    private int numRoomAttempts;
    private boolean drawBorders;

    public LevelState(GameStateManager gsm) {
        super(gsm);

        init();
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(bg, 0, 0, null);
        g.drawImage(maze, pixelBufferX, pixelBufferY, null);
    }

    @Override
    public void configure() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void init() {
        bgColor = Color.LIGHT_GRAY;
        stageWidth = 80;
        stageHeight = 60;
        pixelBufferX = 10;
        pixelBufferY = 10;
        drawBorders = false;
        numRoomAttempts = 30;

        bg = new BufferedImage(config.getWidth(), config.getHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = bg.createGraphics();
        Utils.normalizeGraphics(g);
        g.setColor(bgColor);
        g.fillRect(0, 0, config.getWidth(), config.getHeight());

        maze = new BufferedImage(config.getWidth() - pixelBufferX * 2, config
                .getHeight()
                - pixelBufferY * 2, BufferedImage.TYPE_4BYTE_ABGR);
        g = maze.createGraphics();
        Utils.normalizeGraphics(g);
        stage = new Stage(stageWidth, stageHeight, numRoomAttempts);
        Tile[][] tiles = stage.getTiles();
        double tileWidth = (config.getWidth() - (2.0 * pixelBufferX))
                / tiles.length;
        double tileHeight = (config.getHeight() - (2.0 * pixelBufferY))
                / tiles[0].length;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                g.setColor(config.getFillColor(tiles[i][j]));
                g.fillRect((int) (i * tileWidth), (int) (j * tileHeight),
                        (int) (((i + 1) * tileWidth) - (i * tileWidth) + 1),
                        (int) (((i + 1) * tileHeight) - (i * tileHeight) + 1));
                if (drawBorders) {
                    g.setColor(config.getBorderColor(tiles[i][j].getType()));
                    g.drawRect((int) (i * tileWidth), (int) (j * tileHeight),
                            (int) (((i + 1) * tileWidth) - (i * tileWidth)),
                            (int) (((i + 1) * tileHeight) - (i * tileHeight)));
                }
            }
        }
    }

}
