package org.shigglewitz.game.level;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import org.shigglewitz.game.Direction;
import org.shigglewitz.game.Utils;
import org.shigglewitz.game.level.Tile.Type;

public class Stage {
    private static final int STARTING_REGION_NUMBER = 1;

    private int width;
    private int height;
    private int numRoomAttempts;
    private int windingPercent;
    private int extraConnectorChance;
    private int currentRegion;
    private Random random;
    private Tile[][] tiles;
    private List<Rectangle> rooms;

    public Stage(int width, int height, int numRoomAttempts) {
        if (width % 2 == 0) {
            width++;
        }
        if (height % 2 == 0) {
            height++;
        }

        this.width = width;
        this.height = height;
        this.numRoomAttempts = numRoomAttempts;
        windingPercent = 50;
        extraConnectorChance = 20;
        currentRegion = STARTING_REGION_NUMBER - 1;
        random = new Random();

        init();
    }

    protected void init() {
        tiles = new Tile[width][height];
        rooms = new ArrayList<>();

        fill(Type.WALL);
        addRooms();
        excavateRooms();
        generateMaze();
        connectRegions();
        removeDeadEnds();
    }

    protected void fill(Type type) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(type);
            }
        }
    }

    protected void addRooms() {
        for (int i = 0; i < numRoomAttempts; i++) {
            Rectangle room = generateRoom();
            addRoom(room);
        }
    }

    protected Rectangle generateRoom() {
        int size = (random.nextInt(3) + 1) * 2 + 1;
        int rectangularity = random.nextInt(1 + size / 2) * 2;
        int width = size;
        int height = size;

        if (random.nextBoolean()) {
            width += rectangularity;
        } else {
            height += rectangularity;
        }

        int x = random.nextInt((this.width - width) / 2) * 2 + 1;
        int y = random.nextInt((this.height - height) / 2) * 2 + 1;

        return new Rectangle(x, y, width, height);
    }

    protected void addRoom(Rectangle room) {
        room.grow(1, 1);
        boolean overlaps = false;
        for (Rectangle r : rooms) {
            if (r.intersects(room)) {
                overlaps = true;
                break;
            }
        }

        if (overlaps) {
            return;
        }

        room.grow(-1, -1);
        rooms.add(room);
    }

    protected void excavateRooms() {
        for (Rectangle r : rooms) {
            startRegion();
            excavate(r);
        }
    }

    protected void excavate(Rectangle room) {
        for (int i = 0; i < room.width; i++) {
            for (int j = 0; j < room.height; j++) {
                excavate(i + room.x, j + room.y);
            }
        }
    }

    protected void excavate(int x, int y) {
        tiles[x][y].setType(Type.FLOOR);
        tiles[x][y].setRegion(currentRegion);
    }

    protected void fill(int x, int y) {
        tiles[x][y].setType(Type.WALL);
    }

    protected void generateMaze() {
        for (int i = 1; i < width - 1; i += 2) {
            for (int j = 1; j < height - 1; j += 2) {
                if (tiles[i][j].getType() == Type.WALL) {
                    growMaze(i, j);
                }
            }
        }
    }

    protected void growMaze(int x, int y) {
        List<Point> points = new ArrayList<>();

        Point p = new Point(x, y);
        Direction lastDir = null;
        points.add(p);
        startRegion();
        excavate(p.x, p.y);

        while (!points.isEmpty()) {
            Point cell = points.get(points.size() - 1);
            List<Direction> unmadeDirections = new ArrayList<>();

            for (Direction d : Direction.CARDINAL) {
                if (canExcavate(cell, d)) {
                    unmadeDirections.add(d);
                }
            }

            if (!unmadeDirections.isEmpty()) {
                Direction d = null;

                if (unmadeDirections.contains(lastDir)
                        && random.nextInt(100) > windingPercent) {
                    d = lastDir;
                } else {
                    d = unmadeDirections.get(random.nextInt(unmadeDirections
                            .size()));
                }

                Point toExcavate = Utils.shiftPoint(cell, d, 1);
                excavate(toExcavate.x, toExcavate.y);
                toExcavate = Utils.shiftPoint(cell, d, 2);
                excavate(toExcavate.x, toExcavate.y);
                points.add(toExcavate);
                lastDir = d;
            } else {
                points.remove(points.size() - 1);
                lastDir = null;
            }
        }
    }

    protected boolean canExcavate(Point p, Direction d) {
        Point shift = Utils.shiftPoint(p, d, 3);
        if (shift.x < 0 || shift.x > width || shift.y < 0 || shift.y > height) {
            return false;
        }

        shift = Utils.shiftPoint(p, d, 2);
        return tiles[shift.x][shift.y].getType() == Type.WALL;
    }

    protected void connectRegions() {
        Map<Point, Set<Integer>> connectorRegions = new HashMap<>();
        findConnectors(connectorRegions);
        List<Point> connectors = new ArrayList<>();
        connectors.addAll(connectorRegions.keySet());

        Map<Integer, Integer> mergedRegions = new HashMap<>();
        Set<Integer> openRegions = new HashSet<>();
        for (int i = STARTING_REGION_NUMBER; i <= currentRegion; i++) {
            mergedRegions.put(i, i);
            openRegions.add(i);
        }

        while (openRegions.size() > 1) {
            Point connector = connectors.get(random.nextInt(connectors.size()));
            addJunction(connector);

            List<Integer> regions = new ArrayList<>();
            for (int i : connectorRegions.get(connector)) {
                regions.add(mergedRegions.get(i));
            }
            int destination = regions.get(0);
            List<Integer> sources = new ArrayList<>();
            sources.addAll(regions);
            sources.remove(0);

            for (int i = STARTING_REGION_NUMBER; i <= currentRegion; i++) {
                if (sources.contains(mergedRegions.get(i))) {
                    mergedRegions.put(i, destination);
                }
            }

            openRegions.removeAll(sources);
            for (int i = 0; i < connectors.size(); i++) {
                Point pos = connectors.get(i);
                if (connector.distance(pos) < 2) {
                    connectors.remove(i);
                    i--;
                    continue;
                }

                Set<Integer> updatedRegions = new HashSet<>();
                for (int j : connectorRegions.get(pos)) {
                    updatedRegions.add(mergedRegions.get(j));
                }

                if (updatedRegions.size() <= 1) {
                    if (random.nextInt(extraConnectorChance) == 0) {
                        addJunction(pos);
                    }
                    connectors.remove(i);
                    i--;
                }
            }
        }
    }

    protected void findConnectors(Map<Point, Set<Integer>> connectorRegions) {
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (tiles[i][j].getType() == Type.WALL) {
                    Set<Integer> regions = new HashSet<>();
                    for (Direction d : Direction.CARDINAL) {
                        Point neighbor = Utils.shiftPoint(i, j, d, 1);
                        int region = tiles[neighbor.x][neighbor.y].getRegion();
                        if (region > -1) {
                            regions.add(region);
                        }
                    }

                    if (regions.size() >= 2) {
                        connectorRegions.put(new Point(i, j), regions);
                    }
                }
            }
        }
    }

    protected void addJunction(Point p) {
        tiles[p.x][p.y].setType(Type.DOOR);
    }

    protected void removeDeadEnds() {
        Queue<Point> deadEnds = new LinkedList<>();

        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (tiles[i][j].getType() != Type.WALL) {
                    Point curr = new Point(i, j);
                    int exits = countExits(curr);

                    if (exits <= 1) {
                        deadEnds.add(curr);
                    }
                }
            }
        }

        while (!deadEnds.isEmpty()) {
            Point curr = deadEnds.poll();
            int exits = countExits(curr);
            if (exits <= 1) {
                fill(curr.x, curr.y);
                for (Direction d : Direction.CARDINAL) {
                    Point neighbor = Utils.shiftPoint(curr, d, 1);
                    if (tiles[neighbor.x][neighbor.y].getType() != Type.WALL) {
                        deadEnds.add(neighbor);
                    }
                }
            }
        }
    }

    protected int countExits(Point p) {
        int exits = 0;

        for (Direction d : Direction.CARDINAL) {
            Point neighbor = Utils.shiftPoint(p, d, 1);
            if (tiles[neighbor.x][neighbor.y].getType() != Type.WALL) {
                exits++;
            }
        }

        return exits;
    }

    protected void startRegion() {
        currentRegion++;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
