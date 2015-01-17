package org.shigglewitz.game.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.shigglewitz.game.entity.chemistry.Element;

public class NucleusAnimation extends Animation {
    private int numProtons;
    private int numNeutrons;
    private double neutronsPerProton;
    private Color protonColor;
    private Color neutronColor;
    private int baseScatterSize;
    private int scatterSize;
    private Random random;
    List<Point2D> protonOffsets;
    List<Point2D> neutronOffsets;

    public NucleusAnimation(int delay, Element e, Color protonColor,
            Color neutronColor, int baseScatterSize) {
        super();

        this.delay = delay;
        this.protonColor = protonColor;
        this.neutronColor = neutronColor;
        this.baseScatterSize = baseScatterSize;
        this.scatterSize = baseScatterSize;
        random = new Random();
        protonOffsets = new ArrayList<>();
        neutronOffsets = new ArrayList<>();
        if (e != null) {
            changeElement(e);
        }
    }

    public void changeElement(Element e) {
        numProtons = e.getAtomicNumber();
        numNeutrons = (int) e.getAtomicWeight() - numProtons;
        neutronsPerProton = ((double) numNeutrons) / ((double) numProtons);
        scatterSize = (int) (baseScatterSize * Math.log(e.getAtomicWeight()));
        reset();
    }

    @Override
    public void reset() {
        synchronized (this) {
            while (numProtons > protonOffsets.size()) {
                protonOffsets.add(new Point(0, 0));
            }
            while (numNeutrons > neutronOffsets.size()) {
                neutronOffsets.add(new Point(0, 0));
            }

            int[] offset = new int[2];
            for (int i = 0; i < numProtons; i++) {
                offset = getOffset(offset);
                protonOffsets.get(i).setLocation(offset[0], offset[1]);
            }
            for (int i = 0; i < numNeutrons; i++) {
                offset = getOffset(offset);
                neutronOffsets.get(i).setLocation(offset[0], offset[1]);
            }
        }
    }

    @Override
    protected void incrementFrame() {
        reset();
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int width, int height) {
        synchronized (this) {
            int currentProtons = numProtons;
            double currentNeutrons = numNeutrons;
            int remainingNeutrons = numNeutrons;

            for (int i = 0; i < currentProtons; i++) {
                g.setColor(protonColor);
                g.fillOval(x + (int) protonOffsets.get(i).getX(), y
                        + (int) protonOffsets.get(i).getY(), width, height);

                currentNeutrons -= neutronsPerProton;
                while (currentNeutrons > 0
                        && remainingNeutrons >= currentNeutrons) {
                    g.setColor(neutronColor);
                    g.fillOval(x + (int) neutronOffsets.get(i).getX(), y
                            + (int) neutronOffsets.get(i).getY(), width, height);
                    remainingNeutrons--;
                }
            }
        }
    }

    public int[] getOffset(int[] offset) {
        double theta = 2 * Math.PI * random.nextDouble();
        double unit = random.nextDouble() + random.nextDouble();
        if (unit > 1) {
            unit = 2 - unit;
        }

        unit *= scatterSize;

        offset[0] = (int) (unit * Math.cos(theta));
        offset[1] = (int) (unit * Math.sin(theta));

        return offset;
    }

    public void setBaseScatterSize(int baseScatterSize) {
        this.baseScatterSize = baseScatterSize;
    }
}
