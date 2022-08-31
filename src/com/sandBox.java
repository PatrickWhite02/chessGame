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
        Board board = new Board();
        board.setLayout(new GridLayout(8,8));
        board.addTiles();
        System.out.println(board.getTile(52));
    }
}
