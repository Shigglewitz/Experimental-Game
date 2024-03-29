package org.shigglewitz.game.state.menu;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shigglewitz.game.Utils;
import org.shigglewitz.game.config.Sprite;
import org.shigglewitz.game.entity.Animation;
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

        flashing = new Animation();
        flashing.setFrames(resources.requestSprite(Sprite.FLASHING));
        flashing.setDelay(Sprite.FLASHING.getDelay());
        alphaComposite = Utils.makeComposite((float) 0.25);

        selectedDisplayBackground = Color.BLACK;
        selectedDisplayBorder = Color.LIGHT_GRAY;
        selectedDisplaySymbolColor = Color.WHITE;
        selectedDisplayInfoColor = Color.LIGHT_GRAY;
        // Hydrogen
        selectedDisplayVerticalAnchor = pt.getTable().get(0).get(0);
        // Titanium
        selectedDisplayHorizontalAnchor = pt.getTable().get(3).get(3);

        selectedElementRow = 0;
        selectedElementCol = 0;
        findSelectedElement();

        configure();
    }

    protected void findSelectedElement() {
        logger.debug("Seeking: (" + selectedElementRow + ","
                + selectedElementCol + ")");
        selectedElement = pt.getTable().get(selectedElementRow).get(
                selectedElementCol);
        flashing.setFrame(0);
        logger.debug("Found " + selectedElement.getName());
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
        selectedDisplayAtomicNumberOffset = VERTICAL_PIXEL_PADDING
                + fm.getAscent();
        fm = c.getFontMetrics(selectedDisplaySymbolFont);
        selectedDisplaySymbolOffset = selectedDisplayAtomicNumberOffset
                + fm.getAscent() - 5;
        fm = c.getFontMetrics(selectedDisplayInfoFont);
        selectedDisplayNameOffset = (6 * VERTICAL_PIXEL_PADDING)
                + selectedDisplaySymbolOffset + fm.getAscent();
        selectedDisplayMassOffset = VERTICAL_PIXEL_PADDING
                + selectedDisplayNameOffset + fm.getAscent();

        selectedDisplayX = calculateHorizontalOffset(selectedDisplayHorizontalAnchor)
                + elementWidth / 2;
        selectedDisplayY = calculateVerticalOffset(selectedDisplayVerticalAnchor)
                - elementHeight / 2;
        selectedDisplayWidth = elementWidth * 4;
        selectedDisplayHeight = elementHeight * 3;
    }

    @Override
    protected void keyPressed(int k) {
        super.keyPressed(k);

        switch (k) {
        case KeyEvent.VK_ESCAPE:
            exit();
            break;
        case KeyEvent.VK_LEFT:
            scrollSelectedHorizontal(true);
            break;
        case KeyEvent.VK_RIGHT:
            scrollSelectedHorizontal(false);
            break;
        case KeyEvent.VK_UP:
            scrollSelectedVertical(true);
            break;
        case KeyEvent.VK_DOWN:
            scrollSelectedVertical(false);
            break;
        case KeyEvent.VK_SPACE:
            // fall through
        case KeyEvent.VK_ENTER:
            selectElement();
            break;
        }
    };

    protected void scrollSelectedHorizontal(boolean left) {
        int elementsInRow = pt.getTable().get(selectedElementRow).size();
        if (left) {
            selectedElementCol = Utils.decrementAndWrap(selectedElementCol,
                    elementsInRow);
        } else {
            selectedElementCol = Utils.incrementAndWrap(selectedElementCol,
                    elementsInRow);
        }

        findSelectedElement();
    }

    protected void scrollSelectedVertical(boolean up) {
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

        findSelectedElement();
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

    protected void exit() {
        gsm.pop();
    }

    @Override
    public void update() {
        flashing.update();
    }

    @Override
    public void draw(Graphics2D g) {
        drawBackground(g);
        drawOutlines(g);
        drawAtomicNumbers(g);
        drawSymbols(g);
        drawInformation(g);
        drawSelected(g);
    }

    protected void drawBackground(Graphics2D g) {
        g.setColor(bgColor);
        g.fillRect(0, 0, config.getWidth(), config.getHeight());
    }

    protected void drawOutlines(Graphics2D g) {

        for (List<Element> period : pt.getTable()) {
            for (Element e : period) {
                if (e == null) {
                    continue;
                }
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

    protected void drawInformation(Graphics2D g) {
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
                selectedDisplayX + HORIZONTAL_PIXEL_PADDING, selectedDisplayY
                        + selectedDisplayAtomicNumberOffset);
        // symbol
        g.setFont(selectedDisplaySymbolFont);
        g.setColor(selectedDisplaySymbolColor);
        g.drawString(selectedElement.getSymbol(), selectedDisplayX
                + HORIZONTAL_PIXEL_PADDING, selectedDisplayY
                + selectedDisplaySymbolOffset);
        // name
        g.setFont(selectedDisplayInfoFont);
        g.setColor(selectedDisplayInfoColor);
        g.drawString(selectedElement.getName(), selectedDisplayX
                + HORIZONTAL_PIXEL_PADDING, selectedDisplayY
                + selectedDisplayNameOffset);
        // mass
        g.drawString(SELECTED_ATOMIC_MASS_FORMAT.format(selectedElement
                .getAtomicWeight()), selectedDisplayX
                + HORIZONTAL_PIXEL_PADDING, selectedDisplayY
                + selectedDisplayMassOffset);
        // electron configuration
        // g.drawString(selectedElement.getElectronConfiguration(),
        // selectedDisplayX + HORIZONTAL_PIXEL_PADDING, selectedDisplayY
        // + selectedDisplayMassOffset + 30);
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
