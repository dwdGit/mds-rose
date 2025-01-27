package com.dg.mdsrose.project;

import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;

public class ConcreteShapeBuilder implements ShapeBuilder {

    private Color color;
    private Marker marker;
    private String label;

    @Override
    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    public Shape getResult() {
        return new Shape(color,marker,label);
    }
}
