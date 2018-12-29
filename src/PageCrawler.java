import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageCrawler {
    private List <String> links;
    private String pageContent;
    private String linkPattern = "http[s]://[^\"']+";

    public PageCrawler(){
        links = new LinkedList<>();
    }

    public boolean searchWord(String word){
        if ( pageContent.toLowerCase().contains(word.toLowerCase()) )
            return true;
        else
            return false;
    }

    public List <String> getLinks(){
        return links;
    }

    public void crawl(String url) throws IOException{
        URL currentUrl = null;
        BufferedReader reader = null;
        try {
            currentUrl = new URL(url);
            reader = new BufferedReader(new InputStreamReader(currentUrl.openStream()));
            StringBuilder builder = new StringBuilder();

            while ( (pageContent = reader.readLine()) != null ){
                builder.append(pageContent);
            }

            pageContent = builder.toString();

            Pattern pattern = Pattern.compile(linkPattern);
            Matcher matcher = pattern.matcher(pageContent);

            while (matcher.find()){
                String w = matcher.group();
                links.add(w);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
