package com.dg.mdsrose.project;

import java.awt.*;

public final class Shape {

    private final Color color;
    private final java.awt.Shape marker;
    private final String label;

    public Shape(Color color, java.awt.Shape marker, String label) {
        this.color = color;
        this.marker = marker;
        this.label = label;
    }

    public Color getColor() {
        return color;
    }

    public java.awt.Shape getMarker() {
        return marker;
    }

    public String getLabel() {
        return label;
    }
}
