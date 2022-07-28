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
        Tile t1 = new Tile(0, Color.BLACK);
        Tile t2 = t1;
        t2.setValue("blackQueen");
        System.out.println(t1.getValueAsString());
    }
}
