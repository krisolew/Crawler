import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;



public class Spider {
    private static final int MAX_PAGES_TO_SEARCH = 1;
    private Set<String> pagesVisited = new HashSet<>();
    private List<String> pagesToVisited = new LinkedList<>();

    private String nextUrl(){
        String nextUrl;
        do {
            nextUrl = pagesToVisited.remove(0);
        } while ( pagesVisited.contains(nextUrl) );
        pagesVisited.add(nextUrl);
        return nextUrl;
    }

    public void search (String url, String searchWord){
        while ( pagesVisited.size() < MAX_PAGES_TO_SEARCH ){
            String currentUrl;
            SpiderLeg leg = new SpiderLeg();
            if( pagesToVisited.isEmpty() ){
                currentUrl = url;
                pagesVisited.add(url);
            }
            else{
                currentUrl = nextUrl();
            }

            leg.crawl(currentUrl);
            boolean success = leg.searchForWord(searchWord);
            if (success){
                System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
                break;
            }
            pagesToVisited.addAll(leg.getLinks());
        }
        System.out.println(String.format("**Done** Visited %s web page(s)", this.pagesVisited.size()));
    }
}
