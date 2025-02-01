package com.dg.mdsrose.enums;

import java.awt.*;

public enum MarkerOption {
    SQUARE("Square", new java.awt.geom.Rectangle2D.Double(-3, -3, 6, 6)),
    TRIANGLE_UP("Triangle up", new java.awt.Polygon(new int[]{-5, 5, 0}, new int[]{5, 5, -5}, 3)),
    TRIANGLE_DOWN("Triangle down", new java.awt.Polygon(new int[]{-5, 5, 0}, new int[]{-5, -5, 5}, 3)),
    CIRCLE("Circle", new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6)),
    DIAMOND("Diamond", new java.awt.Polygon(new int[]{-6, 0, 6, 0}, new int[]{0, 6, 0, -6}, 4)),;

    private final String value;
    private final Shape shape;

    MarkerOption(String value, Shape shape) {
        this.value = value;
        this.shape = shape;
    }

    public String getValue() {
        return value;
    }

    public Shape getShape() {
        return shape;
    }

    public static MarkerOption from(String value) {
        for (MarkerOption shape : MarkerOption.values()) {
            if (shape.getValue().equals(value)) {
                return shape;
            }
        }
        return null;
    }

    public static String[] comboBoxOptions() {
        String[] options = new String[MarkerOption.values().length];
        MarkerOption[] values = MarkerOption.values();
        for(int i=0; i<values.length; i++) {
            options[i] = values[i].getValue();
        }
        return options;
    }
}
