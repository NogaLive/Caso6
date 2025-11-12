package com.smartbuild.ui;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new SmartBuildAuthFrame().setVisible(true);
        });
    }
}
