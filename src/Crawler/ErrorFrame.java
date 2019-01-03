package Crawler;

import javax.swing.*;
import java.awt.*;

public class ErrorFrame extends JFrame {

    public ErrorFrame(String message){
        super("Error");
        setPreferredSize(new Dimension(600,300));
        setLocation(650,350);
        setLayout(new BorderLayout());
        setResizable(false);

        JLabel text = new JLabel();
        text.setFont(new Font("Arial",Font.PLAIN,28));
        text.setText(message);
        text.setHorizontalAlignment(0);
        text.setVerticalAlignment(0);
        text.setForeground(new Color(255,0,0));
        add(text);

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
