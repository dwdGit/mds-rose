package com.dg.mdsrose.view;

import javax.swing.*;
import java.awt.*;

public abstract class BaseJFrame extends JFrame {
    protected void createAndShowGUI() {
        this.setTitle(setTitle());
        this.setContentPane(setContentPanel());
        this.setDefaultCloseOperation(setDefaultCloseOperation());
        this.setPreferredSize(setPreferredSize());
        this.pack();
        this.setLocationRelativeTo(setLocationRelativeTo());
        this.setVisible(true);
    }

    protected abstract String setTitle();

    protected abstract JPanel setContentPanel();

    protected int setDefaultCloseOperation() {
        return WindowConstants.DISPOSE_ON_CLOSE;
    }

    protected Component setLocationRelativeTo() {
        return null;
    }

    protected Dimension setPreferredSize() {
        return new Dimension(800, 640);
    }
}
