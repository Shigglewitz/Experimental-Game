package org.shigglewitz.game.entity.chemistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElectronConfiguration {
    protected final static Pattern SHELL_PATTERN = Pattern
            .compile("([0-9])([a-z])([0-9]+)");

    private String initial;
    private List<Integer> shellCounts;
    private List<String> exposedShellCounts;

    public ElectronConfiguration(String input) {
        initial = input;
        shellCounts = new ArrayList<>();

        String[] split = input.split(" ");
        Matcher m = null;
        int shell = -1;
        // TODO: can we do anything with subshell?
        // String subshell = null;
        int numElectrons = -1;

        for (String s : split) {
            m = SHELL_PATTERN.matcher(s);
            if (m.find()) {
                shell = Integer.parseInt(m.group(1));
                // subshell = m.group(2);
                numElectrons = Integer.parseInt(m.group(3));

                if (shell > shellCounts.size()) {
                    shellCounts.add(0);
                }
                shellCounts.set(shell - 1, shellCounts.get(shell - 1)
                        + numElectrons);
            }
        }

        List<String> temp = new ArrayList<>();
        for (int i : shellCounts) {
            temp.add(Integer.toString(i));
        }
        exposedShellCounts = Collections.unmodifiableList(temp);
    }

    public List<String> getShellCounts() {
        return exposedShellCounts;
    }

    @Override
    public String toString() {
        return initial;
    }
}
