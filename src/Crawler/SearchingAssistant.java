package Crawler;

import Crawler.GUI.MyFrame;
import Crawler.GUI.SpecialInteager;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SearchingAssistant implements Runnable {

    private MyFrame frame;
    private FisPageCrawler fisCrawler;

    public SearchingAssistant(MyFrame frame, FisPageCrawler fisCrawler){
        this.frame = frame;
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
                skiers.add( new SkierData(names.remove(0), new SpecialInteager(point), countries.remove(0)));
            }

            frame.rightPanel.setTextContent(skiers);
        }
        catch (IOException ex){
            MyFrame.openErrorWindow("No internet connection");
            frame.resetSettings();
        }
    }
}
