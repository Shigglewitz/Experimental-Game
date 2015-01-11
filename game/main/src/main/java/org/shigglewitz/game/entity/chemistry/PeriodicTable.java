package org.shigglewitz.game.entity.chemistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.shigglewitz.game.Utils;

public class PeriodicTable {
    private static final int NUM_PERIODS = 5;
    private static final int[] ELEMENTS_PER_PERIOD = { 2, 8, 8, 18, 18 };

    private Element[][] table;

    public PeriodicTable() {
        initializeTable();
        readProperties();
    }

    private void initializeTable() {
        table = new Element[NUM_PERIODS][];
        for (int i = 0; i < ELEMENTS_PER_PERIOD.length; i++) {
            table[i] = new Element[ELEMENTS_PER_PERIOD[i]];
        }
    }

    private void readProperties() {
        try {
            InputStream in = getClass().getResourceAsStream("/elements.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String input = null;
            String[] split;

            int period = 0;
            int family = 0;
            int atomicNumber = 0;
            String symbol = "";
            String name = "";
            double weight = 0;
            Element e;

            input = br.readLine();

            while (input != null) {
                // ignore comments
                if (input.startsWith("#")) {
                    continue;
                }

                // if it's a blank line, it symbolizes the next element
                if (Utils.hasText(input)) {
                    if (!input.contains("=")) {
                        name = input;
                    } else {
                        split = input.split("=");
                        switch (split[0]) {
                        case "Num":
                            atomicNumber = Integer.parseInt(split[1]);
                            break;
                        case "Sym":
                            symbol = split[1];
                            break;
                        case "Wei":
                            weight = Double.parseDouble(split[1]);
                            break;
                        case "Per":
                            period = Integer.parseInt(split[1]);
                            break;
                        case "Fam":
                            family = Integer.parseInt(split[1]);
                            break;
                        }
                    }
                } else {
                    e = new Element(period, family, atomicNumber, symbol, name,
                            weight);
                    insertElement(e);

                    // reset properties
                    period = 0;
                    family = 0;
                    atomicNumber = 0;
                    symbol = "";
                    name = "";
                    weight = 0;
                }

                input = br.readLine();
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private void insertElement(Element e) {
        int previousSum = 0;
        for (int i = 1; i < e.getPeriod(); i++) {
            previousSum += ELEMENTS_PER_PERIOD[i - 1];
        }
        int pos = e.getAtomicNumber() - previousSum - 1;
        System.out.println("Inserting element " + e.getName() + " at "
                + (e.getPeriod() - 1) + "," + pos);
        table[e.getPeriod() - 1][pos] = e;

    }

    public Element[][] getTable() {
        return table;
    }
}
