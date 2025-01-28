package com.dg.mdsrose.project;

import java.awt.*;

public class ConcreteShapeBuilder implements ShapeBuilder {

    private Color color;
    private java.awt.Shape marker;
    private String label;

    @Override
    public void setMarker(java.awt.Shape marker) {
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
