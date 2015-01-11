package org.shigglewitz.game.entity.chemistry;

public class Element {
    protected final int period;
    protected final int family;
    protected final int atomicNumber;
    protected final String symbol;
    protected final String name;
    protected final double weight;

    protected Element(int period, int family, int atomicNumber, String symbol,
            String name, double weight) {
        this.period = period;
        this.family = family;
        this.atomicNumber = atomicNumber;
        this.symbol = symbol;
        this.name = name;
        this.weight = weight;
    }

    public int getPeriod() {
        return period;
    }

    public int getFamily() {
        return family;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }
}
