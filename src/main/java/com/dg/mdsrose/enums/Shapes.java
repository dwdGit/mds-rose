package com.dg.mdsrose.enums;

import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.SeriesMarkers;

public enum Shapes {
    SQUARE("Square", SeriesMarkers.SQUARE),
    TRIANGLE_UP("Triangle up", SeriesMarkers.TRIANGLE_UP),
    TRIANGLE_DOWN("Triangle down", SeriesMarkers.TRIANGLE_DOWN),
    CIRCLE("Circle", SeriesMarkers.CIRCLE),
    DIAMOND("Diamond", SeriesMarkers.DIAMOND),;

    private String value;
    private Marker shape;

    Shapes(String value, Marker shape) {
        this.value = value;
        this.shape = shape;
    }

    public String getValue() {
        return value;
    }

    public Marker getShape() {
        return shape;
    }

    public static Shapes from(String marker) {
        for (Shapes shape : Shapes.values()) {
            if (shape.getValue().equals(marker)) {
                return shape;
            }
        }
        return null;
    }
}
