import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //Spider spider = new Spider();
        //spider.search("http://arstechnica.com/", "computer");

        WebCrawler crawler = new WebCrawler();
        try {
            crawler.serch("https://google.com");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
