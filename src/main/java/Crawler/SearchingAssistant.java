package Crawler;

import Crawler.GUI.MainFrame;
import Crawler.GUILogic.MainFrameLogic;

import javax.swing.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SearchingAssistant implements Runnable {

    private MainFrame frame;
    private FisPageCrawler fisCrawler;

    public SearchingAssistant(JFrame frame, FisPageCrawler fisCrawler){
        this.frame = (MainFrame) frame;
        this.fisCrawler = fisCrawler;
    }

    @Override
    public void run() {
        try {
            frame.rightPanel.setTextContent(" Loading...");
            fisCrawler.crawl();

            List<String> names = fisCrawler.getNames();
            List<String> points = fisCrawler.getPoints();
            List<String> countries = fisCrawler.getCountries();
            List<SkierData> skiers = new LinkedList<>();

            while(!names.isEmpty() && !points.get(0).equals("---")){
                String point = points.remove(0);
                if (point.contains("'")) point = point.replace("'","");
                skiers.add( new SkierData(names.remove(0), Integer.parseInt(point), countries.remove(0)));
            }

            frame.rightPanel.setTextContent(skiers);
        }
        catch (IOException ex){
            MainFrameLogic.openErrorWindow("No internet connection");
            frame.resetSettings();
        }
    }
}
