import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LeftPanel extends JPanel{

    private JTextField text;
    public ImageLabel iPanel;
    private JPanel panel;
    private JLabel label;
    private JButton button;
    private JTextArea area;

    public LeftPanel(){
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
        label.setText("Szukaj na stronie Fisu: ");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(label);

        text = new JTextField();
        text.setPreferredSize(new Dimension(350,30));
        text.setHorizontalAlignment(JTextField.CENTER);
        text.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(text);

        button = new SearchButton("Search") {
            @Override
            public void actionPerformed(ActionEvent e) {
                crawlAndPrint();
            }
        };
        panel.add(button);

        area = new JTextArea();
        area.setEditable(false);
        area.setBackground(new Color(240, 245, 245));
        area.setFont(new Font("Arial", Font.PLAIN, 16));
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
