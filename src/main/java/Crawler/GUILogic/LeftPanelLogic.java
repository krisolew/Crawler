package Crawler.GUILogic;

import Crawler.Enums.RaceType;
import Crawler.GUI.LeftPanel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeftPanelLogic implements ItemListener {

    private LeftPanel panel;

    public LeftPanelLogic(LeftPanel panel) {
        this.panel = panel;
    }

    public String[] getYears() {
        Integer year = calculateCurrentYear();
        setYear(year.toString());

        int size = year - 1967 + 1;
        String[] years = new String[size];

        for (int i = 0; i < size; i++) {
            years[i] = year.toString();
            year--;
            years[i] = year + "/" + years[i];
        }

        return years;
    }

    private void setYear(String newYear) {
        Map<RaceType, String> adresses = panel.getFrame().logic.getAdresses();
        String oldYear;
        Pattern pattern = Pattern.compile("([^\\d])+(\\d\\d\\d\\d)([^\\d])+");

        for (Map.Entry<RaceType, String> adress : adresses.entrySet()) {
            Matcher matcher = pattern.matcher(adress.getValue());
            if (matcher.find()) {
                oldYear = matcher.group(2);
                adresses.put(adress.getKey(), adress.getValue().replace(oldYear, newYear));
            }
        }
    }

    private int calculateCurrentYear() {
        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        DateFormat monthFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        int year = Integer.parseInt(yearFormat.format(date));
        int month = Integer.parseInt(monthFormat.format(date));
        if (month >= 10) year++;
        return year;
    }

    public void itemStateChanged(ItemEvent e) {
        Pattern yearPattern = Pattern.compile("(\\d\\d\\d\\d)/(\\d\\d\\d\\d)");
        Matcher yearMatcher = yearPattern.matcher(panel.getComboBoxSelectedItem().toString());

        String newYear = "";
        if (yearMatcher.find())
            newYear = yearMatcher.group(2);

        setYear(newYear);
        panel.resetSettings();

        Thread thread = new Thread(() -> panel.getFrame().rightPanel.setTextContent(""));
        panel.getFrame().logic.addThreadToExecutor(thread);
    }
}
