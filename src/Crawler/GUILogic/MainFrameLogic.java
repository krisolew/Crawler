package Crawler.GUILogic;

import Crawler.FisPageCrawler;
import Crawler.GUI.ErrorFrame;
import Crawler.GUI.MainFrame;
import Crawler.GUI.SearchButton;
import Crawler.SearchingAssistant;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainFrameLogic {

    private MainFrame frame;
    private Map<RaceType, String> adresses;
    private Map<RaceType, String> femaleImages;
    private Map<RaceType, String> maleImages;
    private ExecutorService executor;
    private Future future = null;
    private boolean man = true;

    public MainFrameLogic(MainFrame frame)
    {
        this.frame = frame;
        executor = Executors.newSingleThreadExecutor();

        adresses = new HashMap<>();
        adresses.put(RaceType.ALL, "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=ALL&gendercode=M");
        adresses.put(RaceType.SL, "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=SL&gendercode=M");
        adresses.put(RaceType.GS, "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=GS&gendercode=M");
        adresses.put(RaceType.SG, "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=SG&gendercode=M");
        adresses.put(RaceType.DH, "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=DH&gendercode=M");

        femaleImages = new HashMap<>();
        femaleImages.put(RaceType.ALL, "img/podium.jpg");
        femaleImages.put(RaceType.SL, "img/shiffrin.jpg");
        femaleImages.put(RaceType.GS, "img/nina.jpg");
        femaleImages.put(RaceType.SG, "img/ilka.jpg");
        femaleImages.put(RaceType.DH, "img/vonn.jpg");

        maleImages = new HashMap<>();
        maleImages.put(RaceType.ALL, "img/kula.jpg");
        maleImages.put(RaceType.SL, "img/hirscher.jpg");
        maleImages.put(RaceType.GS, "img/ligety.jpg");
        maleImages.put(RaceType.SG, "img/jansrud.jpg");
        maleImages.put(RaceType.DH, "img/bode.jpg");
    }

    public Map<RaceType, String> getAdresses() {
        return adresses;
    }

    private void addCrawlerToExecutor(FisPageCrawler crawler) {
        Thread thread = new Thread(new SearchingAssistant(frame, crawler));
        if (future != null && !future.isDone()) future.cancel(true);
        future = executor.submit(thread);
    }

    public void addThreadToExecutor(Thread thread){
        executor.submit(thread);
    }

    public static void openErrorWindow(String message){
        EventQueue.invokeLater(new Thread( () -> new ErrorFrame(message)));
    }

    public SearchButton createLadiesButton()
    {
        return new SearchButton("Ladies") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Map.Entry<RaceType, String> adress : adresses.entrySet()) {
                    adresses.put(adress.getKey(), adress.getValue().replace("=M","=L") );
                }

                man = false;
                frame.setGenderButtonOnClickProperties(this, man);
                frame.leftPanel.setAreaText(RaceType.ALL.toString());
                frame.leftPanel.replaceImage(femaleImages.get(RaceType.ALL));

                FisPageCrawler crawler = new FisPageCrawler(adresses.get(RaceType.ALL));
                addCrawlerToExecutor(crawler);
            }
        };
    }

    public SearchButton createManButton()
    {
        return new SearchButton("Men") {

            @Override
            public void specialSettings() {
                frame.setSpecialButtonSettings(this);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                for (Map.Entry<RaceType, String> adress : adresses.entrySet()) {
                    adresses.put(adress.getKey(), adress.getValue().replace("=L","=M") );
                }

                man = true;
                frame.setGenderButtonOnClickProperties(this, man);
                frame.leftPanel.setAreaText(RaceType.ALL.toString());
                frame.leftPanel.replaceImage(maleImages.get(RaceType.ALL));

                FisPageCrawler crawler = new FisPageCrawler(adresses.get(RaceType.ALL));
                addCrawlerToExecutor(crawler);
            }
        };
    }

    public SearchButton createSearchButton(RaceType type)
    {
        return new SearchButton(type.toString()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.leftPanel.setAreaText(type.toString());
                if (man)
                    frame.leftPanel.replaceImage(maleImages.get(type));
                else
                    frame.leftPanel.replaceImage(femaleImages.get(type));
                FisPageCrawler crawler = new FisPageCrawler(adresses.get(type));
                addCrawlerToExecutor(crawler);
            }
        };
    }
}
