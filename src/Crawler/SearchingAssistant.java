package Crawler;

import Crawler.GUI.MyFrame;
import Crawler.GUI.RightPanel;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SearchingAssistant implements Runnable {
    private RightPanel panel;
    private FisPageCrawler fisCrawler;

    public SearchingAssistant(RightPanel panel, FisPageCrawler fisCrawler){
        this.panel = panel;
        this.fisCrawler = fisCrawler;
    }

    @Override
    public void run() {
        try {
            panel.setTextContent(" Loading...");
            fisCrawler.crawl();

            List<String> names = fisCrawler.getNames();
            List<String> points = fisCrawler.getPoints();
            List<String> countries = fisCrawler.getCountries();
            List<SkierData> skiers = new LinkedList<>();
            while(!names.isEmpty()){
                skiers.add( new SkierData(names.remove(0), points.remove(0), countries.remove(0)));
            }

            panel.setTextContent(skiers);
        }
        catch (IOException ex){
            MyFrame.openErrorWindow("Not connected to url");
            panel.setTextContent("");
        }
    }
}
