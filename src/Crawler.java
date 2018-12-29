import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Crawler{
    private int MAX_NUM_OF_VISITED_PAGES;
    private Set<String> visitedPages;
    private List<String> pagesToVisit;
    private String lastPage;

    public Crawler(){
        MAX_NUM_OF_VISITED_PAGES = 10;
        visitedPages = new HashSet<>(MAX_NUM_OF_VISITED_PAGES+1);
        pagesToVisit = new LinkedList<>();
    }

    public void setMAX_NUM_OF_VISITED_PAGES(int num){
        this.MAX_NUM_OF_VISITED_PAGES = num;
    }

    public int getMAX_NUM_OF_VISITED_PAGES(){
        return MAX_NUM_OF_VISITED_PAGES;
    }

    private String getNextPage(){
        if ( pagesToVisit.isEmpty() ) return null;

        String page;
        do {
           page = pagesToVisit.remove(0);
        } while ( visitedPages.contains(page) );

        return page;
    }

    public void search(String url, String searchPhrase){
        pagesToVisit.add(url);
        String currentUrl;

        while ( !pagesToVisit.isEmpty() && visitedPages.size() < MAX_NUM_OF_VISITED_PAGES ){
            currentUrl = getNextPage();

            PageCrawler pageCrawler = new PageCrawler();

            try {
                pageCrawler.crawl(currentUrl);

                if (pageCrawler.searchWord(searchPhrase)) {
                    lastPage = currentUrl;
                    break;
                }
            } catch (IOException e){
            }

            pagesToVisit.addAll(pageCrawler.getLinks());
            visitedPages.add(currentUrl);
        }
    }

    public void ptintVisitedPages(){
        System.out.println("Odwiedzone strony");
        for(String iteretor : visitedPages){
            System.out.println(iteretor);
        }
        System.out.println("Znalezione linki");
        for(String iteretor : pagesToVisit){
            System.out.println(iteretor);
        }
    }

    public String getLastPage(){
        return lastPage;
    }
}
