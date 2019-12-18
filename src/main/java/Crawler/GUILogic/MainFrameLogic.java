package Crawler.GUILogic;

import Crawler.Enums.Gender;
import Crawler.Enums.RaceType;
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
    private Boolean man = true;

    public MainFrameLogic(MainFrame frame) {
        this.frame = frame;
        executor = Executors.newSingleThreadExecutor();

        adresses = new HashMap<>();
        adresses.put(RaceType.ALL, "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=ALL&gendercode=M");
        adresses.put(RaceType.SL, "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=SL&gendercode=M");
        adresses.put(RaceType.GS, "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=GS&gendercode=M");
        adresses.put(RaceType.SG, "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=SG&gendercode=M");
        adresses.put(RaceType.DH, "https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=DH&gendercode=M");

        femaleImages = new HashMap<>();
        femaleImages.put(RaceType.ALL, "src/main/resources/img/podium.jpg");
        femaleImages.put(RaceType.SL, "src/main/resources/img/shiffrin.jpg");
        femaleImages.put(RaceType.GS, "src/main/resources/img/nina.jpg");
        femaleImages.put(RaceType.SG, "src/main/resources/img/ilka.jpg");
        femaleImages.put(RaceType.DH, "src/main/resources/img/vonn.jpg");

        maleImages = new HashMap<>();
        maleImages.put(RaceType.ALL, "src/main/resources/img/kula.jpg");
        maleImages.put(RaceType.SL, "src/main/resources/img/marcel.jpg");
        maleImages.put(RaceType.GS, "src/main/resources/img/ligety.jpg");
        maleImages.put(RaceType.SG, "src/main/resources/img/jansrud.jpg");
        maleImages.put(RaceType.DH, "src/main/resources/img/bode.jpg");
    }

    public Map<RaceType, String> getAdresses() {
        return adresses;
    }

    private void addCrawlerToExecutor(FisPageCrawler crawler) {
        Thread thread = new Thread(new SearchingAssistant(frame, crawler));
        if (future != null && !future.isDone()) future.cancel(true);
        future = executor.submit(thread);
    }

    public void addThreadToExecutor(Thread thread) {
        executor.submit(thread);
    }

    public static void openErrorWindow(String message) {
        EventQueue.invokeLater(new Thread(() -> new ErrorFrame(message)));
    }

    public SearchButton createGenderButton(Gender gender) {
        return new SearchButton(gender.toString()) {
            @Override
            public void specialSettings() {
                if (gender == Gender.MALE)
                    frame.setSpecialButtonSettings(this);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(gender == Gender.MALE && man) && !(gender == Gender.FEMALE && !man)) {
                    man = !man;

                    String target = gender.getOppositeGenderFirstLetter();
                    String replacement = gender.getFirstLetter();
                    adresses.replaceAll((k, v) -> v.replace(target, replacement));
                    frame.setGenderButtonOnClickProperties(this, man);
                }
                frame.leftPanel.setAreaText(RaceType.ALL.toString());
                frame.leftPanel.replaceImage(man ? maleImages.get(RaceType.ALL) : femaleImages.get(RaceType.ALL));

                addCrawlerToExecutor(new FisPageCrawler(adresses.get(RaceType.ALL)));
            }
        };
    }

    public SearchButton createSearchButton(RaceType type) {
        return new SearchButton(type.toString()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.leftPanel.setAreaText(type.toString());
                frame.leftPanel.replaceImage(man ? maleImages.get(type) : femaleImages.get(type));
                addCrawlerToExecutor(new FisPageCrawler(adresses.get(type)));
            }
        };
    }
}
