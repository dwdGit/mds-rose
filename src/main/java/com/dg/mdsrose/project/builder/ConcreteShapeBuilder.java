package com.dg.mdsrose.project.builder;

import com.dg.mdsrose.enums.ColorOption;
import com.dg.mdsrose.enums.MarkerOption;
import com.dg.mdsrose.project.model.Shape;

public class ConcreteShapeBuilder implements ShapeBuilder {

    private ColorOption color;
    private MarkerOption marker;
    private String label;

    @Override
    public void setMarker(MarkerOption marker) {
        this.marker = marker;
    }

    @Override
    public void setColor(ColorOption color) {
        this.color = color;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    public Shape getResult() {
        return new Shape(color,marker,label);
    }

    public static ConcreteShapeBuilder of(String label, ColorOption color, MarkerOption marker) {
        ConcreteShapeBuilder concreteShapeBuilder = new ConcreteShapeBuilder();
        concreteShapeBuilder.setLabel(label);
        concreteShapeBuilder.setColor(color);
        concreteShapeBuilder.setMarker(marker);
        return concreteShapeBuilder;
    }
}
