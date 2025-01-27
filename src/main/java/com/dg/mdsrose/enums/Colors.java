package com.dg.mdsrose.enums;

import java.awt.*;

public enum Colors {
    RED("Red",Color.RED),
    GREEN("Green",Color.GREEN),
    YELLOW("Yellow",Color.YELLOW),
    BLACK("Black",Color.BLACK),
    BLUE("Blue",Color.BLUE);

    private String value;
    private Color color;

    Colors(String value, Color color) {
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public static Colors from(String color) {
        for (Colors colors : Colors.values()) {
            if (colors.getValue().equals(color)) {
                return colors;
            }
        }
        return null;
    }
}
