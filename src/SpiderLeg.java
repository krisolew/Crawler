import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SpiderLeg {
    private List<String> links = new LinkedList<>();
    private Document htmlDocument;

    public void crawl(String url){
        try{
            Connection connection = Jsoup.connect(url).userAgent("USER_AGENT");
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;

            System.out.println("Received web page at " + url);

            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage){
                links.add(link.absUrl("href"));
            }

        } catch (IOException e){
            System.out.println("Error in out HTTP request " + e);
        }
    }

    public boolean searchForWord(String searchWord){
        System.out.println("Searching for the word " + searchWord + "...");
        String bodytext = this.htmlDocument.body().text();
        return bodytext.toLowerCase().contains(searchWord.toLowerCase());
    }

    public List<String> getLinks(){
        return links;
    }
}
