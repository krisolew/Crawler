package Crawler.GUI;

import Crawler.Colors;
import Crawler.Fonts;
import Crawler.GUILogic.RightPanelLogic;
import Crawler.SkierData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RightPanel extends JPanel{

    private RightPanelLogic logic;
    private JTextArea text;
    private JScrollPane scroll;

    RightPanel(){
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(494,600));

        logic = new RightPanelLogic();

        text = new JTextArea();
        text.setFont(Fonts.ARIAL_20);
        text.setTabSize(4);
        text.setBackground(Colors.WHITE);
        text.setEditable(false);

        scroll = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll);
    }

    public void setTextContent(List <SkierData> skiers){
        text.setText(logic.getContentFromList(skiers));
    }

    public void setTextContent(String content){
        scroll.updateUI();
        text.setText(content);
    }
}
