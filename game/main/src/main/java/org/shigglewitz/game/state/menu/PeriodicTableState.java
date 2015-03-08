package org.shigglewitz.game.state.menu;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.text.NumberFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shigglewitz.game.Utils;
import org.shigglewitz.game.config.Sprite;
import org.shigglewitz.game.entity.Animation;
import org.shigglewitz.game.entity.NucleusAnimation;
import org.shigglewitz.game.entity.SpriteAnimation;
import org.shigglewitz.game.entity.chemistry.Element;
import org.shigglewitz.game.entity.chemistry.Element.Type;
import org.shigglewitz.game.entity.chemistry.PeriodicTable;
import org.shigglewitz.game.state.GameState;
import org.shigglewitz.game.state.GameStateManager;

public class PeriodicTableState extends GameState {
    private final Logger logger = LogManager.getLogger(getClass());

    private static final NumberFormat ATOMIC_MASS_FORMAT = NumberFormat
            .getInstance();
    private static final NumberFormat SELECTED_ATOMIC_MASS_FORMAT = NumberFormat
            .getInstance();
    private static final int HORIZONTAL_PIXEL_PADDING = 2;
    private static final int VERTICAL_PIXEL_PADDING = 2;

    static {
        ATOMIC_MASS_FORMAT.setMaximumFractionDigits(3);
        ATOMIC_MASS_FORMAT.setMinimumFractionDigits(1);

        SELECTED_ATOMIC_MASS_FORMAT.setMaximumFractionDigits(6);
        SELECTED_ATOMIC_MASS_FORMAT.setMinimumFractionDigits(1);
    }

    private PeriodicTable pt;
    private Color bgColor;

    private int elementWidth;
    private int elementHeight;
    private int atomicNumberOffset;
    private int symbolOffset;
    private int atomicMassOffset;
    private Color elementOutlineColor;
    private Color elementBackgroundColor;
    private Color elementSymbolColor;
    private Color elementInformationColor;
    private Color elementAtomicNumberColor;
    private Font elementSymbolFont;
    private Font elementInformationFont;
    private Font elementAtomicNumberFont;

    private Element selectedElement;
    private int selectedElementRow;
    private int selectedElementCol;
    private Composite alphaComposite;
    private Animation flashing;

    private int selectedDisplayX;
    private int selectedDisplayY;
    private int selectedDisplayWidth;
    private int selectedDisplayHeight;
    private int selectedDisplayInfoAscent;
    private int selectedDisplayInfoCharWidth;
    private int selectedDisplayAtomicNumberOffset;
    private int selectedDisplaySymbolOffset;
    private int selectedDisplayNameOffset;
    private int selectedDisplayMassOffset;
    private Color selectedDisplayBackground;
    private Color selectedDisplayBorder;
    private Color selectedDisplaySymbolColor;
    private Color selectedDisplayInfoColor;
    private Font selectedDisplayAtomicNumberFont;
    private Font selectedDisplaySymbolFont;
    private Font selectedDisplayInfoFont;
    private Element selectedDisplayHorizontalAnchor;
    private Element selectedDisplayVerticalAnchor;

    private int nucleusDisplayX;
    private int nucleusDisplayY;
    private int nucleusDisplayWidth;
    private int nucleusDisplayHeight;
    private int nucleusDisplayParticleSize;
    private int nucleusDisplayScatterSize;
    private Color nucleusDisplayBackground;
    private Color nucleusDisplayProtonColor;
    private Color nucleusDisplayNeutronColor;
    private Color nucleusDisplayBorderColor;
    private Color nucleusElectronColor;
    private Color nucleusElectronBorderColor;
    private Element nucleusDisplayHorizontalAnchor;
    private Element nucleusDisplayVerticalAnchor;
    private NucleusAnimation nucleusAnimation;

    public PeriodicTableState(GameStateManager gsm) {
        super(gsm);

        init();
    }

