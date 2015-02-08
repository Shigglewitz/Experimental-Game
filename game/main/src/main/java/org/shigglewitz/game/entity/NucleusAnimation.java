package org.shigglewitz.game.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shigglewitz.game.Utils;
import org.shigglewitz.game.entity.chemistry.ElectronConfiguration;
import org.shigglewitz.game.entity.chemistry.Element;
import org.shigglewitz.game.entity.chemistry.Element.Type;

public class NucleusAnimation extends Animation {
    private Logger logger = LogManager.getLogger(getClass());

    private static final int NUM_FRAMES = 4;
    final static BasicStroke stroke = new BasicStroke(2.0f);

    private int numProtons;
    private int numNeutrons;
    private int width;
    private int height;
    private double neutronsPerProton;
    private ElectronConfiguration electronConfiguration;
    private Color protonColor;
    private Color neutronColor;
    private Color borderColor;
    private Color electronColor;
    private Color electronBorderColor;
    private int baseScatterSize;
    private int scatterSize;
    private int particleSize;
    private Random random;
    private boolean isNobleGas;
    private Color topHatColor;

    public NucleusAnimation(int delay, int width, int height, Element e,
            Color protonColor, Color neutronColor, Color borderColor,
            Color electronColor, Color electronBorderColor,
            int baseScatterSize, int particleSize) {
        super();

        this.delay = delay;
        this.width = width;
        this.height = height;
        this.protonColor = protonColor;
        this.neutronColor = neutronColor;
        this.borderColor = borderColor;
        this.electronColor = electronColor;
        this.electronBorderColor = electronBorderColor;
        this.baseScatterSize = baseScatterSize;
        this.scatterSize = baseScatterSize;
        this.particleSize = particleSize;
        random = new Random();
        frames = new BufferedImage[NUM_FRAMES];
        isNobleGas = false;
        topHatColor = Color.BLACK;
        if (e != null) {
            changeElement(e);
        }
    }

    public void changeElement(Element e) {
        numProtons = e.getAtomicNumber();
        numNeutrons = (int) e.getAtomicWeight() - numProtons;
        neutronsPerProton = ((double) numNeutrons) / ((double) numProtons);
        scatterSize = (int) (baseScatterSize * Math.log(e.getAtomicWeight()));
        electronConfiguration = e.getElectronConfiguration();
        isNobleGas = e.getType() == Type.NOBLE_GAS;
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
        Utils.normalizeGraphics(g);

        drawNucleus(g);
        drawElectrons(g);
        if (isNobleGas) {
            drawTopHat(g);
        }

        g.dispose();

        return ret;
    }

    protected void drawNucleus(Graphics2D g) {
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
    }

    protected void drawElectrons(Graphics2D g) {
        int x = width / 2 - scatterSize - particleSize / 2;
        int y = height / 2 - scatterSize - particleSize / 2;
        int numShells = electronConfiguration.getShellCounts().size();
        double horizontalOffsetPerShell = ((double) x) / (numShells + 1);
        double verticalOffsetPerShell = ((double) y) / (numShells + 1);
        int hOffset = 0;
        int vOffset = 0;

        for (int i = 0; i < numShells; i++) {
            hOffset = (int) ((i + 1) * horizontalOffsetPerShell);
            vOffset = (int) ((i + 1) * verticalOffsetPerShell);
            g.setColor(electronColor);
            g.drawOval(x - hOffset, y - vOffset, (hOffset + scatterSize) * 2
                    + (particleSize / 2 * 2) - 1, (vOffset + scatterSize) * 2
                    + (particleSize / 2 * 2) - 1);
        }
    }

    protected void drawTopHat(Graphics2D g) {
        System.out.println(scatterSize);
        int x = width / 2 - particleSize / 2;
        int y = height / 2 - particleSize / 2;
        Shape crown = new RoundRectangle2D.Double(x - (5 * scatterSize / 4), y
                - (7 * scatterSize / 3), scatterSize * 5 / 6, scatterSize * 2,
                1, 1);
        Shape brim = new Ellipse2D.Double(x - (4 * scatterSize / 3), y
                - (2 * scatterSize / 3), scatterSize * 2, scatterSize / 3);
        brim = Utils.rotateShape(brim, Math.PI * 5 / 6);
        crown = Utils.rotateShape(crown, Math.PI * 5 / 6);
        g.setColor(Color.gray);
        g.draw(brim);
        g.draw(crown);
        g.setColor(topHatColor);
        g.fill(brim);
        g.fill(crown);
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
