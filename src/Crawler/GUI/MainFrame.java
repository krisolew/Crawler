package Crawler.GUI;

import Crawler.FisPageCrawler;
import Crawler.SearchingAssistant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainFrame extends JFrame {

    public RightPanel rightPanel;
    private LeftPanel leftPanel;
    private SearchButton button;
    private String [] adresses;
    private SearchButton lButton;
    private SearchButton mButton;
    private boolean man = true;
    private ExecutorService executor;
    private Future future = null;

    public MainFrame(){
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

        executor = Executors.newSingleThreadExecutor();

        leftPanel = new LeftPanel(this);
        add(leftPanel,BorderLayout.WEST);

        rightPanel = new RightPanel();
        add(rightPanel,BorderLayout.EAST);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());

        JPanel searchRow = new JPanel();
        searchRow.setLayout(new GridLayout());

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
                leftPanel.replaceImage("img/podium.jpg");
                leftPanel.setAreaText("Overall");
                man = false;

                FisPageCrawler clawler = new FisPageCrawler(adresses[0]);
                addCrawlerToExecutor(clawler);
            }
        };
        searchRow.add(lButton);

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
                leftPanel.replaceImage("img/kula.jpg");
                leftPanel.setAreaText("Overall");
                man = true;

                FisPageCrawler clawler = new FisPageCrawler(adresses[0]);
                addCrawlerToExecutor(clawler);
            }
        };
        searchRow.add(mButton);

        searchPanel.add(searchRow,BorderLayout.PAGE_START);

        searchRow = new JPanel();
        searchRow.setLayout(new GridLayout());

        button = new SearchButton("Slalom") {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftPanel.setAreaText("Slalom");
                if (man) leftPanel.replaceImage("img/marcel.jpg");
                else leftPanel.replaceImage("img/shiffrin.jpg");
                FisPageCrawler clawler = new FisPageCrawler(adresses[1]);
                addCrawlerToExecutor(clawler);
            }
        };
        searchRow.add(button);

        button = new SearchButton("Gigant Slalom") {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftPanel.setAreaText("Gigant Slalom");
                if (man) leftPanel.replaceImage("img/ligety.jpg");
                else leftPanel.replaceImage("img/nina.jpg");
                FisPageCrawler clawler = new FisPageCrawler(adresses[2]);
                addCrawlerToExecutor(clawler);
            }
        };
        searchRow.add(button);

        button = new SearchButton("Super Gigant") {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftPanel.setAreaText("Super Gigant");
                if (man) leftPanel.replaceImage("img/jansrud.jpg");
                else leftPanel.replaceImage("img/ilka.jpg");
                FisPageCrawler clawler = new FisPageCrawler(adresses[3]);
                addCrawlerToExecutor(clawler);
            }
        };
        searchRow.add(button);

        button = new SearchButton("Downhill") {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftPanel.setAreaText("Downhill");
                if (man) leftPanel.replaceImage("img/bode.jpg");
                else leftPanel.replaceImage("img/vonn.jpg");
                FisPageCrawler clawler = new FisPageCrawler(adresses[4]);
                addCrawlerToExecutor(clawler);
            }
        };
        searchRow.add(button);
        searchPanel.add(searchRow);

        add(searchPanel,BorderLayout.PAGE_END);

        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    String[] getAdresses() {
        return adresses;
    }

    void setAdresses(String [] adresses){
        this.adresses = adresses;
    }

    private void addCrawlerToExecutor(FisPageCrawler crawler) {
        Thread thread = new Thread(new SearchingAssistant(this,crawler));
        if (future != null && !future.isDone()) future.cancel(true);
        future = executor.submit(thread);
    }

    void addThreadToExecutor(Thread thread){
        executor.submit(thread);
    }

    public void resetSettings(){

        leftPanel.resetSettings();
        rightPanel.setTextContent();
    }

    public static void openErrorWindow(String message){
        EventQueue.invokeLater(new Thread( () -> new ErrorFrame(message)));
    }
}