    @Override
    protected void init() {
        pt = new PeriodicTable();

        bgColor = Color.DARK_GRAY;
        elementOutlineColor = Color.BLACK;
        elementBackgroundColor = Color.LIGHT_GRAY;
        elementSymbolColor = Color.RED;
        elementInformationColor = Color.BLACK;
        elementAtomicNumberColor = Color.BLACK;

        flashing = new SpriteAnimation(Sprite.FLASHING);
        alphaComposite = Utils.makeComposite((float) 0.25);

        selectedDisplayBackground = Color.BLACK;
        selectedDisplayBorder = Color.LIGHT_GRAY;
        selectedDisplaySymbolColor = Color.WHITE;
        selectedDisplayInfoColor = Color.LIGHT_GRAY;
        // Hydrogen
        selectedDisplayVerticalAnchor = pt.getTable().get(0).get(0);
        // Scandium
        selectedDisplayHorizontalAnchor = pt.getTable().get(3).get(2);

        nucleusDisplayBackground = Color.WHITE;
        nucleusDisplayProtonColor = Color.RED;
        nucleusDisplayNeutronColor = Color.GRAY;
        nucleusDisplayBorderColor = new Color(
                (nucleusDisplayProtonColor.getRed() + nucleusDisplayNeutronColor
                        .getRed()) / 2,
                (nucleusDisplayProtonColor.getGreen() + nucleusDisplayNeutronColor
                        .getGreen()) / 2,
                (nucleusDisplayProtonColor.getBlue() + nucleusDisplayNeutronColor
                        .getBlue()) / 2);
        nucleusElectronColor = Color.GREEN;
        nucleusElectronBorderColor = Color.BLACK;
        // Hydrogen
        nucleusDisplayVerticalAnchor = pt.getTable().get(0).get(0);
        // Iron
        nucleusDisplayHorizontalAnchor = pt.getTable().get(3).get(7);

        configure();
        nucleusAnimation = new NucleusAnimation(250, nucleusDisplayWidth,
                nucleusDisplayHeight, selectedElement,
                nucleusDisplayProtonColor, nucleusDisplayNeutronColor,
                nucleusDisplayBorderColor, nucleusElectronColor,
                nucleusElectronBorderColor, nucleusDisplayScatterSize,
                nucleusDisplayParticleSize);

        selectedElementRow = 0;
        selectedElementCol = 0;
        findSelectedElement();
    }

    protected void findSelectedElement() {
        logger.debug("Seeking: (" + selectedElementRow + ","
                + selectedElementCol + ")");
        selectedElement = pt.getTable().get(selectedElementRow).get(
                selectedElementCol);
        flashing.reset();
        logger.debug("Found " + selectedElement.getName());
        nucleusAnimation.changeElement(selectedElement);
    }

    @Override
    public void configure() {
        // there are 18 families, with spacing for one half on each side
        elementWidth = config.getWidth() / 19;
        // one cell of padding on each side, with half a cell of padding on each
        // side of rare earth elements
        elementHeight = config.getHeight() / (pt.getTable().size() + 3);

        // TODO: make this based on size
        elementSymbolFont = new Font("Gothic", Font.BOLD, 20);
        elementInformationFont = new Font("Arial", Font.PLAIN, 10);
        elementAtomicNumberFont = new Font("Arial", Font.PLAIN, 14);
        selectedDisplayAtomicNumberFont = new Font("Arial", Font.PLAIN, 25);
        selectedDisplaySymbolFont = new Font("Gothic", Font.BOLD, 60);
        selectedDisplayInfoFont = new Font("Arial", Font.PLAIN, 20);

        Canvas c = new Canvas();
        // main table offsets
        FontMetrics fm = c.getFontMetrics(elementAtomicNumberFont);
        atomicNumberOffset = VERTICAL_PIXEL_PADDING + fm.getAscent();
        fm = c.getFontMetrics(elementSymbolFont);
        symbolOffset = atomicNumberOffset + fm.getAscent();
        fm = c.getFontMetrics(elementInformationFont);
        atomicMassOffset = symbolOffset + VERTICAL_PIXEL_PADDING
                + fm.getAscent();

        // selected offsets
        fm = c.getFontMetrics(selectedDisplayAtomicNumberFont);
        selectedDisplayAtomicNumberOffset = (VERTICAL_PIXEL_PADDING * 3)
                + fm.getAscent();
        fm = c.getFontMetrics(selectedDisplaySymbolFont);
        selectedDisplaySymbolOffset = selectedDisplayAtomicNumberOffset
                + fm.getAscent() - 5;
        fm = c.getFontMetrics(selectedDisplayInfoFont);
        selectedDisplayNameOffset = (6 * VERTICAL_PIXEL_PADDING)
                + selectedDisplaySymbolOffset + fm.getAscent();
        selectedDisplayMassOffset = (VERTICAL_PIXEL_PADDING * 3)
                + selectedDisplayNameOffset + fm.getAscent();
        selectedDisplayInfoAscent = fm.getAscent();
        selectedDisplayInfoCharWidth = fm.charWidth('0');

        selectedDisplayX = calculateHorizontalOffset(selectedDisplayHorizontalAnchor)
                + elementWidth / 2;
        selectedDisplayY = calculateVerticalOffset(selectedDisplayVerticalAnchor)
                - elementHeight / 2;
        selectedDisplayWidth = elementWidth * 4;
        selectedDisplayHeight = elementHeight * 3;

        // nucleus display offsets
        nucleusDisplayX = calculateHorizontalOffset(nucleusDisplayHorizontalAnchor)
                + elementWidth / 2;
        nucleusDisplayY = calculateVerticalOffset(nucleusDisplayVerticalAnchor)
                - elementHeight / 2;
        nucleusDisplayWidth = elementWidth * 4;
        nucleusDisplayHeight = elementHeight * 3;
        nucleusDisplayParticleSize = 15;
        nucleusDisplayScatterSize = 4;

        if (nucleusAnimation != null) {
            nucleusAnimation.setBaseScatterSize(nucleusDisplayScatterSize);
        }
    }

