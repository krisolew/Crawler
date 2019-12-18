package Crawler.GUI;

import Crawler.GUILogic.MainFrameLogic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLabel extends JLabel {

    private BufferedImage image;

    ImageLabel(String path) {
        setPreferredSize(new Dimension(500, 300));
        replaceImage(path);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, this);
    }

    void replaceImage(String path) {
        File file = new File(path);

        try {
            image = ImageIO.read(file);
        } catch (IOException ex) {
            MainFrameLogic.openErrorWindow("Picture not found");
        }
        repaint();
    }
}
