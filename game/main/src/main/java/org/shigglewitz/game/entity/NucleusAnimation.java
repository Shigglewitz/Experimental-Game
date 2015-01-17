package org.shigglewitz.game.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.shigglewitz.game.entity.chemistry.Element;

public class NucleusAnimation extends Animation {
    private static final int NUM_FRAMES = 4;

    private int numProtons;
    private int numNeutrons;
    private int width;
    private int height;
    private double neutronsPerProton;
    private Color protonColor;
    private Color neutronColor;
    private Color borderColor;
    private int baseScatterSize;
    private int scatterSize;
    private int particleSize;
    private Random random;

    public NucleusAnimation(int delay, int width, int height, Element e,
            Color protonColor, Color neutronColor, Color borderColor,
            int baseScatterSize, int particleSize) {
        super();

        this.delay = delay;
        this.width = width;
        this.height = height;
        this.protonColor = protonColor;
        this.neutronColor = neutronColor;
        this.borderColor = borderColor;
        this.baseScatterSize = baseScatterSize;
        this.scatterSize = baseScatterSize;
        this.particleSize = particleSize;
        random = new Random();
        frames = new BufferedImage[NUM_FRAMES];
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
        for (int i = 0; i < NUM_FRAMES; i++) {
            frames[i] = createImage();
        }

        currentFrame = 0;
    }

    protected BufferedImage createImage() {
        BufferedImage ret = new BufferedImage(width, height,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = ret.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int x = width / 2 - particleSize / 2;
        int y = height / 2 - particleSize / 2;
        int currentProtons = numProtons;
        double currentNeutrons = numNeutrons;
        int remainingNeutrons = numNeutrons;
        int[] offset = new int[2];

        for (int i = 0; i < currentProtons; i++) {
            offset = getOffset(offset);
            g.setColor(protonColor);
            g.fillOval(x + offset[0], y + offset[1], particleSize, particleSize);
            g.setColor(borderColor);
            g.drawOval(x + offset[0], y + offset[1], particleSize, particleSize);

            currentNeutrons -= neutronsPerProton;
            while (currentNeutrons > 0 && remainingNeutrons >= currentNeutrons) {
                offset = getOffset(offset);
                g.setColor(neutronColor);
                g.fillOval(x + offset[0], y + offset[1], particleSize,
                        particleSize);
                g.setColor(borderColor);
                g.drawOval(x + offset[0], y + offset[1], particleSize,
                        particleSize);
                remainingNeutrons--;
            }
        }

        g.dispose();

        return ret;
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
