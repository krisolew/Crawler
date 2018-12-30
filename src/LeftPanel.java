import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeftPanel extends JPanel{

    MyFrame frame;
    ImageLabel iPanel;
    private JTextField text;
    private JPanel panel;
    private JLabel label;
    private JButton button;
    private JTextArea area;

    public LeftPanel(MyFrame frame){
        this.frame = frame;
        setPreferredSize(new Dimension(500,600));
        setLayout(new FlowLayout());
        setFont(new Font("Arial", Font.PLAIN, 20));
        setBackground(new Color(240, 240, 245));

        iPanel = new ImageLabel("img/marcel.jpg");
        add(iPanel);

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(500,255));
        panel.setBackground(new Color(240, 240, 245));
        panel.setLayout(new FlowLayout());

        label = new JLabel();
        label.setText("Select year:");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setPreferredSize(new Dimension(350,30));
        label.setHorizontalAlignment(0);
        panel.add(label);

        text = new JTextField();
        text.setPreferredSize(new Dimension(350,30));
        text.setHorizontalAlignment(JTextField.CENTER);
        text.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(text);

        button = new SearchButton("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String [] adresses = frame.getAdresses();
                String oldYear;
                Pattern pattern = Pattern.compile("([^\\d])+(\\d\\d\\d\\d)([^\\d])+");

                String newYear = text.getText();
                Pattern yearPattern = Pattern.compile("\\d\\d\\d\\d");
                Matcher yearMatcher = yearPattern.matcher(newYear);
                if(!yearMatcher.find()) {
                    area.setForeground(new Color(255,0,0));
                    area.setText("Incorrect year format!");
                    return;
                }

                for (int i = 0; i < adresses.length; i++) {
                    Matcher matcher = pattern.matcher(adresses[i]);
                    if(matcher.find()) {
                        oldYear = matcher.group(2);
                        adresses[i] = adresses[i].replace(oldYear, newYear);
                    }
                }
                frame.setAdresses(adresses);
                text.setText("");
                area.setForeground(new Color(0,0,0));
                area.setText("Year " + newYear );
                frame.rightPanel.setTextContent("");
            }
        };
        panel.add(button);

        area = new JTextArea("Year 2019");
        area.setEditable(false);
        area.setBackground(new Color(240, 245, 245));
        area.setFont(new Font("Arial", Font.PLAIN, 17));
        area.setPreferredSize(new Dimension(350,50));
        panel.add(area);

        add(panel);
    }

    void setAreaText(String text){
        area.setText(text);
    }

    private void crawlAndPrint() {
        setAreaText("Loading...");
        Crawler crawler = new Crawler();
        Thread thread = new Thread(new SearchingAssistant(crawler,text.getText(),"https://www.fis-ski.com/",this));
        thread.start();
        area.setEditable(true);
    }
}
