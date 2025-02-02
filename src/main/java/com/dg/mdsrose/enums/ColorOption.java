package com.dg.mdsrose.enums;

import java.awt.*;

public enum ColorOption {
    RED("Red",Color.RED),
    GREEN("Green",Color.GREEN),
    YELLOW("Yellow",Color.YELLOW),
    BLACK("Black",Color.BLACK),
    BLUE("Blue",Color.BLUE);

    private final String value;
    private final Color color;

    ColorOption(String value, Color color) {
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public static ColorOption from(String value) {
        for (ColorOption color : ColorOption.values()) {
            if (color.getValue().equals(value)) {
                return color;
            }
        }
        return null;
    }

    public static String[] comboBoxOptions() {
        String[] options = new String[ColorOption.values().length];
        ColorOption[] values = ColorOption.values();
        for(int i = 0; i < values.length; i++) {
            options[i] = values[i].getValue();
        }
        return options;
    }
}
