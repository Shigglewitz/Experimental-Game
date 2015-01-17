package org.shigglewitz.game.entity.chemistry;

import java.io.Serializable;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;

public class Element implements Serializable {
    protected static final String NOTATION_STRING = "(\\[([A-Z][a-z]?)\\] )?(([0-9][a-z][1-9][0-9]?)[ ]?)+";
    protected static final Pattern NOTATION_PATTERN = Pattern
            .compile(NOTATION_STRING);

    @XmlType
    @XmlEnum(String.class)
    public static enum Type {
        @XmlEnumValue("Actinide") ACTINIDE,
        @XmlEnumValue("Alkali Metal") ALKALI_METAL,
        @XmlEnumValue("Alkaline Earth Metal") ALKALINE_EARTH_METAL,
        @XmlEnumValue("Halogen") HALOGEN,
        @XmlEnumValue("Lanthanide") LANTHANIDE,
        @XmlEnumValue("Metal") METAL,
        @XmlEnumValue("Metalloid") METALLOID,
        @XmlEnumValue("Noble Gas") NOBLE_GAS,
        @XmlEnumValue("Nonmetal") NONMETAL,
        @XmlEnumValue("Transactinide") TRANSACTINIDE,
        @XmlEnumValue("Transition Metal") TRANSITION_METAL
    }

    private static final long serialVersionUID = -3420790083065795349L;

    private int atomicNumber;
    private String name;
    private String symbol;
    private double atomicWeight;
    private int period;
    private int group;
    private String phase;
    private String mostStableCrystal;
    private Type type;
    private double ionicRadius;
    private double atomicRadius;
    private double electroNegativity;
    private double firstIonizationPotential;
    private double density;
    private double meltingPointK;
    private double boilingPointK;
    private int isotopes;
    private String discoverer;
    private int yearOfDiscovery;
    private double specificHeatCapacity;
    private String electronNotation;
    private ElectronConfiguration electronConfiguration;
    private int displayRow;
    private int displayColumn;

    @XmlElement(name = "atomic-number")
    public int getAtomicNumber() {
        return atomicNumber;
    }

    public void setAtomicNumber(int atomicNumber) {
        this.atomicNumber = atomicNumber;
    }

    @XmlElement(name = "element")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "symbol")
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @XmlElement(name = "atomic-weight")
    public double getAtomicWeight() {
        return atomicWeight;
    }

    public void setAtomicWeight(double atomicWeight) {
        this.atomicWeight = atomicWeight;
    }

    @XmlElement(name = "period")
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @XmlElement(name = "group")
    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @XmlElement(name = "phase")
    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    @XmlElement(name = "most-stable-crystal")
    public String getMostStableCrystal() {
        return mostStableCrystal;
    }

    public void setMostStableCrystal(String mostStableCrystal) {
        this.mostStableCrystal = mostStableCrystal;
    }

    @XmlElement(name = "type")
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @XmlElement(name = "ionic-radius")
    public double getIonicRadius() {
        return ionicRadius;
    }

    public void setIonicRadius(double ionicRadius) {
        this.ionicRadius = ionicRadius;
    }

    @XmlElement(name = "atomic-radius")
    public double getAtomicRadius() {
        return atomicRadius;
    }

    public void setAtomicRadius(double atomicRadius) {
        this.atomicRadius = atomicRadius;
    }

    @XmlElement(name = "electron-negativity")
    public double getElectroNegativity() {
        return electroNegativity;
    }

    public void setElectroNegativity(double electroNegativity) {
        this.electroNegativity = electroNegativity;
    }

    @XmlElement(name = "first-ionization-potential")
    public double getFirstIonizationPotential() {
        return firstIonizationPotential;
    }

    public void setFirstIonizationPotential(double firstIonizationPotential) {
        this.firstIonizationPotential = firstIonizationPotential;
    }

    @XmlElement(name = "density")
    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    @XmlElement(name = "melting-point-k")
    public double getMeltingPointK() {
        return meltingPointK;
    }

    public void setMeltingPointK(double meltingPointK) {
        this.meltingPointK = meltingPointK;
    }

    @XmlElement(name = "boiling-point-k")
    public double getBoilingPointK() {
        return boilingPointK;
    }

    public void setBoilingPointK(double boilingPointK) {
        this.boilingPointK = boilingPointK;
    }

    @XmlElement(name = "isotopes")
    public int getIsotopes() {
        return isotopes;
    }

    public void setIsotopes(int isotopes) {
        this.isotopes = isotopes;
    }

    @XmlElement(name = "discoverer")
    public String getDiscoverer() {
        return discoverer;
    }

    public void setDiscoverer(String discoverer) {
        this.discoverer = discoverer;
    }

    @XmlElement(name = "year-of-discovery")
    public int getYearOfDiscovery() {
        return yearOfDiscovery;
    }

    public void setYearOfDiscovery(int yearOfDiscovery) {
        this.yearOfDiscovery = yearOfDiscovery;
    }

    @XmlElement(name = "specific-heat-capacity")
    public double getSpecificHeatCapacity() {
        return specificHeatCapacity;
    }

    public void setSpecificHeatCapacity(double specificHeatCapacity) {
        this.specificHeatCapacity = specificHeatCapacity;
    }

    @XmlElement(name = "electron-configuration")
    public String getElectronNotation() {
        return electronNotation;
    }

    public void setElectronNotation(String electronNotation) {
        this.electronNotation = electronNotation;
    }

    @XmlElement(name = "display-row")
    public int getDisplayRow() {
        return displayRow;
    }

    public void setDisplayRow(int displayRow) {
        this.displayRow = displayRow;
    }

    @XmlElement(name = "display-column")
    public int getDisplayColumn() {
        return displayColumn;
    }

    public void setDisplayColumn(int displayColumn) {
        this.displayColumn = displayColumn;
    }

    public void initializeElectronConfiguration(Map<String, Element> nobleGasses) {
        String[] shells = this.electronNotation.split(" ");

        if (shells[0].startsWith("[")) {
            shells[0] = nobleGasses.get(shells[0]).getElectronConfiguration()
                    .toString();
        }

        electronConfiguration = new ElectronConfiguration(StringUtils.join(
                shells, " "));
    }

    public ElectronConfiguration getElectronConfiguration() {
        return electronConfiguration;
    }

    public void setElectronConfiguration(
            ElectronConfiguration electronConfiguration) {
        this.electronConfiguration = electronConfiguration;
    }
}
