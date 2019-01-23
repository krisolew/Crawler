package Crawler.GUI;

import Crawler.SkierData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RightPanel extends JPanel{

    private JTextArea text;
    private JScrollPane scroll;

    RightPanel(){
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(494,600));

        text = new JTextArea();
        text.setFont(new Font( "Arial", Font.PLAIN, 19));
        text.setTabSize(4);
        text.setBackground(new Color(240, 240, 245));
        text.setEditable(false);

        scroll = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll);
    }

    public void setTextContent(List <SkierData> skiers){
        String name;
        StringBuilder builder = new StringBuilder();
        int number = 1;

        builder.append("  Nr   " + "   Pkt  " + "    Country\t" + "\tName\n" );

        while (!skiers.isEmpty()) {
            SkierData skier = skiers.remove(0);
            builder.append(formatNumber(number));
            builder.append("  ");
            builder.append(skier.toString());
            number++;
        }

        name = builder.toString();
        text.setText(name);
    }

    public void setTextContent(String content){
        scroll.updateUI();
        text.setText(content);
    }

    public static String formatNumber(int number){
        int numOfDigits=4;
        int tmp = number;
        while (tmp > 0){
            numOfDigits--;
            tmp/=10;
        }
        String space="";
        while (numOfDigits>0){
            space = space + " ";
            numOfDigits--;
        }
        return space + number + space;
    }
}
