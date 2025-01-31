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

    public static MarkerOption from(String marker) {
        for (MarkerOption shape : MarkerOption.values()) {
            if (shape.getValue().equals(marker)) {
                return shape;
            }
        }
        return null;
    }
}
