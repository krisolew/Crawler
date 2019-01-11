package Crawler.GUI;

import Crawler.SkierData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RightPanel extends JPanel{

    private JTextArea text;

    RightPanel(){
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500,600));

        text = new JTextArea();
        text.setFont(new Font( "Arial", Font.PLAIN, 19));
        text.setTabSize(4);
        text.setBackground(new Color(240, 240, 245));
        text.setEditable(false);

        JScrollPane scroll = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll);
    }

    public void setTextContent(List <SkierData> skiers){
        String name;
        StringBuilder builder = new StringBuilder();
        SpecialInteager i = new SpecialInteager(1);

        builder.append("  Nr   " + "   Pkt  " + "    Country\t" + "\tName\n" );

        while (!skiers.isEmpty()) {
            SkierData skier = skiers.remove(0);
            builder.append(i);
            builder.append("  ");
            builder.append(skier.toString());
            i.increment();
        }

        name = builder.toString();
        text.setText(name);
    }

    public void setTextContent(String content){
        text.setText(content);
    }
}
