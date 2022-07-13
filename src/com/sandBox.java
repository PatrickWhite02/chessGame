package com;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class sandBox {
    public static void main(String[] args) throws IOException {
        URL iconURL = sandBox.class.getResource("/img/whiteKing.png");
        JFrame f = new JFrame();
        JPanel p = new JPanel();
        JButton b = new JButton();
        ImageIcon icon = new ImageIcon(iconURL);
        b.setIcon(icon);
        p.add(b);
        f.setIconImage(icon.getImage());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(p);
        f.setBounds(500, 500, 500, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }
}
