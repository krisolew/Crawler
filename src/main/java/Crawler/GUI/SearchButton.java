package Crawler.GUI;

import Crawler.Colors;
import Crawler.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class SearchButton extends JButton implements ActionListener {

    public SearchButton(String name) {
        super(name);
        setFont(Fonts.ARIAL_20);
        setPreferredSize(new Dimension(350, 45));
        addActionListener(this);
        setBackground(Colors.BLUE);
        setForeground(Colors.WHITE);
        specialSettings();
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);

    public void specialSettings() {
    } //default implementation
}
