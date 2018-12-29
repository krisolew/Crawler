import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
    private Queue<String> queue = new LinkedList<>();
    private Set<String> marked = new HashSet<>();
    private String regex = "http[s]*://(\\w+\\.)*(\\w+)";

    public void serch(String root) throws IOException {
        queue.add(root);

        while(!queue.isEmpty()){
            String currentUrl = queue.poll();
            System.out.println("Site crowled : " + currentUrl );

            if (marked.size() > 10)
                return;
            marked.add(currentUrl);

            URL url = null;
            BufferedReader br = null;


            url = new URL(currentUrl);
            br = new BufferedReader( new InputStreamReader(url.openStream()));
            //br.close();

            StringBuilder sb = new StringBuilder();
            String tmp = null;

            while ( (tmp = br.readLine()) != null){
                sb.append(tmp);
            }

            tmp = sb.toString();
            System.out.println(tmp);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(tmp);

            while (matcher.find()) {
                String w = matcher.group();

                if (!marked.contains(w)){
                    queue.add(w);
                }
            }
        }

        for (String s : marked){
            System.out.println(s);
        }
    }
}
