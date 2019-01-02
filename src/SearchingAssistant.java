import java.io.IOException;
import java.util.List;

public class SearchingAssistant implements Runnable {
    private RightPanel panel;
    private FisPageCrawler fisCrawler;

    private Crawler crawler;
    private String word;
    private String url;
    private LeftPanel lPanel;

    public SearchingAssistant(RightPanel panel, FisPageCrawler fisCrawler){
        this.panel = panel;
        this.fisCrawler = fisCrawler;
    }
    public SearchingAssistant(Crawler crawler, String word, String url, LeftPanel lPanel){
        this.crawler = crawler;
        this.word = word;
        this.url = url;
        this.lPanel = lPanel;
    }

    @Override
    public void run(){
        if (fisCrawler!=null)
        {
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
        else{
            crawler.search(url,word);
            lPanel.setAreaText(crawler.getLastPage());
        }
    }
}
