package Crawler;

import java.io.IOException;
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
            panel.setTextContent(names,points);
        }
        catch (IOException ex){
            MyFrame.openErrorWindow("Not connected to url");
            panel.setTextContent("");
        }
    }
}
