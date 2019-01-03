package Crawler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyFrame extends JFrame {

    LeftPanel leftPanel;
    RightPanel rightPanel;
    private SearchButton button;
    private String [] adresses;
    private SearchButton lButton;
    private SearchButton mButton;
    private boolean man = true;
    private ExecutorService executor;

    public MyFrame(){
        super("Alpine Skiing Informator");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1000,600));
        setResizable(false);
        setLocation(450,200);
        ImageIcon icon = new ImageIcon("img/fis.png");
        setIconImage(icon.getImage());

        adresses = new String[5];
        try (BufferedReader reader = new BufferedReader(new FileReader("adresses.txt"))){
            int i = 0;
            String line;

            while ( (line = reader.readLine()) != null ) {
                adresses[i] = line;
                i++;
            }
            if (i!=5) throw new IOException();
        } catch (FileNotFoundException ex){
            MyFrame.openErrorWindow("No file with adresses");
        } catch (IOException ex){
            MyFrame.openErrorWindow("To less adresses");
        }

        executor = Executors.newSingleThreadExecutor();

        leftPanel = new LeftPanel(this);
        add(leftPanel,BorderLayout.WEST);

        rightPanel = new RightPanel();
        add(rightPanel,BorderLayout.EAST);

        JPanel searchPanel = new JPanel();
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

                leftPanel.iPanel.replaceImage("img/podium.jpg");

                String adress = adresses[0];
                FisPageCrawler clawler = new FisPageCrawler(adress);
                crawlAndPrint(clawler);
                man = false;
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

                leftPanel.iPanel.replaceImage("img/kula.jpg");

                String adress = adresses[0];
                FisPageCrawler clawler = new FisPageCrawler(adress);
                crawlAndPrint(clawler);
                man = true;
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
                if (man) leftPanel.iPanel.replaceImage("img/marcel.jpg");
                else leftPanel.iPanel.replaceImage("img/shiffrin.jpg");
            }
        };
        searchPanel1.add(button);

        button = new SearchButton("Gigant Slalom") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adress = adresses[2];
                FisPageCrawler clawler = new FisPageCrawler(adress);
                crawlAndPrint(clawler);
                if (man) leftPanel.iPanel.replaceImage("img/ligety.jpg");
                else leftPanel.iPanel.replaceImage("img/nina.jpg");
            }
        };
        searchPanel1.add(button);

        button = new SearchButton("Super Gigant") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adress = adresses[3];
                FisPageCrawler clawler = new FisPageCrawler(adress);
                crawlAndPrint(clawler);
                if (man) leftPanel.iPanel.replaceImage("img/jansrud.jpg");
                else leftPanel.iPanel.replaceImage("img/ilka.jpg");
            }
        };
        searchPanel1.add(button);

        button = new SearchButton("Downhill") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adress = adresses[4];
                FisPageCrawler clawler = new FisPageCrawler(adress);
                crawlAndPrint(clawler);
                if (man) leftPanel.iPanel.replaceImage("img/bode.jpg");
                else leftPanel.iPanel.replaceImage("img/vonn.jpg");
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
        executor.submit(thread);
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
