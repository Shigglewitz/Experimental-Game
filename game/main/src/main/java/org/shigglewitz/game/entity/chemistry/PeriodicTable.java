package org.shigglewitz.game.entity.chemistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.shigglewitz.game.entity.chemistry.Binding.ElementBinding;

public class PeriodicTable {
    private List<List<Element>> table;
    private List<List<Element>> unmodifiableTable;

    private static PeriodicTable INSTANCE = new PeriodicTable();

    public static PeriodicTable getInstance() {
        return INSTANCE;
    }

    private PeriodicTable() {
        table = new ArrayList<>();
        readProperties();
        writeProtectTable();
    }

    private void writeProtectTable() {
        List<List<Element>> temp = new ArrayList<>();

        for (List<Element> list : table) {
            temp.add(Collections.unmodifiableList(list));
        }
        unmodifiableTable = Collections.unmodifiableList(temp);
    }

    private void readProperties() {
        try {
            JAXBContext context = JAXBContext.newInstance(Binding.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Binding binding = (Binding) unmarshaller.unmarshal(getClass()
                    .getResourceAsStream("/Periodic Table of Elements.xml"));
            sortElements(binding.getElements());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    protected void sortElements(List<ElementBinding> list) {
        Map<String, Element> nobleGasses = new HashMap<>();
        List<Element> lanthanides = new ArrayList<>();
        List<Element> actinides = new ArrayList<>();

        for (ElementBinding eb : list) {
            Element e = eb.getElement();
            switch (e.getType()) {
            case LANTHANIDE:
                lanthanides.add(e);
                continue;
            case ACTINIDE:
                actinides.add(e);
                continue;
            case NOBLE_GAS:
                nobleGasses.put("[" + e.getSymbol() + "]", e);
                break;
            default:
                break;
            }

            int row = e.getDisplayRow() - 1;
            if (row >= table.size()) {
                table.add(new ArrayList<Element>());
            }
            table.get(row).add(e);
        }

        table.add(lanthanides);
        table.add(actinides);

        for (ElementBinding eb : list) {
            Element e = eb.getElement();

            e.initializeElectronConfiguration(nobleGasses);
        }
    }

    public List<List<Element>> getTable() {
        return unmodifiableTable;
    }
}
