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
        JFrame f = new JFrame("Sandbox");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(500, 500, 500, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        //JPanel p = new
        //JDialog dialog = new JDialog(f);

        gameOverScreen dialog = new gameOverScreen(f, 2);
        if(!dialog.getNewGame()){
            f.dispose();
        }
    }
}