    @Override
    protected void pressLeft() {
        scrollSelectedHorizontal(true);
    }

    @Override
    protected void pressRight() {
        scrollSelectedHorizontal(false);
    }

    @Override
    protected void pressUp() {
        scrollSelectedVertical(true);
    }

    @Override
    protected void pressDown() {
        scrollSelectedVertical(false);
    }

    @Override
    protected void pressSelect() {
        selectElement();
    }

    protected void scrollSelectedHorizontal(boolean left) {
        int elementsInRow = pt.getTable().get(selectedElementRow).size();
        int numPeriods = pt.getTable().size();
        int newCol = selectedElementCol;
        if (left) {
            newCol--;
        } else {
            newCol++;
        }

        if (newCol < 0) {
            selectedElementCol = elementsInRow - 1;
            selectedElementRow = Utils.decrementAndWrap(selectedElementRow,
                    numPeriods);
            selectedElementCol = findClosestFamily(18, selectedElementRow);
        } else if (newCol >= elementsInRow) {
            selectedElementCol = 0;
            selectedElementRow = Utils.incrementAndWrap(selectedElementRow,
                    numPeriods);
        } else {
            selectedElementCol = newCol;
        }

        findSelectedElement();
    }

    protected void scrollSelectedVertical(boolean up) {
        changeVerticalSelection(up);

        findSelectedElement();
    }

    protected void changeVerticalSelection(boolean up) {
        int numPeriods = pt.getTable().size();
        int previousRow = selectedElementRow;

        if (up) {
            selectedElementRow = Utils.decrementAndWrap(selectedElementRow,
                    numPeriods);
        } else {
            selectedElementRow = Utils.incrementAndWrap(selectedElementRow,
                    numPeriods);
        }

        if (pt.getTable().get(previousRow).size() != pt.getTable().get(
                selectedElementRow).size()) {
            selectedElementCol = findClosestFamily(selectedElement
                    .getDisplayColumn(), selectedElementRow);
        }
    }

    protected int findClosestFamily(int previousDisplayColumn, int period) {
        int closest = -1;
        int minDifference = 100;
        int difference = 0;
        int absDifference = 0;

        for (int i = 0; i < pt.getTable().get(period).size(); i++) {
            Element e = pt.getTable().get(period).get(i);
            difference = e.getDisplayColumn() - previousDisplayColumn;
            absDifference = Math.abs(difference);

            if (absDifference < minDifference) {
                minDifference = absDifference;
                closest = i;
            }

            if (difference >= 0) {
                break;
            }
        }

        return closest;
    }

    protected void selectElement() {
        System.out.println("Selected: " + selectedElement.getName() + "!");
    }

    @Override
    protected void exit() {
        gsm.pop();
    }

    @Override
    public void update() {
        flashing.update();
        nucleusAnimation.update();
    }

    @Override
    public void draw(Graphics2D g) {
        drawBackground(g);
        drawElements(g);
        drawSelected(g);
    }

    protected void drawBackground(Graphics2D g) {
        g.setColor(bgColor);
        g.fillRect(0, 0, config.getWidth(), config.getHeight());
    }

    protected void drawElements(Graphics2D g) {
        drawOutlines(g);
        drawAtomicNumbers(g);
        drawSymbols(g);
        drawAtomicMass(g);
    }

    protected void drawOutlines(Graphics2D g) {
        for (List<Element> period : pt.getTable()) {
            for (Element e : period) {
                g.setColor(elementOutlineColor);
                g.drawRect(calculateHorizontalOffset(e),
                        calculateVerticalOffset(e), elementWidth, elementHeight);
                g.setColor(elementBackgroundColor);
                g.fillRect(calculateHorizontalOffset(e) + 1,
                        calculateVerticalOffset(e) + 1, elementWidth - 1,
                        elementHeight - 1);
            }
        }
    }

    protected void drawAtomicNumbers(Graphics2D g) {
        g.setColor(elementAtomicNumberColor);
        g.setFont(elementAtomicNumberFont);

        for (List<Element> period : pt.getTable()) {
            for (Element e : period) {
                if (e == null) {
                    continue;
                }
                g.drawString(
                        Integer.toString(e.getAtomicNumber()),
                        calculateHorizontalOffset(e) + HORIZONTAL_PIXEL_PADDING,
                        calculateVerticalOffset(e) + atomicNumberOffset);
            }
        }
    }

