package com.dg.mdsrose.project;

import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;

public final class Shape {

    private final Color color;
    private final SeriesMarkers marker;
    private final String label;

    public Shape(Color color, SeriesMarkers marker, String label) {
        this.color = color;
        this.marker = marker;
        this.label = label;
    }

    public Color getColor() {
        return color;
    }

    public SeriesMarkers getMarker() {
        return marker;
    }

    public String getLabel() {
        return label;
    }
}
