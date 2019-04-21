package Crawler.GUI;

import Crawler.GUILogic.LeftPanelLogic;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {

    private LeftPanelLogic logic;
    private ImageLabel iPanel;
    private MainFrame frame;
    private JTextArea area;
    private JComboBox<String> comboBox;

    LeftPanel(MainFrame frame){
    
        this.frame = frame;
        setPreferredSize(new Dimension(500,600));
        setLayout(new FlowLayout());
        setFont(new Font("Arial", Font.PLAIN, 20));
        setBackground(new Color(240, 240, 245));

        logic = new LeftPanelLogic(this);

        iPanel = new ImageLabel("img/start.jpg");
        add(iPanel);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(500,255));
        panel.setBackground(new Color(240, 240, 245));
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel();
        label.setText("Select season:");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setPreferredSize(new Dimension(350,30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);

        String [] years = logic.getYears();

        comboBox = new JComboBox<>(years);
        comboBox.setPreferredSize(new Dimension(350,40));
        comboBox.setFont(new Font("Arial", Font.PLAIN, 23));
        comboBox.addItemListener(logic);
        comboBox.setMaximumRowCount(5);
        panel.add(comboBox);

        area = new JTextArea();
        area.setEditable(false);
        area.setBackground(new Color(240, 240, 245));
        area.setFont(new Font("Arial", Font.PLAIN, 17));
        area.setPreferredSize(new Dimension(350,50));
        panel.add(area);

        add(panel);
    }

    public void setAreaText(String text){
        area.setText(text);
    }

    public void replaceImage(String path){
        iPanel.replaceImage(path);
    }

    public void resetSettings(){
        setAreaText("");
        replaceImage("img/start.jpg");
    }

    public Object getComboBoxSelectedItem()
    {
        return comboBox.getSelectedItem();
    }

    public MainFrame getFrame()
    {
        return frame;
    }
}
