package com.dg.mdsrose.project.builder;

import com.dg.mdsrose.enums.ColorOption;
import com.dg.mdsrose.enums.MarkerOption;

public interface ShapeBuilder {

    void setMarker(MarkerOption marker);
    void setColor(ColorOption color);
    void setLabel(String label);
}
