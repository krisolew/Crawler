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

                leftPanel.iPanel.replaceImage("img/podium.jpg");
                leftPanel.setAreaText("Overall");

                FisPageCrawler clawler = new FisPageCrawler(adresses[0]);
                crawlAndPrint(clawler);
                man = false;
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

                leftPanel.iPanel.replaceImage("img/kula.jpg");
                leftPanel.setAreaText("Overall");

                FisPageCrawler clawler = new FisPageCrawler(adresses[0]);
                crawlAndPrint(clawler);
                man = true;
            }
        };
        searchRow.add(mButton);

        searchPanel.add(searchRow,BorderLayout.PAGE_START);

        searchRow = new JPanel();
        searchRow.setLayout(new GridLayout());

        button = new SearchButton("Slalom") {
            @Override
            public void actionPerformed(ActionEvent e) {
                FisPageCrawler clawler = new FisPageCrawler(adresses[1]);
                crawlAndPrint(clawler);
                if (man) leftPanel.iPanel.replaceImage("img/marcel.jpg");
                else leftPanel.iPanel.replaceImage("img/shiffrin.jpg");
                leftPanel.setAreaText("Slalom");
            }
        };
        searchRow.add(button);

        button = new SearchButton("Gigant Slalom") {
            @Override
            public void actionPerformed(ActionEvent e) {
                FisPageCrawler clawler = new FisPageCrawler(adresses[2]);
                crawlAndPrint(clawler);
                if (man) leftPanel.iPanel.replaceImage("img/ligety.jpg");
                else leftPanel.iPanel.replaceImage("img/nina.jpg");
                leftPanel.setAreaText("Gigant Slalom");
            }
        };
        searchRow.add(button);

        button = new SearchButton("Super Gigant") {
            @Override
            public void actionPerformed(ActionEvent e) {
                FisPageCrawler clawler = new FisPageCrawler(adresses[3]);
                crawlAndPrint(clawler);
                if (man) leftPanel.iPanel.replaceImage("img/jansrud.jpg");
                else leftPanel.iPanel.replaceImage("img/ilka.jpg");
                leftPanel.setAreaText("Super Gigant");
            }
        };
        searchRow.add(button);

        button = new SearchButton("Downhill") {
            @Override
            public void actionPerformed(ActionEvent e) {
                FisPageCrawler clawler = new FisPageCrawler(adresses[4]);
                crawlAndPrint(clawler);
                if (man) leftPanel.iPanel.replaceImage("img/bode.jpg");
                else leftPanel.iPanel.replaceImage("img/vonn.jpg");
                leftPanel.setAreaText("Downhill");
            }
        };
        searchRow.add(button);
        searchPanel.add(searchRow);

        add(searchPanel,BorderLayout.PAGE_END);

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void crawlAndPrint(FisPageCrawler crawler) {
        Thread thread = new Thread(new SearchingAssistant(rightPanel,crawler));
        executor.submit(thread);
    }

    public void addThreadToExecutor(Thread thread){
        executor.submit(thread);
    }

    static void openErrorWindow(String message){
        EventQueue.invokeLater(new Thread( () -> new ErrorFrame(message)));
    }

    String[] getAdresses() {
        return adresses;
    }

    void setAdresses(String [] adresses){
        this.adresses = adresses;
    }
}