    protected void drawSymbols(Graphics2D g) {
        g.setColor(elementSymbolColor);
        g.setFont(elementSymbolFont);
        for (List<Element> period : pt.getTable()) {
            for (Element e : period) {
                if (e == null) {
                    continue;
                }
                g.drawString(e.getSymbol(), calculateHorizontalOffset(e)
                        + HORIZONTAL_PIXEL_PADDING, calculateVerticalOffset(e)
                        + symbolOffset);
            }
        }
    }

    protected void drawAtomicMass(Graphics2D g) {
        g.setColor(elementInformationColor);
        g.setFont(elementInformationFont);
        for (List<Element> period : pt.getTable()) {
            for (Element e : period) {
                if (e == null) {
                    continue;
                }
                g.drawString(
                        ATOMIC_MASS_FORMAT.format(e.getAtomicWeight()),
                        calculateHorizontalOffset(e) + HORIZONTAL_PIXEL_PADDING,
                        calculateVerticalOffset(e) + atomicMassOffset);
            }
        }
    }

    protected void drawSelected(Graphics2D g) {
        drawSelectedFlash(g);
        drawSelectedInfo(g);
        drawNucleus(g);
    }

    protected void drawSelectedFlash(Graphics2D g) {
        Composite originalComposite = g.getComposite();
        g.setComposite(alphaComposite);
        g.drawImage(flashing.getImage(),
                calculateHorizontalOffset(selectedElement),
                calculateVerticalOffset(selectedElement), elementWidth,
                elementHeight, null);
        g.setComposite(originalComposite);
    }

    protected void drawSelectedInfo(Graphics2D g) {
        // background
        g.setColor(selectedDisplayBackground);
        g.fillRect(selectedDisplayX, selectedDisplayY, selectedDisplayWidth,
                selectedDisplayHeight);
        // border
        g.setColor(selectedDisplayBorder);
        g.drawRect(selectedDisplayX, selectedDisplayY, selectedDisplayWidth,
                selectedDisplayHeight);
        // atomic number
        g.setFont(selectedDisplayInfoFont);
        g.setColor(selectedDisplayInfoColor);
        g.drawString(Integer.toString(selectedElement.getAtomicNumber()),
                selectedDisplayX + (HORIZONTAL_PIXEL_PADDING * 4),
                selectedDisplayY + selectedDisplayAtomicNumberOffset);
        // symbol
        g.setFont(selectedDisplaySymbolFont);
        g.setColor(selectedDisplaySymbolColor);
        g.drawString(selectedElement.getSymbol(), selectedDisplayX
                + (HORIZONTAL_PIXEL_PADDING * 4), selectedDisplayY
                + selectedDisplaySymbolOffset);
        // name
        g.setFont(selectedDisplayInfoFont);
        g.setColor(selectedDisplayInfoColor);
        g.drawString(selectedElement.getName(), selectedDisplayX
                + (HORIZONTAL_PIXEL_PADDING * 4), selectedDisplayY
                + selectedDisplayNameOffset);
        // mass
        g.drawString(SELECTED_ATOMIC_MASS_FORMAT.format(selectedElement
                .getAtomicWeight()), selectedDisplayX
                + (HORIZONTAL_PIXEL_PADDING * 4), selectedDisplayY
                + selectedDisplayMassOffset);
        // electron configuration
        List<String> shellCounts = selectedElement.getElectronConfiguration()
                .getShellCounts();
        String s = null;
        for (int i = 0; i < shellCounts.size(); i++) {
            s = shellCounts.get(i);
            g.drawString(s, selectedDisplayX + selectedDisplayWidth
                    - (selectedDisplayInfoCharWidth * s.length())
                    - (HORIZONTAL_PIXEL_PADDING * 4), selectedDisplayY
                    + selectedDisplayInfoAscent * (i + 1)
                    + (VERTICAL_PIXEL_PADDING * 3));
        }
    }

    protected void drawNucleus(Graphics2D g) {
        // background
        g.setColor(nucleusDisplayBackground);
        g.fillRect(nucleusDisplayX, nucleusDisplayY, nucleusDisplayWidth,
                nucleusDisplayHeight);
        g.drawImage(nucleusAnimation.getImage(), nucleusDisplayX,
                nucleusDisplayY, null);
    }

    protected int calculateHorizontalOffset(Element e) {
        return (e.getDisplayColumn() - 1) * elementWidth
                + ((config.getWidth() - 18 * elementWidth) / 2);
    }

    protected int calculateVerticalOffset(Element e) {
        double additionalOffset = 0.0;
        if (e.getType() == Type.LANTHANIDE || e.getType() == Type.ACTINIDE) {
            additionalOffset = 0.5;
        }
        return (int) ((e.getDisplayRow() + additionalOffset) * elementHeight);
    }
}
