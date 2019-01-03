package Crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FisPageCrawler {
    private String adress;
    private List <String> names;
    private List <String> points;
    private String pageContent;
    private String namePattern = "<div class=\"g-xs-10 g-sm-9 g-md-4 g-lg-4 justify-left bold align-xs-top\">([^<]*)<";
    private String pointsPattern = "<div class=\"pl-xs-1 pl-sm-1 g-xs-10 g-sm-7 g-md-15 g-lg-12 justify-right bold\">([^<]*)<";

    public FisPageCrawler(String adress){
        this.adress = adress;
        names = new LinkedList<>();
        points = new LinkedList<>();
    }

    public List <String> getNames(){
        return names;
    }

    public List<String> getPoints() {
        return points;
    }

    public void crawl() throws IOException{
        URL url = null;
        BufferedReader reader = null;
        try {
            url = new URL(adress);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder builder = new StringBuilder();

            while ( (pageContent = reader.readLine()) != null ){
                builder.append(pageContent);
            }

             pageContent = builder.toString();

            Pattern pattern = Pattern.compile(namePattern);
            Matcher matcher = pattern.matcher(pageContent);

            while (matcher.find()){
                String w = matcher.group(1);
                names.add(w);
            }

            pattern = Pattern.compile(pointsPattern);
            matcher = pattern.matcher(pageContent);

            while (matcher.find()){
                String w = matcher.group(1);
                points.add(w);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}