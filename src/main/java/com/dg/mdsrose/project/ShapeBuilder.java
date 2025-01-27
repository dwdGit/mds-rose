package com.dg.mdsrose.project;

import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;

public interface ShapeBuilder {

    void setMarker(Marker marker);
    void setColor(Color color);
    void setLabel(String label);
}
