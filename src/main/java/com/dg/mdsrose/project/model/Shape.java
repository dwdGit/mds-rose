package com.dg.mdsrose.project.model;

import com.dg.mdsrose.enums.ColorOption;
import com.dg.mdsrose.enums.MarkerOption;

public final class Shape {

    private final ColorOption color;
    private final MarkerOption marker;
    private final String label;

    public Shape(ColorOption color, MarkerOption marker, String label) {
        this.color = color;
        this.marker = marker;
        this.label = label;
    }

    public ColorOption getColor() {
        return color;
    }

    public MarkerOption getMarker() {
        return marker;
    }

    public String getLabel() {
        return label;
    }
}
