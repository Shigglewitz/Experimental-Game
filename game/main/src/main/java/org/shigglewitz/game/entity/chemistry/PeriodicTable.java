package org.shigglewitz.game.entity.chemistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shigglewitz.game.entity.chemistry.Binding.ElementBinding;
import org.shigglewitz.game.entity.chemistry.Element.Type;

public class PeriodicTable {
    private Logger logger = LogManager.getLogger(getClass());

    private List<List<Element>> table;
    private List<List<Element>> unmodifiableTable;

    public PeriodicTable() {
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
        Set<Type> types = null;
        List<Element> lanthanides = new ArrayList<>();
        List<Element> actinides = new ArrayList<>();

        if (logger.isDebugEnabled()) {
            types = new HashSet<>();
        }

        for (ElementBinding eb : list) {
            Element e = eb.getElement();
            if (logger.isDebugEnabled()) {
                types.add(e.getType());
            }
            if (e.getType() == Type.LANTHANIDE) {
                lanthanides.add(e);
                continue;
            } else if (e.getType() == Type.ACTINIDE) {
                actinides.add(e);
                continue;
            }
            int row = e.getDisplayRow() - 1;

            if (row >= table.size()) {
                table.add(new ArrayList<Element>());
            }
            table.get(row).add(e);
        }

        table.add(lanthanides);
        table.add(actinides);

        if (logger.isDebugEnabled()) {
            logger.debug(StringUtils.join(types, ","));
        }
    }

    public List<List<Element>> getTable() {
        return unmodifiableTable;
    }
}
