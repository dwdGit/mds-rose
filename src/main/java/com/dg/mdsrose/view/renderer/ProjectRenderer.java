package com.dg.mdsrose.view.renderer;

import com.dg.mdsrose.project.model.Project;

import javax.swing.*;
import java.awt.*;

public class ProjectRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList<?> list,
                                                  Object value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Project project) {
            setText(project.getName());
        }
        return this;
    }
}
