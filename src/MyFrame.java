import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MyFrame extends JFrame {

    private LeftPanel leftPanel;
    RightPanel rightPanel;
    private JPanel searchPanel;
    private SearchButton button;
    private String [] adresses;
    private SearchButton lButton;
    private SearchButton mButton;

    public MyFrame(){
        super("Alpine Skiing Informator");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1000,600));
        setResizable(false);
        setLocation(450,200);
        ImageIcon icon = new ImageIcon("img/fis.png");
        setIconImage(icon.getImage());

        adresses = new String[5];
        adresses[0] = "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=ALL&gendercode=M";
        adresses[1] = "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=SL&gendercode=M";
        adresses[2] = "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=GS&gendercode=M";
        adresses[3] = "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=SG&gendercode=M";
        adresses[4] = "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=DH&gendercode=M";

        leftPanel = new LeftPanel(this);
        add(leftPanel,BorderLayout.WEST);

        rightPanel = new RightPanel();
        add(rightPanel,BorderLayout.EAST);

        searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());

        JPanel searchPanel1 = new JPanel();
        searchPanel1.setLayout(new GridLayout());

        lButton = new SearchButton("Ladies") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String [] adresses = getAdresses();
                for (int i = 0; i < adresses.length; i++) {
                    adresses[i] = adresses[i].replace("=M","=L");
                }
                setAdresses(adresses);
                this.setBackground(new Color(255, 215, 0));
                this.setForeground(new Color(0));
                mButton.setBackground(new Color(0,0,128));
                mButton.setForeground(new Color(255,255,255));

                leftPanel.iPanel.replaceImage("img/shiffrin.jpg");

                String adress = adresses[0];
                FisPageCrawler clawler = new FisPageCrawler(adress);
                crawlAndPrint(clawler);
            }
        };
        searchPanel1.add(lButton);

        mButton = new SearchButton("Men") {

            @Override
            public void specialSettings() {
                this.setBackground(new Color(255, 215, 0));
                this.setForeground(new Color(0));
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                String [] adresses = getAdresses();
                for (int i = 0; i < adresses.length; i++) {
                    adresses[i] = adresses[i].replace("=L","=M");
                }
                setAdresses(adresses);
                this.setBackground(new Color(255, 215, 0));
                this.setForeground(new Color(0));
                lButton.setBackground(new Color(0,0,128));
                lButton.setForeground(new Color(255,255,255));

                leftPanel.iPanel.replaceImage("img/marcel.jpg");

                String adress = adresses[0];
                FisPageCrawler clawler = new FisPageCrawler(adress);
                crawlAndPrint(clawler);
            }
        };
        searchPanel1.add(mButton);

        searchPanel.add(searchPanel1,BorderLayout.PAGE_START);

        searchPanel1 = new JPanel();
        searchPanel1.setLayout(new GridLayout());

        button = new SearchButton("Slalom") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adress = adresses[1];
                FisPageCrawler clawler = new FisPageCrawler(adress);
                crawlAndPrint(clawler);
            }
        };
        searchPanel1.add(button);

        button = new SearchButton("Gigant Slalom") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adress = adresses[2];
                FisPageCrawler clawler = new FisPageCrawler(adress);
                crawlAndPrint(clawler);
            }
        };
        searchPanel1.add(button);

        button = new SearchButton("Super Gigant") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adress = adresses[3];
                FisPageCrawler clawler = new FisPageCrawler(adress);
                crawlAndPrint(clawler);
            }
        };
        searchPanel1.add(button);

        button = new SearchButton("Downhill") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adress = adresses[4];
                FisPageCrawler clawler = new FisPageCrawler(adress);
                crawlAndPrint(clawler);
            }
        };
        searchPanel1.add(button);
        searchPanel.add(searchPanel1,BorderLayout.PAGE_END);

        add(searchPanel,BorderLayout.PAGE_END);

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void crawlAndPrint(FisPageCrawler crawler) {
        Thread thread = new Thread(new SearchingAssistant(rightPanel,crawler));
        thread.start();
    }

    static void openErrorWindow(String message){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ErrorFrame(message);
            }
        });
    }

    String[] getAdresses() {
        return adresses;
    }

    void setAdresses(String [] adresses){
        this.adresses = adresses;
    }
}
