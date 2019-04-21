package Crawler.GUI;

import Crawler.GUILogic.RaceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeftPanel extends JPanel implements ItemListener {

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

        Integer year = calculateCurrentYear();
        setYear(year.toString());
        int size = year - 1967 + 1;
        String [] years = new String[size];
        for (int i = 0; i < size; i++) {
            years[i] = year.toString();
            year--;
            years[i] = year + "/" + years[i];
        }

        comboBox = new JComboBox<>(years);
        comboBox.setPreferredSize(new Dimension(350,40));
        comboBox.setFont(new Font("Arial", Font.PLAIN, 23));
        comboBox.addItemListener(this);
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

    public void itemStateChanged(ItemEvent e) {
        Pattern yearPattern = Pattern.compile("(\\d\\d\\d\\d)/(\\d\\d\\d\\d)");
        Matcher yearMatcher = yearPattern.matcher(comboBox.getSelectedItem().toString());

        String newYear = "";
        if(yearMatcher.find())
            newYear = yearMatcher.group(2);

        setYear(newYear);
        resetSettings();

        Thread thread = new Thread( () -> frame.rightPanel.setTextContent(""));
        frame.logic.addThreadToExecutor(thread);
    }

    private int calculateCurrentYear(){
        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        DateFormat monthFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        int  year = Integer.parseInt(yearFormat.format(date));
        int month = Integer.parseInt(monthFormat.format(date));
        if (month >= 10) year++;
        return year;
    }

    private void setYear(String newYear){
        Map<RaceType, String> adresses = frame.logic.getAdresses();
        String oldYear;
        Pattern pattern = Pattern.compile("([^\\d])+(\\d\\d\\d\\d)([^\\d])+");

        for (Map.Entry<RaceType, String> adress : adresses.entrySet()) {
            Matcher matcher = pattern.matcher(adress.getValue());
            if(matcher.find()) {
                oldYear = matcher.group(2);
                adresses.put(adress.getKey(), adress.getValue().replace(oldYear, newYear));
            }
        }
    }

    void resetSettings(){
        setAreaText("");
        replaceImage("img/start.jpg");
    }
}
