package com.dg.mdsrose;

import com.dg.mdsrose.view.panel.Signup;
import com.dg.mdsrose.view.panel.Login;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
