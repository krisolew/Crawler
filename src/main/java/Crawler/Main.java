package Crawler;

import Crawler.GUI.MainFrame;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Thread(MainFrame::new));
    }
}
